package com.example.fragment.content;

import java.io.File;

import pl.droidsonroids.gif.GifImageView;

import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.BitmapOptions;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.ShareUtil;
import com.example.util.StringUtils;
import com.example.util.Uris;
import com.example.util.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;

public class Duanzi_Comments_Lv_head extends RelativeLayout implements OnClickListener{
	private ImageView user_icon,image,More,hint_img,Zan_img, Cai_img,iv_fav;
	private TextView user_name,Zan_txt,Cai_txt,Hot_txt,Zan_add, Cai_add,content;
	private RelativeLayout Zan_layout, Cai_layout, Hot_layout;
	private GifImageView gif;
	private View view;
	private Duanzi duanzi;
	private ImageLoader imageLoader;
	private Context context;
	private Fragment mFragment;
	private Animation mAnimation;
	private PopupWindow window;
	public Duanzi_Comments_Lv_head(Context context) {
		super(context);
		view = LayoutInflater.from(context).inflate(R.layout.duanzi_comments_lv_head, null);
		addView(view);
		imageLoader = MaimobApplication.imageLoader;
		this.context = context;
	}
	
	public void addDuanzi(Duanzi duanzi,Fragment fragment){
		this.duanzi = duanzi;
		this.mFragment = fragment;
		initView();
	}
	
	public void LayoutChange(){
		duanzi.setComment(String.valueOf(Integer.parseInt(duanzi.getComment()) +1));
		Hot_txt.setText(duanzi.getComment());
	}
	
	private void initView(){
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
		Cai_img = (ImageView)view.findViewById(R.id.mitem_bottom_cai_img_diao);
		Zan_img = (ImageView)view.findViewById(R.id.mitem_bottom_zan_img);
		Zan_layout = (RelativeLayout)view.findViewById(R.id.mitem_bottom_zan);
		Cai_layout = (RelativeLayout)view.findViewById(R.id.mitem_bottom_cai);
		Hot_layout = (RelativeLayout)view.findViewById(R.id.mitem_bottom_hot);
		Zan_add = (TextView)view.findViewById(R.id.mitem_bottom_zan_tv_add);
		Cai_add = (TextView)view.findViewById(R.id.mitem_bottom_cai_tv_add);
		
		Zan_add.setVisibility(View.GONE);
		Cai_add.setVisibility(View.GONE);
		iv_fav.setVisibility(View.GONE);
		user_name.setText(duanzi.getUserName());
		content.setText(duanzi.getContent());
		content.setTextSize(Uris.Font_Size);
		Cai_txt.setText(String.valueOf(Integer.parseInt(duanzi.getCai()) + 1));
		
		Zan_txt.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		Hot_txt.setText(duanzi.getComment());
		
		MyLogger.jLog().i("zan_count  " + duanzi.getZan());
		if (duanzi.isZanPressed()) {
			Zan_img.setImageResource(R.drawable.ic_digg_pressed);
			Zan_txt.setText(duanzi.getZan());
		}else {
			Zan_img.setImageResource(R.drawable.ic_digg_normal);
			Zan_txt.setText(duanzi.getZan());
		}
		
		if (duanzi.isCaiPressed()) {
			Cai_img.setImageResource(R.drawable.ic_digg_pressed);
			Cai_txt.setText(duanzi.getCai());
		}else {
			Cai_img.setImageResource(R.drawable.ic_bury_normal);
			Cai_txt.setText(duanzi.getCai());
		}
		More.setOnClickListener(this);
		gif.setOnClickListener(this);
		Zan_layout.setOnClickListener(this);
		Cai_layout.setOnClickListener(this);
		Hot_layout.setOnClickListener(this);
		
		String imgUri = duanzi.getImageUrl();
		if (imgUri != null && !imgUri.equals("")) {
			String currImgUrl = StringUtils.checkImgPath(imgUri);
			image.setVisibility(View.VISIBLE);
			String currName = StringUtils.checkImgPath(imgUri);
			if (imgUri.substring(imgUri.length() - 3, imgUri.length()).equals("gif")) {
				hint_img.setVisibility(View.VISIBLE);
				gif.setVisibility(View.VISIBLE);
				imageLoader.displayImage(currImgUrl, gif, ImageUtil.getOption());
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
				imageLoader.displayImage(currName, image, ImageUtil.getOption());
			}
		}
		
	}

	
	private void initPop(){
		View popView = LayoutInflater.from(context).inflate(R.layout.duanzi_more_pop, null);
		Button bt_back = (Button)popView.findViewById(R.id.duanzi_more_back);
		bt_back.setOnClickListener(this);
		
		ImageView iv_weixin = (ImageView)popView.findViewById(R.id.duanzi_pop_Weixin);
		iv_weixin.setOnClickListener(this);
		ImageView iv_weixin_circle = (ImageView)popView.findViewById(R.id.duanzi_pop_Weixin_Circle);
		iv_weixin_circle.setOnClickListener(this);
		ImageView iv_sina = (ImageView)popView.findViewById(R.id.duanzi_pop_sina);
		iv_sina.setOnClickListener(this);
		ImageView iv_tencent = (ImageView)popView.findViewById(R.id.duanzi_pop_tencent);
		iv_tencent.setOnClickListener(this);
		ImageView iv_renren = (ImageView)popView.findViewById(R.id.duanzi_pop_renren);
		iv_renren.setOnClickListener(this);
		ImageView iv_douban = (ImageView)popView.findViewById(R.id.duanzi_pop_douban);
		iv_douban.setOnClickListener(this);
		ImageView iv_qq = (ImageView)popView.findViewById(R.id.duanzi_pop_qq);
		iv_qq.setOnClickListener(this);
		ImageView iv_qq_zone = (ImageView)popView.findViewById(R.id.duanzi_pop_qq_zone);
		iv_qq_zone.setOnClickListener(this);
		
		window = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		window.setBackgroundDrawable(new BitmapDrawable());
		window.setFocusable(true);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putSerializable("duanzi", duanzi);
		switch (v.getId()) {
		case R.id.mitem_bottom_cai:
			mAnimation = AnimationUtils.loadAnimation(context, R.anim.up);
			mAnimation.setFillAfter(true);
			duanzi.CanPress(Duanzi.CAI, Cai_txt,Cai_img, context);
			Cai_add.setVisibility(View.VISIBLE);
			Cai_add.startAnimation(mAnimation);
			break;

		case R.id.mitem_bottom_hot:
			Toast.makeText(context, "È¥ÆÀÂÛ", Toast.LENGTH_SHORT).show();
			duanzi.setNeedComment(true);
			bundle.putInt("xwkkx", My_login_select.From_Duanzi);
			if (User.UserIsExists(context)) {
				mFragmentManage.SwitchFrag(context, mFragment, new DuanZi_Comment_Write(), bundle);
			}else {
				mFragmentManage.SwitchFrag(context, mFragment, new My_login_select(), bundle);
			}
			break;
		case R.id.bottom_more:
			initPop();
			window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
			break;
		case R.id.mitem_bottom_zan:
			mAnimation = AnimationUtils.loadAnimation(context, R.anim.up);
			mAnimation.setFillAfter(true);
			duanzi.CanPress(Duanzi.ZAN, Zan_txt,Zan_img, context);
			Zan_add.setVisibility(View.VISIBLE);
			Zan_add.startAnimation(mAnimation);
			break;
		case R.id.mitem_test_gif:
			if (duanzi.getImageUrl() != null && !duanzi.getImageUrl().equals("")) {
				hint_img.setVisibility(View.GONE);
				((GifImageView)v).setImageDrawable(StringUtils.checkImgPathForGif(duanzi.getImageUrl()));
				
			}
			break;
		case R.id.duanzi_pop_Weixin:
			ShareUtil.shareToWeiXin(duanzi, context);
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
		case R.id.duanzi_more_back:
			window.dismiss();
			break;
		}
	}

}
