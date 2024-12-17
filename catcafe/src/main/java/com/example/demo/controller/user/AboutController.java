package com.example.demo.controller.user;
/**
 * aboutユーザーページ
 * @author Qiu
 * @version 1.0
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.controller.admin.AdminAboutController;

import java.util.Map;

@Controller
public class AboutController {

    @Autowired
    private AdminAboutController adminAboutController;

    @GetMapping("/about")
    public String showAboutPage(Model model) {
        Map<String, String> data = adminAboutController.getAboutPageData();
        model.addAttribute("aboutTitle", data.get("aboutTitle"));
        model.addAttribute("aboutContent", data.get("aboutContent"));
        model.addAttribute("catTitle", data.get("catTitle"));
        model.addAttribute("catContent", data.get("catContent"));
        model.addAttribute("teamTitle", data.get("teamTitle"));
        model.addAttribute("teamMember1", data.get("teamMember1"));
        model.addAttribute("teamMember2", data.get("teamMember2"));
        return "about"; 
    }
}