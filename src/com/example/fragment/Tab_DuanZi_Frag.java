package com.example.fragment;

import com.example.maiUtil.Getuuid;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Tab_DuanZi_Frag extends Fragment implements OnClickListener{
//	private Fragment duanzi_hot;
	private DuanZi_Hot duanZi_Hot;
	private Duanzi_Recommend duanzi_Recommend;
	private DuanZI_Essence duanZI_Essence;
	private DuanZi_New duanZi_New;
	private RelativeLayout R1,R2,R3,R4;
	private LinearLayout layout;
	private FragmentTransaction  ft;
	private View view;
//	private FragmentManager mFM = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.duanzi_4_tab, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		selectTab(1);
	}
	
	
	private void initView(){
		R1 = (RelativeLayout)view.findViewById(R.id.channel1);
		R2 = (RelativeLayout)view.findViewById(R.id.channel2);
		R3 = (RelativeLayout)view.findViewById(R.id.channel3);
		R4 = (RelativeLayout)view.findViewById(R.id.channel4);
		R1.setOnClickListener(this);
		R2.setOnClickListener(this);
		R3.setOnClickListener(this);
		R4.setOnClickListener(this);
		
		layout = (LinearLayout)view.findViewById(R.id.duanzi_container);
	}
	
	protected void selectTab(int index){
		clearTab();
		ft = getChildFragmentManager().beginTransaction();
		hideFrag(ft);
		
		switch (index) {
		case 1:
			R1.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (duanZi_Hot == null) {
				duanZi_Hot = new DuanZi_Hot();
				ft.add(R.id.duanzi_container, duanZi_Hot);
			}else {
				ft.show(duanZi_Hot);
			}
			break;

		case 2:
			R2.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (duanzi_Recommend == null) {
				duanzi_Recommend = new Duanzi_Recommend();
				ft.add(R.id.duanzi_container, duanzi_Recommend);
			}else {
				ft.show(duanzi_Recommend);
			}
			break;
			
		case 3:
			R3.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (duanZI_Essence == null) {
				duanZI_Essence = new DuanZI_Essence();
				ft.add(R.id.duanzi_container, duanZI_Essence);
			}else {
				ft.show(duanZI_Essence);
			}
			break;
			
		case 4:
			R4.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (duanZi_New == null) {
				duanZi_New = new DuanZi_New();
				ft.add(R.id.duanzi_container, duanZi_New);
			}else {
				ft.show(duanZi_New);
			}
			break;
		}
		ft.commit();
	}

	private void clearTab(){
		R1.setBackgroundResource(0);
		R2.setBackgroundResource(0);
		R3.setBackgroundResource(0);
		R4.setBackgroundResource(0);
	}
	
	private void hideFrag(FragmentTransaction ft){
		if (duanzi_Recommend != null) {
			ft.hide(duanzi_Recommend);
		}
		if (duanZi_Hot != null) {
			ft.hide(duanZi_Hot);
		}
		if (duanZI_Essence != null) {
			ft.hide(duanZI_Essence);
		}
		if (duanZi_New != null) {
			ft.hide(duanZi_New);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.channel1:
			selectTab(1);
			break;

		case R.id.channel2:
			selectTab(2);
			break;
		
		case R.id.channel3:
			selectTab(3);
			break;
		
		case R.id.channel4:
			selectTab(4);
			break;
		}
	}
	
}
