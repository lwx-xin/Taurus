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
	 * @param folderId 文件夹id
	 * @param folderOwner 文件夹所有者
	 * @return C:/A/B/
	 */
	public String getFolderPath(String folderId, String folderOwner);

	/**
	 * 获取文件夹的相对路径
	 * 
	 * @param folderId 文件夹id
	 * @param folderOwner 文件夹所有者
	 * @return C:/A/B/
	 */
	public String getFolderRelativePath(String folderId, String folderOwner);
	
}