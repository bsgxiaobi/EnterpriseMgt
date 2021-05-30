package com.bishugui.project.controller;

import com.bishugui.project.entity.User;
import com.bishugui.project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;




    @GetMapping("/list")
    public List<User> getUser(){
        List<User> userAll = userService.getUserAll();
//        userAll.forEach(System.out::println);
        return userAll;
    }
}
