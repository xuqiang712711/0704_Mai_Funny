package com.example.util;

import java.io.File;
import java.net.URL;

import com.example.tab.R;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

/**
 *
 */
public class CommonUtils {
	
	public final static String SDCARD_MNT = "/mnt/sdcard";
  	public final static String SDCARD = "/sdcard";
	
	/**
	 * tag
	 */
	public static final String WEIBO = "Weibo";   
	
	/**
	 * 回调地址
	 */
	public static final String REDIRECT_URL = "http://www.sina.com";
	
	/**
	 * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路�?
	 * @param uri
	 * @return
	 */
	public static String getAbsolutePathFromNoStandardUri( Uri mUri )
	{	
		String filePath = null;
		
		String mUriString = mUri.toString();
		mUriString = Uri.decode(mUriString);
		
		Log.i("FFF", "Common  " + mUriString);
		
		String pre1 = "file://" + SDCARD + File.separator;
		String pre2 = "file://" + SDCARD_MNT + File.separator;
		
		if( mUriString.startsWith(pre1) )
		{    
			filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring( pre1.length() );
		}
		else if( mUriString.startsWith(pre2) )
		{
			filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + mUriString.substring( pre2.length() );
		}
		return filePath;
	}
	
	/**
	 * 通过uri获取文件的绝对路径
	 * @param uri
	 * @return
	 */
	public static String getAbsoluteImagePath(Context context, Uri uri) {
		String imagePath = "";
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);

		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				imagePath = cursor.getString(column_index);
			}
		}

		return imagePath;
	}
	
}