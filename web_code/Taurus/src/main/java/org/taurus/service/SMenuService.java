package org.taurus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.taurus.entity.SMenuEntity;
import org.taurus.extendEntity.SMenuEntityEx;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 * @version v1.0
 */
@Service
public interface SMenuService extends IService<SMenuEntity> {

	/**
	 * 获取菜单列表
	 * 
	 * @return
	 */
	public List<SMenuEntityEx> getMenuList();

	/**
	 * 转换菜单列表数据格式（树形结构）
	 * 
	 * @param menuSource
	 * @return
	 */
	public List<SMenuEntityEx> processingMenuData(List<SMenuEntityEx> menuSource);

	/**
	 * 根据id获取菜单信息
	 * 
	 * @param menuId
	 * @return
	 */
	public SMenuEntityEx getMenuDetail(String menuId);

}