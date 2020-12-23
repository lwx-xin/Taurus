package org.taurus.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SUrlDao;
import org.taurus.entity.SUrlEntity;
import org.taurus.service.SUrlService;

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

}
