package com.ekarth.dao;

import com.ekarth.model.Customer;

/**
 * Created by shiwang on 5/23/17.
 */
public interface CustomerDAO {
        public void insert(Customer customer);
        public Customer findByCustomerId(int custId);

        public String signUp(Customer customer);
}
