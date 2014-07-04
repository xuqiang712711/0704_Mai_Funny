package com.example.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class NetworkUtil {
	public final static int NONE = 0;//没有网络
	public final static int WIFI = 1;
	public final static int MOBILE = 2;//GPRS,3G
	public static int getNetworkState(Context context){
		ConnectivityManager connmanager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State state = connmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return MOBILE;
		}
		state = connmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (state == State.CONNECTED || state == State.CONNECTING) {
			return WIFI;
		}

		return NONE;
}
}