package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.taurus.entity.SAuthEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.service.SAuthService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SAuthService authService;

	/**
	 * 获取权限列表
	 */
	@ApiOperation(value = "获取权限列表")
	@RequestMapping(method = RequestMethod.GET)
	public Result<List<SAuthEntityEx>> getList(SAuthEntity authEntity) {

		LoggerUtil.printParam(logger, "authEntity", authEntity);
		List<SAuthEntityEx> data = authService.getAuthList(authEntity);

		return new Result<List<SAuthEntityEx>>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 根据id获取权限信息
	 */
	@ApiOperation(value = " 根据id获取权限信息")
	@RequestMapping(value = "/{authId}", method = RequestMethod.GET)
	public Result<SAuthEntity> getById(@PathVariable("authId") String authId) {

		LoggerUtil.printParam(logger, "authId", authId);
		SAuthEntityEx data = authService.getAuthDetail(authId);

		return new Result<SAuthEntity>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 添加权限信息
	 */
	@ApiOperation(value = "添加权限信息")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<SAuthEntityEx> insert(SAuthEntityEx authEntity, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "authEntity", authEntity);

		SAuthEntityEx data = authService.insert(authEntity, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SAuthEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_3);
		}
		return new Result<SAuthEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 修改权限信息
	 */
	@ApiOperation(value = "修改权限信息")
	@RequestMapping(value = "/{authId}", method = RequestMethod.PUT)
	public Result<SAuthEntityEx> update(@PathVariable("authId") String authId, SAuthEntityEx authEntity, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "authId", authId);
		LoggerUtil.printParam(logger, "authEntity", authEntity);

		SAuthEntityEx data = authService.update(authId, authEntity, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SAuthEntityEx>(data, false, CheckCode.INTERFACE_ERR_CODE_4);
		}
		return new Result<SAuthEntityEx>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
