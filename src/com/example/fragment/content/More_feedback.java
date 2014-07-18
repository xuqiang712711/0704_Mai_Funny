package com.example.fragment.content;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.object.mFragmentManage;
import com.example.tab.R;

public class More_feedback extends Fragment{
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.more_feedback, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TextView title = (TextView)view.findViewById(R.id.top_text);
		title.setText(R.string.more_text_feedback);
		Button right = (Button)view.findViewById(R.id.top_right);
		right.setVisibility(View.GONE);
		Button back = (Button)view.findViewById(R.id.top_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManage.BackStatck(getActivity());
			}
		});
	}
}
