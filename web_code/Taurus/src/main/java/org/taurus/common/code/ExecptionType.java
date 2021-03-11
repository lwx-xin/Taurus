package org.taurus.common.code;

public enum ExecptionType {
	FILE("文件模块"),
	FOLDER("文件夹模块"),
	USER("用户模块"),
	URL("请求模块"),
	AUTH("权限模块"),
	TOKEN("令牌模块"),
	MENU("菜单模块"),
	LOGIN("登录模块"),
	USER_AUTH("用户权限模块"),
	URL_AUTH("请求权限模块"),
	URL_PARAM("请求参数模块"),
	SYSTEM("系统"),
	SYSTEM_LOG("添加日志文件失败");

	private String moduleName;

	private ExecptionType(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

}
