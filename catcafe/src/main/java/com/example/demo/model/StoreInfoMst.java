package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
public class StoreInfoMst extends BaseModel {


    @Column(name = "carousel1")
    private String carousel1; // 画面カルーセル1，可以是图片的URL或相关文本

    @Column(name = "carousel2")
    private String carousel2; // 画面カルーセル2

    @Column(name = "carousel3")
    private String carousel3; // 画面カルーセル3

    @Column(name = "store_intro", columnDefinition = "TEXT")
    private String storeIntro; // 店舗紹介，店铺介绍，可以是长文本

    @Column(name = "cats_intro", columnDefinition = "TEXT")
    private String catsIntro; // 猫たちの紹介，猫的介绍

    @Column(name = "cats_adoption", columnDefinition = "TEXT")
    private String catsAdoption; // 猫の里親募集，猫的领养请求

    @Column(name = "cats_family", columnDefinition = "TEXT")
    private String catsFamily; // 猫の家族，猫的家庭

    @Column(name = "blog", columnDefinition = "TEXT")
    private String blog; // ブログ，博客内容


    public String getCarousel1() {
        return carousel1;
    }

    public void setCarousel1(String carousel1) {
        this.carousel1 = carousel1;
    }

    public String getCarousel2() {
        return carousel2;
    }

    public void setCarousel2(String carousel2) {
        this.carousel2 = carousel2;
    }

    public String getCarousel3() {
        return carousel3;
    }

    public void setCarousel3(String carousel3) {
        this.carousel3 = carousel3;
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
}
