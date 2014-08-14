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
import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.object.setDuanziData;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.PopUtils;
import com.example.util.MyLogger;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Image_Hot extends Fragment implements OnRefreshListener{
	public MyLogger logger = MyLogger.jLog();
	View view;
	private ListView listView;
	private List<Map<String, Object>> data = null;
	private String [] ImageUris ;
	DisplayImageOptions options;
	private SwipeRefreshLayout refreshLayout;
	private XAdapter adapter;
	private Handler tabHandler;
	private Dialog dialog;
	private List<Duanzi> list;
	private int maxID;
	private int add_count_hot = 0;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == Uris.MSG_CHANGEFONT) {
				ChangeFontSize();
			}else {
				dialog.dismiss();
				String json = (String) msg.obj;
				MyLogger.jLog().i("json  " + json);
				if (json == null) {
					return;
				}
				SetListData(json);
				add_count_hot = msg.what;
				MyLogger.jLog().i("______add_count_hot  " + add_count_hot);
			}
			
		}
	};
	
	public void ChangeFontSize(){
		if (adapter != null) {
			MyLogger.jLog().e("Image_Hot changeFont");
			adapter.notifyDataSetChanged();
		}
	}
	
	
	private void SetListData(String json){
		MyLogger.jLog().i("list size old" + list.size());
		list = setDuanziData.getListDuanzi(json, getActivity(), list, 3);
		MyLogger.jLog().i("list size  " + list.size());
		if (adapter == null) {
			adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
			listView.setAdapter(adapter);
		}else {
			MyLogger.jLog().i("更新数据");
			adapter.setData(list);
			adapter.notifyDataSetChanged();
		}
		if (tabHandler != null) {
			tabHandler.sendEmptyMessage(Uris.MSG_REFRESH);
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
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			MyLogger.jLog().i("X_Hide");
			adapter.notifyDataSetChanged();
		}else {
			
		}
	}
	
	
	
	private void initHttp(){
		RequestDataTask mTask = new RequestDataTask(handler);
		MyLogger.jLog().i("maxid_new  " + maxID);
		mTask.execute(Uris.Img_uri + maxID);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		dialog = PopUtils.createLoadingDialog(getActivity());
		
		if (list == null) {
			list = new ArrayList<Duanzi>();
		}
		refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_light, android.R.color.holo_red_light	, android.R.color.holo_purple, android.R.color.holo_green_light);
		initView();
		Mai_DBhelper db = Mai_DBhelper.getInstance(getActivity());
		list = db.selectALLDuanzi(3);
		if (list.size() == 0) {
			maxID = Uris.max_img_hot;
			dialog.show();
			initHttp();
		}else {
			adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
			listView.setAdapter(adapter);
		}
	}
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
	}
	
	public void switchFragment(Fragment from, Fragment to){
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
				initHttp();
				adapter.notifyDataSetChanged();
			}
		}, 3000);
	}
	
	public void Refresh(Handler tabHandler){
		this.tabHandler = tabHandler;
		addDuanzi();
		initHttp();
		adapter.notifyDataSetChanged();
	}
	
	private void addDuanzi(){
		maxID = Uris.max_img_hot;
		MyLogger.jLog().i("  add_count_hot  " + add_count_hot + "~~~~" + "maxID  " +maxID);
		maxID = add_count_hot+ maxID;
		Uris.max_img_hot = maxID;
	}
}
