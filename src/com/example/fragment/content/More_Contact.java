package com.example.fragment.content;

import org.w3c.dom.Text;

import com.example.fragment.Tab_More_Frag;
import com.example.tab.R;
import com.example.tab.XYFTEST;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class More_Contact extends Fragment implements OnClickListener{
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
		initView();
	}
	
	private void initView(){
		TextView title = (TextView)view.findViewById(R.id.back2_text);
		title.setText(R.string.more_text_contact);
//		view.findViewById(R.id.back2_back).setOnClickListener(this);
	}
	
	public void switchFragment(Fragment from , Fragment to){
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof XYFTEST) {
			XYFTEST xyf = (XYFTEST) getActivity();
			xyf.switchContentFull(from, to);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}
	}
}
