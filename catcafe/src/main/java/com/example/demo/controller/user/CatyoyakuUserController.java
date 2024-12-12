package com.example.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatyoyakuUserController {

	@GetMapping("/yoyaku")
	public String yoyaku() {
		return "user/yoyaku";
	}
	
	
	
}
