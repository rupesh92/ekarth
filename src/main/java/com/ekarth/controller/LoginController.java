package com.ekarth.controller;

import com.ekarth.model.Customer;
import com.ekarth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rupesh on 01/09/17.
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;
    @RequestMapping(value = "/signup", method= RequestMethod.POST)
    @ResponseBody
    public String signup(@RequestBody Customer customer) {
        return loginService.signUp(customer);
    }

}
