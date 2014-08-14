package com.example.fragment.content;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import android.R.integer;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.MaiActivity;
import com.example.adapter.CommentAdapter;
import com.example.application.MaimobApplication;
import com.example.fragment.Tab_My_Frag_New;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.BitmapOptions;
import com.example.util.ConnToServer;
import com.example.util.CustomImage;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.StringUtils;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

public class DuanZi_Comment extends Fragment implements OnClickListener{
	private String Tag = "DuanZi_Comment";
	private View view;
	private ImageView user_icon,image,More,hint_img,Zan_img, Cai_img,iv_fav;
	private TextView user_name,Zan_txt,Cai_txt,Hot_txt,Zan_add, Cai_add,content;
	private RelativeLayout Zan_layout, Cai_layout, Hot_layout;
	private DisplayImageOptions options;
	private Duanzi duanzi;
	private GifImageView gif;
	private ImageLoader imageLoader;
	private ListView listView;
	private List<Map<String, Object>> list;
	private ComAdapter adapter;
	private Mai_DBhelper db;
	private Animation mAnimation;
	private Duanzi_Comments_Lv_head head;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view  = inflater.inflate(R.layout.duanzi_comments, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		imageLoader = MaimobApplication.imageLoader;
		duanzi = (Duanzi) getArguments().getSerializable("duanzi");
//		initView();
		initTopView();
		initListView();
	}
	
	private void initListView(){
		db = Mai_DBhelper.getInstance(getActivity());
		list = db.selectComFromID(Integer.parseInt(duanzi.getPoid()));
		listView = (ListView)view.findViewById(R.id.duanzi_comment_listview);
		adapter = new ComAdapter();
		head = new Duanzi_Comments_Lv_head(getActivity());
//		head.addDuanzi(duanzi);
		listView.addHeaderView(head);
		listView.setAdapter(adapter);
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.up);
	}
	
	private void initTopView(){
		TextView textView = (TextView)view.findViewById(R.id.top_text);
		textView.setText(getResources().getString(R.string.app_name));
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		TextView report = (TextView)view.findViewById(R.id.top_right_change2);
		report.setVisibility(View.GONE);
		back.setOnClickListener(this);
	}
	
	private void switchFrag(Fragment from, Fragment to){
		Bundle bundle = new Bundle();
		bundle.putSerializable("duanzi", duanzi);
		MaiActivity xyftest = (MaiActivity) getActivity();
		xyftest.switchContentFullwithBundle(from, to, bundle);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_left_change:
			mFragmentManage.BackStatck(getActivity());
			break;
		}
	}
	
	
	
	class ComAdapter extends BaseAdapter{

		public ComAdapter(){
			MyLogger.jLog().i("ComAdapter ");
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			MyLogger.jLog().i("xwkkx  " +list.size());
			ComViewHolder holder = null;
			if (convertView == null) {
				holder = new ComViewHolder();
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				convertView = inflater.inflate(R.layout.duanzi_comments_items, null);
				holder.tv_content = (TextView)convertView.findViewById(R.id.dz_com_lv_content);
				holder.tv_name = (TextView)convertView.findViewById(R.id.dz_com_lv_name);
				holder.tv_time = (TextView)convertView.findViewById(R.id.dz_com_lv_time);
				holder.iv_icon = (ImageView)convertView.findViewById(R.id.dz_com_lv_icon);
				convertView.setTag(holder);
			}else {
				holder  = (ComViewHolder) convertView.getTag();
			}
			holder.tv_name.setText((String) list.get(position).get("name"));
			holder.tv_content.setText((String) list.get(position).get("comment"));
			holder.tv_time.setText((String) list.get(position).get("time"));
			MaimobApplication.imageLoader.displayImage((String) list.get(position)
					.get("icon"), holder.iv_icon, ImageUtil.getOption());
			return convertView;
		}

		class mOnclick implements OnClickListener{
			ComViewHolder holder;
			public mOnclick( ComViewHolder holder){
				this.holder = holder;
			}
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {

				default:
					break;
				}
			}
			
		}
	}
	
	public static class  ComViewHolder{
		TextView tv_name, tv_time,tv_zan,tv_content;
		ImageView iv_icon,iv_zan;
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			MyLogger.jLog().i("��������");
			if (mFragmentManage.switch_write) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("duanzi", duanzi);
				mFragmentManage.SwitchFrag(getActivity(), DuanZi_Comment.this, new DuanZi_Comment_Write(), bundle);
				mFragmentManage.switch_write = false;
			}
			RefreshData();
		}else {
			MyLogger.jLog().i("����");
		}
	}
	
	private void RefreshData(){
		if (mFragmentManage.Refresh_Comment) {
			head.LayoutChange();
			list = db.selectComFromID(Integer.parseInt(duanzi.getPoid()));
			MyLogger.jLog().i("size  " + list.size() +"  null?  " + listView == null);
			if (list.size() == 1) {
				listView.setVisibility(View.VISIBLE);
				adapter = new ComAdapter();
				listView.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
			}else {
				adapter.notifyDataSetChanged();
			}
			mFragmentManage.Refresh_Comment = false;
		}
	}
	
	

}
