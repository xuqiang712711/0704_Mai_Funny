package com.example.object;

import java.util.ArrayList;
import java.util.Iterator;
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

public class setDuanziData {
	private static String Tag = "setDuanziData";
//	private static List<Duanzi> list = new ArrayList<Duanzi>();
	public static List<Duanzi> getListDuanzi(String json,Context context, List<Duanzi> list){
		if (list == null) {
			list = new  ArrayList<Duanzi>();
		}
		try {
			JSONArray array = new JSONArray(json);
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
				Duanzi duanzi = new Duanzi(imageUrl ,name,null, cai, zan, content, comment,poid,false, false , 0,false, false);
//				list.add(duanzi);
				list.add(0, duanzi);
				Mai_DBhelper dBhelper = Mai_DBhelper.getInstance(context);
				dBhelper.insertDuanziInfo(Integer.parseInt(poid), content, imageUrl, Integer.parseInt(cai), Integer.parseInt(zan)
						, Integer.parseInt(comment), name, null, Integer.parseInt(user_id));
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
