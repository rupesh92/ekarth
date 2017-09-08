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
    @RequestMapping(value = "signup", method= RequestMethod.POST)
    public ResponseEntity<Customer> signup(@RequestBody Customer customer) {
        loginService.signUp(customer);

        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login", method= RequestMethod.POST)
    public Customer login(@RequestParam String companyName, @RequestParam String password) {
        Customer customer = loginService.login(companyName, password);
        System.out.println("Contact number is " + customer.getContactNumber());
        return customer;
    }

    //Add support for forgot password too



}
