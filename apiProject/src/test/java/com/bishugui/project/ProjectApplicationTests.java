package com.bishugui.project;

import com.bishugui.project.entity.User;
import com.bishugui.project.service.UserService;
import com.bishugui.project.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SpringBootTest
class ProjectApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    @Test
    void testGetUser(){
        List<User> userAll = userService.getUserAll();
        userAll.forEach(System.out::println);
    }

    @Test
    void test1(){
        String str1 = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMTAwMDAwIiwic3ViIjoiMTIzIiwiaWF0IjoxNjIwMTkzNzc5fQ.1sySmCDE4qyXkdNuhx7NgTAxWrJWXiIYQtNGoj-Wy9s";
        String str2 = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMTAwMDAwIiwic3ViIjoiMTIzIiwiaWF0IjoxNjIwMTkzODcxfQ.tXl0myNZwrwihazZ_AT7VrtvdHTHb3T6pBfLcaAoJE0";
    }


}
