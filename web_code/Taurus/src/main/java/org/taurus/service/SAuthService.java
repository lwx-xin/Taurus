package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SAuthEntity;
import org.taurus.extendEntity.SAuthEntityEx;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统权限 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 * @version v1.0
 */
@Service
public interface SAuthService extends IService<SAuthEntity> {

	/**
	 * 获取用户的权限ID
	 * 
	 * @param userId 用户id
	 * @return
	 */
	List<String> getAuthByUserId(String userId);

	/**
	 * 获取权限列表
	 * 
	 * @param authEntity
	 * @return
	 */
	List<SAuthEntityEx> getAuthList(SAuthEntity authEntity);

	/**
	 * 根据id获取权限信息
	 * 
	 * @param authId 权限id
	 * @return
	 */
	SAuthEntityEx getAuthDetail(String authId);

	/**
	 * 新增权限
	 * 
	 * @param authEntity
	 * @param operator   操作人员
	 * @return
	 */
	SAuthEntityEx insert(SAuthEntityEx authEntityEx, String operator);

	/**
	 * 新增权限
	 * 
	 * @param authEntity
	 * @param operator   操作人员
	 * @return
	 */
	SAuthEntityEx update(String authId, SAuthEntityEx authEntityEx, String operator);

	/**
	 * 禁用启用-权限(如果有【用户，请求，菜单】使用过该权限将无法禁用)
	 * 
	 * @param authId   权限id
	 * @param operator 操作人员
	 */
	void lock_unLock(String authId, String operator);

}