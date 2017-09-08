package com.ekarth.dao;

/**
 * Created by shiwang on 5/23/17.
 */

import com.ekarth.model.Customer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class CustomerDAO {
    String INSERT_USER = "INSERT INTO CUSTOMER " +
            "(NAME,COMPANY_NAME,EMAIL_ID,CONTACT_NUMBER,PASSWORD_DIGEST) " +
            "VALUES (?,?,?,?,?)";

    String GET_USER_FROM_COMPANY_NAME = "SELECT * FROM CUSTOMER " +
            "WHERE COMPANY_NAME=?";

    String GET_USER_FROM_EMAIL_ID = "SELECT * FROM CUSTOMER " +
            "WHERE EMAIL_ID=?";

    String GET_USER_FROM_COMPANY_NAME_AND_PASSWORD = "SELECT * FROM CUSTOMER " +
            "WHERE COMPANY_NAME=? AND PASSWORD_DIGEST=?";
    private static final Logger logger = Logger.getLogger(CustomerDAO.class);
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Customer customer) {
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_USER);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getCompanyName());
            ps.setString(3, customer.getEmailId());
            ps.setString(4, customer.getContactNumber());
            ps.setString(5, customer.getPasswordDigest());
            logger.debug("Executed sql query is " + ps.toString());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public Customer getCustomerFromEmailId(String emailId) {
        Connection conn = null;
        Customer customer = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_USER_FROM_EMAIL_ID);
            ps.setString(1, emailId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                customer = getCustomerFromResultSet(resultSet);
            }
            ps.close();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return customer;
    }

    public Customer getCustomerFromCompanyName(String companyName) {
        Connection conn = null;
        Customer customer = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_USER_FROM_COMPANY_NAME);
            ps.setString(1, companyName);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                customer = getCustomerFromResultSet(resultSet);
            }
            ps.close();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return customer;
    }

    private Customer getCustomerFromResultSet(ResultSet resultSet) throws SQLException {
//        Customer customer = new Customer(
//                resultSet.getInt("CUST_ID"),
//                resultSet.getString("NAME"),
//                resultSet.getString("COMPANY_NAME"),
//                resultSet.getString("EMAIL_ID"),
//                resultSet.getString("CONTACT_NUMBER"),
//                resultSet.getString("PASSWORD_DIGEST"));
        return null;
    }
}

