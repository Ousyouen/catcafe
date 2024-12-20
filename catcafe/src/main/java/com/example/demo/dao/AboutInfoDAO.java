package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.AboutInfo;

@Mapper
public interface AboutInfoDAO {
	// IDと削除フラグでaboutinfoデータを取得
		@Select("SELECT * FROM about_info WHERE id = #{id} AND delete_flag = 0")
		AboutInfo findCatInfoById(int id);

}
