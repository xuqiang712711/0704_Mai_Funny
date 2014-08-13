package com.example.Activity;

import com.example.sql.Mai_DBhelper;
import com.example.util.MyLogger;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;

public class BaseActivity extends Activity{
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected MyLogger Xlog = MyLogger.jLog();
//	protected Mai_DBhelper db = Mai_DBhelper.getInstance(getApplicationContext());
}
