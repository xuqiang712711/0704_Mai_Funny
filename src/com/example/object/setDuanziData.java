package com.example.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.nfc.Tag;
import android.util.Log;

import com.example.application.MaimobApplication;

public class setDuanziData {
	private static String Tag = "setDuanziData";
	public static List<Duanzi> getListDuanzi(String json){
		List<Duanzi> list = new ArrayList<Duanzi>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				String name = item.getString("nick");
				String zan = item.getString("zan");
				String cai = item.getString("cai");
				String content = item.getString("content");
				String imageUrl = item.getString("img");
				Log.i(Tag, "name " + name + "  content  " +content);
				Duanzi duanzi = new Duanzi(imageUrl ,name, cai, zan, content);
				list.add(duanzi);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
