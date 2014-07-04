package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.tab.R;
import com.example.util.DuanZi_Comments_Praise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DuanZi_CommentsAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>> data ;
	private Holder holder = null;
	public DuanZi_CommentsAdapter(Context context, List<Map<String, Object>> data){
		this.context = context;
		this.data = data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.duanzi_comments_items, null);
			holder = new Holder();
			holder.user_icon = (ImageView)convertView.findViewById(R.id.duanzi_comments_icon);
			holder.user_name = (TextView)convertView.findViewById(R.id.duanzi_comments_name);
			holder.time = (TextView)convertView.findViewById(R.id.duanzi_comments_time);
			holder.content = (TextView)convertView.findViewById(R.id.duanzi_comments_content);
			holder.praise = (DuanZi_Comments_Praise)convertView.findViewById(R.id.duanzi_comments_praise);
		}
		return convertView;
	}
	
	public static class Holder{
		ImageView user_icon;
		DuanZi_Comments_Praise praise;
		TextView content;
		TextView user_name;
		TextView time;
	}
}
