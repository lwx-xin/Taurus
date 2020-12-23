package org.taurus.platform.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.taurus.common.Code;
import org.taurus.common.result.Result;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.entity.SUserEntity;
import org.taurus.service.SAuthService;
import org.taurus.service.SUserService;

@RestController
@RequestMapping("web/login")
public class LoginController {
	
	@Resource
	private SUserService userService;

	@Resource
	private SAuthService authService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result<String> login(String userNumber, String userPwd, HttpServletResponse response, HttpSession session){
		
		SUserEntity userEntity = userService.getUser(userNumber, userPwd);
		
		if (userEntity==null) {
			return new Result<String>(false, Code.LOGIN_WEB_ERROR);
		}
		
		String userId = userEntity.getUserId();
		
		//往cookie中添加token
		List<String> authIdList = authService.getAuthByUserId(userId);
		if (ListUtil.isNotEmpty(authIdList)) {
			CookieUtil.saveUserInfoToken(userId, authIdList, response);
		}
		
		SessionUtil.setUser(session, userEntity);

		return new Result<String>(true, Code.LOGIN_WEB_SUCCESS);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Result<String> logout(HttpSession session, HttpServletResponse response){
		
		SessionUtil.clearSession(session);
		CookieUtil.clearCookie(response);

		return new Result<String>(true, Code.INTERFACE_ERR_CODE_0);
	}

}
