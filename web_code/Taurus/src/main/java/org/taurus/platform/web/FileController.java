package org.taurus.platform.web;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.taurus.extendEntity.SFolderEntityEx;
import org.taurus.service.SFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("web/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SFileService fileService;

    /**
     * 获取文件列表
     */
    @ApiOperation(value = "获取文件列表")
    @RequestMapping(method = RequestMethod.GET)
    public Result<List<SFileEntityEx>> getList(String fileFolder) {

        LoggerUtil.printParam(logger, "fileFolder", fileFolder);

        List<SFileEntityEx> data = fileService.getList(fileFolder);
        if (data == null) {
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

        if (!fileService.fileNameCheck(folderId, files)) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3.getValue(), "存在同名的文件");
        }

        boolean insert = fileService.insert(folderId, files, SessionUtil.getUserId(request));
        if (!insert) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_3);
        }
        return new Result<>(null, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 修改文件信息
     */
    @ApiOperation(value = "修改文件信息")
    @RequestMapping(value = "/{fileId}", method = RequestMethod.PUT)
    public Result<SFileEntityEx> updateFileName(@PathVariable("fileId") String fileId, SFileEntityEx fileEntityEx,
                                                    HttpServletRequest request) {

        LoggerUtil.printParam(logger, "fileId", fileId);
        LoggerUtil.printParam(logger, "fileEntityEx", fileEntityEx);

        SFileEntityEx data = fileService.updateFileName(fileId, fileEntityEx, SessionUtil.getUserId(request));
        if (data == null) {
            return new Result<>(null, false, CheckCode.INTERFACE_ERR_CODE_4);
        }
        return new Result<>(data, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 删除文件信息
     */
    @ApiOperation(value = "删除文件信息")
    @RequestMapping(value = "/{fileId}", method = RequestMethod.DELETE)
    public Result<SFileEntityEx> delete(@PathVariable("fileId") String fileId, HttpServletRequest request) {

        LoggerUtil.printParam(logger, "fileId", fileId);

        fileService.deleteFile(fileId, SessionUtil.getUserId(request));
        return new Result<>(null, true, CheckCode.INTERFACE_ERR_CODE_0.getValue(), "删除成功");
    }

    /**
     * 获取文本文件内容
     */
    @ApiOperation(value = "获取文本文件内容")
    @RequestMapping(value = "/txt/content/{fileId}", method = RequestMethod.GET)
    public Result<String> getTxtFileContent(@PathVariable("fileId") String fileId, HttpServletRequest request){

        LoggerUtil.printParam(logger, "fileId", fileId);

        String content = fileService.getTxtContent(fileId, SessionUtil.getUserId(request));

        return new Result<>(content, true, CheckCode.INTERFACE_ERR_CODE_0);
    }

    /**
     * 获取图片文件内容
     */
    @ApiOperation(value = "获取图片文件内容")
    @RequestMapping(value = "/image/content/{fileId}", method = RequestMethod.GET)
    public void getImageFileContent(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response){

        LoggerUtil.printParam(logger, "fileId", fileId);

        fileService.getImageContent(fileId, SessionUtil.getUserId(request), response);

    }

    /**
     * 获取视频文件内容
     */
    @ApiOperation(value = "获取视频文件内容")
    @RequestMapping(value = "/video/content/{fileId}", method = RequestMethod.GET)
    public void getVideoFileContent(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response){

        LoggerUtil.printParam(logger, "fileId", fileId);

        fileService.getVideoContent(fileId, SessionUtil.getUserId(request), response, request);

    }
}
