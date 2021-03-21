package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.taurus.common.util.StrUtil;
import org.taurus.entity.SFolderEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.extendEntity.SFolderEntityEx;
import org.taurus.service.SFolderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("web/folder")
public class FolderController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SFolderService folderService;

    /**
     * 获取文件夹下的文件以及文件夹
     */
    @ApiOperation(value = "获取文件夹下的文件以及文件夹")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<SFolderEntityEx> getList(SFolderEntityEx folderEntityEx, HttpSession session) {

        LoggerUtil.printParam(logger, "folderEntityEx", folderEntityEx);

        String userId = SessionUtil.getUserId(session);

        SFolderEntityEx data = folderService.getFolderDetail(userId, folderEntityEx.getFolderId());
        if (data==null){
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
        if (data==null){
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

        String folderId = folderService.createFolder(folderName,folderParent,userId,userId);
        SFolderEntity data = folderService.getById(folderId);
        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

}
