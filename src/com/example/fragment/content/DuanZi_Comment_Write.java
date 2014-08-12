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
import org.w3c.dom.Text;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.Activity.MaiActivity;
import com.example.application.MaimobApplication;
import com.example.maiUtil.CustomHttpClient;
import com.example.object.Duanzi;
import com.example.object.mFragmentManage;
import com.example.sql.Mai_DBhelper;
import com.example.tab.R;
import com.example.util.MyLogger;
import com.example.util.PopUtils;
import com.example.util.EditUtil;
import com.example.util.NetworkUtil;
import com.example.util.SerUser;
import com.example.util.ShareUtil;
import com.example.util.SharedPreferencesUtils;
import com.example.util.Uris;
import com.example.util.User;
import com.facebook.widget.FacebookDialog.ShareDialogFeature;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.facebook.controller.utils.ToastUtil;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.utils.OauthHelper;


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
	private String duanziContent,imgUri;
	private String editContent;
	private ImageView sina_img, tencent_img, qzone_img, douban;
	private UMSocialService mcontroller;
	private Dialog dialog;
	private Duanzi duanzi;
	private TextView tv_edit_count;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			dialog.dismiss();
			mFragmentManage.Refresh_Comment = true;
			mFragmentManage.BackStatck(getActivity());
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
		duanzi = (Duanzi) getArguments().getSerializable("duanzi");
		if (duanzi != null) {
			Log.e(Tag, "~~~~~~~~~~~~~~DuanZi_Comment_Write~~~~~~~~bundle");
			pid = duanzi.getPoid();
			imgUri = duanzi.getImageUrl();
			duanziContent= duanzi.getContent();
		}
	}
	private void initView(){
		TextView title = (TextView)view.findViewById(R.id.top_text);
		title.setText(getResources().getString(R.string.my_comment));
		ImageView back = (ImageView)view.findViewById(R.id.top_left_change);
		back.setOnClickListener(this);
		TextView submit = (TextView)view.findViewById(R.id.top_right_change2);
		submit.setText(getResources().getString(R.string.ActionBar_Submit));
		submit.setOnClickListener(this);
		
		sina_img = (ImageView)view.findViewById(R.id.duanzi_comment_write_sina);
		tencent_img = (ImageView)view.findViewById(R.id.duanzi_comment_write_tencent);
		qzone_img = (ImageView)view.findViewById(R.id.duanzi_comment_write_qzone);
		douban = (ImageView)view.findViewById(R.id.duanzi_comment_write_douban);
		
		if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.SINA)) {
			sina_img.setImageResource(R.drawable.close_24);
		}else if (!OauthHelper.isAuthenticatedAndTokenNotExpired(getActivity(), SHARE_MEDIA.RENREN)) {
			MyLogger.jLog().i("tencent");
			tencent_img.setImageResource(R.drawable.close_24);
		}else if (!OauthHelper.isAuthenticated(getActivity(), SHARE_MEDIA.RENREN)) {
			qzone_img.setImageResource(R.drawable.close_24);
			MyLogger.jLog().i("renren");
		}else if (!OauthHelper.isAuthenticatedAndTokenNotExpired(getActivity(), SHARE_MEDIA.DOUBAN)) {
			douban.setImageResource(R.drawable.close_24);
			MyLogger.jLog().i("douban");
		}
		sina_img.setOnClickListener(this);
		tencent_img.setOnClickListener(this);
		qzone_img.setOnClickListener(this);
		douban.setOnClickListener(this);
		
		editText = (EditText)view.findViewById(R.id.duanzi_comments_edit);
		tv_edit_count = (TextView)view.findViewById(R.id.duanzi_comments_edit_count);
		dialog = PopUtils.createLoadingDialog(getActivity());
		new EditUtil(getActivity(), editText, tv_edit_count);
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
					ToastUtil.showToast(getActivity(), "评论失败，请稍后再试");
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
			if (NetworkUtil.getNetworkState(getActivity()) == NetworkUtil.NONE) {
				PopUtils.toastShow(getActivity(), "网络错误,无法发送");
				break;
			}
			editContent = editText.getText().toString();
			if (editContent != null && !editContent.equals("")) {
				dialog.show();
				if (duanzi.isNeedComment()) {
					Log.e("Duanzi_comment_writer", "需要发送评论到服务器");
					new Thread(new SubMitThread()).start();
				}
				Log.e(Tag, "sina  " + isCheck_sina +  "  TENCENT  " +isCheck_tencent);
				if (isCheck_sina) {
					ShareUtil.ShareToSocial(SHARE_MEDIA.SINA, editContent, duanziContent, null,getActivity(), handler);
				}else if (isCheck_tencent) {
					ShareUtil.ShareToSocial(SHARE_MEDIA.TENCENT, editContent, duanziContent,null, getActivity(), handler);
				}else if (isCheck_qzone) {
					Log.e(Tag, "QZONE");
					ShareUtil.ShareToSocial(SHARE_MEDIA.RENREN, editContent, duanziContent, null,getActivity(), handler);
				}else if (isCheck_douban) {
					ShareUtil.ShareToSocial(SHARE_MEDIA.DOUBAN, editContent, duanziContent, null, getActivity(), handler);
				}
				insertComment(imgUri,duanzi.getUserName(),duanziContent , Integer.parseInt(pid), editContent);
			}else {
				ToastUtil.showToast(getActivity(), "评论内容不能为空");
			}
			break;
		case R.id.top_left_change:
			
			editContent = editText.getText().toString();
			if (editContent != null && !editContent.equals("")) {
				new AlertDialog.Builder(getActivity()).setMessage(getResources().getString(R.string.write_editNotNull))
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
						mFragmentManage.BackStatck(getActivity());
					}
				})
				.show();
			}else {
				mFragmentManage.BackStatck(getActivity());
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
	
	public void insertComment(String imgUrl,String dz_userName, String duanziContent, int pid, String editContent) {
		User user = SerUser.deSerializationUser((String)SharedPreferencesUtils
				.getParam(SharedPreferencesUtils.SerUser, getActivity(),
						SharedPreferencesUtils.SerUser_user, ""));
		Mai_DBhelper db = Mai_DBhelper.getInstance(getActivity());
		db.insertUser_Comment(dz_userName, duanziContent, pid, editContent, user,imgUrl);
	}
	
}
