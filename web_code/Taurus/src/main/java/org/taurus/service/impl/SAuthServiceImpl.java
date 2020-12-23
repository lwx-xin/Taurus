package org.taurus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.taurus.common.Code;
import org.taurus.common.util.ListUtil;
import org.taurus.dao.SAuthDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.service.SAuthService;

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
	
}
