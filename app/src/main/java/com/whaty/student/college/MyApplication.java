package com.whaty.student.college;

import android.app.Application;

import com.whaty.student.college.beans.UserInfo;
import com.whaty.student.college.sp.CookiesSP;

public class MyApplication extends Application {

	private static MyApplication mInstance;
	private static UserInfo userInfo = null;


	public static synchronized MyApplication getInstance() {
		return mInstance;
	}


	public static UserInfo getUser() {
		if (userInfo == null) {
			userInfo = CookiesSP.getInstance().get();
		}
		return userInfo;
	}

	public static void setUser(UserInfo mUserInfo) {
		userInfo = mUserInfo;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
}
