package com.ekarth.model;

import com.ekarth.model.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by shiwang on 5/21/17.
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @PrimaryKey
    int productId;
    String name;
    int qty;
    int price;
    List<String> image;
}
