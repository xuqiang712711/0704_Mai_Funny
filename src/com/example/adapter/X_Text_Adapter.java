package com.example.adapter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.example.Activity.MaiActivity;
import com.example.adapter.XAdapter.ViewHolder;
import com.example.application.MaimobApplication;
import com.example.fragment.content.DuanZi_Comment;
import com.example.listener.AnimateFirstDisplayListener;
import com.example.object.Duanzi;
import com.example.tab.R;
import com.example.util.BitmapOptions;
import com.example.util.ConnToServer;
import com.example.util.Uris;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class X_Text_Adapter extends BaseAdapter{
	public static int fontSize = 14;
	private Context context;
	private static Map<Integer, Boolean> isChecked_Cai;
	private static Map<Integer, Boolean> isChecked_Zan;
	private Fragment mFragment;
	private UMSocialService mController;
	private static Handler mHandler;
	private List<Duanzi> mdata;
	private LayoutInflater mInflater;
	private String TAG = "XAdapter";
	
	public static final int ZAN_NORMAL = 1;
	public static final int ZAN_PRESSED = 2;
	public static final int CAI_NORMAL = 3;
	public static final int CAI_PRESSED = 4;
	
	public X_Text_Adapter(List<Duanzi> mdata, Handler handler,
			UMSocialService mController, Fragment mFragment, Context context){
		this.mdata = mdata;
		this.mHandler = handler;
		this.mController = mController;
		this.context = context;
		this.mFragment = mFragment;
		mInflater = LayoutInflater.from(context);
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
			convertView = mInflater.inflate(R.layout.mitem_text, null);
			holder.user_name = (TextView) convertView
					.findViewById(R.id.mitem_username);

			holder.content = (TextView) convertView
					.findViewById(R.id.mitem_test_content);

			holder.zan_img = (ImageView)convertView.findViewById(R.id.mitem_bottom_zan_img);
			holder.cai_img = (ImageView)convertView.findViewById(R.id.mitem_bottom_cai_img);
			
			holder.zan = (TextView) convertView.findViewById(R.id.mitem_bottom_zan_txt);
			holder.cai = (TextView) convertView.findViewById(R.id.mitem_bottom_cai_txt);
			holder.hot = (TextView) convertView.findViewById(R.id.mitem_bottom_hot_txt);
			holder.more = (ImageView) convertView.findViewById(R.id.bottom_more);
			holder.layout_cai = (RelativeLayout)convertView.findViewById(R.id.mitem_bottom_cai);
			holder.layout_zan = (RelativeLayout)convertView.findViewById(R.id.mitem_bottom_zan);
			holder.layout_hot = (RelativeLayout)convertView.findViewById(R.id.mitem_bottom_hot);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		Duanzi duanzi = mdata.get(position);
		String name = duanzi.getUserName();
		String content = duanzi.getContent();
		String cai = duanzi.getCai();
		String zan = duanzi.getZan();
		String hot = duanzi.getComment();
		
		if (duanzi.isZanPressed()== true) {
			holder.zan_img.setImageResource(R.drawable.ic_digg_pressed);
		}else {
			holder.zan_img.setImageResource(R.drawable.ic_digg_normal);
		}
		if (duanzi.isCaiPressed() == true) {
			holder.cai_img.setImageResource(R.drawable.ic_bury_pressed);
		}else {
			holder.cai_img.setImageResource(R.drawable.ic_bury_normal);
		}
		
		holder.cai.setText(cai);
		holder.zan.setText(zan);
		holder.hot.setText(hot);
		holder.user_name.setText(name);
		holder.content.setText(content);
		holder.content.setTextSize(Uris.Font_Size);
		AddListen(holder, position);
		return convertView;
	}
	/**
	 * 监听
	 * @param holder
	 * @param position
	 */
	public void AddListen(final ViewHolder holder, final int position){
//		holder.cai.setTag(position);
//		holder.cai.setOnClickListener(this);
//		holder.zan.setTag(position);
//		holder.zan.setOnClickListener(this);
//		holder.hot.setTag(position);
//		holder.hot.setOnClickListener(this);
		
		holder.layout_cai.setOnClickListener(new mOnclick(position, holder));
		holder.layout_hot.setOnClickListener(new mOnclick(position, holder));
		holder.layout_zan.setOnClickListener(new mOnclick(position, holder));
		holder.more.setOnClickListener(new mOnclick(position, holder));
//		holder.gif.setTag(position);
//		holder.gif.setOnClickListener(this);
//		holder.hint_img.setTag(position);
//		holder.hint_img.setOnClickListener(this);
		
		//未对头像、用户名进行监听
	}
	
	public static class ViewHolder{
		ImageView user_icon, more;
		TextView user_name, content, comment;
		TextView cai, zan, hot;
		ImageView cai_img, zan_img, hot_img, hint_img;
		RelativeLayout layout_cai, layout_zan, layout_hot;
	}
	
	class mOnclick implements OnClickListener{
		ViewHolder holder = null;
		int position = 0;
		Duanzi duanzi;
		Bundle bundle;
		public mOnclick(int position ,ViewHolder holder){
			this.holder = holder;
			this.position = position;
			bundle = new Bundle();
			duanzi = (Duanzi) getItem(position);
			bundle.putSerializable("duanzi", duanzi);
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			
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
			
			case R.id.mitem_bottom_zan:
				Log.e(TAG, "Zan " + position);
				if (duanzi.isZanPressed()== false) {
					if (duanzi.isCaiPressed() == true) {
						Toast.makeText(context, "你已经踩过", Toast.LENGTH_SHORT).show();
						break;
					}
//					int Zan_num = Integer.parseInt(mdata.get(position).getZan());
//					holder.zan_img.setImageResource(R.drawable.ic_digg_pressed);
//					holder.zan.setText(String.valueOf(Zan_num + 1));
//					duanzi.setZanPressed(true);
					duanzi.CanPress(Duanzi.ZAN, holder.zan, holder.zan_img, context);
					ConnToServer.DohttpNoResult(ConnToServer.ZAN, duanzi.getPoid());
				} else {
					Toast.makeText(context, "你已经赞过", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.mitem_bottom_cai:
				if (duanzi.isCaiPressed() == false) {
					if (duanzi.isZanPressed() == true) {
						Toast.makeText(context, "你已经赞过", Toast.LENGTH_SHORT).show();
						break;
					}
//					int Cai_num = Integer.parseInt(mdata.get(position).getCai());
//					holder.cai_img.setImageResource(R.drawable.ic_bury_pressed);
//					holder.cai.setText(String.valueOf(Cai_num + 1));
//					duanzi.setCaiPressed(true);
					duanzi.CanPress(Duanzi.CAI, holder.cai, holder.cai_img, context);
					ConnToServer.DohttpNoResult(ConnToServer.CAI,duanzi.getPoid());
				} else {
					Toast.makeText(context, "你已经踩过", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.mitem_bottom_hot:
				DuanZi_Comment comment3 = new DuanZi_Comment();
				switchFragment(mFragment, comment3, bundle);
				Toast.makeText(context, "点击热门  +  " + position, Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
		
	}
	/**
	 * 分享功能
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
		if (context instanceof MaiActivity) {
			MaiActivity xyftest = (MaiActivity) context;
			xyftest.switchContentFullwithBundle(from, to, bundle);
		}
	}
	
	public static void SetNormal(){
		Message msg = Message.obtain();
		msg.what = 313;
		mHandler.sendMessage(msg);
	}

}
