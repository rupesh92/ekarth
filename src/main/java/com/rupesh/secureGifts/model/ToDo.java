package com.rupesh.secureGifts.model;

import com.rupesh.secureGifts.helper.StatusEnum;

/**
 * Created by rupesh on 29/05/17.
 */
public class ToDo {

    private String id;
    private String customerEmail;
    private String text;
    private StatusEnum status;

    public ToDo(String id, String customerEmail, String text, StatusEnum status) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.text = text;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

}
