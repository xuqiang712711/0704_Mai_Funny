package com.example.util;
/*
 * abstract 处理网络json的http类
 * author:高伟
 * */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	public static HttpClient httpClient=new DefaultHttpClient();
//	public static final String BaseUrl="http://s3-ap-northeast-1.amazonaws.com/testhotel/hotels.json";
	public static final String postUri = "http://md.maimob.net/index.php/player/FetchPost/uuid/YTBhYWYzYmEtOTI2NC0zZDRjLThlNDQtYjExOGQ2OWQ4NGJi/type/1/subType/3/maxID/0";
	
	public static String getRequest(String url) throws Exception{
		HttpGet get=new HttpGet(url);
		HttpResponse httpresponse=httpClient.execute(get);
		if(httpresponse.getStatusLine().getStatusCode()==200){
		   String result=EntityUtils.toString(httpresponse.getEntity());
		   return result;
		}
		return null;
	}
	
	public static String postRequest(String url,Map<String,String> rawParams) throws Exception{
		HttpPost post=new HttpPost(url);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		for(String key:rawParams.keySet()){
			params.add(new BasicNameValuePair(key,rawParams.get(post)));
		}
		HttpResponse httpResponse=httpClient.execute(post);
		if(httpResponse.getStatusLine().getStatusCode()==200){
			String result=EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}

}
