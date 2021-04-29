package org.taurus.common.code;

public enum Code {

	/** 删除标志-禁用 */
	DEL_FLG_0("del_flg", "禁用", "0"),
	/** 删除标志-启用 */
	DEL_FLG_1("del_flg", "启用", "1"),

	/** 平台- 全部 */
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
	REQUEST_METHOD_PUT("request_method", "put", "4"),

	/** 否-false */
	NO("yes_no", "否", "0"),
	/** 是-true */
	YES("yes_no", "是", "1"),

	/** 文件类型-其它*/
	FILE_TYPE_OTHER("file_type", "other", "0"),
	/** 文件类型-音频*/
	FILE_TYPE_AUDIO("file_type", "audio", "1"),
	/** 文件类型-视频*/
	FILE_TYPE_VIDEO("file_type", "video", "2"),
	/** 文件类型-文本*/
	FILE_TYPE_TXT("file_type", "txt", "3"),
	/** 文件类型-日志*/
	FILE_TYPE_LOG("file_type", "log", "4"),
	/** 文件类型-图片*/
	FILE_TYPE_PICTURE("file_type", "picture", "5"),

	/** 日志处理状态-待处理*/
	LOG_STATUS_0("log_status","待处理","0"),
	/** 日志处理状态-已处理*/
	LOG_STATUS_1("log_status","已处理","1"),
	/** 日志处理状态-无需处理*/
	LOG_STATUS_2("log_status","无需处理","2"),

	/** 资源类别-系统资源 */
	RESOURCE_TYPE_SYSTEM("resource_type","系统资源","-1"),
	/** 资源类别-用户根目录 */
	RESOURCE_TYPE_ROOT("resource_type","用户根目录","0"),
	/** 资源类别-自定义资源 */
	RESOURCE_TYPE_CUSTOM("resource_type","自定义资源","1");

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
