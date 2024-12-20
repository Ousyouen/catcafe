package com.example.demo.model;

public class AboutInfo extends BaseModel {

	private String aboutTitle; // タイトル（タイトルの内容を保存）
	private String aboutDescription; // 説明（Aboutページの説明文）

	private String teamTitle; // チームタイトル（チームセクションのタイトル）
	private String teamMembers; // チームメンバー情報
	private String catFamilyDescription; // 猫ファミリーの説明文


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

	public String getTeamTitle() {
		return teamTitle;
	}

	public void setTeamTitle(String teamTitle) {
		this.teamTitle = teamTitle;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getCatFamilyDescription() {
		return catFamilyDescription;
	}

	public void setCatFamilyDescription(String catFamilyDescription) {
		this.catFamilyDescription = catFamilyDescription;
	}

	// Getters and Setters

}
