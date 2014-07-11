package com.example.fragment;

import com.example.fragment.content.More_Contact;
import com.example.fragment.content.My_Check;
import com.example.fragment.content.My_Favorite;
import com.example.fragment.content.My_Message;
import com.example.fragment.content.My_Publish;
import com.example.fragment.content.My_Write;
import com.example.fragment.content.My_userinfo;
import com.example.tab.R;
import com.example.tab.XYFTEST;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_My_Frag_New extends Fragment implements OnClickListener{
private View view;
private RelativeLayout write, check, publish, favorite,message,app, activity;
private TextView edit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab_mine_new, container, false);
		return view;
	}
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			initView();
			
			listenWidget(R.id.my_check_new);
			listenWidget(R.id.my_favorite_new);
			listenWidget(R.id.my_message_new);
			listenWidget(R.id.my_publish_new);
			listenWidget(R.id.my_writer_new);
			listenWidget(R.id.my_app_new);
			listenWidget(R.id.my_activity_new);
		}
	
	private void initView(){
		write = (RelativeLayout)view.findViewById(R.id.my_writer_new);
		check = (RelativeLayout)view.findViewById(R.id.my_check_new);
		publish = (RelativeLayout)view.findViewById(R.id.my_publish_new);
		favorite = (RelativeLayout)view.findViewById(R.id.my_favorite_new);
		message = (RelativeLayout)view.findViewById(R.id.my_message_new);
		
		app = (RelativeLayout)view.findViewById(R.id.my_app_new);
		activity = (RelativeLayout)view.findViewById(R.id.my_activity_new);
		
		setWidget(check, R.string.my_check, R.drawable.mai_check, R.drawable.item_click_normal,2);
		setWidget(write, R.string.my_write, R.drawable.mai_write ,R.drawable.item_click_normal,2);
		setWidget(app, R.string.my_app, R.drawable.mai_app, R.drawable.item_click_normal, 2);
		setWidget(activity, R.string.my_activity, R.drawable.mai_activity, R.drawable.item_click_normal, 2);
		
		setWidget(favorite, R.string.my_favorite, R.drawable.my_favorite_icon ,R.drawable.item_click_normal, 1);
		setWidget(message, R.string.my_message, R.drawable.my_message_icon, R.drawable.item_click_normal, 1);
		setWidget(publish, R.string.my_publish, R.drawable.my_publish_icon ,R.drawable.item_click_normal, 1);
		
		edit = (TextView)view.findViewById(R.id.user_info_edit);
		edit.setOnClickListener(this);
	}
	
	/**
	 * 设置widget的样式
	 * @param layout
	 * @param textID
	 * @param ImageID
	 * @param ResID
	 */
	private void setWidget(RelativeLayout layout, int textID, int ImageID, int ResID , int type){
		layout.setBackgroundResource(ResID);
		switch (type) {
		case 1:
			((TextView)layout.findViewById(R.id.my_tv_tv1)).setText("0");
			((TextView)layout.findViewById(R.id.my_tv_tv2)).setText(textID);
			break;

		case 2:
			((TextView)layout.findViewById(R.id.textView1)).setText(textID);
			((ImageView)layout.findViewById(R.id.imageView1)).setImageResource(ImageID);
			break;
		}
		
	}
	
	private void listenWidget(int layoutID){
		view.findViewById(layoutID).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_info_edit:
			Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
			My_userinfo userinfo = new My_userinfo();
			switchFragment(this, userinfo);
			break;
		case R.id.my_app_new:
			break;
		case R.id.my_activity_new:
			break;
		case R.id.my_check_new:
			My_Check check = new My_Check();
			switchFragment(this, check);
			Toast.makeText(getActivity(), "my_check", Toast.LENGTH_SHORT).show();
			break;

//		case R.id.my_comment_new:
//			My_Comment comment = new My_Comment();
//			switchFragment(this, comment);
//			Toast.makeText(getActivity(), "my_comment", Toast.LENGTH_SHORT).show();
//			break;
		case R.id.my_message_new:
			My_Message message = new My_Message();
			switchFragment(this,message);
			Toast.makeText(getActivity(), "my_message", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_writer_new:
			My_Write write = new My_Write();
			switchFragment(this, write);
			Toast.makeText(getActivity(), "my_write", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_publish_new:
			My_Publish publish = new My_Publish();
			switchFragment(this, publish);
			Toast.makeText(getActivity(), "my_publish", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_favorite_new:
//			My_Favorite favorite = new My_Favorite();
//			switchFragment(this, favorite);
			More_Contact contact = new More_Contact();
			switchFragment(this,contact);
			Toast.makeText(getActivity(), "my_favorite", Toast.LENGTH_SHORT).show();
			break;
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
	
}
