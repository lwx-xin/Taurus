package org.taurus.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.MapUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SMenuDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SAuthMenuEntity;
import org.taurus.entity.SMenuEntity;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.service.SAuthMenuService;
import org.taurus.service.SAuthService;
import org.taurus.service.SMenuService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

	@Autowired
	private SMenuDao menuDao;

	@Autowired
	private SAuthService authService;

	@Autowired
	private SAuthMenuService authMenuService;

	@Override
	public List<SMenuEntityEx> getMenuList() {
		List<SMenuEntityEx> menuList = menuDao.getMenuList();
		return processingMenuData(menuList);
	}

	@Override
	public List<SMenuEntityEx> processingMenuData(List<SMenuEntityEx> menuSource) {

		List<SMenuEntityEx> menuList = new ArrayList<SMenuEntityEx>();

		if (ListUtil.isNotEmpty(menuSource)) {

			Map<String, SMenuEntityEx> menuMap = new HashMap<String, SMenuEntityEx>();

			for (SMenuEntityEx menu : menuSource) {
				String menuParent = menu.getMenuParent();
				Integer menuOrder = menu.getMenuOrder();

				if (StrUtil.isEmpty(menuParent)) {
					menuList.add(menu);
				} else {
					menuMap.put(menuParent + "-" + menuOrder, menu);
				}
			}

			for (SMenuEntityEx menu : menuList) {
				setChildrens(menu, menuMap);
			}
		}

		return menuList;
	}

	@SuppressWarnings("unchecked")
	private void setChildrens(SMenuEntityEx nowNode, Map<String, SMenuEntityEx> menuMap) {
		String menuId = nowNode.getMenuId();
		List<SMenuEntityEx> childrens = MapUtil.get(menuMap, menuId + "-");
		for (SMenuEntityEx menu : childrens) {
			setChildrens(menu, menuMap);
		}
		nowNode.setChildrens(childrens);
	}

	@Override
	public SMenuEntityEx getMenuDetail(String menuId) {
		return menuDao.getMenuDetail(menuId);
	}

	@Override
	public SMenuEntityEx insert(SMenuEntityEx menuEntityEx, String operator) {

		// aa 将要添加的菜单ID
		String willAddMenuId = StrUtil.getUUID();
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SMenuEntity menuEntity_insert = new SMenuEntity();
		menuEntity_insert.setMenuId(willAddMenuId);
		menuEntity_insert.setMenuText(menuEntityEx.getMenuText());
		menuEntity_insert.setMenuParent(menuEntityEx.getMenuParent());
		menuEntity_insert.setMenuOrder(menuEntityEx.getMenuOrder());
		menuEntity_insert.setMenuUrl(menuEntityEx.getMenuUrl());
		menuEntity_insert.setMenuGroup(menuEntityEx.getMenuGroup());
		menuEntity_insert.setMenuIcon(menuEntityEx.getMenuIcon());
		menuEntity_insert.setMenuDelFlg(Code.DEL_FLG_1.getValue());
		menuEntity_insert.setMenuCreateTime(nowTime);
		menuEntity_insert.setMenuCreateUser(operator);
		menuEntity_insert.setMenuModifyTime(nowTime);
		menuEntity_insert.setMenuModifyUser(operator);

		if (!save(menuEntity_insert)) {
			throw new CustomException(ExecptionType.MENU, null, "菜单表添加数据失败");
		}

		if (ListUtil.isNotEmpty(menuEntityEx.getAuthList())) {
			// aa 要给菜单赋予的权限id
			List<String> authIdList = menuEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId)
					.collect(Collectors.toList());

			// aa 要给菜单赋予的权限--详细信息
			SAuthEntity authEntity = new SAuthEntity();
			authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
			queryWrapper.in("AUTH_ID", authIdList);
			List<SAuthEntity> allAuth = authService.list(queryWrapper);

			List<SAuthEntity> authList = new ArrayList<SAuthEntity>();
			// aa 给菜单绑定权限
			List<SAuthMenuEntity> authMenuList = new ArrayList<SAuthMenuEntity>();
			for (SAuthEntity auth : allAuth) {
				SAuthMenuEntity authUserEntity = new SAuthMenuEntity();
				authUserEntity.setAuthMenuId(StrUtil.getUUID());
				authUserEntity.setAuthId(auth.getAuthId());
				authUserEntity.setMenuId(willAddMenuId);
				authUserEntity.setAuthMenuDelFlg(Code.DEL_FLG_1.getValue());
				authUserEntity.setAuthMenuCreateUser(operator);
				authUserEntity.setAuthMenuCreateTime(nowTime);
				authUserEntity.setAuthMenuModifyUser(operator);
				authUserEntity.setAuthMenuModifyTime(nowTime);
				authMenuList.add(authUserEntity);

				authList.add(auth);
			}
			if (!authMenuService.saveBatch(authMenuList)) {
				throw new CustomException(ExecptionType.USER_AUTH, null, "菜单权限关联表添加数据失败");
			}
			menuEntityEx.setAuthList(authList);
		}

		return menuEntityEx;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SMenuEntityEx update(String menuId, SMenuEntityEx menuEntityEx, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SMenuEntity menuEntity_update = new SMenuEntity();
		menuEntity_update.setMenuId(menuId);
		menuEntity_update.setMenuText(menuEntityEx.getMenuText());
		menuEntity_update.setMenuOrder(menuEntityEx.getMenuOrder());
		menuEntity_update.setMenuUrl(menuEntityEx.getMenuUrl());
		menuEntity_update.setMenuGroup(menuEntityEx.getMenuGroup());
		menuEntity_update.setMenuIcon(menuEntityEx.getMenuIcon());
		menuEntity_update.setMenuModifyTime(nowTime);
		menuEntity_update.setMenuModifyUser(operator);
		if (!updateById(menuEntity_update)) {
			throw new CustomException(ExecptionType.USER, null, "菜单表修改数据失败");
		}

		// aa 页面上选择的权限
		List<String> authIdList = null;
		A: if (ListUtil.isNotEmpty(menuEntityEx.getAuthList())) {
			authIdList = menuEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId).collect(Collectors.toList());

			// aa 过滤掉不存在的权限
			SAuthEntity authEntity = new SAuthEntity();
			authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthEntity> queryWrapper_auth = new QueryWrapper<SAuthEntity>(authEntity);
			queryWrapper_auth.in("AUTH_ID", authIdList);
			List<SAuthEntity> allAuth = authService.list(queryWrapper_auth);
			if (ListUtil.isNotEmpty(allAuth)) {
				authIdList = allAuth.stream().map(SAuthEntity::getAuthId).collect(Collectors.toList());
			} else {
				break A;
			}

			// aa 获取当前菜单的权限id（修改前）
			List<String> updateBeforAuthMenuList_authId = null;
			SAuthMenuEntity authMenuEntity_query = new SAuthMenuEntity();
			authMenuEntity_query.setMenuId(menuId);
			authMenuEntity_query.setAuthMenuDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthMenuEntity> queryWrapper_authMenu = new QueryWrapper<SAuthMenuEntity>(
					authMenuEntity_query);
			List<SAuthMenuEntity> updateBeforAuthMenuList = authMenuService.list(queryWrapper_authMenu);
			if (ListUtil.isNotEmpty(updateBeforAuthMenuList)) {
				updateBeforAuthMenuList_authId = updateBeforAuthMenuList.stream().map(SAuthMenuEntity::getAuthId)
						.collect(Collectors.toList());
			}

			// aa 要删除的权限
			List<String> deleteAuth = null;
			// aa 要新增的权限
			List<String> insertAuth = null;
			if (ListUtil.isNotEmpty(updateBeforAuthMenuList_authId)) {
				deleteAuth = ListUtil.except(updateBeforAuthMenuList_authId, authIdList);
				insertAuth = ListUtil.except(authIdList, updateBeforAuthMenuList_authId);
			} else {
				insertAuth = new ArrayList<>();
				insertAuth.addAll(authIdList);
			}

			// aa 删除权限 - 逻辑删除
			if (ListUtil.isNotEmpty(deleteAuth)) {
				for (String authId : deleteAuth) {
					SAuthMenuEntity authMenuEntity = new SAuthMenuEntity();
					authMenuEntity.setAuthId(authId);
					authMenuEntity.setMenuId(menuId);

					QueryWrapper<SAuthMenuEntity> queryWrapper = new QueryWrapper<>(authMenuEntity);

					authMenuEntity = new SAuthMenuEntity();
					authMenuEntity.setAuthMenuDelFlg(Code.DEL_FLG_0.getValue());
					if (!authMenuService.update(authMenuEntity, queryWrapper)) {
						throw new CustomException(ExecptionType.USER_AUTH, null, "菜单权限关联表删除数据失败");
					}
				}
			}

			// aa 新增权限
			if (ListUtil.isNotEmpty(insertAuth)) {
				List<SAuthMenuEntity> authMenuList = new ArrayList<>();
				for (String authId : insertAuth) {
					SAuthMenuEntity authMenuEntity = new SAuthMenuEntity();
					authMenuEntity.setAuthMenuId(StrUtil.getUUID());
					authMenuEntity.setAuthId(authId);
					authMenuEntity.setMenuId(menuId);
					authMenuEntity.setAuthMenuDelFlg(Code.DEL_FLG_1.getValue());
					authMenuEntity.setAuthMenuCreateUser(operator);
					authMenuEntity.setAuthMenuCreateTime(nowTime);
					authMenuEntity.setAuthMenuModifyUser(operator);
					authMenuEntity.setAuthMenuModifyTime(nowTime);
					authMenuList.add(authMenuEntity);
				}
				if (!authMenuService.saveBatch(authMenuList)) {
					throw new CustomException(ExecptionType.USER_AUTH, null, "菜单权限关联表添加数据失败");
				}
			}
		}

		// aa 删除当前菜单全部权限
		if (ListUtil.isEmpty(authIdList)) {
			SAuthMenuEntity authMenuEntity_query = new SAuthMenuEntity();
			authMenuEntity_query.setMenuId(menuId);
			QueryWrapper<SAuthMenuEntity> queryWrapper = new QueryWrapper<SAuthMenuEntity>(authMenuEntity_query);
			if (authMenuService.count(queryWrapper) > 0) {
				SAuthMenuEntity authMenuEntity_updateQuery = new SAuthMenuEntity();
				authMenuEntity_updateQuery.setMenuId(menuId);
				QueryWrapper<SAuthMenuEntity> updateWrapper = new QueryWrapper<SAuthMenuEntity>(
						authMenuEntity_updateQuery);

				SAuthMenuEntity authMenuEntity_update = new SAuthMenuEntity();
				authMenuEntity_update.setAuthMenuDelFlg(Code.DEL_FLG_0.getValue());
				authMenuEntity_update.setAuthMenuModifyTime(nowTime);
				authMenuEntity_update.setAuthMenuModifyUser(operator);
				if (!authMenuService.update(authMenuEntity_update, updateWrapper)) {
					throw new CustomException(ExecptionType.USER_AUTH, null, "菜单权限关联表删除数据失败");
				}
			}
		}
		return menuEntityEx;

	}

	@Override
	public void lock_unLock(String menuId, String operator) {
		String msg = "";
		SMenuEntity menuEntity = getById(menuId);
		String menuDelFlg = menuEntity.getMenuDelFlg();
		if (Code.DEL_FLG_0.getValue().equals(menuDelFlg)) {
			menuDelFlg = Code.DEL_FLG_1.getValue();
			msg = "启用目录失败";
		} else {
			menuDelFlg = Code.DEL_FLG_0.getValue();
			msg = "删除目录失败";
		}

		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		// aa 当前菜单下的全部菜单
		List<SMenuEntity> menuAllChildren = getMenuAllChildren(menuId);
		if (menuAllChildren == null) {
			menuAllChildren = new ArrayList<>();
		}
		menuAllChildren.add(menuEntity);
		for (SMenuEntity menu : menuAllChildren) {
			menu.setMenuDelFlg(menuDelFlg);
			menu.setMenuModifyTime(nowTime);
			menu.setMenuModifyUser(operator);
		}

		if (!updateBatchById(menuAllChildren)) {
			throw new CustomException(ExecptionType.USER, null, msg);
		}
	}

	@Override
	public List<SMenuEntity> getMenuChildren(String menuId) {
		SMenuEntity queryEntity = new SMenuEntity();
		queryEntity.setMenuDelFlg(Code.DEL_FLG_1.getValue());
		Wrapper<SMenuEntity> queryWrapper = new QueryWrapper<SMenuEntity>(queryEntity);
		List<SMenuEntity> allMenu = list(queryWrapper);
		return getMenuChildren(allMenu, new ArrayList<>(Arrays.asList(menuId)));
	}

	@Override
	public List<SMenuEntity> getMenuAllChildren(String menuId) {
		List<SMenuEntity> menuList = new ArrayList<>();

		SMenuEntity queryEntity = new SMenuEntity();
		queryEntity.setMenuDelFlg(Code.DEL_FLG_1.getValue());
		Wrapper<SMenuEntity> queryWrapper = new QueryWrapper<SMenuEntity>(queryEntity);
		List<SMenuEntity> allMenu = list(queryWrapper);

		if (ListUtil.isNotEmpty(allMenu)) {
			List<SMenuEntity> menuChildren = getMenuChildren(allMenu, new ArrayList<>(Arrays.asList(menuId)));
			while (ListUtil.isNotEmpty(menuChildren)) {
				menuList.addAll(menuChildren);
				List<String> menuIds = menuChildren.stream().map(SMenuEntity::getMenuId).collect(Collectors.toList());
				menuChildren = getMenuChildren(allMenu, menuIds);
			}
		}

		return menuList;
	}

	/**
	 * 获取菜单下的子菜单
	 * 
	 * @param allMenu
	 * @param menuIds
	 * @return
	 */
	private List<SMenuEntity> getMenuChildren(List<SMenuEntity> allMenu, List<String> menuIds) {
		List<SMenuEntity> menuList = new ArrayList<>();
		if (ListUtil.isNotEmpty(menuIds) && ListUtil.isNotEmpty(allMenu)) {
			for (SMenuEntity menu : allMenu) {
				String menuParent = menu.getMenuParent();
				if (menuIds.contains(menuParent)) {
					menuList.add(menu);
				}
			}
		}
		return menuList;
	}

}
