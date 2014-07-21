package com.example.fragment.content;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.MaimobApplication;
import com.example.tab.R;
import com.example.util.ImageUtil;

public class My_userinfo extends Fragment implements OnClickListener{
	private String Tag = "My_userinfo";
	private ViewPager vp;
	private List<Fragment> viewList;
	private List<String> TitleList;
	private View view,Vp_1,Vp_2;
	private RelativeLayout tencent_qq, tencent_wb,sina,name, sex, address;
	
	private ImageView iv;
	private int offset = 0;//偏移量
	private int currIndex = 0;
	private int bmpW = 0;
	private AlertDialog.Builder builder;
	private SharedPreferences sp;
	private EditText editText;
	private TextView home,publish;
	private TextView tv_user_name;
	
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
		initBMP();
		initView();
		initViewPager();
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
		
		TitleList = new ArrayList<String>();
		TitleList.add("主页");
		TitleList.add("投稿");
		
		vp.setAdapter(new madapter(getActivity().getSupportFragmentManager()));
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	private void initView(){
		sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
		ImageView iv_user_head = (ImageView)view.findViewById(R.id.userinfo_icon);
		tv_user_name = (TextView)view.findViewById(R.id.userinfo_name);
		TextView tv_user_description = (TextView)view.findViewById(R.id.user_points_num);
		MaimobApplication.imageLoader.displayImage(sp.getString("icon", null), iv_user_head, ImageUtil.getOption());
		tv_user_name.setText(sp.getString("name", null));
		tv_user_description.setText(sp.getString("description", null));
		
		TextView textView = (TextView)view.findViewById(R.id.back2_text);   
		textView.setText(sp.getString("name", null));
		
		
		home = (TextView)view.findViewById(R.id.my_userinfo_home);
		publish = (TextView)view.findViewById(R.id.my_userinfo_publish);
		
		home.setOnClickListener(new mOnClickListener(0));
		publish.setOnClickListener(new mOnClickListener(1));
		
		iv_user_head.setOnClickListener(this);
		tv_user_name.setOnClickListener(this);
		tv_user_description.setOnClickListener(this);
		
		tv_user_name.setOnClickListener(this);
		if (editText == null) {
			editText = new EditText(getActivity());
		}
		editText.setHint(sp.getString("name", null));
		
	}
	class madapter extends FragmentPagerAdapter{
		
		
		public madapter(FragmentManager fm) {
			super(fm);
			Log.e(Tag, "view  " +viewList.size());
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
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.my_userinfo_line).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = screenW / 2;
		
		
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
//		((TextView)v).setText(content);
//		switch (v.getId()) {
//		case R.id.userinfo_name:
//			EditText medit = new EditText(getActivity());
//			new AlertDialog.Builder(getActivity()).setTitle("修改昵称").setView(medit).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					String content = editText.getText().toString();
//					Editor editor = sp.edit();
////					if (content != null && !content.equals("")) {
////						editor.putString("name", content);
////						tv_user_name.setText(content);
////					}
//					((TextView)v).setText(content);
//				}
//			})
//			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					
//				}
//			}).show();
//			break;
//
//		case R.id.userinfo_icon:
//			
//			break;
//		}
	}
}
