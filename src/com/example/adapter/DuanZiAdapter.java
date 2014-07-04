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
import com.example.tab.R;
import com.example.tab.R.layout;
import com.example.tab.XYFTEST;
import com.example.util.CustomImage;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class DuanZiAdapter extends BaseAdapter implements OnClickListener {
	public static int fontSize = 14;
	Holder holder = null;
	private Context context;
	private List<Map<String, Object>> data;
	private static Map<Integer, Boolean> isChecked_bury;
	private static Map<Integer, Boolean> isChecked_praise;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private JSONArray array;
	private ImageLoader imageLoader;
	private Fragment mFragment;
	private static final int Image_Type_GIF = 0;
	private static final int Image_Type_Other = 1;
	private int Image_Type = 1;
	private UMSocialService mController;
	private static Handler mHandler;

	public DuanZiAdapter(Handler handler, UMSocialService mController,Fragment mFragment, Context context, JSONArray array) {
		this.mHandler = handler;
		this.mController = mController;
		this.context = context;
		this.array = array;
		this.mFragment = mFragment;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.maimob)
				.showImageForEmptyUri(R.drawable.maimob)
				.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer()).build();

		init();

	}

	private void init() {
		isChecked_bury = new HashMap<Integer, Boolean>();
		isChecked_praise = new HashMap<Integer, Boolean>();
		for (int i = 0; i < array.length(); i++) {
			isChecked_bury.put(i, false);
			isChecked_praise.put(i, false);
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.length();
	}

	@Override
	public Object getItem(int position) {
		return array.opt(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (position == Image_Type_GIF) {
			return Image_Type_GIF;
		}else {
			return Image_Type_Other;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String imgUri = null;
		String name = null;
		String content = null;
		String cai = null;
		String zan = null;
		try {
			imgUri = ((JSONObject) getItem(position)).getString("img");
			name = ((JSONObject) getItem(position)).getString("nick");
			content = ((JSONObject) getItem(position)).getString("content");
			cai = ((JSONObject) getItem(position)).getString("cai");
			zan = ((JSONObject) getItem(position)).getString("zan");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (imgUri != null && !imgUri.equals("")) {
			if ((imgUri.substring(imgUri.length() - 3, imgUri.length())).equals("gif")) {
				Image_Type = Image_Type_GIF;
			} else {
				Image_Type = Image_Type_Other;
			}
		}
		int type = getItemViewType(Image_Type);
		if (convertView == null) {
			holder = new Holder();
			switch (type) {
			case Image_Type_GIF:
				convertView = LayoutInflater.from(context).inflate(R.layout.duanzi_item_gif, parent, false);
				holder.gif = (GifImageView)convertView.findViewById(R.id.gif);
				break;

			case Image_Type_Other:
				convertView = LayoutInflater.from(context).inflate(R.layout.duanzi_item, parent, false);
				holder.image = (ImageView)convertView.findViewById(R.id.duanzi_imageview);
				break;
			}
			holder.layout_parise = (CustomImage) convertView
					.findViewById(R.id.duanzi_praise);
			holder.layout_bury = (CustomImage) convertView
					.findViewById(R.id.duanzi_bury);
			holder.layout_hot = (CustomImage) convertView
					.findViewById(R.id.duanzi_hot);
			holder.more = (ImageView) convertView
					.findViewById(R.id.duanzi_more);

			holder.user_icon = (ImageView) convertView
					.findViewById(R.id.duanzi_user_icon);
			holder.user_name = (TextView) convertView
					.findViewById(R.id.duanzi_user_name);

			holder.content = (TextView) convertView
					.findViewById(R.id.duanzi_textview);

			holder.comment = (TextView) convertView
					.findViewById(R.id.duanzi_comment);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.layout_parise.setTag(position);
		if (isChecked_praise.get(position) == true) {
			holder.layout_parise.setImageResource(R.drawable.ic_digg_pressed);
		} else {
			holder.layout_parise.setImageResource(R.drawable.ic_digg_normal);
		}

		holder.layout_bury.setTag(position);
		if (isChecked_bury.get(position) == true) {
			holder.layout_bury.setImageResource(R.drawable.ic_bury_pressed);
		} else {
			holder.layout_bury.setImageResource(R.drawable.ic_bury_normal);
		}

		holder.layout_hot.setTag(position);
		holder.layout_hot.setImageResource(R.drawable.hot_commenticon_textpage);
		
		try {
			holder.layout_parise
					.setTextView_String(zan);
			holder.layout_bury
					.setTextView_String(cai);

//			holder.user_name.setText(name);
			//段子内容
//			holder.content.setText(content);
//			Log.i("YYY", "font  " + fontSize);
//			holder.content.setTextSize(fontSize);
			
			if (!imgUri.equals("") && imgUri != null) {
				imageLoader = ImageLoader.getInstance();
				if (type == Image_Type_GIF) {
					imageLoader.displayImage(((JSONObject) getItem(position)).getString("img"),
							holder.gif, options);
				}
				else {
					imageLoader.displayImage(((JSONObject) getItem(position)).getString("img"),
							holder.image, options);
					holder.image.setTag(position);
					holder.image.setOnClickListener(this);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.more.setTag(position);
		holder.content.setTag(position);
		holder.comment.setTag(position);

		addListenWidget(holder, position, type);
		return convertView;
	}
	
	private void initData(){
		
	}


	private void addListenWidget(final Holder holder, final int position, int type) {
		if (type == Image_Type_GIF) {
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
		holder.layout_bury.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int num = Integer.parseInt(((JSONObject) getItem(position))
							.getString("cai"));
//					if (num == 0) {
//						return;
//					}
					if (isChecked_bury.get(position) == false) {
						isChecked_bury.put(position, true);
						holder.layout_bury
								.setImageResource(R.drawable.ic_bury_pressed);
						holder.layout_bury.setTextView_String(String
								.valueOf(num - 1));
					} else {
						isChecked_bury.put(position, false);
						holder.layout_bury
								.setImageResource(R.drawable.ic_bury_normal);
						holder.layout_bury.setTextView_String(String
								.valueOf(num));
					}
					String uri = Uris.Cai
							+ "/uuid/"
							+ Uris.uuid
							+ "/pid/"
							+ ((JSONObject) getItem(position))
									.getString("poid");
					Do_http(uri);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		holder.layout_parise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int num = Integer.parseInt(((JSONObject) getItem(position))
							.getString("zan"));
					if (isChecked_praise.get(position) == false) {
						isChecked_praise.put(position, true);
						holder.layout_parise.setTextView_String(String
								.valueOf(num + 1));
						holder.layout_parise
								.setImageResource(R.drawable.ic_digg_pressed);
					} else {
						isChecked_praise.put(position, false);
						holder.layout_parise.setTextView_String(String
								.valueOf(num));
						holder.layout_parise
								.setImageResource(R.drawable.ic_digg_normal);
					}
					String uri = Uris.Zan
							+ "/uuid/"
							+ Uris.uuid
							+ "/pid/"
							+ ((JSONObject) getItem(position))
									.getString("poid");
					Do_http(uri);
				} catch (JSONException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

		holder.layout_hot.setOnClickListener(this);
		// holder.user_icon.setOnClickListener(this);
		holder.content.setOnClickListener(this);
		// holder.comment.setOnClickListener(this);
		holder.more.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int position = (Integer) v.getTag();
		Bundle bundle = new Bundle();
		bundle.putString("json", ((JSONObject) getItem(position)).toString());
		switch (v.getId()) {
		
		case R.id.duanzi_imageview:
			DuanZi_Comment comment4 = new DuanZi_Comment();
			switchFragment(mFragment, comment4, bundle);
			break;
		case R.id.duanzi_more:
			DuanZi_More more = new DuanZi_More();
			// mcontext.switchFragment(mcontext,more);
			try {
				UMShare(((JSONObject)getItem(position)).getString("content"), ((JSONObject)getItem(position)).getString("img"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
			mController.openShare((Activity) context, false);
			break;
		case R.id.duanzi_hot:
			DuanZi_Comment comment3 = new DuanZi_Comment();
			Log.i("FFF",
					"json   " + ((JSONObject) getItem(position)).toString());
			switchFragment(mFragment, comment3, bundle);
			Toast.makeText(context, "点击热门  +  " + position, Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.duanzi_comment:
			int comment_p = (Integer) v.getTag();
			Toast.makeText(context, "点击评论  +  " + comment_p, Toast.LENGTH_SHORT)
					.show();
//			DuanZi_Comment_write comment2 = new DuanZi_Comment_write();
			// mcontext.switchFragment(mcontext,comment2);
			break;

		case R.id.duanzi_user_icon:
			Toast.makeText(context, "点击用户头像  + " + position, Toast.LENGTH_SHORT)
					.show();
			DuanZI_UserInfo userInfo = new DuanZI_UserInfo();
			// mcontext.switchFragment(mcontext,userInfo);
			break;
		case R.id.duanzi_textview:
			Toast.makeText(context, "点击段子  + " + position, Toast.LENGTH_SHORT)
					.show();
			DuanZi_Comment comment = new DuanZi_Comment();

			Log.i("FFF",
					"json   " + ((JSONObject) getItem(position)).toString());
			switchFragment(mFragment, comment, bundle);
			break;
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
	}
	
	private void UMShare(String content,String uri){
		mController.setShareContent(content);
		mController.setShareMedia(new UMImage(context, uri));
	}
	
	public static void SetNormal(){
		Message msg = Message.obtain();
		msg.what = 313;
		mHandler.sendMessage(msg);
	}
	
}
