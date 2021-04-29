package org.taurus.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.taurus.common.code.Code;
import org.taurus.entity.SFolderEntity;
import org.taurus.extendEntity.SFolderEntityEx;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件夹信息 服务类
 * </p>
 *
 * @author 欣
 * @version v1.0
 * @since 2020-12-28
 */
public interface SFolderService extends IService<SFolderEntity> {

    /**
     * 获取文件夹的全路径
     *
     * @param folderId    文件夹id
     * @param folderOwner 文件夹所有者
     * @return E:/Taurus/download/Afolder/Bfolder/
     */
    public String getFolderPath(String folderId, String folderOwner);

    /**
     * 获取文件夹的相对路径
     *
     * @param folderId    文件夹id
     * @param folderOwner 文件夹所有者
     * @return /Afolder/Bfolder/
     */
    public String getFolderRelativePath(String folderId, String folderOwner);

    /**
     * 添加新用户时，添加该用户的默认文件夹
     *
     * @param folderOwner 文件夹所有者
     * @param operator    操作人员
     */
    public void createInitFolder(String folderOwner, String operator);

    /**
     * 添加文件夹
     *
     * @param folderName   文件夾名称
     * @param parentFolder 父文件夹id
     * @param folderOwner  文件夹所有者
     * @param operator     操作人员
     * @param resourceType 资源类别
     * @return
     */
    public String createFolder(String folderName, String parentFolder, String folderOwner, String operator, Code resourceType);

    /**
     * 获取当前文件夹的详细信息(文件以及文件夹)
     *
     * @param userId   用户ID
     * @param filter   过滤条件
     * @param folderId 文件夹ID
     * @return
     */
    public SFolderEntityEx getFolderDetail(String userId, Map<String, Object> filter, String folderId);

    /**
     * 获取用户能显示的文件夹列表(列表结构，不是树状结构)
     *
     * @param userId
     * @return
     */
    public List<SFolderEntity> getFolderListByUser(String userId);

    /**
     * 获取用户能显示的文件夹列表(树状结构，不是列表结构)
     *
     * @param userId
     * @return
     */
    public List<SFolderEntityEx> getFolderTreeByUser(String userId);

    /**
     * 将文件夹列表从列表结构改成树状结构
     *
     * @param folderList
     * @return
     */
    public List<SFolderEntityEx> processingFolderData(List<SFolderEntity> folderList);

    /**
     * 获取文件夹信息（子文件夹个数，子文件个数）<br/>文件夹ID为空就查询当前用户根目录
     *
     * @param folderId 文件夹ID
     * @param userId   用户ID
     * @return
     */
    public SFolderEntityEx getFolderInfo(String folderId, String userId);

    /**
     * 修改文件夹信息
     *
     * @param folderId
     * @param folderEntityEx
     * @param operator
     * @return
     */
    public SFolderEntityEx updateFolderName(String folderId, SFolderEntityEx folderEntityEx, String operator);

    /**
     * 删除文件夹
     *
     * @param folderId
     * @param operator
     * @return
     */
    public boolean deleteFolder(String folderId, String operator);

    /**
     * 获取目录下的文件夹（不递归）
     *
     * @param parentFolder
     * @return
     */
    public List<SFolderEntity> getFolders(String parentFolder);

}