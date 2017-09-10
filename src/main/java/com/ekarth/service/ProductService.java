package com.ekarth.service;

import com.ekarth.dao.DatabaseInserter;
import com.ekarth.model.Category;
import com.ekarth.model.Product;
import com.ekarth.model.ProductCategory;
import com.ekarth.security.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;

@Service
public class ProductService {
    @Autowired
    Encryptor encryptor;

    public void addProductDetails(Product product, Category category) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        DatabaseInserter<Product> productDatabaseInserter = new DatabaseInserter<>(Product.class, encryptor);
        productDatabaseInserter.insertObjects(Arrays.asList(product));

        if(category.getCategoryId()>0){
            //category already exists
        }
        else{
            DatabaseInserter<Category> categoryDatabaseInserter = new DatabaseInserter<>(Category.class, encryptor);
            categoryDatabaseInserter.insertObjects(Arrays.asList(category));
        }

        ProductCategory productCategory = new ProductCategory(category.getCategoryId(), product.getProductId());
        DatabaseInserter<ProductCategory> productCategoryDatabaseInserter = new DatabaseInserter<>(ProductCategory.class, encryptor);
        productCategoryDatabaseInserter.insertObjects(Arrays.asList(productCategory));


    }

}
