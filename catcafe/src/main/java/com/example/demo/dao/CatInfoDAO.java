package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CatInfoMst;

import java.util.List;

@Repository
public interface CatInfoDAO extends JpaRepository<CatInfoMst, Long> {

    
     // 根据 推荐标志 (おすすめFLG) 和 删除标志 (削除FLG) 获取符合条件的猫的信息
    List<CatInfoMst> findByOsusumeFlgAndDeleteFlag(int osusumeFlg, int deleteFlag);
}
