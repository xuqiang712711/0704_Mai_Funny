package com.example.fragment.content;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maiUtil.CustomHttpClient;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;


public class DuanZi_Comment_Write extends Fragment{
	private View view;
	private Button submit;
	private EditText editText;
	private String pid = "15";
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.duanzi_comments_test, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		String json = getArguments().getString("comment");
		try {
			JSONObject jsonObject = new JSONObject(json);
			pid = jsonObject.getString("poid");
			Log.i("XXX", "poid  " + pid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void initView(){
		submit = (Button)view.findViewById(R.id.duanzi_comments_submit);
		editText = (EditText)view.findViewById(R.id.duanzi_comments_edit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(new SubMitThread()).start();
			}
		});
	}
	
	class SubMitThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int code = PostWithPic(Uris.Post_Comment, Uris.uuid , pid);
				if (code == 200) {
					Message message = Message.obtain();
					handler.sendMessage(message);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public int  PostWithPic(String uri,  String uuid, String pid) throws UnsupportedEncodingException{
		int code = 0;
		HttpClient client = CustomHttpClient.getHttpClient();
		String content = editText.getText().toString();
		HttpPost httpPost = new HttpPost(uri);
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("uuid", new StringBody(uuid));
		entity.addPart("content", new StringBody(content));
		entity.addPart("pid", new StringBody(pid));
		httpPost.setEntity(entity);
		try {
				Log.i("FFF", "投稿___uri" + httpPost.getURI());
			HttpResponse response = client.execute(httpPost);
			code = response.getStatusLine().getStatusCode();
				Log.i("FFF", "投稿___code"+ code);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				String jsonObject = EntityUtils.toString(httpEntity);
				Log.i("FFF", "投稿——result"+jsonObject);
			}
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("FFF", "投稿___IO" + e);
		} 
//			catch (JSONException e) {
//			// TODO Auto-generated catch block
//			Log.i("FFF", "投稿___Json" + e);
//			e.printStackTrace();
//		}
		return code;
}
	
}
