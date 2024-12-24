package com.example.demo.dao;

import com.example.demo.model.CatInfoMst;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CatInfoDAO {
	// 削除されていない猫情報を検索
	@Select("SELECT * FROM cat_info WHERE delete_flag = 0")
	List<CatInfoMst> findCatInfo();

	// IDで猫の情報を調べる（削除されていない猫のみ）
	@Select("SELECT * FROM cat_info WHERE id = #{id} AND delete_flag = 0")
	CatInfoMst findCatInfoById(Long id);

	// 猫情報を挿入する（削除されていない猫のみ）
	@Insert("INSERT INTO cat_info (cat_name, cat_intro, cat_age, cat_image) "
			+ "VALUES (#{catName}, #{catIntro}, #{catAge}, #{catImage})")
	int insertCatInfo(CatInfoMst catInfo);

	// 猫情報を更新する（削除されていない猫のみ）
	@Update("UPDATE cat_info SET cat_name = #{catName}, cat_intro = #{catIntro}, "
			+ " cat_age = #{catAge}, cat_image = #{catImage} " + " WHERE id = #{id} AND delete_flag = 0")
	int updateCatInfo(CatInfoMst catInfo);

	// ソフト削除猫情報（delete_flagを1に設定）
	@Update("UPDATE cat_info SET delete_flag = 1 WHERE id = #{id}")
	int deleteCatInfo(Long id);

	// ページネーション付きで猫情報を検索
	@Select("SELECT * FROM cat_info WHERE delete_flag = 0 LIMIT #{pageSize} OFFSET #{offset}")
	List<CatInfoMst> findCatInfoWithPagination(@Param("pageSize") int pageSize, @Param("offset") int offset);

	// 猫の総数を取得
	@Select("SELECT COUNT(*) FROM cat_info WHERE delete_flag = 0")
	int countCats();

	// 名前と年齢で検索
	@Select("SELECT * FROM cat_info WHERE cat_name = #{name} AND cat_age = #{age}")
	List<CatInfoMst> searchCatsByNameAndAge(@Param("name") String name, @Param("age") Integer age);

	// 名前で検索
	@Select("SELECT * FROM cat_info WHERE cat_name = #{name}")
	List<CatInfoMst> searchCatsByName(@Param("name") String name);

	// 年齢で検索
	@Select("SELECT * FROM cat_info WHERE cat_age = #{age}")
	List<CatInfoMst> searchCatsByAge(@Param("age") Integer age);

	// すべての猫情報を取得
	@Select("SELECT * FROM cat_info")
	List<CatInfoMst> getAllCats();
}
