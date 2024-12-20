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
import com.example.demo.model.AboutInfo;
import com.example.demo.service.AboutInfoService;


@Controller
public class AboutController {

    @Autowired
    private AboutInfoService aboutInfoService;

    @GetMapping("/about")
    public String showAboutPage(Model model) {
    	AboutInfo aboutInfo = aboutInfoService.getAboutInfoById(1);
    	
    	
        model.addAttribute("aboutInfo", aboutInfo);

        return "about"; 
    }
}