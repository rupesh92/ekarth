package com.ekarth.dao;

import com.ekarth.model.annotations.PrimaryKey;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.lang.annotation.Annotation;
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

    protected String query;


    public DriverManagerDataSource databaseConnecter;

    /**
     * Constructor
     *
     * @param type The type of the objects that should be created and filled with
     *             <p>
     *             values from the database or inserted into the database
     */

    protected AbstractDatabaseHandler(Class<T> type) {
        this.type = type;
        databaseConnecter = new DriverManagerDataSource("jdbc:mysql://localhost:3306/ekarth", "root", "spring");
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

    protected String getColumns(boolean usePlaceHolders, boolean isPrimarykeyNeeded) {

        StringBuilder sb = new StringBuilder();
        boolean first = true;

	/* Iterate the column-names */

        for (Field f : type.getDeclaredFields()) {
            Annotation[] check = f.getAnnotations();
            if (!isPrimarykeyNeeded && f.isAnnotationPresent(PrimaryKey.class)) {
                continue;
            }
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


