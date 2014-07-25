package com.example.util;

import android.content.Context;

public class UserUtils {
	//查询是否存在用户
	public static boolean UserIsExists(Context context){
		return (Boolean) SharedPreferencesUtils.getParam("platform", context, "Exists", false);
	}
}
