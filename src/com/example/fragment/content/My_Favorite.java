package com.example.fragment.content;

import java.util.List;

import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class My_Favorite extends Fragment implements OnClickListener{
	private View view;
	private XAdapter xAdapter;
	
	Handler mhandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_fav, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(getResources().getString(R.string.my_fav_title));
		Button bt_back = (Button)view.findViewById(R.id.top_left);
		Button bt_right = (Button)view.findViewById(R.id.top_right);
		bt_right.setVisibility(View.GONE);
		bt_back.setOnClickListener(this);
		Mai_DBhelper dBhelper = Mai_DBhelper.getInstance(getActivity());
		List<Duanzi> list_duanzi = dBhelper.selectFav();
		if (list_duanzi.size() != 0) {
			Log.i("FFF", "size "+ list_duanzi.size());
		}
		xAdapter = new XAdapter(list_duanzi, mhandler, MaimobApplication.mController, this, getActivity());
		ListView listView = (ListView)view.findViewById(R.id.my_fav_list);
		listView.setAdapter(xAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_left:
			mFragmentManage.BackStatck(getActivity());
			break;

		default:
			break;
		}
	}
}
