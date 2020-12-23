package org.taurus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.taurus.common.CommonField;
import org.taurus.common.util.CookieUtil;

@ControllerAdvice
public class AdviceController {
	
	@ExceptionHandler(value = Exception.class)
    public void exceptioinHandler(Exception ex,HttpServletRequest request, HttpServletResponse response) {
    	System.err.println(ex.getMessage());

		// 请求路径
		String url = request.getServletPath();
		
    	String errMessage = ex.getMessage();

		response.addHeader(CommonField.SYSTEM_ERR_MSG, errMessage);
//		response.addHeader(CommonField.SYSTEM_ERR_REDIRECT, redirectUrl);
		response.addHeader(CommonField.SYSTEM_ERR_SOURCE_PATH, url);

		CookieUtil.createCookie(CommonField.SYSTEM_ERR_MSG, errMessage, response);
//		CookieUtil.createCookie(CommonField.SYSTEM_ERR_REDIRECT, redirectUrl, response);
		CookieUtil.createCookie(CommonField.SYSTEM_ERR_SOURCE_PATH, url, response);
		
    }

}