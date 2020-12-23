package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SUrlEntity;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统请求 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 * @version v1.0
 */
@Service
public interface SUrlService extends IService<SUrlEntity> {

	/**
	 * 查询当前权限下能够访问的请求
	 * 
	 * @param authId
	 * @return
	 */
	public List<SUrlEntity> getUrlByAuth(String authId);

	/**
	 * 查询当前权限下能够访问的请求
	 * 
	 * @param authIds
	 * @return
	 */
	public List<SUrlEntity> getUrlByAuths(List<String> auths);

	/**
	 * 查询当前用户能够访问的请求
	 * 
	 * @param userId
	 * @return
	 */
	public List<SUrlEntity> getUrlByUser(String userId);

}