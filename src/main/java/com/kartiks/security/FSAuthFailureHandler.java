package com.kartiks.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

public class FSAuthFailureHandler extends
		ExceptionMappingAuthenticationFailureHandler {
	static Logger logger = Logger.getLogger(FSAuthFailureHandler.class);

	String ajaxLoginfailurePage = null;

	public FSAuthFailureHandler() {
		super();
		if (ajaxLoginfailurePage == null) {
			//ajaxLoginfailurePage = PropertiesUtil.getProperty("fs.ajax.auth.failure.page", "/ajax_failure.jsp");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * ExceptionMappingAuthenticationFailureHandler
	 * #onAuthenticationFailure(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String ajaxRequestHeader = request.getHeader("X-Requested-With");
		if (logger.isDebugEnabled()) {
			logger.debug("commence() X-Requested-With=" + ajaxRequestHeader);
		}
		if (exception instanceof ProviderNotFoundException) {
			response.getWriter().write("communication exception");
			WebApplicationException webApplicationException = new WebApplicationException(
					HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			throw webApplicationException;
		}

		if (ajaxRequestHeader != null
				&& ajaxRequestHeader.equalsIgnoreCase("XMLHttpRequest")) {
			if (logger.isDebugEnabled()) {
				logger.debug("Forwarding AJAX login request failure to "
						+ ajaxLoginfailurePage);
			}
			request.getRequestDispatcher(ajaxLoginfailurePage).forward(request,
					response);
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
