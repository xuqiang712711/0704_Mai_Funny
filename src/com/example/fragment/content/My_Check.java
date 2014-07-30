package com.example.fragment.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tab.R;
import com.example.util.CustomImage;

public class My_Check extends Fragment{
	private View view;
	private CustomImage trash,sex,neihan,funny,skip;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_check, container, false);
		initView();
		return view;
	}
	
	private void initView(){
		trash = (CustomImage)view.findViewById(R.id.my_check_trash);
		sex = (CustomImage)view.findViewById(R.id.my_check_sex);
		neihan = (CustomImage)view.findViewById(R.id.my_check_neihan);
		funny = (CustomImage)view.findViewById(R.id.my_check_boring);
		skip = (CustomImage)view.findViewById(R.id.my_check_skip);
		
		setContent(trash, R.drawable.ic_trash, R.string.my_trash);
		setContent(sex, R.drawable.ic_sex, R.string.my_sexy);
		setContent(neihan, R.drawable.ic_connotation, R.string.my_neihan);
		setContent(funny, R.drawable.ic_funny, R.string.my_boring);
		setContent(skip, R.drawable.ic_skip, R.string.my_skip);
	}
	
	private void setContent(CustomImage ci ,int resImg ,int resText){
		ci.setLayoutOrientation(LinearLayout.VERTICAL);
		ci.setImageResource(resImg);
		ci.setTextView_int(resText);
	}
}
