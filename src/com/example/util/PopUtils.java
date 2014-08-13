package com.example.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;

import com.example.adapter.XAdapter.ViewHolder;
import com.example.tab.R;

public class PopUtils {
	static boolean yesOrno ;
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
     * ����Toast�Ĺ�����
     *
     * @param context
     * @param msg
     */
    public static void toastShow(Context context, String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    
//	public static boolean yesOrno(Context context){
//		new AlertDialog.Builder(context).setTitle("��ʾ").setMessage("�Ƿ�ȡ����")
//		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				yesOrno = true;
//			}
//		})
//		.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				yesOrno = false;
//				dialog.dismiss();
//			}
//		}).show();
//		return yesOrno;
//	}
	/**
	 * ���ʿ�	yes Listener
	 *
	 */
    public interface OnClickYesListener {  
        abstract void onClickYes();  
    }  
  
    /** 
     * ���ʿ��	no Listener 
     *  
     */  
    public interface OnClickNoListener {  
        abstract void onClickNo();  
    }  
    
    public static void showQuestion(Context context, String title, String message, final OnClickNoListener noListener
    		, final OnClickYesListener yesListener){
    		Builder builder = new AlertDialog.Builder(context);
//    		if (!isBlank(message)) {
//				TextView tv = new TextView(context);
//				tv.setText(message);
//				LinearLayout layout =  new LinearLayout(context);
//				layout.setPadding(10, 0, 10, 0);
//				layout.addView(tv, new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
//						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
//				builder.setView(layout);
//			}
    		builder.setMessage(message);
    		builder.setTitle(title);
    		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (yesListener != null) {
						yesListener.onClickYes();
					}
				}
			});
    		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (noListener != null) {
						noListener.onClickNo();
					}
				}
			});
    		builder.setCancelable(true);
    		builder.create().show();
    }
    
    /** 
     * �����ַ��ķ��� 
     *  
     * @param str 
     * @return 
     */  
    public static boolean isBlank(String str) {  
        int strLen;  
        if (str == null || (strLen = str.length()) == 0) {  
            return true;  
        }  
        for (int i = 0; i < strLen; i++) {  
            if ((Character.isWhitespace(str.charAt(i)) == false)) {  
                return false;  
            }  
        }  
        return true;  
    }  
}
