package com.example.fragment;

import java.io.File;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.MaiActivity;
import com.example.adapter.XAdapter;
import com.example.adapter.X_Text_Adapter;
import com.example.application.MaimobApplication;
import com.example.fragment.content.More_Contact;
import com.example.fragment.content.More_Help;
import com.example.fragment.content.More_feedback;
import com.example.fragment.content.My_Favorite;
import com.example.fragment.content.My_Write;
import com.example.tab.R;
import com.example.util.MyLogger;
import com.example.util.SharedPreferencesUtils;
import com.example.util.Uris;

public class Tab_More_Frag extends Fragment implements OnClickListener{
	private View view;
	private MyLogger myLogger = MyLogger.jLog();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myLogger.i("onCreateView");
		view = inflater.inflate(R.layout.tab_more, container, false);
		initTextView();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		myLogger.i("onActivityCreated");
		listenCheckbox();
		listenRadiobutton();
		listenLayout();
	}
	
	
	private void initTextView(){
		ImageView iv_back = (ImageView)view.findViewById(R.id.top_left_change);
		iv_back.setVisibility(View.GONE);
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(R.string.tab_bar_more);
		TextView bt_submit = (TextView)view.findViewById(R.id.top_right_change2);
		bt_submit.setVisibility(View.GONE);
		
		setText(R.id.more_feedback, R.string.more_text_feedback);
//		setText(R.id.more_bookmarker, R.string.more_text_bookmarker);
		setText(R.id.more_Cache, R.string.more_text_cache);
		setText(R.id.more_contact, R.string.more_text_contact);
		setText(R.id.more_help, R.string.more_text_help );
//		setText(R.id.more_push, R.string.more_text_push );
//		setText(R.id.more_dark, R.string.more_text_dark);
		setText(R.id.more_recommond, R.string.more_text_recommend);
		setText(R.id.more_update, R.string.more_text_update);
		setText(R.id.more_zhuanfa, R.string.more_text_zhuanfa);
		setText(R.id.more_fontSize, R.string.more_text_font);
		
		view.findViewById(R.id.more_recommond).setBackgroundResource(R.drawable.item_click_center);
		view.findViewById(R.id.more_update).setBackgroundResource(R.drawable.item_click_center);
		
		view.findViewById(R.id.more_feedback).setBackgroundResource(R.drawable.item_click_center);
		view.findViewById(R.id.more_help).setBackgroundResource(R.drawable.item_click_center);
		
		view.findViewById(R.id.more_Cache).setBackgroundResource(R.drawable.item_click_center);
		view.findViewById(R.id.more_contact).setBackgroundResource(R.drawable.item_click_center);
	}
	
	private void setText(int id, int resID){
		((TextView)view.findViewById(id).findViewById(R.id.more_text1)).setText(resID);
	}
	
	private void listenCheckbox(){
		CheckBox cb = (CheckBox)view.findViewById(R.id.more_checkbox);
		if ((Boolean) SharedPreferencesUtils.getParam(
				SharedPreferencesUtils.setting, getActivity(),
				SharedPreferencesUtils.setting_isZhuanfa, false)) {
			cb.setChecked(true);
		}else {
			cb.setChecked(false);
		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Toast.makeText(getActivity(), "选中", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtils.setParam("setting", getActivity(), "isZhuanfa", true);
				}else {
					Toast.makeText(getActivity(), "未选中", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtils.setParam("setting", getActivity(), "isZhuanfa", false);
				}
			}
		});
		
//		setCheckBox(R.id.more_zhuanfa, R.id.more_checkbox);
//		setCheckBox(R.id.more_bookmarker, R.id.more_checkbox);
//		setCheckBox(R.id.more_dark, R.id.more_checkbox);
//		setCheckBox(R.id.more_push, R.id.more_checkbox);
	}
	
	private void setCheckBox(int id ,int ResID){
		((CheckBox)view.findViewById(id).findViewById(ResID)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Toast.makeText(getActivity(), "选中", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtils.setParam("setting", getActivity(), "isZhuanfa", true);
				}else {
					Toast.makeText(getActivity(), "未选中", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtils.setParam("setting", getActivity(), "isZhuanfa", false);
				}
			}
		});
	}
	
	private void listenRadiobutton(){
		RadioGroup rg = (RadioGroup)view.findViewById(R.id.radioGroup1);
		RadioButton rb_small = (RadioButton)rg.findViewById(R.id.radio0);
		RadioButton rb_normal = (RadioButton)rg.findViewById(R.id.radio1);
		RadioButton rb_big = (RadioButton)rg.findViewById(R.id.radio2);
		String fontSize = (String) SharedPreferencesUtils.getParam(SharedPreferencesUtils.setting, getActivity(),
				SharedPreferencesUtils.setting_font, "small");
		if (fontSize.equals("small")) {
			rb_small.setChecked(true);
		}else if (fontSize.equals("normal")) {
			rb_normal.setChecked(true);
		}else if (fontSize.equals("big")) {
			rb_big.setChecked(true);
		}
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				Log.e("FFF", "fan");
				if (checkedId == R.id.radio0) {
					Uris.Font_Size = Uris.Font_Size_small;
//					XAdapter.SetNormal();
					SharedPreferencesUtils.setParam(SharedPreferencesUtils.setting, getActivity(),
							SharedPreferencesUtils.setting_font, "small");
//					DuanZi_Hot hot = new DuanZi_Hot();
//					hot.ChangeFontSize();
//					Image_Hot img_hot = new Image_Hot();
//					img_hot.ChangeFontSize();
				}else if (checkedId == R.id.radio1) {
					Uris.Font_Size = Uris.Font_Size_normal;
//					XAdapter.SetNormal();
					SharedPreferencesUtils.setParam(SharedPreferencesUtils.setting, getActivity(),
							SharedPreferencesUtils.setting_font, "normal");
//					DuanZi_Hot hot = new DuanZi_Hot();
//					hot.ChangeFontSize();
//					Image_Hot img_hot = new Image_Hot();
//					img_hot.ChangeFontSize();
				}else if (checkedId == R.id.radio2) {
					Uris.Font_Size = Uris.Font_Size_big;
//					XAdapter.SetNormal();
					SharedPreferencesUtils.setParam(SharedPreferencesUtils.setting, getActivity(),
							SharedPreferencesUtils.setting_font, "big");
//					DuanZi_Hot hot = new DuanZi_Hot();
//					hot.ChangeFontSize();
//					Image_Hot img_hot = new Image_Hot();
//					img_hot.ChangeFontSize();
				}
			}
		});
		
//		((RadioGroup)view.findViewById(R.id.more_fontSize).findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				Log.e("FFF", "Fuck ");
//				if (checkedId == R.id.radio0) {
//					Uris.Font_Size = Uris.Font_Size_small;
//					XAdapter.SetNormal();
//					SharedPreferencesUtils.setParam(SharedPreferencesUtils.setting, getActivity(),
//							SharedPreferencesUtils.setting_font, "small");
//				}else if (checkedId == R.id.radio1) {
//					Uris.Font_Size = Uris.Font_Size_normal;
//					XAdapter.SetNormal();
//					SharedPreferencesUtils.setParam(SharedPreferencesUtils.setting, getActivity(),
//							SharedPreferencesUtils.setting_font, "normal");
//				}else if (checkedId == R.id.radio2) {
//					Uris.Font_Size = Uris.Font_Size_big;
//					XAdapter.SetNormal();
//					SharedPreferencesUtils.setParam(SharedPreferencesUtils.setting, getActivity(),
//							SharedPreferencesUtils.setting_font, "big");
//				}
//			}
//		});
	}
	
	private void listenLayout(){
		view.findViewById(R.id.more_recommond).setOnClickListener(this);
		view.findViewById(R.id.more_update).setOnClickListener(this);
		
		view.findViewById(R.id.more_feedback).setOnClickListener(this);
		view.findViewById(R.id.more_help).setOnClickListener(this);
		
		view.findViewById(R.id.more_contact).setOnClickListener(this);;
		view.findViewById(R.id.more_Cache).setOnClickListener(this);;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.more_recommond:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");  
			intent.putExtra(Intent.EXTRA_TEXT, "大麦段子");   
			getActivity().startActivity(Intent.createChooser(intent, "精彩推荐"));
			Toast.makeText(getActivity(), "more_recommond", Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_feedback:
			More_feedback feedback = new More_feedback();
			switchFragment(this, feedback);
			Toast.makeText(getActivity(), "more_feedback", Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_help:
			More_Help help = new More_Help();
			switchFragment(this, help);
			Toast.makeText(getActivity(), "more_help", Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_Cache:
			MaimobApplication.imageLoader.clearDiskCache();
			Toast.makeText(getActivity(), "SDCard中的图片缓存已经清除", Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_update:
//			MaimobApplication.imageLoader.clearDiskCache();
			Toast.makeText(getActivity(), "more_update", Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_contact:
			More_Contact contact = new More_Contact();
			switchFragment(this,contact);
//			Toast.makeText(getActivity(), "more_contact", Toast.LENGTH_SHORT).show();
//			My_Favorite favorite = new My_Favorite();
//			switchFragment(this, favorite);
			break;
		default:
			break;
		}
	}
	
	public void switchFragment(Fragment from , Fragment to){
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MaiActivity) {
			MaiActivity xyf = (MaiActivity) getActivity();
			xyf.switchContentFull(from, to, null);
		}
	}
	
}
