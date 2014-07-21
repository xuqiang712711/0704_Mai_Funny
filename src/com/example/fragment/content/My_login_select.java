package com.example.fragment.content;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.application.MaimobApplication;
import com.example.fragment.Tab_My_Frag_New;
import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;

public class My_login_select extends Fragment{
	View view;
	private UMSocialService mController;
	public static  int sina = 0;
	public static  int tencent =1;
	public static  int renren = 2;
	public static  int douban =3;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_login_select, container, false)
;		return view;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mFragmentManage.RefreshFrag(getActivity(), mFragmentManage.Tag_My);
		Log.e("FFF", "ondestory");
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mController = MaimobApplication.mController;
		ImageView imageView = (ImageView)view.findViewById(R.id.my_select_sina);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.SINA, sina);
			}
		});
		view.findViewById(R.id.my_select_tencent).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.TENCENT, tencent);
			}
		});
		view.findViewById(R.id.my_select_renren).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.RENREN, renren);
			}
		});
		view.findViewById(R.id.my_select_douban).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.DOUBAN, douban);
			}
		});
	}
	
	private void doOauth(SHARE_MEDIA media, final int num){
		mController.doOauthVerify(getActivity(), media, new UMAuthListener() {
			
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
                    Toast.makeText(getActivity(), "授权成功." + platform, Toast.LENGTH_SHORT).show();
                    mOauth.editOauth(getActivity(), num);
                    getUserInfo(platform);
                } else {
                    Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();
                }
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//将用户信息保存到sp,icon、name等信息将使用第一次用户绑定的社交账号信息
	private void getUserInfo(SHARE_MEDIA platform){
		mController.getPlatformInfo(getActivity(), platform, new UMDataListener() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(int status, Map<String, Object> info) {
				// TODO Auto-generated method stub
				if(status == 200 && info != null){
					 SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
	                 Editor editor = sp.edit();
	                 StringBuilder sb = new StringBuilder();
	                 Set<String> keys = info.keySet();
	                 for(String key : keys){
	                     sb.append(key+"="+info.get(key).toString()+"\r\n");
	                     if (key.equals("description")) {
							editor.putString("description", info.get(key).toString());
						}else if (key.equals("screen_name")) {
							editor.putString("name", info.get(key).toString());
						}else if (key.equals("profile_image_url")) {
							editor.putString("icon", info.get(key).toString());
						}
	                 }
	                 Log.d("TestData",sb.toString());
	                 editor.commit();
	                 mFragmentManage.BackStatck(getActivity());
	          }else{
	             Log.d("TestData","发生错误："+status);
	          }
			}
		});
	}
}
