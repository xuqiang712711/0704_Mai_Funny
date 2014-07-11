package com.example.fragment.content;


import com.example.tab.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class More_Contact extends Fragment{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.more_contact, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		initView();
	}
	
	private void initView(){
		TextView title = (TextView)view.findViewById(R.id.back2_text);
		title.setText(R.string.more_text_contact);
//		view.findViewById(R.id.back2_back).setOnClickListener(this);
	}
	
}
