const requestPath = {
	// 获取登录用户
	loginUser:{"url":"/loginUser", "type":"get"},
	// 修改登录用户
	loginUserUpdate:{"url":"/loginUser", "type":"put"},
	
	// 获取code列表
	code:{"url":"/code", "type":"get"},
	// 清除session以及cookie
	clearLoginInfo:{"url":"/clearLoginInfo", "type":"delete"},
	// 获取ajax参数验证所需的json数据
	getAjaxCheckJson:{"url":"/getAjaxCheckJson", "type":"get"},
	// 根据用户获取菜单列表(首页菜单)
	getMenuByUser:{"url":"/getMenuByUser", "type":"get"},
	
	// 登录
	login:{"url":"/web/login/login", "type":"post"},
	// 退出登录
	logout:{"url":"/web/login/logout", "type":"post"},
	
	// 获取菜单列表
	menuList:{"url":"/web/menu", "type":"get"},
	// 添加菜单
	menuInsert:{"url":"/web/menu", "type":"post"},
	// 菜单详细信息
	menuDetail:{"url":"/web/menu/{menuId}", "type":"get"},
	// 编辑菜单信息
	menuUpdate:{"url":"/web/menu/{menuId}", "type":"put"},
	// 禁用启用-菜单
	menurDelete:{"url":"/web/menu/{menuId}", "type":"delete"},
	
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
	// 添加请求参数
	urlParamInsert:{"url":"/web/url-param", "type":"post"},
	// 请求参数详细信息
	urlParamDetail:{"url":"/web/url-param/{urlParamId}", "type":"get"},
	// 编辑请求参数信息
	urlParamUpdate:{"url":"/web/url-param/{urlParamId}", "type":"put"},
	// 禁用启用-请求参数
	urlParamDelete:{"url":"/web/url-param/{urlParamId}", "type":"delete"},
}
$.deepFreeze(requestPath);