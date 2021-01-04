package org.taurus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.taurus.common.CommonField;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.StrUtil;

@ControllerAdvice
public class AdviceController {

	@ExceptionHandler(value = CustomException.class)
	public void exceptioinHandler(CustomException ex, HttpServletRequest request, HttpServletResponse response) {

		// 请求路径
		String url = request.getServletPath();

		String errMessage = StrUtil.toUTF8(ex.getRemark());

		// 是否是ajax请求
		boolean isAjax = false;
		if (StrUtil.isNotEmpty(request.getHeader("req-flg")) && request.getHeader("req-flg").equals("ajax")) {
			isAjax = true;
		}

		if (isAjax) {
			response.addHeader(CommonField.SYSTEM_ERR_MSG, errMessage);
			response.addHeader(CommonField.SYSTEM_ERR_SOURCE_PATH, url);
		} else {
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_MSG, errMessage, response);
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_SOURCE_PATH, url, response);
		}

	}

	@ExceptionHandler(value = Exception.class)
	public void exceptioinHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {

		// 请求路径
		String url = request.getServletPath();

		String errMessage = StrUtil.toUTF8(ex.getMessage());

		// 是否是ajax请求
		boolean isAjax = false;
		if (StrUtil.isNotEmpty(request.getHeader("req-flg")) && request.getHeader("req-flg").equals("ajax")) {
			isAjax = true;
		}

		if (isAjax) {
			response.addHeader(CommonField.SYSTEM_ERR_MSG, errMessage);
			response.addHeader(CommonField.SYSTEM_ERR_SOURCE_PATH, url);
		} else {
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_MSG, errMessage, response);
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_SOURCE_PATH, url, response);
		}

	}

}