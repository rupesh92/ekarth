package com.mkyong.helloworld.dao.impl;

import com.mkyong.helloworld.connection.JdbcManager;
import com.mkyong.helloworld.dao.CustomerDAO;
import com.mkyong.helloworld.model.Customer;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by rupesh on 23/05/17.
 */

public class JdbcCustomerDAO implements CustomerDAO
{
    private DataSource dataSource;
//    public void setDataSource(DataSource dataSource) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//        this.dataSource = dataSource;
//    }

    public void insert(Customer customer){

        String sql = "INSERT INTO CUSTOMER " +
                "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
        Connection conn = JdbcManager.getConnection();;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getCustId());
            ps.setString(2, customer.getName());
            ps.setInt(3, customer.getAge());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public Customer findByCustomerId(int custId){

        String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";

        Connection conn = JdbcManager.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, custId);
            Customer customer = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("CUST_ID"),
                        rs.getString("NAME"),
                        rs.getInt("Age")
                );
            }
            rs.close();
            ps.close();
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}