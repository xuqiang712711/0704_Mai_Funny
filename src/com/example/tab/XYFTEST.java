package com.example.tab;

import com.example.application.MaimobApplication;
import com.example.fragment.DuanZi_Hot;
import com.example.fragment.Tab_DuanZi_Frag;
import com.example.fragment.Tab_Image_Frag2;
import com.example.fragment.Tab_More_Frag;
import com.example.fragment.Tab_My_Frag_New;
import com.example.fragment.Tab_Search_Frag;
import com.example.fragment.content.My_Write;
import com.example.fragment.content.My_userInfo_vp_home;
import com.example.fragment.content.My_userinfo;
import com.example.object.mFragmentManage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class XYFTEST extends FragmentActivity implements OnClickListener {
	 private String Tag = "MainActivity";
	 private Tab_DuanZi_Frag Frag_duanzi;
	 private Tab_Image_Frag2 Frag_image;
	 private Tab_More_Frag Frag_more;
	 private Tab_My_Frag_New Frag_my;
	 private Tab_Search_Frag Frag_search;
	 private My_Write my_Write;

	private RelativeLayout c1, c2, c3, c4, c5;
	private LinearLayout mTab_item_container, content_container,
			content_container2;

	private long exitTime = 0;
//	private FragmentManager fm;
//	private FragmentTransaction fragtrain;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		MaimobApplication.DeviceW = dm.widthPixels;
		MaimobApplication.DeviceH = dm.heightPixels;
		initView();
		selectTab(1);
	}

	private void initView() {
		
		c1 = (RelativeLayout) findViewById(R.id.channel1);
		c2 = (RelativeLayout) findViewById(R.id.channel2);
		c3 = (RelativeLayout) findViewById(R.id.channel3);
		c4 = (RelativeLayout) findViewById(R.id.channel4);
		c5 = (RelativeLayout) findViewById(R.id.channel5);

		c1.setOnClickListener(this);
		c2.setOnClickListener(this);
		c3.setOnClickListener(this);
		c4.setOnClickListener(this);
		c5.setOnClickListener(this);

		content_container = (LinearLayout) findViewById(R.id.content_container);
		content_container2 = (LinearLayout) findViewById(R.id.content_container2);
	}

	/**
	 * 选中tab之后进行的操作
	 * 
	 * @param index
	 */
	public void selectTab(int index) {
		clearTab();
		FragmentManager fm = getSupportFragmentManager();
		
		FragmentTransaction fragtrain = fm.beginTransaction();
		hideFragment(fragtrain);

		switch (index) {
		case 1:
			if (Frag_duanzi == null) {
				Frag_duanzi = new Tab_DuanZi_Frag();
				fragtrain.add(R.id.content_container, Frag_duanzi);
			} else {
				fragtrain.show(Frag_duanzi);
			}
			break;

		case 2:
			if (Frag_image == null) {
				Frag_image = new Tab_Image_Frag2();
				fragtrain.add(R.id.content_container, Frag_image);
			} else {
				fragtrain.show(Frag_image);
			}
			break;
		case 3:
			if (my_Write == null) {
				my_Write = new My_Write();
				fragtrain.add(R.id.content_container2,my_Write);
			}else {
				fragtrain.show(my_Write);
			}
			break;
		case 4:
			if (Frag_my== null) {
				Frag_my = new Tab_My_Frag_New();
				fragtrain.add(R.id.content_container, Frag_my);
			} else {
				fragtrain.show(Frag_my);
			}
			break;
		case 5:
			if (Frag_more == null) {
				Frag_more = new Tab_More_Frag();
				fragtrain.add(R.id.content_container, Frag_more);
			} else {
				fragtrain.show(Frag_more);
			}
			break;
		}
		fragtrain.commit();
	}

	// 清除所有事务
	private void hideFragment(FragmentTransaction fragtrain) {
		if (Frag_duanzi != null) {
			fragtrain.hide(Frag_duanzi);
		}
		if (Frag_image != null) {
			fragtrain.hide(Frag_image);
		}
		if (Frag_my != null) {
			fragtrain.hide(Frag_my);
		}
		if (Frag_more != null) {
			fragtrain.hide(Frag_more);
		}
		if (Frag_search != null) {
			fragtrain.hide(Frag_search);
		}
		if (my_Write !=null) {
			fragtrain.hide(my_Write);
		}
	}

	// 清楚所有按钮的状态
	private void clearTab() {
		c1.setBackgroundResource(0);
		c2.setBackgroundResource(0);
		c3.setBackgroundResource(0);
		c4.setBackgroundResource(0);
		c5.setBackgroundResource(0);
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
	/**
	 * Fragment切换，带参数
	 * @param from
	 * @param to
	 * @param bundle
	 */
	public void switchContentFullwithBundle(Fragment from, Fragment to, Bundle bundle) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		to.setArguments(bundle);
		if (from != to) {
			Log.e(Tag, "First");
			if (to.isAdded()) {
				Log.e(Tag, "Sec");
				ft.hide(from).show(to).commit();
			}else {
				ft.hide(from).add(R.id.content_container2, to).addToBackStack(null).commit();
				Log.i(Tag, "add With Bundle");
			}
		}
	}
	/**
	 * Fragment切换
	 * @param from
	 * @param to
	 */
	public void switchContentFull(Fragment from, Fragment to, Bundle bundle) {
		if (bundle != null) {
		to.setArguments(bundle);
		}
		FragmentManager fm2 = getSupportFragmentManager();
		FragmentTransaction ft2 = fm2.beginTransaction();
		if (from != to) {
			Log.e(Tag, "First");
			if (to.isAdded()) {
				Log.e(Tag, "Sec");
				ft2.hide(from).show(to).commit();
			}else {
				ft2.hide(from).add(R.id.content_container2, to).addToBackStack(null).commit();
				Log.i(Tag, "add");
			}
		}
	}
	
	public void hintFragment(){
		FragmentManager fm2 = getSupportFragmentManager();
		fm2.popBackStack();
	}
	
	public void WriteBack(){
		selectTab(1);
	}
	
	public void RefreshFragment(int FragTag){
		if (FragTag == mFragmentManage.Tag_My) {
			Frag_my.refresh();
		}
	}
	
}
