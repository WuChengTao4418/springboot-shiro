package com.wct.mapper;

import com.wct.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAllUser();
    User getUserByName(@Param("name")String name);
    User getUserById(int id);
    int addUser(User user);
    int deleteUserById(int id);
    int updateUserById(User user);
}
