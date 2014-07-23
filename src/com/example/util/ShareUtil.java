package com.example.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.application.MaimobApplication;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;

public class ShareUtil {
	
	public static final int SHARE_SUC = 345;
	/**
	 * 分享
	 * 
	 * @param media
	 *            类型 sina、tencent、renren、douban
	 * @param editContent
	 *            评论分享
	 * @param DuanziContent
	 *            内容分享、文字
	 * @param imgUrl
	 *            内容分享、图片
	 * @param context
	 */
	public static void ShareToSocial(SHARE_MEDIA media, String editContent,
			String DuanziContent, String imgUrl, final Context context, final Handler mHandler) {
		Log.i("FFF", "con  " + DuanziContent);
		if (imgUrl != null) {
			MaimobApplication.mController.setShareMedia(new UMImage(context,
					imgUrl));
		}
		if (editContent != null && !editContent.equals("")) {
			MaimobApplication.mController.setShareContent( editContent
					+ "//" + DuanziContent + "(来自@大麦段子)");
		}else {
			MaimobApplication.mController.setShareContent(DuanziContent + "(来自@大麦段子)");
		}
		MaimobApplication.mController.directShare(context, media,
				new SnsPostListener() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "分享开始", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onComplete(SHARE_MEDIA arg0, int eCode,
							SocializeEntity arg2) {
						// TODO Auto-generated method stub
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT)
									.show();
							if (mHandler != null) {
								Message msg = Message.obtain();
								msg.what = Uris.MSG_SUC;
								mHandler.sendMessageDelayed(msg, 5000);
							}
						} else {
							Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
	}
}
