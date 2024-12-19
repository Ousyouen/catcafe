package com.example.demo.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.IndexInfoDTO;
import com.example.demo.service.IndexInfoService;

@Controller
public class indexController {

    @Autowired
    private IndexInfoService indexInfoService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/index/indexDTO")
	public ResponseEntity<IndexInfoDTO> showDate() {
		// 调用服务层获取IndexInfoDTO数据
		IndexInfoDTO indexInfoDTO = indexInfoService.getStoreInfo();
		return ResponseEntity.ok(indexInfoDTO);	
    }
	
}
