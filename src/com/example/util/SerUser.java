package com.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerUser {
	private static MyLogger myLogger = MyLogger.jLog();
	/**
	 * 序列化对象
	 * @param user
	 * @return
	 * @throws IOException
	 */
	public static String serializeUser(User user) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream objectOs = new ObjectOutputStream(bos);
		objectOs.writeObject(user);
		String serStr = bos.toString("ISO-8859-1");
		serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		objectOs.close();
		bos.close();
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
	public static User deSerializationUser(String str) throws IOException, ClassNotFoundException{
		String redStr = java.net.URLDecoder.decode(str, "UTF-8");
		ByteArrayInputStream bis = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
		ObjectInputStream objectIs = new ObjectInputStream(bis);
		User user = (User) objectIs.readObject();
		objectIs.close();
		bis.close();
		return user;
	}
}
