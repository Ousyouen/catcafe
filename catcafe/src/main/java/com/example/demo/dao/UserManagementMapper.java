package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserManagementMapper {
    // 查询所有用户
    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    // 根据ID查询用户
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(@Param("id") Integer id);

    // 添加新用户
    @Insert("INSERT INTO users (username, birthday, email) VALUES (#{username}, #{birthday}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);

    // 更新用户
    @Update("UPDATE users SET username = #{username}, birthday = #{birthday}, email = #{email} WHERE id = #{id}")
    int updateUser(User user);

    // 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteUser(@Param("id") Integer id);

    // 根据用户名模糊搜索
    @Select("SELECT * FROM users WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<User> searchUsersByName(@Param("username") String username);
}
