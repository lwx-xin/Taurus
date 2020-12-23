package org.taurus.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taurus.common.Code;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.MD5Util;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SAuthDao;
import org.taurus.dao.SUserDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SAuthUserEntity;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.extendEntity.SUserEntityEx;
import org.taurus.service.SAuthService;
import org.taurus.service.SAuthUserService;
import org.taurus.service.SUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserDao, SUserEntity> implements SUserService {

	@Resource
	private SUserDao userDao;

	@Resource
	private SAuthDao authDao;

	@Resource
	private SAuthService authService;

	@Resource
	private SAuthUserService authUserService;

	@Override
	public SUserEntity getUser(String userNumber, String userPwd) {

		SUserEntity userEntity = new SUserEntity();
		userEntity.setUserNumber(userNumber);
		userEntity.setUserPwd(MD5Util.getMD5(userPwd, userNumber));
		QueryWrapper<SUserEntity> queryWrapper = new QueryWrapper<SUserEntity>(userEntity);

		try {
			userEntity = getOne(queryWrapper);
		} catch (Exception e) {
			userEntity = null;
			String paramStr = "{'userNumber':" + userNumber + ",'userPwd':" + userPwd + "}";
			LoggerUtil.saveErrorLog(e, SUserServiceImpl.class, "getUser", JsonUtil.toEntity(paramStr, Map.class));
		}

		return userEntity;
	}

	@Override
	public List<SUserEntityEx> getUserList(SUserEntity userEntity) {
		return userDao.getUserList(userEntity);
	}

	@Override
	public SUserEntityEx getUserDetail(String userId) {
		return userDao.getUserDetail(userId);
	}

	@Override
	@Transactional
	public SUserEntityEx insert(SUserEntityEx userEntityEx, String operator) {

		// 将要添加的用户ID
		String willAddUserId = StrUtil.getUUID();
		// 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SUserEntity userEntity = new SUserEntity();
		userEntity.setUserId(willAddUserId);
		userEntity.setUserNumber(userEntityEx.getUserNumber());
		userEntity.setUserPwd(MD5Util.getMD5(userEntityEx.getUserPwd(), userEntityEx.getUserNumber()));
		userEntity.setUserName(userEntityEx.getUserName());
		userEntity.setUserHead(userEntityEx.getUserHead());
		userEntity.setUserPlatform(Code.PLATFORM_WEB_WINDOW.getValue());
		userEntity.setUserQq(userEntityEx.getUserQq());
		userEntity.setUserEmail(userEntityEx.getUserEmail());
		userEntity.setUserDelFlg(Code.DEL_FLG_1.getValue());
		userEntity.setUserCreateTime(nowTime);
		userEntity.setUserCreateUser(operator);
		userEntity.setUserModifyTime(nowTime);
		userEntity.setUserModifyUser(operator);

		boolean saveUser = save(userEntity);
		if (saveUser) {
			if (ListUtil.isNotEmpty(userEntityEx.getAuthList())) {
				// 要给用户赋予的权限
				List<String> authIdList = userEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId)
						.collect(Collectors.toList());

				// 要给用户赋予的权限--详细信息
				SAuthEntity authEntity = new SAuthEntity();
				authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
				QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
				queryWrapper.in("AUTH_ID", authIdList);
				List<SAuthEntity> allAuth = authService.list(queryWrapper);

				List<SAuthEntityEx> authList = new ArrayList<SAuthEntityEx>();
				// 给用户绑定权限
				List<SAuthUserEntity> authUserList = new ArrayList<SAuthUserEntity>();
				for (SAuthEntity auth : allAuth) {
					SAuthUserEntity authUserEntity = new SAuthUserEntity();
					authUserEntity.setAuthUserId(StrUtil.getUUID());
					authUserEntity.setAuthId(auth.getAuthId());
					authUserEntity.setAuthUserId(willAddUserId);
					authUserEntity.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
					authUserEntity.setAuthUserCreateUser(operator);
					authUserEntity.setAuthUserCreateTime(nowTime);
					authUserEntity.setAuthUserModifyUser(operator);
					authUserEntity.setAuthUserModifyTime(nowTime);
					authUserList.add(authUserEntity);

					authList.add(JsonUtil.toEntity(auth, SAuthEntityEx.class));
				}
				boolean saveAuthUser = authUserService.saveBatch(authUserList);
				if (saveAuthUser) {
					userEntityEx.setAuthList(authList);
				} else {
					throw new RuntimeException("用户权限关联表添加失败");
				}
			}
			return userEntityEx;
		} else {
			throw new RuntimeException("用户表添加失败");
		}
	}

	@Override
	@Transactional
	public SUserEntityEx update(String userId, SUserEntityEx userEntityEx, String operator) {
		// 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SUserEntity userEntity = JsonUtil.toEntity(userEntityEx, SUserEntity.class);
		userEntity.setUserModifyTime(nowTime);
		userEntity.setUserModifyUser(operator);
		boolean updateUser = updateById(userEntity);
		if (updateUser) {
			if (ListUtil.isNotEmpty(userEntityEx.getAuthList())) {
				// 要给用户赋予的权限
				List<String> authIdList = userEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId)
						.collect(Collectors.toList());

				// 要给用户赋予的权限--详细信息
				SAuthEntity authEntity = new SAuthEntity();
				authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
				QueryWrapper<SAuthEntity> queryWrapper_auth = new QueryWrapper<SAuthEntity>(authEntity);
				queryWrapper_auth.in("AUTH_ID", authIdList);
				List<SAuthEntity> allAuth = authService.list(queryWrapper_auth);

				// 当前用户的权限(修改前的权限)
				SAuthUserEntity authUserEntity_query = new SAuthUserEntity();
				authUserEntity_query.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
				QueryWrapper<SAuthUserEntity> queryWrapper_authUser = new QueryWrapper<SAuthUserEntity>(
						authUserEntity_query);
				List<SAuthUserEntity> updateBeforAuthUserList = authUserService.list(queryWrapper_authUser);
				List<String> updateBeforAuthUserList_authId = new ArrayList<String>();
				if (ListUtil.isNotEmpty(updateBeforAuthUserList_authId)) {
					updateBeforAuthUserList_authId.addAll(updateBeforAuthUserList.stream()
							.map(SAuthUserEntity::getAuthId).collect(Collectors.toList()));
				}

				if (ListUtil.isNotEmpty(allAuth)) {

					// 给用户绑定权限
					List<SAuthUserEntity> authUserList = new ArrayList<SAuthUserEntity>();
					for (SAuthEntity auth : allAuth) {
						String authId = auth.getAuthId();
						if (updateBeforAuthUserList_authId.contains(authId)) {
							updateBeforAuthUserList_authId.remove(authId);
						} else {
							SAuthUserEntity authUserEntity = new SAuthUserEntity();
							authUserEntity.setAuthUserId(StrUtil.getUUID());
							authUserEntity.setAuthId(authId);
							authUserEntity.setUserId(userId);
							authUserEntity.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
							authUserEntity.setAuthUserCreateUser(operator);
							authUserEntity.setAuthUserCreateTime(nowTime);
							authUserEntity.setAuthUserModifyUser(operator);
							authUserEntity.setAuthUserModifyTime(nowTime);
							authUserList.add(authUserEntity);
						}
					}
					if (ListUtil.isNotEmpty(authUserList)) {
						boolean saveAuthUser = authUserService.saveBatch(authUserList);
						if (saveAuthUser) {
							if (ListUtil.isNotEmpty(updateBeforAuthUserList_authId)) {
								QueryWrapper<SAuthUserEntity> updateWrapper = new QueryWrapper<SAuthUserEntity>();
								updateWrapper.in("AUTH_ID", updateBeforAuthUserList_authId);

								SAuthUserEntity authUserEntity_update = new SAuthUserEntity();
								authUserEntity_update.setAuthUserDelFlg(Code.DEL_FLG_0.getValue());
								authUserEntity_update.setAuthUserModifyTime(nowTime);
								authUserEntity_update.setAuthUserModifyUser(operator);
								boolean update = authUserService.update(authUserEntity_update, updateWrapper);
								if (!update) {
									throw new RuntimeException("用户权限关联表删除失败");
								}
							}
						} else {
							throw new RuntimeException("用户权限关联表添加失败");
						}
					}
				}
			} else {
				SAuthUserEntity authUserEntity_query = new SAuthUserEntity();
				authUserEntity_query.setUserId(userId);
				QueryWrapper<SAuthUserEntity> queryWrapper = new QueryWrapper<SAuthUserEntity>(authUserEntity_query);
				if (authUserService.count(queryWrapper)>0) {
					SAuthUserEntity authUserEntity_updateQuery = new SAuthUserEntity();
					authUserEntity_updateQuery.setUserId(userId);
					QueryWrapper<SAuthUserEntity> updateWrapper = new QueryWrapper<SAuthUserEntity>(authUserEntity_updateQuery);

					SAuthUserEntity authUserEntity_update = new SAuthUserEntity();
					authUserEntity_update.setAuthUserDelFlg(Code.DEL_FLG_0.getValue());
					authUserEntity_update.setAuthUserModifyTime(nowTime);
					authUserEntity_update.setAuthUserModifyUser(operator);
					boolean update = authUserService.update(authUserEntity_update, updateWrapper);
					if (!update) {
						throw new RuntimeException("用户权限关联表删除失败");
					}
				}
			}
			return userEntityEx;
		} else {
			throw new RuntimeException("用户表修改失败");
		}
	}

	@Override
	public void delete(String userId, String operator) {
		// 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SUserEntity userEntity = new SUserEntity();
		userEntity.setUserId(userId);
		userEntity.setUserDelFlg(Code.DEL_FLG_0.getValue());
		userEntity.setUserModifyTime(nowTime);
		userEntity.setUserModifyUser(operator);
		boolean updateById = updateById(userEntity);
		if (updateById) {
			throw new RuntimeException("用户表删除失败");
		}
	}

}
