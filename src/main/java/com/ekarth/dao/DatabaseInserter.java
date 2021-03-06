package com.ekarth.dao;


import com.ekarth.model.annotations.Encrypted;
import com.ekarth.model.annotations.PrimaryKey;
import com.ekarth.security.Encryptor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that inserts a list of <T>s into the corresponding database-table.
 *
 * @param <T>
 * @author shiwang
 */
public class DatabaseInserter<T> extends AbstractDatabaseHandler<T> {

    Encryptor encryptor;


    public DatabaseInserter(Class<T> type, Encryptor encryptor) {
        super(type);
        this.query = createQuery();
        this.encryptor = encryptor;
    }

    @Override
    protected String createQuery() {

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append("(");
        sb.append(super.getColumns(false, false));
        sb.append(")");
        sb.append(" VALUES (");
        sb.append(super.getColumns(true, false));
        sb.append(")");

        return sb.toString();
    }


    /**
     * Inserts a list of <T>s into the corresponding database-table
     *
     * @param list List of <T>s that should be inserted into the corresponding
     *             database-table
     * @throws SQLException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public List<T> insertObjects(List<T> list) throws SQLException,
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
                    if (field.isAnnotationPresent(PrimaryKey.class))
                        continue;

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                            field.getName(), type);

                    Method method = propertyDescriptor
                            .getReadMethod();

                    Object value = method.invoke(instance);
                    if( value instanceof List){
                        value = String.valueOf(value);
                    }
                    if (field.isAnnotationPresent(Encrypted.class)) {
                        value = encryptor.getEncryptedObject(value);
                    }

                    preparedStatement.setObject(++i, value);
                }

                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            //get the values inserted , with the primary key as we need the primary key to insert into other tables maybe.

            DatabaseSelector<T> databaseSelector;
            List<T> objectsInserted = new ArrayList<>();
            for (T instance : list) {
                List<Field> fieldsToCheckOn = new ArrayList<>();
                List<Object> valuesToCheckOn = new ArrayList<>();

                for (Field field : type.getDeclaredFields()) {
                    if (field.isAnnotationPresent(PrimaryKey.class))
                        continue;

                    fieldsToCheckOn.add(field);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
                            field.getName(), type);
                    Method method = propertyDescriptor
                            .getReadMethod();
                    Object value = method.invoke(instance);
                    if (field.isAnnotationPresent(Encrypted.class)) {
                        value = encryptor.getEncryptedObject(value);
                    }

                    valuesToCheckOn.add(value);
                }
                databaseSelector =
                        new DatabaseSelector(instance.getClass(), encryptor, fieldsToCheckOn, valuesToCheckOn);
                objectsInserted.addAll(databaseSelector.selectObjects());

            }
            return objectsInserted;

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}

