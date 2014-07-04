package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.content.My_Check;
import com.example.fragment.content.My_Comment;
import com.example.fragment.content.My_Favorite;
import com.example.fragment.content.My_Message;
import com.example.fragment.content.My_Publish;
import com.example.fragment.content.My_Write;
import com.example.fragment.content.My_userinfo;
import com.example.tab.R;
import com.example.tab.XYFTEST;

public class Tab_My_Frag extends Fragment implements OnClickListener{
	private View view;
	private RelativeLayout write, check,publish,comment,favorite,message;
	private TextView edit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab_mine, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView();
		super.onActivityCreated(savedInstanceState);
		listenWidget(R.id.my_check);
		listenWidget(R.id.my_comment);
		listenWidget(R.id.my_favorite);
		listenWidget(R.id.my_message);
		listenWidget(R.id.my_publish);
		listenWidget(R.id.my_write);
	}
	
	private void initView(){
		write = (RelativeLayout)view.findViewById(R.id.my_write);
		check = (RelativeLayout)view.findViewById(R.id.my_check);
		publish = (RelativeLayout)view.findViewById(R.id.my_publish);
		comment = (RelativeLayout)view.findViewById(R.id.my_comment);
		favorite = (RelativeLayout)view.findViewById(R.id.my_favorite);
		message = (RelativeLayout)view.findViewById(R.id.my_message);
		
		setWidget(check, R.string.my_check, R.drawable.my_publish_icon, R.drawable.item_click_bottom);
		setWidget(comment, R.string.my_comment ,R.drawable.my_comment_icon ,R.drawable.item_click_center);
		setWidget(favorite, R.string.my_favorite, R.drawable.my_favorite_icon ,R.drawable.item_click_bottom);
		setWidget(message, R.string.my_message, R.drawable.my_message_icon, R.drawable.item_click_normal);
		setWidget(publish, R.string.my_publish, R.drawable.my_publish_icon ,R.drawable.item_click_top);
		setWidget(write, R.string.my_write, R.drawable.my_write_icon ,R.drawable.item_click_top);
		
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
	private void setWidget(RelativeLayout layout, int textID, int ImageID, int ResID){
		layout.setBackgroundResource(ResID);
		((TextView)layout.findViewById(R.id.my_text)).setText(textID);
		((ImageView)layout.findViewById(R.id.my_image_left)).setImageResource(ImageID);
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
		case R.id.my_check:
			My_Check check = new My_Check();
			switchFragment(this, check);
			Toast.makeText(getActivity(), "my_check", Toast.LENGTH_SHORT).show();
			break;

		case R.id.my_comment:
			My_Comment comment = new My_Comment();
			switchFragment(this, comment);
			Toast.makeText(getActivity(), "my_comment", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_message:
			My_Message message = new My_Message();
			switchFragment(this,message);
			Toast.makeText(getActivity(), "my_message", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_write:
			My_Write write = new My_Write();
			switchFragment(this, write);
			Toast.makeText(getActivity(), "my_write", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_publish:
			My_Publish publish = new My_Publish();
			switchFragment(this, publish);
			Toast.makeText(getActivity(), "my_publish", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_favorite:
			My_Favorite favorite = new My_Favorite();
			switchFragment(this, favorite);
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
