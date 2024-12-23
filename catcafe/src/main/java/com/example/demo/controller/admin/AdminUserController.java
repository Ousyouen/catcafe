package com.example.demo.controller.admin;

import com.example.demo.model.User;
import com.example.demo.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class AdminUserController {
    @Autowired
    private UserManagementService userManagementService;

    // 获取所有用户
    @GetMapping
    @ResponseBody
    public List<User> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Integer id) {
        return userManagementService.getUserById(id);
    }

    // 添加新用户
    @PostMapping
    @ResponseBody
    public String addUser(@RequestBody User user) {
        if (userManagementService.addUser(user)) {
            return "新しいユーザーが追加されました";
        } else {
            return "ユーザーの追加に失敗しました";
        }
    }

    // 更新用户
    @PutMapping("/{id}")
    @ResponseBody
    public String updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        if (userManagementService.updateUser(user)) {
            return "ユーザー情報が更新されました";
        } else {
            return "ユーザー情報の更新に失敗しました";
        }
    }

    // 删除用户
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Integer id) {
        if (userManagementService.deleteUser(id)) {
            return "ユーザー情報が削除されました";
        } else {
            return "ユーザー情報の削除に失敗しました";
        }
    }

    // 模糊搜索用户
    @GetMapping("/search")
    @ResponseBody
    public List<User> searchUsersByName(@RequestParam String name) {
        return userManagementService.searchUsersByName(name);
    }

    // 启动 HTML 页面
    @GetMapping("/usermanage")
    public String userManagementPage() {
        return "usermanage"; // 渲染 `src/main/resources/templates/usermanage.html`
    }
}
