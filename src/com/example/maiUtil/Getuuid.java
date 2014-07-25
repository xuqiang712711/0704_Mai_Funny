package com.example.maiUtil;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import com.example.Activity.XYFTEST;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class Getuuid {
	protected static final String PREFS_FILE = "device_id.xml";
	protected static final String PREFS_DEVICE_ID = "device_id";
	private Context context;
	public Getuuid(Context context){
		this.context = context;
	}
	
	/**
	 * 
	 * @return UUID
	 */
	public String getDeviceIdentity() {
		String identity = "";
		String androidID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		UUID uuid = null;
		if (uuid == null) {
			synchronized (XYFTEST.class) {
				if (uuid == null) {
					final SharedPreferences prefs = context
							.getSharedPreferences(PREFS_FILE, 0);
					final String id = prefs.getString(PREFS_DEVICE_ID, null);

					if (id != null) {
						uuid = UUID.fromString(id);
					} else {
						try {
							if (!"9774d56d682e549c".equals(androidID)) {
								uuid = UUID.nameUUIDFromBytes(androidID
										.getBytes("utf8"));
							} else {
								final String deviceId = ((TelephonyManager) context
										.getSystemService(Context.TELEPHONY_SERVICE))
										.getDeviceId();
								uuid = deviceId != null ? UUID
										.nameUUIDFromBytes(deviceId
												.getBytes("utf8")) : UUID
										.randomUUID();
							}
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
						// Write the value out to the prefs file
						prefs.edit()
								.putString(PREFS_DEVICE_ID, uuid.toString())
								.commit();
					}

				}
			}
		}
		identity = Base64Util.encodeStringToString(uuid != null ? uuid
				.toString() : "");
		return identity;
	}

}
