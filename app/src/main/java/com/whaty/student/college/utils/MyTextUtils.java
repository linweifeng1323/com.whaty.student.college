package com.whaty.student.college.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author yinhailong
 */
public class MyTextUtils {

	public static Charset utf8 = Charset.forName("UTF-8");

	public static Charset utf16 = Charset.forName("UTF-16");

	public static java.text.DecimalFormat dFormat = new java.text.DecimalFormat(
			"0.##");

	private static long seq = 0;

	/**
	 * 1kb字节数
	 */
	public static final long MIN_KB = 1024;

	/**
	 * 1mb字节数
	 */
	public static final long MIN_MB = 1024 * 1024;

	/**
	 * 1gb字节数
	 */
	public static final long MIN_GB = 1024 * 1024 * 1024;

	/**
	 * 通过记录表ID，生成消息ID，唯一 Message id
	 * @return
	 */
	public static String getMessageId(String udn, String localIpAddress) {

		String messageid = localIpAddress + "_" + udn + "_"
				+ generateMessageID() + "_" + System.currentTimeMillis() + "_"
				+ Long.toHexString(seq++);

		return secretMd5HexStr(messageid.getBytes());
	}

	/**
	 * 获取文件大小
	 * 
	 * @param fileSize
	 *            文件字节数
	 * @return
	 */
	public static String getFileSizeFormat(long fileSize) {
		String size = "0B";
		double d = fileSize * 1.0;
		if (d < MIN_KB) {
			size = d + "B";
		} else if (d >= MIN_KB && d < MIN_MB) {
			size = dFormat.format(d * 1.0 / MIN_KB) + "K";
		} else if (d >= MIN_MB && d < MIN_GB) {
			size = dFormat.format(d * 1.0 / MIN_MB) + "M";
		} else {
			size = dFormat.format(d * 1.0 / MIN_GB) + "G";
		}

		return size;
	}
	
	//进度条显示格式转换
	public static String formatSize(long finishedSize, long totalSize) {
		StringBuilder sb = new StringBuilder(50);

		float finished = ((float) finishedSize) / 1024 / 1024;
		if (finished < 1) {
			sb.append(String.format("%1$.2f K / ",
					((float) finishedSize) / 1024));
		} else {
			sb.append((String.format("%1$.2f M / ", finished)));
		}

		float total = ((float) totalSize) / 1024 / 1024;
		if (total < 1) {
			sb.append(String.format("%1$.2f K ", ((float) totalSize) / 1024));
		} else {
			sb.append(String.format("%1$.2f M ", total));
		}
		return sb.toString();
	}
	
	/**
	 * 获取时间大小
	 * 
	 * @return
	 */
	public static String formatTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;
		String strDay = day < 10 ? "0" + day : "" + day; // 天
		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		StringBuffer bf = new StringBuffer();;
		if (day!=0) {
			bf.append(strDay + "天");
		}
		if (hour!=0) {
			bf.append(strHour + "时");
		}
		if (minute!=0) {
			bf.append(strMinute + "分");
		}
		return bf.toString();
	}

	/**
	 * 判断传递的字符串是否为文件的绝对路径
	 * 
	 * @param text
	 * @return
	 */
	public static boolean strIsFilePath(String text) {
		if (text == null || text.trim().length() == 0) {
			return false;
		}

		int index = text.length() - 6;
		String temp = text.substring(index < 0 ? 0 : index);
		if (temp.indexOf(".") != -1) {
			File f = new File(text);
			if (f.isFile()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 或者文件后缀
	 * @return
	 */
	public static String getFileSuffix(String name) {
		int ext = name.lastIndexOf(".");
		if (ext > 0) {
			name = name.substring(ext + 1);
		} else {
			return "";
		}
		return name;
	}

	public static String generateMessageID() {
		UUID id = UUID.randomUUID();
		return Long.toHexString(id.getMostSignificantBits())
				+ Long.toHexString(id.getLeastSignificantBits());
	}

	/**
	 * 把byte数组加密变成长度为16的byte数组
	 * 
	 * @param b
	 * @return
	 */
	public static String secretMd5HexStr(byte[] b) {

		String ret = null;

		byte[] md5 = secretMd5ByteArray(b);

		if (md5 != null) {
			ret = bytesMd5ToHex(md5);
		}

		return ret;
	}

	/**
	 * 16进制数组，长度16
	 * 
	 * @param b
	 * @return
	 */
	private static byte[] secretMd5ByteArray(byte[] b) {

		byte by[] = null;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}

		if (md != null && b != null) {

			md.update(b);
			by = md.digest();
		}

		return by;
	}

	/**
	 * 把加密后长度为16的byte数组，转换成32位16进制的字符串
	 * 
	 * @param by
	 * @return
	 */
	private static String bytesMd5ToHex(byte[] by) {

		String des = "";
		int bl = by.length;
		for (int i = 0; i < bl; i++) {

			String tmp = Integer.toHexString(by[i] & 0xFF);
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}

		return des;
	}

	/**
	 * 是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return null == str || str.trim().length() == 0;
	}

}
