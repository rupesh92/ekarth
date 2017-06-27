package com.rupesh.secureGifts.controller;

import com.rupesh.secureGifts.helper.StatusEnum;
import com.rupesh.secureGifts.model.ToDo;
import com.rupesh.secureGifts.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by rupesh on 29/05/17.
 */
public class ToDoController {
    @Autowired
    ToDoService toDoService;

    @RequestMapping(value = "/api/v1/getAllTodo", method = RequestMethod.GET)
    @ResponseBody
    public List<ToDo> getAllTodo() {
        return toDoService.getAllTodo();
    }

    @RequestMapping(value = "/api/v1/addTodo", method = RequestMethod.POST)
    @ResponseBody
    public ToDo insertTodo(@RequestParam("customerEmail") String customerEmail,
                             @RequestParam("text") String text,
                             @RequestParam("status") String statusString) {
        return toDoService.insertTodo(customerEmail, text, StatusEnum.valueOf(statusString));
    }

    @RequestMapping(value = "/api/v1/updateStatuc", method = RequestMethod.POST)
    @ResponseBody
    public ToDo updateStatus(@RequestParam("customerEmail") String customerEmail,
                           @RequestParam("status") String statusString) {
        return toDoService.updateStatus(customerEmail, StatusEnum.valueOf(statusString));
    }
}
