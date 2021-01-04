package org.taurus.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SUrlDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SAuthUrlEntity;
import org.taurus.entity.SUrlEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.extendEntity.SUrlEntityEx;
import org.taurus.service.SAuthService;
import org.taurus.service.SAuthUrlService;
import org.taurus.service.SUrlService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 系统请求 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-11-08
 */
@Service
public class SUrlServiceImpl extends ServiceImpl<SUrlDao, SUrlEntity> implements SUrlService {

	@Resource
	private SUrlDao urlDao;

	@Resource
	private SAuthService authService;

	@Resource
	private SAuthUrlService authUrlService;

	@Override
	public List<SUrlEntity> getUrlByAuth(String authId) {
		if (StrUtil.isNotEmpty(authId)) {
			return urlDao.getUrlByAuth(authId);
		}
		return null;
	}

	@Override
	public List<SUrlEntity> getUrlByAuths(List<String> auths) {
		if (ListUtil.isNotEmpty(auths)) {
			return urlDao.getUrlByAuths(auths);
		}
		return null;
	}

	@Override
	public List<SUrlEntity> getUrlByUser(String userId) {
		if (StrUtil.isNotEmpty(userId)) {
			return urlDao.getUrlByUser(userId);
		}
		return null;
	}

	@Override
	public List<SUrlEntityEx> getUrlList(SUrlEntity urlEntity) {
		return urlDao.getUrlList(urlEntity);
	}

	@Override
	public SUrlEntityEx getUrlDetail(String urlId) {
		return urlDao.getUrlDetail(urlId);
	}

	@Override
	@Transactional
	public SUrlEntityEx insert(SUrlEntityEx urlEntityEx, String operator) {

		// aa 将要添加的用户ID
		String willAddUrlId = StrUtil.getUUID();
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		// aa 添加请求数据
		SUrlEntity urlEntity = new SUrlEntity();
		urlEntity.setUrlId(willAddUrlId);
		urlEntity.setUrlPath(urlEntityEx.getUrlPath());
		urlEntity.setUrlType(urlEntityEx.getUrlType());
		urlEntity.setUrlPlatform(urlEntityEx.getUrlPlatform());
		urlEntity.setUrlRemarks(urlEntityEx.getUrlRemarks());
		urlEntity.setUrlDelFlg(Code.DEL_FLG_1.getValue());
		urlEntity.setUrlCreateTime(nowTime);
		urlEntity.setUrlCreateUser(operator);
		urlEntity.setUrlModifyTime(nowTime);
		urlEntity.setUrlModifyUser(operator);
		if (!save(urlEntity)) {
			throw new CustomException(ExecptionType.URL, null, "添加请求数据失败");
		}

		if (ListUtil.isNotEmpty(urlEntityEx.getAuthList())) {
			// aa 要给请求赋予的权限
			List<String> authIdList = urlEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId)
					.collect(Collectors.toList());

			// aa 要给请求赋予的权限--详细信息
			SAuthEntity authEntity = new SAuthEntity();
			authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
			queryWrapper.in("AUTH_ID", authIdList);
			List<SAuthEntity> allAuth = authService.list(queryWrapper);

			List<SAuthEntityEx> authList = new ArrayList<SAuthEntityEx>();
			// aa 给请求绑定权限
			List<SAuthUrlEntity> authUrlList = new ArrayList<SAuthUrlEntity>();
			for (SAuthEntity auth : allAuth) {
				SAuthUrlEntity authUrlEntity = new SAuthUrlEntity();
				authUrlEntity.setAuthUrlId(StrUtil.getUUID());
				authUrlEntity.setAuthId(auth.getAuthId());
				authUrlEntity.setUrlId(willAddUrlId);
				authUrlEntity.setAuthUrlDelFlg(Code.DEL_FLG_1.getValue());
				authUrlEntity.setAuthUrlCreateUser(operator);
				authUrlEntity.setAuthUrlCreateTime(nowTime);
				authUrlEntity.setAuthUrlModifyUser(operator);
				authUrlEntity.setAuthUrlModifyTime(nowTime);
				authUrlList.add(authUrlEntity);

				authList.add(JsonUtil.toEntity(auth, SAuthEntityEx.class));
			}
			if (!authUrlService.saveBatch(authUrlList)) {
				throw new CustomException(ExecptionType.USER_AUTH, null, "请求权限关联表添加数据失败");
			}
			urlEntityEx.setAuthList(authList);
		}

		return urlEntityEx;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public SUrlEntityEx update(String urlId, SUrlEntityEx urlEntityEx, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		// aa 修改请求数据
		SUrlEntity urlEntity = new SUrlEntity();
		urlEntity.setUrlId(urlId);
		urlEntity.setUrlPath(urlEntityEx.getUrlPath());
		urlEntity.setUrlType(urlEntityEx.getUrlType());
		urlEntity.setUrlPlatform(urlEntityEx.getUrlPlatform());
		urlEntity.setUrlRemarks(urlEntityEx.getUrlRemarks());
		urlEntity.setUrlModifyTime(nowTime);
		urlEntity.setUrlModifyUser(operator);
		if (!updateById(urlEntity)) {
			throw new CustomException(ExecptionType.URL, null, "修改请求数据失败");
		}

		// aa 页面上选择的权限
		List<String> authIdList = null;
		A:if (ListUtil.isNotEmpty(urlEntityEx.getAuthList())) {
			// aa 要给请求赋予的权限
			authIdList = urlEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId).collect(Collectors.toList());
			
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

			// aa 获取当前请求的权限id（修改前）
			List<String> updateBeforAuthUrlList_authId = null;
			SAuthUrlEntity authUrlEntity_query = new SAuthUrlEntity();
			authUrlEntity_query.setUrlId(urlId);
			authUrlEntity_query.setAuthUrlDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthUrlEntity> queryWrapper_authUrl = new QueryWrapper<SAuthUrlEntity>(
					authUrlEntity_query);
			List<SAuthUrlEntity> updateBeforAuthUrlList = authUrlService.list(queryWrapper_authUrl);
			if (ListUtil.isNotEmpty(updateBeforAuthUrlList)) {
				updateBeforAuthUrlList_authId = updateBeforAuthUrlList.stream().map(SAuthUrlEntity::getAuthId)
						.collect(Collectors.toList());
			}

			// aa 要删除的权限
			List<String> deleteAuth = null;
			// aa 要新增的权限
			List<String> insertAuth = null;
			if (ListUtil.isNotEmpty(updateBeforAuthUrlList_authId)) {
				deleteAuth = ListUtil.except(updateBeforAuthUrlList_authId, authIdList);
				insertAuth = ListUtil.except(authIdList, updateBeforAuthUrlList_authId);
			} else {
				insertAuth= new ArrayList<>();
				insertAuth.addAll(authIdList);
			}

			// aa 删除权限 - 逻辑删除
			if (ListUtil.isNotEmpty(deleteAuth)) {
				for (String authId : deleteAuth) {
					SAuthUrlEntity authUrlEntity = new SAuthUrlEntity();
					authUrlEntity.setAuthId(authId);
					authUrlEntity.setUrlId(urlId);

					QueryWrapper<SAuthUrlEntity> queryWrapper = new QueryWrapper<>(authUrlEntity);

					authUrlEntity = new SAuthUrlEntity();
					authUrlEntity.setAuthUrlDelFlg(Code.DEL_FLG_0.getValue());
					if (!authUrlService.update(authUrlEntity, queryWrapper)) {
						throw new CustomException(ExecptionType.USER_AUTH, null, "请求权限关联表删除数据失败");
					}
				}
			}

			// aa 新增权限
			if (ListUtil.isNotEmpty(insertAuth)) {
				List<SAuthUrlEntity> authUrlList = new ArrayList<>();
				for (String authId : insertAuth) {
					SAuthUrlEntity authUrlEntity = new SAuthUrlEntity();
					authUrlEntity.setAuthUrlId(StrUtil.getUUID());
					authUrlEntity.setAuthId(authId);
					authUrlEntity.setUrlId(urlId);
					authUrlEntity.setAuthUrlDelFlg(Code.DEL_FLG_1.getValue());
					authUrlEntity.setAuthUrlCreateUser(operator);
					authUrlEntity.setAuthUrlCreateTime(nowTime);
					authUrlEntity.setAuthUrlModifyUser(operator);
					authUrlEntity.setAuthUrlModifyTime(nowTime);
					authUrlList.add(authUrlEntity);
				}
				if (!authUrlService.saveBatch(authUrlList)) {
					throw new CustomException(ExecptionType.USER_AUTH, null, "请求权限关联表添加数据失败");
				}
			}
		}
		
		// aa 删除当前请求全部权限
		if (ListUtil.isEmpty(authIdList)) {
			SAuthUrlEntity authUrlEntity_query = new SAuthUrlEntity();
			authUrlEntity_query.setUrlId(urlId);
			QueryWrapper<SAuthUrlEntity> queryWrapper = new QueryWrapper<SAuthUrlEntity>(authUrlEntity_query);
			if (authUrlService.count(queryWrapper) > 0) {
				SAuthUrlEntity authUrlEntity_updateQuery = new SAuthUrlEntity();
				authUrlEntity_updateQuery.setUrlId(urlId);
				QueryWrapper<SAuthUrlEntity> updateWrapper = new QueryWrapper<SAuthUrlEntity>(
						authUrlEntity_updateQuery);

				SAuthUrlEntity authUrlEntity_update = new SAuthUrlEntity();
				authUrlEntity_update.setAuthUrlDelFlg(Code.DEL_FLG_0.getValue());
				authUrlEntity_update.setAuthUrlModifyTime(nowTime);
				authUrlEntity_update.setAuthUrlModifyUser(operator);
				if (!authUrlService.update(authUrlEntity_update, updateWrapper)) {
					throw new CustomException(ExecptionType.URL_AUTH, null, "请求权限关联表删除数据失败");
				}
			}
		}

		return urlEntityEx;
	}

}
