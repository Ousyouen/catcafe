/*
 * 猫の情報
 * @author Wu
 * @version 1.0
 */

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatInfoController {
	@GetMapping("/CatInfo")
	public String CatInfo() {
		return "CatInfo";
	}
}
