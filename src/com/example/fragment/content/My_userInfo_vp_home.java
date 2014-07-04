package com.example.fragment.content;

import com.example.tab.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class My_userInfo_vp_home extends Fragment{
	View view;
	private RelativeLayout tencent_qq, tencent_wb,sina,name, sex, address;
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
	}
	
	private void initView(){
		tencent_qq = (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_tencent_qq);
		tencent_wb= (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_tencent_wb);
		sina = (RelativeLayout)view.findViewById(R.id.my_userinfo_bind_sina);
		
		//没有用户状态，全部用未绑定
		((TextView)tencent_qq.findViewById(R.id.more_text1)).setText(R.string.my_tencent_qq_unbind);
		((TextView)tencent_wb.findViewById(R.id.more_text1)).setText(R.string.my_tencent_wb_unbind);
		((TextView)sina.findViewById(R.id.more_text1)).setText(R.string.my_sina_unbind);
		
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
}
