package com.example.fragment.content;

import java.util.ArrayList;
import java.util.List;

import com.example.tab.R;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class My_userinfo extends Fragment{
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
	
	private TextView home,publish;
	
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
		TextView textView = (TextView)view.findViewById(R.id.back2_text);   
		textView.setText("User_Name");
		
		
		home = (TextView)view.findViewById(R.id.my_userinfo_home);
		publish = (TextView)view.findViewById(R.id.my_userinfo_publish);
		
		home.setOnClickListener(new mOnClickListener(0));
		publish.setOnClickListener(new mOnClickListener(1));
	}
	class madapter extends FragmentPagerAdapter{
		
//		@Override
//		public CharSequence getPageTitle(int position) {
//			// TODO Auto-generated method stub
//			return TitleList.get(position);
//		}
		
		public madapter(FragmentManager fm) {
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
//		iv.setImageResource(R.drawable.my_userinfo_line);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.my_userinfo_line).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = screenW / 2;
		
//		LayoutParams lp = (RelativeLayout.LayoutParams)iv.getLayoutParams();
//		lp.width = offset;
//		lp.height  = 8;
//		iv.setLayoutParams(lp);
		Log.i(Tag, "bmp  " + bmpW + "  offset  " + offset);
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(offset, 0);
//		iv.setImageMatrix(matrix);
		
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
}
