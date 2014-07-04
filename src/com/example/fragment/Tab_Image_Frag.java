package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Fragment;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.AsyTask.MyAsyTask;
import com.example.adapter.DuanZiAdapter;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

//public class Tab_Image_Frag extends Fragment{
//	View view;
//	private ListView listView;
//	private List<Map<String, Object>> data = null;
//	private String [] ImageUris ;
//	DisplayImageOptions options;
//	
//	private Handler handler = new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			Bundle data = msg.getData();
//			try {
//				JSONArray array = new JSONArray(data.getString("json"));
//				DuanZiAdapter adapter = new DuanZiAdapter(Tab_Image_Frag.this,getActivity(), array);
//				listView.setAdapter(adapter);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	};
//	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
////		return super.onCreateView(inflater, container, savedInstanceState);
//		view = inflater.inflate(R.layout.duanzi_tab_hot, container, false);
//		initView();
//		MyAsyTask asyTask = new MyAsyTask(handler);
//		asyTask.execute(Uris.Img_uri);
//		return view;
//	}
//	
//	
//	private void setData(){
//		ImageUris = Uris.IMAGES;
//		data = new ArrayList<Map<String,Object>>();
//		for (int i = 0; i < ImageUris.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("content" + i, String.valueOf(i));
//			map.put("image" + i, ImageUris[i]);
//			data.add(map);
//		}
//	}
//	private void initView(){
//		setData();
//		
////		options = new DisplayImageOptions.Builder()
////		.showImageOnLoading(R.drawable.maimob)
////		.showImageForEmptyUri(R.drawable.maimob)
////		.showImageOnFail(R.drawable.maimob)
////		.cacheInMemory(true)
////		.cacheOnDisk(true)
////		.considerExifParams(true)
////		.displayer(new SimpleBitmapDisplayer())
////		.bitmapConfig(Config.RGB_565)
////		.build();
//		
//		listView = (ListView)view.findViewById(R.id.listview);
////		MyAdapter adapter = new MyAdapter(data, getActivity(), options);
////		listView.setAdapter(adapter);
////		ImageAdapter adapter = new ImageAdapter(Tab_Image_Frag.this, getActivity(), data, options);
////		listView.setAdapter(adapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				int num = position + 1;
//				Toast.makeText(getActivity(), "点击查看大图  ~~ position   " + num , Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//	
//	public void switchFragment(Fragment from, Fragment to){
//		if (getActivity() == null) {
//			return;
//		}
//		if (getActivity() instanceof XYFTEST) {
//			XYFTEST xyf = (XYFTEST) getActivity();
//			xyf.switchContentFull(from, to);
//		}
//	}
//}
