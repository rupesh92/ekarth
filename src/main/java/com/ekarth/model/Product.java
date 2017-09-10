package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by shiwang on 5/21/17.
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    int productId;
    String name;
    int qty;
    int price;
    String image;
}
