package com.example.fragment.content;

import com.example.tab.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class My_userInfo_Vp_Publish extends Fragment{
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo_vp_2, container, false);
		return view;
	}
}
