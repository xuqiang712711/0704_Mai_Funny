package com.example.fragment;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.adapter.DuanZiAdapter;
import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.maiUtil.CustomHttpClient;
import com.example.maiUtil.Getuuid;
import com.example.object.Duanzi;
import com.example.object.setDuanziData;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.CustomImage;
import com.example.util.HttpUtil;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class DuanZi_Hot extends Fragment implements OnRefreshListener{
	View view;
	private ListView listView;
	private int[] icon = {R.drawable.game, R.drawable.game, R.drawable.game,R.drawable.game};
	private SwipeRefreshLayout refreshLayout;
	private DuanZiAdapter adapter;
	private Dialog dialog;
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
		dialog = new AlertDialog.Builder(getActivity()).setTitle("我是标题")
				.setMessage("XWKKX")
				.create();
		dialog.show();
		initView();
		inithttp();
	}
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setItemsCanFocus(true);
	}
	
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			switch (msg.what) {
			case 313:
				ChangeFont();
				break;
			default:
				dialog.dismiss();
				updateListView(json);
				break;
			}
			
		}
	};
	
	private void ChangeFont(){
		adapter.notifyDataSetChanged();
	}
	
	private void updateListView(String json){
//		adapter = new DuanZiAdapter(handler,mController,DuanZi_Hot.this, getActivity(), array);
//		listView.setAdapter(adapter);
		List<Duanzi> list = setDuanziData.getListDuanzi(json);
//		TextAdapter adapter = new TextAdapter(list, handler, mController, DuanZi_Hot.this, getActivity());
		XAdapter adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
		listView.setAdapter(adapter);
	}
	
	
	private void inithttp(){
		String postUri = "http://md.maimob.net/index.php/player/FetchPost/uuid/YTBhYWYzYmEtOTI2NC0zZDRjLThlNDQtYjExOGQ2OWQ4NGJi/type/1/subType/3/maxID/0";
		new MyAsyTask().execute(postUri);
	}
	
	class MyAsyTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return doHttpRequest(params);
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//					array = new JSONArray(result);
					Message message = Message.obtain();
					message.obj = result;
					handler.sendMessage(message);
//				DuanZiAdapter adapter = new DuanZiAdapter(DuanZi_Hot.this, getActivity(), array);
//				listView.setAdapter(adapter);
				
//				Log.i("FFF", "array  " + array + "  length  "+ array.length());
		}
		
		/**
	     * 从服务器获取数据（字符串）
	     * @param urls
	     * @return
	     */
	    private String doHttpRequest(String... urls){
	      HttpClient httpClient = CustomHttpClient.getHttpClient();
	      try {
	    	HttpGet request 	= new HttpGet(urls[0]);
	    	HttpParams params 	= new BasicHttpParams();
	        HttpConnectionParams.setSoTimeout(params, 60000);   // 1 minute
	        request.setParams(params);
	        String response = httpClient.execute(request, new BasicResponseHandler());
	        return response;
		  } catch (IOException e) {
	        e.printStackTrace();
	      }
	      return null;
	    }
		
	}
	
	public void switchFragment(Fragment from , Fragment to){
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof XYFTEST) {
			XYFTEST xyf = (XYFTEST) getActivity();
			xyf.switchContentFull(from, to);
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
//				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
			}
		}, 5000);
	}
	
}
