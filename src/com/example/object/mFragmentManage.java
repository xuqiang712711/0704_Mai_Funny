package com.example.object;

import com.example.Activity.XYFTEST;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class mFragmentManage {
	public static int Tag_My = 0;
	public static int Tag_Userinfo = 1;
	/**
	 * 返回事件
	 * @param context
	 */
	public static void BackStatck(Context context){
		if (context == null) {
			return;
		}
		if (context instanceof XYFTEST) {
			XYFTEST xyftest = (XYFTEST) context;
			xyftest.hintFragment();
		}
	}
	
	/**
	 * Fragment间切换
	 * @param context
	 * @param from
	 * @param to
	 * @param bundle
	 */
	public static void SwitchFrag(Context context, Fragment from, Fragment to, Bundle bundle){
		if (context == null) {
			return;
		}
		if (context instanceof XYFTEST) {
			XYFTEST xyftest = (XYFTEST)context;
			xyftest.switchContentFull(from, to, bundle);
		}
	}
	
	/**
	 * 切换同时刷新
	 * @param context
	 * @param FragTag
	 */
	public static void RefreshFrag(Context context,int FragTag){
		if (context == null) {
			return;
		}
		if (context instanceof XYFTEST) {
			XYFTEST xyftest = (XYFTEST)context;
			xyftest.RefreshFragment(FragTag);
		}
	}
	
	public static void backHome(Context context, int type){
		if (context == null) {
			return;
		}
		if (context instanceof XYFTEST) {
			XYFTEST xyftest =(XYFTEST)context;
			xyftest.WriteBack(type);
		}
	}
	
}
