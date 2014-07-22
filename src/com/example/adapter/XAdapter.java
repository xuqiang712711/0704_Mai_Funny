package com.example.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.example.AsyTask.RequestDataTask;
import com.example.application.MaimobApplication;
import com.example.fragment.content.DuanZi_Comment;
import com.example.fragment.content.Duanzi_More_Comment;
import com.example.listener.AnimateFirstDisplayListener;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.BitmapOptions;
import com.example.util.ConnToServer;
import com.example.util.ShareUtil;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.utils.ToastUtil;
import com.umeng.socialize.media.UMImage;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XAdapter extends BaseAdapter{
	public static int fontSize = 14;
	private Context context;
	private static Map<Integer, Boolean> isChecked_Cai;
	private static Map<Integer, Boolean> isChecked_Zan;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Fragment mFragment;
	private static final int VIEW_TYPE_IMG = 0;
	private static final int VIEW_TYPE_GIF = 1;
	private int Image_Type = 0;
	private UMSocialService mController;
	private static Handler mHandler;
	private List<Duanzi> mdata;
	private LayoutInflater mInflater;
	private String TAG = "XAdapter";
	private PopupWindow window;
	
	public static final int ZAN_NORMAL = 1;
	public static final int ZAN_PRESSED = 2;
	public static final int CAI_NORMAL = 3;
	public static final int CAI_PRESSED = 4;
	
	private ImageLoader imageLoader;
	
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
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		mInflater = LayoutInflater.from(context);
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
			holder.hint_img = (ImageView)convertView.findViewById(R.id.mitem_hint_img);
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
		String imgUri = duanzi.getImageUrl();
		String name = duanzi.getUserName();
		String content = duanzi.getContent();
		String cai = duanzi.getCai();
		String zan = duanzi.getZan();
		String hot = duanzi.getComment();
		imageLoader = MaimobApplication.imageLoader;
		
		if (!imgUri.equals("") && imgUri != null) {
			Log.e(TAG, "img  " + imgUri);
			if ((imgUri.substring(imgUri.length() - 3, imgUri.length()))
					.equals("gif")) {
				holder.hint_img.setVisibility(View.VISIBLE);
				holder.image.setVisibility(View.GONE);
				holder.gif.setVisibility(View.VISIBLE);
				imageLoader.displayImage(imgUri, holder.gif, options);
				File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(),
						imageLoader.getDiskCache());
				Log.e(TAG, "imgUri  " + imgFile);
				if (imgFile != null) {
					int h = BitmapOptions.getWH(imgFile.toString(),
							MaimobApplication.DeviceW);
//					AbsListView.LayoutParams params = new AbsListView.LayoutParams(
//							MaimobApplication.DeviceW, h + 220);
//					convertView.setLayoutParams(params);
					FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) holder.gif.getLayoutParams();
					params.height = h;
					params.width = FrameLayout.LayoutParams.MATCH_PARENT;
					holder.gif.setLayoutParams(params);
				}
				Log.e(TAG, "GIF");
			} else {
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT,
						AbsListView.LayoutParams.MATCH_PARENT);
				convertView.setLayoutParams(params);
				holder.hint_img.setVisibility(View.GONE);
				holder.gif.setVisibility(View.GONE);
				holder.image.setVisibility(View.VISIBLE);
				imageLoader.displayImage(imgUri, holder.image, options);
				Log.e(TAG, "image");
			}
			
		}
		
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
//		initPop(position, content);
		
		return convertView;
	}
	/**
	 * 监听
	 * @param holder
	 * @param position
	 */
	public void AddListen(final ViewHolder holder, final int position){
		
		holder.layout_cai.setOnClickListener(new mOnclick(position, holder));
		holder.layout_hot.setOnClickListener(new mOnclick(position, holder));
		holder.layout_zan.setOnClickListener(new mOnclick(position, holder));
		holder.more.setOnClickListener(new mOnclick(position, holder));
		holder.image.setOnClickListener(new mOnclick(position, holder));
		holder.gif.setOnClickListener(new mOnclick(position, holder));
		//未对头像、用户名进行监听
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
			
			case R.id.mitem_test_gif:
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
				break;
			
			case R.id.bottom_more:
//				String imgUri = mdata.get(position).getImageUrl();
//				Log.e(TAG, "imgUri  "+ imgUri);
//				if (imgUri.equals("") || imgUri == null) {
//					UMShare(mdata.get(position).getContent(), null);
//				}else {
//					UMShare(mdata.get(position).getContent(),
//							imgUri);
//				}
//				mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
//						SHARE_MEDIA.DOUBAN);
//				mController.openShare((Activity) context, false);
				initPop(position, holder);
				window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
				break;
			
			case R.id.mitem_bottom_zan:
				if (duanzi.isZanPressed()== false) {
					if (duanzi.isCaiPressed() == true) {
						Toast.makeText(context, "你已经踩过", Toast.LENGTH_SHORT).show();
						break;
					}
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
			case R.id.duanzi_comment_write_sina:
				duanzi.setMedia(SHARE_MEDIA.SINA);
				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_comment_write_qzone:
				duanzi.setMedia(SHARE_MEDIA.RENREN);
				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_comment_write_tencent:
				duanzi.setMedia(SHARE_MEDIA.TENCENT);
				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_comment_write_douban:
				duanzi.setMedia(SHARE_MEDIA.DOUBAN);
				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_more_fav:
//				RequestDataTask reqTask = new RequestDataTask(adapterHandler);
//				reqTask.execute(Uris.FAV);
				ConnToServer.DohttpNoResult(ConnToServer.CAI,duanzi.getPoid());
				break;
			}
		}
		
	}
	
	public void initPop(int position, ViewHolder holder){
		Log.e(TAG, "position  " + position);
		View popView = mInflater.inflate(R.layout.duanzi_more_pop, null);
		ImageView iv_sina = (ImageView)popView.findViewById(R.id.duanzi_comment_write_sina);
		iv_sina.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_tencent = (ImageView)popView.findViewById(R.id.duanzi_comment_write_tencent);
		iv_tencent.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_renren = (ImageView)popView.findViewById(R.id.duanzi_comment_write_qzone);
		iv_renren.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_douban = (ImageView)popView.findViewById(R.id.duanzi_comment_write_douban);
		iv_douban.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_fav = (ImageView)popView.findViewById(R.id.duanzi_more_fav);
		iv_fav.setOnClickListener(new mOnclick(position, holder));
		
		window = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.setFocusable(true);
	}
	
	public static class ViewHolder{
		GifImageView gif;
		ImageView user_icon, more,image;
		TextView user_name, content, comment;
		TextView cai, zan, hot;
		ImageView cai_img, zan_img, hot_img, hint_img;
		RelativeLayout layout_cai, layout_zan, layout_hot;
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
		if (mHandler == null) {
			return;
		}
		Message msg = Message.obtain();
		msg.what = Uris.MSG_CHANGEFONT;
		mHandler.sendMessage(msg);
	}
	
	private Handler adapterHandler = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what == Uris.MSG_SUC) {
				ToastUtil.showToast(context, "收藏成功");
			}else {
				ToastUtil.showToast(context, "你已经收藏过了");
			}
			window.dismiss();
		}
	};

}
