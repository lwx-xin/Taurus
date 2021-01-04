package org.taurus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.taurus.common.code.Code;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.dao.SAuthDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.extendEntity.SAuthEntityEx;
import org.taurus.service.SAuthService;

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
	
	@Resource
	private SAuthDao authDao;

	@Override
	public List<String> getAuthByUserId(String userId) {
		
		List<SAuthEntity> authEntityList = authDao.getAuthByUserId(userId, Code.DEL_FLG_1.getValue());
		
		if (ListUtil.isNotEmpty(authEntityList)) {
			return authEntityList.stream().map(SAuthEntity::getAuthId).collect(Collectors.toList());
		}
		
		return null;
	}

	@Override
	public List<SAuthEntityEx> getAuthList(SAuthEntity authEntity) {
		QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
		List<SAuthEntity> data = list(queryWrapper);
		return JsonUtil.toList(data, SAuthEntityEx.class);
	}

	@Override
	public SAuthEntityEx getAuthDetail(String authId) {
		SAuthEntity authEntity = getById(authId);
		return JsonUtil.toEntity(authEntity, SAuthEntityEx.class);
	}
	
}
