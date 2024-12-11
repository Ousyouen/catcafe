package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QiuUserController {
	@GetMapping("/猫情報")
	public String user() {
		return "QIU.User";
	}

}
