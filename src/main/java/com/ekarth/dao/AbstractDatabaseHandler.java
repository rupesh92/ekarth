package com.ekarth.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * An abstract class that handles insert/select-operations into/from a database
 *
 * @param <T>
 */

public abstract class AbstractDatabaseHandler<T> {


    /**
     * The type of the objects that should be created and filled with values
     * <p>
     * from the database or inserted into the database
     */

    protected Class<T> type;





    /**
     * The SQL-select-query
     */

    protected final String query;


    /**
     * Constructor
     *
     * @param type The type of the objects that should be created and filled with
     *             <p>
     *             values from the database or inserted into the database
     */

    protected AbstractDatabaseHandler(Class<T> type) {
        this.type = type;
        this.query = createQuery();


    }


    /**
     * Create the SQL-String to insert into / select from the database
     *
     * @return the SQL-String
     */

    protected abstract String createQuery();


    /**
     * Creates a comma-separated-String with the names of the variables in this
     * <p>
     * class
     *
     * @param usePlaceHolders true, if PreparedStatement-placeholders ('?') should be used
     *                        <p>
     *                        instead of the names of the variables
     * @return
     */

    protected String getColumns(boolean usePlaceHolders) {

        StringBuilder sb = new StringBuilder();
        boolean first = true;

	/* Iterate the column-names */

        for (Field f : type.getDeclaredFields()) {
            if (first)
                first = false;
            else
                sb.append(", ");

            if (usePlaceHolders)
                sb.append("?");
            else
                sb.append(f.getName());
        }
        return sb.toString();

    }
}


