package com.example.object;

public class Duanzi {
	private String userName;
	private String cai;
	private String zan;
	private String content;
	private String imageUrl;
	
	public Duanzi(String imageUrl,String name, String cai, String zan, String content){
		this.imageUrl = imageUrl;
		this.userName = name;
		this.cai = cai;
		this.zan = zan;
		this.content = content;
		
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCai() {
		return cai;
	}
	public void setCai(String cai) {
		this.cai = cai;
	}
	public String getZan() {
		return zan;
	}
	public void setZan(String zan) {
		this.zan = zan;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
