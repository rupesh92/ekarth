package com.ekarth.controller;

import com.ekarth.model.Category;
import com.ekarth.model.Customer;
import com.ekarth.pojos.LoginResponse;
import com.ekarth.service.CategoryService;
import com.ekarth.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Created by rupesh on 01/09/17.
 * <p>
 * Sample input:
 * {
 * "name": "Shiwangi",
 * "companyName": "Ekarth",
 * "contactNumber":"834857632",
 * "emailId": "shiwangishah93@gmail.com",
 * "passwordDigest": "godbachao"
 * }
 */
@RestController
@RequestMapping(value = "api/v1")
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;
    @Autowired
    CategoryService categoryService;

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
    public ResponseEntity<LoginResponse> login(@RequestParam String companyName, @RequestParam String password) {
        Customer customer = null;
        List<Category> categoryList = null;
        System.out.println("Company name is" + companyName + " and password is " + password);
        try {
            Optional<Customer> customerOptional = loginService.login(companyName, password);
            if (customerOptional.isPresent()) {
                customer = customerOptional.get();
                categoryList = categoryService.getAllCategories(customer.getCustId());
            } else {
                return new ResponseEntity<LoginResponse>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<LoginResponse>(new LoginResponse(customer, categoryList), HttpStatus.OK);
    }

    //Add support for forgot password too


}
