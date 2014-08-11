package com.example.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.example.application.MaimobApplication;
import com.example.sql.Mai_DBhelper;
import com.example.util.MyLogger;

public class setDuanziData {
	private static String Tag = "setDuanziData";
	
//	private static List<Duanzi> list = new ArrayList<Duanzi>();
	public static List<Duanzi> getListDuanzi(String json,Context context, List<Duanzi> list,int tag){
		if (list == null) {
			list = new  ArrayList<Duanzi>();
		}
		try {
			JSONArray array = new JSONArray(json);
			boolean isNew = false;
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				String name = item.getString("nick");//昵称
				String zan = item.getString("zan");//赞——个数
				String cai = item.getString("cai");//踩——个数
				String content = item.getString("content");//内容
				String imageUrl = item.getString("img");//图片链接
				String comment = item.getString("comment");//评论数
				String poid = item.getString("poid");//段子ID
				String user_id = item.getString("pid");
				Duanzi duanzi = new Duanzi(imageUrl ,name,null, cai, zan, content, comment,poid,false, false , 0,false, false,0l);
//				list.add(duanzi);
				Mai_DBhelper dBhelper = Mai_DBhelper.getInstance(context);
				isNew = dBhelper.insertDuanziInfo(Integer.parseInt(poid), content, imageUrl, Integer.parseInt(cai), Integer.parseInt(zan)
						, Integer.parseInt(comment), name, null, Integer.parseInt(user_id), tag);
				MyLogger.jLog().i("插入段子  " + duanzi.toString() +"  isNew  " + isNew);
				if (isNew) {
					list.add(0, duanzi);
				}
			}
			return new ArrayList<Duanzi>(new LinkedHashSet<Duanzi>(list));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Duanzi>(new LinkedHashSet<Duanzi>(list));
	}
}
