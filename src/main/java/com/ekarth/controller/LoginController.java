package com.ekarth.controller;

import com.ekarth.model.Category;
import com.ekarth.model.Seller;
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
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Seller> signup(@RequestBody Seller seller) {
        try {
            loginService.signUp(seller);
        } catch (Exception e) {
            logger.error("We could not add your details due to ", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<Seller>(seller, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<LoginResponse> login(@RequestParam String companyName, @RequestParam String password) {
        Seller seller = null;
        List<Category> categoryList = null;
        System.out.println("Company name is" + companyName + " and password is " + password);
        try {
            Optional<Seller> sellerOptional = loginService.login(companyName, password);
            if (sellerOptional.isPresent()) {
                seller = sellerOptional.get();
                categoryList = categoryService.getAllCategories(seller.getCustId());
            } else {
                return new ResponseEntity<LoginResponse>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<LoginResponse>(new LoginResponse(seller, categoryList), HttpStatus.OK);
    }

    //Add support for forgot password too


}
