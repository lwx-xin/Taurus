package org.taurus.service;

import org.springframework.stereotype.Service;
import org.taurus.entity.SFolderEntity;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件夹信息 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 * @version v1.0
 */
@Service
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
	 * @return
	 */
	public String createFolder(String folderName, String parentFolder, String folderOwner, String operator);

}