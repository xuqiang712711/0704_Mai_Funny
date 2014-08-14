package com.example.fragment;

import org.w3c.dom.Text;

import com.example.Activity.MaiActivity;
import com.example.maiUtil.Getuuid;
import com.example.tab.R;
import com.example.tab.R.drawable;
import com.example.util.MyLogger;
import com.example.util.Uris;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Tab_DuanZi_Frag extends Fragment implements OnClickListener, OnDismissListener{
//	private Fragment duanzi_hot;
	private DuanZi_Hot duanZi_Hot;
	private DuanZi_New duanZi_New;
	private RelativeLayout R1,R2,R3,R4;
	private LinearLayout layout;
	private FragmentTransaction  ft;
	private View view;
	private TextView textView, text_new, text_hot;
	private boolean check_1 = true;
	private ImageView iv_refresh;
	private Animation animation;
	private long exitTime = 0;
	
	private PopupWindow pop;// 0710添加
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
		initPop();
		selectTab2(1);
	}
	
	
	
	@Override
		public void onHiddenChanged(boolean hidden) {
			// TODO Auto-generated method stub
			super.onHiddenChanged(hidden);
			if (hidden) {
				MyLogger.jLog().i("hide is true");
			}else {
				MyLogger.jLog().i("hide is false");
				if (duanZi_Hot == null) {
					MyLogger.jLog().i("duanzi_hot is null");
				}
				if (duanZi_New == null) {
					MyLogger.jLog().i("duanzi_new  is null");
				}
				if (!duanZi_Hot.isHidden()) {
					duanZi_Hot.ChangeFontSize();
				}else if (!duanZi_New.isHidden()) {
					duanZi_New.ChangeFontSize();
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
		pop.setOnDismissListener(this);
	}
	
	private void selectTab2(int index){
		clearTextColor();
		ft = getChildFragmentManager().beginTransaction();
		hideFrag(ft);
		switch (index) {
		case 1:
			if (duanZi_Hot == null) {
				duanZi_Hot = new DuanZi_Hot();
				ft.add(R.id.duanzi_container, duanZi_Hot);
			}else {
				ft.show(duanZi_Hot);
			}
			text_hot.setTextColor(getResources().getColor(R.color.orange));
			Drawable drawable_hot = getActivity().getResources().getDrawable(R.drawable.mai_hot_cl);
			drawable_hot.setBounds(0, 0, drawable_hot.getMinimumWidth(), drawable_hot.getMinimumHeight());
			text_hot.setCompoundDrawables(drawable_hot, null, null, null);
			break;

		case 2:
			if (duanZi_New == null) {
				duanZi_New = new DuanZi_New();
				ft.add(R.id.duanzi_container, duanZi_New);
			}else {
				ft.show(duanZi_New);
			}
			text_new.setTextColor(getResources().getColor(R.color.orange));
			Drawable drawable_new = getActivity().getResources().getDrawable(R.drawable.mai_new_cl);
			drawable_new.setBounds(0, 0, drawable_new.getMinimumWidth(), drawable_new.getMinimumHeight());
			text_new.setCompoundDrawables(drawable_new, null, null, null);
			break;
		}
		if (pop.isShowing()) {
			pop.dismiss();
		}
		SetTitle(index);
		ft.commit();
	}
	
	private void hideFrag(FragmentTransaction ft){
		if (duanZi_Hot != null) {
			ft.hide(duanZi_Hot);
		}
		if (duanZi_New != null) {
			ft.hide(duanZi_New);
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
			int text_w = textView.getWidth()/2;
			int pop_w = pop.getWidth() / 2;
			int w = pop_w - text_w ;
			pop.showAsDropDown(v, -w, 10);
			setArrow(2);
			break;
		case R.id.top_hot:
			selectTab2(1);
//			setArrow(1);
			check_1 = true;
			break;
		case R.id.top_new:
			selectTab2(2);
//			setArrow(1);
			check_1 = false;
			break;
		case R.id.tab_refresh:
			iv_refresh.startAnimation(animation);
			if (check_1) {
				duanZi_Hot.Refresh(Tabhandler);
			}else {
				duanZi_New.Refresh(Tabhandler);
			}
			break;
		}
	}
	
	private void clearTextColor(){
		text_hot.setTextColor(getResources().getColor(R.color.white));
		text_new.setTextColor(getResources().getColor(R.color.white));
		Drawable drawable_new = getActivity().getResources().getDrawable(R.drawable.mai_new);
		drawable_new.setBounds(0, 0, drawable_new.getMinimumWidth(), drawable_new.getMinimumHeight());
		text_new.setCompoundDrawables(drawable_new, null, null, null);
		Drawable drawable_hot = getActivity().getResources().getDrawable(R.drawable.mai_hot);
		drawable_hot.setBounds(0, 0, drawable_hot.getMinimumWidth(), drawable_hot.getMinimumHeight());
		text_hot.setCompoundDrawables(drawable_hot, null, null, null);
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
	
	Handler Tabhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if (msg.what == Uris.MSG_REFRESH) {
//				iv_refresh.setImageResource(R.drawable.refresh_pre);
				iv_refresh.clearAnimation();
			}
			
		}
	};
	/**
	 * 设置箭头方向
	 * @param tag	1、向下		2、向上
	 * @return
	 */
	public void setArrow(int tag){
		Drawable drawable = null;
		switch (tag) {
		case 1:
			drawable = getActivity().getResources().getDrawable(R.drawable.down_arrow_titlebar);
			break;

		case 2:
			drawable = getActivity().getResources().getDrawable(R.drawable.up_arrow_titlebar);
			break;
		}
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		textView.setCompoundDrawables(null, null, null, drawable);
	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		if (!pop.isShowing()) {
			setArrow(1);
		}
	}
	
}
