package com.ekarth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
  
/**
 *
 * Class that inserts a list of <T>s into the corresponding database-table.
 *
 * @author Tino for http://www.java-blog.com
 *
 * @param <T>
 */
public class DatabaseInserter<T> extends AbstractDatabaseHandler<T> {
  
    public DatabaseInserter(Class<T> type) {
        super(type);
    }
    /**
     * Contains the settings to create a connection to the database like
     * <p>
     * host/port/database/user/password
     */
    @Autowired
    private DriverManagerDataSource databaseConnecter;

    @Override
    protected String createQuery() {
  
        StringBuilder sb = new StringBuilder();
  
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append("(");
        sb.append(super.getColumns(false));
        sb.append(")");
        sb.append(" VALUES (");
        sb.append(super.getColumns(true));
        sb.append(")");
  
        return sb.toString();
    }
  
    /**
     * Inserts a list of <T>s into the corresponding database-table
     *
     * @param list
     *            List of <T>s that should be inserted into the corresponding
     *            database-table
     *
     * @throws SQLException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public void insertObjects(List<T> list) throws SQLException,
            SecurityException, IllegalArgumentException,
            InstantiationException, IllegalAccessException,
            IntrospectionException, InvocationTargetException {
  
        Connection connection = null;
        PreparedStatement preparedStatement = null;
  
        try {
            connection = databaseConnecter.getConnection();
            preparedStatement = connection.prepareStatement(query);
  
            for (T instance : list) {
                int i = 0;
  
                for (Field field : type.getDeclaredFields()) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                            field.getName(), type);
 
                    Method method = propertyDescriptor
                            .getReadMethod();
  
                    Object value = method.invoke(instance);
  
                    preparedStatement.setObject(++i, value);
                }
  
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
  
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }
}

