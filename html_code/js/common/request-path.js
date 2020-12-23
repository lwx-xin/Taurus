var requestPath = {
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
	// 编辑信息
	userUpdate:{"url":"/web/user/{userId}", "type":"put"},
	// 删除用户
	userDelete:{"url":"/web/user/{userId}", "type":"delete"},
	
	//权限列表
	authList:{"url":"/web/auth", "type":"get"},
	//权限信息
	authDetail:{"url":"/web/auth/{authId}", "type":"get"}
}