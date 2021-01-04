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
import org.taurus.common.util.ListUtil;
import org.taurus.entity.SAuthEntity;
import org.taurus.service.CommonService;
import org.taurus.service.SAuthService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class CommonServiceImpl implements CommonService {
	
	@Resource
	private SAuthService authService;

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
		
		for (String group : codeGroup) {
			List<CodeElement> codeList = CodeUtil.getByGroup(group);
			if (ListUtil.isNotEmpty(codeList)) {
				codeMap.put(group, codeList);
			}
		}
		
		return codeMap;
	}

}
