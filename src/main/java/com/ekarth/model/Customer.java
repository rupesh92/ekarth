package com.ekarth.model;


import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.ekarth.model.annotations.Encrypted;
import com.ekarth.model.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by shiwang on 5/21/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer implements Serializable {
    @PrimaryKey
    int custId;
    String name;
    String companyName;
    String contactNumber;
    String emailId;
    @Encrypted
    String passwordDigest;

}
