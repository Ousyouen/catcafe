package com.example.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 获取用户信息（返回 JSON 数据）
    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);

        // 调试日志：打印传入的 ID 和查询结果
        System.out.println("Received request for user with ID: " + id);

        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            return ResponseEntity.ok(user);
        } else {
            System.out.println("No user found for ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    // 更新用户信息（返回 JSON 数据）
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        System.out.println("Received request to update user: " + user);
        
        if (userService.updateUser(user)) {
            System.out.println("User updated successfully: " + user.getId());
            return ResponseEntity.ok("更新成功");
        } else {
            System.out.println("Failed to update user: " + user.getId());
            return ResponseEntity.status(500).body("更新失败");
        }
    }

    // 显示用户信息页面（返回 Thymeleaf 模板）
    @GetMapping("/mypage/{id}")
    public String myPageWithUserData(@PathVariable int id, Model model) {
        // 获取用户信息
        User user = userService.getUserById(id);

        // 调试日志：打印传入的 ID 和查询结果
        System.out.println("Request for user page with ID: " + id);

        if (user != null) {
            System.out.println("User found: " + user.getUsername());
        } else {
            System.out.println("No user found for ID: " + id);
        }

        // 将用户信息添加到模型中（允许模板检查 null 值）
        model.addAttribute("user", user);

        // 返回模板页面的名称（mypage.html）
        return "mypage";
    }
}
