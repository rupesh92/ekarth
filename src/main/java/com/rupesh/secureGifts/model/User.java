package com.rupesh.secureGifts.model;

/**
 * Created by rupesh on 23/05/17.
 */
public class User {
    private String email;
    private String firstName;
    private String secondName;
    private String passwordDigest;

    public User(String email, String first_name, String second_name, String password_digest) {
        this.email = email;
        this.firstName = first_name;
        this.secondName = second_name;
        this.passwordDigest = password_digest;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String second_name) {
        this.secondName = second_name;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String password_digest) {
        this.passwordDigest = password_digest;
    }

}
