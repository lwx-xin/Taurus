package org.taurus.common.util;

import org.springframework.util.DigestUtils;

public class MD5Util {

	//默认的固定盐值   
	public static final String saltSource="taurus";
	
	/**
	  *  生成md5
	 * @param str 要加密的字符串
	 * @param salt 进行加密的salt
	 * @return
	 */
	public static String getMD5(String str, String salt) {
		String base = str + "/" + salt;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}
	
	public static void main(String[] args) {
		System.err.println(getMD5("test", "test"));//26e01b5d0687d131fb288e28021f0453
	}

}
