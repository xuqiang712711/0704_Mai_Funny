package com.example.object;

import com.example.application.MaimobApplication;
import com.example.util.SharedPreferencesUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.exception.SocializeException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
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
	                    Message msg = Message.obtain();
	                    msg.what = 1;
	                    mHandler.sendMessage(msg);
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
}
