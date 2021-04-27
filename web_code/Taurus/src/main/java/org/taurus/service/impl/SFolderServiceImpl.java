package org.taurus.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.*;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.dao.SFolderDao;
import org.taurus.entity.SFileEntity;
import org.taurus.entity.SFolderEntity;
import org.taurus.extendEntity.SFileEntityEx;
import org.taurus.extendEntity.SFolderEntityEx;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 文件夹信息 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
@Service
public class SFolderServiceImpl extends ServiceImpl<SFolderDao, SFolderEntity> implements SFolderService {

    @Autowired
    private TaurusProperties taurusProperties;

    @Autowired
    private SFileService fileService;

    @Autowired
    private SFolderDao folderDao;

    @Override
    public String getFolderPath(String folderId, String folderOwner) {
        // aa 文件夹的相对路径
        String folderRelativePath = getFolderRelativePath(folderId, folderOwner);

        return taurusProperties.getFolderRoot() + folderRelativePath;
    }

    @Override
    public String getFolderRelativePath(String folderId, String folderOwner) {
        String folderPath = "";// taurusProperties.getFolderRoot();

        SFolderEntity queryEntity = new SFolderEntity();
//		queryEntity.setFolderId(folderId);
        queryEntity.setFolderOwner(folderOwner);

        QueryWrapper<SFolderEntity> queryWrapper = new QueryWrapper<SFolderEntity>(queryEntity);
        List<SFolderEntity> folderList = list(queryWrapper);

        if (ListUtil.isNotEmpty(folderList)) {
            List<SFolderEntity> folders = folderList.stream()
                    .filter((SFolderEntity folderEntity) -> folderEntity.getFolderId().equals(folderId))
                    .collect(Collectors.toList());
            if (ListUtil.isNotEmpty(folders)) {
                SFolderEntity thisFolder = folders.get(0);
                String parentFolderName = getParentFolderName(folderList, thisFolder);
                if (StrUtil.isNotEmpty(parentFolderName)) {
                    folderPath = parentFolderName + "/";
                }
            }
        }

        return folderPath;
    }

    private String getParentFolderName(List<SFolderEntity> folderList, SFolderEntity thisFolder) {
        // aa 文件夹名称
        String folderName = thisFolder.getFolderName();
        // aa 上级文件夹id
        String folderParent = thisFolder.getFolderParent();

        String parentFolderName = "";

        if (StrUtil.isNotEmpty(folderParent)) {
            List<SFolderEntity> folders = folderList.stream()
                    .filter((SFolderEntity folderEntity) -> folderEntity.getFolderId().equals(folderParent))
                    .collect(Collectors.toList());
            if (ListUtil.isNotEmpty(folders)) {
                parentFolderName = getParentFolderName(folderList, folders.get(0));
                parentFolderName = parentFolderName + "/" + folderName;
            }
        } else {
            parentFolderName = folderName;
        }

        return parentFolderName;
    }

    @Override
    public void createInitFolder(String folderOwner, String operator) {
        // aa 添加用户根目录
        String rootFolderId = createFolder(folderOwner, "", folderOwner, operator);
        // aa 添加系统资源目录
        String systemFolderId = createFolder(taurusProperties.getFolderSystemName(), rootFolderId, folderOwner,
                operator);
        // aa 添加头像图片资源目录
        String headFolderId = createFolder(taurusProperties.getFolderHeadImgName(), systemFolderId, folderOwner,
                operator);
        // aa 添加头像图片资源目录
        String logFolderId = createFolder(taurusProperties.getFolderLogName(), systemFolderId, folderOwner, operator);
    }

    @Override
    public String createFolder(String folderName, String parentFolder, String folderOwner, String operator) {
        String folderId = "";

        // aa 判断文件夹是否存在
        SFolderEntity queryEntity = new SFolderEntity();
        queryEntity.setFolderName(folderName);
        queryEntity.setFolderOwner(folderOwner);
        queryEntity.setFolderParent(parentFolder);
        queryEntity.setFolderDelFlg(Code.DEL_FLG_1.getValue());
        QueryWrapper<SFolderEntity> queryWrapper = new QueryWrapper<SFolderEntity>(queryEntity);
        SFolderEntity systemFolderEntity = getOne(queryWrapper);
        if (systemFolderEntity != null) {
            folderId = systemFolderEntity.getFolderId();
            throw new CustomException(ExecptionType.FOLDER, null, "文件夹已存在");
        }

        // aa 文件目录
        File rootFolder = new File(getFolderPath(parentFolder, folderOwner) + folderName);
        if (!rootFolder.exists()) {
            rootFolder.mkdirs();// 创建目录文件夹
        }

        // aa 当前时间
        LocalDateTime nowTime = DateUtil.getLocalDateTime();
        folderId = StrUtil.getUUID();

        SFolderEntity folderEntity = new SFolderEntity();
        folderEntity.setFolderId(folderId);
        folderEntity.setFolderName(folderName);
        folderEntity.setFolderOwner(folderOwner);
        folderEntity.setFolderParent(parentFolder);
        folderEntity.setFolderDelFlg(Code.DEL_FLG_1.getValue());
        folderEntity.setFolderCreateTime(nowTime);
        folderEntity.setFolderCreateUser(operator);
        folderEntity.setFolderModifyTime(nowTime);
        folderEntity.setFolderModifyUser(operator);
        if (!save(folderEntity)) {
            rootFolder.delete();
            throw new CustomException(ExecptionType.FOLDER, null, "添加目录失败");
        }

        return folderId;
    }

    @Override
    public SFolderEntityEx getFolderDetail(String userId, Map<String, Object> filter, String folderId) {
        if (StrUtil.isEmpty(userId)) {
            return null;
        }

        // 文件类型
        Object fileTypeObj = filter.get("fileType");
        List<String> filter_fileTypes = null;
        if (fileTypeObj != null) {
            filter_fileTypes = new ArrayList<>(Arrays.asList((String[]) fileTypeObj));
        }

        // 获取当前文件夹的信息
        SFolderEntityEx returnFolder = null;
        if (StrUtil.isNotEmpty(folderId)) {
            returnFolder = JsonUtil.toEntity(getById(folderId), SFolderEntityEx.class);
        } else {
            SFolderEntity folderEntity_query = new SFolderEntity();
            folderEntity_query.setFolderName(userId);
            QueryWrapper<SFolderEntity> folderQuery = new QueryWrapper<>(folderEntity_query);
            returnFolder = JsonUtil.toEntity(getOne(folderQuery), SFolderEntityEx.class);
            folderId = returnFolder.getFolderId();
        }
        if (returnFolder == null) {
            return null;
        }

        // 当前文件夹下的文件夹
        SFolderEntity folderEntity_query = new SFolderEntity();
        folderEntity_query.setFolderParent(folderId);
        QueryWrapper<SFolderEntity> folderQuery = new QueryWrapper<>(folderEntity_query);
        List<SFolderEntity> folderList = list(folderQuery);
        if (ListUtil.isNotEmpty(folderList)) {
            returnFolder.setChildrens(JsonUtil.toList(JsonUtil.toJson(folderList), SFolderEntityEx.class));
            returnFolder.setChildrenCount(folderList.size());
        }

        // 当前文件夹下的文件
        SFileEntity fileEntity_query = new SFileEntity();
        fileEntity_query.setFileFolder(folderId);
        QueryWrapper<SFileEntity> fileQuery = new QueryWrapper<>(fileEntity_query);
        List<SFileEntity> fileList = fileService.list(fileQuery);
        if (ListUtil.isNotEmpty(fileList)) {
            List<SFileEntityEx> fileExList = JsonUtil.toList(JsonUtil.toJson(fileList), SFileEntityEx.class);
            List<SFileEntityEx> fileFilterList = new ArrayList<>();

            for (SFileEntityEx fileEntityEx : fileExList) {
                if (ListUtil.isNotEmpty(filter_fileTypes) && !filter_fileTypes.contains("") && !filter_fileTypes.contains(fileEntityEx.getFileType())) {
                    continue;
                }

                // 略缩图路径
                if (Code.FILE_TYPE_PICTURE.getValue().equals(fileEntityEx.getFileType())) {
                    // 图片略缩图的请求路径
                    String fileThumbnailsUrl = fileService.getFileThumbnailsUrl(fileEntityEx.getFilePath());
                    fileEntityEx.setFileThumbnailsUrl(fileThumbnailsUrl);
                }
                fileFilterList.add(fileEntityEx);
            }

            returnFolder.setChildrenFiles(fileFilterList);
            returnFolder.setChildrenFileCount(fileFilterList.size());
        }

        return returnFolder;
    }

    @Override
    public List<SFolderEntity> getFolderListByUser(String userId) {

        SFolderEntity folderQueryEntity = new SFolderEntity();
        folderQueryEntity.setFolderOwner(userId);
        QueryWrapper<SFolderEntity> folderQueayWrapper = new QueryWrapper<SFolderEntity>(folderQueryEntity);
        return list(folderQueayWrapper);
    }

    @Override
    public List<SFolderEntityEx> getFolderTreeByUser(String userId) {
        return processingFolderData(getFolderListByUser(userId));
    }

    @Override
    public List<SFolderEntityEx> processingFolderData(List<SFolderEntity> folderSource) {

        List<SFolderEntityEx> folderList = new ArrayList<SFolderEntityEx>();

        if (ListUtil.isNotEmpty(folderSource)) {

            Map<String, SFolderEntityEx> folderMap = new HashMap<String, SFolderEntityEx>();

            for (SFolderEntity folder : folderSource) {
                String folderId = folder.getFolderId();
                String folderParent = folder.getFolderParent();

                SFolderEntityEx folderEx = JsonUtil.toEntity(folder, SFolderEntityEx.class);

                if (StrUtil.isEmpty(folderParent)) {
                    // aa一级菜单
                    folderList.add(folderEx);
                } else {
                    folderMap.put(folderParent + "-" + folderId, folderEx);
                }
            }

            for (SFolderEntityEx folder : folderList) {
                setChildrens(folder, folderMap);
            }
        }

        return folderList;
    }

    @SuppressWarnings("unchecked")
    private void setChildrens(SFolderEntityEx nowNode, Map<String, SFolderEntityEx> folderMap) {
        String folderId = nowNode.getFolderId();
        List<SFolderEntityEx> childrens = MapUtil.get(folderMap, folderId + "-");
        for (SFolderEntityEx folder : childrens) {
            setChildrens(folder, folderMap);
        }
        nowNode.setChildrens(childrens);
    }

    @Override
    public SFolderEntityEx getFolderInfo(String folderId, String userId) {
        if (StrUtil.isEmpty(userId)) {
            return null;
        }

        SFolderEntityEx folderInfo = null;

        if (StrUtil.isNotEmpty(folderId)) {
            folderInfo = folderDao.getFolderInfo(folderId);
        } else {
            SFolderEntity folderEntity_query = new SFolderEntity();
            folderEntity_query.setFolderName(userId);
            QueryWrapper<SFolderEntity> folderQuery = new QueryWrapper<>(folderEntity_query);
            SFolderEntity rootFolder = getOne(folderQuery);
            if (rootFolder != null) {
                folderInfo = folderDao.getFolderInfo(rootFolder.getFolderId());
            }
        }
        return folderInfo;
    }

    @Override
    public SFolderEntityEx updateFolderName(String folderId, SFolderEntityEx folderEntityEx, String operator) {
        SFolderEntity folderInfo = getById(folderId);
        if (folderInfo == null) {
            return null;
        }

        // aa 当前时间
        LocalDateTime nowTime = DateUtil.getLocalDateTime();

        if (StrUtil.isNotEmpty(folderEntityEx.getFolderName())) {
            folderInfo.setFolderName(folderEntityEx.getFolderName());
        }

        folderInfo.setFolderModifyTime(nowTime);
        folderInfo.setFolderModifyUser(operator);
        if (!updateById(folderInfo)) {
            throw new CustomException(ExecptionType.FOLDER, null, "修改文件夹信息失败");
        }

        return JsonUtil.toEntity(folderInfo, SFolderEntityEx.class);
    }

    @Override
    public boolean deleteFolder(String folderId, String operator) {

        String folderPath = getFolderPath(folderId, operator);
        if (StrUtil.isEmpty(folderPath)){
            return false;
        }

        // 删除文件夹信息
        if(!removeById(folderId)){
            throw new CustomException(ExecptionType.FOLDER, null, "文件夹信息删除失败");
        }

        // 删除文件夹下的文件信息
        if (!fileService.removeByParentFolderInfo(folderId)){
            throw new CustomException(ExecptionType.FOLDER, null, "文件信息删除失败");
        }

        // 删除文件夹以及文件夹下的文件
        FileUtil.deleteFile(folderPath);

        return true;
    }

}
