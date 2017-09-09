package com.ekarth.service;

import com.ekarth.dao.CustomerDAO;
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
    CustomerDAO customerDAO;
    @Autowired
    Encryptor encryptor;

    public String signUp(Customer customer) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        //TODO: make these columns unique , these checks wouldnot be needed
        validateCompanyNameNonExistence(customer.getCompanyName());
        validateEmailNonExistence(customer.getEmailId());

        DatabaseInserter<Customer> customerDatabaseInserter = new DatabaseInserter<>(Customer.class, encryptor);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customerDatabaseInserter.insertObjects(customers);

        return "success";
    }


    private void validateEmailNonExistence(String emailId) {
        try {
            if (customerDAO.getCustomerFromEmailId(emailId) != null) {
                throw new RuntimeException("Email ID " + emailId + " already exists");
            }
        } catch (Exception e) {
            return;
        }
    }

    private void validateCompanyNameNonExistence(String companyName) {
        try {
            if (customerDAO.getCustomerFromCompanyName(companyName) != null) {
                throw new RuntimeException("Company Name " + companyName + " already exists");
            }
        } catch (Exception e) {
            return;
        }
    }

    public Customer login(String companyName, String password) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        //TODO: Update to use selector
        Field companyNameField = Customer.class.getField("companyName");
        Field passwordDisgestField = Customer.class.getField("passwordDisgest");
        List<Field> fields = new ArrayList<>();
        fields.add(companyNameField);
        fields.add(passwordDisgestField);

        List<Object> values = new ArrayList<>();
        values.add(companyName);
        values.add(password);

        DatabaseSelector<Customer> databaseSelector= new DatabaseSelector<>(Customer.class, encryptor, fields,values );
        List<Customer> customers = databaseSelector.selectObjects();
        if(customers.isEmpty()){
            System.out.println("no customer like that dude!");
        }
        if(customers.size()>1){
            System.out.println("Something bad has happened with our database");
        }
        return customers.get(0);

    }
}
