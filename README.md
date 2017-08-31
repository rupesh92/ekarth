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


<strong> Update password SQL </strong>

        sudo /usr/local/mysql/bin/mysqld_safe --skip-grant-tables

        $ mysql -u root

        mysql> USE mysql;

        mysql> UPDATE user SET authentication_string=PASSWORD("XXXXXXX") WHERE User='root';

        mysql> FLUSH PRIVILEGES;

        mysql> quit
