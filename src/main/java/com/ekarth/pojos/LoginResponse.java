package com.ekarth.pojos;

import com.ekarth.model.Category;
import com.ekarth.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    Seller seller;
    List<Category> categories;
}
