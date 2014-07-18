package com.example.object;

import com.example.tab.XYFTEST;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class mFragmentManage {
	/**
	 * �����¼�
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
	 * Fragment���л�
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
	
	
}
