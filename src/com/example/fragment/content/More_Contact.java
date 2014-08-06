package com.example.fragment.content;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Activity.MaiActivity;
import com.example.fragment.Tab_More_Frag;
import com.example.object.mFragmentManage;
import com.example.tab.R;

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
		TextView title = (TextView)view.findViewById(R.id.top_text);
		title.setText(R.string.more_text_contact);
		Button right = (Button)view.findViewById(R.id.top_right);
		right.setVisibility(View.GONE);
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManage.BackStatck(getActivity());
			}
		});
	}
	
}
