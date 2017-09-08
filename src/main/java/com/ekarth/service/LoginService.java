package com.ekarth.service;

import com.ekarth.dao.CustomerDAO;
import com.ekarth.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Created by rupesh on 01/09/17.
 */


@Service
public class LoginService {

    @Autowired
    CustomerDAO customerDAO;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String signUp(Customer customer) {
        validateCompanyNameNonExistence(customer.getCompanyName());
        validateEmailNonExistence(customer.getEmailId());
        customer.setPasswordDigest(getEncryptedPassword(customer.getPasswordDigest()));
        customerDAO.insert(customer);
        return "success";
    }

    private String getEncryptedPassword(String password) {
        String passwordDigest = bCryptPasswordEncoder.encode(password);
        System.out.println("Password: " + password + " Encoded Pass: " + passwordDigest);
        return passwordDigest;
    }

    private void validateEmailNonExistence(String emailId) {
        try {
            if (customerDAO.getCustomerFromEmailId(emailId) != null ) {
                throw new RuntimeException("Email ID " + emailId + " already exists");
            }
        } catch (Exception e) {
            return;
        }
    }

    private void validateCompanyNameNonExistence(String companyName) {
        try {
            if (customerDAO.getCustomerFromCompanyName(companyName) != null ) {
                throw new RuntimeException("Company Name " + companyName + " already exists");
            }
        } catch (Exception e) {
            return;
        }
    }

    public Customer login(String companyName, String password) {
        String passwordDigest = getEncryptedPassword(password);
        Customer customer = customerDAO.getCustomerFromCompanyName(companyName);

        if (customer == null || !bCryptPasswordEncoder.matches(password, customer.getPasswordDigest())) {
            throw new RuntimeException("Incorrect Company Name or Password");
        }
        return customer;

    }
}
