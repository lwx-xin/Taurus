package org.taurus.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.RepeatLogin;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.CodeElement;
import org.taurus.common.result.Result;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
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
	 * 获取登录用户
	 */
	@ApiOperation(value = "获取登录用户")
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public Result<SUserEntityEx> getUserInfo(HttpServletRequest request) {

		// aa 用户id
		String userId = SessionUtil.getUserId(request);
		if (StrUtil.isNotEmpty(userId)) {
			SUserEntityEx userDetail = userService.getUserDetail(userId);
			// aa 用户头像文件id
			String userHead = userDetail.getUserHead();
			userDetail.setHeadFilePath(fileService.getFileUrl(userHead, userId));
			return new Result<SUserEntityEx>(userDetail, true, CheckCode.INTERFACE_ERR_CODE_0);
		}
		return new Result<SUserEntityEx>(null, false, CheckCode.INTERFACE_ERR_CODE_5.getValue(), "未获取到用户信息");
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

}
