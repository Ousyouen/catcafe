package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.StoreInfoMst;

import java.util.Optional;

@Repository
public interface StoreInfoDAO extends JpaRepository<StoreInfoMst, Long> {

    // 根据删除标志（delete_flag = 0）获取店铺信息
    Optional<StoreInfoMst> findByDeleteFlag(int i);
}
