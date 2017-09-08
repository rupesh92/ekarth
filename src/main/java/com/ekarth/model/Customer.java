package com.ekarth.model;


import java.io.Serializable;
import com.ekarth.model.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String passwordDigest;

}
