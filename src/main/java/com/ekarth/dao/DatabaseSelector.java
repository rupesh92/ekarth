package com.ekarth.dao;


import com.ekarth.model.annotations.Encrypted;
import com.ekarth.security.Encryptor;

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
 * Class that creates a list of <T>s filled with values from the corresponding
 * database-table.
 *
 * @param <T>
 * @author shiwang
 */

public class DatabaseSelector<T> extends AbstractDatabaseHandler<T> {
    List<Field> fields;
    List<Object> values;

    Encryptor encryptor;

    public DatabaseSelector(Class<T> type, Encryptor encryptor, List<Field> fields, List<Object> values) {
        super(type);
        this.fields = fields;
        this.values = values;
        this.encryptor = encryptor;
        this.query = createQuery();
    }



    @Override
    public String createQuery() {


        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        sb.append(super.getColumns(false, true));
        sb.append(" FROM ");

		/* We assume the table-name exactly matches the simpleName of T */
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        int valueId = 0;
        for (Field f :
                fields) {
            if (valueId > 0)
                sb.append(" AND ");
            sb.append(f.getName() + "=");
            String value = (String) (values.get(valueId));
            if(f.isAnnotationPresent(Encrypted.class)){
                value = encryptor.getEncryptedObject(value);
            }
            if(f.getType().isAssignableFrom(String.class)) value = appendWithQuotes(value);
            sb.append(value);
            valueId++;

        }
        System.out.println("Formed sql query is " + sb.toString());
        return sb.toString();
    }

    private String appendWithQuotes(String value) {
        return "'" + value + "'";
    }


    /**
     * Creates a list of <T>s filled with values from the corresponding
     * database-table
     *
     * @return List of <T>s filled with values from the corresponding
     * database-table
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
                } catch (SQLException e) {
                }
            }
        }
    }

    /**
     * Creates a list of <T>s filled with values from the provided ResultSet
     *
     * @param resultSet ResultSet that contains the result of the
     *                  database-select-query
     * @return List of <T>s filled with values from the provided ResultSet
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


