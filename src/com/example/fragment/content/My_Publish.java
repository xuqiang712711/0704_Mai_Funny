package com.example.fragment.content;

import java.util.List;

import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.MyLogger;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class My_Publish extends Fragment implements OnClickListener, OnItemClickListener{
	private View view;
	private List<Duanzi> list_duanzi;
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
		list_duanzi = dbhelper.selectPub();
		XAdapter xAdapter = new XAdapter(list_duanzi, mHandler, MaimobApplication.mController, this, getActivity());
		ListView lv = (ListView)view.findViewById(R.id.my_pub_list);
		lv.setOnItemClickListener(this);
		MyLogger.jLog().i("size  " + list_duanzi.size());
		if (list_duanzi.size() != 0) {
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Duanzi duanzi = list_duanzi.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("duanzi", duanzi);
		mFragmentManage.SwitchFrag(getActivity(), My_Publish.this, new DuanZi_Comment(), bundle);
		MyLogger.jLog().i("onitemclick  " + duanzi.getContent());
		
	}
}
