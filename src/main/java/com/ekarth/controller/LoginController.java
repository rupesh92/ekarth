package com.ekarth.controller;

import com.ekarth.model.Customer;
import com.ekarth.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by rupesh on 01/09/17.
 *
 * Sample input:
 {
 "name": "Shiwangi",
 "companyName": "Ekarth",
 "contactNumber":"834857632",
 "emailId": "shiwangishah93@gmail.com",
 "passwordDigest": "godbachao"
 }
 */
@RestController
@RequestMapping(value = "api/v1")
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Customer> signup(@RequestBody Customer customer) {
        try {
            loginService.signUp(customer);
        } catch (Exception e) {
            logger.error("We could not add your details due to ", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public Customer login(@RequestParam String companyName, @RequestParam String password) {
        Customer customer = null;
        System.out.println("Company name is" + companyName + " and password is " + password);
        try {
            customer = loginService.login(companyName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    //Add support for forgot password too


}
