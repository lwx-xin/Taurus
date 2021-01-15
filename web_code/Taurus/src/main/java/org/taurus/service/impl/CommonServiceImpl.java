package org.taurus.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.taurus.common.code.Code;
import org.taurus.common.result.CodeElement;
import org.taurus.common.util.CodeUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.dao.SMenuDao;
import org.taurus.dao.SUrlParamDao;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SUrlEntity;
import org.taurus.entity.SUrlParamEntity;
import org.taurus.extendEntity.SMenuEntityEx;
import org.taurus.extendEntity.SUrlParamEntityEx;
import org.taurus.service.CommonService;
import org.taurus.service.SAuthService;
import org.taurus.service.SMenuService;
import org.taurus.service.SUrlService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class CommonServiceImpl implements CommonService {

	@Resource
	private SAuthService authService;

	@Resource
	private SUrlParamDao urlParamDao;

	@Resource
	private SMenuService menuService;
	
	@Resource
	private SUrlService urlService;

	@Override
	public Map<String, List<CodeElement>> code(List<String> codeGroup) {
		Map<String, List<CodeElement>> codeMap = new HashMap<>();

		if (ListUtil.isEmpty(codeGroup)) {
			return codeMap;
		}

		if (codeGroup.contains("auth")) {
			SAuthEntity authEntity = new SAuthEntity();
			authEntity.setAuthDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SAuthEntity> queryWrapper = new QueryWrapper<SAuthEntity>(authEntity);
			List<SAuthEntity> authList = authService.list(queryWrapper);

			if (ListUtil.isNotEmpty(authList)) {
				List<CodeElement> authCodeElementList = new ArrayList<>();
				for (SAuthEntity auth : authList) {
					CodeElement authCodeElement = new CodeElement();
					authCodeElement.setName(auth.getAuthName());
					authCodeElement.setValue(auth.getAuthId());
					authCodeElementList.add(authCodeElement);
				}
				codeMap.put("auth", authCodeElementList);
			}
			codeGroup.remove("auth");
		}

		if (codeGroup.contains("url")) {
			SUrlEntity urlEntity = new SUrlEntity();
			urlEntity.setUrlDelFlg(Code.DEL_FLG_1.getValue());
			QueryWrapper<SUrlEntity> queryWrapper = new QueryWrapper<SUrlEntity>(urlEntity);
			List<SUrlEntity> urlList = urlService.list(queryWrapper);

			if (ListUtil.isNotEmpty(urlList)) {
				List<CodeElement> urlCodeElementList = new ArrayList<>();
				for (SUrlEntity url : urlList) {
					CodeElement urlCodeElement = new CodeElement();
					urlCodeElement.setName(url.getUrlPath());
					urlCodeElement.setValue(url.getUrlId());
					urlCodeElementList.add(urlCodeElement);
				}
				codeMap.put("url", urlCodeElementList);
			}

			codeGroup.remove("url");
		}

		for (String group : codeGroup) {
			List<CodeElement> codeList = CodeUtil.getByGroup(group);
			if (ListUtil.isNotEmpty(codeList)) {
				codeMap.put(group, codeList);
			}
		}

		return codeMap;
	}

	@Override
	public String getAjaxCheckJson() {
		List<SUrlParamEntityEx> urlParamList = urlParamDao.getList_url(new SUrlParamEntity());

		if (ListUtil.isNotEmpty(urlParamList)) {
			Map<String, Map<String, Map<String, SUrlParamEntityEx>>> checkMap = new HashMap<>();
			A: for (SUrlParamEntityEx urlParamEntity : urlParamList) {
				SUrlEntity urlEntity = urlParamEntity.getUrlEntity();
				if (urlEntity == null) {
					continue A;
				}

				String urlPath = urlEntity.getUrlPath();
				String urlType = CodeUtil.getCodeName(Code.REQUEST_METHOD_ALL.getGroup(), urlEntity.getUrlType());
				String urlParamName = urlParamEntity.getUrlParamName();

				Map<String, Map<String, SUrlParamEntityEx>> typeMap = checkMap.get(urlPath);
				if (typeMap == null) {
					typeMap = new HashMap<>();
					checkMap.put(urlPath, typeMap);
				}
				Map<String, SUrlParamEntityEx> paramMap = typeMap.get(urlType);
				if (paramMap == null) {
					paramMap = new HashMap<>();
					typeMap.put(urlType, paramMap);
				}
				paramMap.put(urlParamName, urlParamEntity);

			}
			return JsonUtil.toJson(checkMap);
		}

		return "";
	}

	@Resource
	private SMenuDao menuDao;

	@Override
	public List<SMenuEntityEx> getMenuListByUser(String userId) {
		List<SMenuEntityEx> menuSource = menuDao.getMenuListByUser(userId);
		return menuService.processingMenuData(menuSource);
	}

}
