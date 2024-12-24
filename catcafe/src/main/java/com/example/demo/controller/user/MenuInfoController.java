package com.example.demo.controller.user;

import com.example.demo.model.CatInfoMst;
import com.example.demo.model.MenuInfoPO;
import com.example.demo.service.CatInfoService;
import com.example.demo.service.MenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuInfoController {

	@Autowired
	private MenuInfoService menuInfoService;

	/**
	 * 获取所有菜单信息
	 * @param model
	 * @return
	 */
	@GetMapping("/menus")
	public String getAllMenuInfo(Model model) {
		List<MenuInfoPO> menuInfos = menuInfoService.getAllMenuInfo();
		model.addAttribute("menuInfos", menuInfos);
		return "menu";
	}

}
