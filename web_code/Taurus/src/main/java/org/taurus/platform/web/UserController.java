package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.CommonField;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SUserEntityEx;
import org.taurus.service.SFileService;
import org.taurus.service.SUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SUserService sUserService;
	
	@Resource
	private SFileService fileService;

	/**
	 * 获取用户列表
	 */
	@ApiOperation(value = "获取用户列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result<List<SUserEntityEx>> getList(SUserEntity userEntity) {

		LoggerUtil.printParam(logger, "userEntity", userEntity);

		List<SUserEntityEx> data = sUserService.getUserList(userEntity);
		return new Result<List<SUserEntityEx>>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 根据id获取用户信息
	 */
	@ApiOperation(value = "根据id获取用户信息")
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public Result<SUserEntityEx> getById(@PathVariable("userId") String userId) {

		LoggerUtil.printParam(logger, "userId", userId);

		SUserEntityEx data = sUserService.getUserDetail(userId);
		if (data == null) {
			return new Result<SUserEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_2);
		}
		// aa 用户头像文件id
		String userHead = data.getUserHead();
		// aa 用户头像文件请求路径
		data.setHeadFilePath(fileService.getFileUrl(userHead, userId));
		return new Result<SUserEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 添加用户信息
	 */
	@ApiOperation(value = "添加用户信息")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<SUserEntityEx> insert(SUserEntityEx userEntityEx, HttpServletRequest request, MultipartFile files) {

		LoggerUtil.printParam(logger, "userEntityEx", userEntityEx);

		SUserEntityEx data = sUserService.insert(userEntityEx, files, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SUserEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_3);
		}
		return new Result<SUserEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 修改用户信息
	 */
	@ApiOperation(value = "修改用户信息")
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public Result<SUserEntityEx> update(@PathVariable("userId") String userId, SUserEntityEx userEntityEx,
			MultipartFile files, HttpServletRequest request, HttpServletResponse response) {

		LoggerUtil.printParam(logger, "userId", userId);
		LoggerUtil.printParam(logger, "userEntityEx", userEntityEx);

		SUserEntityEx data = sUserService.update(userId, userEntityEx, files, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SUserEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_4);
		}

		if (SessionUtil.getUserId(request).equals(userId)) {
			response.setHeader(CommonField.SYSTEM_ERR_REDIRECT, StrUtil.toUTF8("/html/login.html"));
			response.setHeader(CommonField.SYSTEM_ERR_MSG,
					StrUtil.toUTF8(CheckCode.INTERFACE_ERR_CODE_reLogin.getName()));
			return new Result<SUserEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_reLogin);
		}

		return new Result<SUserEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 禁用启用-用户账号
	 */
	@ApiOperation(value = "禁用启用-用户账号")
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public Result<SUserEntityEx> lock_unLock(@PathVariable("userId") String userId, SUserEntityEx userEntityEx,
			HttpServletRequest request, HttpServletResponse response) {

		LoggerUtil.printParam(logger, "userId", userId);
		LoggerUtil.printParam(logger, "userEntityEx", userEntityEx);

		sUserService.lock_unLock(userId, SessionUtil.getUserId(request));

		if (SessionUtil.getUserId(request).equals(userId)) {
			response.setHeader(CommonField.SYSTEM_ERR_REDIRECT, StrUtil.toUTF8("/html/login.html"));
			response.setHeader(CommonField.SYSTEM_ERR_MSG,
					StrUtil.toUTF8(CheckCode.INTERFACE_ERR_CODE_reLogin.getName()));
			return new Result<SUserEntityEx>(null, true, CheckCode.INTERFACE_ERR_CODE_reLogin);
		}

		return new Result<SUserEntityEx>(null, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
