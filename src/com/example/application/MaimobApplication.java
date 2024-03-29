package com.example.application;

import java.io.File;

import com.example.maiUtil.Getuuid;
import com.example.util.Uris;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;

import android.R.anim;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

public class MaimobApplication extends Application{
	public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
            RequestType.SOCIAL);
	public static boolean Jelly_Bean;
	public static int DeviceW ;
	public static int DeviceH;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
		Jelly_Bean = Version();
		
	}
	public static int page = 1;
	public static int count = 5;
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		Log.i("XXX", "application" +Environment.getExternalStorageDirectory());
		File cacheFile = StorageUtils.getOwnCacheDirectory(context, "Mai");
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.diskCache(new UnlimitedDiscCache(cacheFile))
//				.discCache(new UnlimitedDiscCache(cacheFile))
//				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		Getuuid getuuid = new Getuuid(context);
		Uris.uuid = getuuid.getDeviceIdentity();
		Log.i("XXX", "uuid  " + Uris.uuid);
		
	}
	
	public static boolean Version (){
		int num = android.os.Build.VERSION.SDK_INT;
		int ice = android.os.Build.VERSION_CODES.JELLY_BEAN;
		if (num > ice) {
			return true;
		}
			return false;
	}

}
