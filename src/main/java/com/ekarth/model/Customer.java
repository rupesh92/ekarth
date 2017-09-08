package com.ekarth.model;


import java.io.Serializable;

/**
 * Created by shiwang on 5/21/17.
 */
public class Customer implements Serializable {
    int custId;
    String name;
    String companyName;
    String contactNumber;

    public Customer() {
    }

    String emailId;
    String passwordDigest;

    public Customer(int custId, String name, String companyName, String contactNumber, String emailId, String passwordDigest) {
        this.custId = custId;
        this.name = name;
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.emailId = emailId;
        this.passwordDigest = passwordDigest;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }
}
