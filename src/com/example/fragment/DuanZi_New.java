package com.example.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.AsyTask.RequestDataTask;
import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.fragment.DuanZi_Hot.MyAsyTask;
import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.object.setDuanziData;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class DuanZi_New extends Fragment implements OnRefreshListener{
	View view;
	private ListView listView;
	private SwipeRefreshLayout refreshLayout;
	private XAdapter adapter;
	private Handler TabHandler;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			updateListView(json);
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.duanzi_tab_hot, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light,
				android.R.color.holo_red_light);
		initView();
		inithttp();
	}
	
	private void updateListView(String json){
		List<Duanzi> list = setDuanziData.getListDuanzi(json);
		adapter = new XAdapter(list, mHandler, MaimobApplication.mController, this, getActivity());
		listView.setAdapter(adapter);
		if (TabHandler != null) {
			Message msg = Message.obtain();
			msg.what = Uris.MSG_REFRESH;
			TabHandler.sendMessage(msg);
		}
	}
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setItemsCanFocus(true);
	}
	
	
	
	
	private void inithttp(){
		String postUri = "http://md.maimob.net/index.php/player/FetchPost/uuid/YTBhYWYzYmEtOTI2NC0zZDRjLThlNDQtYjExOGQ2OWQ4NGJi/type/1/subType/3/maxID/0";
		RequestDataTask requestData = new RequestDataTask(mHandler);
		requestData.execute(postUri);
	}
	
	
	public void switchFragment(Fragment from , Fragment to){
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof XYFTEST) {
			XYFTEST xyf = (XYFTEST) getActivity();
			xyf.switchContentFull(from, to, null);
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				refreshLayout.setRefreshing(false);
				inithttp();
				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
			}
		}, 5000);
	}
	
	public void Refresh(Handler tabHandler){
		inithttp();
		adapter.notifyDataSetChanged();
		this.TabHandler = tabHandler;
	}
}
