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
import android.widget.ImageView;
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
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;


public class DuanZi_Comment_Write extends Fragment implements OnClickListener{
	private View view;
	private Button submit;
	private EditText editText;
	private String pid = "15";
	private String Tag = "DuanZi_Comment_Write";
	private boolean isCheck_sina= false;
	private boolean isCheck_tencent = false;
	private boolean isCheck_qzone = false;
	private boolean isCheck_douban = false;
	private String content,imgUri;
	private String editContent;
	private ImageView sina_img, tencent_img, qzone_img, douban;
	private UMSocialService mcontroller;
	
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
		mcontroller = MaimobApplication.mController;
		mcontroller.getConfig().setSsoHandler(new QZoneSsoHandler(getActivity(), "APP ID", "APP KEY"));
		initView();
		Duanzi duanzi = (Duanzi) getArguments().getSerializable("commit");
		pid = duanzi.getPoid();
		imgUri = duanzi.getImageUrl();
		content= duanzi.getContent();
	}
	private void initView(){
		sina_img = (ImageView)view.findViewById(R.id.duanzi_comment_write_sina);
		tencent_img = (ImageView)view.findViewById(R.id.duanzi_comment_write_tencent);
		qzone_img = (ImageView)view.findViewById(R.id.duanzi_comment_write_qzone);
		douban = (ImageView)view.findViewById(R.id.duanzi_comment_write_douban);
		
		sina_img.setOnClickListener(this);
		tencent_img.setOnClickListener(this);
		qzone_img.setOnClickListener(this);
		douban.setOnClickListener(this);
//		sina_img.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				if (MaimobApplication.Jelly_Bean) {
//					sina_img.setBackground(getResources().getDrawable(R.drawable.sina_layer_check));
//				}else {
//					sina_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.sina_layer_check));
//				}
//				if (!isCheck_sina) {
//					if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//						sina_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.sina_layer_check));
//					}else {
//						sina_img.setBackground(getResources().getDrawable(R.drawable.sina_layer_check));
//					}
//					
//					isCheck_sina = true;
//				}else {
//					if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//						sina_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.sina_layer));
//					}else {
//						sina_img.setBackground(getResources().getDrawable(R.drawable.sina_layer));
//					}
//					
//					
//					isCheck_sina = false;
//				}
//			}
//		});
		
		
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
			new Thread(new SubMitThread()).start();
			Log.e(Tag, "sina  " + isCheck_sina +  "  TENCENT  " +isCheck_tencent);
			if (isCheck_sina) {
				ShareToSocial(SHARE_MEDIA.SINA);
			}else if (isCheck_tencent) {
				ShareToSocial(SHARE_MEDIA.TENCENT);
			}else if (isCheck_qzone) {
				Log.e(Tag, "QZONE");
				ShareToSocial(SHARE_MEDIA.RENREN);
			}else if (isCheck_douban) {
				ShareToSocial(SHARE_MEDIA.DOUBAN);
			}
			break;
		case R.id.duanzi_comment_write_sina:
			if (!isCheck_sina) {
				if (MaimobApplication.Jelly_Bean) {
					sina_img.setBackground(getResources().getDrawable(R.drawable.weibo2_72x72));
				}else {
					sina_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.weibo2_72x72));
				}
				isCheck_sina = true;
			}else {
				if (MaimobApplication.Jelly_Bean) {
					sina_img.setBackground(getResources().getDrawable(R.drawable.weibo_72x72));
				}else {
					sina_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.weibo_72x72));
				}
				isCheck_sina = false;
			}
			break;
		case R.id.duanzi_comment_write_qzone:
			if (!isCheck_qzone) {
				if (MaimobApplication.Jelly_Bean) {
					qzone_img.setBackground(getResources().getDrawable(R.drawable.renren2_72x72));
				}else {
					qzone_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.renren2_72x72));
				}
				isCheck_qzone = true;
			}else {
				if (MaimobApplication.Jelly_Bean) {
					qzone_img.setBackground(getResources().getDrawable(R.drawable.renren_72x72));
				}else {
					qzone_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.renren_72x72));
				}
				isCheck_qzone =false;
			}
			break;
		case R.id.duanzi_comment_write_tencent:
			if (!isCheck_tencent) {
				if (MaimobApplication.Jelly_Bean) {
					tencent_img.setBackground(getResources().getDrawable(R.drawable.tencent2_72x72));
				}else {
					tencent_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.tencent2_72x72));
				}
				isCheck_tencent = true;
			}else {
				if (MaimobApplication.Jelly_Bean) {
					tencent_img.setBackground(getResources().getDrawable(R.drawable.tencen_72x72));
				}else {
					tencent_img.setBackgroundDrawable(getResources().getDrawable(R.drawable.tencen_72x72));
				}
				isCheck_tencent =false;
			}
			break;
			
		case R.id.duanzi_comment_write_douban:
			if (!isCheck_douban) {
				if (MaimobApplication.Jelly_Bean) {
					douban.setBackground(getResources().getDrawable(R.drawable.douban2_72x72));
				}else {
					douban.setBackgroundDrawable(getResources().getDrawable(R.drawable.douban2_72x72));
				}
				isCheck_douban = true;
			}else {
				if (MaimobApplication.Jelly_Bean) {
					douban.setBackground(getResources().getDrawable(R.drawable.douban_72x72));
				}else {
					douban.setBackgroundDrawable(getResources().getDrawable(R.drawable.douban_72x72));
				}
				isCheck_douban =false;
			}
			break;
		}
	}
	
	public void ShareToSocial(SHARE_MEDIA media){
			Log.e(Tag, "content  " + editContent + "  " + content);
			MaimobApplication.mController.setShareContent(editContent + "~~~~~" + content + "~~~~来自大麦段子");
			MaimobApplication.mController.directShare(getActivity(), media, new SnsPostListener() {
				
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
