package com.ekarth.service;

import com.ekarth.dao.CustomerDAO;
import com.ekarth.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rupesh on 01/09/17.
 */



public class LoginService {

    @Autowired
    CustomerDAO customerDAO;

    public String signUp(Customer customer) {
        return customerDAO.signUp(customer);
    }
}
