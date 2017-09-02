package com.ekarth.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Category {
    int categoryId;
    String name;
    int companyId;

}
