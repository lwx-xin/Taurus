package org.taurus.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtil {
	
	/**
	 * 根据key模糊检索
	 * @param map
	 * @param likeKey
	 * @return value列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List get(Map<String, ?> map, String likeKey) {
		List returnList = new ArrayList<>();
		
		for (Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			if (key.indexOf(likeKey)!=-1) {
				returnList.add(entry.getValue());
			}
		}
		
		return returnList;
	}

}
