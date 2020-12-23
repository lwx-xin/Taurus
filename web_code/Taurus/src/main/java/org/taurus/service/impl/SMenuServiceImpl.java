package org.taurus.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.MapUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SMenuDao;
import org.taurus.entity.SMenuEntity;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.service.SMenuService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
@Service
public class SMenuServiceImpl extends ServiceImpl<SMenuDao, SMenuEntity> implements SMenuService {
	
	@Resource
	private SMenuDao menuDao;

	@Override
	public List<SMenuEntityEx> getMenuListByUser(String userId) {
		List<SMenuEntity> menuSource = menuDao.getMenuListByUser(userId);
		
		List<SMenuEntityEx> menuList = new ArrayList<SMenuEntityEx>();
		
		Map<String, SMenuEntityEx> menuMap = new HashMap<String, SMenuEntityEx>();
		
		for (SMenuEntity menu : menuSource) {
			String menuParent = menu.getMenuParent();
			Integer menuOrder = menu.getMenuOrder();
			
			SMenuEntityEx menuEntityEx = JsonUtil.toEntity(JsonUtil.toMap(menu), SMenuEntityEx.class);
			if (StrUtil.isEmpty(menuParent)) {
				menuList.add(menuEntityEx);
			} else {
				menuMap.put(menuParent+"-"+menuOrder, menuEntityEx);
			}
		}
		
		for (SMenuEntityEx menu : menuList) {
			setChildrens(menu, menuMap);
		}
		
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	private void setChildrens(SMenuEntityEx nowNode, Map<String, SMenuEntityEx> menuMap) {
		String menuId = nowNode.getMenuId();
		List<SMenuEntityEx> childrens = MapUtil.get(menuMap, menuId+"-");
		for (SMenuEntityEx menu : childrens) {
			setChildrens(menu, menuMap);
		}
		nowNode.setChildrens(childrens);
	}
	
}
