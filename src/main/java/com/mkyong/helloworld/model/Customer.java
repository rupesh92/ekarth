package com.mkyong.helloworld.model;

/**
 * Created by rupesh on 23/05/17.
 */

public class Customer
{
    int custId;
    String name;
    int age;

    public Customer(int cust_id, String name, int age) {
        this.custId = cust_id;
        this.name = name;
        this.age = age;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
