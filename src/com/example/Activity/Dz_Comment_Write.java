package com.example.Activity;

import com.example.object.Duanzi;
import com.example.tab.R;
import com.example.util.MyLogger;

import android.app.Activity;
import android.os.Bundle;

public class Dz_Comment_Write extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.duanzi_comments_write);
		Bundle bundle = this.getIntent().getExtras();
		Duanzi duanzi = (Duanzi) bundle.getSerializable("duanzi");
		String content = duanzi.getContent();
		MyLogger.jLog().i("xwkkx "  + content);
	}
	
	private void initview(){
		
	}
}
