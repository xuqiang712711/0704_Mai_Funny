package com.example.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;

import com.example.adapter.XAdapter.ViewHolder;
import com.example.tab.R;

public class PopUtils {
	/**
	 * loding dialog
	 * @param context
	 * @return
	 */
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
	
	/**
     * 弹出Toast的工具类
     *
     * @param context
     * @param msg
     */
    public static void toastShow(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    
	
}
