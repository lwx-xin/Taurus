package org.taurus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.entity.SFileEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文件信息 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 * @version v1.0
 */
@Service
public interface SFileService extends IService<SFileEntity> {

	/**
	 * 保存文件
	 * 
	 * @param file
	 * @param folderId  所属文件夹
	 * @param operator  操作人员
	 * @param fileOwner 文件所有者
	 * @return
	 */
	public SFileEntity saveFile(MultipartFile file, String folderId, String operator, String fileOwner);

	/**
	 * 保存用户头像文件
	 * 
	 * @param file
	 * @param userId
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

}