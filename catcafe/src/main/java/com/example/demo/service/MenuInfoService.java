package com.example.demo.service;

import com.example.demo.dao.CatInfoDAO;
import com.example.demo.dao.MenuInfoDAO;
import com.example.demo.model.CatInfoMst;
import com.example.demo.model.MenuInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuInfoService {

	@Autowired
	private MenuInfoDAO menuInfoDAO;

	/**
	 * 获取所有菜单信息
	 * @return
	 */
	public List<MenuInfoPO> getAllMenuInfo() {
		return menuInfoDAO.getAllMenuInfo();
	}

}
