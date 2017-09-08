package com.ekarth.service;

import com.ekarth.dao.CustomerDAO;
import com.ekarth.dao.DatabaseInserter;
import com.ekarth.model.Customer;
import com.ekarth.security.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
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

    public String signUp(Customer customer) {
        validateCompanyNameNonExistence(customer.getCompanyName());
        validateEmailNonExistence(customer.getEmailId());
        customer.setPasswordDigest(encryptor.getEncryptedPassword(customer.getPasswordDigest()));
//        DatabaseInserter<Customer> customerDatabaseInserter = new DatabaseInserter<>(Customer.class);
//        List<Customer> customers = new ArrayList<>();
//        customers.add(customer);
//        try {
//            customerDatabaseInserter.insertObjects(customers);
//        } catch (SQLException | InstantiationException | IllegalAccessException | IntrospectionException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
        customerDAO.insert(customer);
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

    public Customer login(String companyName, String password) {
        Customer customer = customerDAO.getCustomerFromCompanyName(companyName);

        if (customer == null || !encryptor.isMatch(password, customer.getPasswordDigest())) {
            throw new RuntimeException("Incorrect Company Name or Password");
        }
        return customer;

    }
}
