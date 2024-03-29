package com.example.fragment;

import org.w3c.dom.Text;

import com.example.maiUtil.Getuuid;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;

import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

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
	private TextView textView, text_new, text_hot;
	
	private PopupWindow pop;// 0710����
//	private FragmentManager mFM = null;
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
			if (duanZi_Hot == null) {
				duanZi_Hot = new DuanZi_Hot();
				ft.add(R.id.duanzi_container, duanZi_Hot);
			}else {
				ft.show(duanZi_Hot);
			}
			text_hot.setTextColor(getResources().getColor(R.color.teal));
			break;

		case 2:
			if (duanZi_New == null) {
				duanZi_New = new DuanZi_New();
				ft.add(R.id.duanzi_container, duanZi_New);
			}else {
				ft.show(duanZi_New);
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
	
}
