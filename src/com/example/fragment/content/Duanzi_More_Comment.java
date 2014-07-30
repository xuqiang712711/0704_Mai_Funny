package com.example.fragment.content;

import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.ShareUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Duanzi_More_Comment extends Fragment implements OnClickListener{
	View view;
	private EditText et_write;
	private Duanzi duanzi;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.duanzi_more_comment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		duanzi = (Duanzi) getArguments().getSerializable("duanzi");
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(" ‰»Î∆¿¬€");
		Button bt_back = (Button)view.findViewById(R.id.top_left);
		bt_back.setOnClickListener(this);
		Button bt_submit = (Button)view.findViewById(R.id.top_right);
		bt_submit.setText(getActivity().getString(R.string.ActionBar_Submit));
		bt_submit.setOnClickListener(this);
		et_write = (EditText)view.findViewById(R.id.duanzi_more_edit);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.top_left) {
			mFragmentManage.BackStatck(getActivity());
		}else {
			Log.i("FFF", "media " + duanzi.getMedia()  +" content " + duanzi.getContent());
//			ShareUtil.ShareToSocial(duanzi.getMedia(), et_write.getText().toString(), duanzi.getContent(), duanzi.getImageUrl(), getActivity(), handler);
		}
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == ShareUtil.SHARE_SUC) {
				mFragmentManage.BackStatck(getActivity());
			}
		}
	};
}
