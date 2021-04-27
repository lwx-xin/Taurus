package org.taurus.common.code;

public enum CheckCode {

	/** 权限过滤器-验证成功 */
	AUTH_FILTER_SUCCESS("auth_filter", "", ""),
	/** 权限过滤器-未登录 */
	AUTH_FILTER_NO_LOGIN("auth_filter", "请先登录后在访问", "/html/login.html"),
	/** 权限过滤器-权限不足 */
//	AUTH_FILTER_NO_AUTH("auth_filter", "权限不足", "/html/error/err-noAuth.html"),
	AUTH_FILTER_NO_AUTH("auth_filter", "权限不足", ""),
	/** 权限过滤器-未知的请求 */
//	AUTH_FILTER_UNKNOWN_URL("auth_filter", "未知的请求", "/html/error/err-unknownUrl.html"),
	AUTH_FILTER_UNKNOWN_URL("auth_filter", "未知的请求", ""),
	/** 权限过滤器-无需重新登录 */
	AUTH_FILTER_NO_NEED_LOGIN("auth_filter", "无需重新登录", "/html/error/home.html"),
	/** 权限过滤器-参数异常 */
//	AUTH_FILTER_PARAM_ERR("auth_filter", "参数异常", "/html/error/err-paramsErr.html"),
	AUTH_FILTER_PARAM_ERR("auth_filter", "参数异常", ""),
	/** 权限过滤器-令牌过期 */
	AUTH_FILTER_TOKEN_OVERDUE("auth_filter", "请重新登录", "/html/login.html"),
	/** 权限过滤器-令牌验证失败 */
	AUTH_FILTER_TOKEN_ERR("auth_filter", "请重新登录", "/html/login.html"),
	/** 权限过滤器-缺少令牌 */
	AUTH_FILTER_TOKEN_NULL("auth_filter", "缺少令牌", "/html/login.html"),

	/** 令牌解析状态-解析成功 */
	TOKEN_SUCCESS("token_verify", "解析成功", "0"),
	/** 令牌解析状态-解析失败 */
	TOKEN_ERR("token_verify", "解析失败", "1"),
	/** 令牌解析状态-令牌过期 */
	TOKEN_OVERDUE("token_verify", "令牌过期", "2"),
	/** 令牌解析状态-没有令牌 */
	TOKEN_NULL("token_verify", "没有令牌", "3"),

	/** 用户验证-验证成功 */
	USER_SUCCESS("user_info", "验证成功", "0"),
	/** 用户验证-验证失败 */
	USER_ERR("user_info", "验证失败", "1"),
	/** 用户验证-验证失败-令牌解析失败 */
	USER_ERR_TOKEN_ERR("user_info", "验证失败-令牌解析失败", "2"),
	/** 用户验证-验证失败-令牌过期 */
	USER_ERR_TOKEN_OVERDUE("token_verify", "验证失败-令牌过期", "3"),
	/** 用户验证-验证失败-没有令牌 */
	USER_ERR_TOKEN_NULL("token_verify", "验证失败-没有令牌", "4"),

	/** 接口返回异常状态码-重新登录 */
	INTERFACE_ERR_CODE_reLogin("interface_err_code", "需要重新登录", "-1"),
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
	/** 接口返回异常状态码-删除失败 */
	INTERFACE_ERR_CODE_6("interface_err_code", "删除失败", "6"),

	/** 登录状态(pc)-登录成功 */
	LOGIN_WEB_SUCCESS("login_web", "登录成功", "0"),
	/** 登录状态(pc)-登录失败 */
	LOGIN_WEB_ERROR("login_web", "登录失败", "1");

	private String group;

	private String name;

	private String value;

	private CheckCode(String group, String name, String value) {
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

	/**
	 * 修改code的name
	 * 
	 * @param name
	 * @param checkCode
	 * @return
	 */
	public static CheckCode changeName(String name, CheckCode checkCode) {
		checkCode.name = name;
		return checkCode;
	}
}
