package org.taurus.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.MD5Util;
import org.taurus.common.util.StrUtil;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.dao.SAuthDao;
import org.taurus.dao.SUserDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SAuthUserEntity;
import org.taurus.entity.SFileEntity;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.extendEntity.SUserEntityEx;
import org.taurus.service.SAuthService;
import org.taurus.service.SAuthUserService;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;
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

	@Autowired
	private TaurusProperties taurusProperties;

	@Autowired
	private SUserDao userDao;

	@Autowired
	private SAuthDao authDao;

	@Autowired
	private SAuthService authService;

	@Autowired
	private SAuthUserService authUserService;

	@Autowired
	private SFolderService folderService;

	@Autowired
	private SFileService fileService;

	@Override
	public SUserEntity getUser(String userNumber, String userPwd) {

		SUserEntity userEntity = new SUserEntity();
		userEntity.setUserNumber(userNumber);
		userEntity.setUserPwd(MD5Util.getMD5(userPwd, userNumber));
		QueryWrapper<SUserEntity> queryWrapper = new QueryWrapper<SUserEntity>(userEntity);

		userEntity = getOne(queryWrapper);

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
	public SUserEntityEx insert(SUserEntityEx userEntityEx, MultipartFile files, String operator) {

		// aa 将要添加的用户ID
		String willAddUserId = StrUtil.getUUID();
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		// aa 添加默认文件夹
		folderService.createInitFolder(willAddUserId, operator);

		// aa 上传头像文件
		SFileEntity saveHeadPicFile = fileService.saveHeadPicFile(files, willAddUserId, operator);

		SUserEntity userEntity = new SUserEntity();
		userEntity.setUserId(willAddUserId);
		userEntity.setUserNumber(userEntityEx.getUserNumber());
		userEntity.setUserPwd(MD5Util.getMD5(userEntityEx.getUserPwd(), userEntityEx.getUserNumber()));
		userEntity.setUserName(userEntityEx.getUserName());
		userEntity.setUserPlatform(Code.PLATFORM_WEB_WINDOW.getValue());
		userEntity.setUserQq(userEntityEx.getUserQq());
		userEntity.setUserEmail(userEntityEx.getUserEmail());
		userEntity.setUserDelFlg(Code.DEL_FLG_1.getValue());
		userEntity.setUserCreateTime(nowTime);
		userEntity.setUserCreateUser(operator);
		userEntity.setUserModifyTime(nowTime);
		userEntity.setUserModifyUser(operator);
		if (saveHeadPicFile != null) {
			userEntity.setUserHead(saveHeadPicFile.getFileId());
		}
		if (!save(userEntity)) {
			throw new CustomException(ExecptionType.USER, null, "用户表添加数据失败");
		}

		if (ListUtil.isNotEmpty(userEntityEx.getAuthList())) {
			// aa 要给用户赋予的权限
			List<String> authIdList = userEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId)
					.collect(Collectors.toList());

			// aa 要给用户赋予的权限--详细信息
			SAuthEntity authEntity = new SAuthEntity();
			authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
			queryWrapper.in("AUTH_ID", authIdList);
			List<SAuthEntity> allAuth = authService.list(queryWrapper);

			List<SAuthEntityEx> authList = new ArrayList<SAuthEntityEx>();
			// aa 给用户绑定权限
			List<SAuthUserEntity> authUserList = new ArrayList<SAuthUserEntity>();
			for (SAuthEntity auth : allAuth) {
				SAuthUserEntity authUserEntity = new SAuthUserEntity();
				authUserEntity.setAuthUserId(StrUtil.getUUID());
				authUserEntity.setAuthId(auth.getAuthId());
				authUserEntity.setUserId(willAddUserId);
				authUserEntity.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
				authUserEntity.setAuthUserCreateUser(operator);
				authUserEntity.setAuthUserCreateTime(nowTime);
				authUserEntity.setAuthUserModifyUser(operator);
				authUserEntity.setAuthUserModifyTime(nowTime);
				authUserList.add(authUserEntity);

				authList.add(JsonUtil.toEntity(auth, SAuthEntityEx.class));
			}
			if (!authUserService.saveBatch(authUserList)) {
				throw new CustomException(ExecptionType.USER_AUTH, null, "用户权限关联表添加数据失败");
			}
			userEntityEx.setAuthList(authList);
		}

		return userEntityEx;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public SUserEntityEx update(String userId, SUserEntityEx userEntityEx, MultipartFile files, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		// aa 上传头像文件
		SFileEntity saveHeadPicFile = fileService.saveHeadPicFile(files, userId, operator);

		SUserEntity userEntity = new SUserEntity();
		userEntity.setUserId(userId);
		userEntity.setUserName(userEntityEx.getUserName());
		userEntity.setUserPlatform(Code.PLATFORM_WEB_WINDOW.getValue());
		userEntity.setUserQq(userEntityEx.getUserQq());
		userEntity.setUserEmail(userEntityEx.getUserEmail());
		userEntity.setUserModifyTime(nowTime);
		userEntity.setUserModifyUser(operator);
		if (saveHeadPicFile != null) {
			userEntity.setUserHead(saveHeadPicFile.getFileId());
		}
		if (!updateById(userEntity)) {
			throw new CustomException(ExecptionType.USER, null, "用户表修改数据失败");
		}

		// aa 页面上选择的权限
		List<String> authIdList = null;
		A: if (ListUtil.isNotEmpty(userEntityEx.getAuthList())) {
			authIdList = userEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId).collect(Collectors.toList());

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

			// aa 获取当前用户的权限id（修改前）
			List<String> updateBeforAuthUserList_authId = null;
			SAuthUserEntity authUserEntity_query = new SAuthUserEntity();
			authUserEntity_query.setUserId(userId);
			authUserEntity_query.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthUserEntity> queryWrapper_authUser = new QueryWrapper<SAuthUserEntity>(
					authUserEntity_query);
			List<SAuthUserEntity> updateBeforAuthUserList = authUserService.list(queryWrapper_authUser);
			if (ListUtil.isNotEmpty(updateBeforAuthUserList)) {
				updateBeforAuthUserList_authId = updateBeforAuthUserList.stream().map(SAuthUserEntity::getAuthId)
						.collect(Collectors.toList());
			}

			// aa 要删除的权限
			List<String> deleteAuth = null;
			// aa 要新增的权限
			List<String> insertAuth = null;
			if (ListUtil.isNotEmpty(updateBeforAuthUserList_authId)) {
				deleteAuth = ListUtil.except(updateBeforAuthUserList_authId, authIdList);
				insertAuth = ListUtil.except(authIdList, updateBeforAuthUserList_authId);
			} else {
				insertAuth = new ArrayList<>();
				insertAuth.addAll(authIdList);
			}

			// aa 删除权限 - 逻辑删除
			if (ListUtil.isNotEmpty(deleteAuth)) {
				for (String authId : deleteAuth) {
					SAuthUserEntity authUserEntity = new SAuthUserEntity();
					authUserEntity.setAuthId(authId);
					authUserEntity.setUserId(userId);

					QueryWrapper<SAuthUserEntity> queryWrapper = new QueryWrapper<>(authUserEntity);

					authUserEntity = new SAuthUserEntity();
					authUserEntity.setAuthUserDelFlg(Code.DEL_FLG_0.getValue());
					if (!authUserService.update(authUserEntity, queryWrapper)) {
						throw new CustomException(ExecptionType.USER_AUTH, null, "用户权限关联表删除数据失败");
					}
				}
			}

			// aa 新增权限
			if (ListUtil.isNotEmpty(insertAuth)) {
				List<SAuthUserEntity> authUserList = new ArrayList<>();
				for (String authId : insertAuth) {
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
				if (!authUserService.saveBatch(authUserList)) {
					throw new CustomException(ExecptionType.USER_AUTH, null, "用户权限关联表添加数据失败");
				}
			}
		}

		// aa 删除当前用户全部权限
		if (ListUtil.isEmpty(authIdList)) {
			SAuthUserEntity authUserEntity_query = new SAuthUserEntity();
			authUserEntity_query.setUserId(userId);
			QueryWrapper<SAuthUserEntity> queryWrapper = new QueryWrapper<SAuthUserEntity>(authUserEntity_query);
			if (authUserService.count(queryWrapper) > 0) {
				SAuthUserEntity authUserEntity_updateQuery = new SAuthUserEntity();
				authUserEntity_updateQuery.setUserId(userId);
				QueryWrapper<SAuthUserEntity> updateWrapper = new QueryWrapper<SAuthUserEntity>(
						authUserEntity_updateQuery);

				SAuthUserEntity authUserEntity_update = new SAuthUserEntity();
				authUserEntity_update.setAuthUserDelFlg(Code.DEL_FLG_0.getValue());
				authUserEntity_update.setAuthUserModifyTime(nowTime);
				authUserEntity_update.setAuthUserModifyUser(operator);
				if (!authUserService.update(authUserEntity_update, updateWrapper)) {
					throw new CustomException(ExecptionType.USER_AUTH, null, "用户权限关联表删除数据失败");
				}
			}
		}
		return userEntityEx;

	}

	@Override
	@Transactional
	public void lock_unLock(String userId, String operator) {
		String msg = "";
		SUserEntity userEntity = getById(userId);
		String userDelFlg = userEntity.getUserDelFlg();
		if (Code.DEL_FLG_0.getValue().equals(userDelFlg)) {
			userDelFlg = Code.DEL_FLG_1.getValue();
			msg = "启用用户失败";
		} else {
			userDelFlg = Code.DEL_FLG_0.getValue();
			msg = "禁用用户失败";
		}

		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		userEntity.setUserDelFlg(userDelFlg);
		userEntity.setUserModifyTime(nowTime);
		userEntity.setUserModifyUser(operator);
		if (!updateById(userEntity)) {
			throw new CustomException(ExecptionType.USER, null, msg);
		}
	}

}
