package com.example.adapter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.example.application.MaimobApplication;
import com.example.fragment.content.DuanZi_Comment;
import com.example.listener.AnimateFirstDisplayListener;
import com.example.object.Duanzi;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.BitmapOptions;
import com.example.util.ConnToServer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class XAdapter extends BaseAdapter implements OnClickListener{
	public static int fontSize = 14;
	private Context context;
	private static Map<Integer, Boolean> isChecked_Cai;
	private static Map<Integer, Boolean> isChecked_Zan;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoader imageLoader;
	private Fragment mFragment;
	private static final int VIEW_TYPE_IMG = 0;
	private static final int VIEW_TYPE_GIF = 1;
	private int Image_Type = 0;
	private UMSocialService mController;
	private static Handler mHandler;
	private List<Duanzi> mdata;
	private LayoutInflater mInflater;
	private String TAG = "PicAdapter";
	
	public static final int ZAN_NORMAL = 1;
	public static final int ZAN_PRESSED = 2;
	public static final int CAI_NORMAL = 3;
	public static final int CAI_PRESSED = 4;
	
	public XAdapter(List<Duanzi> mdata, Handler handler,
			UMSocialService mController, Fragment mFragment, Context context){
		this.mdata = mdata;
		this.mHandler = handler;
		this.mController = mController;
		this.context = context;
		this.mFragment = mFragment;

		options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.showImageOnLoading(R.drawable.maimob)
		.showImageForEmptyUri(R.drawable.maimob)
		.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.displayer(new SimpleBitmapDisplayer()).build();
		mInflater = LayoutInflater.from(context);
		
		imageLoader = ImageLoader.getInstance();

		init();

	}
	
	private void init() {
		isChecked_Cai = new HashMap<Integer, Boolean>();
		isChecked_Zan = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mdata.size(); i++) {
			isChecked_Cai.put(i, false);
			isChecked_Zan.put(i, false);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView ==null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.mitem_test, null);
			holder.image = (ImageView)convertView.findViewById(R.id.mitem_test_img);
			holder.gif = (GifImageView)convertView.findViewById(R.id.mitem_test_gif);
			holder.user_name = (TextView) convertView
					.findViewById(R.id.mitem_username);

			holder.content = (TextView) convertView
					.findViewById(R.id.mitem_test_content);

			holder.zan = (TextView) convertView.findViewById(R.id.bottom_zan);
			holder.cai = (TextView) convertView.findViewById(R.id.bottom_cai);
			holder.hot = (TextView) convertView.findViewById(R.id.bottom_hot);
			holder.more = (ImageView) convertView.findViewById(R.id.bottom_more);
			holder.hint_img = (ImageView)convertView.findViewById(R.id.hint_img);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		Duanzi duanzi = mdata.get(position);
		String imgUri = duanzi.getImageUrl();
		String name = duanzi.getUserName();
		String content = duanzi.getContent();
		String cai = duanzi.getCai();
		String zan = duanzi.getZan();
		String hot = duanzi.getComment();
		
		imageLoader = ImageLoader.getInstance();
		if (imgUri.equals("") || imgUri == null) {
			holder.hint_img.setVisibility(View.GONE);
			holder.image.setVisibility(View.GONE);
			holder.gif.setVisibility(View.GONE);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
					AbsListView.LayoutParams.MATCH_PARENT);
			convertView.setLayoutParams(params);
			Log.e(TAG, "Text");
		}else if ((imgUri.substring(imgUri.length() - 3, imgUri.length())).equals("gif")) {
			holder.hint_img.setVisibility(View.VISIBLE);
			holder.image.setVisibility(View.GONE);
			holder.gif.setVisibility(View.VISIBLE);
			File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(), imageLoader.getDiskCache());
			int h = BitmapOptions.getWH(imgFile.toString(), MaimobApplication.DeviceW);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(MaimobApplication.DeviceW,
					h+200);
			convertView.setLayoutParams(params);
			imageLoader.displayImage(imgUri, holder.gif, options);
			Log.e(TAG, "GIF");
		}else {
			holder.hint_img.setVisibility(View.GONE);
			holder.gif.setVisibility(View.GONE);
			holder.image.setVisibility(View.VISIBLE);
			imageLoader.displayImage(imgUri, holder.image, options);
			Log.e(TAG, "image");
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
					AbsListView.LayoutParams.MATCH_PARENT);
			convertView.setLayoutParams(params);
		}
		
		if (duanzi.isZanPressed()== true) {
			holder.zan.setCompoundDrawables(duanzi.ChangePic(context, Duanzi.ZAN_PRESSED), null, null, null);
		}else {
			holder.zan.setCompoundDrawables(duanzi.ChangePic(context, Duanzi.ZAN_NORMAL), null, null, null);
		}
		if (duanzi.isCaiPressed() == true) {
			holder.cai.setCompoundDrawables(duanzi.ChangePic(context, Duanzi.CAI_PRESSED), null, null, null);
		}else {
			holder.cai.setCompoundDrawables(duanzi.ChangePic(context, Duanzi.CAI_NORMAL), null, null, null);
		}
		
		holder.cai.setText(cai);
		holder.zan.setText(zan);
		holder.hot.setText(hot);
		holder.user_name.setText(name);
		holder.content.setText(content);
		holder.content.setTextSize(fontSize);
		AddListen(holder, position);
		return convertView;
	}
	/**
	 * ����
	 * @param holder
	 * @param position
	 */
	public void AddListen(final ViewHolder holder, final int position){
		holder.cai.setTag(position);
		holder.cai.setOnClickListener(this);
		holder.zan.setTag(position);
		holder.zan.setOnClickListener(this);
		holder.hot.setTag(position);
		holder.hot.setOnClickListener(this);
		holder.more.setTag(position);
		holder.more.setOnClickListener(this);
		holder.image.setTag(position);
		holder.image.setOnClickListener(this);
//		holder.gif.setTag(position);
//		holder.gif.setOnClickListener(this);
//		holder.hint_img.setTag(position);
//		holder.hint_img.setOnClickListener(this);
		holder.gif.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder.hint_img.setVisibility(View.GONE);
				File cache = DiskCacheUtils.findInCache(mdata.get(position)
						.getImageUrl(), imageLoader.getDiskCache());
				try {
					GifDrawable gifDrawable = new GifDrawable(cache);
					((GifImageView)v).setImageDrawable(gifDrawable);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//δ��ͷ���û������м���
	}
	
	public static class ViewHolder{
		GifImageView gif;
		ImageView user_icon, more,image;
		TextView user_name, content, comment;
		TextView cai, zan, hot;
		ImageView cai_img, zan_img, hint_img;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();
		Bundle bundle = new Bundle();
		Duanzi duanzi = (Duanzi) getItem(position);
		bundle.putSerializable("duanzi", duanzi);
		switch (v.getId()) {
		case R.id.mitem_test_gif:
			Log.e(TAG, "mitem_test_gif");
			
			
			break;
		case R.id.hint_img:
			Log.e(TAG, "hint_img");
//			((ImageView)v).setVisibility(View.GONE);
			
			break;
		case R.id.mitem_test_img:

			break;
		case R.id.mitem_test_content:

			break;
		case R.id.bottom_zan:
			Log.e(TAG, "Zan " + position);
			if (duanzi.isZanPressed()== false) {
				if (duanzi.isCaiPressed() == true) {
					Toast.makeText(context, "���Ѿ��ȹ�", Toast.LENGTH_SHORT).show();
					break;
				}
				Drawable drawable = duanzi.ChangePic(context, Duanzi.ZAN_PRESSED);
				((TextView) v).setCompoundDrawables(drawable, null, null, null);
				int Zan_num = Integer.parseInt(mdata.get(position).getZan());
				((TextView) v).setText(String.valueOf(Zan_num + 1));
//				isChecked_Zan.put(position, true);
				duanzi.setZanPressed(true);
				ConnToServer.DohttpNoResult(ConnToServer.ZAN, duanzi.getPoid());
			} else {
				Toast.makeText(context, "���Ѿ��޹�", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.bottom_cai:
			if (duanzi.isCaiPressed() == false) {
				if (duanzi.isZanPressed() == true) {
					Toast.makeText(context, "���Ѿ��޹�", Toast.LENGTH_SHORT).show();
					break;
				}
				Drawable drawable = duanzi.ChangePic(context, Duanzi.CAI_PRESSED);
				((TextView) v).setCompoundDrawables(drawable, null, null, null);
				int Cai_num = Integer.parseInt(mdata.get(position).getCai());
				((TextView) v).setText(String.valueOf(Cai_num + 1));
				duanzi.setCaiPressed(true);
				ConnToServer.DohttpNoResult(ConnToServer.CAI,duanzi.getPoid());
			} else {
				Toast.makeText(context, "���Ѿ��ȹ�", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.bottom_hot:
			DuanZi_Comment comment3 = new DuanZi_Comment();
			switchFragment(mFragment, comment3, bundle);
			Toast.makeText(context, "�������  +  " + position, Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.bottom_more:
			String imgUri = mdata.get(position).getImageUrl();
			Log.e(TAG, "imgUri  "+ imgUri);
			if (imgUri.equals("") || imgUri == null) {
				UMShare(mdata.get(position).getContent(), null);
			}else {
				UMShare(mdata.get(position).getContent(),
						imgUri);
			}
			mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
					SHARE_MEDIA.DOUBAN);
			mController.openShare((Activity) context, false);
			break;
		}
	}
	/**
	 * ������
	 * @param content
	 * @param uri
	 */
	private void UMShare(String content, String uri) {
		mController.setShareContent(content);
		if (uri != null) {
			mController.setShareMedia(new UMImage(context, uri));
		}
	}

	public void switchFragment(Fragment from, Fragment to, Bundle bundle) {
		if (context == null) {
			return;
		}
		if (context instanceof XYFTEST) {
			XYFTEST xyftest = (XYFTEST) context;
			xyftest.switchContentFullwithBundle(from, to, bundle);
		}
	}
	
	public static void SetNormal(){
		Message msg = Message.obtain();
		msg.what = 313;
		mHandler.sendMessage(msg);
	}

}
