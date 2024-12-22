package com.example.demo.controller.admin;
/*ホームページ管理
 * author 林晟
 * 
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexManageController {

    // 显示首页
   
	
	@GetMapping("/homepage/admin")
    public String index() {
      
        return "admin_kanri";  // 返回模板的名字
    }

    // 编辑首页内容
    @GetMapping("/homepage/admin/edit")
    public String editPage() {
       
        return "admin_editIndex";  // 返回编辑页面模板
    }
   
  
}
 


	
	
	
	

