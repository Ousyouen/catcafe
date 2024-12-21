package com.example.demo.model;

public class AboutInfo extends BaseModel {

    private String aboutTitle; // タイトル (タイトルの内容を保存)
    private String aboutDescription; // 説明 (Aboutページの説明文)
    private String catImageUrl; // 猫の画像URL
    private String catName; // 猫の名前
    private String catTip; // 注意事項
    private String catDescription; // 猫の詳細説明
    private String teamTitle; // チームタイトル (チームセクションのタイトル)
    private String teamUrl; // チーム画像URL
    private String teamDescription; // チームの説明
    private String teamMembers; // チームメンバー情報
    
    
	public String getAboutTitle() {
		return aboutTitle;
	}
	public void setAboutTitle(String aboutTitle) {
		this.aboutTitle = aboutTitle;
	}
	public String getAboutDescription() {
		return aboutDescription;
	}
	public void setAboutDescription(String aboutDescription) {
		this.aboutDescription = aboutDescription;
	}
	public String getCatImageUrl() {
		return catImageUrl;
	}
	public void setCatImageUrl(String catImageUrl) {
		this.catImageUrl = catImageUrl;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatTip() {
		return catTip;
	}
	public void setCatTip(String catTip) {
		this.catTip = catTip;
	}
	public String getCatDescription() {
		return catDescription;
	}
	public void setCatDescription(String catDescription) {
		this.catDescription = catDescription;
	}
	public String getTeamTitle() {
		return teamTitle;
	}
	public void setTeamTitle(String teamTitle) {
		this.teamTitle = teamTitle;
	}
	public String getTeamUrl() {
		return teamUrl;
	}
	public void setTeamUrl(String teamUrl) {
		this.teamUrl = teamUrl;
	}
	public String getTeamDescription() {
		return teamDescription;
	}
	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
	}
	public String getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}
    
    
    
}
