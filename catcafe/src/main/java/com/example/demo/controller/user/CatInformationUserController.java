package com.example.demo.controller.user;
/**
 * 猫情報ユーザーページ
 * @author Qiu
 * @version 1.0
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatInformationUserController {
	@GetMapping("/mao")
	public String user() {
		return "QIU/user";
	}

}
