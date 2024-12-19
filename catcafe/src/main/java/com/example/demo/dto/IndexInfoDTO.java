package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.CatInfoMst;

public class IndexInfoDTO {

    // 画面カルーセルリスト，存储多个画面轮播的内容（例如图片URL）
    private List<String> carouselList;

    // 店舗紹介：店铺介绍
    private String storeIntro;

    // 猫たちの紹介：猫的介绍
    private String catsIntro;

    // 猫の里親募集：猫的领养请求
    private String catsAdoption;

    // 猫の家族：猫的家庭
    private String catsFamily;

    // ブログ：博客内容
    private String blog;

    // 猫のリスト：多个猫的列表，每个猫包含名字、介绍、点赞数、图片等
    private List<CatInfoMst> catsList;

    // Getter 和 Setter 方法
    public List<String> getCarouselList() {
        return carouselList;
    }

    public void setCarouselList(List<String> carouselList) {
        this.carouselList = carouselList;
    }

    public String getStoreIntro() {
        return storeIntro;
    }

    public void setStoreIntro(String storeIntro) {
        this.storeIntro = storeIntro;
    }

    public String getCatsIntro() {
        return catsIntro;
    }

    public void setCatsIntro(String catsIntro) {
        this.catsIntro = catsIntro;
    }

    public String getCatsAdoption() {
        return catsAdoption;
    }

    public void setCatsAdoption(String catsAdoption) {
        this.catsAdoption = catsAdoption;
    }

    public String getCatsFamily() {
        return catsFamily;
    }

    public void setCatsFamily(String catsFamily) {
        this.catsFamily = catsFamily;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

	public List<CatInfoMst> getCatsList() {
		return catsList;
	}

	public void setCatsList(List<CatInfoMst> catsList) {
		this.catsList = catsList;
	}


 
}

