package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CatInfoDAO;
import com.example.demo.dao.StoreInfoDAO;
import com.example.demo.dto.IndexInfoDTO;
import com.example.demo.model.CatInfoMst;
import com.example.demo.model.StoreInfoMst;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndexInfoService {

    @Autowired
    private StoreInfoDAO storeInfoDAO;

    @Autowired
    private CatInfoDAO catInfoDAO;

    // 根据店铺ID获取店铺信息和猫信息的DTO
    public IndexInfoDTO getStoreInfo() {
        // 从DAO获取店铺信息
        Optional<StoreInfoMst> storeInfoOptional = storeInfoDAO.findByDeleteFlag(0);
        if (!storeInfoOptional.isPresent()) {
            throw new RuntimeException("Store not found or already deleted");
        }
        StoreInfoMst storeInfo = storeInfoOptional.get();

        // 构建DTO对象
        IndexInfoDTO indexInfoDTO = new IndexInfoDTO();
        
        // 画面カルーセルリスト
        indexInfoDTO.setCarouselList(List.of(storeInfo.getCarousel1(), storeInfo.getCarousel2(), storeInfo.getCarousel3()));
        
        // 店舗紹介
        indexInfoDTO.setStoreIntro(storeInfo.getStoreIntro());
        
        // 猫たちの紹介
        indexInfoDTO.setCatsIntro(storeInfo.getCatsIntro());
        
        // 猫の里親募集
        indexInfoDTO.setCatsAdoption(storeInfo.getCatsAdoption());
        
        // 猫の家族
        indexInfoDTO.setCatsFamily(storeInfo.getCatsFamily());
        
        // ブログ
        indexInfoDTO.setBlog(storeInfo.getBlog());

        // 获取猫的信息列表
        List<CatInfoMst> cats = catInfoDAO.findByOsusumeFlgAndDeleteFlag(1, 0);

        // 将每个猫的信息封装到DTO中
        List<CatInfoMst> catInfoDTOList = cats.stream().map(cat -> {
        	CatInfoMst catInfoMst = new CatInfoMst();
        	catInfoMst.setCatName(cat.getCatName());
        	catInfoMst.setCatIntro(cat.getCatIntro());
        	catInfoMst.setLikesCount(cat.getLikesCount());
        	catInfoMst.setCatImage(cat.getCatImage());
            return catInfoMst;
        }).collect(Collectors.toList());

        // 设置猫的信息列表
        indexInfoDTO.setCatsList(catInfoDTOList);

        return indexInfoDTO;
    }
}
