package com.example.object;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.application.MaimobApplication;
import com.example.util.MyLogger;
import com.example.util.PopUtils;
import com.example.util.SerUser;
import com.example.util.SharedPreferencesUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
/**
 * 平台账号绑定、注销
 * @author xieyifan
 *
 */
public class mOauth {
	private static UMSocialService mController = MaimobApplication.mController;
		
		/**
		 * 注销账号
		 * @param context
		 */
		public static void delOauth(Context context){
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.platform, context, SharedPreferencesUtils.platform_Exists, false);
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.platform, context, SharedPreferencesUtils.platform_sina, 0);
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.platform, context, SharedPreferencesUtils.platform_tencent, 0);
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.platform, context, SharedPreferencesUtils.platform_renren, 0);
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.platform, context, SharedPreferencesUtils.platform_douban, 0);
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, context, SharedPreferencesUtils.user_name, "");
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, context, SharedPreferencesUtils.user_description, "");
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, context, SharedPreferencesUtils.user_icon, "");
		}
		
		public static void deletePlatform(final Context context,SHARE_MEDIA media, final Handler mHandler){
			mController.deleteOauth(context, media, new SocializeClientListener() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(int arg0, SocializeEntity arg1) {
					// TODO Auto-generated method stub
					MyLogger.jLog().i("code  " +arg0);
					if (arg0 == 200) {
						PopUtils.toastShow(context, "删除成功");
						if (mHandler != null) {
							Message msg = Message.obtain();
							msg.what = 1;
							mHandler.sendMessage(msg);
						}
					}else {
						PopUtils.toastShow(context, "删除失败,稍后请重试");
					}

				}
			});
		}
	
		//将账号是否绑定信息保存到sp
		public static void editOauth(Context context, int platform) {
			SharedPreferences sp = context.getSharedPreferences("platform", Activity.MODE_PRIVATE);
			Editor editor = sp.edit();
			switch (platform) {
			case 0://sina
				editor.putInt("sina", 1);
				break;
			case 1://tencent
				editor.putInt("tencent", 1);
				break;
			case 2://renren
				editor.putInt("renren", 1);
				break;
			case 3://douban
				editor.putInt("douban", 1);
				break;
			}
			editor.putBoolean("Exists", true);//true		存在平台账号		false	不存在平台账号
			editor.commit();
		}
		
		public static void doOauth(final Context context,SHARE_MEDIA media,final int num ,final Handler mHandler){
			mController.doOauthVerify(context, media, new UMAuthListener() {
				
				@Override
				public void onStart(SHARE_MEDIA arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
					// TODO Auto-generated method stub

				}
				
				@Override
				public void onComplete(Bundle value, SHARE_MEDIA platform) {
					// TODO Auto-generated method stub
					if (value != null && !TextUtils.isEmpty(value.getString("uid"))) {
	                    Toast.makeText(context, "授权成功." + platform, Toast.LENGTH_SHORT).show();
	                    editOauth(context, num);

	                    String user = (String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.SerUser, context,
	                            SharedPreferencesUtils.SerUser_user, "");
	                    MyLogger.jLog().i("user  " + user +"   isNUll  "+ user.equals(""));
	                    if (user.equals("")) {
                    			getUserInfo(context, platform, mHandler);
                    			mFragmentManage.Refresh_userInfo = true;
						}else {
							if (mHandler != null) {
								Message msg = Message.obtain();
								msg.what = 1;
								mHandler.sendMessage(msg);
							}
						}
	                } else {
	                    Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
	                }
				}
				
				@Override
				public void onCancel(SHARE_MEDIA arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		//将用户信息保存到sp,icon、name等信息将使用第一次用户绑定的社交账号信息
		public static void getUserInfo(final Context context ,SHARE_MEDIA platform ,final Handler mHandler){
			mController.getPlatformInfo(context, platform, new UMDataListener() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					MyLogger.jLog().i("onStart");
				}
				
				@Override
				public void onComplete(int status, Map<String, Object> info) {
					// TODO Auto-generated method stub
					if(status == 200 && info != null){
						MyLogger.jLog().i("onComplete");
						 SharedPreferences sp = context.getSharedPreferences("user", Activity.MODE_PRIVATE);
		                 Editor editor = sp.edit();
		                 StringBuilder sb = new StringBuilder();
		                 Set<String> keys = info.keySet();
		                 int gender = 0;
		                 String name = null;
		                 String icon = null;
		                 String location = null;
		                 String description = null;
		                 for(String key : keys){
		                     sb.append(key+"="+info.get(key).toString()+"\r\n");
		                     if (key.equals("description")) {
		                    	 description = info.get(key).toString();
								editor.putString("description", description);
							}else if (key.equals("screen_name")) {
								name = info.get(key).toString();
								editor.putString("name", name);
							}else if (key.equals("profile_image_url")) {
								icon = info.get(key).toString();
								editor.putString("icon", icon);
							}else if (key.equals("location")) {
								location = info.get(key).toString();
								editor.putString("location", location);
							}else if (key.equals("gender")) {
								gender = (Integer) info.get(key);
								editor.putInt("gender", gender);
							}
		                 }
		                 editor.commit();
		                 User user= new User(name, icon, location, description, gender);
		                	 user.saveUser(context, SerUser.serializeUser(user));
		                	 Message msg = Message.obtain();
		                	 msg.what = 1;
		                	 mHandler.sendMessage(msg);
		          }else{
		             Log.d("TestData","发生错误："+status);
		          }
				}
			});
		}
		
		
}
