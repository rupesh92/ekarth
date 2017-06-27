package com.rupesh.secureGifts.dao;

import com.rupesh.secureGifts.connection.JdbcManager;
import com.rupesh.secureGifts.dbModel.DbUser;
import com.rupesh.secureGifts.model.User;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rupesh on 24/05/17.
 */

@Service
public class UserDao {

    private static String INSERT_USER = "INSERT INTO " + DbUser.TABLE_NAME
            + " ("
            + DbUser.EMAIL + " , "
            + DbUser.FIRST_NAME + " , "
            + DbUser.SECOND_NAME + " , "
            + DbUser.PASSWORD_DIGEST + ") "
            +  " VALUES (?, ?, ?, ?)";

    private static String FETCH_USER = "SELECT * FROM "
            + DbUser.TABLE_NAME + " WHERE "
            + DbUser.EMAIL + " = ? AND "
            + DbUser.PASSWORD_DIGEST + " = ?";

    private static String UPDATE_PASSWORD = "UPDATE " + DbUser.TABLE_NAME + " SET "
            + DbUser.PASSWORD_DIGEST + " = ? WHERE "
            + DbUser.EMAIL + " = ?";
    private static String FETCH_ALL_USERS = "SELECT * FROM " + DbUser.TABLE_NAME;


    public void insertUser(User user) {
        Connection conn = JdbcManager.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_USER);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getSecondName());
            ps.setString(4, user.getPasswordDigest());
            ps.executeUpdate();
            ps.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User fetchUserByCreds(String email, String password_digest) {
        Connection conn = JdbcManager.getConnection();
        User user = null;
        try {
            PreparedStatement ps = conn.prepareStatement(FETCH_USER);
            ps.setString(1, email);
            ps.setString(2, password_digest);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user =  new User(rs.getString(DbUser.EMAIL), rs.getString(DbUser.FIRST_NAME),
                        rs.getString(DbUser.SECOND_NAME), rs.getString(DbUser.PASSWORD_DIGEST));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User updatePassword(String email, String encryptedNewPassword) {
        Connection conn = JdbcManager.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(UPDATE_PASSWORD);
            ps.setString(1, email);
            ps.setString(2, encryptedNewPassword);
            ps.executeUpdate();
            ps.close();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetchUserByCreds(email, encryptedNewPassword);
    }

    public List<User> getAllUsers() {
        Connection conn = JdbcManager.getConnection();
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(FETCH_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User user =  new User(rs.getString(DbUser.EMAIL), rs.getString(DbUser.FIRST_NAME),
                        rs.getString(DbUser.SECOND_NAME), rs.getString(DbUser.PASSWORD_DIGEST));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}
