package com.mkyong.helloworld.model;

/**
 * Created by rupesh on 15/05/17.
 */
public class Counter {
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int count;

    public Counter(int count) {
        this.count = count;
    }
}
