package org.taurus.common.util;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.code.Code;
import org.taurus.entity.SFileEntity;

public class FileUtil {

	/**
	 * 将文件信息保存到fileEntity
	 * 
	 * @param file
	 * @return
	 */
	public static SFileEntity getInfoByFile(MultipartFile file, LocalDateTime nowTime) {
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
		fileEntity.setFileType(getFileType(fileName));
		fileEntity.setFileDelFlg(Code.DEL_FLG_1.getValue());
		fileEntity.setFileCreateTime(nowTime);
		fileEntity.setFileModifyTime(nowTime);
//		fileEntity.setFileFolder(folderId);
//		fileEntity.setFileOwner(fileOwner);
//		fileEntity.setFileCreateUser(fileOwner);
//		fileEntity.setFileModifyUser(operator);
//		fileEntity.setFilePath();
		return fileEntity;
	}

	/**
	 * 将文件信息保存到fileEntity
	 * 
	 * @param file
	 * @return
	 */
	public static SFileEntity getInfoByFile(File file, LocalDateTime nowTime) {
		// aa 时间戳
		String timeStamp = String.valueOf(new Date().getTime());
		// aa 文件名
		String fileName = file.getName();
		// aa 后缀
		String extension = FilenameUtils.getExtension(fileName);
		// aa 带时间戳的文件名
		String fileNameTimestamp = FilenameUtils.getBaseName(fileName) + timeStamp + "." + extension;
		// aa 文件大小(B)
		long fileLength = file.length();
		// aa 文件大小(KB)保留两位小数，四舍五入
		String fileSize = new BigDecimal(fileLength).divide(new BigDecimal("1024"))
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

		SFileEntity fileEntity = new SFileEntity();
		fileEntity.setFileId(StrUtil.getUUID());
		fileEntity.setFileName(fileName);
		fileEntity.setFileNameTimestamp(fileNameTimestamp);
		fileEntity.setFileSize(fileSize);
		fileEntity.setFileType(getFileType(fileName));
		fileEntity.setFileDelFlg(Code.DEL_FLG_1.getValue());
		fileEntity.setFileCreateTime(nowTime);
		fileEntity.setFileModifyTime(nowTime);
//		fileEntity.setFileFolder(folderId);
//		fileEntity.setFileOwner(fileOwner);
//		fileEntity.setFileCreateUser(fileOwner);
//		fileEntity.setFileModifyUser(operator);
//		fileEntity.setFilePath();
		return fileEntity;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		String type = Code.FILE_TYPE_OTHER.getValue();
		// aa 后缀 - 小写
		String extension = FilenameUtils.getExtension(fileName).toLowerCase();
		if (extension != null) {
			switch (extension) {
			case "mp3":
				type = Code.FILE_TYPE_AUDIO.getValue();
				break;
			case "mp4":
				type = Code.FILE_TYPE_VIDEO.getValue();
				break;
			case "text":
			case "txt":
				type = Code.FILE_TYPE_TXT.getValue();
				break;
			case "log":
				type = Code.FILE_TYPE_LOG.getValue();
				break;
			case "png":
			case "jpg":
				type = Code.FILE_TYPE_PICTURE.getValue();
				break;
			default:
				type = Code.FILE_TYPE_OTHER.getValue();
				break;
			}
		}
		return type;
	}

}
