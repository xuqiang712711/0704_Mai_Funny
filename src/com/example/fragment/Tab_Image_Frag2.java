package com.example.fragment;

import com.example.tab.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Tab_Image_Frag2 extends Fragment implements OnClickListener{
	private Image_Hot image_Hot;
	private Image_Essence image_Essence;
	private Image_New image_New;
	private Image_Recommend image_Recommend;
	private RelativeLayout R1,R2,R3,R4;
	View view;
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
	
	protected void selectTab(int index){
		clearTab();
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		hideFrag(ft);
		
		switch (index) {
		case 1:
			R1.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (image_Hot == null) {
				image_Hot = new Image_Hot();
				ft.add(R.id.duanzi_container, image_Hot);
			}else {
				ft.show(image_Hot);
			}
			break;

		case 2:
			R2.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (image_Recommend == null) {
				image_Recommend = new Image_Recommend();
				ft.add(R.id.duanzi_container, image_Recommend);
			}else {
				ft.show(image_Recommend);
			}
			break;
			
		case 3:
			R3.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (image_Essence == null) {
				image_Essence = new Image_Essence();
				ft.add(R.id.duanzi_container, image_Essence);
			}else {
				ft.show(image_Essence);
			}
			break;
			
		case 4:
			R4.setBackgroundResource(R.drawable.tt_tab_bar_guide_selected_bg_s);
			if (image_New == null) {
				image_New = new Image_New();
				ft.add(R.id.duanzi_container, image_New);
			}else {
				ft.show(image_New);
			}
			break;
		}
		ft.commit();
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
	}
	
	private void clearTab(){
		R1.setBackgroundResource(0);
		R2.setBackgroundResource(0);
		R3.setBackgroundResource(0);
		R4.setBackgroundResource(0);
	}
	
	private void hideFrag(FragmentTransaction ft){
		if (image_Essence != null) {
			ft.hide(image_Essence);
		}
		if (image_Hot != null) {
			ft.hide(image_Hot);
		}
		if (image_New != null) {
			ft.hide(image_New);
		}
		if (image_Recommend != null) {
			ft.hide(image_Recommend);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.channel1:
			// changeDuanZi();
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
		case R.id.channel5:
			selectTab(5);
			break;

		}
	}
}
