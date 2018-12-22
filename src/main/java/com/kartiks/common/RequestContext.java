
package com.kartiks.common;

import java.io.Serializable;


public class RequestContext implements Serializable {
	private static final long serialVersionUID = -7083383106845193385L;
	String ipAddress = null;
	String msaCookie = null;
	String userAgent = null;
	String requestURL = null;
	String serverRequestId = null;
	boolean isSync = true;
	long startTime = System.currentTimeMillis();

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress
	 *            the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the msaCookie
	 */
	public String getMsaCookie() {
		return msaCookie;
	}

	/**
	 * @param msaCookie
	 *            the msaCookie to set
	 */
	public void setMsaCookie(String msaCookie) {
		this.msaCookie = msaCookie;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent
	 *            the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @return the serverRequestId
	 */
	public String getServerRequestId() {
		return serverRequestId;
	}

	/**
	 * @param serverRequestId
	 *            the serverRequestId to set
	 */
	public void setServerRequestId(String serverRequestId) {
		this.serverRequestId = serverRequestId;
	}

	/**
	 * @return the isSync
	 */
	public boolean isSync() {
		return isSync;
	}

	/**
	 * @param isSync
	 *            the isSync to set
	 */
	public void setSync(boolean isSync) {
		this.isSync = isSync;
	}

	/**
	 * @return the location
	 */
	/**
	 * @return the requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param requestURL
	 *            the requestURL to set
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestContext [ipAddress=" + ipAddress + ", msaCookie="
				+ msaCookie + ", userAgent=" + userAgent + ", requestURL="
				+ requestURL
				+ ", serverRequestId=" + serverRequestId
				+ ", isSync=" + isSync + ", startTime=" + startTime
				+ "]";
	}

}
