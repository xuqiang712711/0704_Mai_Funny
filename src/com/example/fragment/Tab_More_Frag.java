package com.example.fragment;

import java.io.File;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.XAdapter;
import com.example.adapter.X_Text_Adapter;
import com.example.application.MaimobApplication;
import com.example.fragment.content.More_Contact;
import com.example.fragment.content.More_Help;
import com.example.fragment.content.More_feedback;
import com.example.fragment.content.My_Favorite;
import com.example.fragment.content.My_Write;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.SharedPreferencesUtils;
import com.example.util.Uris;

public class Tab_More_Frag extends Fragment implements OnClickListener{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab_more, container, false);
		initTextView();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listenCheckbox();
		listenRadiobutton();
		listenLayout();
	}
	
	
	private void initTextView(){
		
		
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
		
		view.findViewById(R.id.more_recommond).setBackgroundResource(R.drawable.item_click_top);
		view.findViewById(R.id.more_update).setBackgroundResource(R.drawable.item_click_top);
		
		view.findViewById(R.id.more_feedback).setBackgroundResource(R.drawable.item_click_bottom);
		view.findViewById(R.id.more_help).setBackgroundResource(R.drawable.item_click_bottom);
		
		view.findViewById(R.id.more_Cache).setBackgroundResource(R.drawable.item_click_center);
		view.findViewById(R.id.more_contact).setBackgroundResource(R.drawable.item_click_center);
	}
	
	private void setText(int id, int resID){
		((TextView)view.findViewById(id).findViewById(R.id.more_text1)).setText(resID);
	}
	
	private void listenCheckbox(){
		setCheckBox(R.id.more_zhuanfa, R.id.more_checkbox);
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
					Toast.makeText(getActivity(), "ѡ��", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtils.setParam("setting", getActivity(), "isZhuanfa", true);
				}else {
					Toast.makeText(getActivity(), "δѡ��", Toast.LENGTH_SHORT).show();
					SharedPreferencesUtils.setParam("setting", getActivity(), "isZhuanfa", false);
				}
			}
		});
	}
	
	private void listenRadiobutton(){
		((RadioGroup)view.findViewById(R.id.more_fontSize).findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio0) {
					Toast.makeText(getActivity(), "С", Toast.LENGTH_SHORT).show();
//					X_Text_Adapter.fontSize = 14;
					Uris.Font_Size = Uris.Font_Size_small;
					XAdapter.SetNormal();
//					X_Text_Adapter.SetNormal();
				}else if (checkedId == R.id.radio1) {
					Toast.makeText(getActivity(), "��", Toast.LENGTH_SHORT).show();
					Uris.Font_Size = Uris.Font_Size_normal;
					XAdapter.SetNormal();
//					X_Text_Adapter.SetNormal();
				}else if (checkedId == R.id.radio2) {
					Toast.makeText(getActivity(), "��", Toast.LENGTH_SHORT).show();
					Uris.Font_Size = Uris.Font_Size_big;
					XAdapter.SetNormal();
//					X_Text_Adapter.SetNormal();
				}
			}
		});
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
			intent.putExtra(Intent.EXTRA_TEXT, "�������");   
			getActivity().startActivity(Intent.createChooser(intent, "�����Ƽ�"));
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
			Toast.makeText(getActivity(), "SDCard�е�ͼƬ�����Ѿ����", Toast.LENGTH_SHORT).show();
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
		if (getActivity() instanceof XYFTEST) {
			XYFTEST xyf = (XYFTEST) getActivity();
			xyf.switchContentFull(from, to, null);
		}
	}
	
}
