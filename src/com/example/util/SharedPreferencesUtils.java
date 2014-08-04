package com.example.util;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences��һ��������
 * ����setParam���ܱ���String, Integer, Boolean, Float, Long���͵Ĳ���
 * ����getParam���ܻ�ȡ���������ֻ����������
 *
 */
public class SharedPreferencesUtils {
	
	public static String maxID = "maxID";
	public static String maxID_DZ_HOT = "DZ_HOT";
	public static String maxID_DZ_NEW = "DZ_NEW";
	public static String maxID_IMG_HOT = "IMG_HOT";
	public static String maxID_IMG_NEW = "IMG_NEW";
	
	public static String setting = "setting";
	public static String setting_isZhuanfa = "isZhuanfa";
	public static String setting_font = "font";
	public static String user = "user";
	public static String user_name = "name";
	public static String user_icon = "icon";
	public static String user_description = "description";
	public static String user_location = "location";
	public static String user_gender = "gender";
	
	public static String platform = "platform";
	public static String platform_Exists = "Exists";
	public static String platform_sina	= "sina";
	public static String platform_tencent	= "tencent";
	public static String platform_renren	= "renren";
	public static String platform_douban	= "douban";
	
	public static String SerUser	= "SerUser";
	public static String SerUser_user	= "user";
	
	
	/**
	 * �������ݵķ�����������Ҫ�õ��������ݵľ������ͣ�Ȼ��������͵��ò�ͬ�ı��淽��
	 * @param context
	 * @param key
	 * @param object 
	 */
	public static void setParam(String fileName, Context context , String key, Object object){
		
		String type = object.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		
		if("String".equals(type)){
			editor.putString(key, (String)object);
		}
		else if("Integer".equals(type)){
			editor.putInt(key, (Integer)object);
		}
		else if("Boolean".equals(type)){
			editor.putBoolean(key, (Boolean)object);
		}
		else if("Float".equals(type)){
			editor.putFloat(key, (Float)object);
		}
		else if("Long".equals(type)){
			editor.putLong(key, (Long)object);
		}
		
		editor.commit();
	}
	
	
	/**
	 * �õ��������ݵķ��������Ǹ���Ĭ��ֵ�õ���������ݵľ������ͣ�Ȼ���������ڵķ�����ȡֵ
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object getParam(String fileName,Context context , String key, Object defaultObject){
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		
		if("String".equals(type)){
			return sp.getString(key, (String)defaultObject);
		}
		else if("Integer".equals(type)){
			return sp.getInt(key, (Integer)defaultObject);
		}
		else if("Boolean".equals(type)){
			return sp.getBoolean(key, (Boolean)defaultObject);
		}
		else if("Float".equals(type)){
			return sp.getFloat(key, (Float)defaultObject);
		}
		else if("Long".equals(type)){
			return sp.getLong(key, (Long)defaultObject);
		}
		
		return null;
	}
	
	
}
