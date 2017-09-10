package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    List<String> image;
}
