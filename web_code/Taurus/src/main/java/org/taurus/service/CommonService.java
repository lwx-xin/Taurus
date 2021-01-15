package org.taurus.service;

import java.util.List;
import java.util.Map;

import org.taurus.common.result.CodeElement;
import org.taurus.extendEntity.SMenuEntityEx;

public interface CommonService {

	/**
	 * 获取code列表
	 * 
	 * @param codeGroup
	 * @return
	 */
	public Map<String, List<CodeElement>> code(List<String> codeGroup);

	/**
	 * 获取ajax参数验证所需的json数据
	 * 
	 * @return
	 */
	public String getAjaxCheckJson();

	/**
	 * 获取用户能显示的菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<SMenuEntityEx> getMenuListByUser(String userId);

}
