package com.example.Activity;

import com.example.tab.R;
import com.example.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LoadingActivity extends Activity{
	private String stringjson;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.obj.equals("ok")) {
				Intent intent = new Intent(LoadingActivity.this, MaiActivity.class);
				intent.putExtra("json", stringjson);
				startActivity(intent);
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
	}
	
	private String getData() throws Exception{
		return HttpUtil.getRequest(HttpUtil.postUri);
	}
	
	class mThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				stringjson = getData();
				Message msg = Message.obtain();
				msg.obj = "ok";
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
