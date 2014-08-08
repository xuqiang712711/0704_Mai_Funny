package com.example.fragment.content;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.object.mFragmentManage;
import com.example.tab.R;

public class More_feedback extends Fragment{
	View view;
	private EditText et_feedback;
	private InputMethodManager imm;
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
		TextView bt_submit = (TextView)view.findViewById(R.id.top_right_change2);
//		bt_submit.setVisibility(View.GONE);
		bt_submit.setText(R.string.ActionBar_Submit);
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFragmentManage.BackStatck(getActivity());
				imm.hideSoftInputFromWindow(et_feedback.getWindowToken(), 0);
			}
		});
		et_feedback = (EditText)view.findViewById(R.id.feedback_edit);
		et_feedback.requestFocus();
		et_feedback.setFocusable(true);
		et_feedback.setFocusableInTouchMode(true);
		imm = (InputMethodManager) getActivity()
				  .getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//		imm.showSoftInput(et_feedback, 0);
	}
}
