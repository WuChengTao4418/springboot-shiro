package com.wct.service;

import com.wct.pojo.User;

public interface UserService {
   User getUserByName(String name);
    User getUserById(int id);

}
