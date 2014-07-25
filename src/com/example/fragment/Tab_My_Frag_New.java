package com.example.fragment;

import java.util.Set;

import com.example.Activity.OauthActivity;
import com.example.Activity.XYFTEST;
import com.example.application.MaimobApplication;
import com.example.fragment.content.More_Contact;
import com.example.fragment.content.My_Check;
import com.example.fragment.content.My_Comment;
import com.example.fragment.content.My_Favorite;
import com.example.fragment.content.My_Message;
import com.example.fragment.content.My_Publish;
import com.example.fragment.content.My_login_select;
import com.example.fragment.content.My_userinfo;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.SharedPreferencesUtils;
import com.example.util.UserUtils;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_My_Frag_New extends Fragment implements OnClickListener{
private View view;
private RelativeLayout comment, check, publish, favorite,message,app, activity;
private TextView edit;
private RelativeLayout logined, unLogin;
private String Tag = "Tab_My_Frag_New";
private TextView tv_user_description,tv_user_name;
private ImageView iv_user_head;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e(Tag, "onCreateView");
		view = inflater.inflate(R.layout.tab_mine_new, container, false);
		return view;
	}
	
	@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			Log.e(Tag, "onPause");
		}
	
	@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			Log.e(Tag, "onActivityCreated");
			initView();
			
			Mai_DBhelper db = Mai_DBhelper.getInstance(getActivity());
			int max = db.selectFavCount();
			Log.e(Tag, "max  " + max);
			
			listenWidget(R.id.my_check_new);
			listenWidget(R.id.my_favorite_new);
			listenWidget(R.id.my_message_new);
			listenWidget(R.id.my_publish_new);
			listenWidget(R.id.my_comment_new);
			listenWidget(R.id.my_app_new);
			listenWidget(R.id.my_activity_new);
		}
	
	private void initView(){
		
		//未登录
		unLogin = (RelativeLayout)view.findViewById(R.id.my_unlogin);
		//已登录
		logined = (RelativeLayout)view.findViewById(R.id.my_logined);
		iv_user_head = (ImageView)view.findViewById(R.id.userinfo_icon);
		tv_user_name = (TextView)view.findViewById(R.id.userinfo_name);
		tv_user_description = (TextView)view.findViewById(R.id.user_points_num);
		
		SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
		String user_name = sp.getString("name", null);
		if (user_name == null) {
			unLogin.setVisibility(View.VISIBLE);
			logined.setVisibility(View.GONE);
			Log.e("FFF", "unLogin");
		}else {
			unLogin.setVisibility(View.GONE);
			logined.setVisibility(View.VISIBLE);
			tv_user_name.setText(user_name);
			tv_user_description.setText(sp.getString("description", null));
			MaimobApplication.imageLoader.displayImage(sp.getString("icon", null), iv_user_head, ImageUtil.getOption());
			Log.e("FFF", "logined");
		}

		
		unLogin.setOnClickListener(this);
		RelativeLayout userinfo = (RelativeLayout)view.findViewById(R.id.my_userinfo_top);
		userinfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				MaimobApplication.loginController.openUserCenter(getActivity(), SocializeConstants.FLAG_USER_CENTER_HIDE_LOGININFO);
			}
		});
		
		comment = (RelativeLayout)view.findViewById(R.id.my_comment_new);
		check = (RelativeLayout)view.findViewById(R.id.my_check_new);
		publish = (RelativeLayout)view.findViewById(R.id.my_publish_new);
		favorite = (RelativeLayout)view.findViewById(R.id.my_favorite_new);
		message = (RelativeLayout)view.findViewById(R.id.my_message_new);
		
		app = (RelativeLayout)view.findViewById(R.id.my_app_new);
		activity = (RelativeLayout)view.findViewById(R.id.my_activity_new);
		
		setWidget(check, R.string.my_check, R.drawable.mai_check, R.drawable.item_click_normal,2);
		setWidget(comment, R.string.my_comment, R.drawable.mai_write ,R.drawable.item_click_normal,2);
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
		Bundle bundle = new Bundle();
		bundle.putInt("xwkkx", My_login_select.From_My);
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
//			My_Check check = new My_Check();
			if (UserUtils.UserIsExists(getActivity())) {
				Tab_Search_Frag check = new Tab_Search_Frag();
				switchFragment(this, check);
			}else {
				mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_login_select(), bundle);
			}
			Toast.makeText(getActivity(), "my_check", Toast.LENGTH_SHORT).show();
			break;

		case R.id.my_comment_new:
			if (UserUtils.UserIsExists(getActivity())) {
				My_Comment comment = new My_Comment();
				switchFragment(this, comment);
			}else {
				mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_login_select(), bundle);
			}
			Toast.makeText(getActivity(), "my_comment", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_message_new:
			My_Message message = new My_Message();
			switchFragment(this,message);
			Toast.makeText(getActivity(), "my_message", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_publish_new:
			if (UserUtils.UserIsExists(getActivity())) {
				My_Publish publish = new My_Publish();
				switchFragment(this, publish);
			}else {
				mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_login_select(), bundle);
			}
			break;
		case R.id.my_favorite_new:
			My_Favorite favorite = new My_Favorite();
			switchFragment(this, favorite);
			Toast.makeText(getActivity(), "my_favorite", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_unlogin:
			 mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_login_select(), bundle);
			break;
		}
	}
	
	public void switchFragment(Fragment from , Fragment to){
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof XYFTEST) {
			XYFTEST xyf = (XYFTEST) getActivity();
			xyf.switchContentFull(from, to , null);
		}
	}
	
	public void refresh(){
		Log.e(Tag, "~~~~~~~~~~refresh");
		SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
		String user_name = sp.getString("name", null);
		if (user_name == null) {
			unLogin.setVisibility(View.VISIBLE);
			logined.setVisibility(View.GONE);
			Log.e("FFF", "unLogin");
		}else {
			unLogin.setVisibility(View.GONE);
			logined.setVisibility(View.VISIBLE);
			tv_user_name.setText(user_name);
			tv_user_description.setText(sp.getString("description", null));
			MaimobApplication.imageLoader.displayImage(sp.getString("icon", null), iv_user_head, ImageUtil.getOption());
			Log.e("FFF", "logined");
		}
	}
	
}
