package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.SessionUtil;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.service.SMenuService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/menu")
public class MenuController {

	@Resource
	private SMenuService menuService;

    @ApiOperation(value = "获取菜单列表")
	@RequestMapping(method = RequestMethod.GET)
	public Result<List<SMenuEntityEx>> getMenu(HttpSession session, HttpServletResponse response){
		
		String userId = SessionUtil.getUserId(session);
		List<SMenuEntityEx> menuList = menuService.getMenuListByUser(userId);

		return new Result<List<SMenuEntityEx>>(menuList, true, CheckCode.INTERFACE_ERR_CODE_0);
	}
	
}
