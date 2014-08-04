package com.example.fragment.content;

import java.util.List;

import org.w3c.dom.Text;

import com.example.adapter.XAdapter;
import com.example.application.MaimobApplication;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.MyLogger;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class My_userInfo_Vp_Publish extends Fragment implements OnItemClickListener{
	View view;
	private ListView lv;
	private MyLogger logger = MyLogger.jLog();
	private List<Duanzi> list_duanzi;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo_vp_2, container, false);
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		logger.i("@!~@@@@@@");
//		layout_title.setVisibility(View.GONE);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		logger.i("onResume  ___________");
		TextView tv_note = (TextView) view.findViewById(R.id.my_user_vp_empty);
		lv = (ListView)view.findViewById(R.id.my_userinfo_vp2_list);
		lv.setOnItemClickListener(this);
		Mai_DBhelper dbhelper = Mai_DBhelper.getInstance(getActivity());
		list_duanzi = dbhelper.selectPub();
		XAdapter xAdapter = new XAdapter(list_duanzi, mHandler, MaimobApplication.mController, this, getActivity());
		MyLogger.jLog().i("comment size  " + list_duanzi.size());
		if (list_duanzi.size() != 0) {
			lv.setAdapter(xAdapter);
		}else {
			tv_note.setVisibility(View.VISIBLE);
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		}
	};
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Duanzi duanzi = list_duanzi.get(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable("duanzi", duanzi);
		mFragmentManage.SwitchFrag(getActivity(), getParentFragment(), new DuanZi_Comment(), bundle);
		MyLogger.jLog().i("xwKKX  " + duanzi.getContent());
	}
}
