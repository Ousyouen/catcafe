package com.example.demo.controller.regist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MyResisterController {

    @RequestMapping(value = "/toRegister", method = RequestMethod.GET)
    public String toRegister() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(RegisterVO registerVO, HttpServletResponse response) throws IOException {
        System.out.println("收到注册请求...");
        System.out.println("registerVO = " + registerVO.toString());
        System.out.println("注册完成...");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        writer.write("register success");
    }
}
