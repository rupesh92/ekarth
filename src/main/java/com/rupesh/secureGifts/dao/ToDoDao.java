package com.rupesh.secureGifts.dao;

import com.rupesh.secureGifts.connection.JdbcManager;
import com.rupesh.secureGifts.dbModel.DbToDo;
import com.rupesh.secureGifts.helper.StatusEnum;
import com.rupesh.secureGifts.model.ToDo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rupesh on 29/05/17.
 */
public class ToDoDao {

    private static String FETCH_ALL_TODO = "SELECT * FROM " + DbToDo.TABLE_NAME;
    private static String INSERT_TODO = "INSERT INTO " + DbToDo.TABLE_NAME
            + " ("
            + DbToDo.CUSTOMER_EMAIL + " , "
            + DbToDo.TEXT + " , "
            + DbToDo.STATUS + ") "
            +  " VALUES (?, ?, ?)";

    private static String UPDATE_STATUS_FROM_ID = "UPDATE " + DbToDo.TABLE_NAME
            + " SET " + DbToDo.STATUS
            + " ? WHERE "
            + DbToDo.ID + " = ?";

    private static String UPDATE_STATUS_FROM_CUSTOMER_EMAIL = "UPDATE " + DbToDo.TABLE_NAME
            + " SET " + DbToDo.STATUS
            + " ? WHERE "
            + DbToDo.CUSTOMER_EMAIL + " = ?";

    private static String FETCH_TODO_FROM_CUSTOMER_EMAIL = "SELECT * FROM " + DbToDo.TABLE_NAME
            + " WHERE "
            + DbToDo.CUSTOMER_EMAIL + " = ?";

    public List<ToDo> getAllTodo() {
        Connection conn = JdbcManager.getConnection();
        List<ToDo> allToDos = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(FETCH_ALL_TODO)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ToDo todo =  new ToDo(rs.getString(DbToDo.ID), rs.getString(DbToDo.CUSTOMER_EMAIL),
                        rs.getString(DbToDo.TEXT), StatusEnum.valueOf(rs.getString(DbToDo.STATUS)));
                allToDos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allToDos;
    }

    public ToDo fetchTodo(String customerEmail) {
        Connection conn = JdbcManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(FETCH_TODO_FROM_CUSTOMER_EMAIL)) {
            ps.setString(1, customerEmail);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new ToDo(rs.getString(DbToDo.ID), rs.getString(DbToDo.CUSTOMER_EMAIL),
                        rs.getString(DbToDo.TEXT), StatusEnum.valueOf(rs.getString(DbToDo.STATUS)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ToDo insertTodo(String customerEmail, String text, StatusEnum status) {
        Connection conn = JdbcManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(INSERT_TODO)) {
            ps.setString(1, customerEmail);
            ps.setString(2, text);
            ps.setString(3, status.name());
            ps.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetchTodo(customerEmail);

    }

    public ToDo updateStatusFromCustomerEmail(String customerEmail, StatusEnum status) {
        Connection conn = JdbcManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS_FROM_CUSTOMER_EMAIL)) {
            ps.setString(1, status.name());
            ps.setString(2, customerEmail);
            ps.executeUpdate();
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fetchTodo(customerEmail);
    }


}
