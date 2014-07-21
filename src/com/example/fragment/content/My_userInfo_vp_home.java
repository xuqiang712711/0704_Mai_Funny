package com.example.fragment.content;

import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.tab.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMediaObject.MediaType;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class My_userInfo_vp_home extends Fragment implements OnClickListener{
	View view;
	private RelativeLayout renren, tencent_wb,sina,douban,name, sex, address;
	private int sinaStatus,tencentStatus,renrenStatus,doubanStatus;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo_vp_1, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		checkPlatformStatus();
	}
	
	private void initView(){
		
		renren = (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_renren);
		tencent_wb= (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_tencent_wb);
		sina = (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_sina);
		douban = (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_douban);
		//没有用户状态，全部用未绑定
		
		name = (RelativeLayout)view.findViewById(R.id.my_userinfo_sec_name);
		sex = (RelativeLayout)view.findViewById(R.id.my_userinfo_sec_sex);
		address = (RelativeLayout)view.findViewById(R.id.my_userinfo_sec_address);
		
		((TextView)name.findViewById(R.id.more_text1)).setText("昵称");
		((TextView)name.findViewById(R.id.more_text2)).setVisibility(View.VISIBLE);;
		((TextView)name.findViewById(R.id.more_text2)).setText("XXX");
		
		((TextView)sex.findViewById(R.id.more_text2)).setVisibility(View.VISIBLE);
		((TextView)sex.findViewById(R.id.more_text2)).setText("X");
		((TextView)sex.findViewById(R.id.more_text1)).setText("性别");
		
		((TextView)address.findViewById(R.id.more_text1)).setText("地址");
		((TextView)address.findViewById(R.id.more_text2)).setVisibility(View.VISIBLE);;
		((TextView)address.findViewById(R.id.more_text2)).setText("TTTTTTTTT");
		
	}
	
	
	
	private void checkPlatformStatus(){
		SharedPreferences sp = getActivity().getSharedPreferences("platform", Activity.MODE_PRIVATE);
		sinaStatus = sp.getInt("sina", 0);
		tencentStatus = sp.getInt("tencent", 0);
		renrenStatus = sp.getInt("renren", 0);
		doubanStatus = sp.getInt("douban", 0);
		Log.e("FFF", "sinaStatus  " + sinaStatus + "tencentStatus  " + tencentStatus + "  renrenStatus  " + renrenStatus);
		
		if (sinaStatus == 0) {
			((TextView)sina.findViewById(R.id.more_text1)).setText(R.string.my_sina_unbind);
			((TextView)sina.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
			sina.setOnClickListener(this);
		}else {
			((TextView)sina.findViewById(R.id.more_text1)).setText(R.string.my_sina_binded);
			((TextView)sina.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		}
		
		if (tencentStatus == 0) {
			((TextView)tencent_wb.findViewById(R.id.more_text1)).setText(R.string.my_tencent_wb_unbind);
			((TextView)tencent_wb.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
			tencent_wb.setOnClickListener(this);
		}else {
			Log.e("FFF", "干你娘亲");
			((TextView)tencent_wb.findViewById(R.id.more_text1)).setText(R.string.my_tencent_wb_binded);
			((TextView)tencent_wb.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		}
		
		if (renrenStatus == 0) {
			((TextView)renren.findViewById(R.id.more_text1)).setText(R.string.my_renren_unbind);
			((TextView)renren.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
			renren.setOnClickListener(this);
		}else {
			((TextView)renren.findViewById(R.id.more_text1)).setText(R.string.my_renren_binged);
			((TextView)renren.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		}
		
		if (doubanStatus == 0) {
			((TextView)douban.findViewById(R.id.more_text1)).setText(R.string.my_douban_unbind);
			((TextView)douban.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
			douban.setOnClickListener(this);
		}else {
			((TextView)douban.findViewById(R.id.more_text1)).setText(R.string.my_douban_binged);
			((TextView)douban.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_userinfo_bind_sina:
			if (sinaStatus == 0) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.SINA, My_login_select.sina, mHandler);
			}else {
				return;
			}
			break;

		case R.id.my_userinfo_bind_tencent_wb:
			if (tencentStatus == 0) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.TENCENT, My_login_select.tencent, mHandler);
			}else {
				return;
			}
			break;
		case R.id.my_userinfo_bind_douban:
			if (doubanStatus == 0) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.DOUBAN, My_login_select.douban, mHandler);
			}else {
				return;
			}
			break;
		case R.id.my_userinfo_bind_renren:
			if (renrenStatus == 0) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.RENREN, My_login_select.renren, mHandler);
			}else {
				return;
			}
			break;
		}
//		mFragmentManage.BackStatck(getActivity());
//		refreshThis();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		mFragmentManage.RefreshFrag(getActivity(), mFragmentManage.Tag_Userinfo);
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				checkPlatformStatus();
			}
		}
	};
	
}
