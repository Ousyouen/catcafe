package com.example.demo.controller.user;
/**
 * 
 * @author Qiu
 * @version 1.0
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatIndexController {
	@GetMapping("/shouye")
	public String index() {
		return "index";
	}
	@GetMapping("/shouye/information")
	public String information() {
		return "portfolio";
	}

}
