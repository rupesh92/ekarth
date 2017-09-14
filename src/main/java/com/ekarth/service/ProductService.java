package com.ekarth.service;

import com.ekarth.dao.DatabaseInserter;
import com.ekarth.model.*;
import com.ekarth.security.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    Encryptor encryptor;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public void addProductDetails(Product product, Category category, List<Property> propertyList) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Product productUpdated = addProduct(product);
        Category categoryUpdated = addCategory(category);

        ProductCategory productCategory = new ProductCategory(categoryUpdated.getCategoryId(), productUpdated.getProductId());
        addProductCategory(productCategory);

        List<Property> propertyListNew = addProperties(propertyList);
        List<Property> propertyListOld = propertyList.stream()
                .filter(property -> !propertyNotExistent(property))
                .collect(Collectors.toList());

        propertyListNew.addAll(propertyListOld);
        List<Property> propertiesUpdated = propertyListNew;


        //get product, category and property from the table

        //TODO: the productId and propId could be empty !! fix this.
        List<ProductProperty> productProperties = propertiesUpdated.stream()
                .map(property -> new ProductProperty(productUpdated.getProductId(), property.getPropertyId()))
                .collect(Collectors.toList());
        DatabaseInserter<ProductProperty> productPropertyDatabaseInserter = new DatabaseInserter<>(ProductProperty.class, encryptor);
        productPropertyDatabaseInserter.insertObjects(productProperties);

    }

    private List<Property> addProperties(List<Property> propertyList) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<Property> propertyDatabaseInserter = new DatabaseInserter<>(Property.class, encryptor);

        List<Property> filteredProperties = propertyList.stream()
                .filter(property -> propertyNotExistent(property))
                .collect(Collectors.toList());
        List<Property> propertiesInserted = propertyDatabaseInserter.insertObjects(filteredProperties);
        return propertiesInserted;

    }

    private boolean propertyNotExistent(Property property) {
        return property.getPropertyId() <= 0;
    }

    private void addProductCategory(ProductCategory productCategory) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<ProductCategory> productCategoryDatabaseInserter = new DatabaseInserter<>(ProductCategory.class, encryptor);
        productCategoryDatabaseInserter.insertObjects(Arrays.asList(productCategory));
    }

    private Category addCategory(Category category) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        if (category.getCategoryId() > 0) {
            //category already exists
        } else {
            DatabaseInserter<Category> categoryDatabaseInserter = new DatabaseInserter<>(Category.class, encryptor);
            List<Category> categoryListInserted = categoryDatabaseInserter.insertObjects(Arrays.asList(category));
            if (categoryListInserted.size() == 0 || categoryListInserted.size() > 1) {
                logger.error("I meant to insert one category, why am I getting back 0/multiple? ");
            }
            return categoryListInserted.get(0);
        }
        return category;
    }

    private Product addProduct(Product product) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<Product> productDatabaseInserter = new DatabaseInserter<>(Product.class, encryptor);
        List<Product> products = productDatabaseInserter.insertObjects(Arrays.asList(product));
        if (products.size() == 0 || products.size() > 1) {
            logger.error("I meant to insert one products, why am I getting back 0/multiple? ");
        }
        return products.get(0);
    }

}
