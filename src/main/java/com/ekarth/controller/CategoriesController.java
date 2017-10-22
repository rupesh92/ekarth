package com.ekarth.controller;

import com.ekarth.model.Category;
import com.ekarth.model.Product;
import com.ekarth.pojos.GetProductResponse;
import com.ekarth.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1")
public class CategoriesController {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "getCategories", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Category>> getCategories(@RequestParam int custId) {
        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = categoryService.getAllCategories(custId);
        } catch (Exception e) {
            logger.error("We could not add your details due to ", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
    }

    @RequestMapping(value = "getProducts", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<GetProductResponse> getProducts(@RequestParam int categoryId) {
        List<Product> productList = new ArrayList<>();
        try {
            productList = categoryService.getAllProducts(categoryId);
        } catch (Exception e) {
            logger.error("We could not add your details due to ", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<GetProductResponse>(new GetProductResponse(productList), HttpStatus.OK);
    }
}
