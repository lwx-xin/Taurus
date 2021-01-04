package org.taurus.service.impl;

import org.taurus.entity.SFileEntity;
import org.taurus.entity.SFolderEntity;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.load.properties.TaurusProperties;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SFileDao;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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

		SFileEntity fileEntity = getInfoByFile(file, nowTime);
		fileEntity.setFileFolder(folderId);
		fileEntity.setFileOwner(fileOwner);
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
	public SFileEntity saveHeadPicFile(MultipartFile file, String userId) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		// aa 用户文件根目录id
		String rootFolderId = userId;
		// aa 用户文件根目录名称
		String rootFolderName = userId;
		// aa 用户文件根目录
		File rootFolder = new File(taurusProperties.getFolderRoot() + rootFolderName);

		// aa 如果根目录文件夹不存在
		if (!rootFolder.exists()) {

			SFolderEntity folderEntity = new SFolderEntity();
			folderEntity.setFolderId(rootFolderId);
			folderEntity.setFolderName(rootFolderName);
			folderEntity.setFolderOwner(userId);
			folderEntity.setFolderParent(null);
			folderEntity.setFolderDelFlg(Code.DEL_FLG_1.getValue());
			folderEntity.setFolderCreateTime(nowTime);
			folderEntity.setFolderCreateUser(userId);
			folderEntity.setFolderModifyTime(nowTime);
			folderEntity.setFolderModifyUser(userId);
			if (!folderService.save(folderEntity)) {
				throw new CustomException(ExecptionType.FOLDER, null, "创建用户，添加根目录失败");
			}
			rootFolder.mkdirs();// 创建根目录文件夹
		}

		// aa 用户系统资源文件夹id
		String userSystemFolderId = StrUtil.getUUID();
		// aa 用户系统资源文件夹名称
		String userSystemFolderName = "system";
		// aa 用户系统资源文件夹
		File userSystemFolder = new File(taurusProperties.getFolderRoot() + userId + "/" + userSystemFolderName);

		// aa 如果用户系统资源文件夹不存在
		if (!userSystemFolder.exists()) {

			SFolderEntity folderEntity = new SFolderEntity();
			folderEntity.setFolderId(userSystemFolderId);
			folderEntity.setFolderName(userSystemFolderName);
			folderEntity.setFolderOwner(userId);
			folderEntity.setFolderParent(rootFolderId);
			folderEntity.setFolderDelFlg(Code.DEL_FLG_1.getValue());
			folderEntity.setFolderCreateTime(nowTime);
			folderEntity.setFolderCreateUser(userId);
			folderEntity.setFolderModifyTime(nowTime);
			folderEntity.setFolderModifyUser(userId);
			if (!folderService.save(folderEntity)) {
				throw new CustomException(ExecptionType.FOLDER, null, "创建用户，添加系统资源文件夹失败");
			}
			userSystemFolder.mkdirs();// 创建用户系统资源文件夹
		}

		SFileEntity fileEntity = new SFileEntity();
		if (file != null) {
			// aa 保存头像文件信息
			fileEntity = getInfoByFile(file, nowTime);
			fileEntity.setFileFolder(userSystemFolderId);
			fileEntity.setFileOwner(userId);
			fileEntity.setFileCreateUser(userId);
			fileEntity.setFileModifyUser(userId);
			if (!save(fileEntity)) {
				throw new CustomException(ExecptionType.FILE, null, "保存头像文件信息失败");
			}

			// aa 上传文件
			try {
				String path = taurusProperties.getFolderRoot() + rootFolderName + "/" + userSystemFolderName + "/"
						+ fileEntity.getFileNameTimestamp();
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			} catch (IOException e) {
				throw new CustomException(ExecptionType.FILE, e.getMessage(), "文件上传失败");
			}
		}

		return fileEntity;
	}

	/**
	 * 将文件信息保存到fileEntity
	 * 
	 * @param file
	 * @return
	 */
	private SFileEntity getInfoByFile(MultipartFile file, LocalDateTime nowTime) {
		// aa 时间戳
		String timeStamp = String.valueOf(new Date().getTime());
		// aa 文件名
		String fileName = file.getOriginalFilename();
		// aa 后缀
		String extension = FilenameUtils.getExtension(fileName);
		// aa 带时间戳的文件名
		String fileNameTimestamp = FilenameUtils.getBaseName(fileName) + timeStamp + "." + extension;
		// aa 文件大小(B)
		long fileLength = file.getSize();
		// aa 文件大小(KB)保留两位小数，四舍五入
		String fileSize = new BigDecimal(fileLength).divide(new BigDecimal("1024"))
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

		SFileEntity fileEntity = new SFileEntity();
		fileEntity.setFileId(StrUtil.getUUID());
		fileEntity.setFileName(fileName);
		fileEntity.setFileNameTimestamp(fileNameTimestamp);
		fileEntity.setFileSize(fileSize);
		fileEntity.setFileType(StrUtil.getFileType(fileName));
//		fileEntity.setFileFolder(folderId);
//		fileEntity.setFileOwner(fileOwner);
		fileEntity.setFileDelFlg(Code.DEL_FLG_1.getValue());
		fileEntity.setFileCreateTime(nowTime);
//		fileEntity.setFileCreateUser(fileOwner);
		fileEntity.setFileModifyTime(nowTime);
//		fileEntity.setFileModifyUser(operator);
		return fileEntity;
	}

	@Override
	public String getFilePath(String fileId, String owner) {
		String filePath = "";
		if (StrUtil.isNotEmpty(fileId) && StrUtil.isNotEmpty(owner)) {
			SFileEntity fileEntity = getById(fileId);
			if (fileEntity == null) {
				return filePath;
			}
			String fileFolder = fileEntity.getFileFolder();
			filePath = folderService.getFolderPath(fileFolder, owner)+fileEntity.getFileNameTimestamp();
		}
		return filePath;
	}

	@Override
	public String getFileUrl(String fileId, String owner) {
		String filePath = "";
		if (StrUtil.isNotEmpty(fileId) && StrUtil.isNotEmpty(owner)) {
			SFileEntity fileEntity = getById(fileId);
			if (fileEntity == null) {
				return filePath;
			}
			String fileFolder = fileEntity.getFileFolder();
			filePath = "/f/" + folderService.getFolderRelativePath(fileFolder, owner)+fileEntity.getFileNameTimestamp();
		}
		return filePath;
	}

}
