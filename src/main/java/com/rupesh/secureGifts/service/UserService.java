package com.rupesh.secureGifts.service;
import com.rupesh.secureGifts.dao.UserDao;
import com.rupesh.secureGifts.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by rupesh on 24/05/17.
 */
@Service
public class UserService {
    private String SALT = "14525rftedhuye87";
    @Autowired UserDao userDao;

    public void persistUser(User user) {
        user.setPasswordDigest( encryptPassword(user.getPasswordDigest()) );
        userDao.insertUser(user);
    }

    public String encryptPassword(String password) {
        MessageDigest messageDigest;
        StringBuffer sb = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] passwordWithSaltBytes = (SALT + password).getBytes();
            messageDigest.reset();
            byte[] digestedBytes = messageDigest.digest( passwordWithSaltBytes );
            for(int i=0;i<digestedBytes.length;i++){
                sb.append(Integer.toHexString(0xff & digestedBytes[i]));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public User fetchUserByCreds(String email, String password) {
        return userDao.fetchUserByCreds(email, password);
    }

    public User updatePassword(String email, String encryptedNewPassword) {
        return userDao.updatePassword(email, encryptedNewPassword);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
