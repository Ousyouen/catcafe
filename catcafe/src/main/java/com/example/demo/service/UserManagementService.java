package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.dao.UserManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService {
    @Autowired
    private UserManagementMapper userManagementMapper;

    public List<User> getAllUsers() {
        return userManagementMapper.getAllUsers();
    }

    public User getUserById(Integer id) {
        return userManagementMapper.getUserById(id);
    }

    public boolean addUser(User user) {
        return userManagementMapper.addUser(user) > 0;
    }

    public boolean updateUser(User user) {
        return userManagementMapper.updateUser(user) > 0;
    }

    public boolean deleteUser(Integer id) {
        return userManagementMapper.deleteUser(id) > 0;
    }

    public List<User> searchUsersByName(String username) {
        return userManagementMapper.searchUsersByName(username);
    }
}
