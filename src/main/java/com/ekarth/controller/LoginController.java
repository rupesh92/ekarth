package com.ekarth.controller;

import com.ekarth.model.Customer;
import com.ekarth.service.LoginService;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseBody
    public String signup(@RequestParam String name,
                         @RequestParam String companyName,
                         @RequestParam String emailId,
                         @RequestParam String password,
                         @RequestParam String contactNumber) {
        return loginService.signUp(name, companyName, contactNumber, emailId, password);
    }

    @RequestMapping(value = "login", method= RequestMethod.POST)
    @ResponseBody
    public Customer login(@RequestParam String companyName, @RequestParam String password) {
        Customer customer = loginService.login(companyName, password);
        System.out.println("Contact number is " + customer.getContactNumber());
        return customer;
    }

    //Add support for forgot password too



}
