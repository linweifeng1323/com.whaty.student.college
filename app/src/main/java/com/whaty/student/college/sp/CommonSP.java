package com.whaty.student.college.sp;

import android.content.Context;

import com.whaty.student.college.MyApplication;


/**
 * 公告缓存
 */
public class CommonSP extends SpUtil {
	
	private static CommonSP instance;
	private static final String SP_NAME = "Common";
	
	private final String KEY_FIRST_LAUNCH = "first_launch";
	private final String KEY_LOCK = "key_lock";
	private final String KEY_CACHE = "key_cache";
	private final String KEY_REMEMBER_PSW = "key_remember_psw";
	private final String KEY_AUTO_LOGIN = "key_auto_login";
	private final String KEY_TEACHER_DOWNLOAD = "key_teacher_download";
	private final String KEY_IM_LOGIN = "key_im_login";

	private CommonSP(Context context) {
		super(context, SP_NAME);
	}
	
	public static final CommonSP getInstance() {
		if (instance == null) {
			synchronized (CommonSP.class) {
				if (instance == null) {
					instance = new CommonSP(MyApplication.getInstance());
				}
			}
		}
		return instance;
	}
	
	public boolean isDownload() {
		return getValue(KEY_TEACHER_DOWNLOAD, false);
	}
	
	public void setDownload(boolean isDownload) {
		setValue(KEY_TEACHER_DOWNLOAD, isDownload);
	}
	
	public boolean isAutoLogin() {
		return getValue(KEY_AUTO_LOGIN, false);
	}
	
	public void setAutoLogin(boolean isAutoLogin) {
		setValue(KEY_AUTO_LOGIN, isAutoLogin);
	}
	
	public boolean isRememberPSW() {
		return getValue(KEY_REMEMBER_PSW, false);
	}
	
	public void setRememberPSW(boolean isRememberPSW) {
		setValue(KEY_REMEMBER_PSW, isRememberPSW);
	}
	
	public boolean isFirstLaunch() {
		return getValue(KEY_FIRST_LAUNCH, true);
	}
	
	public void setFirstLaunch(boolean isFirstLaunch) {
		setValue(KEY_FIRST_LAUNCH, isFirstLaunch);
	}
	
	public String getLock(String uid) {
		return getValue(KEY_LOCK + uid, "");
	}
	
	public void setLock(String uid, String lockStr) {
		setValue(KEY_LOCK + uid, lockStr);
	}

	
	public void setCache(String key, String string) {
		setValue(KEY_CACHE + key, string);
	}

	public String getCache(String key) {
		return getValue(KEY_CACHE + key, "");
	}
	
	public String getCache(String key, String defaultValue) {
		return getValue(KEY_CACHE + key, defaultValue);
	}
}
