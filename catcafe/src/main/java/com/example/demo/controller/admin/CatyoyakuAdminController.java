package com.example.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatyoyakuAdminController {

	@GetMapping("/yoyakukanri")
	public String yoyakukanri() {
		return "admin/yoyakuAdmin";
		
	}
}
