package org.taurus.dao;

import java.util.List;

import org.taurus.entity.SAuthEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统权限 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
public interface SAuthDao extends BaseMapper<SAuthEntity> {
	
	/**
	 * 获取用户权限
	 * @param userId
	 * @param delFlg
	 * @return
	 */
	public List<SAuthEntity> getAuthByUserId(String userId, String delFlg);

}
