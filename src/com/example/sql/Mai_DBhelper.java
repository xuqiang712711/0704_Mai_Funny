package com.example.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.object.Duanzi;
import com.example.util.MyLogger;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.example.util.User;

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
	public static final String DATABASE_NAME_PUBLISH = "user_publish";
	public static final String DATABASE_NAME_COMMENT = "user_comment";
	private static Mai_DBhelper instance = null;
	private static Context mContext;
	private static Cursor mCursor;
	private static SQLiteDatabase db;
	
	public static final String DB_CREATE_DUANZI_LIST_INFO = ""
			+ "CREATE TABLE IF NOT EXISTS duanzi_list_info("
			+ "id integer primary key,"
			+ "pid int,"						//段子ID
			+ "tag int,"						//段子类型	1：段子——热门、2：段子——最新、3图片——热门、4:图片——最新
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
			+ "imgurl varchar,"				//图片url
			+ "favTime long"				//被收藏的时间
			+ ")";
	
	public static final String DB_CREATE_USER_PUBLISH = "CREATE TABLE IF NOT EXISTS " +DATABASE_NAME_PUBLISH 
			+ "(id integer primary key,"
			+ "content varchar,"				//文字内容
			+ "imgurl varchar,"				//图片链接
			+ "pubTime long"					//投稿时间
			+ ")";
	
	public static final String DB_CREATE_USER_COMMENT ="CREATE TABLE IF NOT EXISTS "+ DATABASE_NAME_COMMENT
			+ "(id integer primary key,"
			+ "content varchar,"
			+ "pid int,"
			+ "name varchar,"
			+ "icon varchar,"
			+ "comment varchar,"
			+ "time long,"
			+ "dz_user_name varchar,"
			+ "imgurl varchar"
			+ ")";
	
	/**
	 * 获得db的单例
	 * @param context
	 * @return
	 */
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
		db.execSQL(DB_CREATE_USER_PUBLISH);
		db.execSQL(DB_CREATE_USER_COMMENT);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_CREATE_DUANZI_LIST_INFO);
		db.execSQL(DB_CREATE_USER_PUBLISH);
		db.execSQL(DB_CREATE_USER_COMMENT);
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
			,String userName, String iconUrl, int user_id ,int tag){
		db = getWritableDatabase();
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
			cv.put("tag", tag);
			db.insert(DATABASE_NAME_DUANZI, "ID", cv);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}finally{
		}
	}
	
	/**
	 * 插入评论
	 * @param content
	 * @param pid
	 * @return
	 */
	public boolean insertUser_Comment(String dz_user_name,String content, int pid, String comment, User user, String imgUrl){
		db = getWritableDatabase();
		try {
			ContentValues cv = new ContentValues();
			cv.put("imgurl", imgUrl);
			cv.put("dz_user_name", dz_user_name);
			cv.put("content", content);
			cv.put("pid", pid);
			cv.put("comment", comment);
			cv.put("time", System.currentTimeMillis());
			cv.put("name", user.getName());
			cv.put("icon", user.getIcon());
			db.insert(DATABASE_NAME_COMMENT, "id", cv);
			db.execSQL("update " + DATABASE_NAME_DUANZI + " set comment_count = comment_count + 1 where pid = ?", new String[]{String.valueOf(pid)});
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			MyLogger.jLog().i("insertUser_Comment    error");
			return false;
		}
	}
	/**
	 * 获得用户评论和段子内容
	 * @return
	 */
	public List<Map<String, Object>> selectComment(){
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		db = getReadableDatabase();
		try {
			mCursor = db.rawQuery("select * from "+DATABASE_NAME_COMMENT + " order by time desc", new String[]{});
			if (mCursor.moveToFirst()) {
				do {
					String imgUrl = mCursor.getString(mCursor.getColumnIndex("imgurl"));
					String icon_url = mCursor.getString(mCursor.getColumnIndex("icon"));
					String name = mCursor.getString(mCursor.getColumnIndex("name"));
					String comment = mCursor.getString(mCursor.getColumnIndex("comment"));
					String content = mCursor.getString(mCursor.getColumnIndex("content"));
					String dz_user_name = mCursor.getString(mCursor.getColumnIndex("dz_user_name"));
					int pid		  = mCursor.getInt(mCursor.getColumnIndex("pid"));
					long time = mCursor.getLong(mCursor.getColumnIndex("time"));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pid", pid);
					map.put("content", content);
					map.put("name", name);
					map.put("comment", comment);
					map.put("icon", icon_url);
					map.put("time", StringUtils.getTime(time));
					map.put("dz_user_name", dz_user_name);
					map.put("imgurl", imgUrl);
					data.add(map);
				} while (mCursor.moveToNext());
			}
			return data;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "selectComment     error");
			return data;
		}finally{
			if (mCursor != null) {
				mCursor.close();
			}
		}
	}
	
	public List<Map<String, Object>> selectComFromID(int id){
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		db = getReadableDatabase();
		try {
			mCursor = db.rawQuery("select * from "+DATABASE_NAME_COMMENT + " where pid = ? order by time desc ", new String[]{String.valueOf(id)});
			if (mCursor.moveToFirst()) {
				do {
					String icon_url = mCursor.getString(mCursor.getColumnIndex("icon"));
					String name = mCursor.getString(mCursor.getColumnIndex("name"));
					String comment = mCursor.getString(mCursor.getColumnIndex("comment"));
					String content = mCursor.getString(mCursor.getColumnIndex("content"));
					int pid		  = mCursor.getInt(mCursor.getColumnIndex("pid"));
					long time = mCursor.getLong(mCursor.getColumnIndex("time"));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pid", pid);
					map.put("content", content);
					map.put("name", name);
					map.put("comment", comment);
					map.put("icon", icon_url);
					map.put("time", StringUtils.getTime(time));
					data.add(map);
				} while (mCursor.moveToNext());
			}
			return data;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "selectComment     error");
			return data;
		}finally{
			if (mCursor != null) {
				mCursor.close();
			}
		}
	}
	
	/**
	 * 插入用户投稿、仅限本地、无同步
	 * @param user_id
	 * @param user_name
	 * @param content
	 * @param imgurl
	 * @return
	 */
	public boolean insertUser_Publish(String content, String imgurl){
		db = getWritableDatabase();
		try {
			ContentValues cv = new ContentValues();
			cv.put("content", content);
			if (imgurl == null) {
				cv.put("imgurl", "");
			}
			cv.put("imgurl", imgurl);
			cv.put("pubTime", System.currentTimeMillis());
			db.insert(DATABASE_NAME_PUBLISH, "id", cv);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "insertUser_Publish error");
			return false;
		}
	}
	
	/**
	 * 查询用户投稿
	 * @return
	 */
	public List<Duanzi> selectPub(){
		db = getReadableDatabase();
		List<Duanzi> duanzi_list = new ArrayList<Duanzi>();
		try {
			mCursor = db.rawQuery("select * from "+ DATABASE_NAME_PUBLISH +" order by pubTime desc", new String[]{});
			if (mCursor.moveToFirst()) {
				do {
					String content = mCursor.getString(mCursor.getColumnIndex("content"));
					String content_imgurl = mCursor.getString(mCursor.getColumnIndex("imgurl"));
					String name = (String) SharedPreferencesUtils.getParam("user", mContext, "name", "");
					String icon_imgurl = (String) SharedPreferencesUtils.getParam("user", mContext, "icon", "");
					Duanzi duanzi = new Duanzi(content_imgurl, name, icon_imgurl,"0", "0",content, "0", "0", false, false, 0, false, false,0l);
					duanzi_list.add(duanzi);
				} while (mCursor.moveToNext());
			}else {
				Log.e(tag, "selectPub no data");
			}
			return duanzi_list;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(tag, "selectPub erroe");
			return null;
		}finally{
			if (mCursor != null) {
				mCursor.close();
			}
		}
	}
	/**
	 * 查询段子是否已存在
	 * @param pid
	 * @return
	 */
	public boolean DuanziExists(int pid){
		db = getReadableDatabase();
		mCursor = db.rawQuery("select * from duanzi_list_info where pid = ?", new String[]{String.valueOf(pid)});
		try {
			if (mCursor.moveToFirst()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return true;
		}finally{
			if (mCursor != null) {
				mCursor.close();
			}
		}
		
	}
	
	/**
	 * 将段子标记为已收藏
	 * @param pid
	 * @return
	 */
	public boolean updateFav(int pid){
		db = getWritableDatabase();
		try {
			db.execSQL("update " + DATABASE_NAME_DUANZI + " set boo_fav = 1 where pid = ?", new String[]{String.valueOf(pid)});
			db.execSQL("update " + DATABASE_NAME_DUANZI + " set favTime = " + System.currentTimeMillis() + " where pid = ?",
					new String[]{String.valueOf(pid)});
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	/**
	 * 取消对段子的收藏
	 * @param pid
	 * @return
	 */
	public boolean cancelFav(int pid){
		db = getWritableDatabase();
		try {
			db.execSQL("update " + DATABASE_NAME_DUANZI + " set boo_fav = 0 where pid = ?", new String[]{String.valueOf(pid)});
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	/**
	 * 得到用户收藏的段子
	 * @return
	 */
	public List<Duanzi> selectFav(){
		db = getReadableDatabase();
		List<Duanzi> list_duanzi = new ArrayList<Duanzi>();
		mCursor = db.rawQuery("select * from "+ DATABASE_NAME_DUANZI + " where boo_fav = 1 order by favTime desc", new String[]{});
		try {
			if (mCursor.moveToFirst()) {
				do {
					String content = mCursor.getString(mCursor.getColumnIndex("content"));
					String imgUrl = mCursor.getString(mCursor.getColumnIndex("imgurl"));
					String userName = mCursor.getString(mCursor.getColumnIndex("user_name"));
					String userIcon = mCursor.getString(mCursor.getColumnIndex("user_icon"));
					int pid			= mCursor.getInt(mCursor.getColumnIndex("pid"));
					int zan			= mCursor.getInt(mCursor.getColumnIndex("zan_count"));
					int cai 			= mCursor.getInt(mCursor.getColumnIndex("cai_count"));
					int comment		= mCursor.getInt(mCursor.getColumnIndex("comment_count"));
					int userid		= mCursor.getInt(mCursor.getColumnIndex("user_id"));
					long favTime		= mCursor.getLong(mCursor.getColumnIndex("favTime"));
					Duanzi duanzi = new Duanzi(imgUrl, userName, userIcon,String.valueOf(cai), String.valueOf(zan), 
							content, String.valueOf(comment), String.valueOf(pid), false, false, 0, true, false,favTime);
					list_duanzi.add(duanzi);
				} while (mCursor.moveToNext());
			}else {
				Log.e(tag, "no found data");
			}
			return list_duanzi;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	/**
	 * 通过pid的到段子
	 * @param pid
	 * @return
	 */
	public Duanzi selectDuanzi(int pid){
		db = getReadableDatabase();
		Duanzi duanzi = null;
		mCursor = db.rawQuery("select * from " + DATABASE_NAME_DUANZI + " where pid = ?", new String[]{String.valueOf(pid)});
		try {
			if (mCursor.moveToFirst()) {
				do {
					String imgUrl = mCursor.getString(mCursor.getColumnIndex("imgurl"));
					String name 	  = mCursor.getString(mCursor.getColumnIndex("user_name"));
					String id 	  = mCursor.getString(mCursor.getColumnIndex("user_id"));
					String icon 	  = mCursor.getString(mCursor.getColumnIndex("user_icon"));
					String content 	  = mCursor.getString(mCursor.getColumnIndex("content"));
					int comment_count = mCursor.getInt(mCursor.getColumnIndex("comment_count"));
					int cai_count = mCursor.getInt(mCursor.getColumnIndex("cai_count"));
					int zan_count = mCursor.getInt(mCursor.getColumnIndex("zan_count"));
					duanzi = new Duanzi(imgUrl, name, icon, String.valueOf(cai_count), String.valueOf(zan_count), content, String.valueOf(comment_count),
							String.valueOf(pid), false, false, 0, false, false,0l);
				} while (mCursor.moveToNext());
			}
			MyLogger.jLog().i("try");
		} catch (Exception e) {
			// TODO: handle exception
			MyLogger.jLog().i("catch");
		}finally{
			if (mCursor !=null) {
				mCursor.close();
				MyLogger.jLog().i("mCursor");
			}
		}
		return duanzi;
		
	}
	/**
	 * 得到全部的段子
	 * @param tag	段子类型		文字：热门、最新		图片：热门、最新
	 * @return
	 */
	public List<Duanzi> selectALLDuanzi(int tag){
		db = getReadableDatabase();
		List<Duanzi> list = new ArrayList<Duanzi>();
		try {
			mCursor =  db.rawQuery("select * from " + DATABASE_NAME_DUANZI + " where tag = ? order by pid desc", new String[]{String.valueOf(tag)});
			if (mCursor.moveToFirst()) {
				do {
					int pid = mCursor.getInt(mCursor.getColumnIndex("pid"));
					int comment_count = mCursor.getInt(mCursor.getColumnIndex("comment_count"));
					int cai_count = mCursor.getInt(mCursor.getColumnIndex("cai_count"));
					int zan_count = mCursor.getInt(mCursor.getColumnIndex("zan_count"));
					String icon 	  = mCursor.getString(mCursor.getColumnIndex("user_icon"));
					String name 	  = mCursor.getString(mCursor.getColumnIndex("user_name"));
					String content 	  = mCursor.getString(mCursor.getColumnIndex("content"));
					String imgUrl = mCursor.getString(mCursor.getColumnIndex("imgurl"));
					int boo_Cai	= mCursor.getInt(mCursor.getColumnIndex("boo_cai"));
					int boo_Zan	= mCursor.getInt(mCursor.getColumnIndex("boo_zan"));
					int boo_Fav	= mCursor.getInt(mCursor.getColumnIndex("boo_fav"));
					Duanzi duanzi = new Duanzi(imgUrl, name, icon, String.valueOf(cai_count), String.valueOf(zan_count), content, 
							String.valueOf(comment_count), String.valueOf(pid), boo(boo_Zan), boo(boo_Cai), 0, boo(boo_Fav), false,0l);
					list.add(duanzi);
				} while (mCursor.moveToNext());
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			return list;
		}finally{
			if (mCursor != null) {
				mCursor.close();
			}
		}
	}
	/**
	 * 得到true、false
	 * @param tag	boo_zan、boo_cao、boo_fav
	 * @return
	 */
	public boolean boo(int tag){
		return tag == 0?false:true;
	}
	
	/**
	 * 更改赞状态
	 * @param pid
	 */
	public void updateZan(String pid){
		db = getWritableDatabase();
		db.execSQL("update " +DATABASE_NAME_DUANZI + " set boo_zan = 1 where pid = ?", new String[]{pid});
		db.execSQL("update " +DATABASE_NAME_DUANZI + " set zan_count = zan_count + 1 where pid = ?", new String[]{pid});
	}
	/**
	 * 更改踩状态
	 * @param pid
	 */
	public void updateCai(String pid){
		db = getWritableDatabase();
		db.execSQL("update " +DATABASE_NAME_DUANZI + " set boo_cai = 1 where pid = ?", new String[]{pid});
		db.execSQL("update " +DATABASE_NAME_DUANZI + " set cai_count = zan_count + 1 where pid = ?", new String[]{pid});
	}
	/**
	 * 更改评论条数
	 * @param pid
	 */
	public void updateComment(String pid){
		db = getWritableDatabase();
		db.execSQL("update " + DATABASE_NAME_DUANZI + " set comment_count = comment_count + 1 where pid = ?", new String[]{pid});
	}
}
