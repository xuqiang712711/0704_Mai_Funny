package com.example.fragment.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.application.MaimobApplication;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.SerUser;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.example.util.User;

public class My_userinfo extends Fragment implements OnClickListener{
	private String Tag = "My_userinfo";
	private ViewPager vp;
	private List<Fragment> viewList;
	private List<String> TitleList;
	private View view,Vp_1,Vp_2;
	private RelativeLayout tencent_qq, tencent_wb,sina,name, sex, address;
	private RelativeLayout userinfo;
	
	private ImageView iv;
	private int offset = 0;//偏移量
	private int currIndex = 0;
	private SharedPreferences sp;
	private TextView home,publish;
	private TextView tv_user_name;
	private TextView tv_user_description;
	private ImageView iv_user_head;
	private User user;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		user = SerUser.deSerializationUser((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.SerUser, getActivity(),
				SharedPreferencesUtils.user, ""));
		if (mFragmentManage.Refresh_userInfo) {
			if (!hidden) {
				MyLogger.jLog().i("刷新数据~~~~ " + user.getName());
				tv_user_name.setText(user.getName());
				tv_user_description.setText(user.getDescription());
				MaimobApplication.imageLoader.displayImage(
						StringUtils.checkImgPath(user.getIcon()), iv_user_head, ImageUtil.getOption());
				initViewPager();
			}
		}
	}
	
	
	private void  initViewPager(){
		Log.e(Tag, "InitViewpager");
		vp = (ViewPager)view.findViewById(R.id.viewpager);
		
		LayoutInflater lif = LayoutInflater.from(getActivity());
		Vp_1 = lif.inflate(R.layout.my_userinfo_vp_1, null);
		Vp_2 = lif.inflate(R.layout.my_userinfo_vp_2, null);
		
		viewList = new ArrayList<Fragment>();
		viewList.add(new My_userInfo_vp_home());
		viewList.add(new My_userInfo_Vp_Publish());
		
//		TitleList = new ArrayList<String>();
//		TitleList.add("主页");
//		TitleList.add("投稿");
		
//		vp.setAdapter(new madapter(getActivity().getSupportFragmentManager()));
		vp.setAdapter(new fadapter(getChildFragmentManager()));
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyLogger.jLog().i("初始化user");
		user = SerUser.deSerializationUser((String) SharedPreferencesUtils
				.getParam("SerUser", getActivity(), "user", ""));
		initBMP();
		initView();
		initViewPager();
	}
	
	private void initView(){
		userinfo = (RelativeLayout)view.findViewById(R.id.my_userinfo_main);
		userinfo.setOnClickListener(this);
		
		//User_Info
		sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
 		tv_user_name = (TextView)view.findViewById(R.id.userinfo_name);
		tv_user_description = (TextView)view.findViewById(R.id.user_points_num);
		iv_user_head = (ImageView)view.findViewById(R.id.userinfo_icon);
		MaimobApplication.imageLoader.displayImage(
		StringUtils.checkImgPath(StringUtils.checkImgPath(user.getIcon())), iv_user_head, ImageUtil.getOption());
		tv_user_name.setText(user.getName());
		tv_user_description.setText(user.getDescription());
		
		//Title
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);   
		tv_title.setText(user.getName());
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setOnClickListener(this);
		TextView right = (TextView)view.findViewById(R.id.top_right_change2);
		right.setVisibility(View.GONE);
		
		//Content
		home = (TextView)view.findViewById(R.id.my_userinfo_home);
		publish = (TextView)view.findViewById(R.id.my_userinfo_publish);
		
		home.setOnClickListener(new mOnClickListener(0));
		publish.setOnClickListener(new mOnClickListener(1));
		
		iv_user_head.setOnClickListener(this);
		tv_user_name.setOnClickListener(this);
		tv_user_description.setOnClickListener(this);
		
//		if (editText == null) {
//			editText = new EditText(getActivity());
//		}
//		editText.setHint(sp.getString("name", null));
		
	}
	
	class fadapter extends FragmentStatePagerAdapter{

		public fadapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return (viewList == null || viewList.size() == 0) ? null : viewList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}
		
	}
	
	private void initBMP(){
		iv = (ImageView)view.findViewById(R.id.my_userinfo_tab);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = screenW / 2;
		RelativeLayout.LayoutParams line_paParams = (LayoutParams) iv.getLayoutParams();
		line_paParams.width = offset;
		iv.setLayoutParams(line_paParams);
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener{
		int move = offset;
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Log.i(Tag, "move  " + move);
			Animation animation = new TranslateAnimation(move*currIndex, move*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			iv.startAnimation(animation);
		}
		
	}

	class mOnClickListener implements OnClickListener{
		private int i = 0;
		public mOnClickListener(int index){
			i = index;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vp.setCurrentItem(i);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_left_change:
			mFragmentManage.BackStatck(getActivity());
			break;

		case R.id.my_userinfo_main:
			mFragmentManage.SwitchFrag(getActivity(), My_userinfo.this, new My_Userinfo_Edit(), null);
			break;
		}
	}
	
	public void refresh(){
//		tv_user_name.setText(user.getName());
//		tv_user_description.setText(user.getDescription());
	}
	
}
