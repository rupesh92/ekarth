package com.ekarth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryptor {
    @Autowired
    static BCryptPasswordEncoder bCryptPasswordEncoder;


    public static String getEncryptedPassword(String password) {
        String passwordDigest = bCryptPasswordEncoder.encode(password);
        System.out.println("Password: " + password + " Encoded Pass: " + passwordDigest);
        return passwordDigest;
    }

    public static boolean isMatch(String password, String passwordDigest){
        return bCryptPasswordEncoder.matches(password, passwordDigest);
    }
}
