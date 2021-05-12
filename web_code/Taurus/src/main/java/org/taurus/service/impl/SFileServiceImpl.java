package org.taurus.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.taurus.common.util.*;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SFileEntity;
import org.taurus.entity.SFolderEntity;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.dao.SFileDao;
import org.taurus.extendEntity.SFileEntityEx;
import org.taurus.extendEntity.SFolderEntityEx;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件信息 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
@Service
public class SFileServiceImpl extends ServiceImpl<SFileDao, SFileEntity> implements SFileService {

    @Autowired
    private TaurusProperties taurusProperties;

    @Autowired
    private SFolderService folderService;

    @Autowired
    SFileDao fileDao;

    @Override
    @Transactional
    public SFileEntity saveFile(MultipartFile file, String folderId, String operator, String fileOwner) {

        // 文件夹信息
        SFolderEntity folder = folderService.getById(folderId);
        if (folder == null) {
            throw new CustomException(ExecptionType.FOLDER, null, "未知的文件路径");
        }

        // 当前时间
        LocalDateTime nowTime = DateUtil.getLocalDateTime();
        // 文件夹路径
        String folderPath = folderService.getFolderPath(folderId, fileOwner);

        SFileEntity fileEntity = FileUtil.getInfoByFile(file, nowTime, folderPath);
        fileEntity.setFileFolder(folderId);
        fileEntity.setFileOwner(fileOwner);
        fileEntity.setFilePath(folderService.getFolderRelativePath(folderId, fileOwner) + fileEntity.getFileNameTimestamp());
        fileEntity.setFileCreateUser(fileOwner);
        fileEntity.setFileModifyUser(operator);

        if (!save(fileEntity)) {
            throw new CustomException(ExecptionType.FILE, null, "保存文件信息失败");
        }

        try {
            // 上传文件
            String path = folderPath + fileEntity.getFileNameTimestamp();
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));

            // 上传的是图片的话，额外生成略缩图
            String thumbnailsPath = folderPath + FileUtil.getFileCoverImgName(fileEntity.getFileNameTimestamp());
            if (Code.FILE_TYPE_PICTURE.getValue().equals(fileEntity.getFileType())) {
                FileUtil.createThumbnails(file.getInputStream(), 198, 132, thumbnailsPath);
            }

        } catch (IOException e) {
            throw new CustomException(ExecptionType.FILE, e.getMessage(), "文件上传失败");
        }
        return fileEntity;
    }

    @Override
    public SFileEntity saveHeadPicFile(MultipartFile file, String userId, String operator) {
        SFileEntity fileEntity = null;

        // 获取根目录
        SFolderEntity rootFolderEntity_query = new SFolderEntity();
        rootFolderEntity_query.setFolderName(userId);
        rootFolderEntity_query.setFolderOwner(userId);
        rootFolderEntity_query.setFolderParent("");
        rootFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
        QueryWrapper<SFolderEntity> rootQueryWrapper = new QueryWrapper<>(rootFolderEntity_query);
        SFolderEntity rootFolderEntity = folderService.getOne(rootQueryWrapper);
        if (rootFolderEntity == null) {
            return fileEntity;
        }

        // 获取系统资源目录
        SFolderEntity systemFolderEntity_query = new SFolderEntity();
        systemFolderEntity_query.setFolderName(taurusProperties.getFolderSystemName());
        systemFolderEntity_query.setFolderOwner(userId);
        systemFolderEntity_query.setFolderParent(rootFolderEntity.getFolderId());
        systemFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
        QueryWrapper<SFolderEntity> systemQueryWrapper = new QueryWrapper<>(systemFolderEntity_query);
        SFolderEntity systemFolderEntity = folderService.getOne(systemQueryWrapper);
        if (systemFolderEntity == null) {
            return fileEntity;
        }

        // 获取头像文件目录
        SFolderEntity headImgFolderEntity_query = new SFolderEntity();
        headImgFolderEntity_query.setFolderName(taurusProperties.getFolderHeadImgName());
        headImgFolderEntity_query.setFolderOwner(userId);
        headImgFolderEntity_query.setFolderParent(systemFolderEntity.getFolderId());
        headImgFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
        QueryWrapper<SFolderEntity> headImgQueryWrapper = new QueryWrapper<>(headImgFolderEntity_query);
        SFolderEntity headImgFolderEntity = folderService.getOne(headImgQueryWrapper);
        if (headImgFolderEntity == null) {
            return fileEntity;
        }

        if (file != null) {
            fileEntity = saveFile(file, headImgFolderEntity.getFolderId(), operator, userId);
        }

        return fileEntity;
    }

    @Override
    public String getFilePath(String fileId, String owner) {
        String filePath = "";
        if (StrUtil.isNotEmpty(fileId) && StrUtil.isNotEmpty(owner)) {
            SFileEntity queryFileEntity = new SFileEntity();
            queryFileEntity.setFileId(fileId);
            queryFileEntity.setFileOwner(owner);
            QueryWrapper<SFileEntity> fileQueryWrapper = new QueryWrapper<>(queryFileEntity);
            SFileEntity fileEntity = getOne(fileQueryWrapper);
            if (fileEntity != null) {
                filePath = taurusProperties.getFolderRoot() + fileEntity.getFilePath();
            }
        }
        return filePath;
    }

    @Override
    public String getFileUrl(String fileId, String owner) {
        String filePath = "";
        if (StrUtil.isNotEmpty(fileId) && StrUtil.isNotEmpty(owner)) {
            SFileEntity queryFileEntity = new SFileEntity();
            queryFileEntity.setFileId(fileId);
            queryFileEntity.setFileOwner(owner);
            QueryWrapper<SFileEntity> fileQueryWrapper = new QueryWrapper<>(queryFileEntity);
            SFileEntity fileEntity = getOne(fileQueryWrapper);
            if (fileEntity != null) {
                filePath = getFileUrl(fileEntity.getFilePath());
            }
        }
        return filePath;
    }

    @Override
    public String getFileUrl(String filePath) {
        if (StrUtil.isNotEmpty(filePath)) {
            String virtualPath = taurusProperties.getFolderRootVirtual().substring(0,
                    taurusProperties.getFolderRootVirtual().lastIndexOf("**"));
            return virtualPath + filePath;
        }
        return "";
    }

    @Override
    public List<SFileEntity> getList(SFileEntity fileEntity) {
        QueryWrapper<SFileEntity> queryWrapper = new QueryWrapper<>(fileEntity);
        return list(queryWrapper);
    }

    @Override
    public List<SFileEntityEx> getList(String folderId) {
        SFileEntity fileEntity_query = new SFileEntity();
        fileEntity_query.setFileFolder(folderId);
        List<SFileEntity> fileEntityList = getList(fileEntity_query);
        if (ListUtil.isNotEmpty(fileEntityList)) {

            List<SFileEntityEx> fileList = new ArrayList<>();

            for (SFileEntity fileEntity : fileEntityList) {
                SFileEntityEx fileEntityEx = JsonUtil.toEntity(fileEntity, SFileEntityEx.class);
                if (fileEntityEx != null) {
                    // 文件请求路径
//                    String fileUrl = getFileUrl(fileEntityEx.getFilePath());
//                    fileEntityEx.setFileUrl(fileUrl);

                    String fileDetailInfo = fileEntityEx.getFileDetailInfo();
                    String filePath = fileEntityEx.getFilePath();
                    String fileType = fileEntityEx.getFileType();

                    // 获取封面请求地址
                    String fileCoverImgUrl = FileUtil.getFileCoverImgUrl(filePath, fileType, fileDetailInfo, taurusProperties);
                    fileEntityEx.setFileCoverUrl(fileCoverImgUrl);
                    fileList.add(fileEntityEx);
                }
            }
            return fileList;
        }
        return null;
    }

    @Override
    public SFileEntityEx getFileDetail(String fileId, String owner) {
        SFileEntity fileDetail = getById(fileId);
        if (fileDetail == null) {
            return null;
        }

        SFileEntityEx fileEntityEx = JsonUtil.toEntity(fileDetail, SFileEntityEx.class);
        if (fileEntityEx != null) {
            // 获取文件请求地址
            String fileUrl = getFileUrl(fileId, owner);
            fileEntityEx.setFileUrl(fileUrl);

            String fileDetailInfo = fileEntityEx.getFileDetailInfo();
            String filePath = fileEntityEx.getFilePath();
            String fileType = fileEntityEx.getFileType();

            // 获取封面请求地址
            String fileCoverImgUrl = FileUtil.getFileCoverImgUrl(filePath, fileType, fileDetailInfo, taurusProperties);
            fileEntityEx.setFileCoverUrl(fileCoverImgUrl);
        }
        return fileEntityEx;
    }

    @Override
    public boolean insert(String fileFolder, MultipartFile[] files, String owner) {
        if (ListUtil.isNotEmpty(files)) {
            for (MultipartFile file : files) {
                saveFile(file, fileFolder, owner, owner);
            }
        }
        return true;
    }

    @Override
    public SFileEntityEx updateFileName(String fileId, SFileEntityEx fileEntityEx, String operator) {
        SFileEntity fileInfo = getById(fileId);
        if (fileInfo == null) {
            return null;
        }

        //验证文件所属文件夹是否可以修改
        checkFolderResourceType(fileInfo.getFileFolder());

        // aa 当前时间
        LocalDateTime nowTime = DateUtil.getLocalDateTime();

        fileInfo.setFileName(fileEntityEx.getFileName());
        fileInfo.setFileModifyUser(operator);
        fileInfo.setFileModifyTime(nowTime);
        if (!updateById(fileInfo)) {
            throw new CustomException(ExecptionType.FILE, null, "修改文件信息失败");
        }

        return JsonUtil.toEntity(fileInfo, SFileEntityEx.class);
    }

    /**
     * 验证文件是否有编辑权限（判断是否是系统文件）
     *
     * @param folderId
     */
    private void checkFolderResourceType(String folderId) {
        SFolderEntity folderInfo = folderService.getById(folderId);
        if (folderInfo == null) {
            throw new CustomException(ExecptionType.FOLDER, null, "文件夹信息获取失败");
        }
        if (Code.RESOURCE_TYPE_SYSTEM.getValue().equals(folderInfo.getFolderResourceType())) {
            throw new CustomException(ExecptionType.FOLDER, null, "系统资源无法修改");
        }
    }

    @Override
    public boolean fileNameCheck(String fileFolder, MultipartFile[] files) {

        if (StrUtil.isEmpty(fileFolder)) {
            return false;
        }

        SFileEntity fileEntityQuery = new SFileEntity();
        fileEntityQuery.setFileFolder(fileFolder);
        QueryWrapper<SFileEntity> fileQueryWrapper = new QueryWrapper<>(fileEntityQuery);
        List<SFileEntity> fileListByFolder = list(fileQueryWrapper);
        if (ListUtil.isEmpty(fileListByFolder)) {
            return true;
        }

        List<String> fileNameListByFolder = fileListByFolder.stream().map(SFileEntity::getFileName)
                .collect(Collectors.toList());

        for (MultipartFile file : files) {
            if (fileNameListByFolder.contains(file.getOriginalFilename())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getTxtContent(String fileId, String operator) {
        String filePath = getFilePath(fileId, operator);

        String content = FileUtil.getTxtContent(filePath);

        return content;
    }

    @Override
    public void getImageContent(String fileId, String operator, HttpServletResponse response) {
        String filePath = getFilePath(fileId, operator);

        if (StrUtil.isNotEmpty(filePath)) {
            File filePic = new File(filePath);
            if (filePic.exists()) {
                FileInputStream is = null;
                OutputStream toClient = null;
                try {
                    is = new FileInputStream(filePic);
                    int i = is.available(); // 得到文件大小
                    byte data[] = new byte[i];
                    is.read(data); // 读数据
                    response.setContentType("image/*"); // 设置返回的文件类型
                    toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
                    toClient.write(data); // 输出数据
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        toClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void getVideoContent(String fileId, String operator, HttpServletResponse response, HttpServletRequest request) {
        String filePath = getFilePath(fileId, operator);

        if (StrUtil.isNotEmpty(filePath)) {
            response.reset();
            // 获取从那个字节开始读取文件
            String rangeString = request.getHeader("Range");
            if (rangeString == null) {
                return;
            }

            try {
                // 获取响应的输出流
                OutputStream outputStream = response.getOutputStream();
                File file = new File(filePath);
                if (file.exists()) {
                    RandomAccessFile targetFile = new RandomAccessFile(file, "r");
                    long fileLength = targetFile.length();
                    // 播放
                    if (rangeString != null) {
                        long range = Long
                                .valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
                        // 设置内容类型
                        response.setHeader("Content-Type", "video/mp4");
                        // 设置此次相应返回的数据长度
                        response.setHeader("Content-Length", String.valueOf(fileLength - range));
                        // 设置此次相应返回的数据范围
                        response.setHeader("Content-Range", "bytes " + range + "-" + (fileLength - 1) + "/" + fileLength);
                        // 返回码需要为206，而不是200
                        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                        // 设定文件读取开始位置（以字节为单位）
                        targetFile.seek(range);
                    }
//				else {// 下载
//					System.err.println("下载");
//					// 设置响应头，把文件名字设置好
//					response.setHeader("Content-Disposition", "attachment; filename=" + "test.mp4");
//					// 设置文件长度
//					response.setHeader("Content-Length", String.valueOf(fileLength));
//					// 解决编码问题
//					response.setHeader("Content-Type", "application/octet-stream");
//				}

                    byte[] cache = new byte[1024 * 300];
                    int flag;
                    while ((flag = targetFile.read(cache)) != -1) {
                        outputStream.write(cache, 0, flag);
                    }
                } else {
                    String message = "file:" + "test.mp4" + " not exists";
                    // 解决编码问题
                    response.setHeader("Content-Type", "application/json");
                    outputStream.write(message.getBytes(StandardCharsets.UTF_8));
                }

                outputStream.flush();
                outputStream.close();

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @Override
    public int removeByParentFolderInfo(String folderId) {
        return fileDao.removeByParentFolder(folderId);
    }

    @Override
    public List<SFileEntity> getFiles(String parentFolder) {
        if (StrUtil.isEmpty(parentFolder)) {
            return null;
        }
        SFileEntity file_query = new SFileEntity();
        file_query.setFileFolder(parentFolder);
        QueryWrapper<SFileEntity> fileQueryWrapper = new QueryWrapper<>(file_query);
        return list(fileQueryWrapper);
    }

    @Override
    @Transactional
    public void deleteFile(String fileId, String operator) {
        SFileEntity fileInfo = getById(fileId);
        if (fileInfo == null) {
            throw new CustomException(ExecptionType.FILE, null, "文件不存在");
        }

        //验证文件所属文件夹是否可以修改
        checkFolderResourceType(fileInfo.getFileFolder());

        String filePath = getFilePath(fileId, operator);
        File willDelFile = new File(filePath);
        if (StrUtil.isEmpty(filePath) || !willDelFile.exists() || !willDelFile.isFile()) {
            throw new CustomException(ExecptionType.FILE, null, "文件不存在");
        }

        if (!removeById(fileId)) {
            throw new CustomException(ExecptionType.FILE, null, "文件信息删除失败");
        }

        FileUtil.deleteFile(willDelFile);
    }

}
