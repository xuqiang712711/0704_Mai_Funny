package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.Activity.MaiActivity;
import com.example.AsyTask.RequestDataTask;
import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.setDuanziData;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.PopUtils;
import com.example.util.MyLogger;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
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

public class Image_New extends Fragment implements OnRefreshListener{

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
	private int add_count_new = 0;
	private int maxID ;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == Uris.MSG_CHANGEFONT) {
				ChangeFontSize();
			}else {
				dialog.dismiss();
				String json = (String) msg.obj;
				if (json == null) {
					return;
				}
				add_count_new = msg.what;
				MyLogger.jLog().i("______add_count_new  " + add_count_new);
				SetListData(json);
			}
		}
	};
	
	public void ChangeFontSize(){
		if (adapter != null) {
			MyLogger.jLog().e("Image_New changeFont");
			adapter.notifyDataSetChanged();
		}
	}
	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			ChangeFontSize();
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
		dialog = PopUtils.createLoadingDialog(getActivity());
		initView();
		refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_light, android.R.color.holo_red_light	, android.R.color.holo_purple, android.R.color.holo_green_light);
		Mai_DBhelper db = Mai_DBhelper.getInstance(getActivity());
		list = db.selectALLDuanzi(3);
		if (list.size() == 0) {
			maxID  = Uris.max_img_hot;
			dialog.show();
			initHttp();
		}else {
			MyLogger.jLog().i("list size  " + list.size());
			adapter = new XAdapter(list, handler, MaimobApplication.mController, Image_New.this, getActivity());
			listView.setAdapter(adapter);
		}
		MyLogger.jLog().i("~!!!!add_count_new  " + add_count_new);
	}
	
	private void SetListData(String json){
		list = setDuanziData.getListDuanzi(json, getActivity(), list, 3);
		if (adapter == null) {
			adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
			listView.setAdapter(adapter);
		}else {
			adapter.setData(list);
			adapter.notifyDataSetChanged();
		}
		if (tabHandler != null) {
			Log.i("XXX", "NEW_REFRESH");
			tabHandler.sendEmptyMessage(Uris.MSG_REFRESH);
		}
	}
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int num = position + 1;
				Toast.makeText(getActivity(), "点击查看大图  ~~ position   " + num , Toast.LENGTH_SHORT).show();
			}
		});
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
			}
		}, 5000);
	}
	
	public void Refresh(Handler tabHandler){
		this.tabHandler = tabHandler;
		addDuanzi();
		initHttp();
	}
	
	private void addDuanzi(){
		maxID = Uris.max_img_hot;
		MyLogger.jLog().i("  add_count_new  " + add_count_new + "~~~~" + "maxID  " +maxID);
		maxID = add_count_new+ maxID;
		Uris.max_img_hot = maxID;
	}
	
	private void initHttp(){
		RequestDataTask mTask = new RequestDataTask(handler);
		MyLogger.jLog().i("maxid_new  " + maxID);
		mTask.execute(Uris.Img_uri + maxID);
	}
}
