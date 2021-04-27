package org.taurus.dao;

import org.apache.ibatis.annotations.Param;
import org.taurus.entity.SFolderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.taurus.extendEntity.SFolderEntityEx;

/**
 * <p>
 * 文件夹信息 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
public interface SFolderDao extends BaseMapper<SFolderEntity> {

    /**
     * 获取文件夹信息（子文件夹个数，子文件个数）
     * @param FolderId
     * @return
     */
    public SFolderEntityEx getFolderInfo(@Param("folderId")String FolderId);

}
