package com.ekarth.controller;

import com.ekarth.model.Customer;
import com.ekarth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;


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
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)

    public Customer login(@RequestParam String companyName, @RequestParam String password) {
        Customer customer = null;
        try {
            customer = loginService.login(companyName, password);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return customer;
    }

    //Add support for forgot password too


}
