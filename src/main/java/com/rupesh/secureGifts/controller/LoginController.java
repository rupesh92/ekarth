package com.rupesh.secureGifts.controller;

import com.rupesh.secureGifts.model.User;
import com.rupesh.secureGifts.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rupesh on 24/05/17.
 */

@Controller
public class LoginController {
    @Autowired UserService userService;
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    //Logger logger = Logger.getLogger(LoginController.class.getName());
    @RequestMapping(value = "/api/v1/signup", method = RequestMethod.POST)
    @ResponseBody
    public User signup(User user) {
        user.setPasswordDigest(userService.encryptPassword(user.getPasswordDigest()));
        System.out.println("dgjdwe");
        logger.info("updated password is" + user.getPasswordDigest());
        userService.persistUser(user);
        return user;
    }

    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)
    @ResponseBody
    public User login(@RequestParam("email")  String email,
                      @RequestParam("password")  String password) {
        String encryptedPassword = userService.encryptPassword(password);
        logger.info("User trying to login with email " + email + " and password " + encryptedPassword);
        return userService.fetchUserByCreds(email, encryptedPassword);
    }

    @RequestMapping(value = "/api/v1/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam("email") String email,
                      @RequestParam("password") String password) {
        return email + " " + password;
    }

    @RequestMapping(value = "/api/v1/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public User login(@RequestParam("email")  String email,
                      @RequestParam("oldPassword")  String oldPassword,
                      @RequestParam("newPassword")  String newPassword) {
        String encryptedOldPassword = userService.encryptPassword(oldPassword);
        String encryptedNewPassword = userService.encryptPassword(newPassword);
        if (userService.fetchUserByCreds(email, encryptedOldPassword) != null) {
            return userService.updatePassword(email, encryptedNewPassword);
        }
        throw new RuntimeException("No such user exists");

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<User> login() {
        logger.info("Application started");
        return userService.getAllUsers();

    }
}
