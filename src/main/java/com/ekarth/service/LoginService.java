package com.ekarth.service;

import com.ekarth.dao.DatabaseInserter;
import com.ekarth.dao.DatabaseSelector;
import com.ekarth.model.Seller;
import com.ekarth.security.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by rupesh on 01/09/17.
 */


@Service
public class LoginService {

    @Autowired
    Encryptor encryptor;

    @Autowired EmailService emailService;

    public String signUp(Seller seller) throws InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //TODO: make these columns unique , these checks wouldnot be needed
        validateNonExistance(Seller.class.getDeclaredField("companyName"), seller.getCompanyName());
        validateNonExistance(Seller.class.getDeclaredField("emailId"), seller.getEmailId());

        DatabaseInserter<Seller> sellerDatabaseInserter = new DatabaseInserter<>(Seller.class, encryptor);
        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);
        sellerDatabaseInserter.insertObjects(sellers);

        return "success";
    }

    private void validateNonExistance(Field field, String value) {

    }


    public Optional<Seller> login(String companyName, String password) throws NoSuchFieldException, InvocationTargetException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException {
        Field companyNameField = Seller.class.getDeclaredField("companyName");
        List<Field> fields = new ArrayList<>();
        fields.add(companyNameField);

        List<Object> values = new ArrayList<>();
        values.add(companyName);

        DatabaseSelector<Seller> databaseSelector= new DatabaseSelector<>(Seller.class, encryptor, fields,values );
        List<Seller> sellers = databaseSelector.selectObjects();

        if(sellers.size()>1){
            System.out.println("Something bad has happened with our database, " +
                    "there are more than 1 companies with this name");
            return Optional.empty();
        }

        if(sellers.isEmpty() || !encryptor.getbCryptPasswordEncoder().matches(password, sellers.get(0).getPasswordDigest())){
            System.out.println("No seller like that dude!");
            return Optional.empty();
        }

        return Optional.of(sellers.get(0));

    }
}
