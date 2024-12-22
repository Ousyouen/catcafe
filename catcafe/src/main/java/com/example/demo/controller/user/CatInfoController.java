package com.example.demo.controller.user;

import com.example.demo.model.CatInfoMst;
import com.example.demo.service.CatInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CatInfoController {

	@Autowired
	private CatInfoService catInfoService;

	// すべての猫の情報を取得
	@GetMapping("/cats")
	public String getAllCats(Model model) {
		List<CatInfoMst> cats = catInfoService.getCatInfoList();
		model.addAttribute("cats", cats);// 猫の情報をモデルに追加
		return "cats"; // ビュー名を返す（ビュー解析器がこの名前に対応するページを探します）
	}
	@GetMapping("/admin")
	public String getAdmin() {
		return "admin_kanri";
	}
}
