package com.ekarth.controller;

import com.ekarth.dao.CustomerDAO;
import com.ekarth.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
public class WelcomeController {

	private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	private final HelloWorldService helloWorldService;

	@Autowired
    CustomerDAO userDAO;

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
//
//	@RequestMapping(value = "/signup", method= RequestMethod.GET)
//	@ResponseBody
//	public String signup() throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
//
//		//TODO: Show a form on this link. and from the input - on form submit update the table (React)
//		Customer user = new Customer(1,"sachinta","Levis", "shiwangishah93@gmail.com",
//				"8348804751", "testPass");
//
//		DatabaseInserter<Customer> inserter = new DatabaseInserter<Customer>(
//
//				Customer.class);
//		List<Customer> list = new ArrayList<>();
//		list.add(user);
//		inserter.insertObjects(list);
//		return "success";
//
//	}

}
