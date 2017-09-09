package com.ekarth.controller;

import com.ekarth.model.Customer;
import com.ekarth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by rupesh on 01/09/17.
 */
@RestController
@RequestMapping(value = "api/v1")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity<Customer> signup(@RequestBody Customer customer) {
        try {
            loginService.signUp(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)

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
