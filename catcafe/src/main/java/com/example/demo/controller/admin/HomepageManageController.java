package com.example.demo.controller.admin;
/*ホームページ管理
 * author 林晟
 * 
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomepageManageController {

    // 显示首页
    @GetMapping("/homepage/admin")
    public String index(Model model) {
        model.addAttribute("title", "欢迎来到猫咖管理后台");
        model.addAttribute("description", "在这里，您可以管理猫咖的首页内容。");
        return "admin_index";  // 返回模板的名字
    }

    // 编辑首页内容
    @GetMapping("/homepage/admin/edit")
    public String editPage(Model model) {
        model.addAttribute("title", "欢迎来到猫咖后台管理");
        model.addAttribute("description", "请编辑首页标题和简介。");
        return "admin_editIndex";  // 返回编辑页面模板
    }

    // 处理提交的表单
    @PostMapping("/homepage/admin/success")
    public String updateHomepage(String title, String description, Model model) {
        
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        return "admin_success";  // 返回成功页面
    
   
    
    
    
    }
}

	
	
	
	

