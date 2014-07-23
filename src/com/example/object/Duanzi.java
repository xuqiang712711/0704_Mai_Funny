package com.example.object;

import java.io.Serializable;

import com.example.tab.R;
import com.example.tab.R.drawable;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.utils.ToastUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("serial")
public class Duanzi implements Serializable {
	private String Tag = "Duanzi";
	public static final int CAI = 100;
	public static final int CAI_PRESSED = 102;
	public static final int CAI_NORMAL = 101;
	public static final int ZAN = 200;
	public static final int ZAN_PRESSED = 202;
	public static final int ZAN_NORMAL = 201;
	public static final String ISZANED = "你已经赞过";
	public static final String ISCAIED = "你已经踩过XXXXXXXX";
	private String userName;		//用户名
	private String cai;			//踩的数量
	private String zan;			//赞的数量
	private String content;		//段子内容
	private String imageUrl;		//图片url
	private String comment;		//评论数量
	private String poid;			//段子id
	private boolean isZanPressed;// 是否赞过
	private boolean isCaiPressed;// 是否踩过
	private SHARE_MEDIA media;	//转发的平台
	private boolean isFav;		//是否收藏
	private boolean needComment;  //是否需要发表评论到服务器

	public Duanzi(String imageUrl, String name, String cai, String zan,
			String content, String comment, String poid, boolean isZanPressed,
			boolean isCaiPressed,SHARE_MEDIA media,boolean isFav, boolean needComment) {
		this.imageUrl = imageUrl;
		this.userName = name;
		this.cai = cai;
		this.zan = zan;
		this.content = content;
		this.comment = comment;
		this.poid = poid;
		this.isZanPressed = isZanPressed;
		this.isCaiPressed = isCaiPressed;
		this.media = media;
		this.isFav = isFav;
		this.needComment = needComment;
	}
	
	public void CanPress(int type,TextView txtview,ImageView  imgView,Context context){
		switch (type) {
		case Duanzi.ZAN:
			if (isZanPressed() == false) {
				if (isCaiPressed()== true) {
					ToastUtil.showToast(context, ISCAIED);
					break;
				}
//				txtview.setCompoundDrawables(ChangePic(context, ZAN_PRESSED), null, null, null);
				imgView.setImageResource(R.drawable.ic_digg_pressed);
				txtview.setText(String.valueOf(Integer.parseInt(zan) +1));
				setZanPressed(true);
			}else {
				ToastUtil.showToast(context, ISZANED);
			}
			break;

		case Duanzi.CAI:
			if (!isCaiPressed) {
				if (isZanPressed() == true) {
					ToastUtil.showToast(context, ISZANED);
					break;
				}
				Log.e(Tag, "Cai~~~~");
//				txtview.setCompoundDrawables(ChangePic(context, CAI_PRESSED), null, null, null);
				imgView.setImageResource(R.drawable.ic_bury_pressed);
				txtview.setText(String.valueOf(Integer.parseInt(getCai())+ 1));
				this.setCaiPressed(true);
			}else {
				ToastUtil.showToast(context, ISCAIED);
			}
			break;
		}
	}
	
	
	
	
	

	public boolean isNeedComment() {
		return needComment;
	}

	public void setNeedComment(boolean needComment) {
		this.needComment = needComment;
	}

	public boolean isFav() {
		return isFav;
	}

	public void setFav(boolean isFav) {
		this.isFav = isFav;
	}

	/**
	 * 分享的平台
	 * @return
	 */
	public SHARE_MEDIA getMedia() {
		return media;
	}

	public void setMedia(SHARE_MEDIA media) {
		this.media = media;
	}

	/**
	 * 选择赞、踩的图片
	 * @param context
	 * @param type
	 * @return
	 */
	public Drawable ChangePic(Context context, int type) {
		Drawable drawable = null;
		switch (type) {
		case CAI_PRESSED:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_bury_pressed);
			break;
		case CAI_NORMAL:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_bury_normal);
			break;
		case ZAN_PRESSED:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_digg_pressed);
			break;
		case ZAN_NORMAL:
			drawable = context.getResources().getDrawable(
					R.drawable.ic_digg_normal);
			break;
		}
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		return drawable;
	}

	/**
	 * 设置图片状态为已经踩过、赞过
	 * 
	 * @param context
	 * @return
	 */

	public boolean isZanPressed() {
		return isZanPressed;
	}

	public void setZanPressed(boolean isZanPressed) {
		this.isZanPressed = isZanPressed;
	}

	public boolean isCaiPressed() {
		return isCaiPressed;
	}

	public void setCaiPressed(boolean isCaiPressed) {
		this.isCaiPressed = isCaiPressed;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCai() {
		return cai;
	}

	public void setCai(String cai) {
		this.cai = cai;
	}

	public String getZan() {
		return zan;
	}

	public void setZan(String zan) {
		this.zan = zan;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
