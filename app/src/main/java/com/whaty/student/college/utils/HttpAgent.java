package com.whaty.student.college.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.google.gson.Gson;
import com.whaty.student.college.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;


/**
 * HTTP 请求工具类
 */
@SuppressLint("NewApi")
public class HttpAgent {

	private final static String TAG = HttpAgent.class.getSimpleName();

	private final static Gson gson = new Gson();

	/**
	 * 获取Get方式的URl地址
	 * 
	 * @param url
	 * @param paraMap
	 * @return
	 */
	public static String getUrl(String url, Map<String, Object> paraMap) {
		if (paraMap.size() > 0 && url.indexOf("?") == -1) {
			url += "?";
		}

		StringBuffer sb = new StringBuffer();
		for (Entry<String, Object> ie : paraMap.entrySet()) {
			sb.append(ie.getKey()).append("=").append(ie.getValue())
					.append("&");
		}

		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		if (!url.contains(":80")) {
			if (!url.contains("sys/login") && !url.contains("sys/checkdomainIsExist")) {
				url = MyApplication.getUser().getDomianName() + url;
			}
		}
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		return url + sb;
	}

	public static String getUrl(String url) {
		if (!url.contains(":80")) {
			if (!url.contains("sys/login") && !url.contains("sys/checkdomainIsExist")) {
				url = MyApplication.getUser().getDomianName() + url;
			}
		}
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		return url;
	}

	/**
	 * 通用请求
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static String queryData(String url) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("loginToken", MyApplication.getUser().getLoginToken());
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			// out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 把服务器的Json对象，变成Gson对象，存在data标签
	 * 
	 * @param ret
	 * @return
	 */
	public String getObjectGsonHasData(String ret) {

		String start_flag = ",\"object\":[{\"";
		String end_flag = "}],\"data\":";

		int index = ret.indexOf(start_flag);
		if (ret.length() > 0 && index != -1) {
			int lastIndex = ret.indexOf(end_flag, index);
			if (lastIndex == -1) {
				return null;
			}
			return ret
					.substring(index + start_flag.length() - 3, lastIndex + 2);
		}
		return null;
	}

	public static Gson getGson() {
		return gson;
	}

	// 获取版本信息
	public static PackageInfo getVerCode(Context con) {
		PackageManager packageManager = con.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo;
	}
}