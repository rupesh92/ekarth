package com.ekarth.dao;

/**
 * Created by shiwang on 5/23/17.
 */
import com.ekarth.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDAO implements CustomerDAO
{
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Customer customer){

        String sql = "INSERT INTO CUSTOMER " +
                "(CUST_ID, NAME, COMPANY_NAME, EMAIL_ID, CONTACT_NUMBER) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        /*
        CREATE TABLE CUSTOMER(
                CUST_ID   INT NOT NULL AUTO_INCREMENT,
                NAME VARCHAR(40) NOT NULL,
                COMPANY_NAME VARCHAR(40) NOT NULL,
                EMAIL_ID VARCHAR(40) NOT NULL,
                CONTACT_NUMBER VARCHAR(40) NOT NULL,
                PRIMARY KEY (CUST_ID)
        );
        */
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getCustId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getCompanyName());
            ps.setString(4, customer.getEmailId());
            ps.setString(5, customer.getContactNumber());
            ps.executeUpdate();
            ps.close();

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

    public Customer findByCustomerId(int custId){

        String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, custId);
            Customer customer = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("CUST_ID"),
                        rs.getString("NAME"),
                        rs.getString("COMPANY_NAME"),
                        rs.getString("EMAIL_ID"),
                        rs.getString("CONTACT_NUMBER")
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

    @Override
    public String signUp(Customer customer) {
        return null;
    }
}

