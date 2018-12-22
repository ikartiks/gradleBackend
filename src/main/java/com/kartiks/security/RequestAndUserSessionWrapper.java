package com.kartiks.security;

import java.io.Serializable;

import com.kartiks.common.RequestContext;
import com.kartiks.common.UserSessionBase;

public class RequestAndUserSessionWrapper implements Serializable{
    private static final long serialVersionUID = 1L;
    private UserSessionBase userSession;
    private RequestContext requestContext;

    public UserSessionBase getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSessionBase userSession) {
        this.userSession = userSession;
    }

    /**
     * @return the requestContext
     */
    public RequestContext getRequestContext() {
        return requestContext;
    }

    /**
     * @param requestContext the requestContext to set
     */
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }


}
