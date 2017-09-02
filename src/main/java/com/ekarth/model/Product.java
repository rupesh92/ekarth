package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by shiwang on 5/21/17.
 */

@Data
@AllArgsConstructor
public class Product {
    int productId;
    String name;
    int qty;
    int price;
    String image;
}
