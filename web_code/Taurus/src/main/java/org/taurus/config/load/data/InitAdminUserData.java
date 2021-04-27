package org.taurus.config.load.data;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.taurus.common.code.Code;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.MD5Util;
import org.taurus.common.util.StrUtil;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SAuthUserEntity;
import org.taurus.entity.SUserEntity;
import org.taurus.service.SAuthService;
import org.taurus.service.SAuthUserService;
import org.taurus.service.SFolderService;
import org.taurus.service.SUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Configuration
public class InitAdminUserData {

	/**
	 * 当前时间
	 */
	private final LocalDateTime nowTime = DateUtil.getLocalDateTime();

	@Autowired
	private TaurusProperties taurusProperties;

	@Autowired
	private SUserService userService;

	@Autowired
	private SAuthService authService;

	@Autowired
	private SAuthUserService authUserService;
	
	@Autowired
	private SFolderService folderService;
	
	@Transactional
	public void initData() {
		initUser();
		initAuth();
		initAuthUser();
	}

	public String initUser() {
		String accountId = taurusProperties.getAccountId();

		if (userService.getById(accountId) == null) {
			String accountNumber = taurusProperties.getAccountNumber();
			String accountPwd = taurusProperties.getAccountPwd();
			String accountName = taurusProperties.getAccountName();

			SUserEntity userEntity = new SUserEntity();
			userEntity.setUserId(accountId);
			userEntity.setUserNumber(accountNumber);
			userEntity.setUserPwd(MD5Util.getMD5(accountPwd, accountNumber));
			userEntity.setUserName(accountName);
			userEntity.setUserPlatform(Code.PLATFORM_WEB_ALL.getValue());
			userEntity.setUserDelFlg(Code.DEL_FLG_1.getValue());
			userEntity.setUserCreateTime(nowTime);
			userEntity.setUserCreateUser(accountId);
			userEntity.setUserModifyTime(nowTime);
			userEntity.setUserModifyUser(accountId);

			if (userService.save(userEntity)) {
				folderService.createInitFolder(accountId, accountId);
			}
		}
		return accountId;
	}

	public String initAuth() {
		String accountId = taurusProperties.getAccountId();
		String adminAuthId = taurusProperties.getAdminAuthId();

		if (authService.getById(adminAuthId) == null) {
			String adminAuthName = taurusProperties.getAdminAuthName();
			String adminAuthLevel = taurusProperties.getAdminAuthLevel();

			SAuthEntity authEntity = new SAuthEntity();
			authEntity.setAuthId(adminAuthId);
			authEntity.setAuthName(adminAuthName);
			authEntity.setAuthLevel(adminAuthLevel);
			authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			authEntity.setAuthCreateTime(nowTime);
			authEntity.setAuthCreateUser(accountId);
			authEntity.setAuthModifyTime(nowTime);
			authEntity.setAuthModifyUser(accountId);

			authService.save(authEntity);
		}
		return adminAuthId;
	}

	public String initAuthUser() {
		String authUserId = "";
		String accountId = taurusProperties.getAccountId();
		String adminAuthId = taurusProperties.getAdminAuthId();

		SAuthUserEntity authUserEntityQuery = new SAuthUserEntity();
		authUserEntityQuery.setAuthId(adminAuthId);
		authUserEntityQuery.setUserId(accountId);
		authUserEntityQuery.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SAuthUserEntity> queryWrapper = new QueryWrapper<SAuthUserEntity>(authUserEntityQuery);
		SAuthUserEntity authUserEntity = authUserService.getOne(queryWrapper);
		if (authUserEntity == null) {
			authUserEntity = new SAuthUserEntity();
			authUserId = StrUtil.getUUID();
			authUserEntity.setAuthUserId(authUserId);
			authUserEntity.setAuthId(adminAuthId);
			authUserEntity.setUserId(accountId);
			authUserEntity.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
			authUserEntity.setAuthUserCreateTime(nowTime);
			authUserEntity.setAuthUserCreateUser(accountId);
			authUserEntity.setAuthUserModifyTime(nowTime);
			authUserEntity.setAuthUserModifyUser(accountId);

			authUserService.save(authUserEntity);
		}
		authUserId = authUserEntity.getAuthUserId();
		return authUserId;
	}

}
