package com.example.fragment.content;

import java.util.List;

import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class My_Publish extends Fragment implements OnClickListener{
	private View view;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_publish, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TextView tv = (TextView)view.findViewById(R.id.top_text);
		tv.setText(getResources().getString(R.string.my_pub_title));
		Button bt_back = (Button)view.findViewById(R.id.top_left);
		bt_back.setOnClickListener(this);
		Button bt_right = (Button)view.findViewById(R.id.top_right);
		bt_right.setVisibility(View.GONE);
		
		Mai_DBhelper dbhelper = Mai_DBhelper.getInstance(getActivity());
		List<Duanzi> list_duanzi = dbhelper.selectPub();
		XAdapter xAdapter = new XAdapter(list_duanzi, mHandler, MaimobApplication.mController, this, getActivity());
		ListView lv = (ListView)view.findViewById(R.id.my_pub_list);
		if (list_duanzi.size() != 0) {
			Log.e("FFF", "size  +~~" +list_duanzi.size());
			lv.setAdapter(xAdapter);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_left:
			mFragmentManage.BackStatck(getActivity());
			break;

		default:
			break;
		}
	}
}
