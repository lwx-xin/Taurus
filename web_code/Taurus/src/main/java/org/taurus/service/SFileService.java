package org.taurus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.entity.SFileEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.taurus.entity.SFolderEntity;
import org.taurus.extendEntity.SFileEntityEx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 文件信息 服务类
 * </p>
 *
 * @author 欣
 * @version v1.0
 * @since 2020-12-28
 */
public interface SFileService extends IService<SFileEntity> {

    /**
     * 保存文件
     *
     * @param file      文件
     * @param folderId  所属文件夹
     * @param operator  操作人员
     * @param fileOwner 文件所有者
     * @return
     */
    public SFileEntity saveFile(MultipartFile file, String folderId, String operator, String fileOwner);

    /**
     * 保存用户头像文件
     *
     * @param file     文件
     * @param userId   用户id
     * @param operator 操作人员
     * @return
     */
    public SFileEntity saveHeadPicFile(MultipartFile file, String userId, String operator);

    /**
     * 获取文件的全路径
     *
     * @param fileId 文件id
     * @param owner  文件所有者
     * @return E:/a/b/c/d.txt
     */
    public String getFilePath(String fileId, String owner);

    /**
     * 获取文件的请求路径
     *
     * @param fileId 文件id
     * @param owner  文件所有者
     * @return /f/xxx/xxxx/x.txt
     */
    public String getFileUrl(String fileId, String owner);

    /**
     * 获取文件的请求路径
     *
     * @param filePath 文件路径
     * @return /f/xxx/xxxx/x.txt
     */
    public String getFileUrl(String filePath);

    /**
     * 获取图片略缩图的请求路径
     *
     * @param filePath 文件路径
     * @return /f/xxx/xxxx/xx_thumbnails.png
     */
    public String getFileThumbnailsUrl(String filePath);

    /**
     * 获取文件列表
     *
     * @param fileEntity 过滤条件
     * @return 文件列表数据
     */
    public List<SFileEntity> getList(SFileEntity fileEntity);

    /**
     * 获取当前文件夹下的文件列表
     *
     * @param folderId 文件所属文件夹
     * @return 文件列表数据
     */
    public List<SFileEntityEx> getList(String folderId);

    /**
     * 根据id获取文件信息
     *
     * @param fileId 文件id
     * @param owner  文件所有者
     * @return
     */
    public SFileEntityEx getFileDetail(String fileId, String owner);

    /**
     * 添加文件
     *
     * @param fileFolder 文件所属文件夹
     * @param files      文件
     * @param owner      文件所有者
     * @return 状态
     */
    public boolean insert(String fileFolder, MultipartFile[] files, String owner);

    /**
     * 验证文件夹下是否存在同名的文件
     *
     * @param fileFolder 文件夹
     * @param files      文件
     * @return
     */
    public boolean fileNameCheck(String fileFolder, MultipartFile[] files);

    /**
     * 获取文本文件内容
     *
     * @param fileId   文件ID
     * @param operator 操作人员
     * @return
     */
    public String getTxtContent(String fileId, String operator);

    /**
     * 获取图片文件内容
     *
     * @param fileId   文件ID
     * @param operator 操作人员
     * @param response
     */
    public void getImageContent(String fileId, String operator, HttpServletResponse response);

    /**
     * 获取视频文件内容
     *
     * @param fileId   文件ID
     * @param operator 操作人员
     * @param response
     */
    public void getVideoContent(String fileId, String operator, HttpServletResponse response, HttpServletRequest request);

    /**
     * 删除当前目录下的全部文件信息(删db不删文件)
     * @param folderId
     * @return
     */
    public boolean removeByParentFolderInfo(String folderId);

}