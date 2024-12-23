package com.example.demo.service;

import com.example.demo.dao.RegisterUserDAO;
import com.example.demo.model.RegisterUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {
    @Autowired
    private RegisterUserDAO registerUserDAO;

    public Integer registerUser(RegisterUserVO registerUserVO) {
        return registerUserDAO.registerUser(registerUserVO);
    }

    public RegisterUserVO getUserInfoByName(String name) {
        return registerUserDAO.getUserInfoByName(name);
    }

}
