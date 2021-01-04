package org.taurus.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.entity.SUserEntity;

public class UserInfo {

	/**
	 * 从session中获取用户信息
	 * 
	 * @param session
	 * @return
	 */
	public SUserEntity getUserInfo(HttpSession session) {
		return SessionUtil.getUser(session);
	}

	/**
	 * 从cookie中获取用户信息Token
	 * 
	 * @param request
	 * @return
	 */
	public SUserEntity getUserInfo(HttpServletRequest request) {
		// 获取用户信息令牌
		String userInfoToken = CookieUtil.getCookie(CommonField.SYSTEM_USER_INFO, request);

		return JsonUtil.toEntity(userInfoToken, SUserEntity.class);
	}

	public void setUserInfo(HttpSession session, SUserEntity userEntity) {
		SessionUtil.setUser(session, userEntity);
	}

	public void setUserInfo(HttpServletResponse response, SUserEntity userEntity) {
//		CookieUtil.createCookie(USER_INFO, cookieValue, response);
	}

}
