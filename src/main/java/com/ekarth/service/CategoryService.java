package com.ekarth.service;

import com.ekarth.dao.DatabaseSelector;
import com.ekarth.model.Category;
import com.ekarth.model.Product;
import com.ekarth.model.ProductCategory;
import com.ekarth.security.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    Encryptor encryptor;

    public List<Category> getAllCategories(int custId) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        Field companyIdField = Category.class.getDeclaredField("companyId");
        List<Field> fields = new ArrayList<>();
        fields.add(companyIdField);

        List<Object> values = new ArrayList<>();
        values.add(custId);

        DatabaseSelector<Category> databaseSelector = new DatabaseSelector<>(Category.class, encryptor, fields, values);
        List<Category> categories = databaseSelector.selectObjects();
        return categories;

    }

    public List<Product> getAllProducts(int categoryId) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {

        Field categoryIdField = ProductCategory.class.getDeclaredField("categoryId");
        List<Field> fields = new ArrayList<>();
        fields.add(categoryIdField);

        List<Object> values = new ArrayList<>();
        values.add(categoryId);

        DatabaseSelector<ProductCategory> databaseSelector = new DatabaseSelector<>(ProductCategory.class, encryptor, fields, values);
        List<ProductCategory> productCategories = databaseSelector.selectObjects();
        List<Integer> productIds = productCategories.stream()
                .map(productCategory -> productCategory.getProductId()).collect(Collectors.toList());

        List<Product> products = new ArrayList<>();
        for (Integer productId : productIds) {
            Product product = getProduct(productId);
            if (product != null) {
                products.add(product);
            }
        }

        return products;
    }

    private Product getProduct(Integer productId) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        Field productIdField = Product.class.getDeclaredField("productId");
        List<Field> fields = new ArrayList<>();
        fields.add(productIdField);

        List<Object> values = new ArrayList<>();
        values.add(productId);

        DatabaseSelector<Product> databaseSelector = new DatabaseSelector<>(Product.class, encryptor, fields, values);
        List<Product> products = databaseSelector.selectObjects();

        if (products != null && products.size() == 1) {
            return products.get(0);
        } else {
            return null;
        }

    }
}
