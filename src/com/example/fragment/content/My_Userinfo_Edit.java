package com.example.fragment.content;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.MaimobApplication;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.CommonUtils;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class My_Userinfo_Edit extends Fragment implements OnClickListener{
	private View view;
	private RelativeLayout layout_icon, layout_name, layout_Signature;
	private ImageView iv_Icon;
	private TextView tv_Name, tv_Signature;
	private boolean needRefresh = false;
	private AlertDialog.Builder builder_Pic;
	private ArrayAdapter<String> adapter_Pic;
	private final String[] items = new String[]{"����" , "�����ѡ��"};
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private String currImg = null;
	private AlertDialog dialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.my_userinfo_edit, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(getResources().getString(R.string.my_edit));
		Button back = (Button)view.findViewById(R.id.top_left);
		back.setOnClickListener(this);
		Button right = (Button)view.findViewById(R.id.top_right);
		right.setVisibility(View.GONE);
		
		layout_icon  = (RelativeLayout)view.findViewById(R.id.my_edit_icon);
		layout_name = (RelativeLayout)view.findViewById(R.id.my_edit_name);
		layout_Signature = (RelativeLayout)view.findViewById(R.id.my_edit_Signature);
		layout_icon.setOnClickListener(this);
		layout_name.setOnClickListener(this);
		layout_Signature.setOnClickListener(this);
		
		iv_Icon = (ImageView)view.findViewById(R.id.my_edit_icon_image);
		
		tv_Name = (TextView)view.findViewById(R.id.my_edit_name_right);
		tv_Signature = (TextView)view.findViewById(R.id.my_edit_Signature_right);
		
		tv_Name.setText((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, getActivity(),
				SharedPreferencesUtils.user_name, ""));
		tv_Signature.setText((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, getActivity(),
				SharedPreferencesUtils.user_description, ""));
		MaimobApplication.imageLoader.displayImage((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, getActivity(),
				SharedPreferencesUtils.user_icon, ""), iv_Icon, ImageUtil.getOption());
		
		adapter_Pic = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
		builder_Pic = new AlertDialog.Builder(getActivity());
		builder_Pic.setAdapter(adapter_Pic, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DialogChose(which);
			}
		});
		dialog = builder_Pic.create();
		
		iv_Icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});
		
		Button button = (Button)view.findViewById(R.id.weixin_bt);
		String appId ="wxd88b7b000ddc25df";
		UMWXHandler umwxHandler = new UMWXHandler(getActivity(), appId);
		umwxHandler.addToSocialSDK();
		WeiXinShareContent shareContent = new WeiXinShareContent("���Ƿ�");
		MaimobApplication.mController.setShareMedia(shareContent);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MaimobApplication.mController.directShare(getActivity(), SHARE_MEDIA.WEIXIN, new SnsPostListener() {
					
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						MyLogger.jLog().i("start share");
					}
					
					@Override
					public void onComplete(SHARE_MEDIA arg0, int code, SocializeEntity arg2) {
						// TODO Auto-generated method stub
						MyLogger.jLog().i("code " + String.valueOf(code));
					}
				});
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	
	private void DialogChose(int which){
		if (which == 0) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File file = new File(Environment.getExternalStorageDirectory() + "/Mai",
					"Mai_"
					+String.valueOf(System.currentTimeMillis()) + ".jpg");
			mImageCaptureUri = Uri.fromFile(file);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
					mImageCaptureUri);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, PICK_FROM_CAMERA);
		}else if (which == 1) {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			MyLogger.jLog().i("result  " + resultCode + "  request  " + requestCode);
			return;
		}
		if (requestCode == PICK_FROM_FILE) {
			Uri uri = data.getData();
			String imgPath = CommonUtils.getAbsolutePathFromNoStandardUri(uri);
			if (StringUtils.isBlank(imgPath)) {
				currImg = CommonUtils.getAbsoluteImagePath(getActivity(), uri);
			}
		}else if (requestCode == PICK_FROM_CAMERA) {
			currImg = mImageCaptureUri.getPath();
		}
		MyLogger.jLog().i(currImg);
		String filePath = StringUtils.checkImgPath(currImg);
		MyLogger.jLog().i("imgPath  " + filePath);
		MaimobApplication.imageLoader.displayImage(filePath, iv_Icon, ImageUtil.getOption());
		SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, getActivity(), SharedPreferencesUtils.user_icon, filePath);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final EditText et_input  = new EditText(getActivity());
		switch (v.getId()) {
		case R.id.top_left:
			mFragmentManage.BackStatck(getActivity());
			//TODO REFRESH
			break;

		case R.id.my_edit_icon:
			break;
		case R.id.my_edit_name:
			et_input.setHint((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, getActivity(),
					SharedPreferencesUtils.user_name, ""));
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("�༭�ǳ�").setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
					SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, getActivity(), SharedPreferencesUtils.user_name, content);
					tv_Name.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		case R.id.my_edit_Signature:
			et_input.setHint((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, getActivity(),
					SharedPreferencesUtils.user_description, ""));
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("�༭ǩ��").setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
					SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, getActivity(), SharedPreferencesUtils.user_description, content);
					tv_Signature.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (needRefresh) {
			mFragmentManage.RefreshFrag(getActivity(), mFragmentManage.Tag_Userinfo);
		}
	}
	
}
