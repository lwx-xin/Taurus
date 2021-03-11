package org.taurus.common;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.taurus.common.code.CheckCode;
import org.taurus.common.result.Result;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;

/**
 * 重复登录
 * 
 * @author Lu Wenxin
 *
 */
public class RepeatLogin {

	public static HashMap<String, HttpSession> userSessionMap = new HashMap<>();

	public static synchronized void addUser(String userId, HttpSession session) {
		HttpSession beforeSession = userSessionMap.put(userId, session);
		if (beforeSession != null && !beforeSession.equals(session)) {
			SessionUtil.clearSession(beforeSession);
		}
	}
	
	public static synchronized void removeUser(String userId) {
		userSessionMap.remove(userId);
	}

	/**
	 * 重新登录
	 * @param data
	 * @param response
	 * @return
	 */
	public static <T> Result<T> reLogin(Object data, Class<T> clazz, HttpServletResponse response){
		response.setHeader(CommonField.SYSTEM_ERR_REDIRECT, StrUtil.toUTF8("/html/login.html"));
		response.setHeader(CommonField.SYSTEM_ERR_MSG,
				StrUtil.toUTF8(CheckCode.INTERFACE_ERR_CODE_reLogin.getName()));
		return new Result(data, true, CheckCode.INTERFACE_ERR_CODE_reLogin);
	}
}
