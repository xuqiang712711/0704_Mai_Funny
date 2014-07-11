package com.example.fragment;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.AsyTask.MyAsyTask;
import com.example.AsyTask.MyTask_No_Result;
import com.example.tab.R;
import com.example.util.CustomImage;
import com.example.util.Uris;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Tab_Search_Frag extends Fragment implements OnClickListener {

	private String Tag = "Tab_Search_Frag";
	private View view;
	private CustomImage trash, sex, neihan, funny, skip;
	private TextView textView;
	private ImageView imageView;
	private DisplayImageOptions options;
	private JSONArray array = null;
	private int flag_neihan = 1;
	private int flag_trash = 2;
	private int flag_sex = 3;
	private int flag_old = 4;
	private int num = 0;
	private int pid = 0;
	private MyTask_No_Result task = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e(Tag, "Tab_Search_Frag");
		view = inflater.inflate(R.layout.my_check, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initHttp();
		listenerWidget();
	}

	private void initView() {
		TextView title = (TextView)view.findViewById(R.id.back2_text);
		title.setText(R.string.my_check);
		
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.maimob)
				.showImageForEmptyUri(R.drawable.maimob)
				.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer()).build();

		trash = (CustomImage) view.findViewById(R.id.my_check_trash);
		sex = (CustomImage) view.findViewById(R.id.my_check_sex);
		neihan = (CustomImage) view.findViewById(R.id.my_check_neihan);
		funny = (CustomImage) view.findViewById(R.id.my_check_boring);
		skip = (CustomImage) view.findViewById(R.id.my_check_skip);

		setContent(trash, R.drawable.ic_trash, R.string.my_trash);
		setContent(sex, R.drawable.ic_sex, R.string.my_sex);
		setContent(neihan, R.drawable.ic_connotation, R.string.my_neihan);
		setContent(funny, R.drawable.ic_funny, R.string.my_boring);
		setContent(skip, R.drawable.ic_skip, R.string.my_skip);

		textView = (TextView) view.findViewById(R.id.my_check_content);
		imageView = (ImageView) view.findViewById(R.id.my_check_image);
	}

	private void setContent(CustomImage ci, int resImg, int resText) {
		ci.setLayoutOrientation(LinearLayout.VERTICAL);
		ci.setImageResource(resImg);
		ci.setTextView_int(resText);
	}

	private void initHttp() {
		String check = Uris.Check + "/uuid/" + Uris.uuid + "/maxID/" + 0;
		MyAsyTask asyTask = new MyAsyTask(handler);
		asyTask.execute(check);
	}

	private void listenerWidget() {
		trash.setOnClickListener(this);
		sex.setOnClickListener(this);
		neihan.setOnClickListener(this);
		funny.setOnClickListener(this);
		skip.setOnClickListener(this);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				num = (int) ((Math.random()) * array.length());
				break;

			case 2:
				Bundle data = msg.getData();
				try {
					array = new JSONArray(data.getString("json"));
					Log.i(Tag, "Check_array  " + array);
					num = (int) ((Math.random()) * array.length());
					// JSONObject jsonObject = new
					// JSONObject(data.getString("json"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			Log.i(Tag, "Num  " + num);
			String text = null;
			String img = null;
			try {
				text = ((JSONObject) array.get(num)).getString("content");
				img = ((JSONObject) array.get(num)).getString("img");
				pid = ((JSONObject)array.get(num)).getInt("id");
				textView.setText(text);
				ImageLoader imageLoader = ImageLoader.getInstance();
				if (img != null && !img.equals("")) {
					imageLoader.displayImage(img, imageView, options);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i(Tag, "Warring");
				e.printStackTrace();
			}

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.my_check_skip:
			SwitchContent();
			break;

		case R.id.my_check_neihan:
			check_handle(flag_neihan);
			break;
		case R.id.my_check_boring:
			check_handle(flag_old);
			break;
		case R.id.my_check_sex:
			check_handle(flag_sex);
			break;
		case R.id.my_check_trash:
			check_handle(flag_trash);
			break;
		}
	}
	
	private void SwitchContent(){
		Message message = Message.obtain();
		message.what = Uris.MSG_UPDATE;
		handler.sendMessage(message);
	}
	
	private void check_handle(int flag){
		task = new MyTask_No_Result();
		String uris = Uris.Check_handle +"/uuid/" + Uris.uuid + "/pid/" + pid + "/vflag/" + flag;
		task.execute(uris);
		SwitchContent();
	}
}
