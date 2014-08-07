package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.application.MaimobApplication;
import com.example.fragment.content.My_Comment.ViewHolder;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.Uris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	private List<Map<String, Object>> data;
	private Context context;

	public CommentAdapter(List<Map<String, Object>> data, Context context) {
		this.data = data;
		this.context = context;
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.my_comment_item, null);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.my_comment_head);
			holder.name = (TextView) convertView
					.findViewById(R.id.my_comment_name);
			holder.content = (TextView) convertView
					.findViewById(R.id.my_comment_dz_content);
			holder.comment = (TextView) convertView
					.findViewById(R.id.my_comment_content);
			holder.time = (TextView) convertView
					.findViewById(R.id.my_comment_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText((String) data.get(position).get("name"));
		holder.content.setTextSize(Uris.Font_Size);
		holder.content.setText((String) data.get(position).get("content"));
		holder.comment.setTextSize(Uris.Font_Size);
		holder.comment.setText((String) data.get(position).get("comment"));
		holder.time.setText((String) data.get(position).get("time"));
		MaimobApplication.imageLoader.displayImage((String) data.get(position)
				.get("icon"), holder.icon, ImageUtil.getOption());
		return convertView;
	}

	public static class ViewHolder {
		ImageView icon;
		TextView name, comment, content, time;
	}

}
