package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SUrlEntity;
import org.taurus.extendEntity.SUrlEntityEx;

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

	/**
	 * 获取请求列表
	 * 
	 * @param urlEntity
	 * @return
	 */
	public List<SUrlEntityEx> getUrlList(SUrlEntity urlEntity);

	/**
	 * 根据请求id获取详细信息
	 * 
	 * @param urlId
	 * @return
	 */
	public SUrlEntityEx getUrlDetail(String urlId);

	/**
	 * 新增请求
	 * 
	 * @param urlEntityEx
	 * @param operator    操作人员
	 * @return
	 */
	public SUrlEntityEx insert(SUrlEntityEx urlEntityEx, String operator);

	/**
	 * 编辑请求
	 * 
	 * @param urlId
	 * @param urlEntityEx
	 * @param operator    操作人员
	 * @return
	 */
	public SUrlEntityEx update(String urlId, SUrlEntityEx urlEntityEx, String operator);

}