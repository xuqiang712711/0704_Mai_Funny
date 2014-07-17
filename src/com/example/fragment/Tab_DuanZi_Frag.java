package com.example.fragment;

import org.w3c.dom.Text;

import com.example.maiUtil.Getuuid;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;
import com.umeng.socialize.view.abs.am;

import android.graphics.drawable.BitmapDrawable;
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
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class Tab_DuanZi_Frag extends Fragment implements OnClickListener{
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
	
	private PopupWindow pop;// 0710Ìí¼Ó
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
			break;
		case R.id.top_hot:
			selectTab2(1);
			check_1 = true;
			break;
		case R.id.top_new:
			selectTab2(2);
			check_1 = false;
			break;
		case R.id.tab_refresh:
//			iv_refresh.setImageResource(R.drawable.refresh_normal);
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
				Log.i("XXX", "Stop");
				iv_refresh.clearAnimation();
			}
			
		}
	};
}
