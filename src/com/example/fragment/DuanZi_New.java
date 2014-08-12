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

import com.example.Activity.MaiActivity;
import com.example.AsyTask.RequestDataTask;
import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.fragment.DuanZi_Hot.MyAsyTask;
import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.object.setDuanziData;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.PopUtils;
import com.example.util.MyLogger;
import com.example.util.Uris;

import android.app.Dialog;
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
	private Dialog dialog;
	private List<Duanzi> list;
	private int maxID;
	private Mai_DBhelper db;
	private int add_count = 0;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == Uris.MSG_CHANGEFONT) {
				ChangeFontSize();
			}else {
				add_count += msg.what;
				dialog.dismiss();
				String json = (String) msg.obj;
				updateListView(json);
			}

		}
	};
	
	public void ChangeFontSize(){
		if (adapter != null) {
			MyLogger.jLog().e("Duanzi_new changeFont");
			adapter.notifyDataSetChanged();
		}
	}
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
		if (list == null) {
			list = new ArrayList<Duanzi>();
		}
		dialog = PopUtils.createLoadingDialog(getActivity());
		refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light,
				android.R.color.holo_red_light);
		initView();
		if (list == null) {
			list = new ArrayList<Duanzi>();
		}
		db = Mai_DBhelper.getInstance(getActivity());
		list = db.selectALLDuanzi(1);
		if (list.size() == 0) {
			inithttp();
			dialog.show();
		}else {
			adapter = new XAdapter(list, mHandler, MaimobApplication.mController, this, getActivity());
			listView.setAdapter(adapter);
		}
		maxID = Uris.max_dz_hot;
	}
	
	private void updateListView(String json){
		list = setDuanziData.getListDuanzi(json, getActivity(), list, 1);
		adapter = new XAdapter(list, mHandler, MaimobApplication.mController, this, getActivity());
		listView.setAdapter(adapter);
		if (TabHandler != null) {
			Message msg = Message.obtain();
			msg.what = Uris.MSG_REFRESH;
			TabHandler.sendMessageDelayed(msg, 1000);
		}
	}
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setItemsCanFocus(true);
	}
	
	
	
	
	private void inithttp(){
		MyLogger.jLog().i("maxId_new  " + maxID);
		String postUri = "http://md.maimob.net/index.php/player/FetchPost/uuid/YTBhYWYzYmEtOTI2NC0zZDRjLThlNDQtYjExOGQ2OWQ4NGJi/type/1/subType/3/maxID/" +maxID;
		RequestDataTask requestData = new RequestDataTask(mHandler);
		requestData.execute(postUri);
	}
	
	
	public void switchFragment(Fragment from , Fragment to){
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MaiActivity) {
			MaiActivity xyf = (MaiActivity) getActivity();
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
				addDuanzi();
				inithttp();
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
			}
		}, 5000);
	}
	
	public void Refresh(Handler tabHandler){
		this.TabHandler = tabHandler;
		addDuanzi();
		inithttp();
	}
	
	private void addDuanzi(){
		maxID = Uris.max_dz_hot;
		maxID = 20+ maxID;
		Uris.max_dz_hot = maxID;
		MyLogger.jLog().i("  maxid  " + maxID + "~~~~" );
	}
}
