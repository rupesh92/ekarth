<strong> Create db : </strong>

      CREATE ekarth;

      use ekarth;

<strong> Create user table </strong>

       CREATE TABLE CUSTOMER(

                CUST_ID   INT NOT NULL AUTO_INCREMENT,
                
                NAME VARCHAR(40) NOT NULL,
                
                COMPANY_NAME VARCHAR(40) NOT NULL,
                
                EMAIL_ID VARCHAR(40) NOT NULL,
                
                CONTACT_NUMBER VARCHAR(40) NOT NULL,
                
                PRIMARY KEY (CUST_ID)
                
        );

        ALTER CUSTOMER ADD PASSWORD_DIGEST VARCHAR(100);
        
        CREATE TABLE CATEGORY(

                CATEGORY_ID   INT NOT NULL AUTO_INCREMENT,
                
                NAME VARCHAR(40) NOT NULL,
                
                COMPANY_ID INT NOT NULL,
                
                PRIMARY KEY (CATEGORY_ID)
                
        );
        
        CREATE TABLE PROPERTY(

                PROPERTY_ID   INT NOT NULL AUTO_INCREMENT,
                
                NAME VARCHAR(40) NOT NULL,
                
                VALUES VARCHAR(400),
                
                PRIMARY KEY (PROPERTY_ID)
                
        );
        
        CREATE TABLE PRODUCT(

                PRODUCT_ID   INT NOT NULL AUTO_INCREMENT,
                
                NAME VARCHAR(40) NOT NULL,
                
                QTY INT NOT NULL,
                
                PRICE INT NOT NULL,
                
                IMAGE VARCHAR(40),
                
                PRIMARY KEY (PRODUCT_ID)
                
        );
        
        CREATE TABLE PRODUCT_PROPERTY(

                PRODUCT_ID   INT NOT NULL,
                
                PROPERTY_ID INT NOT NULL,
                
                VALUE VARCHAR(40) NOT NULL
                
        );
        
        CREATE TABLE PRODUCT_CATEGORY(

                PRODUCT_ID   INT NOT NULL,
                
                CATEGORY_ID INT NOT NULL
                
        );


<strong> Update password SQL </strong>

        sudo /usr/local/mysql/bin/mysqld_safe --skip-grant-tables

        $ mysql -u root

        mysql> USE mysql;

        mysql> UPDATE user SET authentication_string=PASSWORD("XXXXXXX") WHERE User='root';

        mysql> FLUSH PRIVILEGES;

        mysql> quit
