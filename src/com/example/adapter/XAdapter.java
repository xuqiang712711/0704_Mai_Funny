package com.example.adapter;

import im.yixin.sdk.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.example.Activity.MaiActivity;
import com.example.AsyTask.RequestDataTask;
import com.example.application.MaimobApplication;
import com.example.fragment.content.DuanZi_Comment;
import com.example.fragment.content.DuanZi_Comment_Write;
import com.example.fragment.content.Duanzi_More_Comment;
import com.example.fragment.content.Duanzi_Pop_Zhuanfa;
import com.example.fragment.content.My_login_select;
import com.example.listener.AnimateFirstDisplayListener;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.BitmapOptions;
import com.example.util.ConnToServer;
import com.example.util.DialogToastUtil;
import com.example.util.MyLogger;
import com.example.util.ShareUtil;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.example.util.Uris;
import com.example.util.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XAdapter extends BaseAdapter{
	public static int fontSize = 14;
	private Context context;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Fragment mFragment;
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
	
	private Mai_DBhelper dBhelper;
	
	public XAdapter(List<Duanzi> mdata, Handler handler,
			UMSocialService mController, Fragment mFragment, Context context){
		this.mHandler = handler;
		this.mdata = mdata;
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
		dBhelper = Mai_DBhelper.getInstance(context);
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
			holder.user_icon = (ImageView)convertView.findViewById(R.id.mitem_icon);

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
		String user_head = duanzi.getUserIcon();
		String imgUri = duanzi.getImageUrl();
		String name = duanzi.getUserName();
		String content = duanzi.getContent();
		String cai = duanzi.getCai();
		String zan = duanzi.getZan();
		String hot = duanzi.getComment();
		imageLoader = MaimobApplication.imageLoader;
		holder.image.setVisibility(View.GONE);
		holder.gif.setVisibility(View.GONE);
		holder.hint_img.setVisibility(View.GONE);
		
		
		if (user_head != null && !user_head.equals("")) {
			imageLoader.displayImage(user_head, holder.user_icon, options);
		}
		if (imgUri != null &&!imgUri.equals("")) {
			String currImgUrl = StringUtils.checkImgPath(imgUri);
			Log.e(TAG, "curr  " +currImgUrl + "  img  " + imgUri);
			if ((currImgUrl.substring(currImgUrl.length() - 3, currImgUrl.length()))
					.equals("gif")) {
				holder.hint_img.setVisibility(View.VISIBLE);
				holder.gif.setVisibility(View.VISIBLE);
				imageLoader.displayImage(currImgUrl, holder.gif, options);
				File imgFile = DiskCacheUtils.findInCache(duanzi.getImageUrl(),
						imageLoader.getDiskCache());
				if (imgFile != null) {
					int h = BitmapOptions.getWH(imgFile.toString(),
							MaimobApplication.DeviceW);
					FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) holder.gif.getLayoutParams();
					params.height = h;
					params.width = FrameLayout.LayoutParams.MATCH_PARENT;
					holder.gif.setLayoutParams(params);
				}else if (currImgUrl.startsWith("file")) {
					int h = BitmapOptions.getWH(imgUri, MaimobApplication.DeviceW);
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
				holder.image.setVisibility(View.VISIBLE);
				imageLoader.displayImage(currImgUrl, holder.image, options);
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
				((GifImageView)v).setImageDrawable(StringUtils.checkImgPathForGif(duanzi.getImageUrl()));
				break;
			
			case R.id.bottom_more:
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
//				duanzi.setMedia(Duanzi);
//				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_comment_write_qzone:
//				duanzi.setMedia(SHARE_MEDIA.RENREN);
//				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_comment_write_tencent:
//				duanzi.setMedia(SHARE_MEDIA.TENCENT);
//				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
			case R.id.duanzi_comment_write_douban:
//				duanzi.setMedia(SHARE_MEDIA.DOUBAN);
//				mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_More_Comment(), bundle);
				break;
				
			case R.id.duanzi_more_fav:
				if (duanzi.isFav()) {
					duanzi.setFav(false);
					window.dismiss();
					dBhelper.cancelFav(Integer.parseInt(duanzi.getPoid()));
					DialogToastUtil.toastShow(context, "取消收藏成功");
				}else {
					dBhelper.updateFav(Integer.parseInt(duanzi.getPoid()));
					Log.e(TAG, "FAV");
					RequestDataTask reqTask = new RequestDataTask(adapterHandler);
					reqTask.execute(ConnToServer.getUrl(ConnToServer.FAV, duanzi.getPoid()));
					duanzi.setFav(true);
					boolean isZhuanfa = (Boolean) SharedPreferencesUtils.getParam("setting", context, "isZhuanfa", false);
					//是否勾选设置中的收藏同时转发
					if (isZhuanfa) {
						if ((Integer) SharedPreferencesUtils.getParam("platform", context, "sina", 0)== 1) {
							ShareUtil.ShareToSocial(SHARE_MEDIA.SINA, null, duanzi.getContent(), duanzi.getImageUrl(), context, null);
						}else if (SharedPreferencesUtils.getParam("platform", context, "tencent", 0).equals("1")) {
							ShareUtil.ShareToSocial(SHARE_MEDIA.TENCENT, null, duanzi.getContent(), duanzi.getImageUrl(), context, null);
						}else if (SharedPreferencesUtils.getParam("platform", context, "renren", 0).equals("1")) {
							ShareUtil.ShareToSocial(SHARE_MEDIA.RENREN, null, duanzi.getContent(), duanzi.getImageUrl(), context, null);
						}else if (SharedPreferencesUtils.getParam("platform", context, "douban", 0).equals("1")) {
							ShareUtil.ShareToSocial(SHARE_MEDIA.DOUBAN, null, duanzi.getContent(), duanzi.getImageUrl(), context, null);
						}else {
							ToastUtil.showToast(context, "你尚未绑定账号,无法转发");
						}
					}
				}
				break;
//			case R.id.duanzi_more_zhuanfa:
//				duanzi.setNeedComment(false);
//				mFragmentManage.SwitchFrag(context, mFragment, new DuanZi_Comment_Write(), bundle);
//				window.dismiss();
//				break;
			case R.id.duanzi_more_back:
				window.dismiss();
				break;
			case R.id.duanzi_pop_Weixin:
				ShareUtil.shareToWeiXin(duanzi, context, adapterHandler);
				break;
			case R.id.duanzi_pop_Weixin_Circle:
				ShareUtil.shareToWeiXinCircle(duanzi, context);
				break;
			case R.id.duanzi_pop_sina:
				window.dismiss();
				if (User.UserIsExists(context)) {
					duanzi.setMedia(Duanzi.SHARE_MEDIA_SINA);
					mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_Pop_Zhuanfa(), bundle);
				}else {
					mFragmentManage.SwitchFrag(context, mFragment, new My_login_select(), bundle);
				}
				
				break;
			case R.id.duanzi_pop_tencent:
				window.dismiss();
				if (User.UserIsExists(context)) {
					duanzi.setMedia(Duanzi.SHARE_MEDIA_TENCENT);
					mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_Pop_Zhuanfa(), bundle);
				}else {
					mFragmentManage.SwitchFrag(context, mFragment, new My_login_select(), bundle);
				}
				break;
			case R.id.duanzi_pop_renren:
				window.dismiss();
				if (User.UserIsExists(context)) {
					duanzi.setMedia(Duanzi.SHARE_MEDIA_RENREN);
					mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_Pop_Zhuanfa(), bundle);
				}else {
					mFragmentManage.SwitchFrag(context, mFragment, new My_login_select(), bundle);
				}
				break;
			case R.id.duanzi_pop_douban:
				window.dismiss();
				if (User.UserIsExists(context)) {
					duanzi.setMedia(Duanzi.SHARE_MEDIA_DOUBAN);
					mFragmentManage.SwitchFrag(context, mFragment, new Duanzi_Pop_Zhuanfa(), bundle);
				}else {
					mFragmentManage.SwitchFrag(context, mFragment, new My_login_select(), bundle);
				}
				
				break;
			case R.id.duanzi_pop_qq:
				break;
			case R.id.duanzi_pop_qq_zone:
				break;
			}
		}
		
	}
	
	public void initPop(int position, ViewHolder holder){
		Duanzi duanzi = (Duanzi) getItem(position);
		Log.e(TAG, "position  " + position);
		View popView = mInflater.inflate(R.layout.duanzi_more_pop, null);
		TextView tv_pop_text = (TextView)popView.findViewById(R.id.duanzi_more_zhuanfa_text);
		if (duanzi.isFav()) {
			tv_pop_text.setText("取消收藏");
		}else {
			tv_pop_text.setText("收藏");
		}
		
//		ImageView iv_zhuanfa = (ImageView)popView.findViewById(R.id.duanzi_more_zhuanfa);
//		iv_zhuanfa.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_Rep = (ImageView)popView.findViewById(R.id.duanzi_more_rep);
		iv_Rep.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_fav = (ImageView)popView.findViewById(R.id.duanzi_more_fav);
		iv_fav.setOnClickListener(new mOnclick(position, holder));
		Button bt_back = (Button)popView.findViewById(R.id.duanzi_more_back);
		bt_back.setOnClickListener(new mOnclick(position, holder));
		
		ImageView iv_weixin = (ImageView)popView.findViewById(R.id.duanzi_pop_Weixin);
		iv_weixin.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_weixin_circle = (ImageView)popView.findViewById(R.id.duanzi_pop_Weixin_Circle);
		iv_weixin_circle.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_sina = (ImageView)popView.findViewById(R.id.duanzi_pop_sina);
		iv_sina.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_tencent = (ImageView)popView.findViewById(R.id.duanzi_pop_tencent);
		iv_tencent.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_renren = (ImageView)popView.findViewById(R.id.duanzi_pop_renren);
		iv_renren.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_douban = (ImageView)popView.findViewById(R.id.duanzi_pop_douban);
		iv_douban.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_qq = (ImageView)popView.findViewById(R.id.duanzi_pop_qq);
		iv_qq.setOnClickListener(new mOnclick(position, holder));
		ImageView iv_qq_zone = (ImageView)popView.findViewById(R.id.duanzi_pop_qq_zone);
		iv_qq_zone.setOnClickListener(new mOnclick(position, holder));
		
		window = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
		if (context instanceof MaiActivity) {
			MaiActivity xyftest = (MaiActivity) context;
			xyftest.switchContentFullwithBundle(from, to, bundle);
		}
	}
	
	public static void SetNormal(){
		MyLogger logger = MyLogger.jLog();
		logger.i("Xadapter_changeFont");
		if (mHandler == null) {
			logger.i("mHandler is null");
			return;
		}
		Message msg = Message.obtain();
		msg.what = Uris.MSG_CHANGEFONT;
		mHandler.sendMessage(msg);
	}
	
	private Handler adapterHandler = new Handler(){
		public void handleMessage(Message msg) {
			ToastUtil.showToast(context, "收藏成功");
			window.dismiss();
		}
	};
	
	public void Refresh(){
		notifyDataSetChanged();
	}

}
