package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductProperty {
    int propertyId;
    int productId;
    String propertyValue;
}
