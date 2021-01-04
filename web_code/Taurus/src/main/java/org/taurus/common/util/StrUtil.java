package org.taurus.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.taurus.common.code.Code;

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
	 * 转换编码格式，前端使用decodeURI()还原编码
	 * 
	 * @param str
	 * @return
	 */
	public static String toUTF8(String str) {
		if (isEmpty(str)) {
			return "";
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			return "";
		}
	}

	/**
	 * 获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		String type = Code.FILE_TYPE_OTHER.getValue();
		// aa 后缀 - 小写
		String extension = FilenameUtils.getExtension(fileName).toLowerCase();
		if (isNotEmpty(extension)) {
			switch (extension) {
			case "mp3":
				type = Code.FILE_TYPE_AUDIO.getValue();
				break;
			case "mp4":
				type = Code.FILE_TYPE_VIDEO.getValue();
				break;
			case "text":
			case "txt":
				type = Code.FILE_TYPE_TXT.getValue();
				break;
			case "log":
				type = Code.FILE_TYPE_LOG.getValue();
				break;
			case "png":
			case "jpg":
				type = Code.FILE_TYPE_PICTURE.getValue();
				break;
			default:
				type = Code.FILE_TYPE_OTHER.getValue();
				break;
			}
		}
		return type;
	}
}
