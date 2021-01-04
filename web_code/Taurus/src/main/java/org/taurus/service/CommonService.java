package org.taurus.service;

import java.util.List;
import java.util.Map;

import org.taurus.common.result.CodeElement;

public interface CommonService {

	/**
	 * 获取code列表
	 * 
	 * @param codeGroup
	 * @return
	 */
	public Map<String, List<CodeElement>> code(List<String> codeGroup);

}
