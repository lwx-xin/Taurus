package org.taurus.platform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.RepeatLogin;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.entity.SFolderEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.extendEntity.SFolderEntityEx;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.service.SFolderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/folder")
public class FolderController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SFolderService folderService;

    /**
     * 获取文件夹下的文件以及文件夹
     */
    @ApiOperation(value = "获取文件夹下的文件以及文件夹")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<SFolderEntityEx> getList(SFolderEntityEx folderEntityEx, String[] filter_fileType, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "folderEntityEx", folderEntityEx);
        LoggerUtil.printParam(logger, "filter_fileType", JsonUtil.toJson(filter_fileType));

        String userId = SessionUtil.getUserId(request);

        Map<String, Object> filter = new HashMap<>();
        filter.put("fileType", filter_fileType);

        SFolderEntityEx data = folderService.getFolderDetail(userId,filter, folderEntityEx.getFolderId());
        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_2);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 获取文件夹列表--树状结构(不包含文件)
     */
    @ApiOperation(value = "获取文件夹列表--树状结构(不包含文件)")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Result<List<SFolderEntityEx>> getTreeList(SFolderEntityEx folderEntityEx, HttpSession session) {

        LoggerUtil.printParam(logger, "folderEntityEx", folderEntityEx);

        String userId = SessionUtil.getUserId(session);

        List<SFolderEntityEx> data = folderService.getFolderTreeByUser(userId);
        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_2);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 添加文件夹
     */
    @ApiOperation(value = "添加文件夹")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<SFolderEntity> insert(SFolderEntity folderEntity, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "folderEntity", folderEntity);

        String userId = SessionUtil.getUserId(request);
        String folderName = folderEntity.getFolderName();
        String folderParent = folderEntity.getFolderParent();

        String folderId = folderService.createFolder(folderName, folderParent, userId, userId);
        SFolderEntity data = folderService.getById(folderId);
        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 获取文件夹的详细信息
     */
    @ApiOperation(value = "获取文件夹的详细信息")
    @RequestMapping(value = "/{folderId}", method = RequestMethod.GET)
    public Result<SFolderEntity> getById(@PathVariable("folderId") String folderId, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "folderId", folderId);

        String userId = SessionUtil.getUserId(request);

        SFolderEntityEx data = folderService.getFolderInfo(folderId, userId);

        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_2);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 修改文件夹信息
     */
    @ApiOperation(value = "修改文件夹信息")
    @RequestMapping(value = "/{folderId}", method = RequestMethod.PUT)
    public Result<SFolderEntityEx> updateFolderName(@PathVariable("folderId") String folderId, SFolderEntityEx folderEntityEx,
                                                    HttpServletRequest request) {

        LoggerUtil.printParam(logger, "folderId", folderId);
        LoggerUtil.printParam(logger, "folderEntityEx", folderEntityEx);

        SFolderEntityEx data = folderService.updateFolderName(folderId, folderEntityEx, SessionUtil.getUserId(request));
        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_4);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 修改文件夹信息
     */
    @ApiOperation(value = "修改文件夹信息")
    @RequestMapping(value = "/{folderId}", method = RequestMethod.DELETE)
    public Result<SFolderEntityEx> delete(@PathVariable("folderId") String folderId, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "folderId", folderId);

        boolean b = folderService.deleteFolder(folderId, SessionUtil.getUserId(request));
        if (b) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_6);
        }
        return new Result<>(null, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

}
