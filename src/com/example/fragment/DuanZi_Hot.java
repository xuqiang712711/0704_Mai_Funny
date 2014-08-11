package com.example.fragment;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.Activity.MaiActivity;
import com.example.adapter.XAdapter;
import com.example.adapter.X_Text_Adapter;
import com.example.application.MaimobApplication;
import com.example.fragment.content.DuanZi_Comment;
import com.example.maiUtil.CustomHttpClient;
import com.example.maiUtil.Getuuid;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.object.setDuanziData;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.CustomImage;
import com.example.util.DialogToastUtil;
import com.example.util.HttpUtil;
import com.example.util.MyLogger;
import com.example.util.Uris;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.Activity;
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


public class DuanZi_Hot extends Fragment implements OnRefreshListener,OnItemClickListener{
	public MyLogger myLogger = MyLogger.jLog();
	View view;
	private ListView listView;
	private SwipeRefreshLayout refreshLayout;
//	private X_Text_Adapter adapter;
	private XAdapter adapter;
	private Dialog dialog;
	private Handler TabHandler;
	private int maxId = 0;
	private int add_count = 0;
	private List<Duanzi> list;
	private JSONArray array;
	private Mai_DBhelper db ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myLogger.i("onCreateView");
		view = inflater.inflate(R.layout.duanzi_tab_hot, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		myLogger.i("onActivityCreated  adapter is null");
		refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light,
				android.R.color.holo_red_light);
//		dialog = new AlertDialog.Builder(getActivity()).setTitle("我是标题")
		dialog = DialogToastUtil.createLoadingDialog(getActivity());
		initView();
		db = Mai_DBhelper.getInstance(getActivity());
		list = db.selectALLDuanzi(1);
		myLogger.i("size  " + list.size());
		if (list.size() == 0) {
			inithttp();
			dialog.show();
		}else {
			adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
			listView.setAdapter(adapter);
		}
		maxId = Uris.max_dz_hot;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		myLogger.i("onStart");
		if (adapter == null) {
			myLogger.i("onStart" +  "  adapter is null");
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myLogger.i("onResume");
		if (adapter == null) {
			myLogger.i("onResume" +  "  adapter is null");
		}
	}
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setItemsCanFocus(true);
	}
	
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == Uris.MSG_CHANGEFONT) {
				ChangeFontSize();
			}else {
				String json = (String) msg.obj;
				add_count = msg.what;
				dialog.dismiss();
				updateListView(json);
			}
			
		}
	};
	
	public void ChangeFontSize(){
		if (adapter != null) {
			MyLogger.jLog().e("Duanzi_Hot changeFont");
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myLogger.i("onAttach");
		if (adapter == null) {
			myLogger.i("onAttach" +  "  adapter is null");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myLogger.i("onCreate");
		if (adapter == null) {
			myLogger.i("onCreate" +  "  adapter is null");
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myLogger.i("onPause ");
		if (adapter == null) {
			myLogger.i("onPause" +  "  adapter is null");
		}
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		myLogger.i("onStop");
		if (adapter == null) {
			myLogger.i("onStop" +  "  adapter is null");
		}
	}
	
	private void updateListView(String json){
		list = setDuanziData.getListDuanzi(json,getActivity(), list, 1);
		adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
		myLogger.e("initAdapter");
		if (adapter == null) {
			myLogger.e("initAdapter adapter is null");
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		if (TabHandler != null) {
			Message msg = Message.obtain();
			msg.what = Uris.MSG_REFRESH;
			TabHandler.sendMessage(msg);
		}
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			myLogger.i("hidden is true");
		}else {
			myLogger.i("hidden is false");
		}
	}
	
	
	public void inithttp(){
		MyLogger.jLog().i("maxId_new  " + maxId);
		String postUri = "http://md.maimob.net/index.php/player/FetchPost/"
				+ "uuid/"+ Uris.uuid+"/type/1/subType/3/maxID/" + maxId;
		new MyAsyTask().execute(postUri);
	}
	
	class MyAsyTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			return doHttpRequest(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
					Message message = Message.obtain();
					try {
						array = new JSONArray(result);
						myLogger.i("array  " +array.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					message.obj = result;
					message.what = array.length();
					handler.sendMessage(message);
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
				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
			}
		}, 5000);
	}
	
	public void Refresh(Handler Tabhandler){
		this.TabHandler = Tabhandler;
		addDuanzi();
		inithttp();
		adapter.notifyDataSetChanged();
	}
	
	private void addDuanzi(){
		if (array == null) {
			
		}
		maxId = Uris.max_dz_hot;
		maxId = 20+ maxId;
		Uris.max_dz_hot = maxId;
		myLogger.i("  maxid  " + maxId + "~~~~" );
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Duanzi duanzi = list.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("duanzi", duanzi);
		mFragmentManage.SwitchFrag(getActivity(), DuanZi_Hot.this, new DuanZi_Comment(), bundle);
		myLogger.i("onItemClick  " + duanzi.getContent());
	}
	
}
