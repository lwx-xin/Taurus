package org.taurus.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SAuthDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SAuthMenuEntity;
import org.taurus.entity.SAuthUrlEntity;
import org.taurus.entity.SAuthUserEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.service.SAuthMenuService;
import org.taurus.service.SAuthService;
import org.taurus.service.SAuthUrlService;
import org.taurus.service.SAuthUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统权限 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
@Service
public class SAuthServiceImpl extends ServiceImpl<SAuthDao, SAuthEntity> implements SAuthService {

	@Autowired
	private SAuthDao authDao;

	@Autowired
	private SAuthUrlService authUrlService;

	@Autowired
	private SAuthUserService authUserService;

	@Autowired
	private SAuthMenuService authMenuService;

	@Override
	public List<String> getAuthByUserId(String userId) {

		List<SAuthEntity> authEntityList = authDao.getAuthByUserId(userId, Code.DEL_FLG_1.getValue());

		if (ListUtil.isNotEmpty(authEntityList)) {
			return authEntityList.stream().map(SAuthEntity::getAuthId).collect(Collectors.toList());
		}

		return null;
	}

	@Override
	public List<String> getAuthNameByUserId(String userId) {

		List<SAuthEntity> authEntityList = authDao.getAuthByUserId(userId, Code.DEL_FLG_1.getValue());

		if (ListUtil.isNotEmpty(authEntityList)) {
			return authEntityList.stream().map(SAuthEntity::getAuthName).collect(Collectors.toList());
		}

		return null;
	}

	@Override
	public List<SAuthEntityEx> getAuthList(SAuthEntity authEntity) {
		QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
		queryWrapper.orderByDesc("AUTH_MODIFY_TIME");
		List<SAuthEntity> data = list(queryWrapper);
		return JsonUtil.toList(data, SAuthEntityEx.class);
	}

	@Override
	public SAuthEntityEx getAuthDetail(String authId) {
		SAuthEntity authEntity = getById(authId);
		return JsonUtil.toEntity(authEntity, SAuthEntityEx.class);
	}

	@Override
	public SAuthEntityEx insert(SAuthEntityEx authEntityEx, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SAuthEntity authEntity = new SAuthEntity();
		authEntity.setAuthId(StrUtil.getUUID());
		authEntity.setAuthName(authEntityEx.getAuthName());
		authEntity.setAuthLevel(authEntityEx.getAuthLevel());
		authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
		authEntity.setAuthCreateTime(nowTime);
		authEntity.setAuthCreateUser(operator);
		authEntity.setAuthModifyTime(nowTime);
		authEntity.setAuthModifyUser(operator);

		if (save(authEntity)) {
			throw new CustomException(ExecptionType.AUTH, null, "新增权限失败");
		}
		return JsonUtil.toEntity(authEntity, SAuthEntityEx.class);
	}

	@Override
	public SAuthEntityEx update(String authId, SAuthEntityEx authEntityEx, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SAuthEntity authEntity = new SAuthEntity();
		authEntity.setAuthId(authId);
		authEntity.setAuthName(authEntityEx.getAuthName());
		authEntity.setAuthLevel(authEntityEx.getAuthLevel());
		authEntity.setAuthModifyTime(nowTime);
		authEntity.setAuthModifyUser(operator);

		if (updateById(authEntity)) {
			throw new CustomException(ExecptionType.AUTH, null, "修改权限失败");
		}

		return JsonUtil.toEntity(authEntity, SAuthEntityEx.class);
	}

	@Override
	public void lock_unLock(String authId, String operator) {

		// aa 查询使用当前权限的用户个数
		SAuthUserEntity authUserQueryEntity = new SAuthUserEntity();
		authUserQueryEntity.setAuthId(authId);
		authUserQueryEntity.setAuthUserDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SAuthUserEntity> queryWrapper_authUser = new QueryWrapper<SAuthUserEntity>(authUserQueryEntity);
		int authUserCount = authUserService.count(queryWrapper_authUser);
		if (authUserCount > 0) {
			throw new CustomException(ExecptionType.USER, null, "当前权限已被用户使用");
		}

		// aa 查询使用当前权限的请求个数
		SAuthUrlEntity authUrlQueryEntity = new SAuthUrlEntity();
		authUrlQueryEntity.setAuthId(authId);
		authUrlQueryEntity.setAuthUrlDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SAuthUrlEntity> queryWrapper_authUrl = new QueryWrapper<SAuthUrlEntity>(authUrlQueryEntity);
		int authUrlCount = authUrlService.count(queryWrapper_authUrl);
		if (authUrlCount > 0) {
			throw new CustomException(ExecptionType.URL, null, "当前权限已被请求使用");
		}

		// aa 查询使用当前权限的菜单个数
		SAuthMenuEntity authMenuQueryEntity = new SAuthMenuEntity();
		authMenuQueryEntity.setAuthId(authId);
		authMenuQueryEntity.setAuthMenuDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SAuthMenuEntity> queryWrapper_authMenu = new QueryWrapper<SAuthMenuEntity>(authMenuQueryEntity);
		int authMenuCount = authMenuService.count(queryWrapper_authMenu);
		if (authMenuCount > 0) {
			throw new CustomException(ExecptionType.MENU, null, "当前权限已被菜单使用");
		}

		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();
		SAuthEntity authEditEntity = new SAuthEntity();
		String msg = "";
		
		SAuthEntity authEntity = getById(authId);
		if (Code.DEL_FLG_0.getValue().equals(authEntity.getAuthDelFlg())) {
			authEditEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			msg = "启用权限失败";
		} else {
			authEditEntity.setAuthDelFlg(Code.DEL_FLG_0.getValue());
			msg = "禁用权限失败";
		}

		authEditEntity.setAuthId(authId);
		authEditEntity.setAuthModifyTime(nowTime);
		authEditEntity.setAuthModifyUser(operator);
		if (updateById(authEditEntity)) {
			throw new CustomException(ExecptionType.AUTH, null, msg);
		}

	}

}
