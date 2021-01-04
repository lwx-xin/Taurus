package org.taurus.common.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.taurus.common.CommonField;
import org.taurus.entity.SUserEntity;

public class SessionUtil {

	/**
	 * 清除全部session
	 * 
	 * @param session
	 */
	public static void clearSession(HttpSession session) {
		Enumeration<String> em = session.getAttributeNames();
		while (em.hasMoreElements()) {
			session.removeAttribute(em.nextElement().toString());
		}
	}

	/**
	 * 清除全部session
	 * 
	 * @param session
	 */
	public static void clearSession(HttpServletRequest request) {
		clearSession(request.getSession());
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param session
	 * @return
	 */
	public static SUserEntity getUser(HttpSession session) {
		Object attribute = session.getAttribute(CommonField.SYSTEM_USER_INFO);
		if (attribute != null) {
			return (SUserEntity) attribute;
		}
		return null;
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request
	 * @return
	 */
	public static SUserEntity getUser(HttpServletRequest request) {
		return getUser(request.getSession());
	}

	/**
	 * 从session中获取用户ID
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request) {
		return getUserId(request.getSession());
	}

	/**
	 * 从session中获取用户ID
	 * 
	 * @param session
	 * @return
	 */
	public static String getUserId(HttpSession session) {
		SUserEntity user = getUser(session);
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}

	/**
	 * 往session中保存用户信息
	 * 
	 * @param session
	 * @param userEntity
	 */
	public static void setUser(HttpSession session, SUserEntity userEntity) {
		session.setAttribute(CommonField.SYSTEM_USER_INFO, userEntity);
	}

	/**
	 * 往session中保存用户信息
	 * 
	 * @param request
	 * @param userEntity
	 */
	public static void setUser(HttpServletRequest request, SUserEntity userEntity) {
		request.getSession().setAttribute(CommonField.SYSTEM_USER_INFO, userEntity);
	}

}
