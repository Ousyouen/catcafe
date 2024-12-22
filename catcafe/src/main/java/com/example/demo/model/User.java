package com.example.demo.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
    private Integer id;         // 用户 ID
    private String username;    // 用户名

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo") // 日期格式化
    private Date birthday;      // 生日

    private String email;       // 邮箱

    // Getters 和 Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", birthday=" + birthday +
               ", email='" + email + '\'' +
               '}';
    }
}
