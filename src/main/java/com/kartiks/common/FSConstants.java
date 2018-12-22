package com.kartiks.common;

import javax.servlet.http.HttpServletRequest;

public class FSConstants extends FSCommonEnums {

	public static final String imageStoreBasePath = "/Users/kartikshah/Documents/workspace/javaWorkspace/HopWeb/WebContent/images/";

	public static String getBasePath(HttpServletRequest request) {

		return request.getServletContext().getRealPath("");
	}

	/**
	 * CLASS_TYPE_MESSAGE is an element of enum ClassTypes. Its value is
	 * "CLASS_TYPE_MESSAGE".
	 */
	public static final int CLASS_TYPE_MESSAGE = 1;

	/**
	 * CLASS_TYPE_MESSAGE is an element of enum ClassTypes. Its value is
	 * "CLASS_TYPE_MESSAGE".
	 */
	public static final int CLASS_TYPE_RESPONSE = 2;

}
