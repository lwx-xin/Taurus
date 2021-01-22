package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.service.SMenuService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/menu")
public class MenuController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SMenuService menuService;

	@ApiOperation(value = "获取菜单列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result<List<SMenuEntityEx>> getMenu(HttpSession session, HttpServletResponse response) {

		List<SMenuEntityEx> menuList = menuService.getMenuList();

		return new Result<List<SMenuEntityEx>>(menuList, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 根据id获取菜单信息
	 */
	@ApiOperation(value = "根据id获取菜单信息")
	@RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
	public Result<SMenuEntityEx> getById(@PathVariable("menuId") String menuId) {

		LoggerUtil.printParam(logger, "menuId", menuId);

		SMenuEntityEx data = menuService.getMenuDetail(menuId);
		if (data == null) {
			return new Result<SMenuEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_2);
		}
		return new Result<SMenuEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 添加菜单信息
	 */
	@ApiOperation(value = "添加菜单信息")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<SMenuEntityEx> insert(SMenuEntityEx menuEntityEx, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "menuEntityEx", menuEntityEx);

		SMenuEntityEx data = menuService.insert(menuEntityEx, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SMenuEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_3);
		}
		return new Result<SMenuEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 修改菜单信息
	 */
	@ApiOperation(value = "修改菜单信息")
	@RequestMapping(value = "/{menuId}", method = RequestMethod.PUT)
	public Result<SMenuEntityEx> update(@PathVariable("menuId") String menuId, SMenuEntityEx menuEntityEx,
			HttpServletRequest request, HttpServletResponse response) {

		LoggerUtil.printParam(logger, "menuId", menuId);
		LoggerUtil.printParam(logger, "menuEntityEx", menuEntityEx);

		SMenuEntityEx data = menuService.update(menuId, menuEntityEx, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SMenuEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_4);
		}

		return new Result<SMenuEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 删除菜单信息
	 */
	@ApiOperation(value = "删除菜单信息")
	@RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
	public Result<SMenuEntityEx> delete(@PathVariable("menuId") String menuId, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "menuId", menuId);

		menuService.lock_unLock(menuId, SessionUtil.getUserId(request));

		return new Result<SMenuEntityEx>(null, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
