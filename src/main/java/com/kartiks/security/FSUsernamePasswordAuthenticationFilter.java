
package com.kartiks.security;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class FSUsernamePasswordAuthenticationFilter extends
	UsernamePasswordAuthenticationFilter {

    static Logger logger = Logger
	    .getLogger(FSUsernamePasswordAuthenticationFilter.class);

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * AbstractAuthenticationProcessingFilter
     * #setRememberMeServices(org.springframework
     * .security.web.authentication.RememberMeServices)
     */
    @Override
    public void setRememberMeServices(RememberMeServices rememberMeServices) {
	if (logger.isDebugEnabled()) {
	    logger.debug("setRememberMeServices() enter: rememberMeServices="
		    + rememberMeServices.toString());
	}
	super.setRememberMeServices(rememberMeServices);
    }

}
