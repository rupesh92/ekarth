package com.rupesh.secureGifts.service;

import com.rupesh.secureGifts.dao.ToDoDao;
import com.rupesh.secureGifts.helper.StatusEnum;
import com.rupesh.secureGifts.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by rupesh on 29/05/17.
 */
public class ToDoService {

    @Autowired
    ToDoDao toDoDao;

    public List<ToDo> getAllTodo() {
        return toDoDao.getAllTodo();
    }

    public ToDo insertTodo(String customerEmail, String text, StatusEnum statusEnum) {
        return toDoDao.insertTodo(customerEmail, text, statusEnum);
    }

    public ToDo updateStatus(String customerEmail, StatusEnum statusEnum) {
        return toDoDao.updateStatusFromCustomerEmail(customerEmail, statusEnum);
    }
}
