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

import com.example.object.Duanzi;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.CustomImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class DuanZi_Comment extends Fragment{
	private View view;
	private ImageView user_icon,image;
	private TextView user_name,Zan,Cai,Hot,More;
	private TextView content;
	private LinearLayout layout;
	private DisplayImageOptions options;
	private Duanzi duanzi;
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
		TextView textView = (TextView)view.findViewById(R.id.back2_text);
		textView.setText(getResources().getString(R.string.app_name));
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.maimob)
		.showImageForEmptyUri(R.drawable.maimob)
		.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.displayer(new SimpleBitmapDisplayer()).build();
		
		layout = (LinearLayout)view.findViewById(R.id.duanzi_comment_write);
		user_icon = (ImageView)view.findViewById(R.id.mitem_icon);
		user_name = (TextView)view.findViewById(R.id.mitem_username);
		content = (TextView)view.findViewById(R.id.mitem_test_content);
		image = (ImageView)view.findViewById(R.id.mitem_test_img);
		
		user_name.setText(duanzi.getUserName());
		content.setText(duanzi.getContent());
		if (duanzi.getImageUrl() != null) {
			image.setVisibility(View.VISIBLE);
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(duanzi.getImageUrl(), image, options);
		}
		
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "È¥ÆÀÂÛ", Toast.LENGTH_SHORT).show();
				DuanZi_Comment_Write comment_Test = new DuanZi_Comment_Write();
				switchFrag(DuanZi_Comment.this, comment_Test);
			}
		});
	}
	private void switchFrag(Fragment from, Fragment to){
		Bundle bundle = new Bundle();
		bundle.putSerializable("commit", duanzi);
		XYFTEST xyftest = (XYFTEST) getActivity();
		xyftest.switchContentFullwithBundle(from, to, bundle);
	}
	
}
