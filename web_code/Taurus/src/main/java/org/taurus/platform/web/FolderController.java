package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.extendEntity.SFolderEntityEx;
import org.taurus.service.SFolderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/folder")
public class FolderController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SFolderService folderService;

	/**
	 * 获取文件夹列表
	 */
	@ApiOperation(value = "获取文件夹列表")
	@RequestMapping(method = RequestMethod.GET)
	public Result<List<SFolderEntityEx>> getList(SFolderEntityEx folderEntityEx, HttpSession session) {

		LoggerUtil.printParam(logger, "folderEntityEx", folderEntityEx);

		String userId = SessionUtil.getUserId(session);

		List<SFolderEntityEx> data = folderService.getFolderTreeByUser(userId);

		return new Result<List<SFolderEntityEx>>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
	}

}
