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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.XYFTEST;
import com.example.application.MaimobApplication;
import com.example.fragment.Tab_My_Frag_New;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.tab.R;
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
	public static final int From_Duanzi = 10;
	public static final int From_My	= 11;
	public static final int From_Write = 12;
	private Tab_My_Frag_New my_Frag;
	private DuanZi_Comment_Write my_Write;
	private Duanzi duanzi;
	private boolean needRefresh = true;
	int type;
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
		if (type == From_My) {
			Log.e("FFF", "99999999");
			mFragmentManage.RefreshFrag(getActivity(), mFragmentManage.Tag_My);
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle =getArguments();
		type= bundle.getInt("xwkkx");
		Log.e("FFF", "xwkkx  " + type);
		if (type == From_Duanzi) {
			duanzi = (Duanzi) bundle.getSerializable("duanzi");
		}
		
		TextView title = (TextView)view.findViewById(R.id.top_text);
		title.setText("平台绑定");
		Button back = (Button)view.findViewById(R.id.top_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type == From_Write) {
					mFragmentManage.backHome(getActivity(), From_Write);
				}else {
					mFragmentManage.BackStatck(getActivity());
				}
			}
		});
		Button right = (Button)view.findViewById(R.id.top_right);
		right.setVisibility(View.GONE);
		mController = MaimobApplication.mController;
		ImageView imageView = (ImageView)view.findViewById(R.id.my_select_sina);
		RelativeLayout.LayoutParams p1 = (LayoutParams) imageView.getLayoutParams();
		p1.setMargins(MaimobApplication.DeviceW/4, 0, 0, 0);
		imageView.setLayoutParams(p1);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.SINA, sina);
			}
		});
		ImageView iv_2 = (ImageView)view.findViewById(R.id.my_select_tencent);
		RelativeLayout.LayoutParams p2 = (LayoutParams) iv_2.getLayoutParams();
		p2.setMargins(0, 0, MaimobApplication.DeviceW/4, 0);
		iv_2.setLayoutParams(p2);
		iv_2.setOnClickListener(new OnClickListener() {
			
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
	                 if (type == From_Write) {
	                	 	mFragmentManage.backHome(getActivity(), From_Write);
					}else {
						mFragmentManage.BackStatck(getActivity());
					}
	          }else{
	             Log.d("TestData","发生错误："+status);
	          }
			}
		});
	}
}
