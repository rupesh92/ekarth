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
import java.util.Optional;

/**
 * Created by rupesh on 01/09/17.
 */


@Service
public class LoginService {

    @Autowired
    Encryptor encryptor;

    public String signUp(Customer customer) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //TODO: make these columns unique , these checks wouldnot be needed
        validateNonExistance(Customer.class.getDeclaredField("companyName"), customer.getCompanyName());
        validateNonExistance(Customer.class.getDeclaredField("emailId"), customer.getEmailId());

        DatabaseInserter<Customer> customerDatabaseInserter = new DatabaseInserter<>(Customer.class, encryptor);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customerDatabaseInserter.insertObjects(customers);

        return "success";
    }

    private void validateNonExistance(Field field, String value) {

    }


    public Optional<Customer> login(String companyName, String password) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        Field companyNameField = Customer.class.getDeclaredField("companyName");
        List<Field> fields = new ArrayList<>();
        fields.add(companyNameField);

        List<Object> values = new ArrayList<>();
        values.add(companyName);

        DatabaseSelector<Customer> databaseSelector= new DatabaseSelector<>(Customer.class, encryptor, fields,values );
        List<Customer> customers = databaseSelector.selectObjects();

        if(customers.size()>1){
            System.out.println("Something bad has happened with our database, " +
                    "there are more than 1 companies with this name");
            return Optional.empty();
        }

        if(customers.isEmpty() || !encryptor.getbCryptPasswordEncoder().matches(password, customers.get(0).getPasswordDigest())){
            System.out.println("No customer like that dude!");
            return Optional.empty();
        }

        return Optional.of(customers.get(0));

    }
}
