package com.example.adapter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AsyTask.MyTask_No_Result;
import com.example.fragment.content.DuanZI_UserInfo;
import com.example.fragment.content.DuanZi_Comment;
import com.example.fragment.content.DuanZi_More;
import com.example.listener.AnimateFirstDisplayListener;
import com.example.object.Duanzi;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.CustomImage;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.tencent.a.b.h;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class TestAdapter extends BaseAdapter implements OnClickListener {
	public static int fontSize = 14;
	Holder holder = null;
	private Context context;
	private List<Map<String, Object>> data;
	private static Map<Integer, Boolean> isChecked_Cai;
	private static Map<Integer, Boolean> isChecked_Zan;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private JSONArray array;
	private ImageLoader imageLoader;
	private Fragment mFragment;
	private static final int IMAGE_TYPE_NONE = 2;
	private static final int IMAGE_TYPE_GIF = 0;
	private static final int IMAGE_TYPE_OTHER = 1;
	private int Image_Type = 0;
	private UMSocialService mController;
	private static Handler mHandler;
	private List<Duanzi> mdata;
	private LayoutInflater mInflater;
	private String TAG = "TestAdapter";

	public static final int ZAN_NORMAL = 1;
	public static final int ZAN_PRESSED = 2;
	public static final int CAI_NORMAL = 3;
	public static final int CAI_PRESSED = 4;

	public TestAdapter(List<Duanzi> mdata, Handler handler,
			UMSocialService mController, Fragment mFragment, Context context,
			JSONArray array) {
		this.mdata = mdata;
		this.mHandler = handler;
		this.mController = mController;
		this.context = context;
		this.array = array;
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
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Duanzi duanzi = mdata.get(position);
		String imgUri = duanzi.getImageUrl();
		String name = duanzi.getUserName();
		String content = duanzi.getContent();
		String cai = duanzi.getCai();
		String zan = duanzi.getZan();
		String hot = duanzi.getComment();
		if (convertView == null) {
			Log.i(TAG, "converView is null  " + Image_Type);
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.mitem, null);
			// holder.user_icon = (ImageView) convertView
			// .findViewById(R.id.mitem_icon);
			holder.user_name = (TextView) convertView
					.findViewById(R.id.mitem_username);

			holder.content = (TextView) convertView
					.findViewById(R.id.mitem_content);

			holder.zan = (TextView) convertView.findViewById(R.id.zan_txt);
			holder.cai = (TextView) convertView.findViewById(R.id.cai_txt);
			holder.hot = (TextView) convertView.findViewById(R.id.hot_txt);
			holder.more = (ImageView) convertView.findViewById(R.id.more_img);

			convertView.setTag(holder);
		} else {
			Log.i(TAG, "converView is not null~~~~~");
			holder = (Holder) convertView.getTag();
		}

		if (isChecked_Cai.get(position) == false) {
			Drawable drawable = ChangePic(CAI_NORMAL);
			holder.cai.setCompoundDrawables(drawable, null, null, null);
		} else {
			Drawable drawable = ChangePic(CAI_PRESSED);
			holder.cai.setCompoundDrawables(drawable, null, null, null);
		}

		if (isChecked_Zan.get(position) == false) {
			Drawable drawable = ChangePic(ZAN_NORMAL);
			holder.zan.setCompoundDrawables(drawable, null, null, null);
		} else {
			Drawable drawable = ChangePic(ZAN_PRESSED);
			holder.zan.setCompoundDrawables(drawable, null, null, null);
		}

		holder.cai.setText(cai);
		holder.zan.setText(zan);
		holder.hot.setText(hot);
		holder.user_name.setText(name);
		holder.content.setText(content);
		holder.content.setTextSize(fontSize);

//		addListen(position);
		return convertView;
	}

	private void addListen(int position) {
		holder.cai.setOnClickListener(this);
		holder.zan.setOnClickListener(this);
		holder.hot.setOnClickListener(this);
		holder.more.setOnClickListener(this);

		holder.zan.setTag(position);
		holder.cai.setTag(position);
		holder.hot.setTag(position);
		holder.more.setTag(position);

		holder.content.setOnClickListener(this);
		holder.content.setTag(position);
	}

	private void addListenWidget(final Holder holder, final int position,
			int type) {
		if (type == IMAGE_TYPE_GIF) {
			holder.gif.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						File cache = DiskCacheUtils.findInCache(
								((JSONObject) getItem(position))
										.getString("img"), imageLoader
										.getDiskCache());
						GifDrawable drawable = new GifDrawable(cache);
						holder.gif.setImageDrawable(drawable);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();
		Bundle bundle = new Bundle();
		Duanzi duanzi = (Duanzi) getItem(position);
		bundle.putSerializable("duanzi", duanzi);
		switch (v.getId()) {

		case R.id.zan_txt:
			Log.e(TAG, "Zan " + position);
			if (isChecked_Zan.get(position) == false) {
				if (isChecked_Cai.get(position) == true) {
					Toast.makeText(context, "你已经踩过", Toast.LENGTH_SHORT).show();
					break;
				}
				Drawable drawable = context.getResources().getDrawable(
						R.drawable.ic_digg_pressed);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());
				((TextView) v).setCompoundDrawables(drawable, null, null, null);
				int Zan_num = Integer.parseInt(mdata.get(position).getZan());
				((TextView) v).setText(String.valueOf(Zan_num + 1));
				isChecked_Zan.put(position, true);
				String uri = Uris.Zan + "/uuid/" + Uris.uuid + "/pid/"
						+ mdata.get(position).getPoid();
				Do_http(uri);
			} else {
				Toast.makeText(context, "你已经赞过", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.cai_txt:
			if (isChecked_Cai.get(position) == false) {
				if (isChecked_Zan.get(position) == true) {
					Toast.makeText(context, "你已经赞过", Toast.LENGTH_SHORT).show();
					break;
				}
				Drawable drawable = ChangePic(CAI_PRESSED);
				((TextView) v).setCompoundDrawables(drawable, null, null, null);
				int Cai_num = Integer.parseInt(mdata.get(position).getCai());
				((TextView) v).setText(String.valueOf(Cai_num - 1));
				isChecked_Cai.put(position, true);

				String uri = Uris.Cai + "/uuid/" + Uris.uuid + "/pid/"
						+ mdata.get(position).getPoid();
				Do_http(uri);
			} else {
				Toast.makeText(context, "你已经踩过", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.hot_txt:
			DuanZi_Comment comment3 = new DuanZi_Comment();
			switchFragment(mFragment, comment3, bundle);
			Toast.makeText(context, "点击热门  +  " + position, Toast.LENGTH_SHORT)
					.show();
			break;

		case R.id.more_img:
			try {
				UMShare(((JSONObject) getItem(position)).getString("content"),
						((JSONObject) getItem(position)).getString("img"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, "tuituibangdexiwang");
			}
			mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
					SHARE_MEDIA.DOUBAN);
			mController.openShare((Activity) context, false);
			break;
		case R.id.duanzi_imageview:
			DuanZi_Comment comment4 = new DuanZi_Comment();
			switchFragment(mFragment, comment4, bundle);
			break;
		case R.id.duanzi_comment:
			// int comment_p = (Integer) v.getTag();
			// Toast.makeText(context, "点击评论  +  " + comment_p,
			// Toast.LENGTH_SHORT)
			// .show();
			// DuanZi_Comment_write comment2 = new DuanZi_Comment_write();
			// mcontext.switchFragment(mcontext,comment2);
			break;

		case R.id.duanzi_user_icon:
			// Toast.makeText(context, "点击用户头像  + " + position,
			// Toast.LENGTH_SHORT)
			// .show();
			// DuanZI_UserInfo userInfo = new DuanZI_UserInfo();
			// mcontext.switchFragment(mcontext,userInfo);
			break;
		case R.id.duanzi_textview:
			Toast.makeText(context, "点击段子  + " + position, Toast.LENGTH_SHORT)
					.show();
			DuanZi_Comment comment = new DuanZi_Comment();
			switchFragment(mFragment, comment, bundle);
			break;
		// case R.id.mitem_img:
		// File cache = DiskCacheUtils.findInCache(
		// mdata.get(position).getImageUrl(), imageLoader
		// .getDiskCache());
		// GifDrawable drawable = null;
		// try {
		// drawable = new GifDrawable(cache);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// holder.gif.setImageDrawable(drawable);
		// break;
		}
	}

	private void Do_http(String uri) {
		MyTask_No_Result myTask = new MyTask_No_Result();
		myTask.execute(uri);
	}

	public void switchFragment(Fragment from, Fragment to, Bundle bundle) {
		XYFTEST xyftest = (XYFTEST) context;
		xyftest.switchContentFullwithBundle(from, to, bundle);
	}

	public static class Holder {
		CustomImage layout_parise, layout_bury, layout_hot;
		ImageView user_icon, more, image;
		TextView user_name, content, comment;
		GifImageView gif;
		TextView cai, zan, hot;
		ImageView cai_img, zan_img;
	}

	private void UMShare(String content, String uri) {
		mController.setShareContent(content);
		mController.setShareMedia(new UMImage(context, uri));
	}

	public static void SetNormal() {
		Message msg = Message.obtain();
		msg.what = 313;
		mHandler.sendMessage(msg);
	}

	public Drawable ChangePic(int type) {
		Drawable drawable = null;
		switch (type) {
		case CAI_PRESSED:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_bury_pressed);
			break;
		case CAI_NORMAL:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_bury_normal);
			break;
		case ZAN_PRESSED:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_digg_pressed);
			break;
		case ZAN_NORMAL:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_digg_normal);
			break;
		}
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		return drawable;
	}

}
