package org.taurus.common;

/**
 * 通用字段名
 * 
 * @author 祈
 *
 */
public class CommonField {

	/**
	 * 系统异常提示信息<br/>
	 * 账号错误，权限不够 等等
	 */
	public static final String SYSTEM_ERR_MSG = "systemErrMsg";

	/**
	 * 系统异常需要重定向的地址
	 */
	public static final String SYSTEM_ERR_REDIRECT = "systemErrRedirect";

	/**
	 * 导致系统异常的请求地址
	 */
	public static final String SYSTEM_ERR_SOURCE_PATH = "systemErrSourcePath";

	/**
	 * 用户信息<br/>
	 * session的属性名称<br/>
	 * cookie的属性名称
	 */
	public static final String SYSTEM_USER_INFO = "systemUserInfo";

}
