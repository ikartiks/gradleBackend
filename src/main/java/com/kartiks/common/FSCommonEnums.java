package com.kartiks.common;

import com.kartiks.entity.EWUser;

public class FSCommonEnums {

	public static final int MIME_UNKNOWN = 0;

	public static final int MIME_TEXT = 1;

	public static final int MIME_HTML = 2;

	public static final int MIME_PNG = 3;

	public static final int MIME_JPEG = 4;

	public static final int MimeType_MAX = 4;

	/***************************************************************
	 * Enum values for ClassTypes
	 **************************************************************/

	public static final int CLASS_TYPE_NONE = 0;

	public static final int CLASS_TYPE_LONG = 1;

	public static final int CLASS_TYPE_DBBASE = 2;

	public static final int CLASS_TYPE_DBVIEW_BASE = 3;

	public static final int CLASS_TYPE_STRING = 4;

	public static final int CLASS_TYPE_EWUser = 5;
	
	public static final int CLASS_TYPE_EWPredefinedReasons = 6;
	
	public static final int CLASS_TYPE_EWSales = 7;
	
	public static final int CLASS_TYPE_EWUserContacts = 8;
	
	public static final int CLASS_TYPE_EWRoles = 9;
	
	public static final int CLASS_TYPE_EWSalesPersons = 10;
	
	/**
	 * Max value for enum ClassTypes_MAX
	 */
	public static final int ClassTypes_MAX = 11;

	static public String getLabelFor_MimeType(int elementValue) {
		if (elementValue == 0) {
			return "Unknown"; // MIME_UNKNOWN
		}
		if (elementValue == 1) {
			return "Text"; // MIME_TEXT
		}
		if (elementValue == 2) {
			return "Html"; // MIME_HTML
		}
		if (elementValue == 3) {
			return "png"; // MIME_PNG
		}
		if (elementValue == 4) {
			return "jpeg"; // MIME_JPEG
		}
		return null;
	}

	static public String getLabelFor_ClassTypes(int elementValue) {
		
		if (elementValue == 0) {
			return "None"; // CLASS_TYPE_NONE
		}
		
		if (elementValue == 1) {
			return "Long"; // CLASS_TYPE_LONG
		}
		
		if (elementValue == 2) {
			return "DB Base"; // CLASS_TYPE_DBBASE
		}
		
		if (elementValue == 3) {
			return "DB View Base"; // CLASS_TYPE_DBVIEW_BASE
		}
		
		if (elementValue == 4) {
			return "VString";
		}
		
		if (elementValue == 5) {
			return EWUser.class.getSimpleName(); 
		}
		
		
		
		return null;
	}

}
