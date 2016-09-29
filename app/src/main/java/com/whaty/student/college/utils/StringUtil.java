package com.whaty.student.college.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Html;
import android.text.Spanned;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 字符串工具类
 * 
 * @version
 */
public class StringUtil {
	public static final String EMPTY = "";

	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return true=空
	 */
	public static boolean isEmpty(String str) {
		return str == null || EMPTY.equals(str)
				|| str.trim().compareTo(EMPTY) == 0 || str.equals("null");
	}
	
	public static boolean isEmpty1(String str) {
		return str == null || EMPTY.equals(str)
				|| str.trim().compareTo(EMPTY) == 0;
	}

	/**
	 * 是否不为空
	 * 
	 * @param str
	 * @return true=非空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}



	/**
	 * 偶数位红色
	 * 
	 * @param strings
	 * @return
	 */
	public static Spanned getHtml4Red(String... strings) {
		return getHtml4Color("#e51c23", strings);
	}

	/**
	 * HTML颜色字符串
	 * 
	 * @param color
	 *            颜色值
	 * @param strings
	 *            偶数变颜色
	 * @return 字符串
	 */
	public static Spanned getHtml4Color(String color, String... strings) {
		String startTag = "<font color=\"" + color + "\">";
		String endTag = "</font>";

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			if (i % 2 != 0) {
				sb.append(startTag).append(strings[i]).append(endTag);
			} else {
				sb.append(strings[i]);
			}
		}
		return Html.fromHtml(sb.toString());
	}

	/**
	 * HTML处理
	 * 
	 * @param pHTMLString
	 * @return
	 */
	public static String stripTags(final String pHTMLString) {
		String result = pHTMLString;
		result = result.replaceAll("\\<.*?>|&nbsp;|&lt;|&gt;|&quot;|&amp;|\n",
				"");
		result = result.replaceAll("null", "\"\"");
		return result;
	}

	public static String fomatHTML(final String pHTMLString) {
		String result = pHTMLString;
		result = result.replaceAll("\\<.*?>|&nbsp;|&lt;|&gt;|&quot;|&amp;",
				"");
		result = result.replaceAll("null", "\"\"");
		return result;
	}

	/**
	 * 对象显示为字段字符串
	 * 
	 * @param obj
	 *            对象
	 * @return 字段字符串
	 */
	public static String toString(Object obj) {
		Class c = obj.getClass();
		String r = c.getName();

		// inspect the fields of this class and all superclasses
		do {
			r += "[";
			Field[] fields = c.getDeclaredFields();
			AccessibleObject.setAccessible(fields, true);
			// get the names and values of all fields
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				r += f.getName() + "=";
				try {
					Object val = f.get(obj);
					r += val.toString();
				} catch (Exception e) {
					continue;
				}
				if (i < fields.length - 1)
					r += ",";
			}
			r += "]";
			c = c.getSuperclass();
		} while (c instanceof Class);
		return r;
	}

	/**
	 * 编码
	 */
	public static String encode(String input, String charset) {
		if (isEmpty(input)) {
			return input;
		}
		try {
			return URLEncoder.encode(input, charset);
		} catch (UnsupportedEncodingException e) {
			// Log.e(e);
		}
		return input;
	}

	/**
	 * 解码
	 */
	public static String decode(String input, String coding) {
		if (isEmpty(input))
			return input;
		try {
			return URLDecoder.decode(input, coding);
		} catch (UnsupportedEncodingException e) {
		}
		return input;
	}

	  public static boolean isTablet(Context context) {
	        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	   }
}