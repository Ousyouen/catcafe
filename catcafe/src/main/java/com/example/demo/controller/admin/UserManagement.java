package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagement {
	
	@GetMapping("usermanage")
	public String UserManage() {
		return "usermanage";	
	}
}