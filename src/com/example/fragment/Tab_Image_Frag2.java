package com.example.fragment;

import com.example.tab.R;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Tab_Image_Frag2 extends Fragment implements OnClickListener{
	private Image_Hot image_Hot;
	private Image_Essence image_Essence;
	private Image_New image_New;
	private Image_Recommend image_Recommend;
	private RelativeLayout R1,R2,R3,R4;
	private TextView textView, text_new, text_hot;
	private PopupWindow pop;// 0710Ìí¼Ó
	private FragmentTransaction  ft;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.duanzi_top_item, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		initView();
//		selectTab(1);
		initPop();
		selectTab2(1);
	}
	
	private void initPop(){
		textView = (TextView)view.findViewById(R.id.tab_top_text);
		textView.setOnClickListener(this);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View PopView = inflater.inflate(R.layout.top_pop, null);
		text_hot = (TextView)PopView.findViewById(R.id.top_hot);
		text_hot.setOnClickListener(this);
		text_new = (TextView)PopView.findViewById(R.id.top_new);
		text_new.setOnClickListener(this);
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels/3;
		pop = new PopupWindow(PopView, width, LayoutParams.WRAP_CONTENT, true);
		pop.setBackgroundDrawable(new BitmapDrawable());
	}
	
	private void selectTab2(int index){
		clearTextColor();
		ft = getChildFragmentManager().beginTransaction();
		hideFrag(ft);
		switch (index) {
		case 1:
			if (image_Hot == null) {
				image_Hot = new Image_Hot();
				ft.add(R.id.duanzi_container, image_Hot);
			}else {
				ft.show(image_Hot);
			}
			text_hot.setTextColor(getResources().getColor(R.color.teal));
			break;

		case 2:
			if (image_New == null) {
				image_New = new Image_New();
				ft.add(R.id.duanzi_container, image_New);
			}else {
				ft.show(image_New);
			}
			text_new.setTextColor(getResources().getColor(R.color.teal));
			break;
		}
		if (pop.isShowing()) {
			pop.dismiss();
		}
		SetTitle(index);
		ft.commit();
	}
	
	private void clearTextColor(){
		text_hot.setTextColor(getResources().getColor(R.color.white));
		text_new.setTextColor(getResources().getColor(R.color.white));
	}
	
	private void SetTitle(int index){
		switch (index) {
		case 1:
			textView.setText(getResources().getString(R.string.duanzi_hot));
			break;

		case 2:
			textView.setText(getResources().getString(R.string.duanzi_new));
			break;
		}
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
		case R.id.tab_top_text:
			DisplayMetrics metrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int width = metrics.widthPixels/2 - pop.getWidth()/2;
			pop.showAsDropDown(v, width, 10);
			break;
		case R.id.top_hot:
			selectTab2(1);
			break;
		case R.id.top_new:
			selectTab2(2);
			break;

		}
	}
}
