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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.DuanZiAdapter;
import com.example.fragment.DuanZi_Hot.MyAsyTask;
import com.example.maiUtil.CustomHttpClient;
import com.example.tab.R;
import com.example.tab.XYFTEST;

public class Duanzi_Recommend extends Fragment implements OnRefreshListener{
	private String test_content1 = " GDP和波波维奇依然畏惧而谨慎，这不是攒人品，这的的确确是刻骨铭心一辈子不敢忘。两年前，西部决赛第二战，无论是个人能力还是团队战术，马刺都打出了极高的水平，马努上半场结束时空手跑位，腾空接球出手像一只悬停的老鹰一样浮在空中投出去一记压哨三分。";
	private String test_content2 = " 魔术师饱含着热泪说——如果奈史密斯博士在天有灵，见到马刺的团队进攻，肯定会含笑九泉，说——这才是我想要的篮球！我们姑且把他的话当做一种鼓励和缅怀，因为马刺现在这个状况，和他的showtime时代有异曲同工之妙。他和伯德不止一次说过：“我们那个年代，没有这么多蛋疼的胯下运球、个人单打之类的玩意儿，我们就是不停的跑动、传球、投篮，然后就赢了，我们推个快攻，只需要运三下球。";
	private String test_content3 = "这两场球，和2012年的两场球其实并没有区别。比分差距大了一些而已，你对比二少的笑容和GDP的冷脸，就可以发现压力到底在哪边？";
	private String test_content4 = "能这么说，小牛现在的阵容是夺冠年以来最合理的，卡尔德隆刚好是德克喜欢的那种传统PG（有三分不乱打），埃利斯成为了近年来最合适做二当家的那一个（除了特里之外最勇敢的持球手，霍华德之后最强突破手），而且埃利斯的比赛习惯刚好是德克最喜欢的（靠着个人单打和突分顶住前三节，第四节不能独力扛起球队，而德克最擅长的就是第四节现身）。";
	View view;
	private ListView listView;
	private String[] name = {"qie", "le", "sheng", "qian"};
	private int[] icon = {R.drawable.game, R.drawable.game, R.drawable.game,R.drawable.game};
	private String [] content = {test_content1	,test_content2,test_content3 , test_content4};
	private String[] comment = {"格兰特船长的儿女", "神秘岛", "海底两万里" ,"推推棒"};
	private List<Map<String, Object>> data = null;
	private JSONArray array = null;;
	private SwipeRefreshLayout refreshLayout;
	private DuanZiAdapter adapter;
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
	
	private void initView(){
		listView = (ListView)view.findViewById(R.id.listview);
		listView.setItemsCanFocus(true);
		data = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name" + i, name[i]);
			map.put("icon" + i, icon[i]);
			map.put("content" + i, content[i]);
			map.put("comment" + i, comment[i]);
			data.add(map);
		}
	}
	
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			updateListView();
		}
	};
	
	private void updateListView(){
		adapter = new DuanZiAdapter(handler,DuanZi_Hot.mController,Duanzi_Recommend.this, getActivity(), array);
//		adapter = new DuanZiAdapter(Duanzi_Recommend.this, getActivity(), array);
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
			try {
				array = new JSONArray(result);
//				DuanZiAdapter adapter = new DuanZiAdapter(DuanZi_Hot.this, getActivity(), array);
//				listView.setAdapter(adapter);
				Message message = Message.obtain();
				handler.sendMessage(message);
				Log.i("FFF", "array  " + array + "  length  "+ array.length());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				adapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
			}
		}, 5000);
	}
	
}
