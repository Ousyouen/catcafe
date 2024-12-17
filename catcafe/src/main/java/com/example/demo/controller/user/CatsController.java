package com.example.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CatsController {
	@GetMapping("/cats")
	public String cats() {
		return "cats";
	}
	@GetMapping("/catsManagement")
	public String catManagement() {
		return "catinfomanagement";
	}
}
