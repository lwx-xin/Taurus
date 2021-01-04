package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SUserEntityEx;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 * @version v1.0
 */
@Service
public interface SUserService extends IService<SUserEntity> {

	/**
	 * 根据账号密码获取用户信息(如果有多条信息满足条件，会抛出异常)
	 * 
	 * @param userNumber 账号
	 * @param userPwd    密码
	 * @return
	 */
	public SUserEntity getUser(String userNumber, String userPwd);

	/**
	 * 获取用户列表
	 * 
	 * @param userEntity
	 * @return
	 */
	public List<SUserEntityEx> getUserList(SUserEntity userEntity);

	/**
	 * 获取用户详细信息(逻辑删除的也可以查询)
	 * 
	 * @param userId
	 * @return
	 */
	public SUserEntityEx getUserDetail(String userId);

	/**
	 * 新增用户
	 * 
	 * @param userEntityEx
	 * @param files        头像
	 * @param operator     操作人员
	 * @return
	 */
	public SUserEntityEx insert(SUserEntityEx userEntityEx, MultipartFile files, String operator);

	/**
	 * 修改用户
	 * 
	 * @param userId
	 * @param userEntityEx
	 * @param files        头像
	 * @param operator     操作人员
	 * @return
	 */
	public SUserEntityEx update(String userId, SUserEntityEx userEntityEx, MultipartFile files, String operator);

	/**
	 * 禁用启用-用户账号
	 * 
	 * @param userId
	 * @param operator
	 */
	public void lock_unLock(String userId, String operator);

}