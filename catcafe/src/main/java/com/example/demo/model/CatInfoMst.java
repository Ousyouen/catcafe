package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CatInfoMst extends BaseModel{

    @Column(name = "cat_name", nullable = false)
    private String catName; // 猫の名前，猫的名字

    @Column(name = "cat_intro", columnDefinition = "TEXT")
    private String catIntro; // 猫の紹介，猫的介绍

    @Column(name = "likes_count")
    private Integer likesCount; // 猫のいいね数，猫的点赞数
    
    @Column(name = "osusume_flg")
    private Long osusumeFlg;      // おすすめFLG (1表示推荐)

    @Column(name = "cat_image")
    private String catImage; // 猫の画像，猫的图片URL（或其他存储方式）


    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatIntro() {
        return catIntro;
    }

    public void setCatIntro(String catIntro) {
        this.catIntro = catIntro;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

	public Long getOsusumeFlg() {
		return osusumeFlg;
	}

	public void setOsusumeFlg(Long osusumeFlg) {
		this.osusumeFlg = osusumeFlg;
	}
    
    
}
