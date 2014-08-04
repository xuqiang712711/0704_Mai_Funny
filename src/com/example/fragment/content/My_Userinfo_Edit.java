package com.example.fragment.content;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.application.MaimobApplication;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.ImageUtil;
import com.example.util.MyLogger;
import com.example.util.SerUser;
import com.example.util.SharedPreferencesUtils;
import com.example.util.StringUtils;
import com.example.util.User;

public class My_Userinfo_Edit extends Fragment implements OnClickListener{
	private View view;
	private RelativeLayout layout_icon, layout_name, layout_Signature, layout_sex, layout_address;
	private ImageView iv_Icon;
	private TextView tv_Name, tv_Signature, tv_sex, tv_address;
	private boolean needRefresh = false;
	private AlertDialog.Builder builder_Pic;
	private ArrayAdapter<String> adapter_Pic;
	private final String[] items = new String[]{"拍照" , "从相册选择"};
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private static final int PHOTO_REQUEST_CUT = 3;// 剪切结果
	private String currImg = null;
	private AlertDialog dialog;
	private MyLogger logger = MyLogger.jLog();
	private User user;
	private int selectIndex = 0;
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
		user = SerUser.deSerializationUser((String) SharedPreferencesUtils
				.getParam("SerUser", getActivity(), "user", ""));
		TextView tv_title = (TextView)view.findViewById(R.id.top_text);
		tv_title.setText(getResources().getString(R.string.my_edit));
		Button back = (Button)view.findViewById(R.id.top_left);
		back.setOnClickListener(this);
		Button right = (Button)view.findViewById(R.id.top_right);
		right.setVisibility(View.GONE);
		
		layout_icon  = (RelativeLayout)view.findViewById(R.id.my_edit_icon);
		layout_name = (RelativeLayout)view.findViewById(R.id.my_edit_name);
		layout_Signature = (RelativeLayout)view.findViewById(R.id.my_edit_Signature);
		layout_address = (RelativeLayout)view.findViewById(R.id.my_edit_address);
		layout_sex = (RelativeLayout)view.findViewById(R.id.my_edit_sex);
		layout_icon.setOnClickListener(this);
		layout_name.setOnClickListener(this);
		layout_Signature.setOnClickListener(this);
		layout_address.setOnClickListener(this);
		layout_sex.setOnClickListener(this);
		
		iv_Icon = (ImageView)view.findViewById(R.id.my_edit_icon_image);
		
		tv_Name = (TextView)view.findViewById(R.id.my_edit_name_right);
		tv_Signature = (TextView)view.findViewById(R.id.my_edit_Signature_right);
		tv_sex = (TextView)view.findViewById(R.id.my_edit_sex_right);
		tv_address = (TextView)view.findViewById(R.id.my_edit_address_right);
		
		tv_Name.setText(user.getName());
		tv_Signature.setText(user.getDescription());
		tv_sex.setText(user.judgeGender());
		tv_address.setText(user.getLocation());
		MaimobApplication.imageLoader.displayImage(StringUtils.checkImgPath(user.getIcon()), iv_Icon, ImageUtil.getOption());
		
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
			startActivityForResult(Intent.createChooser(intent, "XWKKX"), PICK_FROM_FILE);
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
			startPhotoZoom(uri, 150);
		}else if (requestCode == PICK_FROM_CAMERA) {
			startPhotoZoom(mImageCaptureUri, 150);
		}else if (requestCode == PHOTO_REQUEST_CUT) {
			setPicToView(data);
			mFragmentManage.Refresh_userInfo = true;
		}
	}
	
	 //将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			File filePath = bitmapToFile(photo);
			MaimobApplication.imageLoader.displayImage(
					StringUtils.checkImgPath(filePath.toString()), iv_Icon,
					ImageUtil.getOption());
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.user,
					getActivity(), SharedPreferencesUtils.user_icon,
					StringUtils.checkImgPath(filePath.toString()));
			user.setIcon(StringUtils.checkImgPath(filePath.toString()));
			user.saveUser(getActivity(), SerUser.serializeUser(user));
		}

	}
    
	private File bitmapToFile(Bitmap bitmap) {
		File file = new File(
				Environment.getExternalStorageDirectory() + "/Mai", "Mai_crop_"
						+ String.valueOf(System.currentTimeMillis()) + ".jpg");
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
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
			dialog.show();
			break;
		case R.id.my_edit_name:
			et_input.setHint(user.getName());
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("编辑昵称").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
//					SharedPreferencesUtils.setParam(SharedPreferencesUtils.user, getActivity(), SharedPreferencesUtils.user_name, content);
					user.setName(content);
					tv_Name.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		case R.id.my_edit_Signature:
			et_input.setHint(user.getDescription());
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("编辑签名").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
					user.setDescription(content);
					tv_Signature.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		case R.id.my_edit_address:
			et_input.setHint(user.getLocation());
			new AlertDialog.Builder(getActivity()).setView(et_input).setTitle("编辑地址").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String content = et_input.getText().toString();
					user.setLocation(content);
					tv_address.setText(content);
					dialog.dismiss();
					mFragmentManage.Refresh_userInfo = true;
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					MyLogger.jLog().i(content);
				}
			})
			.show();
			break;
		case R.id.my_edit_sex:
			final String[] gender = new String[]{"女","男"};
			new AlertDialog.Builder(getActivity()).setTitle("编辑性别").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					logger.i("index  " +gender[selectIndex]);
					user.saveUser(getActivity(), SerUser.serializeUser(user));
					tv_sex.setText(user.judgeGender());
					mFragmentManage.Refresh_userInfo = true;
				}
			})
			.setSingleChoiceItems(gender, user.getGender(), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					user.setGender(which);;
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
