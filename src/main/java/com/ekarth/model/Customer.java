package com.ekarth.model;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by shiwang on 5/21/17.
 */
@Data
@AllArgsConstructor
public class Customer {

    int custId;
    String name;
    String companyName;
    String contactNumber;
    String emailId;

}
