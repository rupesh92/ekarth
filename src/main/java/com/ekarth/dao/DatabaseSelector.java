package com.ekarth.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class that creates a list of <T>s filled with values from the corresponding
 * database-table.
 *
 * @author Tino for http://www.java-blog.com
 *
 * @param <T>
 */
public class DatabaseSelector<T> extends AbstractDatabaseHandler<T> {
    /**
     * Contains the settings to create a connection to the database like
     * <p>
     * host/port/database/user/password
     */
    @Autowired
    private DriverManagerDataSource databaseConnecter;

    public DatabaseSelector(Class<T> type) {
        super(type);
    }

    @Override
    protected String createQuery() {

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        sb.append(super.getColumns(false));
        sb.append(" FROM ");

		/* We assume the table-name exactly matches the simpleName of T */
        sb.append(type.getSimpleName());

        return sb.toString();
    }

    /**
     * Creates a list of <T>s filled with values from the corresponding
     * database-table
     *
     * @return List of <T>s filled with values from the corresponding
     *         database-table
     *
     * @throws SQLException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public List<T> selectObjects() throws SQLException,
            SecurityException, IllegalArgumentException,
            InstantiationException, IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnecter.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            return createObjects(resultSet);

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }

    /**
     *
     * Creates a list of <T>s filled with values from the provided ResultSet
     *
     * @param resultSet
     *            ResultSet that contains the result of the
     *            database-select-query
     *
     * @return List of <T>s filled with values from the provided ResultSet
     *
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    private List<T> createObjects(ResultSet resultSet)
            throws SecurityException, IllegalArgumentException,
            SQLException, InstantiationException,
            IllegalAccessException, IntrospectionException,
            InvocationTargetException {

        List<T> list = new ArrayList<T>();

        while (resultSet.next()) {

            T instance = type.newInstance();

            for (Field field : type.getDeclaredFields()) {

				/* We assume the table-column-names exactly match the variable-names of T */
                Object value = resultSet.getObject(field.getName());

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                        field.getName(), type);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(instance, value);
            }

            list.add(instance);
        }
        return list;
    }
}


