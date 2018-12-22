
package com.kartiks.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class FSAuthSuccessHandler extends
SavedRequestAwareAuthenticationSuccessHandler {
    static Logger logger = Logger.getLogger(FSAuthSuccessHandler.class);

    String ajaxLoginSuccessPage = null;

    public FSAuthSuccessHandler() {
	super();
	if (ajaxLoginSuccessPage == null) {
	    //ajaxLoginSuccessPage = PropertiesUtil.getProperty("fs.ajax.auth.success.page", "/index.html");
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * SavedRequestAwareAuthenticationSuccessHandler
     * #onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
	    HttpServletResponse response, Authentication authentication)
    throws ServletException, IOException {
	String ajaxRequestHeader = request.getHeader("X-Requested-With");
	if (logger.isDebugEnabled()) {
	    logger.debug("commence() X-Requested-With=" + ajaxRequestHeader);
	}
	if (ajaxRequestHeader != null && ajaxRequestHeader.equalsIgnoreCase("XMLHttpRequest")) {
	    if (logger.isDebugEnabled()) {
		logger.debug("Forwarding AJAX login request success to "
			+ ajaxLoginSuccessPage + " for user "
			+ authentication.getName());
	    }
	    request.getRequestDispatcher(ajaxLoginSuccessPage).forward(request,
		    response);
	    clearAuthenticationAttributes(request);
	} else {
	    super.onAuthenticationSuccess(request, response, authentication);
	}
    }

}
