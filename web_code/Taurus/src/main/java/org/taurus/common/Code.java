package org.taurus.common;

public enum Code {

	/** 权限过滤器-验证成功 */
	AUTH_FILTER_SUCCESS("auth_filter", "", ""),
	/** 权限过滤器-未登录 */
	AUTH_FILTER_NO_LOGIN("auth_filter", "请先登录后在访问", "/html/login.html"),
	/** 权限过滤器-令牌过期 */
	AUTH_FILTER_TOKEN_EXPIRED("auth_filter", "请重新登录", "/html/login.html"),
	/** 权限过滤器-令牌验证失败 */
	AUTH_FILTER_TOKEN_EXCEPTION("auth_filter", "请重新登录", "/html/login.html"),
	/** 权限过滤器-缺少令牌 */
	AUTH_FILTER_NO_TOKEN("no_token", "缺少令牌", "/html/login.html"),
	/** 权限过滤器-权限不足 */
	AUTH_FILTER_NO_AUTH("auth_filter", "权限不足", "/html/system/no-auth.html"),
	/** 权限过滤器-未知的请求 */
	AUTH_FILTER_UNKNOWN_URL("auth_filter", "未知的请求", "/html/system/unknown-url.html"),
	/** 权限过滤器-无需重新登录 */
	AUTH_FILTER_NO_NEED_LOGIN("no_need_login", "无需重新登录", "/html/system/home.html"),

	/** 令牌解析状态-解析成功 */
	TOKEN_SUCCESS("token_verify", "解析成功", "0"),
	/** 令牌解析状态-解析失败 */
	TOKEN_ERR("token_verify", "解析失败", "1"),
	/** 令牌解析状态-令牌过期 */
	TOKEN_OVERDUE("token_verify", "令牌过期", "2"),

	/** 接口返回异常状态码-正常 */
	INTERFACE_ERR_CODE_0("interface_err_code", "", "0"),
	/** 接口返回异常状态码-参数错误 */
	INTERFACE_ERR_CODE_1("interface_err_code", "参数错误", "1"),
	/** 接口返回异常状态码-未查询到数据 */
	INTERFACE_ERR_CODE_2("interface_err_code", "未查询到数据", "2"),
	/** 接口返回异常状态码-添加失败 */
	INTERFACE_ERR_CODE_3("interface_err_code", "添加失败", "3"),
	/** 接口返回异常状态码-编辑失败 */
	INTERFACE_ERR_CODE_4("interface_err_code", "编辑失败", "4"),
	/** 接口返回异常状态码-其他异常 */
	INTERFACE_ERR_CODE_5("interface_err_code", "其他异常", "5"),

	/** 登录状态(pc)-登录成功 */
	LOGIN_WEB_SUCCESS("login_web", "登录成功", "0"),
	/** 登录状态(pc)-登录成功 */
	LOGIN_WEB_ERROR("login_web", "登录失败", "1"),

	/** 删除标志-禁用 */
	DEL_FLG_0("del_flg", "禁用", "0"),
	/** 删除标志-启用 */
	DEL_FLG_1("del_flg", "启用", "1"),

	/** 平台- 全部*/
	PLATFORM_WEB_ALL("platform", "All", "0"),
	/** 平台-Window */
	PLATFORM_WEB_WINDOW("platform", "Window", "1"),
	/** 平台-Mac */
	PLATFORM_APP_MAC("platform", "Mac", "2"),
	/** 平台-Android */
	PLATFORM_APP_ANDROID("platform", "Android", "3"),
	/** 平台-IOS */
	PLATFORM_APP_IOS("platform", "IOS", "4"),

	/** 请求方式-全部 */
	REQUEST_METHOD_ALL("request_method", "all method", "0"),
	/** 请求方式-GET */
	REQUEST_METHOD_GET("request_method", "get", "1"),
	/** 请求方式-POST */
	REQUEST_METHOD_POST("request_method", "post", "2"),
	/** 请求方式-DELETE */
	REQUEST_METHOD_DELETE("request_method", "delete", "3"),
	/** 请求方式-PUT */
	REQUEST_METHOD_PUT("request_method", "put", "4");

	private String group;

	private String name;

	private String value;

	private Code(String group, String name, String value) {
		this.group = group;
		this.name = name;
		this.value = value;
	}

	/**
	 * 获取code分组
	 * 
	 * @return
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * 获取code名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取code值
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

}
