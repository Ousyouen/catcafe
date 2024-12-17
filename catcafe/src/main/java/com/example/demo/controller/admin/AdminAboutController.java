package com.example.demo.controller.admin;
/**
 * about管理者ページ
 * @author Qiu
 * @version 1.0
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/about")
public class AdminAboutController {

    // 存储页面数据的假数据
    private Map<String, String> aboutPageData = new HashMap<>();

    // 初始化默认数据
    public AdminAboutController() {
        aboutPageData.put("aboutTitle", "About");
        aboutPageData.put("aboutContent", "Lorem Ipsum is simply dummy text...");
        aboutPageData.put("catTitle", "Cat Info");
        aboutPageData.put("catContent", "关于猫的信息，管理员可修改。");
        aboutPageData.put("teamTitle", "Our Team");
        aboutPageData.put("teamMember1", "MICHAEL FREEMAN");
        aboutPageData.put("teamMember2", "ADRIANA SPELLING");
    }

    // 显示管理页面
    @GetMapping
    public String showAdminAboutPage(Model model) {
        model.addAttribute("data", aboutPageData);
        return "admin_about";
    }

    // 保存修改数据
    @PostMapping("/save")
    public String saveAboutPageData(@RequestParam("aboutTitle") String aboutTitle,
                                    @RequestParam("aboutContent") String aboutContent,
                                    @RequestParam("catTitle") String catTitle,
                                    @RequestParam("catContent") String catContent,
                                    @RequestParam("teamTitle") String teamTitle,
                                    @RequestParam("teamMember1") String teamMember1,
                                    @RequestParam("teamMember2") String teamMember2) {
        aboutPageData.put("aboutTitle", aboutTitle);
        aboutPageData.put("aboutContent", aboutContent);
        aboutPageData.put("catTitle", catTitle);
        aboutPageData.put("catContent", catContent);
        aboutPageData.put("teamTitle", teamTitle);
        aboutPageData.put("teamMember1", teamMember1);
        aboutPageData.put("teamMember2", teamMember2);

        return "redirect:/admin/about";
    }

    // 提供数据给前台页面
    @GetMapping("/data")
    @ResponseBody
    public Map<String, String> getAboutPageData() {
        return aboutPageData;
    }
}
