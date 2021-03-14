package org.taurus.platform.web;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.entity.SFileEntity;
import org.taurus.extendEntity.SFileEntityEx;
import org.taurus.service.SFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("web/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SFileService fileService;

    /**
     * 获取文件列表
     */
    @ApiOperation(value = "获取文件列表")
    @RequestMapping(method = RequestMethod.GET)
    public Result<List<SFileEntityEx>> getList(String fileFolder) {

        LoggerUtil.printParam(logger, "fileFolder", fileFolder);

        List<SFileEntityEx> data = fileService.getList(fileFolder);
        if (data==null){
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_2);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 根据id获取文件信息
     */
    @ApiOperation(value = " 根据id获取文件信息")
    @RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
    public Result<SFileEntityEx> getById(@PathVariable("fileId") String fileId, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "fileId", fileId);
        SFileEntityEx data = fileService.getFileDetail(fileId, SessionUtil.getUserId(request));

        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 添加文件,返回添加后的文件列表
     */
    @ApiOperation(value = "添加文件")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<SFileEntity> insert(String folderId, MultipartFile[] files, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "folderId", folderId);

        boolean insert = fileService.insert(folderId, files, SessionUtil.getUserId(request));
        if (!insert) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3);
        }
        return new Result<>(null, true, CheckCode.INTERFACE_ERR_CODE_0);
    }
}
