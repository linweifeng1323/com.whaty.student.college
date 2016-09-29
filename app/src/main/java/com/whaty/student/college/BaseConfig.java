package com.whaty.student.college;

import android.os.Environment;

public class BaseConfig {
	
//	public static String BASE_URL = "http://suining.flipclass.webtrn.cn/flipclass_sdk";
//	public static String BASE_URL = "http://192.168.23.66:8080";
//	public static String BASE_URL = "http://192.168.23.99:8080";
//	public static String BASE_URL = "http://192.168.23.25:8080";
	public static String BASE_URL = ".fzcollege.com/flipclass_sdk";
//	public static String BASE_URL = "http://192.168.23.146:80/flipclasssdk";
//	public static String BASE_URL = "dygz.fzcollege.com/flipclass_sdk";
	public static String UPGRADE_URL = "http://fzcollege.com";
//	public static String UPGRADE_URL = "http://192.168.23.146:18090";
//	public static String UPGRADE_URL = "http://192.168.23.146:18090/flipclasssdk";
//  public static String BASE_URL = "http://192.168.23.58:8080";
    public final static String SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/upgrage/download/";
	public static final String UPGRADE_INI = "upgrade.ini";
	
	public static String XMPP_URL = "http://125.208.27.151:9090/plugins/httpAPI";
//	public static String XMPP_URL = "http://192.168.23.144:9090/plugins/httpAPI";
//	public static String XMPP_URL = "http://192.168.23.25:9090/plugins/httpAPI";
	
	public static String SERVER = "125.208.27.151";
//	public static String SERVER = "192.168.23.144";
//	public static String SERVER = "192.168.23.25";
}
