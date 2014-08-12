package com.example.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

import com.example.AsyTask.MyTask_No_Result;
import com.example.AsyTask.RequestDataTask;
import com.example.application.MaimobApplication;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.BitmapOptions;
import com.example.util.CustomImage;
import com.example.util.PopUtils;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.StringUtils;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Tab_Search_Frag extends Fragment implements OnClickListener {

	private String Tag = "Tab_Search_Frag";
	private View view;
	private CustomImage trash, sex, neihan, funny, skip;
	private TextView textView;
	private ImageView imageView,hintImg;
	private GifImageView gif;
	private DisplayImageOptions options;
	private JSONArray array = null;
	private int flag_neihan = 1;
	private int flag_trash = 2;
	private int flag_sex = 3;
	private int flag_old = 4;
	private int num = 0;
	private int id = 0;
	private MyTask_No_Result task = null;
	private ImageLoader imageLoader;
	private int maxID;
	private String text = null;
	private String img = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e(Tag, "Tab_Search_Frag");
		view = inflater.inflate(R.layout.my_check, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		maxID = Uris.max_check;
		MyLogger.jLog().i("StartmaxID  " + maxID);
		initView();
		initHttp();
		listenerWidget();
		imageLoader = MaimobApplication.imageLoader;
	}

	private void initView() {
		TextView title = (TextView)view.findViewById(R.id.top_text);
		title.setText(R.string.my_check);
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setOnClickListener(this);
		TextView right = (TextView)view.findViewById(R.id.top_right_change2);
		right.setVisibility(View.GONE);
		
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.showImageOnLoading(R.drawable.maimob)
				.showImageForEmptyUri(R.drawable.maimob)
				.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer()).build();

		trash = (CustomImage) view.findViewById(R.id.my_check_trash);
		sex = (CustomImage) view.findViewById(R.id.my_check_sex);
		neihan = (CustomImage) view.findViewById(R.id.my_check_neihan);
		funny = (CustomImage) view.findViewById(R.id.my_check_boring);
		skip = (CustomImage) view.findViewById(R.id.my_check_skip);

		setContent(trash, R.drawable.ic_trash, R.string.my_trash);
		setContent(sex, R.drawable.ic_sex, R.string.my_sexy);
		setContent(neihan, R.drawable.ic_connotation, R.string.my_neihan);
		setContent(funny, R.drawable.ic_funny, R.string.my_boring);
		setContent(skip, R.drawable.ic_skip, R.string.my_skip);

		textView = (TextView) view.findViewById(R.id.my_check_content);
		imageView = (ImageView) view.findViewById(R.id.my_check_image);
		gif = (GifImageView)view.findViewById(R.id.my_check_gif);
		hintImg = (ImageView)view.findViewById(R.id.my_check_hintImg);
	}

	private void setContent(CustomImage ci, int resImg, int resText) {
		ci.setLayoutOrientation(LinearLayout.VERTICAL);
		ci.setImageResource(resImg);
		ci.setTextView_int(resText);
	}

	private void initHttp() {
		String check = Uris.Check + "/uuid/" + Uris.uuid + "/maxID/" + maxID;
		RequestDataTask mTask = new RequestDataTask(handler);
		mTask.execute(check);
	}

	private void listenerWidget() {
		trash.setOnClickListener(this);
		sex.setOnClickListener(this);
		neihan.setOnClickListener(this);
		funny.setOnClickListener(this);
		skip.setOnClickListener(this);
		gif.setOnClickListener(this);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			MyLogger.jLog().i("msg  " + msg.what);
			if (msg.what == Uris.MSG_NOTIHING) {
				PopUtils.toastShow(getActivity(), "没有更多了");
			}else if (msg.what == Uris.MSG_UPDATE) {
				
			}else {
				String data = (String) msg.obj;
				try {
					array = new JSONArray(data);
					Log.i(Tag, "Check_array  " + array);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			ShowContent();
		}
	};
	
	private void ShowContent(){
		try {
			text = ((JSONObject) array.get(num)).getString("content");
			img = ((JSONObject) array.get(num)).getString("img");
			id = ((JSONObject) array.get(num)).getInt("id");
			maxID  = id + 1;
			Uris.max_check = maxID;
			textView.setText(text);
			textView.setTextSize(Uris.Font_Size);
			gif.setVisibility(View.GONE);
			hintImg.setVisibility(View.GONE);
			imageView.setVisibility(View.GONE);
			if (StringUtils.StringisPic(img)) {
				if ((img.substring(img.length() - 3, img.length())).equals("gif")) {
					hintImg.setVisibility(View.VISIBLE);
					gif.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
					imageLoader.displayImage(img, gif, options);
					File imgFile = DiskCacheUtils.findInCache(img,
							imageLoader.getDiskCache());
					if (imgFile != null) {
						int h = BitmapOptions.getWH(imgFile.toString(),
								MaimobApplication.DeviceW);
						FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) gif.getLayoutParams();
						params.height = h;
						params.width = FrameLayout.LayoutParams.MATCH_PARENT;
						gif.setLayoutParams(params);
					}
				}else {
					MyLogger.jLog().i("img  " + img);
					gif.setVisibility(View.GONE);
					hintImg.setVisibility(View.GONE);
					imageView.setVisibility(View.VISIBLE);
					imageLoader.displayImage(img, imageView, ImageUtil.getOption());
				}
			}else {
				MyLogger.jLog().i("img  no" + img);
				imageView.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i(Tag, "Warring");
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_check_skip:
			SwitchContent();
			break;

		case R.id.my_check_neihan:
			check_handle(flag_neihan);
			break;
		case R.id.my_check_boring:
			check_handle(flag_old);
			break;
		case R.id.my_check_sex:
			check_handle(flag_sex);
			break;
		case R.id.my_check_trash:
			check_handle(flag_trash);
			break;
		case R.id.top_left_change:
			mFragmentManage.BackStatck(getActivity());
			break;
		case R.id.my_check_gif:
			hintImg.setVisibility(View.GONE);
			((GifImageView)v).setImageDrawable(StringUtils.checkImgPathForGif(img));
		}
	}
	
	private void SwitchContent(){
		num++;
		if (num >= array.length()) {
			num =0;
			initHttp();
		}else {
			Message message = Message.obtain();
			message.what = Uris.MSG_UPDATE;
			handler.sendMessage(message);
		}

	}
	
	private void check_handle(int flag){
		task = new MyTask_No_Result();
		String uris = Uris.Check_handle +"/uuid/" + Uris.uuid + "/pid/" + id + "/vflag/" + flag;
		task.execute(uris);
		SwitchContent();
	}
}
