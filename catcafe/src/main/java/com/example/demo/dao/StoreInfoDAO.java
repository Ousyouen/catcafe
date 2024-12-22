package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.demo.model.StoreInfoMst;


@Mapper
public interface StoreInfoDAO {

    // 根据删除标志（delete_flag = 0）获取店铺信息
	@Select("SELECT * FROM store_info WHERE delete_flag = 0")
	StoreInfoMst findByDeleteFlag(int id);
}