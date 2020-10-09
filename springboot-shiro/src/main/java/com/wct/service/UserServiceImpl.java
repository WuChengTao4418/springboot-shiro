package com.wct.service;

import com.wct.mapper.UserMapper;
import com.wct.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }
}
