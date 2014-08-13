package com.example.fragment;

import java.util.List;
import java.util.Set;

import com.example.Activity.MaiActivity;
import com.example.Activity.My_Comments;
import com.example.application.MaimobApplication;
import com.example.fragment.content.More_Contact;
import com.example.fragment.content.More_feedback;
import com.example.fragment.content.My_Check;
import com.example.fragment.content.My_Comment;
import com.example.fragment.content.My_Favorite;
import com.example.fragment.content.My_Message;
import com.example.fragment.content.My_Publish;
import com.example.fragment.content.My_login_select;
import com.example.fragment.content.My_userinfo;
import com.example.object.Duanzi;
import com.example.object.User;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.SerUser;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.umeng.socialize.common.SocializeConstants;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_My_Frag_New extends Fragment implements OnClickListener{
private View view;
private RelativeLayout comment, check, publish, favorite,message,app, activity,backfeed,userinfo_login;
private RelativeLayout nemo1, nemo2, nemo3, nemo4;
private TextView edit;
private RelativeLayout logined, unLogin;
private String Tag = "Tab_My_Frag_New";
private TextView tv_user_description,tv_user_name;
private ImageView iv_user_head,my_arrow;
private int count_Fav= 0;
private int count_publish = 0;
private int count_comment = 0;
private My_userinfo Frag_userinfo;
private User user;
private MyLogger logger;
private boolean userIsExists = false;
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
		}
	
	@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			userIsExists = (Boolean) SharedPreferencesUtils.getParam(SharedPreferencesUtils.platform, getActivity(),
					SharedPreferencesUtils.platform_Exists, false);
            user = SerUser.deSerializationUser((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.SerUser, getActivity(),
                    SharedPreferencesUtils.user, ""));
			Mai_DBhelper db = Mai_DBhelper.getInstance(getActivity()); 
			count_Fav = db.selectFav().size();
			count_comment = db.selectComment().size();
			count_publish = db.selectPub().size();
//			logger.i("count_Fav  " + count_Fav);
			initView();
			listenWidget(R.id.my_check_new);
			listenWidget(R.id.my_favorite_new);
			listenWidget(R.id.my_message_new);
			listenWidget(R.id.my_publish_new);
			listenWidget(R.id.my_comment_new);
			listenWidget(R.id.my_app_new);
			listenWidget(R.id.my_activity_new);
			listenWidget(R.id.my_backfeed);
		}
	
	private void initView(){
		
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setVisibility(View.GONE);
		TextView bt_submit = (TextView)view.findViewById(R.id.top_right_change2);
		bt_submit.setVisibility(View.GONE);
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(R.string.tab_bar_mine);
		my_arrow = (ImageView)view.findViewById(R.id.my_arrow);
		my_arrow.setOnClickListener(this);
		
		//未登录
		unLogin = (RelativeLayout)view.findViewById(R.id.my_unlogin);
		//已登录
		logined = (RelativeLayout)view.findViewById(R.id.my_logined);
		iv_user_head = (ImageView)view.findViewById(R.id.userinfo_icon);
		tv_user_name = (TextView)view.findViewById(R.id.userinfo_name);
		tv_user_description = (TextView)view.findViewById(R.id.user_points_num);
		MyLogger.jLog().i("boo  " + userIsExists);
		if (!userIsExists) {
			unLogin.setVisibility(View.VISIBLE);
			logined.setVisibility(View.GONE);
			Log.e("FFF", "unLogin");
		} else {
			unLogin.setVisibility(View.GONE);
			logined.setVisibility(View.VISIBLE);
			tv_user_name.setText(user.getName());
			tv_user_description.setText(user.getDescription());
			MaimobApplication.imageLoader.displayImage(StringUtils
					.checkImgPath(user.getIcon()),
					iv_user_head, ImageUtil.getOption());
			Log.e("FFF", "logined");
		}
		unLogin.setOnClickListener(this);
		logined.setOnClickListener(this);
		
		check = (RelativeLayout)view.findViewById(R.id.my_check_new);
		publish = (RelativeLayout)view.findViewById(R.id.my_publish_new);
		favorite = (RelativeLayout)view.findViewById(R.id.my_favorite_new);
		message = (RelativeLayout)view.findViewById(R.id.my_message_new);
		backfeed = (RelativeLayout)view.findViewById(R.id.my_backfeed);
		comment = (RelativeLayout)view.findViewById(R.id.my_comment_new);
		app = (RelativeLayout)view.findViewById(R.id.my_app_new);
		activity = (RelativeLayout)view.findViewById(R.id.my_activity_new);
		
		setWidget(check, R.string.my_check, R.drawable.mai_2_check, R.drawable.item_click_center,2);
		setWidget(app, R.string.my_app, R.drawable.mai_2_app, R.drawable.item_click_center, 2);
		setWidget(activity, R.string.my_activity, R.drawable.mai_2_app, R.drawable.item_click_center, 2);
		setWidget(backfeed, R.string.my_feedback, R.drawable.mai_2_check, R.drawable.item_click_center, 2);
		
		setWidget(comment, R.string.my_comment, R.drawable.mai_write ,R.drawable.item_click_center,1);
		setWidget(favorite, R.string.my_favorite, R.drawable.my_favorite_icon ,R.drawable.item_click_center, 1);
		setWidget(message, R.string.my_nemo, R.drawable.my_message_icon, R.drawable.item_click_center, 1);
		setWidget(publish, R.string.my_publish, R.drawable.my_publish_icon ,R.drawable.item_click_center, 1);
		
		TextView tv_Fav = (TextView)favorite.findViewById(R.id.my_tv_tv1);
		TextView tv_Comment = (TextView)comment.findViewById(R.id.my_tv_tv1);
		TextView tv_Pub = (TextView)publish.findViewById(R.id.my_tv_tv1);
		Log.e(Tag, "Fav  " + count_Fav);
		tv_Fav.setText(String.valueOf(count_Fav));
		tv_Comment.setText(String.valueOf(count_comment));
		tv_Pub.setText(String.valueOf(count_publish));
		
		
		nemo1 = (RelativeLayout)view.findViewById(R.id.my_nemo1_new);
		nemo2 = (RelativeLayout)view.findViewById(R.id.my_nemo2_new);
		nemo3 = (RelativeLayout)view.findViewById(R.id.my_nemo3_new);
		nemo4 = (RelativeLayout)view.findViewById(R.id.my_nemo4_new);
		
		setWidget(nemo1, R.string.my_nemo, R.drawable.mai_2_app, R.drawable.item_click_normal, 2);
		setWidget(nemo2, R.string.my_nemo, R.drawable.mai_2_app, R.drawable.item_click_normal, 2);
		setWidget(nemo3, R.string.my_nemo, R.drawable.mai_2_x, R.drawable.item_click_normal, 2);
		setWidget(nemo4, R.string.my_nemo, R.drawable.mai_2_sign, R.drawable.item_click_normal, 2);
//		edit = (TextView)view.findViewById(R.id.user_info_edit);
//		edit.setOnClickListener(this);
		
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
		case R.id.my_backfeed:
			mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new More_feedback(), null);
			break;
		case R.id.my_logined:
			MyLogger.kLog().i("my_logined");
			mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_userinfo(), null);
			break;
		case R.id.my_arrow:
			Frag_userinfo = new My_userinfo();
			switchFragment(this, Frag_userinfo);
			break;
		
		case R.id.my_app_new:
			break;
		case R.id.my_activity_new:
			break;
		case R.id.my_check_new:
//			My_Check check = new My_Check();
			if (User.UserIsExists(getActivity())) {
				Tab_Search_Frag check = new Tab_Search_Frag();
				switchFragment(this, check);
			}else {
				mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_login_select(), bundle);
			}
			Toast.makeText(getActivity(), "my_check", Toast.LENGTH_SHORT).show();
			break;

		case R.id.my_comment_new:
			if (User.UserIsExists(getActivity())) {
//				My_Comment comment = new My_Comment();
//				switchFragment(this, comment);
				Intent intent = new Intent(getActivity(), My_Comments.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}else {
//				mFragmentManage.SwitchFrag(getActivity(), Tab_My_Frag_New.this, new My_login_select(), bundle);
				Intent intent = new Intent(getActivity(), My_Comments.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			Toast.makeText(getActivity(), "my_comment", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_message_new:
			My_Message message = new My_Message();
			switchFragment(this,message);
			Toast.makeText(getActivity(), "my_message", Toast.LENGTH_SHORT).show();
			break;
		case R.id.my_publish_new:
			if (User.UserIsExists(getActivity())) {
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
		if (getActivity() instanceof MaiActivity) {
			MaiActivity xyf = (MaiActivity) getActivity();
			xyf.switchContentFull(from, to , null);
		}
	}
	
	public void refresh(){
		SharedPreferences sp = getActivity().getSharedPreferences("user", Activity.MODE_PRIVATE);
		String user_name = sp.getString("name", "");
		MyLogger.jLog().i("name   " +user_name);
		if (user_name.equals("")) {
			unLogin.setVisibility(View.VISIBLE);
			logined.setVisibility(View.GONE);
			MyLogger.jLog().i("未登录");
		}else {
			unLogin.setVisibility(View.GONE);
			logined.setVisibility(View.VISIBLE);
			tv_user_name.setText(user_name);
			tv_user_description.setText(sp.getString("description", null));
			MaimobApplication.imageLoader.displayImage(sp.getString("icon", null), iv_user_head, ImageUtil.getOption());
			MyLogger.jLog().i("已登录");
		}
		
	}
	@Override
		public void onHiddenChanged(boolean hidden) {
			// TODO Auto-generated method stub
			super.onHiddenChanged(hidden);
			MyLogger.jLog().i("我叫你玉蝴蝶");
			user = SerUser.deSerializationUser((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.SerUser, getActivity(),
					SharedPreferencesUtils.user, ""));
			if (mFragmentManage.Refresh_userInfo) {
				if (!hidden) {
					unLogin.setVisibility(View.GONE);
					logined.setVisibility(View.VISIBLE);
					MyLogger.jLog().i("刷新数据~~~~ " + user.getName());
					tv_user_name.setText(user.getName());
					tv_user_description.setText(user.getDescription());
					MaimobApplication.imageLoader.displayImage(
							StringUtils.checkImgPath(user.getIcon()), iv_user_head, ImageUtil.getOption());
				}
				mFragmentManage.Refresh_userInfo = false;
			}
	
		}
	
	
}
