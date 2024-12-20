package com.example.demo.model;

public class CatInfoMst extends BaseModel {//共通クラスの継承
	
	private String catName; // 猫の名前

	private String catIntro; // 猫の紹介
	
	private int catAge;//猫の年齢

	private Integer likesCount; // 猫のいいね数

	private Long osusumeFlg; // おすすめFLG  1：おすすめ  0：

	private String catImage; // 猫の画像URL

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

	public int getCatAge() {
		return catAge;
	}

	public void setCatAge(int catAge) {
		this.catAge = catAge;
	}

}
