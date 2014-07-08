package com.example.object;

import java.io.Serializable;

public class Duanzi implements Serializable{
	private String userName;
	private String cai;
	private String zan;
	private String content;
	private String imageUrl;
	private String comment;
	private String poid;
	
	public Duanzi(String imageUrl,String name, String cai, String zan, String content, String comment, String poid){
		this.imageUrl = imageUrl;
		this.userName = name;
		this.cai = cai;
		this.zan = zan;
		this.content = content;
		this.comment = comment;
		this.poid = poid;
	}
	
	
	
	public String getPoid() {
		return poid;
	}



	public void setPoid(String poid) {
		this.poid = poid;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
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
