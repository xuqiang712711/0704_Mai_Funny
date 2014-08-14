package com.example.Activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.object.User;
import com.example.object.mFragmentManage;
import com.example.object.mOauth;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.EditUtil;
import com.example.util.MyLogger;
import com.example.util.NetworkUtil;
import com.example.util.PopUtils;
import com.example.util.SerUser;
import com.example.util.ShareUtil;
import com.example.util.SharedPreferencesUtils;
import com.example.util.Uris;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;
import com.umeng.socialize.utils.OauthHelper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Dz_Comment_Write extends BaseActivity implements OnClickListener{
	private EditText editText;
	private String pid = "15";
	private String Tag = "DuanZi_Comment_Write";
	private boolean isCheck_sina= false;
	private boolean isCheck_tencent = false;
	private boolean isCheck_qzone = false;
	private boolean isCheck_douban = false;
	private String duanziContent,imgUri;
	private String editContent;
	private ImageView sina_img, tencent_img, qzone_img, douban;
	private Dialog dialog;
	private Duanzi duanzi;
	private TextView tv_edit_count;
	private int result_Code = 333;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.duanzi_comments_write);
		Bundle bundle = this.getIntent().getExtras();
		duanzi = (Duanzi) bundle.getSerializable("duanzi");
		if (duanzi != null) {
			Log.e(Tag, "~~~~~~~~~~~~~~DuanZi_Comment_Write~~~~~~~~bundle");
			pid = duanzi.getPoid();
			imgUri = duanzi.getImageUrl();
			duanziContent= duanzi.getContent();
		}
		String content = duanzi.getContent();
		String name = duanzi.getUserName();
		MyLogger.jLog().i("xwkkx "  + content +"  name  " +name);
		initView();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				platFormStatus();
			}else {
				dialog.dismiss();
//				mFragmentManage.Refresh_Comment = true;
//				mFragmentManage.BackStatck(this);
				setResult(result_Code);
				finish();
			}
		}
	};
	
	private void initView(){
		TextView title = (TextView)findViewById(R.id.top_text);
		title.setText(getResources().getString(R.string.my_comment));
		ImageView back = (ImageView)findViewById(R.id.top_left_change);
		back.setOnClickListener(this);
		TextView submit = (TextView)findViewById(R.id.top_right_change2);
		submit.setText(getResources().getString(R.string.ActionBar_Submit));
		submit.setOnClickListener(this);
		
		sina_img = (ImageView)findViewById(R.id.duanzi_comment_write_sina);
		tencent_img = (ImageView)findViewById(R.id.duanzi_comment_write_tencent);
		qzone_img = (ImageView)findViewById(R.id.duanzi_comment_write_qzone);
		douban = (ImageView)findViewById(R.id.duanzi_comment_write_douban);
		
		platFormStatus();
		
		sina_img.setOnClickListener(this);
		tencent_img.setOnClickListener(this);
		qzone_img.setOnClickListener(this);
		douban.setOnClickListener(this);
		
		editText = (EditText)findViewById(R.id.duanzi_comments_edit);
		tv_edit_count = (TextView)findViewById(R.id.duanzi_comments_edit_count);
		dialog = PopUtils.createLoadingDialog(this);
		new EditUtil(this, editText, tv_edit_count);
	}
	
	class SubMitThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int code = PostWithPic(Uris.Post_Comment, Uris.uuid , pid);
				Message message = Message.obtain();
				message.obj = Uris.MSG_SUC;
				handler.sendMessageDelayed(message, 5000);
				if (code != 200) {
					ToastUtil.showToast(Dz_Comment_Write.this, "评论失败，请稍后再试");
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
			HttpResponse response = client.execute(httpPost);
			code = response.getStatusLine().getStatusCode();
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
		return code;
}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_right_change2:
			if (NetworkUtil.getNetworkState(this) == NetworkUtil.NONE) {
				PopUtils.toastShow(this, "网络错误,无法发送");
				break;
			}
			editContent = editText.getText().toString();
			if (editContent != null && !editContent.equals("")) {
				dialog.show();
				new Thread(new SubMitThread()).start();
				Log.e(Tag, "sina  " + isCheck_sina +  "  TENCENT  " +isCheck_tencent);
				if (isCheck_sina) {
					ShareUtil.ShareToSocial(SHARE_MEDIA.SINA, editContent, duanziContent, null,this, handler);
				}else if (isCheck_tencent) {
					ShareUtil.ShareToSocial(SHARE_MEDIA.TENCENT, editContent, duanziContent,null, this, handler);
				}else if (isCheck_qzone) {
					Log.e(Tag, "QZONE");
					ShareUtil.ShareToSocial(SHARE_MEDIA.RENREN, editContent, duanziContent, null,this, handler);
				}else if (isCheck_douban) {
					ShareUtil.ShareToSocial(SHARE_MEDIA.DOUBAN, editContent, duanziContent, null, this, handler);
				}
				insertComment(imgUri,duanzi.getUserName(),duanziContent , Integer.parseInt(pid), editContent);
			}else {
				ToastUtil.showToast(this, "评论内容不能为空");
			}
			break;
		case R.id.top_left_change:
			
			editContent = editText.getText().toString();
			if (editContent != null && !editContent.equals("")) {
				new AlertDialog.Builder(this).setMessage(getResources().getString(R.string.write_editNotNull))
				.setNegativeButton(getResources().getString(R.string.write_cancel), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.setPositiveButton(getResources().getString(R.string.write_confrim), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
//						mFragmentManage.BackStatck(this);
						finish();
					}
				})
				.show();
			}else {
//				mFragmentManage.BackStatck(this);
				finish();
			}
			break;
		case R.id.duanzi_comment_write_sina:
			if (OauthHelper.isAuthenticated(this, SHARE_MEDIA.SINA)) {
				if (!isCheck_sina) {
					sina_img.setImageResource(R.drawable.weibo2_72x72);
					isCheck_sina = true;
				}else {
					sina_img.setImageResource(R.drawable.weibo_72x72);
					isCheck_sina = false;
				}
			}else {
				mOauth.doOauth(this, SHARE_MEDIA.SINA, 0, handler);
			}
			
			break;
		case R.id.duanzi_comment_write_qzone:
			if (OauthHelper.isAuthenticated(this, SHARE_MEDIA.RENREN)) {
				if (!isCheck_qzone) {
					qzone_img.setImageResource(R.drawable.renren2_72x72);
					isCheck_qzone = true;
				}else {
					qzone_img.setImageResource(R.drawable.renren_72x72);
					isCheck_qzone =false;
				}

			}else {
				mOauth.doOauth(this, SHARE_MEDIA.RENREN, 2, handler);
			}
				
			break;
		case R.id.duanzi_comment_write_tencent:
			if (OauthHelper.isAuthenticated(this, SHARE_MEDIA.TENCENT)) {
				if (!isCheck_tencent) {
					tencent_img.setImageResource(R.drawable.tencent2_72x72);
					isCheck_tencent = true;
				}else {
					tencent_img.setImageResource(R.drawable.tencen_72x72);
					isCheck_tencent =false;
				}
			}else {
				mOauth.doOauth(this, SHARE_MEDIA.TENCENT, 1, handler);
			}

			break;
			
		case R.id.duanzi_comment_write_douban:
			if (!isCheck_douban) {
				douban.setImageResource(R.drawable.douban2_72x72);
				isCheck_douban = true;
			}else {
				douban.setImageResource(R.drawable.douban_72x72);
				isCheck_douban =false;
			}
			break;
		}
	}
	
	public void insertComment(String imgUrl,String dz_userName, String duanziContent, int pid, String editContent) {
		User user = SerUser.deSerializationUser((String)SharedPreferencesUtils
				.getParam(SharedPreferencesUtils.SerUser, this,
						SharedPreferencesUtils.SerUser_user, ""));
		Xlog.i("user " + user.getName());
		Mai_DBhelper db = Mai_DBhelper.getInstance(this);
		db.insertUser_Comment(dz_userName, duanziContent, pid, editContent, user,imgUrl);
	}
	
	/**
	 * 平台状态
	 */
	private void platFormStatus(){
		if (!OauthHelper.isAuthenticated(this, SHARE_MEDIA.RENREN)) {
			qzone_img.setImageResource(R.drawable.close_24);
		}else {
			qzone_img.setImageResource(R.drawable.renren_72x72);
		}
		if (!OauthHelper.isAuthenticated(this, SHARE_MEDIA.DOUBAN)) {
			douban.setImageResource(R.drawable.close_24);
		}else {
			douban.setImageResource(R.drawable.douban_72x72);
		}
		if (!OauthHelper.isAuthenticated(this, SHARE_MEDIA.TENCENT)) {
			tencent_img.setImageResource(R.drawable.close_24);
		}else {
			tencent_img.setImageResource(R.drawable.tencen_72x72);
		}
		if (!OauthHelper.isAuthenticated(this, SHARE_MEDIA.SINA)) {
			sina_img.setImageResource(R.drawable.close_24);
		}else {
			sina_img.setImageResource(R.drawable.weibo_72x72);
		}
	}
	
}
