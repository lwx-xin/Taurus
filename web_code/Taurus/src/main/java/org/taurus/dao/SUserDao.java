package org.taurus.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SUserEntityEx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
public interface SUserDao extends BaseMapper<SUserEntity> {
	
	/**
	 * 获取用户列表(逻辑删除的也可以查询)
	 * @param userEntity
	 * @return
	 */
	public List<SUserEntityEx> getUserList(SUserEntity userEntity);

	/**
	 * 获取用户详细信息(逻辑删除的也可以查询)
	 * @param userId
	 * @return
	 */
	public SUserEntityEx getUserDetail(@Param("userId")String userId);

}
