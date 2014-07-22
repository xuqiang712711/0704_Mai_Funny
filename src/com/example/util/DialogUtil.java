package com.example.util;

import com.example.tab.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DialogUtil {
	public static Dialog createLoadingDialog(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.mloading, null);
		RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.loading_view);
		ImageView iv_loding = (ImageView)view.findViewById(R.id.loading_iv);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.round_loading);
		iv_loding.setAnimation(animation);
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setCancelable(false);
		loadingDialog.setContentView(layout, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				RelativeLayout.LayoutParams.MATCH_PARENT));
		return loadingDialog;
	}
}
