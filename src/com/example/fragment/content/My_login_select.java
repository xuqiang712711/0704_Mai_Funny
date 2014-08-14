package com.example.fragment.content;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.Activity.MaiActivity;
import com.example.application.MaimobApplication;
import com.example.fragment.Tab_My_Frag_New;
import com.example.object.Duanzi;
import com.example.object.User;
import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.tab.R;
import com.example.util.MyLogger;
import com.example.util.SerUser;
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
	private Bundle bundle;
	private int type;
	private int result_Code = 333;
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
		MyLogger.jLog().i("type  " + type);
		mFragmentManage.Refresh_userInfo = true;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		bundle =getArguments();
		type= bundle.getInt("xwkkx");
		if (type == From_Duanzi) {
			duanzi = (Duanzi) bundle.getSerializable("duanzi");
		}
		
		TextView title = (TextView)view.findViewById(R.id.top_text);
		title.setText("ƽ̨��");
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type == From_Write) {
					mFragmentManage.backHome(getActivity(), mFragmentManage.BACK_HOME);
				}else {
					mFragmentManage.BackStatck(getActivity());
				}
			}
		});
		TextView bt_right = (TextView)view.findViewById(R.id.top_right_change2);
		bt_right.setVisibility(View.GONE);
		mController = MaimobApplication.mController;
		ImageView imageView = (ImageView)view.findViewById(R.id.my_select_sina);
		RelativeLayout.LayoutParams p1 = (LayoutParams) imageView.getLayoutParams();
		p1.setMargins(MaimobApplication.DeviceW/4, 40, 40, 40);
		imageView.setLayoutParams(p1);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				doOauth(SHARE_MEDIA.SINA, sina);
				mOauth.doOauth(getActivity(), SHARE_MEDIA.SINA, 0, handler);
			}
		});
		ImageView iv_2 = (ImageView)view.findViewById(R.id.my_select_tencent);
		RelativeLayout.LayoutParams p2 = (LayoutParams) iv_2.getLayoutParams();
		p2.setMargins(40, 40, MaimobApplication.DeviceW/4, 40);
		iv_2.setLayoutParams(p2);
		iv_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOauth.doOauth(getActivity(), SHARE_MEDIA.TENCENT, 1, handler);
			}
		});
		ImageView iv_3 = (ImageView)view.findViewById(R.id.my_select_douban);
		RelativeLayout.LayoutParams p3 = (LayoutParams) iv_3.getLayoutParams();
		p3.setMargins(MaimobApplication.DeviceW/4, 40, 40, 40);
		iv_3.setLayoutParams(p3);
		iv_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.DOUBAN, douban);
			}
		});
		
		ImageView iv_4 = (ImageView)view.findViewById(R.id.my_select_renren);
		RelativeLayout.LayoutParams p4 = (LayoutParams) iv_4.getLayoutParams();
		p4.setMargins(40, 40, MaimobApplication.DeviceW/4, 40);
		iv_4.setLayoutParams(p4);
		iv_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doOauth(SHARE_MEDIA.RENREN, renren);
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
                    Toast.makeText(getActivity(), "��Ȩ�ɹ�." + platform, Toast.LENGTH_SHORT).show();
//                    mOauth.editOauth(getActivity(), num);
//                    getUserInfo(platform);
	                 if (type == From_Write) {
	                	 	mFragmentManage.backHome(getActivity(), mFragmentManage.BACK_WRITE);
					}else {
						mFragmentManage.BackStatck(getActivity());
					}
                } else {
                    Toast.makeText(getActivity(), "��Ȩʧ��", Toast.LENGTH_SHORT).show();
                }
			}
			
			@Override
			public void onCancel(SHARE_MEDIA arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	//���û���Ϣ���浽sp,icon��name����Ϣ��ʹ�õ�һ���û��󶨵��罻�˺���Ϣ
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
//	                 User user= new User();
//	                 user.setName(name);
//	                 user.setDescription(description);
//	                 user.setGender(gender);
//	                 user.setLocation(location);
//	                 user.setIcon(icon);
	                	 user.saveUser(getActivity(), SerUser.serializeUser(user));
	          }else{
	             Log.d("TestData","�������"+status);
	          }
			}
		});
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		}
	};
}
