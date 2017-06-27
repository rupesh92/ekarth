package com.rupesh.secureGifts.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by rupesh on 24/05/17.
 */
public class JdbcManager {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn =  DriverManager.getConnection("jdbc:mysql://localhost:8080/belsio?" + "user=root&password=spring");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
