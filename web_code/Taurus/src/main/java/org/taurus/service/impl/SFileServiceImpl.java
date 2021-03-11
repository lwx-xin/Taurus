package org.taurus.service.impl;

import org.taurus.entity.SFileEntity;
import org.taurus.entity.SFolderEntity;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.FileUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.dao.SFileDao;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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

	@Resource
	private TaurusProperties taurusProperties;

	@Resource
	private SFolderService folderService;

	@Override
	public SFileEntity saveFile(MultipartFile file, String folderId, String operator, String fileOwner) {

		// aa 文件夹信息
		SFolderEntity folder = folderService.getById(folderId);
		if (folder == null) {
			throw new CustomException(ExecptionType.FOLDER, null, "未知的文件路径");
		}

		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SFileEntity fileEntity = FileUtil.getInfoByFile(file, nowTime);
		fileEntity.setFileFolder(folderId);
		fileEntity.setFileOwner(fileOwner);
		fileEntity.setFilePath(
				folderService.getFolderRelativePath(folderId, fileOwner) + fileEntity.getFileNameTimestamp());
		fileEntity.setFileCreateUser(fileOwner);
		fileEntity.setFileModifyUser(operator);

		if (!save(fileEntity)) {
			throw new CustomException(ExecptionType.FILE, null, "保存文件信息失败");
		}
		// aa 上传文件
		try {
			String path = folderService.getFolderPath(folderId, fileOwner) + fileEntity.getFileNameTimestamp();
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
		} catch (IOException e) {
			throw new CustomException(ExecptionType.FILE, e.getMessage(), "文件上传失败");
		}
		return fileEntity;
	}

	@Override
	public SFileEntity saveHeadPicFile(MultipartFile file, String userId, String operator) {
		SFileEntity fileEntity = null;

		// aa 获取根目录
		SFolderEntity rootFolderEntity_query = new SFolderEntity();
		rootFolderEntity_query.setFolderName(userId);
		rootFolderEntity_query.setFolderOwner(userId);
		rootFolderEntity_query.setFolderParent("");
		rootFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SFolderEntity> rootQueryWrapper = new QueryWrapper<SFolderEntity>(rootFolderEntity_query);
		SFolderEntity rootFolderEntity = folderService.getOne(rootQueryWrapper);
		if (rootFolderEntity == null) {
			return fileEntity;
		}

		// aa 获取系统资源目录
		SFolderEntity systemFolderEntity_query = new SFolderEntity();
		systemFolderEntity_query.setFolderName(taurusProperties.getFolderSystemName());
		systemFolderEntity_query.setFolderOwner(userId);
		systemFolderEntity_query.setFolderParent(rootFolderEntity.getFolderId());
		systemFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SFolderEntity> systemQueryWrapper = new QueryWrapper<SFolderEntity>(systemFolderEntity_query);
		SFolderEntity systemFolderEntity = folderService.getOne(systemQueryWrapper);
		if (systemFolderEntity == null) {
			return fileEntity;
		}

		// aa 获取头像文件目录
		SFolderEntity headImgFolderEntity_query = new SFolderEntity();
		headImgFolderEntity_query.setFolderName(taurusProperties.getFolderHeadImgName());
		headImgFolderEntity_query.setFolderOwner(userId);
		headImgFolderEntity_query.setFolderParent(systemFolderEntity.getFolderId());
		headImgFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SFolderEntity> headImgQueryWrapper = new QueryWrapper<SFolderEntity>(headImgFolderEntity_query);
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
			QueryWrapper<SFileEntity> fileQueryWrapper = new QueryWrapper<SFileEntity>(queryFileEntity);
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
			QueryWrapper<SFileEntity> fileQueryWrapper = new QueryWrapper<SFileEntity>(queryFileEntity);
			SFileEntity fileEntity = getOne(fileQueryWrapper);
			if (fileEntity != null) {
				String virtualPath = taurusProperties.getFolderRootVirtual().substring(0,
						taurusProperties.getFolderRootVirtual().lastIndexOf("**"));
				filePath = virtualPath + fileEntity.getFilePath();
			}
		}
		return filePath;
	}

	@Override
	public List<SFileEntity> getList(SFileEntity fileEntity){
		QueryWrapper<SFileEntity> queryWrapper = new QueryWrapper<>(fileEntity);
		return list(queryWrapper);
	}

}
