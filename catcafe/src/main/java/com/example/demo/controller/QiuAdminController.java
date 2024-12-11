package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QiuAdminController {
	@GetMapping("/猫情報管理")
	public String user() {
		return "QIU.Admin";
	}

}
