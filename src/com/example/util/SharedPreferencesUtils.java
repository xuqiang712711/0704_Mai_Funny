package com.example.util;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的一个工具类
 * 调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 调用getParam就能获取到保存在手机里面的数据
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
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
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
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
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
