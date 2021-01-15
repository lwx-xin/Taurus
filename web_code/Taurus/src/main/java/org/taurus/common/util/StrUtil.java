package org.taurus.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class StrUtil {

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str) || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null || "".equals(obj) || "".equals(format(obj).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断字符串是否相等
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean equals(String a, String b) {
		if (a == null && b == null) {
			return true;
		} else if (a == null || b == null) {
			return false;
		} else {
			return a.equals(b);
		}
	}

	/**
	 * 转换成String
	 * 
	 * @param strObject
	 * @return
	 */
	public static String format(Object strObject) {
		if (strObject != null) {
			return strObject.toString();
		}
		return "";
	}

	/**
	 * 转换编码格式，前端使用toUTF8（）还原编码
	 * 
	 * @param str
	 * @return
	 */
	public static String toUTF8(String str) {
		if (isEmpty(str)) {
			return "";
		}
		str = str.replaceAll("[\\t\\n\\r]", "");
		try {
			return URLEncoder.encode(toAscii(str), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			return "";
		}
	}

	/**
	 * 转换编码格式，前端使用fromCharCode()还原编码
	 * 
	 * @param str
	 * @return
	 */
	public static String toAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}
}
