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
	private RelativeLayout layout_icon, layout_name, layout_Signature;
	private ImageView iv_Icon;
	private TextView tv_Name, tv_Signature;
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
		
		SharedPreferences sp = getActivity().getSharedPreferences("SerUser", Context.MODE_PRIVATE);
		String MYuser = sp.getString("user", null);
		try {
			User user = SerUser.deSerializationUser(MYuser);
			logger.e("user  " + user.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			User user = SerUser.deSerializationUser((String)SharedPreferencesUtils.getParam("SerUser", getActivity(),
//					"user", ""));
//			logger.i("name  " + user.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
//			String imgPath = CommonUtils.getAbsolutePathFromNoStandardUri(uri);
//			if (StringUtils.isBlank(imgPath)) {
//				currImg = CommonUtils.getAbsoluteImagePath(getActivity(), uri);
//			}
//			
//			Uri outUri = Uri.fromFile(new File(getActivity().getCacheDir(), "Mai"));
//			new Crop(uri).output(outUri).asSquare().start(getActivity());
		}else if (requestCode == PICK_FROM_CAMERA) {
//			currImg = mImageCaptureUri.getPath();
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
			// Drawable drawable = new BitmapDrawable(photo);
			// img_btn.setBackgroundDrawable(drawable);
			// iv_Icon.setImageDrawable(drawable);
			MaimobApplication.imageLoader.displayImage(
					StringUtils.checkImgPath(filePath.toString()), iv_Icon,
					ImageUtil.getOption());
			SharedPreferencesUtils.setParam(SharedPreferencesUtils.user,
					getActivity(), SharedPreferencesUtils.user_icon,
					StringUtils.checkImgPath(filePath.toString()));
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
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

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
			et_input.setHint((String)SharedPreferencesUtils.getParam(SharedPreferencesUtils.user, getActivity(),
					SharedPreferencesUtils.user_name, ""));
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
