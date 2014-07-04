package com.example.AsyTask;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.maiUtil.CustomHttpClient;
import com.example.util.Uris;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MyAsyTask extends AsyncTask<String, Void, String> {
	private Handler handler;

	public MyAsyTask(Handler handler) {
		this.handler = handler;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return doHttpRequest(params);
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Message msg = Message.obtain();
		msg.what = Uris.MSG_GETDATA;
		Bundle bundle = new Bundle();
		bundle.putString("json", result);
		msg.setData(bundle);
		handler.sendMessage(msg);
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
