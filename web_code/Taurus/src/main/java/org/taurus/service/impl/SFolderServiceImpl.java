package org.taurus.service.impl;

import org.taurus.entity.SFolderEntity;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.dao.SFolderDao;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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

	@Resource
	private TaurusProperties taurusProperties;

	@Resource
	private SFolderService folderService;

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
		String systemFolderId = createFolder(taurusProperties.getFolderSystemName(), rootFolderId, folderOwner, operator);
		// aa 添加头像图片资源目录
		String headFolderId = createFolder(taurusProperties.getFolderHeadImgName(), systemFolderId, folderOwner, operator);
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
		if (!folderService.save(folderEntity)) {
			rootFolder.delete();
			throw new CustomException(ExecptionType.FOLDER, null, "添加目录失败");
		}

		return folderId;
	}

}
