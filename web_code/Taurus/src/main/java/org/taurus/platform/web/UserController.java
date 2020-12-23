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
import org.taurus.common.Code;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SUserEntityEx;
import org.taurus.service.SUserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SUserService sUserService;

	/**
	 * 获取用户列表
	 */
	@ApiOperation(value = "获取用户列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result<List<SUserEntityEx>> getList(SUserEntity userEntity) {

		LoggerUtil.printParam(logger, "userEntity", userEntity);

		List<SUserEntityEx> data = sUserService.getUserList(userEntity);
		return new Result<List<SUserEntityEx>>(data, true, Code.INTERFACE_ERR_CODE_0);
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
			return new Result<SUserEntityEx>(data, false, Code.INTERFACE_ERR_CODE_2);
		}
		return new Result<SUserEntityEx>(data, true, Code.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 添加用户信息
	 */
	@ApiOperation(value = "添加用户信息")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<SUserEntityEx> insert(SUserEntityEx userEntityEx, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "userEntityEx", userEntityEx);

		SUserEntityEx data = sUserService.insert(userEntityEx, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SUserEntityEx>(data, false, Code.INTERFACE_ERR_CODE_3);
		}
		return new Result<SUserEntityEx>(data, true, Code.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 修改用户信息
	 */
	@ApiOperation(value = "修改用户信息")
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public Result<SUserEntityEx> update(@PathVariable("userId") String userId, SUserEntityEx userEntityEx,
			HttpServletRequest request) {

		LoggerUtil.printParam(logger, "userId", userId);
		LoggerUtil.printParam(logger, "userEntityEx", userEntityEx);

		SUserEntityEx data = sUserService.update(userId, userEntityEx, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<SUserEntityEx>(data, false, Code.INTERFACE_ERR_CODE_4);
		}
		return new Result<SUserEntityEx>(data, true, Code.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 删除用户信息-逻辑删除
	 */
	@ApiOperation(value = "删除用户信息-逻辑删除")
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public Result<SUserEntityEx> delete(@PathVariable("userId") String userId, SUserEntityEx userEntityEx,
			HttpServletRequest request) {

		LoggerUtil.printParam(logger, "userId", userId);
		LoggerUtil.printParam(logger, "userEntityEx", userEntityEx);
		
		sUserService.delete(userId, SessionUtil.getUserId(request));
		
		return new Result<SUserEntityEx>(null, true, Code.INTERFACE_ERR_CODE_0);
	}
	
	

}
