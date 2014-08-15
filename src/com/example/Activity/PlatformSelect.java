package com.example.Activity;

import com.example.application.MaimobApplication;
import com.example.fragment.Tab_My_Frag_New;
import com.example.fragment.content.DuanZi_Comment_Write;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.tab.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class PlatformSelect extends BaseActivity{
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
	int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_login_select);
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			type= bundle.getInt("xwkkx");
		}
		if (type == From_Duanzi) {
			duanzi = (Duanzi) bundle.getSerializable("duanzi");
		}
		
		
		TextView title = (TextView)findViewById(R.id.top_text);
		title.setText("Æ½Ì¨°ó¶¨");
		ImageView back = (ImageView)findViewById(R.id.top_left_change);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type == From_Write) {
//					mFragmentManage.backHome(this, mFragmentManage.BACK_HOME);
					startActivity(new Intent(PlatformSelect.this, MaiActivity.class));
				}else {
//					mFragmentManage.BackStatck(this);
					finish();
				}
			}
		});
		TextView bt_right = (TextView)findViewById(R.id.top_right_change2);
		bt_right.setVisibility(View.GONE);
		mController = MaimobApplication.mController;
		ImageView imageView = (ImageView)findViewById(R.id.my_select_sina);
		RelativeLayout.LayoutParams p1 = (LayoutParams) imageView.getLayoutParams();
		p1.setMargins(MaimobApplication.DeviceW/4, 40, 40, 40);
		imageView.setLayoutParams(p1);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				doOauth(SHARE_MEDIA.SINA, sina);
				mOauth.doOauth(PlatformSelect.this, SHARE_MEDIA.SINA, 0, handler);
			}
		});
		ImageView iv_2 = (ImageView)findViewById(R.id.my_select_tencent);
		RelativeLayout.LayoutParams p2 = (LayoutParams) iv_2.getLayoutParams();
		p2.setMargins(40, 40, MaimobApplication.DeviceW/4, 40);
		iv_2.setLayoutParams(p2);
		iv_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOauth.doOauth(PlatformSelect.this, SHARE_MEDIA.TENCENT, 1, handler);
			}
		});
		ImageView iv_3 = (ImageView)findViewById(R.id.my_select_douban);
		RelativeLayout.LayoutParams p3 = (LayoutParams) iv_3.getLayoutParams();
		p3.setMargins(MaimobApplication.DeviceW/4, 40, 40, 40);
		iv_3.setLayoutParams(p3);
		iv_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOauth.doOauth(PlatformSelect.this, SHARE_MEDIA.DOUBAN, 3, handler);
			}
		});
		
		ImageView iv_4 = (ImageView)findViewById(R.id.my_select_renren);
		RelativeLayout.LayoutParams p4 = (LayoutParams) iv_4.getLayoutParams();
		p4.setMargins(40, 40, MaimobApplication.DeviceW/4, 40);
		iv_4.setLayoutParams(p4);
		iv_4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOauth.doOauth(PlatformSelect.this, SHARE_MEDIA.RENREN, 2, handler);
			}
		});
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
//			if (type == From_Write) {
//				mFragmentManage.backHome(this,
//						mFragmentManage.BACK_WRITE);
//			} else if (type == From_Duanzi) {
//				mFragmentManage.switch_write = true;
//				mFragmentManage.BackStatck(getActivity());
//			} else if (type == From_My) {
//				mFragmentManage.Refresh_userInfo = true;
//				mFragmentManage.BackStatck(getActivity());
//			}else {
//				mFragmentManage.BackStatck(getActivity());
////				mFragmentManage.SwitchFrag(getActivity(), My_login_select.this, new Duanzi_Pop_Zhuanfa(), bundle);
//				mFragmentManage.switch_write = true;
//			}
		}
	};
}
