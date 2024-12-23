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

	// ページネーション付きで猫情報を取得
	public List<CatInfoMst> getCatInfoList(int offset, int pageSize) {
		List<CatInfoMst> cats = catInfoDAO.findCatInfoWithPagination(pageSize, offset);

		return cats;
	}

	// 猫の総数を取得
	public int getTotalCatCount() {
		return catInfoDAO.countCats();
	}

	// 名前と年齢で猫情報を検索
	public List<CatInfoMst> searchCats(String name, Integer age, int offset, int pageSize) {
		return catInfoDAO.searchCats(name, age, offset, pageSize);
	}

	// 名前と年齢での検索結果の総数を取得
	public int getTotalCatCountBySearch(String name, Integer age) {
		return catInfoDAO.getTotalCatCountBySearch(name, age);
	}
}
