package com.example.fragment;

import com.example.tab.R;
import com.example.util.MyLogger;
import com.example.util.Uris;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Tab_Image_Frag2 extends Fragment implements OnClickListener{
	private Image_Hot image_Hot;
	private Image_New image_New;
	private RelativeLayout R1,R2,R3,R4;
	private TextView textView, text_new, text_hot;
	private PopupWindow pop;// 0710Ìí¼Ó
	private FragmentTransaction  ft;
	private boolean isHot = true;
	private ImageView iv_refresh;
	private Animation animation;
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
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			MyLogger.jLog().i("img hide is true");
		}else {
//			if (image_Hot != null) {
//				image_Hot.ChangeFontSize();
//			}else {
//				image_New.ChangeFontSize();
//			}
			if (!image_Hot.isHidden()) {
				image_Hot.ChangeFontSize();
			}else if (!image_New.isHidden()) {
				image_New.ChangeFontSize();
			}
		}
	}
	
	private void initPop(){
		animation = AnimationUtils.loadAnimation(getActivity(), R.anim.round_loading);
		iv_refresh = (ImageView)view.findViewById(R.id.tab_refresh);
		iv_refresh.setOnClickListener(this);
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
	
	private void hideFrag(FragmentTransaction ft){
		if (image_Hot != null) {
			ft.hide(image_Hot);
		}
		if (image_New != null) {
			ft.hide(image_New);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tab_top_text:
//			DisplayMetrics metrics = new DisplayMetrics();
//			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//			int width = metrics.widthPixels/2 - pop.getWidth()/2;
			int text_w = textView.getWidth()/2;
			int pop_w = pop.getWidth() / 2;
			int w = pop_w - text_w ;
			pop.showAsDropDown(v, -w, 10);
			break;
		case R.id.top_hot:
			selectTab2(1);
			isHot = true;
			break;
		case R.id.top_new:
			selectTab2(2);
			isHot = false;
			break;
		case R.id.tab_refresh:
//			iv_refresh.setImageResource(R.drawable.refresh_normal);
			iv_refresh.startAnimation(animation);
			if (isHot) {
				image_Hot.Refresh(Tabhandler);
			}else {
				image_New.Refresh(Tabhandler);
			}
			break;
		}
	}
	
	Handler Tabhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if (msg.what == Uris.MSG_REFRESH) {
//				iv_refresh.setImageResource(R.drawable.refresh_pre);
				iv_refresh.clearAnimation();
			}
			
		}
	};
}
