package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductProperty {
    int propertyId;
    int productId;
}
