package com.example.util;

import android.app.Activity;
import android.bluetooth.BluetoothClass.Device;
import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceInfo {
	public static int screenW ;
	public static int screenH;
	private Context context;
	DisplayMetrics metrics;
	public DeviceInfo(Activity context){
		this.context = context;
		metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	}
	
	public static void getWidth(){
		
	}
	public static void getScreenInfo(Activity context){
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenW = metrics.widthPixels;
		screenH = metrics.heightPixels;
	}
}
