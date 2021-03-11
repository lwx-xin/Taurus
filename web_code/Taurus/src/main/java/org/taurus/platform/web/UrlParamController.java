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
import org.taurus.entity.SUrlParamEntity;
import org.taurus.service.SUrlParamService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/url-param")
public class UrlParamController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SUrlParamService urlParamService;

	/**
	 * 获取请求参数列表
	 */
	@ApiOperation(value = " 获取请求参数列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result<List<SUrlParamEntity>> getList(SUrlParamEntity urlParamEntity) {

		LoggerUtil.printParam(logger, "urlParamEntity", urlParamEntity);

		List<SUrlParamEntity> data = urlParamService.getUrlParamList(urlParamEntity);
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 根据id获取请求参数信息
	 */
	@ApiOperation(value = "根据id获取请求参数信息")
	@RequestMapping(value = "/{urlParamId}", method = RequestMethod.GET)
	public Result<SUrlParamEntity> getById(@PathVariable("urlParamId") String urlParamId) {

		LoggerUtil.printParam(logger, "urlParamId", urlParamId);

		SUrlParamEntity data = urlParamService.getURLParamDetail(urlParamId);
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 添加请求参数信息
	 */
	@ApiOperation(value = "添加请求参数信息")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<SUrlParamEntity> insert(SUrlParamEntity urlParamEntity, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "urlParamEntity", urlParamEntity);

		SUrlParamEntity data = urlParamService.insert(urlParamEntity, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3);
		}
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 修改请求参数信息
	 */
	@ApiOperation(value = "修改请求参数信息")
	@RequestMapping(value = "/{urlParamId}", method = RequestMethod.PUT)
	public Result<SUrlParamEntity> update(@PathVariable("urlParamId") String urlParamId, SUrlParamEntity urlParamEntity,
			HttpServletRequest request) {

		LoggerUtil.printParam(logger, "urlParamId", urlParamId);
		LoggerUtil.printParam(logger, "urlParamEntity", urlParamEntity);

		SUrlParamEntity data = urlParamService.update(urlParamId, urlParamEntity, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_4);
		}
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
