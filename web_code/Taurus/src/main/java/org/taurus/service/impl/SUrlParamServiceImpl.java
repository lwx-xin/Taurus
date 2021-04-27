package org.taurus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.taurus.entity.SUrlParamEntity;
import org.taurus.common.code.Code;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.dao.SUrlParamDao;
import org.taurus.service.SUrlParamService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 请求参数 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2020-12-28
 */
@Service
public class SUrlParamServiceImpl extends ServiceImpl<SUrlParamDao, SUrlParamEntity> implements SUrlParamService {

	@Autowired
	private SUrlParamDao urlParamDao;

	@Override
	public List<SUrlParamEntity> getUrlParamList(SUrlParamEntity urlParamEntity) {
		if (urlParamEntity == null || StrUtil.isEmpty(urlParamEntity.getUrlId())) {
			return null;
		}
		urlParamEntity.setUrlParamDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SUrlParamEntity> queryWrapper = new QueryWrapper<SUrlParamEntity>(urlParamEntity);
		return list(queryWrapper);
	}

	@Override
	public SUrlParamEntity getURLParamDetail(String urlParamId) {
		return getById(urlParamId);
	}

	@Override
	public SUrlParamEntity insert(SUrlParamEntity urlParamEntity, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SUrlParamEntity urlParamEntity_insert = new SUrlParamEntity();
		urlParamEntity_insert.setUrlParamId(StrUtil.getUUID());
		urlParamEntity_insert.setUrlId(urlParamEntity.getUrlId());
		urlParamEntity_insert.setUrlParamName(urlParamEntity.getUrlParamName());
		urlParamEntity_insert.setUrlParamValue(urlParamEntity.getUrlParamValue());
		urlParamEntity_insert.setUrlParamRequired(urlParamEntity.getUrlParamRequired());
		urlParamEntity_insert.setUrlParamNull(urlParamEntity.getUrlParamNull());
		urlParamEntity_insert.setUrlParamRemark(urlParamEntity.getUrlParamRemark());
		urlParamEntity_insert.setUrlParamErrHint(urlParamEntity.getUrlParamErrHint());
		urlParamEntity_insert.setUrlParamDelFlg(Code.DEL_FLG_1.getValue());
		urlParamEntity_insert.setUrlParamCreateTime(nowTime);
		urlParamEntity_insert.setUrlParamCreateUser(operator);
		urlParamEntity_insert.setUrlParamModifyTime(nowTime);
		urlParamEntity_insert.setUrlParamModifyUser(operator);

		if (!save(urlParamEntity_insert)) {
			throw new CustomException(ExecptionType.URL_PARAM, null, "添加请求参数信息失败");
		}

		return urlParamEntity_insert;
	}

	@Override
	public SUrlParamEntity update(String urlParamId, SUrlParamEntity urlParamEntity, String operator) {
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SUrlParamEntity urlParamEntity_update = new SUrlParamEntity();
		urlParamEntity_update.setUrlParamId(urlParamId);
		urlParamEntity_update.setUrlId(urlParamEntity.getUrlId());
		urlParamEntity_update.setUrlParamName(urlParamEntity.getUrlParamName());
		urlParamEntity_update.setUrlParamValue(urlParamEntity.getUrlParamValue());
		urlParamEntity_update.setUrlParamRequired(urlParamEntity.getUrlParamRequired());
		urlParamEntity_update.setUrlParamNull(urlParamEntity.getUrlParamNull());
		urlParamEntity_update.setUrlParamRemark(urlParamEntity.getUrlParamRemark());
		urlParamEntity_update.setUrlParamErrHint(urlParamEntity.getUrlParamErrHint());
//		urlParamEntity_update.setUrlParamDelFlg(urlParamEntity.getUrlParamDelFlg());
		urlParamEntity_update.setUrlParamModifyTime(nowTime);
		urlParamEntity_update.setUrlParamModifyUser(operator);

		if (!updateById(urlParamEntity_update)) {
			throw new CustomException(ExecptionType.URL_PARAM, null, "编辑请求参数信息失败");
		}

		return urlParamEntity_update;
	}

}
