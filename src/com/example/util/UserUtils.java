package com.example.util;

import android.content.Context;

public class UserUtils {
	//��ѯ�Ƿ�����û�
	public static boolean UserIsExists(Context context){
		return (Boolean) SharedPreferencesUtils.getParam("platform", context, "Exists", false);
	}
}
