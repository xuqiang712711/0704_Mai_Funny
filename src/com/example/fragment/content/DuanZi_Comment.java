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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
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
		duanzi = (Duanzi) getArguments().getSerializable("duanzi");
		initView();
	}
	
	private void initView(){
//		mitem_top = (LinearLayout)view.findViewById(R.id.mitem_top);
		TextView textView = (TextView)view.findViewById(R.id.back2_text);
		textView.setText(getResources().getString(R.string.app_name));
		
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
//			Zan.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.ZAN_PRESSED), null, null, null);
//			Zan.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
			Zan_img.setImageResource(R.drawable.ic_digg_pressed);
			Zan_txt.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		}else {
//			Zan.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.ZAN_NORMAL), null, null, null);
//			Zan.setText(duanzi.getZan());
			Zan_img.setImageResource(R.drawable.ic_digg_normal);
			Zan_txt.setText(duanzi.getZan());
		}
		
		if (duanzi.isCaiPressed()) {
//			Cai.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.CAI_PRESSED), null, null, null);
//			Cai.setText(String.valueOf(Integer.parseInt(duanzi.getCai()) + 1));
			Cai_img.setImageResource(R.drawable.ic_digg_pressed);
			Cai_txt.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		}else {
//			Cai.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.CAI_NORMAL), null, null, null);
//			Cai.setText(duanzi.getCai());
			Cai_img.setImageResource(R.drawable.ic_bury_normal);
			Cai_txt.setText(duanzi.getCai());
		}
		Cai_txt.setOnClickListener(this);
		Zan_txt.setOnClickListener(this);
		Hot_txt.setOnClickListener(this);
		More.setOnClickListener(this);
		gif.setOnClickListener(this);
		
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
			imageLoader = ImageLoader.getInstance();
			if (imgUri.substring(imgUri.length() - 3, imgUri.length()).equals("gif")) {
//				FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) hint_img.getLayoutParams();
//				Log.e(Tag, "w  " + MaimobApplication.DeviceW / 2);
//				lp.setMargins(MaimobApplication.DeviceW / 2, 150, 0, 0);
//				hint_img.setLayoutParams(lp);
				
//				File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(), imageLoader.getDiskCache());
//				if (imgFile != null) {
//					int h = BitmapOptions.getWH(imgFile.toString(), MaimobApplication.DeviceW);
//					AbsListView.LayoutParams params = new AbsListView.LayoutParams(MaimobApplication.DeviceW,
//							h+180);
//					mitem_top.setLayoutParams(params);
//				}
				
//				hint_img.setVisibility(View.VISIBLE);
				imageLoader.displayImage(duanzi.getImageUrl(), gif, options);
			}else {
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
		case R.id.mitem_bottom_cai_txt:
			duanzi.CanPress(Duanzi.CAI, Cai_txt,Cai_img, getActivity());
			break;

		case R.id.mitem_bottom_hot_txt:
			Toast.makeText(getActivity(), "ȥ����", Toast.LENGTH_SHORT).show();
			DuanZi_Comment_Write comment_Test = new DuanZi_Comment_Write();
			switchFrag(DuanZi_Comment.this, comment_Test);
			break;
		case R.id.bottom_more:

			break;
		case R.id.mitem_bottom_zan_txt:
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
		}
	}
	
	
}
