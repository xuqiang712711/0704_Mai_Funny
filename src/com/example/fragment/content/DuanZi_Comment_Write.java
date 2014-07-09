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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.MaimobApplication;
import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.tab.R;
import com.example.tab.XYFTEST;
import com.example.util.Uris;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;


public class DuanZi_Comment_Write extends Fragment implements OnClickListener, OnCheckedChangeListener{
	private View view;
	private Button submit;
	private EditText editText;
	private String pid = "15";
	private String Tag = "DuanZi_Comment_Write";
	private CheckBox sina,tencent;
	private boolean isCheck_sina= false;
	private boolean isCheck_tencent = false;
	private String content,imgUri;
	private String editContent;
	
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
		Duanzi duanzi = (Duanzi) getArguments().getSerializable("commit");
		pid = duanzi.getPoid();
		imgUri = duanzi.getImageUrl();
		content= duanzi.getContent();
	}
	private void initView(){
		sina = (CheckBox)view.findViewById(R.id.duanzi_comment_sian);
		sina.setOnCheckedChangeListener(this);
		tencent = (CheckBox)view.findViewById(R.id.duanzi_comment_tencent);
		
		TextView title = (TextView)view.findViewById(R.id.back_text);
		title.setText(getResources().getString(R.string.duanzi_comment_title));
		
		submit = (Button)view.findViewById(R.id.back_submit);
		editText = (EditText)view.findViewById(R.id.duanzi_comments_edit);
		submit.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_submit:
			editContent = editText.getText().toString();
//			new Thread(new SubMitThread()).start();
			Log.e(Tag, "sina  " + isCheck_sina);
			ShareToSocial();
			break;
		}
	}
	
	public void ShareToSocial(){
		if (isCheck_sina) {
			Log.e(Tag, "content  " + editContent + "  " + content);
			MaimobApplication.mController.setShareContent(editContent + "~~~~~" + content + "~~~~来自大麦段子");
			if (imgUri != null || !imgUri.equals("")) {
				MaimobApplication.mController.setShareMedia(new UMImage(getActivity(), imgUri));
			}
			MaimobApplication.mController.directShare(getActivity(), SHARE_MEDIA.SINA, new SnsPostListener() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					 Toast.makeText(getActivity(), "分享开始",Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onComplete(SHARE_MEDIA arg0, int eCode, SocializeEntity arg2) {
					// TODO Auto-generated method stub
					 if(eCode == StatusCode.ST_CODE_SUCCESSED){
		                    Toast.makeText(getActivity(), "分享成功",Toast.LENGTH_SHORT).show();
		                }else{
		                    Toast.makeText(getActivity(), "分享失败",Toast.LENGTH_SHORT).show();
		                }
				}
			});
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.duanzi_comment_sian:
			if (isChecked) {
				isCheck_sina = true;
			}else {
				isCheck_sina = false;
			}
			Log.e(Tag, "红眼睛  " + isCheck_sina);
			break;

		default:
			break;
		}
	}
	
}
