package org.taurus.dao;

import java.util.List;

import org.taurus.entity.SUrlParamEntity;
import org.taurus.extendEntity.SUrlParamEntityEx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 请求参数 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
public interface SUrlParamDao extends BaseMapper<SUrlParamEntity> {

	/**
	 * 关联请求表，查询数据
	 * 
	 * @param urlParamEntity
	 * @return
	 */
	public List<SUrlParamEntityEx> getList_url(SUrlParamEntity urlParamEntity);

}
