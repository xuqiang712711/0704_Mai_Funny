package com.example.util;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import com.example.tab.R;
import com.example.tab.XYFTEST;

public class FragmentUtil {
	public static void switchFragment(Fragment fragment){
//		if ( == null) {
//			return;
//		}
//		if (getActivity() instanceof XYFTEST) {
//			XYFTEST xyf = (XYFTEST) getActivity();
//			xyf.switchContentFull(fragment);
//		}
		XYFTEST xyftest = new XYFTEST();
//		xyftest.switchContentFull(fragment);
	}
	
}
