package com.example.application;

import java.io.File;

import com.example.maiUtil.Getuuid;
import com.example.object.mFragmentManage;
import com.example.tab.R;
import com.example.util.SharedPreferencesUtils;
import com.example.util.Uris;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
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
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

public class MaimobApplication extends Application{
	public Handler mHandler = null;
	public static ImageLoader imageLoader = ImageLoader.getInstance();
	public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
            RequestType.SOCIAL);
//	public static final UMSocialService loginController = UMServiceFactory.getUMSocialService(arg0, arg1)
	public static boolean Jelly_Bean;
	public static int DeviceW ;
	public static int DeviceH;
	public static DisplayImageOptions options;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
		Jelly_Bean = Version();
		selectFontSize(getApplicationContext());
		mFragmentManage.Refresh_userInfo = false;
	}
	public static int page = 1;
	public static int count = 5;
	public static void initImageLoader(Context context) {
		Log.i("XXX", "application" +Environment.getExternalStorageDirectory());
		File cacheFile = StorageUtils.getOwnCacheDirectory(context, "Mai");
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.diskCache(new UnlimitedDiscCache(cacheFile))
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		
		Getuuid getuuid = new Getuuid(context);
		Uris.uuid = getuuid.getDeviceIdentity();
		Log.i("XXX", "uuid  " + Uris.uuid);
		
	}
	/**
	 * 版本是否高于JELLY_BEAN
	 * @return
	 */
	public static boolean Version (){
		int num = android.os.Build.VERSION.SDK_INT;
		int ice = android.os.Build.VERSION_CODES.JELLY_BEAN;
		if (num > ice) {
			return true;
		}
			return false;
	}
	/**
	 * 得到ImageLoader的Option
	 */
	public static void getOption(){
		options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
		.showImageOnLoading(R.drawable.maimob)
		.showImageForEmptyUri(R.drawable.maimob)
		.showImageOnFail(R.drawable.maimob).cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
		}
	public Handler getmHandler() {
		return mHandler;
	}
	public void setmHandler(Handler mHandler) {
		this.mHandler = mHandler;
	}
	
	public static void  selectFontSize(Context context){
		String font = (String) SharedPreferencesUtils.getParam(SharedPreferencesUtils.setting, context, 
				SharedPreferencesUtils.setting_font, "naomal");
		if (font.equals("small")) {
			Uris.Font_Size = Uris.Font_Size_small;
		}else if (font.equals("big")) {
			Uris.Font_Size  = Uris.Font_Size_big;
		}else {
			Uris.Font_Size = Uris.Font_Size_normal;
		}
	}
}
