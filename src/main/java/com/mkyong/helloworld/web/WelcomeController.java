package com.mkyong.helloworld.web;

import java.util.Map;

import com.mkyong.helloworld.dao.impl.JdbcCustomerDAO;
import com.mkyong.helloworld.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.mkyong.helloworld.service.HelloWorldService;

@Controller
public class WelcomeController {

	private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	private final HelloWorldService helloWorldService;

	JdbcCustomerDAO jdbcCustomerDAO = new JdbcCustomerDAO();
	@Autowired
	public WelcomeController(HelloWorldService helloWorldService) {
		this.helloWorldService = helloWorldService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Map<String, Object> model) {

		logger.debug("index() is executed!");

		model.put("title", helloWorldService.getTitle(""));
		model.put("msg", helloWorldService.getDesc());
		
		return "index";
	}

	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		logger.debug("hello() is executed - $name {}", name);

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		
		model.addObject("title", helloWorldService.getTitle(name));
		model.addObject("msg", helloWorldService.getDesc());
		
		return model;

	}

	@RequestMapping(value = "/api/testing", method= RequestMethod.GET)
	@ResponseBody
	public Customer hellojava() {
		Customer customer = new Customer(5, "Shiwangi", 24);
		jdbcCustomerDAO.insert(customer);
		return customer;

	}

	@RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)
	@ResponseBody
    public String login(@RequestParam("username") String username,
						@RequestParam("password") String password) {

		if (username != null) {
			return username + password;
		}
		return "sff";
    }

}