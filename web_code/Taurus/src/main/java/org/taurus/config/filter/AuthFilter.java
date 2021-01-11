package org.taurus.config.filter;

import java.io.IOException;
import java.util.ArrayList;
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
import org.taurus.common.CommonField;
import org.taurus.common.code.CheckCode;
import org.taurus.common.code.Code;
import org.taurus.common.result.TokenVerifyResult;
import org.taurus.common.util.CodeUtil;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.JsonUtil;
import org.taurus.common.util.JwtUtil;
import org.taurus.common.util.ListUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.entity.SAuthEntity;
import org.taurus.entity.SUrlEntity;
import org.taurus.entity.SUrlParamEntity;
import org.taurus.entity.SUserEntity;
import org.taurus.extendEntity.SUrlEntityEx;
import org.taurus.service.SUrlParamService;
import org.taurus.service.SUrlService;
import org.taurus.service.SUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.jsonwebtoken.Claims;

public class AuthFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SUrlService urlService;

	@Resource
	private SUserService userService;

	@Resource
	private SUrlParamService urlParamService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("------------------------------------初始化AuthFilter权限过滤器------------------------------------");
		Filter.super.init(filterConfig);
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// aa 参数集合
		Map<String, String[]> parameterMap = request.getParameterMap();
		// aa 请求路径
		String url = request.getServletPath();
		// aa 请求方法-post，get
		String method = request.getMethod();

		// aa 是否是ajax请求
		boolean isAjax = false;
		if (StrUtil.isNotEmpty(request.getHeader("req-flg")) && request.getHeader("req-flg").equals("ajax")) {
			isAjax = true;
		}

		CheckCode authCheck = authCheck(url, parameterMap, method, request, response);

		if (CheckCode.AUTH_FILTER_SUCCESS.equals(authCheck)) {
			System.err.println("=====正常请求=====");
			System.err.println("正常请求参数：" + JsonUtil.toJson(parameterMap));
			System.err.println("正常请求：" + url);
			System.err.println("正常请求方法：" + method);
			System.err.println("=====异常请求=====");
			filterChain.doFilter(request, response);
		} else {
			System.err.println("=====异常请求=====");
			System.err.println("异常请求参数：" + JsonUtil.toJson(parameterMap));
			System.err.println("异常请求：" + url);
			System.err.println("异常请求方法：" + method);
			System.err.println("=====异常请求=====");
			String redirectUrl = authCheck.getValue();
			String errMessage = authCheck.getName();

			String redirectUrl_utf8 = StrUtil.toUTF8(redirectUrl);
			String errMessage_utf8 = StrUtil.toUTF8(errMessage);
			String url_utf8 = StrUtil.toUTF8(url);

			if (CheckCode.AUTH_FILTER_NO_LOGIN.equals(authCheck) || CheckCode.AUTH_FILTER_TOKEN_NULL.equals(authCheck)
					|| CheckCode.AUTH_FILTER_TOKEN_ERR.equals(authCheck)
					|| CheckCode.AUTH_FILTER_TOKEN_OVERDUE.equals(authCheck)) {
				// aa 用户信息获取失败
				response.setStatus(401);
			} else if (CheckCode.AUTH_FILTER_NO_AUTH.equals(authCheck)) {
				// aa 没有权限访问
				response.setStatus(403);
			} else if (CheckCode.AUTH_FILTER_UNKNOWN_URL.equals(authCheck)) {
				// aa 没有权限访问
				response.setStatus(404);
			} else if (CheckCode.AUTH_FILTER_PARAM_ERR.equals(authCheck)) {
				// aa 参数错误
				response.setStatus(400);
			} else {
				response.setStatus(301);
				response.setLocale(new Locale(redirectUrl));
			}

			if (isAjax) {
				response.addHeader(CommonField.SYSTEM_ERR_MSG, errMessage_utf8);
				response.addHeader(CommonField.SYSTEM_ERR_SOURCE_PATH, url_utf8);

				if (CheckCode.AUTH_FILTER_PARAM_ERR.equals(authCheck)) {
					response.addHeader(CommonField.SYSTEM_ERR_REDIRECT, "");
				} else {
					response.addHeader(CommonField.SYSTEM_ERR_REDIRECT, redirectUrl_utf8);
					// filterChain.doFilter(request, response);
				}
			} else {
				CookieUtil.createCookie(CommonField.SYSTEM_ERR_MSG, errMessage_utf8, response);
				CookieUtil.createCookie(CommonField.SYSTEM_ERR_REDIRECT, redirectUrl_utf8, response);
				CookieUtil.createCookie(CommonField.SYSTEM_ERR_SOURCE_PATH, url_utf8, response);
				response.sendRedirect(redirectUrl);
			}
		}

	}

	@Override
	public void destroy() {
		logger.info("------------------------------------销毁AuthFilter权限过滤器------------------------------------");
		Filter.super.destroy();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> userInfoCheck(HttpServletRequest request, HttpServletResponse response) {
		CheckCode errCode = null;
		List<String> authIdList = null;
		SUserEntity userInfo = null;
		Map<String, Object> returnMap = new HashMap<>();

		// aa 从session中获取用户信息
		SUserEntity userInfo_session = SessionUtil.getUser(request);
		System.err.println("session-id:::" + request.getSession().getId());

		if (userInfo_session == null) {
			returnMap.put("code", CheckCode.USER_ERR);
			returnMap.put("userInfo", userInfo);
			returnMap.put("authIdList", authIdList);
			return returnMap;
		}

		// aa 从cookie中获取用户信息令牌
		String userInfoToken = CookieUtil.getUserInfoToken(request);

		// aa 解析令牌信息
		Map<String, Object> parseUserInfoToken = parseUserInfoToken(userInfoToken);
		CheckCode code = (CheckCode) parseUserInfoToken.get("code");
		Object userInfoObj = parseUserInfoToken.get("userInfo");
		Object authIdListObj = parseUserInfoToken.get("authIdList");

		if (CheckCode.TOKEN_SUCCESS.equals(code)) {
			// aa 令牌解析正常
			errCode = CheckCode.USER_SUCCESS;
			if (userInfoObj != null) {
				userInfo = (SUserEntity) userInfoObj;
			}
			if (authIdListObj != null) {
				authIdList = (List<String>) authIdListObj;
			}
		} else {
			// aa 令牌解析错误
			if (CheckCode.TOKEN_ERR.equals(code)) {
				errCode = CheckCode.USER_ERR_TOKEN_ERR;
			} else if (CheckCode.TOKEN_NULL.equals(code)) {
				errCode = CheckCode.USER_ERR_TOKEN_NULL;
			} else if (CheckCode.TOKEN_OVERDUE.equals(code)) {
				errCode = CheckCode.USER_ERR_TOKEN_OVERDUE;
			}
		}

		returnMap.put("code", errCode);
		returnMap.put("userInfo", userInfo);
		returnMap.put("authIdList", authIdList);
		return returnMap;
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
	private CheckCode authCheck(String url, Map<String, String[]> parameterMap, String methodType,
			HttpServletRequest request, HttpServletResponse response) {

		if (noCheckSource(url)) {
			return CheckCode.AUTH_FILTER_SUCCESS;
		}

		// aa 验证用户信息
		Map<String, Object> userInfoCheck = userInfoCheck(request, response);
		CheckCode errCode = (CheckCode) userInfoCheck.get("code");
//		Object userInfoObj = userInfoCheck.get("userInfo");
		Object authIdListObj = userInfoCheck.get("authIdList");

		if (!CheckCode.USER_SUCCESS.equals(errCode) && "/html/login.html".equals(url)) {
			return CheckCode.AUTH_FILTER_SUCCESS;
		}

		if (CheckCode.USER_ERR_TOKEN_ERR.equals(errCode)) {
			return CheckCode.AUTH_FILTER_TOKEN_ERR;
		} else if (CheckCode.USER_ERR_TOKEN_NULL.equals(errCode)) {
			return CheckCode.AUTH_FILTER_TOKEN_NULL;
		} else if (CheckCode.USER_ERR_TOKEN_OVERDUE.equals(errCode)) {
			return CheckCode.AUTH_FILTER_TOKEN_OVERDUE;
		} else if (CheckCode.USER_ERR.equals(errCode)) {
			return CheckCode.AUTH_FILTER_NO_LOGIN;
		}

//		SUserEntity userInfo;
//		if (userInfoObj != null) {
//			userInfo = (SUserEntity) userInfoObj;
//		}
		List<String> authIdList;
		if (authIdListObj != null) {
			authIdList = (List<String>) authIdListObj;
		} else {
			return CheckCode.AUTH_FILTER_NO_AUTH;
		}

		// aa 全部未删除的请求
		SUrlEntity urlQueryEntity = new SUrlEntity();
		urlQueryEntity.setUrlDelFlg(Code.DEL_FLG_1.getValue());
		List<SUrlEntityEx> urlList_all = urlService.getUrlList(urlQueryEntity);

		// aa 判断当前系统是否存在匹配的请求
		List<SUrlEntityEx> urlList = new ArrayList<>();
		if (ListUtil.isNotEmpty(urlList_all)) {
			for (SUrlEntityEx urlEntityEx : urlList_all) {
				// aa 请求路径(正则表达式)
				String urlPath = urlEntityEx.getUrlPath();
				// aa 请求方式
				String urlType = CodeUtil.getCodeName(Code.REQUEST_METHOD_ALL.getGroup(), urlEntityEx.getUrlType());
				if (StrUtil.isNotEmpty(urlPath) && StrUtil.isNotEmpty(urlType)) {
					if (Pattern.matches(urlPath, url)
							&& (urlType.toUpperCase().equals(methodType) || urlType.toLowerCase().equals(methodType))) {
						urlList.add(urlEntityEx);
					}
				}
			}
		}
		if (ListUtil.isEmpty(urlList)) {
			return CheckCode.AUTH_FILTER_UNKNOWN_URL;
		}

		// aa 判断当前用户是否有权限访问
		for (SUrlEntityEx urlEntityEx : urlList) {
			// aa 能访问请求的权限
			List<String> authIds = urlEntityEx.getAuthList().stream().map(SAuthEntity::getAuthId)
					.collect(Collectors.toList());

			// aa 未设置权限的资源可以随意访问
			if (ListUtil.isNotEmpty(authIds) && ListUtil.isEmpty(ListUtil.intersection(authIds, authIdList))) {
				return CheckCode.AUTH_FILTER_NO_AUTH;
			}
		}

		// aa 判断请求参数是否正确
		for (SUrlEntityEx urlEntityEx : urlList) {
			if (!validateParam(urlEntityEx, parameterMap)) {
				return CheckCode.AUTH_FILTER_PARAM_ERR;
			}
		}

		return CheckCode.AUTH_FILTER_SUCCESS;
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
		} else if ("/clearLoginInfo".equals(url)) {
			return true;
		} else if ("/html/error/err-noAuth.html".equals(url) || "/html/error/err-paramsErr.html".equals(url)
				|| "/html/error/err-unknownUrl.html".equals(url)) {
			return true;
		} else if (url.startsWith("/f/")) {
			return true;
		}

		return false;
	}

	/**
	 * 解析令牌
	 * 
	 * @param userInfoToken
	 * @return {code: 'CheckCode', userInfo: 'SUserEntity', authList:
	 *         'List<String>'}
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> parseUserInfoToken(String userInfoToken) {

		Map<String, Object> retuenMap = new HashMap<String, Object>();
		CheckCode errCode = null;
		List<String> authIdList = null;
		SUserEntity userInfo = null;

		if (StrUtil.isNotEmpty(userInfoToken)) {
			TokenVerifyResult token = JwtUtil.parseToken(userInfoToken);
			if (CheckCode.TOKEN_SUCCESS.getValue().equals(token.getErrCode())) {
				// aa 令牌解析成功，抽出当前令牌中的权限列表
				try {
					Claims body = (Claims) token.getData();
					Object authListObject = body.get("authIdList");// aa 权限列表
					Object userIdObject = body.get("userId");// aa 用户ID
					if (authListObject != null) {
						authIdList = (List<String>) authListObject;
					}
					if (userIdObject != null) {
						userInfo = userService.getById(StrUtil.format(userIdObject));
					}
					errCode = CheckCode.TOKEN_SUCCESS;
				} catch (Exception e) {
					System.err.println(e);
				}
			} else if (CheckCode.TOKEN_ERR.getValue().equals(token.getErrCode())) {
				// aa 令牌解析失败
				errCode = CheckCode.TOKEN_ERR;
			} else if (CheckCode.TOKEN_OVERDUE.getValue().equals(token.getErrCode())) {
				// aa 令牌过期
				errCode = CheckCode.TOKEN_OVERDUE;
			}
		} else {
			errCode = CheckCode.TOKEN_NULL;
		}

		retuenMap.put("code", errCode);
		retuenMap.put("userInfo", userInfo);
		retuenMap.put("authIdList", authIdList);
		return retuenMap;
	}

	/**
	 * 验证参数
	 * 
	 * @param urlEntity
	 * @param parameterMap
	 * @return
	 */
	private boolean validateParam(SUrlEntity urlEntity, Map<String, String[]> parameterMap) {
		SUrlParamEntity queryParam = new SUrlParamEntity();
		queryParam.setUrlId(urlEntity.getUrlId());
		queryParam.setUrlParamDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SUrlParamEntity> queryWrapper = new QueryWrapper<SUrlParamEntity>(queryParam);
		List<SUrlParamEntity> urlParamList = urlParamService.list(queryWrapper);

		boolean paramCheck = true;

		if (ListUtil.isNotEmpty(urlParamList)) {
			if (parameterMap != null) {
				A: for (SUrlParamEntity urlParam : urlParamList) {
					// aa 参数名
					String paramName = urlParam.getUrlParamName();
					// aa 参数格式（正则表达式）
					String paramValue = urlParam.getUrlParamValue();
					// aa 是否为必须参数
					String paramRequired = urlParam.getUrlParamRequired();
					// aa 是否允许空值
					String paramNull = urlParam.getUrlParamNull();

					String[] params = parameterMap.get(paramName);
					if (ListUtil.isEmpty(params)) {
						if (Code.YES.getValue().equals(paramRequired)) {
							paramCheck = false;
							break A;
						}
						continue A;
					}

					B: for (String param : params) {
						if (StrUtil.isEmpty(param)) {
							if (Code.NO.getValue().equals(paramNull)) {
								paramCheck = false;
								break A;
							}
							continue B;
						}

						if (!Pattern.matches(paramValue, param)) {
							paramCheck = false;
							break A;
						}
					}
				}
			}
		}
		return paramCheck;
	}

}
