<strong> Create db : </strong>

      CREATE ekarth;

      use ekarth;
      
<strong> delete table </strong>

      DROP TABLE SELLER;
      
      
<strong> Create user table </strong>

       CREATE TABLE SELLER(

                id   INT NOT NULL AUTO_INCREMENT,
                
                name VARCHAR(40) NOT NULL,
                
                companyName VARCHAR(40) NOT NULL,
                
                emailId VARCHAR(40) NOT NULL,
                
                contactNumber VARCHAR(40) NOT NULL,
                
                PRIMARY KEY (id)
                
        );

        ALTER TABLE SELLER ADD passwordDigest VARCHAR(100);
        
        CREATE TABLE CATEGORY(

                categoryId   INT NOT NULL AUTO_INCREMENT,
                
                name VARCHAR(40) NOT NULL,
                
                companyId INT NOT NULL,
                
                PRIMARY KEY (categoryId)
                
        );
        
        CREATE TABLE PROPERTY(

                propertyId   INT NOT NULL AUTO_INCREMENT,
                
                name VARCHAR(40) NOT NULL,
                
                propertyValues VARCHAR(80),
                
                PRIMARY KEY (propertyId)
                
        );
        
        CREATE TABLE PRODUCT(

                productId   INT NOT NULL AUTO_INCREMENT,
                
                name VARCHAR(40) NOT NULL,
                
                qty INT NOT NULL,
                
                price INT NOT NULL,
                
                image VARCHAR(40),
                
                PRIMARY KEY (productId)
                
        );
        
        CREATE TABLE PRODUCTPROPERTY(

                productId   INT NOT NULL,
                
                propertyId INT NOT NULL,
                
                propertyValue VARCHAR(40) NOT NULL
                
        );
        
        CREATE TABLE PRODUCTCATEGORY(

                productId   INT NOT NULL,
                
                categoryId INT NOT NULL
                
        );


<strong> Update password SQL </strong>

        sudo /usr/local/mysql/bin/mysqld_safe --skip-grant-tables

        $ mysql -u root

        mysql> USE mysql;

        mysql> UPDATE user SET authentication_string=PASSWORD("XXXXXXX") WHERE User='root';

        mysql> FLUSH PRIVILEGES;

        mysql> quit
