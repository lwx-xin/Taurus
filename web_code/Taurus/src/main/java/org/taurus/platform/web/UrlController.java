package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.entity.SUrlEntity;
import org.taurus.extendEntity.SUrlEntityEx;
import org.taurus.service.SUrlService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/url")
public class UrlController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SUrlService urlService;

	/**
	 * 获取请求列表
	 */
	@ApiOperation(value = " 获取请求列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result<List<SUrlEntityEx>> getList(SUrlEntity urlEntity) {

		LoggerUtil.printParam(logger, "urlEntity", urlEntity);

		List<SUrlEntityEx> data = urlService.getUrlList(urlEntity);
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 根据id获取请求信息
	 */
	@ApiOperation(value = "根据id获取请求信息")
	@RequestMapping(value = "/{urlId}", method = RequestMethod.GET)
	public Result<SUrlEntityEx> getById(@PathVariable("urlId") String urlId) {

		LoggerUtil.printParam(logger, "urlId", urlId);

		SUrlEntityEx data = urlService.getUrlDetail(urlId);
		if (data == null) {
			return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_2);
		}
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 添加请求信息
	 */
	@ApiOperation(value = "添加请求信息")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<SUrlEntityEx> insert(SUrlEntityEx urlEntityEx, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "urlEntityEx", urlEntityEx);

		SUrlEntityEx data = urlService.insert(urlEntityEx, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3);
		}
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

	/**
	 * 修改请求信息
	 */
	@ApiOperation(value = "修改请求信息")
	@RequestMapping(value = "/{urlId}", method = RequestMethod.PUT)
	public Result<SUrlEntityEx> update(@PathVariable("urlId") String urlId, SUrlEntityEx urlEntityEx, HttpServletRequest request) {

		LoggerUtil.printParam(logger, "urlId", urlId);
		LoggerUtil.printParam(logger, "urlEntityEx", urlEntityEx);

		SUrlEntityEx data = urlService.update(urlId, urlEntityEx, SessionUtil.getUserId(request));
		if (data == null) {
			return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_4);
		}
		return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
