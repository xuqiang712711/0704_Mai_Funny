package com.example.fragment.content;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maiUtil.CustomHttpClient;
import com.example.tab.R;
import com.example.util.BitmapOptions;
import com.example.util.CommonUtils;
import com.example.util.EditUtil;
import com.example.util.IMG_Compress;
import com.example.util.NetworkUtil;
import com.example.util.StringUtils;
import com.example.util.Uris;

public class My_Write extends Fragment {
	private String Tag = "My_Write";
	private View view;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private final String[] items = new String[]{"拍照" , "从相册选择"};
	private final String[] items2 = new String[]{"拍照" , "从相册选择", "删除"};
	private String mPath;
    String currImgPath = null;
	private ImageView mimageView;
	private Bitmap bitmap  = null;
	private Uri mImageCaptureUri;
	private ArrayAdapter<String> adapter, adapter2;
	private EditText editText;
	private Button submit;
	private ProgressDialog Loadingdialog;
	public static final int POST_SUC = 1;
	private boolean switch_dialog =false;
	private AlertDialog.Builder builder, del_builder;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.my_write, container, false);
		Log.e(Tag, Tag);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	private void initView(){
		TextView title = (TextView)view.findViewById(R.id.back_text);
		title.setText(R.string.my_write);
		
		submit = (Button)view.findViewById(R.id.back_submit);
		editText = (EditText)view.findViewById(R.id.my_write_edit);
		TextView textView = (TextView)view.findViewById(R.id.my_write_count);
		mimageView = (ImageView)view.findViewById(R.id.my_write_image);
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
		adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items2);
		
		builder = new AlertDialog.Builder(getActivity());
		del_builder = new AlertDialog.Builder(getActivity());
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogChose(which);
			}
		});
		del_builder.setAdapter(adapter2, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DialogChose(which);
			}
		});
		
		final AlertDialog normal_dialog = builder.create();
		final AlertDialog del_dialog = del_builder.create();
		mimageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if (!switch_dialog) {
//					normal_dialog.show();
//					switch_dialog = true;
//				}else {
//					del_dialog.show();
//					switch_dialog = false;
//				}
				Log.i("FFF", "~~~~~~" + switch_dialog);
				if (!switch_dialog) {
					normal_dialog.show();
				}else {
					del_dialog.show();
				}
			}
		});
		new EditUtil(getActivity(), editText, textView);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (NetworkUtil.getNetworkState(getActivity()) != NetworkUtil.NONE) {
					Loadingdialog = ProgressDialog.show(getActivity(), "标题", "发送中");
					Loadingdialog.setCancelable(true);
					Loadingdialog.setIndeterminate(true);
					new Thread(new PostThread()).start();
				}
			}
		});
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
		}else if (which == 2) {
			mimageView.setImageResource(R.drawable.ugc_publish_upload_icon);
			switch_dialog = false;
		}
	}
	
	class PostThread implements Runnable{

		@Override
		public void run() {
			File file = new File(currImgPath);
			try {
				String compressFile = IMG_Compress.Compress(currImgPath, 480);
				Log.i("FFF", "comFile  " + compressFile);
				PostWithPic(Uris.Post_Draft, file, Uris.uuid);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message message = Message.obtain();
			message.what = POST_SUC;
			handler.sendMessage(message);
		}
		
	}
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case POST_SUC:
				Loadingdialog.dismiss();
				Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();;
				break;
			}
		}
	};
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("FFF", "data  " + data);
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == PICK_FROM_FILE) {
			Uri uri = data.getData();
			mPath = CommonUtils.getAbsolutePathFromNoStandardUri(uri);
			Log.i("FFF", "mPath  " + mPath);
			if (StringUtils.isBlank(mPath)) {
				currImgPath = CommonUtils.getAbsoluteImagePath(getActivity(), uri);
			}
		}else if (requestCode == PICK_FROM_CAMERA) {
			currImgPath = mImageCaptureUri.getPath();
		}
        Bitmap bitmap = BitmapOptions.decodeSampledBitmapFromFile(currImgPath, 50, 50);
        mimageView.setImageBitmap(bitmap);
        switch_dialog = !switch_dialog;
    }

    
    public void PostWithPic(String uri, File imgFile, String uuid) throws UnsupportedEncodingException{
    		HttpClient client = CustomHttpClient.getHttpClient();
    		String content = editText.getText().toString();
    		Log.i("FFF" + "投稿___content", content + "  投稿___uuid  " + uuid + " 投稿___img  " + imgFile);
    		HttpPost httpPost = new HttpPost(uri);
    		MultipartEntity entity = new MultipartEntity();
    		entity.addPart("uuid", new StringBody(uuid));
    		entity.addPart("content", new StringBody(content));
    		FileBody body = new FileBody(imgFile);
    		entity.addPart("img", body);
    		httpPost.setEntity(entity);
    		try {
    				Log.i("FFF", "投稿___uri" + httpPost.getURI());
				HttpResponse response = client.execute(httpPost);
				int code = response.getStatusLine().getStatusCode();
    				Log.i("FFF", "投稿___code"+ code);
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					String jsonObject = EntityUtils.toString(httpEntity);
					Log.i("FFF", "投稿——result"+jsonObject);
				}
			}  catch (IOException e) {
				e.printStackTrace();
				Log.i("FFF", "投稿___IO" + e);
			} 
//    			catch (JSONException e) {
//				Log.i("FFF", "投稿___Json" + e);
//				e.printStackTrace();
//			}
    }
}
