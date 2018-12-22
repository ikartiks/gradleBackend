
package com.kartiks.common;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;

import com.kartiks.security.RequestAndUserSessionHolder;
import com.kartiks.security.RequestAndUserSessionWrapper;

public class ContextUtil {
	static final Logger logger = Logger.getLogger(ContextUtil.class);
	/**
	 * Singleton class
	 */
	private ContextUtil() {
	}

	public static Long getCurrentUserId() {
		RequestAndUserSessionWrapper context = RequestAndUserSessionHolder.getSecurityContext();
		if (context != null) {
			UserSessionBase userSession = context.getUserSession();
			if (userSession != null) {
				return userSession.getEWUser().getId();
			}
		}
		return null;
	}

	public static UserSessionBase getCurrentUserSession() {
		UserSessionBase userSession = null;
		RequestAndUserSessionWrapper context = RequestAndUserSessionHolder.getSecurityContext();
		if (context != null) {
			userSession = context.getUserSession();
		}
		return userSession;
	}

	public static RequestContext getCurrentRequestContext() {
		RequestAndUserSessionWrapper context = RequestAndUserSessionHolder.getSecurityContext();
		if (context != null) {
			return context.getRequestContext();
		}
		return null;
	}

	public static String getCurrentUserLoginId() {
		RequestAndUserSessionWrapper context = RequestAndUserSessionHolder.getSecurityContext();
		if (context != null) {
			UserSessionBase userSession = context.getUserSession();
			if (userSession != null) {
				return userSession.getEWUser().getEmail();
			}
		}
		return null;
	}

	public static String getCurrentSessionId() {
		try{
			return RequestContextHolder.currentRequestAttributes().getSessionId();
		}catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
	

}
