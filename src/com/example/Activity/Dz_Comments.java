package com.example.Activity;

import java.util.List;
import java.util.Map;


import com.example.application.MaimobApplication;
import com.example.fragment.content.Duanzi_Comments_Lv_head;
import com.example.object.Duanzi;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Dz_Comments extends BaseActivity  implements OnClickListener{
	private Duanzi duanzi;
	private ListView listView;
	private List<Map<String, Object>> list;
	private ComAdapter adapter;
	private Mai_DBhelper db;
	private Duanzi_Comments_Lv_head head;
	private int result_Code = 333;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.duanzi_comments);
		Bundle bundle = getIntent().getExtras();
		duanzi = (Duanzi) bundle.getSerializable("duanzi");
		Xlog.i("duanzi  " + duanzi.getContent());
		initTopView();
		initListView();
	}
	
	private void initTopView(){
		TextView textView = (TextView)findViewById(R.id.top_text);
		textView.setText(getResources().getString(R.string.app_name));
		ImageView back = (ImageView)findViewById(R.id.top_left_change);
		TextView report = (TextView)findViewById(R.id.top_right_change2);
		report.setVisibility(View.GONE);
		back.setOnClickListener(this);
	}
	
	private void initListView(){
		db = Mai_DBhelper.getInstance(this);
		list = db.selectComFromID(Integer.parseInt(duanzi.getPoid()));
		listView = (ListView)findViewById(R.id.duanzi_comment_listview);
		adapter = new ComAdapter();
		head = new Duanzi_Comments_Lv_head(this);
//		head.addDuanzi(duanzi,this);
//		listView.addHeaderView(head);
//		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.top_left_change) {
			Xlog.i("left _back");
			finish();
		}
	}
	
	class ComAdapter extends BaseAdapter{

		public ComAdapter(){
			
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			MyLogger.jLog().i("xwkkx  " +list.size());
			ComViewHolder holder = null;
			if (convertView == null) {
				holder = new ComViewHolder();
				LayoutInflater inflater = LayoutInflater.from(Dz_Comments.this);
				convertView = inflater.inflate(R.layout.duanzi_comments_items, null);
				holder.tv_content = (TextView)convertView.findViewById(R.id.dz_com_lv_content);
				holder.tv_name = (TextView)convertView.findViewById(R.id.dz_com_lv_name);
				holder.tv_time = (TextView)convertView.findViewById(R.id.dz_com_lv_time);
				holder.iv_icon = (ImageView)convertView.findViewById(R.id.dz_com_lv_icon);
				convertView.setTag(holder);
			}else {
				holder  = (ComViewHolder) convertView.getTag();
			}
			holder.tv_name.setText((String) list.get(position).get("name"));
			holder.tv_content.setText((String) list.get(position).get("comment"));
			holder.tv_time.setText((String) list.get(position).get("time"));
			MaimobApplication.imageLoader.displayImage((String) list.get(position)
					.get("icon"), holder.iv_icon, ImageUtil.getOption());
			return convertView;
		}
	}
	
	public static class  ComViewHolder{
		TextView tv_name, tv_time,tv_zan,tv_content;
		ImageView iv_icon,iv_zan;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == result_Code) {
			Xlog.i("activityforresult");
			head.LayoutChange();
			list = db.selectComFromID(Integer.parseInt(duanzi.getPoid()));
			MyLogger.jLog().i("size  " + list.size() +"  null?  " + listView == null);
//			if (list.size() == 1) {
//				listView.setVisibility(View.VISIBLE);
//				adapter = new ComAdapter();
//				listView.setAdapter(adapter);
//			}else {
//				adapter.notifyDataSetChanged();
//			}
			adapter.notifyDataSetChanged();
		}
	}
}
