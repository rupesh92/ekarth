package com.ekarth.service;

import com.ekarth.dao.DatabaseSelector;
import com.ekarth.model.Category;
import com.ekarth.model.Customer;
import com.ekarth.security.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

        DatabaseSelector<Category> databaseSelector= new DatabaseSelector<>(Category.class, encryptor, fields,values );
        List<Category> categories = databaseSelector.selectObjects();
        return categories;

    }
}
