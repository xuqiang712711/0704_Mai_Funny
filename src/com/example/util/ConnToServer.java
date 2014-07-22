package com.example.util;

import com.example.AsyTask.MyTask_No_Result;

public class ConnToServer {
	public static final int CAI = 33;
	public static final int ZAN = 34;
	public static final int FAV = 35;
	
	public static void DohttpNoResult(int type,String poid){
		MyTask_No_Result myTask = new MyTask_No_Result();
		String uri = null;
		switch (type) {
		case ZAN:
			uri = Uris.Zan + "/uuid/" + Uris.uuid + "/pid/"+ poid;
			break;

		case CAI:
			uri = Uris.Cai + "/uuid/" + Uris.uuid + "/pid/" + poid;
			break;
		case FAV:
			uri = Uris.FAV + "/uuid/" + Uris.uuid + "/pid/" + poid;
			break;
		}
		myTask.execute(uri);
	}
}
