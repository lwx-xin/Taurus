package org.taurus.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.taurus.common.CommonField;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.CookieUtil;
import org.taurus.common.util.SessionUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.service.SLogService;

@ControllerAdvice
public class AdviceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SLogService logService;

	@ExceptionHandler(value = CustomException.class)
	public void exceptionHandler(CustomException ex, HttpServletRequest request, HttpServletResponse response) {

		// 请求路径
		String url = StrUtil.toUTF8(request.getServletPath());
		// 错误消息
		String errMessage = StrUtil.toUTF8(ex.getRemark());

		saveErrLog(errMessage, url, ex, request, response);
	}

	@ExceptionHandler(value = Exception.class)
	public void exceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {

		// 请求路径
		String url = StrUtil.toUTF8(request.getServletPath());
		// 错误消息
		String errMessage = StrUtil.toUTF8(ex.getMessage());

		saveErrLog(errMessage, url, ex, request, response);
	}

	private void saveErrLog(String errMessage, String url, Exception ex, HttpServletRequest request, HttpServletResponse response){

		// 是否是ajax请求
		boolean isAjax = false;
		if (StrUtil.isNotEmpty(request.getHeader("req-flg")) && request.getHeader("req-flg").equals("ajax")) {
			isAjax = true;
		}

		response.setStatus(500);
		if (isAjax) {
			response.addHeader(CommonField.SYSTEM_ERR_MSG, errMessage);
			response.addHeader(CommonField.SYSTEM_ERR_SOURCE_PATH, url);
		} else {
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_MSG, errMessage, response);
			CookieUtil.createCookie(CommonField.SYSTEM_ERR_SOURCE_PATH, url, response);
		}

		try {
			if ((ex instanceof CustomException) && (ExecptionType.SYSTEM_LOG.equals(((CustomException)ex).getExecptionType()))) {
				logger.error("记录日志失败");
				logger.error(ex.getMessage());
				return;
			}
			logService.saveLogFile(ex, SessionUtil.getUserId(request));
		} catch (CustomException e) {
			logger.error("记录日志失败");
			logger.error(e.getMessage());
		}
	}

}