package com.ekarth.service;

import com.ekarth.dao.DatabaseInserter;
import com.ekarth.dao.DatabaseSelector;
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

/**
 * Created by rupesh on 01/09/17.
 */


@Service
public class LoginService {

    @Autowired
    Encryptor encryptor;

    public String signUp(Customer customer) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //TODO: make these columns unique , these checks wouldnot be needed
//        validateNonExistance(Customer.class.getDeclaredField("CompanyName"), customer.getCompanyName());
//        validateNonExistance(Customer.class.getDeclaredField("EmailId"), customer.getEmailId());

        DatabaseInserter<Customer> customerDatabaseInserter = new DatabaseInserter<>(Customer.class, encryptor);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customerDatabaseInserter.insertObjects(customers);

        return "success";
    }

    private void validateNonExistance(Field field, String value) {

    }


    public Customer login(String companyName, String password) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        //TODO: Update to use selector
        Field companyNameField = Customer.class.getDeclaredField("companyName");
//        Field passwordDisgestField = Customer.class.getDeclaredField("passwordDigest");
        List<Field> fields = new ArrayList<>();
        fields.add(companyNameField);
//        fields.add(passwordDisgestField);

        List<Object> values = new ArrayList<>();
        values.add(companyName);
//        values.add(password);

        DatabaseSelector<Customer> databaseSelector= new DatabaseSelector<>(Customer.class, encryptor, fields,values );
        List<Customer> customers = databaseSelector.selectObjects();

        if(customers.size()>1){
            System.out.println("Something bad has happened with our database");
        }

        if(customers.isEmpty() || !encryptor.getbCryptPasswordEncoder().matches(password, customers.get(0).getPasswordDigest())){
            System.out.println("no customer like that dude!");
        }

        return customers.get(0);

    }
}
