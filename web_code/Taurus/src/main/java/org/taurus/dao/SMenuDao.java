package org.taurus.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.taurus.entity.SMenuEntity;
import org.taurus.extendEntity.SMenuEntityEx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
public interface SMenuDao extends BaseMapper<SMenuEntity> {

	/**
	 * 获取用户能显示的菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<SMenuEntityEx> getMenuListByUser(@Param("userId") String userId);

	/**
	 * 获取菜单列表
	 * 
	 * @return
	 */
	public List<SMenuEntityEx> getMenuList();

	/**
	 * 根据id获取菜单信息
	 * 
	 * @param menuId
	 * @return
	 */
	public SMenuEntityEx getMenuDetail(@Param("menuId") String menuId);

}
