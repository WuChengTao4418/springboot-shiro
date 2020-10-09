package com.wct;

import com.wct.mapper.UserMapper;
import com.wct.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootShiroApplicationTests {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
//        System.out.println(userService.getUserByName("wct"));
        System.out.println(userService.getUserByName("wct"));
        System.out.println(userMapper.getUserById(1005));
        System.out.println(userService.getUserById(1005));
    }

}
