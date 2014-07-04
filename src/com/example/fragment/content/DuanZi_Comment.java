package com.example.fragment.content;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.CustomImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class DuanZi_Comment extends Fragment{
	private String json;
	private View view;
	private ImageView user_icon,image;
	private TextView user_name;
	private TextView content;
	private CustomImage layout_Zan, layout_Cai, layout_Hot;
	private LinearLayout layout;
	private DisplayImageOptions options;
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
		json = getArguments().getString("json");
		initView();
	}
	
	private void initView(){
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.maimob)
		.showImageForEmptyUri(R.drawable.maimob)
		.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.displayer(new SimpleBitmapDisplayer()).build();
		
		layout = (LinearLayout)view.findViewById(R.id.duanzi_comment_write);
		user_icon = (ImageView)view.findViewById(R.id.duanzi_user_icon);
		user_name = (TextView)view.findViewById(R.id.duanzi_user_name);
		content = (TextView)view.findViewById(R.id.duanzi_textview);
		layout_Cai = (CustomImage)view.findViewById(R.id.duanzi_bury);
		layout_Hot = (CustomImage)view.findViewById(R.id.duanzi_hot);
		layout_Zan = (CustomImage)view.findViewById(R.id.duanzi_praise);
		image = (ImageView)view.findViewById(R.id.duanzi_imageview);
		
		try {
			JSONObject jsonObject = new JSONObject(json);
			if (jsonObject.getString("icon") != null) {
				
			}
			user_name.setText(jsonObject.getString("nick"));
			content.setText(jsonObject.getString("content"));
			layout_Cai.setTextView_String(jsonObject.getString("cai"));
			layout_Cai.setImageResource(R.drawable.ic_bury_normal);
			layout_Zan.setTextView_String(jsonObject.getString("zan"));
			layout_Zan.setImageResource(R.drawable.ic_digg_normal);
			layout_Hot.setTextView_String(jsonObject.getString("comment"));
			layout_Hot.setImageResource(R.drawable.hot_commenticon_textpage);
			String content = (String) jsonObject.get("content");
			Log.i("FFF", "img  "  + jsonObject.getString("img") );
			if (jsonObject.getString("img") != null) {
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(jsonObject.getString("img"), image,options);
			}
			Log.i("FFF", "content  " + content);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "ȥ����", Toast.LENGTH_SHORT).show();
				DuanZi_Comment_Write comment_Test = new DuanZi_Comment_Write();
				switchFrag(DuanZi_Comment.this, comment_Test);
			}
		});
	}
	private void switchFrag(Fragment from, Fragment to){
		Bundle bundle = new Bundle();
		bundle.putString("comment", json);
		XYFTEST xyftest = (XYFTEST) getActivity();
		xyftest.switchContentFullwithBundle(from, to, bundle);
	}
	
}
