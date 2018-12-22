
package com.kartiks.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class FSAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	static Logger logger = Logger.getLogger(FSAuthenticationEntryPoint.class);
	static int ajaxReturnCode = -1;

	public FSAuthenticationEntryPoint() {
		//TODO kartik. By default below super line was super() only check why
		super("");
		if (logger.isDebugEnabled()) {
			logger.debug("AjaxAwareAuthenticationEntryPoint(): constructor");
		}

		if (ajaxReturnCode < 0) {
			/*
			 * ajaxReturnCode = PropertiesUtil.getIntProperty( "fs.ajax.auth.required.code",
			 * 401);
			 */
		}
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String ajaxRequestHeader = request.getHeader("X-Requested-With");
		if (logger.isDebugEnabled()) {
			logger.debug("commence() X-Requested-With=" + ajaxRequestHeader);
		}

		if (ajaxRequestHeader != null && ajaxRequestHeader.equalsIgnoreCase("XMLHttpRequest")) {
			if (logger.isDebugEnabled()) {
				logger.debug("commence() AJAX request. Authentication required. Returning " + ajaxReturnCode + ". URL="
						+ request.getRequestURI());
			}
			response.sendError(ajaxReturnCode, "");
		} else {
			super.commence(request, response, authException);
		}
	}
}
