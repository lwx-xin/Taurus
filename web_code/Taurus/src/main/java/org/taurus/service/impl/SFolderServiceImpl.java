package org.taurus.service.impl;

import org.taurus.entity.SFolderEntity;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.load.properties.TaurusProperties;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SFolderDao;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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

	@Override
	public String getFolderPath(String folderId, String folderOwner) {
		// aa 文件夹的相对路径
		String folderRelativePath = getFolderRelativePath(folderId, folderOwner);
		
		return  taurusProperties.getFolderRoot() + folderRelativePath;
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

		if (StrUtil.isEmpty(folderPath)) {
			throw new CustomException(ExecptionType.FOLDER, null, "文件夹路径获取失败");
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

}
