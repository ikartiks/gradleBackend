package com.kartiks.security;

public class RequestAndUserSessionHolder {

	private static final ThreadLocal<RequestAndUserSessionWrapper> securityContextThreadLocal = new ThreadLocal<RequestAndUserSessionWrapper>();

	private RequestAndUserSessionHolder() {

	}

	public static RequestAndUserSessionWrapper getSecurityContext() {
		return securityContextThreadLocal.get();
	}

	public static void setSecurityContext(RequestAndUserSessionWrapper context) {
		securityContextThreadLocal.set(context);
	}

	public static void resetSecurityContext() {
		securityContextThreadLocal.remove();
	}

}
