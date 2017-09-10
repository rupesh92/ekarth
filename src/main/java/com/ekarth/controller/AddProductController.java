package com.ekarth.controller;

import com.ekarth.model.Category;
import com.ekarth.model.Product;
import com.ekarth.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1")
public class AddProductController {
    private static final Logger logger = LoggerFactory.getLogger(AddProductController.class);

    @Autowired
    ProductService productService;

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestBody Category category) {
        try {
            productService.addProductDetails(product,category);
        } catch (Exception e) {
            logger.error("We could not add your details due to ", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }
}
