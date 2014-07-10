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
import com.example.util.ConnToServer;
import com.example.util.CustomImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.umeng.socialize.controller.utils.ToastUtil;

public class DuanZi_Comment extends Fragment implements OnClickListener{
	private String Tag = "DuanZi_Comment";
	private View view;
	private ImageView user_icon,image,More;
	private TextView user_name,Zan,Cai,Hot;
	private TextView content;
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
		
		user_icon = (ImageView)view.findViewById(R.id.mitem_icon);
		user_name = (TextView)view.findViewById(R.id.mitem_username);
		content = (TextView)view.findViewById(R.id.mitem_test_content);
		image = (ImageView)view.findViewById(R.id.mitem_test_img);
		Cai = (TextView)view.findViewById(R.id.bottom_cai);
		Zan = (TextView)view.findViewById(R.id.bottom_zan);
		Hot = (TextView)view.findViewById(R.id.bottom_hot);
		More = (ImageView)view.findViewById(R.id.bottom_more);
		
		Log.e("yyy", "name  " + duanzi.getUserName());
		user_name.setText(duanzi.getUserName());
		content.setText(duanzi.getContent());
		Cai.setText(String.valueOf(Integer.parseInt(duanzi.getCai()) + 1));
		
		Zan.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		Hot.setText(duanzi.getComment());
		
		if (duanzi.isZanPressed()) {
			Zan.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.ZAN_PRESSED), null, null, null);
			Zan.setText(String.valueOf(Integer.parseInt(duanzi.getZan()) + 1));
		}else {
			Zan.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.ZAN_NORMAL), null, null, null);
			Zan.setText(duanzi.getZan());
		}
		
		if (duanzi.isCaiPressed()) {
			Cai.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.CAI_PRESSED), null, null, null);
			Cai.setText(String.valueOf(Integer.parseInt(duanzi.getCai()) + 1));
		}else {
			Cai.setCompoundDrawables(duanzi.ChangePic(getActivity(), Duanzi.CAI_NORMAL), null, null, null);
			Cai.setText(duanzi.getCai());
		}
		Cai.setOnClickListener(this);
		Zan.setOnClickListener(this);
		Hot.setOnClickListener(this);
		More.setOnClickListener(this);
		
		
		if (duanzi.getImageUrl() != null) {
			image.setVisibility(View.VISIBLE);
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(duanzi.getImageUrl(), image, options);
		}
		
	}
	private void switchFrag(Fragment from, Fragment to){
		Bundle bundle = new Bundle();
		bundle.putSerializable("commit", duanzi);
		XYFTEST xyftest = (XYFTEST) getActivity();
		xyftest.switchContentFullwithBundle(from, to, bundle);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bottom_cai:
			duanzi.CanPress(Duanzi.CAI, ((TextView)v), getActivity());
			break;

		case R.id.bottom_hot:
			Toast.makeText(getActivity(), "È¥ÆÀÂÛ", Toast.LENGTH_SHORT).show();
			DuanZi_Comment_Write comment_Test = new DuanZi_Comment_Write();
			switchFrag(DuanZi_Comment.this, comment_Test);
			break;
		case R.id.bottom_more:

			break;
		case R.id.bottom_zan:
			duanzi.CanPress(Duanzi.ZAN, ((TextView)v), getActivity());
			break;
		}
	}
	
}
