package com.example.fragment.content;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.MaimobApplication;
import com.example.object.User;
import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.tab.R;
import com.example.util.MyLogger;
import com.example.util.PopUtils;
import com.example.util.PopUtils.OnClickNoListener;
import com.example.util.PopUtils.OnClickYesListener;
import com.example.util.SerUser;
import com.example.util.SharedPreferencesUtils;
import com.facebook.widget.FacebookDialog.ShareDialogFeature;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.utils.OauthHelper;

public class My_userInfo_vp_home extends Fragment implements OnClickListener {
	View view;
	private RelativeLayout renren, tencent_wb, sina, douban, name, sex,
			address;
	private int sinaStatus, tencentStatus, renrenStatus, doubanStatus;
	private TextView tv_name, tv_gender, tv_location;
	private Button bt_UnRegister;
	private int type;
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo_vp_1, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		user = SerUser.deSerializationUser((String) SharedPreferencesUtils
				.getParam(SharedPreferencesUtils.SerUser, getActivity(),
						SharedPreferencesUtils.SerUser_user, ""));
		initView();
		checkPlatformStatus();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		MyLogger.jLog().i("刷新数据Vp");
		user = SerUser.deSerializationUser((String) SharedPreferencesUtils
				.getParam(SharedPreferencesUtils.SerUser, getActivity(),
						SharedPreferencesUtils.user, ""));
		if (mFragmentManage.Refresh_userInfo) {
			if (!hidden) {
				MyLogger.jLog().i("刷新数据Vp~~~~ " + user.judgeGender());
				tv_name.setText(user.getName());
				tv_location.setText(user.getLocation());
				tv_gender.setText(user.judgeGender());
			}
		}
	}

	private void initView() {

		renren = (RelativeLayout) view.findViewById(R.id.my_userinfo_bind_renren);
		tencent_wb = (RelativeLayout) view.findViewById(R.id.my_userinfo_bind_tencent_wb);
		sina = (RelativeLayout) view.findViewById(R.id.my_userinfo_bind_sina);
		douban = (RelativeLayout) view.findViewById(R.id.my_userinfo_bind_douban);

		renren.setOnClickListener(this);
		douban.setOnClickListener(this);
		tencent_wb.setOnClickListener(this);
		sina.setOnClickListener(this);

		name = (RelativeLayout) view.findViewById(R.id.my_userinfo_sec_name);
		sex = (RelativeLayout) view.findViewById(R.id.my_userinfo_sec_sex);
		address = (RelativeLayout) view.findViewById(R.id.my_userinfo_sec_address);

		((TextView) name.findViewById(R.id.more_text1)).setText("昵称");
		((TextView) name.findViewById(R.id.more_text2)).setVisibility(View.VISIBLE);
		;
		((TextView) name.findViewById(R.id.more_text2)).setText(user.getName());
		((ImageView) name.findViewById(R.id.more_img)).setVisibility(View.GONE);

		// User.decideGender(getActivity());
		((TextView) sex.findViewById(R.id.more_text2))
				.setVisibility(View.VISIBLE);
		((TextView) sex.findViewById(R.id.more_text2)).setText(user
				.judgeGender());
		((TextView) sex.findViewById(R.id.more_text1)).setText("性别");
		((ImageView) sex.findViewById(R.id.more_img)).setVisibility(View.GONE);

		((TextView) address.findViewById(R.id.more_text1)).setText("地址");
		((TextView) address.findViewById(R.id.more_text2))
				.setVisibility(View.VISIBLE);
		;
		((TextView) address.findViewById(R.id.more_text2)).setText(user
				.getLocation());
		((ImageView) address.findViewById(R.id.more_img))
				.setVisibility(View.GONE);

		bt_UnRegister = (Button) view.findViewById(R.id.my_unRegister);
		bt_UnRegister.setOnClickListener(this);
	}

	private void checkPlatformStatus() {
		// SharedPreferences sp = getActivity().getSharedPreferences("platform",
		// Activity.MODE_PRIVATE);
		// sinaStatus = sp.getInt("sina", 0);
		// tencentStatus = sp.getInt("tencent", 0);
		// renrenStatus = sp.getInt("renren", 0);
		// doubanStatus = sp.getInt("douban", 0);
		// Log.e("FFF", "sinaStatus  " + sinaStatus + "tencentStatus  " +
		// tencentStatus + "  renrenStatus  " + renrenStatus);
		//
		// if (sinaStatus == 0) {
		// ((TextView)sina.findViewById(R.id.more_text1)).setText(R.string.my_sina_unbind);
		// ((TextView)sina.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
		// sina.setOnClickListener(this);
		// }else {
		// ((TextView)sina.findViewById(R.id.more_text1)).setText(R.string.my_sina_binded);
		// ((TextView)sina.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		// }
		//
		// if (tencentStatus == 0) {
		// ((TextView)tencent_wb.findViewById(R.id.more_text1)).setText(R.string.my_tencent_wb_unbind);
		// ((TextView)tencent_wb.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
		// tencent_wb.setOnClickListener(this);
		// }else {
		// ((TextView)tencent_wb.findViewById(R.id.more_text1)).setText(R.string.my_tencent_wb_binded);
		// ((TextView)tencent_wb.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		// }
		//
		// if (renrenStatus == 0) {
		// ((TextView)renren.findViewById(R.id.more_text1)).setText(R.string.my_renren_unbind);
		// ((TextView)renren.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
		// renren.setOnClickListener(this);
		// }else {
		// ((TextView)renren.findViewById(R.id.more_text1)).setText(R.string.my_renren_binged);
		// ((TextView)renren.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		// }
		//
		// if (doubanStatus == 0) {
		// ((TextView)douban.findViewById(R.id.more_text1)).setText(R.string.my_douban_unbind);
		// ((TextView)douban.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.red));
		// douban.setOnClickListener(this);
		// }else {
		// ((TextView)douban.findViewById(R.id.more_text1)).setText(R.string.my_douban_binged);
		// ((TextView)douban.findViewById(R.id.more_text1)).setTextColor(getActivity().getResources().getColor(R.color.black));
		// }

		if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.RENREN)) {
			((TextView) renren.findViewById(R.id.more_text1))
					.setText(R.string.my_renren_unbind);
			((TextView) renren.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.red));
		} else {
			((TextView) renren.findViewById(R.id.more_text1))
					.setText(R.string.my_renren_binged);
			((TextView) renren.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.black));
		}
		if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.DOUBAN)) {
			((TextView) douban.findViewById(R.id.more_text1))
					.setText(R.string.my_douban_unbind);
			((TextView) douban.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.red));
		} else {
			((TextView) douban.findViewById(R.id.more_text1))
					.setText(R.string.my_douban_binged);
			((TextView) douban.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.black));
		}
		if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.TENCENT)) {
			((TextView) tencent_wb.findViewById(R.id.more_text1))
					.setText(R.string.my_tencent_wb_unbind);
			((TextView) tencent_wb.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.red));
		} else {
			((TextView) tencent_wb.findViewById(R.id.more_text1))
					.setText(R.string.my_tencent_wb_binded);
			((TextView) tencent_wb.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.black));
		}
		if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.SINA)) {
			((TextView) sina.findViewById(R.id.more_text1))
					.setText(R.string.my_sina_unbind);
			((TextView) sina.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.red));
		} else {
			((TextView) sina.findViewById(R.id.more_text1))
					.setText(R.string.my_sina_binded);
			((TextView) sina.findViewById(R.id.more_text1))
					.setTextColor(getActivity().getResources().getColor(
							R.color.black));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_userinfo_bind_sina:

			
			if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.SINA)) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.SINA,
						My_login_select.sina, mHandler);
			} else {
				popShow(SHARE_MEDIA.SINA,"新浪微博");
			}
			break;

		case R.id.my_userinfo_bind_tencent_wb:
			if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.TENCENT)) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.TENCENT,
						My_login_select.tencent, mHandler);
			} else {
				popShow(SHARE_MEDIA.TENCENT,"腾讯微博");
			}
			break;
		case R.id.my_userinfo_bind_douban:
			if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.DOUBAN)) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.DOUBAN,
						My_login_select.douban, mHandler);
			} else {
				popShow(SHARE_MEDIA.DOUBAN,"豆瓣网");
			}
			break;
		case R.id.my_userinfo_bind_renren:
			if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.RENREN)) {
				mOauth.doOauth(getActivity(), SHARE_MEDIA.RENREN,
						My_login_select.renren, mHandler);
			} else {
				popShow(SHARE_MEDIA.RENREN,"人人网");
			}
			break;
		case R.id.my_unRegister:
			PopUtils.showQuestion(getActivity(), "提示", "确定退出账号吗", new OnClickNoListener() {
				
				@Override
				public void onClickNo() {
					// TODO Auto-generated method stub

				}
			}, new OnClickYesListener() {
				
				@Override
				public void onClickYes() {
					// TODO Auto-generated method stub
					type = My_login_select.From_My;
					mOauth.delOauth(getActivity());
					mFragmentManage.BackStatck(getActivity());
				}
			});
			break;
		}
		// mFragmentManage.BackStatck(getActivity());
		// refreshThis();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// mFragmentManage.RefreshFrag(getActivity(),
		// mFragmentManage.Tag_Userinfo);
		if (type == My_login_select.From_My) {
			mFragmentManage.RefreshFrag(getActivity(), mFragmentManage.Tag_My);
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				checkPlatformStatus();
			}
		}
	};

	private void popShow(final SHARE_MEDIA media,String platform){
		PopUtils.showQuestion(getActivity(), "提示", "确定解除绑定的"+platform+"账号？", new OnClickNoListener() {
			
			@Override
			public void onClickNo() {
				// TODO Auto-generated method stub
				
			}
		}, new OnClickYesListener() {
			
			@Override
			public void onClickYes() {
				// TODO Auto-generated method stub
				mOauth.deletePlatform(getActivity(), media, mHandler);
			}
		});
	}
}
