package com.bishugui.project.service;

import com.bishugui.project.entity.User;
import com.bishugui.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> getUserAll(){
        List<User> userList = userMapper.selectList(null);
        return userList;

    }
}
