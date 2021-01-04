package org.taurus.common.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

	/**
	 * 字符串转实体 / Map
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T toEntity(String jsonStr, Class<T> clazz) {
		try {
			return JSON.parseObject(jsonStr, clazz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Map转实体
	 * 
	 * @param <T>
	 * @param map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T toEntity(Map map, Class<T> clazz) {
		try {
			return JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 实体转实体
	 * 
	 * @param <T>
	 * @param entity
	 * @param clazz
	 * @return
	 */
	public static <T> T toEntity(Object entity, Class<T> clazz) {
		try {
			String json = toJson(entity);
			return toEntity(json, clazz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 字符串转List
	 * 
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
		try {
			return JSON.parseArray(jsonStr, clazz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * List转List
	 * 
	 * @param <T>
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(List<?> list, Class<T> clazz) {
		try {
			return JSON.parseArray(JsonUtil.toJson(list), clazz);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 实体对象转Map
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map toMap(Object obj) {
		try {
			return JSON.parseObject(JSON.toJSONString(obj));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * list转json字符串
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(List list) {
		try {
			return JSON.toJSONString(list);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 实体转json字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
