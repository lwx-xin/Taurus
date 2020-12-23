package org.taurus.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taurus.common.Code;
import org.taurus.common.CommonField;
import org.taurus.common.result.TokenVerifyResult;
import org.taurus.common.util.CodeUtil;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.JwtUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.entity.SUrlEntity;
import org.taurus.entity.SUserEntity;
import org.taurus.service.SUrlService;
import org.taurus.service.SUserService;

import io.jsonwebtoken.Claims;

public class AuthFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SUrlService urlService;

	@Resource
	private SUserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("------------------------------------初始化AuthFilter权限过滤器------------------------------------");
		Filter.super.init(filterConfig);
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// 参数集合
		Map<String, String[]> parameterMap = request.getParameterMap();
		// 请求路径
		String url = request.getServletPath();
		// 请求方法-post，get
		String method = request.getMethod();

		Code authCheck = authCheck(url, parameterMap, method, request, response);

		if (Code.AUTH_FILTER_SUCCESS.equals(authCheck)) {
			filterChain.doFilter(request, response);
		} else {
			System.err.println("参数：" + parameterMap);
			System.err.println("请求：" + url);
			System.err.println("方法：" + method);
			String redirectUrl = authCheck.getValue();
			String errMessage = authCheck.getName();

			response.addHeader(CommonField.SYSTEM_ERR_MSG, errMessage);
			response.addHeader(CommonField.SYSTEM_ERR_REDIRECT, redirectUrl);
			response.addHeader(CommonField.SYSTEM_ERR_SOURCE_PATH, url);

			CookieUtil.createCookie(CommonField.SYSTEM_ERR_MSG, errMessage, response);
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_REDIRECT, redirectUrl, response);
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_SOURCE_PATH, url, response);

			if (Code.AUTH_FILTER_NO_LOGIN.equals(authCheck) || Code.AUTH_FILTER_NO_TOKEN.equals(authCheck)
					|| Code.AUTH_FILTER_TOKEN_EXCEPTION.equals(authCheck)
					|| Code.AUTH_FILTER_TOKEN_EXPIRED.equals(authCheck)) {
				// 用户信息获取失败
				response.setStatus(401);
			} else if(Code.AUTH_FILTER_NO_AUTH.equals(authCheck)) {
				// 没有权限访问
				response.setStatus(403);
			} else if(Code.AUTH_FILTER_UNKNOWN_URL.equals(authCheck)) {
				// 没有权限访问
				response.setStatus(404);
			} else {
				response.setStatus(301);
				response.setLocale(new Locale(redirectUrl));
			}

			response.sendRedirect(redirectUrl);
		}

	}

	@Override
	public void destroy() {
		logger.info("------------------------------------销毁AuthFilter权限过滤器------------------------------------");
		Filter.super.destroy();
	}

	/**
	 * 权限验证
	 * 
	 * @param url          请求路径
	 * @param parameterMap 参数
	 * @param methodType   请求方式
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Code authCheck(String url, Map<String, String[]> parameterMap, String methodType,
			HttpServletRequest request, HttpServletResponse response) {

		if (noCheckSource(url)) {
			return Code.AUTH_FILTER_SUCCESS;
		}

		// 从session中获取用户信息
		SUserEntity userInfo_session = SessionUtil.getUser(request);

		// 从cookie中获取用户信息令牌
		String userInfoToken = CookieUtil.getUserInfoToken(request);
		// 解析令牌
		Map<String, Object> parseUserInfoToken = parseUserInfoToken(userInfoToken);
		Code code = (Code) parseUserInfoToken.get("code");
		Object userInfoObj = parseUserInfoToken.get("userInfo");
		Object authListObj = parseUserInfoToken.get("authList");

		// 从cookie中获取用户信息
		SUserEntity userInfo_cookie = null;
		if (userInfoObj != null) {
			userInfo_cookie = (SUserEntity) parseUserInfoToken.get("userInfo");
			if (userInfo_session==null) {
				SessionUtil.setUser(request, userInfo_cookie);
			}
		}

		// 从cookie中获取权限列表
		List<String> authList = null;
		if (authListObj != null) {
			authList = (List<String>) parseUserInfoToken.get("authList");
		}

		if (!Code.TOKEN_SUCCESS.equals(code)) { // 令牌解析失败
			if ("/html/login.html".equals(url)) {
				return Code.AUTH_FILTER_SUCCESS;
			}
			return code;
		}

		if (("/html/login.html".equals(url) || "/html/system/home.jsp".equals(url))
				&& (userInfo_session != null || userInfo_cookie != null)) {
			SessionUtil.setUser(request, userInfo_cookie);
			return Code.AUTH_FILTER_NO_NEED_LOGIN;
		}

		if (ListUtil.isEmpty(authList)) {
			return Code.AUTH_FILTER_NO_AUTH;
		}

		// 当前权限可以访问的请求
		List<SUrlEntity> urlList = urlService.getUrlByAuths(authList);

		if (ListUtil.isEmpty(urlList)) {
			// 该权限未配置可访问的请求
			return Code.AUTH_FILTER_NO_AUTH;
		}
		List<SUrlEntity> urlFilterList = urlList.stream()
				.filter((SUrlEntity urlEntity) -> Pattern.matches(urlEntity.getUrlPath(), url))
				.collect(Collectors.toList());
		if (ListUtil.isEmpty(urlFilterList)) {
			// 该请求当前令牌无权访问
			return Code.AUTH_FILTER_NO_AUTH;
		}

		for (SUrlEntity urlEntity : urlFilterList) {
			// 请求参数(json格式)
//			String urlParams = urlEntity.getUrlParams();
			// 请求方式(get,post)
			String urlType = urlEntity.getUrlType();
//			if (Code.REQUEST_METHOD_ALL.getValue().equals(urlType)) {
//				return Code.AUTH_FILTER_SUCCESS;
//			}
			String urlTypeName = CodeUtil.getCodeName(Code.REQUEST_METHOD_ALL.getGroup(), urlType);
			if (StrUtil.isNotEmpty(urlTypeName)
					&& (methodType.equals(urlTypeName.toUpperCase()) || methodType.equals(urlTypeName.toLowerCase()))) {
				return Code.AUTH_FILTER_SUCCESS;
			}
		}

		return Code.AUTH_FILTER_SUCCESS;
	}

	/**
	 * 判断是否是无需验证的资源
	 * 
	 * @param url
	 * @return
	 */
	private boolean noCheckSource(String url) {
		if (url.startsWith("/css/") || url.startsWith("/js/") || url.startsWith("/fonts/") || url.startsWith("/img/")) {
			return true;
		} else if ("/web/login/login".equals(url)) {
			return true;
		} else if ("/web/login/logout".equals(url)) {
			return true;
		} else if ("/html/system/no-auth.html".equals(url) || "/html/system/unknown-url.html".equals(url)) {
			return true;
		}

		return false;
	}

	/**
	 * 解析令牌
	 * 
	 * @param userInfoToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> parseUserInfoToken(String userInfoToken) {

		Map<String, Object> retuenMap = new HashMap<String, Object>();
		Code errCode = null;
		List<String> authList = null;
		SUserEntity userInfo = null;

		if (StrUtil.isNotEmpty(userInfoToken)) {
			TokenVerifyResult token = JwtUtil.parseToken(userInfoToken);
			if (Code.TOKEN_SUCCESS.getValue().equals(token.getErrCode())) {
				// 令牌解析成功，抽出当前令牌中的权限列表
				try {
					Claims body = (Claims) token.getData();
					Object authListObject = body.get("authIdList");// 权限列表
					Object userIdObject = body.get("userId");// 用户ID
					if (authListObject != null) {
						authList = (List<String>) authListObject;
					}
					if (userIdObject != null) {
						userInfo = userService.getById(StrUtil.format(userIdObject));
					}
					errCode = Code.TOKEN_SUCCESS;
				} catch (Exception e) {
					System.err.println(e);
				}
			} else if (Code.TOKEN_ERR.getValue().equals(token.getErrCode())) {
				// 令牌解析失败
				errCode = Code.AUTH_FILTER_TOKEN_EXCEPTION;
			} else if (Code.TOKEN_OVERDUE.getValue().equals(token.getErrCode())) {
				// 令牌过期
				errCode = Code.AUTH_FILTER_TOKEN_EXPIRED;
			}
		} else {
			errCode = Code.AUTH_FILTER_NO_TOKEN;
		}

		retuenMap.put("code", errCode);
		retuenMap.put("userInfo", userInfo);
		retuenMap.put("authList", authList);
		return retuenMap;
	}

}
