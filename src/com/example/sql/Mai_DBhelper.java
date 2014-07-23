package com.example.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Mai_DBhelper extends SQLiteOpenHelper{

	public static String tag = "Mai_DBhelper";
	public static final String DATABASE_NAME_DUANZI = "duanzi_list_info";
	public static final String DATABASE_NAME_PUBLISH = "user_post";
	private static Mai_DBhelper instance = null;
	private static Context mContext;
	
	private static final String DB_CREATE_DUANZI_LIST_INFO = ""
			+ "CREATE TABLE IF NOT EXISTS duanzi_list_info("
			+ "id integer primary key,"
			+ "pid int,"						//段子ID
			+ "tag int,"						//段子类型	1：段子——热门、2：段子——最新、3图片——热门、图片——最新
			+ "zan_count int,"				//赞的次数
			+ "cai_count int,"				//踩的次数
			+ "comment_count int,"			//评论条数
			+ "boo_zan int,"				//用户是否赞过	1 yes	0 no
			+ "boo_cai int,"				//用户是否踩过	1 yes	0 no
			+ "boo_fav int,"				//用户是否收藏过	1 yes	0 no	
			+ "user_icon varchar,"		//发表该条段子的用户头像 
			+ "user_name varchar,"			//发表该条段子的用户昵称
			+ "user_id int,"						//发表该条段子的用户ID
			+ "content varchar,"				//段子内容
			+ "imgurl varchar"				//图片url
			+ ")";
	
	public static synchronized Mai_DBhelper getInstance(Context context){
		if (instance == null) {
			instance = new Mai_DBhelper(context, DATABASE_NAME_DUANZI, null, 1);
		}
		mContext = context;
		return instance;
	}
	
	public Mai_DBhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(DB_CREATE_DUANZI_LIST_INFO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_CREATE_DUANZI_LIST_INFO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 段子数据插入
	 * @param pid	
	 * @param content	
	 * @param imgurl
	 * @param cai
	 * @param zan
	 * @param comment
	 * @param userName
	 * @param iconUrl
	 * @param user_id
	 * @return
	 */
	public boolean insertDuanziInfo(int pid, String content, String imgurl, int cai, int zan, int comment
			,String userName, String iconUrl, int user_id ){
		SQLiteDatabase db = getWritableDatabase();
		try {
			if (DuanziExists(pid)) {
				return false;
			}
			ContentValues cv = new ContentValues();
			cv.put("pid", pid);
			cv.put("content", content);
			cv.put("imgurl", imgurl);
			cv.put("cai_count", cai);
			cv.put("zan_count", zan);
			cv.put("comment_count", comment);
			cv.put("user_icon", iconUrl);
			cv.put("user_name", userName);
			cv.put("user_id", user_id);
			cv.put("boo_cai", 0);
			cv.put("boo_zan", 0);
			cv.put("boo_fav", 0);
			cv.put("tag", 1);
			db.insert(DATABASE_NAME_DUANZI, "ID", cv);
			db.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			db.close();
			return true;
		}
	}
	
	public boolean DuanziExists(int pid){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from duanzi_list_info where pid = ?", new String[]{String.valueOf(pid)});
		try {
			if (cursor.moveToFirst()) {
				Log.e(tag, "存在");
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return true;
		}
		
	}
}
