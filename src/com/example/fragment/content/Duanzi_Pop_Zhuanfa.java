package com.example.fragment.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.PopUtils;
import com.example.util.MyLogger;
import com.example.util.ShareUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMediaObject.MediaType;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Duanzi_Pop_Zhuanfa extends Fragment implements OnClickListener{
	private View view;
	private Duanzi duanzi;
	private int share_Media;
	private EditText et_Input;
	private String input_Content;
	private Map<Integer, SHARE_MEDIA> map;
	private Dialog mDialog;
	private MyLogger myLogger = MyLogger.jLog();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.duanzi_pop_zhuanfa, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		duanzi = (Duanzi) getArguments().getSerializable("duanzi");
		share_Media = duanzi.getMedia();
		myLogger.i("share  " +String.valueOf(share_Media));
		et_Input = (EditText)view.findViewById(R.id.duanzi_pop_zhuanfa_edit);
		TextView tv_Title = (TextView)view.findViewById(R.id.top_text);
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		TextView bt_submit = (TextView)view.findViewById(R.id.top_right_change2);
		bt_submit.setText(R.string.ActionBar_Submit);
		back.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		map  = new HashMap<Integer, SHARE_MEDIA>();
		if (share_Media== Duanzi.SHARE_MEDIA_SINA) {
			tv_Title.setText(R.string.sina);
			map.put(share_Media, SHARE_MEDIA.SINA);
		}else if (share_Media == Duanzi.SHARE_MEDIA_TENCENT) {
			tv_Title.setText(R.string.tencent);
			map.put(share_Media, SHARE_MEDIA.TENCENT);
		}else if (share_Media == Duanzi.SHARE_MEDIA_RENREN) {
			tv_Title.setText(R.string.renren);
			map.put(share_Media, SHARE_MEDIA.RENREN);
		}else if (share_Media == Duanzi.SHARE_MEDIA_DOUBAN) {
			tv_Title.setText(R.string.douban);
			map.put(share_Media, SHARE_MEDIA.DOUBAN);
		}
		
		mDialog = PopUtils.createLoadingDialog(getActivity());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_left_change:
			mFragmentManage.BackStatck(getActivity());
			break;

		case R.id.top_right_change2:
			mDialog.show();
			input_Content = et_Input.getText().toString();
			ShareUtil.ShareToSocial(map.get(share_Media), input_Content, duanzi.getContent(), duanzi.getImageUrl(), getActivity(), mHandler);
			break;
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mDialog.dismiss();
			mFragmentManage.BackStatck(getActivity());
		}
	};
}
