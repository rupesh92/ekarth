package com.ekarth.controller;

import com.ekarth.model.Product;
import com.ekarth.model.ProductDetails;
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

/**
 *
 * Sample input:
 {
 "product": {
 "name": "Frayed Pants",
 "qty": 100,
 "price": 3000,
 "image": ["image1","image2"]
 },
 "category": {
 "name": "Bottom Wear",
 "companyId": 1
 },
 "propertyList" :[{
 "name": "Color",
 "propertyValues": ["Red","Blue"]
 }]
 }

 */

@RestController
@RequestMapping(value = "api/v1")
public class AddProductController {
    private static final Logger logger = LoggerFactory.getLogger(AddProductController.class);

    @Autowired
    ProductService productService;

    @RequestMapping(value = "addProduct", method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody ProductDetails productDetails) {
        try {
            productService.addProductDetails(productDetails.getProduct(), productDetails.getCategory(),
                    productDetails.getPropertyList());
        } catch (Exception e) {
            logger.error("We could not add your details due to ", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<Product>(productDetails.getProduct(), HttpStatus.CREATED);
    }
}
