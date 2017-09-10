package com.ekarth.service;

import com.ekarth.dao.DatabaseInserter;
import com.ekarth.model.*;
import com.ekarth.security.Encryptor;
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

    public void addProductDetails(Product product, Category category, List<Property> propertyList) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {

        addProduct(product);
        addCategory(category);

        ProductCategory productCategory = new ProductCategory(category.getCategoryId(), product.getProductId());
        addProductCategory(productCategory);

        addProperties(propertyList);

        //TODO: the productId and propId could be empty !! fix this.
        List<ProductProperty> productProperties = propertyList.stream()
                .map(property -> new ProductProperty(product.getProductId(), property.getPropertyId()))
                .collect(Collectors.toList());
        DatabaseInserter<ProductProperty> productPropertyDatabaseInserter = new DatabaseInserter<>(ProductProperty.class, encryptor);
        productPropertyDatabaseInserter.insertObjects(productProperties);

    }

    private void addProperties(List<Property> propertyList) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<Property> propertyDatabaseInserter = new DatabaseInserter<>(Property.class, encryptor);

        List<Property> filteredProperties = propertyList.stream()
                .filter(property -> propertyNotExistent(property))
                .collect(Collectors.toList());
        propertyDatabaseInserter.insertObjects(filteredProperties);
    }

    private boolean propertyNotExistent(Property property) {
        return property.getPropertyId() <= 0;
    }

    private void addProductCategory(ProductCategory productCategory) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<ProductCategory> productCategoryDatabaseInserter = new DatabaseInserter<>(ProductCategory.class, encryptor);
        productCategoryDatabaseInserter.insertObjects(Arrays.asList(productCategory));
    }

    private void addCategory(Category category) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        if (category.getCategoryId() > 0) {
            //category already exists
        } else {
            DatabaseInserter<Category> categoryDatabaseInserter = new DatabaseInserter<>(Category.class, encryptor);
            categoryDatabaseInserter.insertObjects(Arrays.asList(category));
        }
    }

    private void addProduct(Product product) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<Product> productDatabaseInserter = new DatabaseInserter<>(Product.class, encryptor);
        productDatabaseInserter.insertObjects(Arrays.asList(product));
    }

}
