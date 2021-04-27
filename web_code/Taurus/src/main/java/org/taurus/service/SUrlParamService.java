package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SUrlParamEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 请求参数 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 * @version v1.0
 */
public interface SUrlParamService extends IService<SUrlParamEntity> {

	/**
	 * 获取请求参数列表
	 * 
	 * @param urlParamEntity
	 * @return
	 */
	List<SUrlParamEntity> getUrlParamList(SUrlParamEntity urlParamEntity);

	/**
	 * 根据id获取请求参数信息
	 * 
	 * @param urlParamId
	 * @return
	 */
	SUrlParamEntity getURLParamDetail(String urlParamId);

	/**
	 * 添加请求参数信息
	 * 
	 * @param urlParamEntity
	 * @param operator       操作人员
	 * @return
	 */
	SUrlParamEntity insert(SUrlParamEntity urlParamEntity, String operator);

	/**
	 * 修改请求参数信息
	 * 
	 * @param urlParamId
	 * @param urlParamEntity
	 * @param operator       操作人员
	 * @return
	 */
	SUrlParamEntity update(String urlParamId, SUrlParamEntity urlParamEntity, String operator);

}