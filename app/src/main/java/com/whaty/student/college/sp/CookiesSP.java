package com.whaty.student.college.sp;

import android.content.Context;

import com.google.gson.Gson;
import com.whaty.student.college.MyApplication;
import com.whaty.student.college.beans.UserInfo;
import com.whaty.student.college.utils.StringUtil;



/**
 * 用户缓存
 */
public class CookiesSP extends SpUtil {
	
	private static CookiesSP instance;
	private static final String SP_NAME = "Cookies";
	
	private UserInfo c;
	private final String KEY_USER = "user_cookies";
	
	private CookiesSP(Context context) {
		super(context, SP_NAME);
	}
	
	public static final CookiesSP getInstance() {
		if (instance == null) {
			synchronized (CookiesSP.class) {
				if (instance == null) {
					instance = new CookiesSP(MyApplication.getInstance());
				}
			}
		}
		return instance;
	}
	
	/**
	 * 获取用户信息
	 */
	public static UserInfo getCookies() {
		return getInstance().get();
	}
	
	/**
	 * 是否登录
	 */
	public static boolean isLogin() {
		UserInfo cookies = getInstance().get();
		if (cookies == null) {
			return false;
		}
		if (StringUtil.isEmpty(cookies.getPassword())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 清空Md5
	 */
	public static void clear() {
		UserInfo cookies = MyApplication.getUser();
		if (cookies != null) {
			cookies.setPassword("");
		}
		getInstance().save(cookies);
	}
	
	
	
	public UserInfo get() {
		if (c == null) {
			c = new Gson().fromJson(getValue(KEY_USER, ""), UserInfo.class);
		}
		return c;
	}
	

	public void save(UserInfo cookies) {
		this.c = cookies; // 内存
		setValue(KEY_USER, new Gson().toJson(cookies)); // sp
	}
	
	
	
}
