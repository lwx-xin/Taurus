package org.taurus.common.util;

import org.taurus.common.code.DataType;

/**
 * 字符串验证
 * 
 * @author Lu Wenxin
 *
 */
public class ValidateUtil {

	/**
	 * 验证邮箱格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean email(String str) {
		boolean b = false;
		if (str != null && !str.equals("") && !str.trim().equals("")) {
			String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			return str.matches(regex);
		}
		return b;
	}

	/**
	 * 验证电话格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean phone(String str) {
		boolean b = false;
		if (str != null && !str.equals("") && !str.trim().equals("")) {
			String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
			return str.matches(regex);
		}
		return b;
	}

	/**
	 * 验证请求格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean url(String str) {
		boolean b = false;
		if (str != null && !str.equals("") && !str.trim().equals("")) {
			String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
			return str.matches(regex);
		}
		return b;
	}

	/**
	 * 验证整数格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean numberInt(String str, DataType dataType) {
		boolean b = false;
		if (str != null && !str.equals("") && !str.trim().equals("")) {
			String regex = "";

			if (DataType.INT_1.equals(dataType)) {
				regex = "^\\d+$";// 非负整数（>=0）
			} else if (DataType.INT_2.equals(dataType)) {
				regex = "^[0-9]*[1-9][0-9]*$";// 正整数(>0)
			} else if (DataType.INT_3.equals(dataType)) {
				regex = "^((-\\d+)|(0+))$";// 非正整数（<=0）
			} else if (DataType.INT_4.equals(dataType)) {
				regex = "^-[0-9]*[1-9][0-9]*$";// 负整数(<0)
			} else if (DataType.INT_5.equals(dataType)) {
				regex = "^-?\\d+$";// 整数
			}

			if (!regex.equals("")) {
				return str.matches(regex);
			}
		}
		return b;
	}

	public static boolean iDCard(String str) {
		boolean b = false;
		if (str != null && !str.equals("") && !str.trim().equals("")) {
			String regex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
					+ "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
			if (str.matches(regex)) {
				if (str.length() == 18) {
					try {
						char[] charArray = str.toCharArray();
						// 前十七位加权因子
						int[] idCardWi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
						// 这是除以11后，可能产生的11位余数对应的验证码
						String[] idCardY = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
						int sum = 0;
						for (int i = 0; i < idCardWi.length; i++) {
							int current = Integer.parseInt(String.valueOf(charArray[i]));
							int count = current * idCardWi[i];
							sum += count;
						}
						char idCardLast = charArray[17];
						int idCardMod = sum % 11;
						if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
							return true;
						} else {
							return false;
						}

					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return b;
	}

}