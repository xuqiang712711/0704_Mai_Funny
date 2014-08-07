package com.example.AsyTask;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.maiUtil.CustomHttpClient;
import com.example.util.MyLogger;
import com.example.util.Uris;

import android.R.array;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RequestDataTask extends AsyncTask<String, Void, String>{
	String Tag = "RequestDataTask";
	private Handler mHandler;
	public RequestDataTask(Handler handler){
		this.mHandler = handler;
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		MyLogger.jLog().i("uri  " + params[0]);
		return doHttpRequest(params[0]);
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.e(Tag, "result  " + result);
		JSONArray array2 = null;
		try {
			array2 = new JSONArray(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (array2.length() == 0) {
			Log.e("Tag", "result 为空");
		}
		if (mHandler != null) {
			if (result != null) {
				int maxID = 0;
				try {
					JSONArray array = new JSONArray(result);
					maxID = array.length();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (maxID == 0) {
					Message msg = Message.obtain();
					msg.what = Uris.MSG_NOTIHING;
					mHandler.sendMessage(msg);
				}else {
					Message msg = Message.obtain();
					msg.obj = result;
					msg.what = maxID;
					mHandler.sendMessage(msg);
				}
			}else {
				Message msg = Message.obtain();
				msg.what = Uris.MSG_NOTIHING;
				mHandler.sendMessage(msg);
			}
		}
	}
	
	/**
	 * 从服务器获取数据（字符串）
	 * 
	 * @param urls
	 * @return
	 */
	private String doHttpRequest(String... urls) {
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		try {
			HttpGet request = new HttpGet(urls[0]);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(params, 60000); // 1 minute
			request.setParams(params);
			String response = httpClient.execute(request,
					new BasicResponseHandler());
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
