package org.taurus.dao;

import org.apache.ibatis.annotations.Param;
import org.taurus.entity.SFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文件信息 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
public interface SFileDao extends BaseMapper<SFileEntity> {

    public int removeByParentFolder(@Param("folderId") String folderId);

}
