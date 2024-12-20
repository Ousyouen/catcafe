package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CatInfoDAO;
import com.example.demo.model.CatInfoMst;




@Service
public class CatInfoService {
	@Autowired
	private CatInfoDAO catInfoDAO;

	// すべての猫の情報を取得（削除されていない猫のみ）
	public List<CatInfoMst> getCatInfoList() {
		return catInfoDAO.findCatInfo();
	}

	
	// IDで猫の情報を取得（削除されていない猫のみ）
	public CatInfoMst getCatInfoById(Long id) {
		return catInfoDAO.findCatInfoById(id);
	}

	// 新しい猫の情報を追加
	@Transactional
	public int addCatInfo(CatInfoMst catInfo) {
		// ここで追加のビジネスロジックを追加できます
		// 例えば、猫の名前が重複していないか、データの検証など
		return catInfoDAO.insertCatInfo(catInfo);
	}

	// 猫の情報を更新
	@Transactional
	public int updateCatInfo(CatInfoMst catInfo) {
		// ここで追加のビジネスロジックを追加できます
		// 例えば、指定された猫が存在するか、データの検証など
		return catInfoDAO.updateCatInfo(catInfo);
	}

	// 猫の情報をソフト削除（delete_flagを1に設定）
	@Transactional
	public int deleteCatInfo(Long id) {
		return catInfoDAO.deleteCatInfo(id);
	}
}