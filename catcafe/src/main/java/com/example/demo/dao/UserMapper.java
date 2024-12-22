package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;

@Mapper
@Component
public interface UserMapper {

    // 获取用户信息
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(int id);

    // 更新用户信息
    @Update("UPDATE users SET username = #{username}, birthday = #{birthday}, email = #{email} WHERE id = #{id}")
    int updateUser(User user);
}
