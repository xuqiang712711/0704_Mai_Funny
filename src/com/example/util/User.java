package com.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;

import android.content.Context;

public class User implements Serializable{
	private static MyLogger myLogger = MyLogger.jLog();
	private String name;
	private String icon;
	private String location;
	private int gender;
	private String description;
	
	public User(){
		
	}
	
	public User(String name, String icon, String location, String description, int gender){
		this.name = name;
		this.icon = icon;
		this.location = location;
		this.gender = gender;
		this.description = description;
	}
	
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User[name=" + name + ",icon=" +icon + ",location=" +location + ",description=" + description + ",gender="+gender + "]";
	}




	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	public int getGender() {
		return gender;
	}



	public void setGender(int gender) {
		this.gender = gender;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

	public void saveUser(Context context,String str){
		SharedPreferencesUtils.setParam("SerUser", context, "user", str);
	}
	
	//��ѯ�Ƿ�����û�
	public static boolean UserIsExists(Context context){
		return (Boolean) SharedPreferencesUtils.getParam("platform", context, "Exists", false);
	}
	//��ȡ�Ա�
	public static String decideGender(Context context){
		int gender = (Integer)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, context, SharedPreferencesUtils.user_gender, 1);
		MyLogger.jLog().i("gender  " +gender);
//		return gender == 1?"��":"Ů";
		return "xxx";
	}
}