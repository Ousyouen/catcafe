package com.example.demo.dao;

import com.example.demo.model.CatInfoMst;
import com.example.demo.model.MenuInfoPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuInfoDAO {

	// 获取所有菜单信息
	@Select("SELECT * FROM menu_info WHERE delete_flag = 0")
	List<MenuInfoPO> getAllMenuInfo();

}
