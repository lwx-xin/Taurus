package org.taurus.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.CommonField;
import org.taurus.common.RepeatLogin;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.CodeElement;
import org.taurus.common.result.Result;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.extendEntity.SUserEntityEx;
import org.taurus.service.CommonService;
import org.taurus.service.SFileService;
import org.taurus.service.SUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping()
public class CommonController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CommonService commonService;

	@Resource
	private SFileService fileService;

	@Resource
	private SUserService userService;

	/**
	 * 获取code列表
	 */
	@ApiOperation(value = "获取权限列表")
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public Result<Map<String, List<CodeElement>>> getCodeList(String codeGroup) {

		LoggerUtil.printParam(logger, "codeGroup", codeGroup);

		List<String> list = JsonUtil.toList(codeGroup, String.class);
		Map<String, List<CodeElement>> code = commonService.code(list);

		return new Result<Map<String, List<CodeElement>>>(code, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 获取登录用户信息
	 */
	@ApiOperation(value = "获取登录用户信息")
	@RequestMapping(value = "/loginUser", method = RequestMethod.GET)
	public Result<SUserEntityEx> getUserInfo(HttpServletRequest request) {

		// aa 用户id
		String userId = SessionUtil.getUserId(request);
		if (StrUtil.isNotEmpty(userId)) {
			SUserEntityEx userDetail = userService.getUserDetail(userId);
			// aa 用户头像文件id
			String userHead = userDetail.getUserHead();
			// aa 用户头像文件请求路径
			userDetail.setHeadFilePath(fileService.getFileUrl(userHead, userId));
			return new Result<SUserEntityEx>(userDetail, true, CheckCode.INTERFACE_ERR_CODE_0);
		}
		return new Result<SUserEntityEx>(null, false, CheckCode.INTERFACE_ERR_CODE_2.getValue(), "未获取到用户信息");
	}

	/**
	 * 修改登录用户信息
	 */
	@ApiOperation(value = "修改录用户信息")
	@RequestMapping(value = "/loginUser", method = RequestMethod.PUT)
	public Result<SUserEntityEx> editUserInfo(HttpServletRequest request, SUserEntityEx userEntityEx,
			MultipartFile files, HttpServletResponse response) {

		// aa 用户id
		String userId = SessionUtil.getUserId(request);

		SUserEntityEx data = userService.update(userId, userEntityEx, files, SessionUtil.getUserId(request));

		response.setHeader(CommonField.SYSTEM_ERR_REDIRECT, StrUtil.toUTF8("/html/login.html"));
		response.setHeader(CommonField.SYSTEM_ERR_MSG, StrUtil.toUTF8(CheckCode.INTERFACE_ERR_CODE_reLogin.getName()));
		return new Result<SUserEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_reLogin);
	}

	/**
	 * 清除session以及cookie
	 */
	@ApiOperation(value = "清除session以及cookie")
	@RequestMapping(value = "/clearLoginInfo", method = RequestMethod.DELETE)
	public Result<SUserEntityEx> clearLoginInfo(HttpServletRequest request, HttpServletResponse response) {

		String userId = SessionUtil.getUserId(request);

		RepeatLogin.removeUser(userId);
		SessionUtil.clearSession(request);
		CookieUtil.clearCookie(response);

		return new Result<SUserEntityEx>(null, false, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 获取ajax参数验证所需的json数据
	 */
	@ApiOperation(value = "获取ajax参数验证所需的json数据")
	@RequestMapping(value = "/getAjaxCheckJson", method = RequestMethod.GET)
	public Result<String> getAjaxCheckJson() {
		String ajaxCheckJson = commonService.getAjaxCheckJson();
		return new Result<String>(ajaxCheckJson, false, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 根据用户获取菜单列表
	 */
	@ApiOperation(value = "根据用户获取菜单列表")
	@RequestMapping(value = "/getMenuByUser", method = RequestMethod.GET)
	public Result<List<SMenuEntityEx>> getMenu(HttpSession session, HttpServletResponse response) {

		String userId = SessionUtil.getUserId(session);
		List<SMenuEntityEx> menuList = commonService.getMenuListByUser(userId);

		return new Result<List<SMenuEntityEx>>(menuList, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
