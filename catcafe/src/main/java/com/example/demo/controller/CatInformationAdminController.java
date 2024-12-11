package com.example.demo.controller;
/**
 * 猫情報管理者ページ
 * @author Qiu
 * @version 1.0
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatInformationAdminController {
	@GetMapping("/maoguanli")
	public String user() {
		return "QIU/admin";
	}

}
