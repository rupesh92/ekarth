package com.ekarth;

import com.ekarth.dao.CustomerDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shiwang on 5/23/17.
 */
    public class Main
    {
        public static void main( String[] args )
        {
            String userDir = System.getProperty("user.dir");
            String relativeDir = "/src/main/webapp/WEB-INF/spring-datasource.xml";
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("spring-core-config.xml");
            

            CustomerDAO customerDAO = (CustomerDAO) context.getBean("customerDAO");


        }
    }

