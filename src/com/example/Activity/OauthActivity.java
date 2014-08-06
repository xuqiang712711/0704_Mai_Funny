package com.example.Activity;

import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.tab.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class OauthActivity extends Activity{
	String Tag  = "OauthActivity";
	int content = 0;
	private Duanzi duanzi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		content = intent.getIntExtra("Activity", 0);
		Log.e(Tag, String.valueOf(content));
		switch (content) {
		case 3:
			duanzi = (Duanzi) intent.getSerializableExtra("duanzi");
			break;

		default:
			break;
		}
		setContentView(R.layout.my_login_select);
		ImageView back = (ImageView)findViewById(R.id.top_left_change);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("result", content);
				if (duanzi != null) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("duanzi", duanzi);
					intent.putExtras(bundle);
				}
				OauthActivity.this.setResult(content, intent);
				OauthActivity.this.finish();
			}
		});
	}
}
