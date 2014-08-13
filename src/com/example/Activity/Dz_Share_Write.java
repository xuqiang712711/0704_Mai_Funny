package com.example.Activity;

import java.util.HashMap;
import java.util.Map;

import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.PopUtils;
import com.example.util.ShareUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Dz_Share_Write extends BaseActivity implements OnClickListener{
	private Duanzi duanzi;
	private int share_Media;
	private EditText et_Input;
	private String input_Content;
	private Map<Integer, SHARE_MEDIA> map;
	private Dialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.duanzi_pop_zhuanfa);
		Bundle bundle = getIntent().getExtras();
		duanzi = (Duanzi) bundle.getSerializable("duanzi");
		initView();
	}
	
	private void initView(){
		share_Media = duanzi.getMedia();
		et_Input = (EditText)findViewById(R.id.duanzi_pop_zhuanfa_edit);
		TextView tv_Title = (TextView)findViewById(R.id.top_text);
		ImageView back = (ImageView)findViewById(R.id.top_left_change);
		TextView bt_submit = (TextView)findViewById(R.id.top_right_change2);
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
		mDialog = PopUtils.createLoadingDialog(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_left_change:
			finish();
			break;

		case R.id.top_right_change2:
			mDialog.show();
			input_Content = et_Input.getText().toString();
			ShareUtil.ShareToSocial(map.get(share_Media), input_Content, duanzi.getContent(), duanzi.getImageUrl(),
					this, mHandler);
			break;
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mDialog.dismiss();
			finish();
		}
	};
}
