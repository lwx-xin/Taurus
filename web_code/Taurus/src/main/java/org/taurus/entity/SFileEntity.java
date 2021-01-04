package org.taurus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 文件信息
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
@TableName("s_file")
public class SFileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件id
	 */
	@TableId("FILE_ID")
	private String fileId;

	/**
	 * 文件名
	 */
	@TableField("FILE_NAME")
	private String fileName;

	/**
	 * 文件名+时间戳
	 */
	@TableField("FILE_NAME_TIMESTAMP")
	private String fileNameTimestamp;

	/**
	 * 文件大小
	 */
	@TableField("FILE_SIZE")
	private String fileSize;

	/**
	 * 文件类型
	 */
	@TableField("FILE_TYPE")
	private String fileType;

	/**
	 * 文件所属文件夹id
	 */
	@TableField("FILE_FOLDER")
	private String fileFolder;

	/**
	 * 文件所有者
	 */
	@TableField("FILE_OWNER")
	private String fileOwner;

	/**
	 * 删除标识
	 */
	@TableField("FILE_DEL_FLG")
	private String fileDelFlg;

	/**
	 * 创建时间
	 */
	@TableField("FILE_CREATE_TIME")
	private LocalDateTime fileCreateTime;

	/**
	 * 创建者
	 */
	@TableField("FILE_CREATE_USER")
	private String fileCreateUser;

	/**
	 * 编辑时间
	 */
	@TableField("FILE_MODIFY_TIME")
	private LocalDateTime fileModifyTime;

	/**
	 * 编辑者
	 */
	@TableField("FILE_MODIFY_USER")
	private String fileModifyUser;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNameTimestamp() {
		return fileNameTimestamp;
	}

	public void setFileNameTimestamp(String fileNameTimestamp) {
		this.fileNameTimestamp = fileNameTimestamp;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileFolder() {
		return fileFolder;
	}

	public void setFileFolder(String fileFolder) {
		this.fileFolder = fileFolder;
	}

	public String getFileOwner() {
		return fileOwner;
	}

	public void setFileOwner(String fileOwner) {
		this.fileOwner = fileOwner;
	}

	public String getFileDelFlg() {
		return fileDelFlg;
	}

	public void setFileDelFlg(String fileDelFlg) {
		this.fileDelFlg = fileDelFlg;
	}

	public LocalDateTime getFileCreateTime() {
		return fileCreateTime;
	}

	public void setFileCreateTime(LocalDateTime fileCreateTime) {
		this.fileCreateTime = fileCreateTime;
	}

	public String getFileCreateUser() {
		return fileCreateUser;
	}

	public void setFileCreateUser(String fileCreateUser) {
		this.fileCreateUser = fileCreateUser;
	}

	public LocalDateTime getFileModifyTime() {
		return fileModifyTime;
	}

	public void setFileModifyTime(LocalDateTime fileModifyTime) {
		this.fileModifyTime = fileModifyTime;
	}

	public String getFileModifyUser() {
		return fileModifyUser;
	}

	public void setFileModifyUser(String fileModifyUser) {
		this.fileModifyUser = fileModifyUser;
	}

	@Override
	public String toString() {
		return "SFileEntity{" + "fileId=" + fileId + ", fileName=" + fileName + ", fileNameTimestamp="
				+ fileNameTimestamp + ", fileSize=" + fileSize + ", fileType=" + fileType + ", fileFolder=" + fileFolder
				+ ", fileOwner=" + fileOwner + ", fileDelFlg=" + fileDelFlg + ", fileCreateTime=" + fileCreateTime
				+ ", fileCreateUser=" + fileCreateUser + ", fileModifyTime=" + fileModifyTime + ", fileModifyUser="
				+ fileModifyUser + "}";
	}
}
