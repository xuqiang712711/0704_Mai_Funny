package com.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;

import com.example.object.User;

public class SerUser {
	private static MyLogger myLogger = MyLogger.jLog();
	/**
	 * 序列化对象
	 * @param user
	 * @return
	 * @throws IOException
	 */
	public static String serializeUser(User user){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream objectOs;
		String serStr = null;
		try {
			objectOs = new ObjectOutputStream(bos);
			objectOs.writeObject(user);
			serStr = bos.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
			objectOs.close();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myLogger.i("serialize str = " + serStr);
		return serStr;
	}
	/**
	 * 反序列化对象
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User deSerializationUser(String str){
		String redStr;
		User user = null;
		try {
			redStr = java.net.URLDecoder.decode(str, "UTF-8");
			ByteArrayInputStream bis = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
			ObjectInputStream objectIs = new ObjectInputStream(bis);
			user = (User) objectIs.readObject();
			objectIs.close();
			bis.close();
		}  catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
