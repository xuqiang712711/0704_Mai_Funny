package com.example.Activity;

import java.util.List;
import java.util.Map;

import com.example.application.MaimobApplication;
import com.example.fragment.content.DuanZi_Comment;
import com.example.fragment.content.My_Comment;
import com.example.fragment.content.My_Comment.ViewHolder;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.Uris;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class My_Comments extends BaseActivity implements OnItemClickListener, OnClickListener{
	private List<Map<String, Object>> data;
	private LayoutInflater mInflater;
	private TextView tv_note;
	private ListView lv_comment;
	Mai_DBhelper db = Mai_DBhelper.getInstance(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_comment);
		initView();
	}
	
	private void initView(){
		tv_note = (TextView) findViewById(R.id.my_comment_note);
		TextView tv_title = (TextView)findViewById(R.id.top_text);
		tv_title.setText(getResources().getString(R.string.my_comment_title));
		ImageView back = (ImageView)findViewById(R.id.top_left_change);
		TextView bt_right = (TextView)findViewById(R.id.top_right_change2);
		bt_right.setVisibility(View.GONE);
		back.setOnClickListener(this);
		
		db = Mai_DBhelper.getInstance(this);
		data = db.selectComment();
		Duanzi duanzi = db.selectDuanzi(13);
		if (duanzi != null) {
			MyLogger.jLog().i("name " +duanzi.getUserName() + "  content " + duanzi.getContent());
		}
		if (data.size() > 0) {
			lv_comment = (ListView)findViewById(R.id.my_comment_lv);
			lv_comment.setOnItemClickListener(this);
			CommentAdapter adapter = new CommentAdapter();
			lv_comment.setAdapter(adapter);
		}else {
			tv_note.setVisibility(View.VISIBLE);
		}
	}
	
	class CommentAdapter extends BaseAdapter{
		public CommentAdapter(){
			
		}
		@Override
		public int getCount() {
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
				LayoutInflater inflater = LayoutInflater.from(My_Comments.this);
				convertView = inflater.inflate(R.layout.my_comment_item, null);
				holder.icon = (ImageView) convertView.findViewById(R.id.my_comment_head);
				holder.name = (TextView)convertView.findViewById(R.id.my_comment_name);
				holder.content = (TextView)convertView.findViewById(R.id.my_comment_dz_content);
				holder.comment = (TextView)convertView.findViewById(R.id.my_comment_content);
				holder.time = (TextView)convertView.findViewById(R.id.my_comment_time);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.name.setText((String)data.get(position).get("name"));
			holder.content.setTextSize(Uris.Font_Size);
			MyLogger.jLog().i("是否为图片  " + "是");
			if (!((String)data.get(position).get("imgurl")).equals("")) {
				holder.content.setText("图片");
				MyLogger.jLog().i("是否为图片  " + "是");
			}else {
				MyLogger.jLog().i("是否为图片  " + "否");
				holder.content.setText((String)data.get(position).get("dz_user_name") + " : "+(String)data.get(position).get("content"));
			}
			holder.comment.setTextSize(Uris.Font_Size);
			holder.comment.setText((String)data.get(position).get("comment"));
			holder.time.setText((String)data.get(position).get("time"));
			MyLogger.jLog().i("content  " + (String)data.get(position).get("content") + "  comment  " + (String)data.get(position).get("comment") );
			MaimobApplication.imageLoader.displayImage((String)data.get(position).get("icon"), holder.icon, ImageUtil.getOption());
			return convertView;
		}
		
	}
	
	public static class ViewHolder{
		ImageView icon;
		TextView name,comment,content,time;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		int pid = (Integer) data.get(position).get("pid");
		Duanzi duanzi = db.selectDuanzi(pid);
		MyLogger.jLog().i("duanzi  " + duanzi.getContent());
		bundle.putSerializable("duanzi", duanzi);
//		mFragmentManage.SwitchFrag(getActivity(), My_Comment.this, new DuanZi_Comment(), bundle);
		Xlog.i("跳转还没做~~~~~~~~~~~~");
		//TODO
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.top_left_change) {
			finish();
		}
	}
}
