package com.example.Activity;

import com.example.sql.Mai_DBhelper;
import com.example.util.MyLogger;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.app.Activity;

public class BaseActivity extends Activity{
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected MyLogger Xlog = MyLogger.jLog();
	protected UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
            RequestType.SOCIAL);
//	protected Mai_DBhelper db = Mai_DBhelper.getInstance(getApplicationContext());
}
