package com.kartiks.biz;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.kartiks.common.RESTErrorUtil;
import com.kartiks.common.UserSessionBase;
import com.kartiks.db.DaoManager;
import com.kartiks.entity.EWUser;
import com.kartiks.security.RequestAndUserSessionHolder;
import com.kartiks.security.RequestAndUserSessionWrapper;
import com.kartiks.view.VString;

@Component
public class SessionMgr {
	static final Logger logger = Logger.getLogger(SessionMgr.class);

	@Autowired
	DaoManager daoManager;
	
	@Autowired
	RESTErrorUtil restErrorUtil;

	public UserSessionBase processSuccessLogin(int authType, String userAgent) {
		return processSuccessLogin(authType, userAgent, null);
	}

	public UserSessionBase processSuccessLogin(int authType, String userAgent, HttpServletRequest httpRequest) {
		
		boolean newSessionCreation = true;
		UserSessionBase userSession = null;

		RequestAndUserSessionWrapper requestAndUserSessionWrapper = RequestAndUserSessionHolder.getSecurityContext();
		if (requestAndUserSessionWrapper != null) {
			userSession = requestAndUserSessionWrapper.getUserSession();
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
		
		String username = authentication.getName();
		if (userSession != null) {
			if (validateUserSession(userSession, username)) {
				newSessionCreation = false;
			}
		}

		if (newSessionCreation) {

			EWUser ewUser=daoManager.getEWUser().findByUserName(username);
			if(ewUser==null)
				throw restErrorUtil.createKartiksRESTException(new VString("error finding user with specified credentials"));
			
			userSession = new UserSessionBase();
			userSession.setEWUser(ewUser);
			requestAndUserSessionWrapper.setUserSession(userSession);

		}

		return userSession;

	}

	protected boolean validateUserSession(UserSessionBase userSession, String username) {

		if (username.equalsIgnoreCase(userSession.getEWUser().getEmail())) {
			return true;
		} else {
			logger.info("loginId doesn't match loginId from HTTPSession. Will create new session. loginId=" + username
					+ ", userSession=" + userSession, new Exception("loginId doesn't match loginId from HTTPSession. Will create new session."));
			return false;
		}
	}

}
