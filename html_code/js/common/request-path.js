const requestPath = {
	// 获取登录用户
	loginUser:{"url":"/loginUser", "type":"get"},
	// 修改登录用户
	loginUserUpdate:{"url":"/loginUser", "type":"put"},
	
	// 获取code列表
	code:{"url":"/code", "type":"get"},
	// 清除session以及cookie
	clearLoginInfo:{"url":"/clearLoginInfo", "type":"delete"},
	
	// 登录
	login:{"url":"/web/login/login", "type":"post"},
	// 退出登录
	logout:{"url":"/web/login/logout", "type":"post"},
	// 获取菜单列表
	menuList:{"url":"/web/menu", "type":"get"},
	
	// 用户列表
	userList:{"url":"/web/user", "type":"get"},
	// 添加用户
	userInsert:{"url":"/web/user", "type":"post"},
	// 用户详细信息
	userDetail:{"url":"/web/user/{userId}", "type":"get"},
	// 编辑用户信息
	userUpdate:{"url":"/web/user/{userId}", "type":"put"},
	// 禁用启用-用户账号
	userDelete:{"url":"/web/user/{userId}", "type":"delete"},
	
	// 请求列表
	urlList:{"url":"/web/url", "type":"get"},
	// 添加请求
	urlInsert:{"url":"/web/url", "type":"post"},
	// 请求详细信息
	urlDetail:{"url":"/web/url/{urlId}", "type":"get"},
	// 编辑请求信息
	urlUpdate:{"url":"/web/url/{urlId}", "type":"put"},
	// 禁用启用-请求
	urlDelete:{"url":"/web/url/{urlId}", "type":"delete"},
	
	// 权限列表
	authList:{"url":"/web/auth", "type":"get"},
	// 添加权限
	authInsert:{"url":"/web/auth", "type":"post"},
	// 权限详细信息
	authDetail:{"url":"/web/auth/{authId}", "type":"get"},
	// 编辑权限信息
	authUpdate:{"url":"/web/auth/{authId}", "type":"put"},
	// 禁用启用-权限
	authDelete:{"url":"/web/auth/{authId}", "type":"delete"},
	
	// 请求参数列表
	urlParamList:{"url":"/web/url-param", "type":"get"},
}
$.deepFreeze(requestPath);