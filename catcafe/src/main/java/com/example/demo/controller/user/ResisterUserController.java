package com.example.demo.controller.user;

import cn.hutool.json.JSONUtil;
import com.example.demo.model.RegisterUserVO;
import com.example.demo.service.RegisterUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author csr
 */
@Controller
public class ResisterUserController {

    @Autowired
    RegisterUserService registerUserService;


    @RequestMapping(value = "/toRegister", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String  register(RegisterUserVO registerVO, HttpServletResponse response) throws IOException {

        // 前置校验：判断当前用户是否已经注册过，如果注册过，则不允许再次注册；否则，可以注册
        RegisterUserVO userInfoByName = registerUserService.getUserInfoByName(registerVO.getName());
        if (userInfoByName!= null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", 0);
            map.put("msg", "该用户名已被注册，请更换用户名");
            String jsonStr = JSONUtil.toJsonStr(map);
            System.out.println("jsonStr = " + jsonStr);
            return jsonStr;
        }

        System.out.println("收到注册请求...");
        System.out.println("registerVO = " + registerVO.toString());
        System.out.println("注册完成...");

        registerVO.setCreateTime(new Date());
        registerVO.setUpdateTime(new Date());
        registerVO.setDelFlag(0);
        registerUserService.registerUser(registerVO);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "恭喜您，注册成功");
        String jsonStr = JSONUtil.toJsonStr(map);
        System.out.println("jsonStr = " + jsonStr);
        return jsonStr;
    }


}
