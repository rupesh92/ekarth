package com.mkyong.helloworld.dao;

import com.mkyong.helloworld.model.Customer;

/**
 * Created by rupesh on 24/05/17.
 */

public interface CustomerDAO
{
    public void insert(Customer customer);
    public Customer findByCustomerId(int custId);
}
