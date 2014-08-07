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

import com.example.Activity.OauthActivity;
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
import com.example.util.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

public class DuanZi_Comment extends Fragment implements OnClickListener{
	private String Tag = "DuanZi_Comment";
	private View view;
	private ImageView user_icon,image,More,hint_img,Zan_img, Cai_img,iv_fav;
	private TextView user_name,Zan_txt,Cai_txt,Hot_txt;
	private TextView content;
	private RelativeLayout Zan_layout, Cai_layout, Hot_layout;
	private DisplayImageOptions options;
	private Duanzi duanzi;
	private GifImageView gif;
	private ImageLoader imageLoader;
	private ListView listView;
	private List<Map<String, Object>> list;
	private ComAdapter adapter;
	private Mai_DBhelper db;
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
		initView();
		initListView();
	}
	
	private void initListView(){
		db = Mai_DBhelper.getInstance(getActivity());
		list = db.selectComFromID(Integer.parseInt(duanzi.getPoid()));
		listView = (ListView)view.findViewById(R.id.duanzi_comment_listview);
		if (list.size() != 0) {
			adapter = new ComAdapter();
			listView.setAdapter(adapter); 
		}else {
			listView.setVisibility(View.GONE);
		}
	}
	
	@SuppressWarnings("unused")
	private void initView(){
//		mitem_top = (LinearLayout)view.findViewById(R.id.mitem_top);
		TextView textView = (TextView)view.findViewById(R.id.top_text);
		textView.setText(getResources().getString(R.string.app_name));
//		Button back = (Button)view.findViewById(R.id.top_left);
//		back.setText("");
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		TextView report = (TextView)view.findViewById(R.id.top_right_change2);
		report.setVisibility(View.GONE);
		
		iv_fav = (ImageView)view.findViewById(R.id.mitem_top_fav);
		hint_img = (ImageView)view.findViewById(R.id.mitem_hint_img);
		gif = (GifImageView)view.findViewById(R.id.mitem_test_gif);
		user_icon = (ImageView)view.findViewById(R.id.mitem_icon);
		user_name = (TextView)view.findViewById(R.id.mitem_username);
		content = (TextView)view.findViewById(R.id.mitem_test_content);
		image = (ImageView)view.findViewById(R.id.mitem_test_img);
		Cai_txt = (TextView)view.findViewById(R.id.mitem_bottom_cai_txt);
		Zan_txt = (TextView)view.findViewById(R.id.mitem_bottom_zan_txt);
		Hot_txt = (TextView)view.findViewById(R.id.mitem_bottom_hot_txt);
		More = (ImageView)view.findViewById(R.id.bottom_more);
		Cai_img = (ImageView)view.findViewById(R.id.mitem_bottom_cai_img);
		Zan_img = (ImageView)view.findViewById(R.id.mitem_bottom_zan_img);
		Zan_layout = (RelativeLayout)view.findViewById(R.id.mitem_bottom_zan);
		Cai_layout = (RelativeLayout)view.findViewById(R.id.mitem_bottom_cai);
		Hot_layout = (RelativeLayout)view.findViewById(R.id.mitem_bottom_hot);
		
		iv_fav.setVisibility(View.GONE);
		user_name.setText(duanzi.getUserName());
		content.setText(duanzi.getContent());
		content.setTextSize(Uris.Font_Size);
		Cai_txt.setText(String.valueOf(Integer.parseInt(duanzi.getCai()) + 1));
		
		Zan_txt.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		Hot_txt.setText(duanzi.getComment());
		
		if (duanzi.isZanPressed()) {
			Zan_img.setImageResource(R.drawable.ic_digg_pressed);
			Zan_txt.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		}else {
			Zan_img.setImageResource(R.drawable.ic_digg_normal);
			Zan_txt.setText(duanzi.getZan());
		}
		
		if (duanzi.isCaiPressed()) {
			Cai_img.setImageResource(R.drawable.ic_digg_pressed);
			Cai_txt.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		}else {
			Cai_img.setImageResource(R.drawable.ic_bury_normal);
			Cai_txt.setText(duanzi.getCai());
		}
		More.setOnClickListener(this);
		gif.setOnClickListener(this);
		Zan_layout.setOnClickListener(this);
		Cai_layout.setOnClickListener(this);
		Hot_layout.setOnClickListener(this);
		back.setOnClickListener(this);
		report.setOnClickListener(this);
		
		String imgUri = duanzi.getImageUrl();
		Log.e(Tag, "Imguri  " + imgUri);
		if (imgUri != null && !imgUri.equals("")) {
			String currImgUrl = StringUtils.checkImgPath(imgUri);
			options = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.showImageOnLoading(R.drawable.maimob)
			.showImageForEmptyUri(R.drawable.maimob)
			.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.displayer(new SimpleBitmapDisplayer()).build();
			image.setVisibility(View.VISIBLE);
			String currName = StringUtils.checkImgPath(imgUri);
			if (imgUri.substring(imgUri.length() - 3, imgUri.length()).equals("gif")) {
//				File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(),
//						imageLoader.getDiskCache());
//				if (imgFile != null && !imgFile.equals("")) {
//					int ReqHeight = BitmapOptions.getWH(imgFile.toString(), MaimobApplication.DeviceW);
//					FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) gif.getLayoutParams();
//					params.height = ReqHeight;
//					params.width = FrameLayout.LayoutParams.MATCH_PARENT;
//					gif.setLayoutParams(params);
//				}
//				hint_img.setVisibility(View.VISIBLE);
//				image.setVisibility(View.GONE);
//				gif.setVisibility(View.VISIBLE);
//				imageLoader.displayImage(currName, gif, options);
				

				hint_img.setVisibility(View.VISIBLE);
				gif.setVisibility(View.VISIBLE);
				imageLoader.displayImage(currImgUrl, gif, options);
				File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(),
						imageLoader.getDiskCache());
				if (imgFile != null) {
					int h = BitmapOptions.getWH(imgFile.toString(),
							MaimobApplication.DeviceW);
					FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) gif.getLayoutParams();
					params.height = h;
					params.width = FrameLayout.LayoutParams.MATCH_PARENT;
					gif.setLayoutParams(params);
				}else if (currImgUrl.startsWith("file")) {
					int h = BitmapOptions.getWH(imgUri, MaimobApplication.DeviceW);
					FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) gif.getLayoutParams();
					params.height = h;
					params.width = FrameLayout.LayoutParams.MATCH_PARENT;
					gif.setLayoutParams(params);
				}
			}else {
				hint_img.setVisibility(View.GONE);
				gif.setVisibility(View.GONE);
				imageLoader.displayImage(currName, image, options);
			}
		}
		
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
		case R.id.mitem_bottom_cai:
			duanzi.CanPress(Duanzi.CAI, Cai_txt,Cai_img, getActivity());
			break;

		case R.id.mitem_bottom_hot:
			Toast.makeText(getActivity(), "去评论", Toast.LENGTH_SHORT).show();
			
			duanzi.setNeedComment(true);
			Bundle bundle = new Bundle();
			bundle.putSerializable("duanzi", duanzi);
			bundle.putInt("xwkkx", My_login_select.From_Duanzi);
			if (User.UserIsExists(getActivity())) {
				mFragmentManage.SwitchFrag(getActivity(), DuanZi_Comment.this, new DuanZi_Comment_Write(), bundle);
			}else {
				mFragmentManage.SwitchFrag(getActivity(), DuanZi_Comment.this, new My_login_select(), bundle);

			}
			break;
		case R.id.bottom_more:

			break;
		case R.id.mitem_bottom_zan:
			duanzi.CanPress(Duanzi.ZAN, Zan_txt,Zan_img, getActivity());
			break;
		case R.id.mitem_test_gif:
			Log.e(Tag, "imgUri  " + duanzi.getImageUrl());
			if (duanzi.getImageUrl() != null && !duanzi.getImageUrl().equals("")) {
				hint_img.setVisibility(View.GONE);
				((GifImageView)v).setImageDrawable(StringUtils.checkImgPathForGif(duanzi.getImageUrl()));
				
			}
			break;
		case R.id.top_left_change:
			mFragmentManage.BackStatck(getActivity());
			break;
		case R.id.top_right_change2:
			mFragmentManage.SwitchFrag(getActivity(), DuanZi_Comment.this, new Duanzi_Report(), null);
			break;
		}
	}
	
	class ComAdapter extends BaseAdapter{

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
			ComViewHolder holder = null;
			if (convertView == null) {
				holder = new ComViewHolder();
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				convertView = inflater.inflate(R.layout.duanzi_comments_items, null);
				holder.tv_content = (TextView)convertView.findViewById(R.id.dz_com_lv_content);
				holder.tv_name = (TextView)convertView.findViewById(R.id.dz_com_lv_name);
				holder.tv_time = (TextView)convertView.findViewById(R.id.dz_com_lv_time);
				holder.iv_icon = (ImageView)convertView.findViewById(R.id.dz_com_lv_icon);
				holder.tv_zan = (TextView)convertView.findViewById(R.id.dz_com_lv_tv_Zan);
				holder.iv_zan = (ImageView)convertView.findViewById(R.id.dz_com_lv_iv_Zan);
				convertView.setTag(holder);
			}else {
				holder  = (ComViewHolder) convertView.getTag();
			}
			holder.tv_name.setText((String) list.get(position).get("name"));
			holder.tv_content.setTextSize(Uris.Font_Size);
			holder.tv_content.setText((String) list.get(position).get("comment"));
			holder.tv_time.setText((String) list.get(position).get("time"));
			MaimobApplication.imageLoader.displayImage((String) list.get(position)
					.get("icon"), holder.iv_icon, ImageUtil.getOption());
			holder.iv_zan.setOnClickListener(new mOnclick(holder));
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
				case R.id.dz_com_lv_iv_Zan:
					holder.iv_zan.setImageResource(R.drawable.ic_digg_pressed);
					break;

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
			MyLogger.jLog().i("不是隐藏");
			RefreshData();
		}else {
			MyLogger.jLog().i("隐藏");
		}
	}
	
	private void RefreshData(){
		Hot_txt.setText(String.valueOf(Integer.parseInt(duanzi.getComment())+1));
		list = db.selectComFromID(Integer.parseInt(duanzi.getPoid()));
		adapter.notifyDataSetChanged();
	}
	
	

}
