package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AboutInfoDAO;
import com.example.demo.model.AboutInfo;

@Service
public class AboutInfoService {
	@Autowired
	private AboutInfoDAO aboutInfoDAO;
	
	// IDで猫の情報を取得（削除されていない猫のみ）
	public AboutInfo getAboutInfoById(int id) {
		return aboutInfoDAO.findCatInfoById(id);
	}

}
