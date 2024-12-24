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

	// 削除されていないすべての猫情報を取得
	public List<CatInfoMst> getCatInfoList() {
		return catInfoDAO.findCatInfo();
	}

	// IDで猫の情報を取得（削除されていない猫のみ）
	public CatInfoMst getCatInfoById(Long id) {
		return catInfoDAO.findCatInfoById(id);
	}

	// 新しい猫情報を追加
	@Transactional
	public int addCatInfo(CatInfoMst catInfo) {
		return catInfoDAO.insertCatInfo(catInfo);
	}

	// 猫の情報を更新
	@Transactional
	public int updateCatInfo(CatInfoMst catInfo) {
		return catInfoDAO.updateCatInfo(catInfo);
	}

	// 猫の情報をソフト削除（delete_flagを1に設定）
	@Transactional
	public int deleteCatInfo(Long id) {
		return catInfoDAO.deleteCatInfo(id);
	}

	// ページネーション付きで猫情報を取得
	public List<CatInfoMst> getCatInfoList(int offset, int pageSize) {
		List<CatInfoMst> cats = catInfoDAO.findCatInfoWithPagination(pageSize, offset);
		return cats;
	}

	// 猫の総数を取得
	public int getTotalCatCount() {
		return catInfoDAO.countCats();
	}

	// 猫情報を検索（名前と年齢で）
	public List<CatInfoMst> searchCats(String name, Integer age) {
		if (name != null && age != null) {
			return catInfoDAO.searchCatsByNameAndAge(name, age); // 同時に名前と年齢で検索
		} else if (name != null) {
			return catInfoDAO.searchCatsByName(name); // 名前で検索
		} else if (age != null) {
			return catInfoDAO.searchCatsByAge(age); // 年齢で検索
		} else {
			return catInfoDAO.getAllCats(); // 検索条件がなければ、すべての猫情報を返す
		}
	}
}
