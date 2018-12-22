
package com.kartiks.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.kartiks.biz.SessionMgr;
import com.kartiks.common.RequestContext;
import com.kartiks.common.UserSessionBase;

public class AKASecurityContextFormationFilter extends  GenericFilterBean{
	//
	static Logger logger = Logger.getLogger(AKASecurityContextFormationFilter.class);

	public static final String AKA_SC_SESSION_KEY = "AKA_SECURITY_CONTEXT";
	public static final String USER_AGENT = "User-Agent";

	@Autowired
	SessionMgr sessionMgr;

	String testIP = null;

	public AKASecurityContextFormationFilter() {
		//testIP = PropertiesUtil.getProperty("aka.mystudy.env.ip");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth instanceof AnonymousAuthenticationToken) {
				// ignore
			} else {
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				HttpSession httpSession = httpRequest.getSession(false);
				Cookie[] cookieList = httpRequest.getCookies();
				
				// [1]get the context from session
				RequestAndUserSessionWrapper requestAndUserSessionWrapper = (RequestAndUserSessionWrapper) httpSession.getAttribute(AKA_SC_SESSION_KEY);
				if (requestAndUserSessionWrapper == null) {
					requestAndUserSessionWrapper = new RequestAndUserSessionWrapper();
					httpSession.setAttribute(AKA_SC_SESSION_KEY, requestAndUserSessionWrapper);
				}
				String userAgent = httpRequest.getHeader(USER_AGENT);

				// Get the request specific info
				RequestContext requestContext = new RequestContext();
				String reqIP = testIP;
				if (testIP == null) {
					reqIP = httpRequest.getRemoteAddr();
				}
				requestContext.setIpAddress(reqIP);
				// requestContext.setMsaCookie(msaCookie);
				requestContext.setUserAgent(userAgent);
				// requestContext.setServerRequestId(GUIDUtil.genGUI());
				requestContext.setRequestURL(httpRequest.getRequestURI());
				requestAndUserSessionWrapper.setRequestContext(requestContext);

				RequestAndUserSessionHolder.setSecurityContext(requestAndUserSessionWrapper);
				UserSessionBase userSession = sessionMgr.processSuccessLogin(1, userAgent);

				requestAndUserSessionWrapper.setUserSession(userSession);
			}
			chain.doFilter(request, response);

		} finally {
			//remove context from thread-local
			RequestAndUserSessionHolder.resetSecurityContext();
		}
	}
}
