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
	// 猫情報の検索（削除されていない猫のみ）
	@Select("SELECT * FROM cat_info WHERE delete_flag = 0")
	List<CatInfoMst> findCatInfo();

	// IDで猫の情報を調べる（削除されていない猫のみ）
	@Select("SELECT * FROM cat_info WHERE id = #{id} AND delete_flag = 0")
	CatInfoMst findCatInfoById(Long id);

	// 猫情報の挿入（削除されていない猫のみ）
	@Insert("INSERT INTO cat_info (cat_name, cat_intro, cat_age, cat_image) "
			+ "VALUES (#{catName}, #{catIntro}, #{catAge}, #{catImage})")
	int insertCatInfo(CatInfoMst catInfo);

	// 猫情報の更新（削除されていない猫のみ）
	@Update("UPDATE cat_info SET cat_name = #{catName}, cat_intro = #{catIntro}, "
			+ " cat_age = #{catAge}, cat_image = #{catImage} " + " WHERE id = #{id} AND delete_flag = 0")
	int updateCatInfo(CatInfoMst catInfo);

	// ソフト削除猫情報（delete_flagを1に設定）
	@Update("UPDATE cat_info SET delete_flag = 1 WHERE id = #{id}")
	int deleteCatInfo(Long id);

	// 分页查询猫信息
	@Select("SELECT * FROM cat_info WHERE delete_flag = 0 LIMIT #{pageSize} OFFSET #{offset}")
	List<CatInfoMst> findCatInfoWithPagination(@Param("pageSize") int pageSize, @Param("offset") int offset);

	// 获取猫咪的总数
	@Select("SELECT COUNT(*) FROM cat_info WHERE delete_flag = 0")
	int countCats();

	// 根据条件查询猫咪信息
	@Select("<script>" + "SELECT * FROM cat_info_mst " + "WHERE 1=1 " + "<if test='name != null and name != \"\"'>"
			+ "  AND cat_name LIKE CONCAT('%', #{name}, '%') " + "</if>" + "<if test='age != null'>"
			+ "  AND cat_age = #{age} " + "</if>" + "LIMIT #{offset}, #{pageSize}" + "</script>")
	List<CatInfoMst> searchCats(@Param("name") String name, @Param("age") Integer age, @Param("offset") int offset,
			@Param("pageSize") int pageSize);

	// 获取符合条件的猫咪数量（用于分页）
	@Select("<script>" + "SELECT COUNT(*) FROM cat_info " + "WHERE 1=1 " + "<if test='name != null and name != \"\"'>"
			+ "  AND cat_name LIKE CONCAT('%', #{name}, '%') " + "</if>" + "<if test='age != null'>"
			+ "  AND cat_age = #{age} " + "</if>" + "</script>")
	int getTotalCatCountBySearch(@Param("name") String name, @Param("age") Integer age);
}
