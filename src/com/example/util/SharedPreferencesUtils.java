package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences��һ��������
 * ����setParam���ܱ���String, Integer, Boolean, Float, Long���͵Ĳ���
 * ����getParam���ܻ�ȡ���������ֻ����������
 *
 */
public class SharedPreferencesUtils {
	
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
