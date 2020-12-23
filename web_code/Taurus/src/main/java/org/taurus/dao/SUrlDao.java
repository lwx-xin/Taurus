package org.taurus.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.taurus.entity.SUrlEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统请求 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
public interface SUrlDao extends BaseMapper<SUrlEntity> {

	/**
	 * 查询当前权限下能够访问的请求
	 * 
	 * @param authId
	 * @return
	 */
	public List<SUrlEntity> getUrlByAuth(@Param("authId") String authId);

	/**
	 * 查询当前权限下能够访问的请求
	 * 
	 * @param authIds
	 * @return
	 */
	public List<SUrlEntity> getUrlByAuths(@Param("authIds") List<String> authIds);

	/**
	 * 查询当前用户能够访问的请求
	 * 
	 * @param userId
	 * @return
	 */
	public List<SUrlEntity> getUrlByUser(@Param("userId") String userId);

}
