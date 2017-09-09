package com.ekarth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Encryptor {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Encryptor(){
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String getEncryptedObject(Object password) {
        String passwordDigest = bCryptPasswordEncoder.encode((CharSequence) password);
        System.out.println("Password: " + password + " Encoded Pass: " + passwordDigest);
        return passwordDigest;
    }

}
