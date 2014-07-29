package com.example.AsyTask;

import java.io.IOException;

import org.apache.http.HttpResponse;
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

import android.os.AsyncTask;
import android.util.Log;

public class MyTask_No_Result extends AsyncTask<String, Void, Void> {
	private String Tag = "MyTask_No_Result";

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		return doHttpRequest(params[0]);
	}

	/**
	 * 
	 * 
	 * @param urls
	 * @return
	 */
	private Void doHttpRequest(String... urls) {
		HttpClient httpClient = CustomHttpClient.getHttpClient();
		try {
			HttpGet request = new HttpGet(urls[0]);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setSoTimeout(params, 60000); // 1 minute
			request.setParams(params);
			// HttpResponse httpResponse = httpClient.execute(request);
			String response = httpClient.execute(request,
					new BasicResponseHandler());
			JSONObject object = new JSONObject(response);
			String flag = object.optString("flag");
			Log.i(Tag, "String  " + flag);
			// return response;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
