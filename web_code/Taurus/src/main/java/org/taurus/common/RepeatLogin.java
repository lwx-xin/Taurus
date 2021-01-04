package org.taurus.common;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.taurus.common.util.SessionUtil;

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

}
