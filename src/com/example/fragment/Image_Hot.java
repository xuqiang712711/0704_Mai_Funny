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

import com.example.AsyTask.MyAsyTask;
import com.example.adapter.DuanZiAdapter;
import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.object.setDuanziData;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

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
	View view;
	private ListView listView;
	private List<Map<String, Object>> data = null;
	private String [] ImageUris ;
	DisplayImageOptions options;
	private SwipeRefreshLayout refreshLayout;
	private XAdapter adapter;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
				String json = data.getString("json");
				Log.i("YYY", "json  img  " + json);
				SetListData(json);
//				JSONArray array = new JSONArray(data.getString("json"));
//				adapter = new DuanZiAdapter(handler,DuanZi_Hot.mController,Image_Hot.this, getActivity(), array);
//				adapter = new DuanZiAdapter(Image_Hot.this,getActivity(), array);
//				listView.setAdapter(adapter);
		}
	};
	
	private void SetListData(String json){
		List<Duanzi> list = setDuanziData.getListDuanzi(json);
//		TestAdapter adapter = new TestAdapter(list, handler, DuanZi_Hot.mController, this, getActivity());
//		PicAdapter adapter = new PicAdapter(list, handler, DuanZi_Hot.mController, this, getActivity());
		adapter = new XAdapter(list, handler, MaimobApplication.mController, this, getActivity());
		listView.setAdapter(adapter);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.duanzi_tab_hot, container, false);
		initView();
		MyAsyTask asyTask = new MyAsyTask(handler);
		asyTask.execute(Uris.Img_uri);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
		refreshLayout.setOnRefreshListener(this);
		refreshLayout.setColorScheme(android.R.color.holo_blue_light, android.R.color.holo_red_light	, android.R.color.holo_purple, android.R.color.holo_green_light);
	}
	
	private void setData(){
		ImageUris = Uris.IMAGES;
		data = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < ImageUris.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content" + i, String.valueOf(i));
			map.put("image" + i, ImageUris[i]);
			data.add(map);
		}
	}
	private void initView(){
		setData();
		
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.maimob)
//		.showImageForEmptyUri(R.drawable.maimob)
//		.showImageOnFail(R.drawable.maimob)
//		.cacheInMemory(true)
//		.cacheOnDisk(true)
//		.considerExifParams(true)
//		.displayer(new SimpleBitmapDisplayer())
//		.bitmapConfig(Config.RGB_565)
//		.build();
		
		listView = (ListView)view.findViewById(R.id.listview);
//		MyAdapter adapter = new MyAdapter(data, getActivity(), options);
//		listView.setAdapter(adapter);
//		ImageAdapter adapter = new ImageAdapter(Tab_Image_Frag.this, getActivity(), data, options);
//		listView.setAdapter(adapter);
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
				MyAsyTask asyTask = new MyAsyTask(handler);
				asyTask.execute(Uris.Img_uri);
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
				adapter.notifyDataSetChanged();
			}
		}, 5000);
	}
}
