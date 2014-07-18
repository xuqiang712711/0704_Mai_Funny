package com.example.fragment.content;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.BitmapOptions;
import com.example.util.ConnToServer;
import com.example.util.CustomImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.umeng.socialize.controller.utils.ToastUtil;

public class DuanZi_Comment extends Fragment implements OnClickListener{
	private String Tag = "DuanZi_Comment";
	private View view;
	private ImageView user_icon,image,More,hint_img,Zan_img, Cai_img;
	private TextView user_name,Zan_txt,Cai_txt,Hot_txt;
	private TextView content;
	private RelativeLayout Zan_layout, Cai_layout, Hot_layout;
	private DisplayImageOptions options;
	private Duanzi duanzi;
	private GifImageView gif;
	private ImageLoader imageLoader;
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
	}
	
	private void initView(){
//		mitem_top = (LinearLayout)view.findViewById(R.id.mitem_top);
		TextView textView = (TextView)view.findViewById(R.id.top_text);
		textView.setText(getResources().getString(R.string.app_name));
		Button back = (Button)view.findViewById(R.id.top_left);
		Button report = (Button)view.findViewById(R.id.top_right);
		
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
		
		Log.e("yyy", "name  " + duanzi.getUserName());
		user_name.setText(duanzi.getUserName());
		content.setText(duanzi.getContent());
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
		if (image != null && !imgUri.equals("")) {
			options = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.showImageOnLoading(R.drawable.maimob)
			.showImageForEmptyUri(R.drawable.maimob)
			.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.displayer(new SimpleBitmapDisplayer()).build();
			image.setVisibility(View.VISIBLE);
			if (imgUri.substring(imgUri.length() - 3, imgUri.length()).equals("gif")) {
				File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(),
						imageLoader.getDiskCache());
				if (imgFile != null && !imgFile.equals("")) {
					int ReqHeight = BitmapOptions.getWH(imgFile.toString(), MaimobApplication.DeviceW);
					FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) gif.getLayoutParams();
					params.height = ReqHeight;
					params.width = FrameLayout.LayoutParams.MATCH_PARENT;
					gif.setLayoutParams(params);
				}
				hint_img.setVisibility(View.VISIBLE);
				image.setVisibility(View.GONE);
				gif.setVisibility(View.VISIBLE);
				imageLoader.displayImage(duanzi.getImageUrl(), gif, options);
			}else {
				hint_img.setVisibility(View.GONE);
				gif.setVisibility(View.GONE);
				imageLoader.displayImage(duanzi.getImageUrl(), image, options);
			}
		}
		
	}
	private void switchFrag(Fragment from, Fragment to){
		Bundle bundle = new Bundle();
		bundle.putSerializable("commit", duanzi);
		XYFTEST xyftest = (XYFTEST) getActivity();
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
			Toast.makeText(getActivity(), "È¥ÆÀÂÛ", Toast.LENGTH_SHORT).show();
			DuanZi_Comment_Write comment_Test = new DuanZi_Comment_Write();
			switchFrag(DuanZi_Comment.this, comment_Test);
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
				File cache = DiskCacheUtils.findInCache(duanzi.getImageUrl(), imageLoader.getDiskCache());
				try {
					Log.e(Tag, "xwkkx");
					GifDrawable gifDrawable = new GifDrawable(cache);
					((GifImageView)v).setImageDrawable(gifDrawable);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			break;
		case R.id.top_left:
			mFragmentManage.BackStatck(getActivity());
			break;
		case R.id.top_right:
			mFragmentManage.SwitchFrag(getActivity(), DuanZi_Comment.this, new Duanzi_Report(), null);
			break;
		}
	}
	
	
}
