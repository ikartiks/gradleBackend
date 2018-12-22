
package com.kartiks.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.w3c.tidy.Tidy;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Source;

@Component
public class StringUtil implements Serializable {
	static final Logger logger = Logger.getLogger(StringUtil.class);

	@Autowired
	protected RESTErrorUtil restErrorUtil;

	static final public int MIN_PASSWORD_LENGTH = 8;

	static final public String VALIDATION_NAME = "[\\w\\ \\-\\']*";
	static final public String VALIDATION_USERNAME = "^[a-z,A-Z][\\w\\ \\-]*";
	static final public String VALIDATION_NAME_SPECIAL = "[\\w\\ \\-'\\.]*";
	static final public String VALIDATION_TEXT = "[a-zA-Z0-9\\ \"!@#$%^&amp;*()-_=+;:'&quot;|~`&lt;&gt;?/{}\\.\\,\\-\\?<>]*";
	static final public String VALIDATION_TEXT_WITH_NEWLINES = "[a-zA-Z0-9\\ \"!@#$%^&amp;*()-_=+;:'&quot;|~`&lt;&gt;?/{}\\.\\,\\-\\?<>\\r\\n\\t]*";
	static final public String VALIDATION_ALPHANUMERIC = "[a-z,A-Z,0-9]*";
	// Only for Student loginId
	static final public String VALIDATION_LOGINID = "[a-z,A-Z][\\w\\-\\_]*[a-z,A-Z,0-9]";

	static final public String VALIDATION_ALPHA = "[a-z,A-Z]*";
	// \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
	static final public String VALIDATION_IP_ADDRESS = "[\\d\\.\\%\\:]*";
	// "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	// "[0-9]3\\.[0-9]3\\.[0-9]3\\.[0-9]3";

	static HashMap<String, Pattern> compiledRegEx = new HashMap<String, Pattern>();

	static PolicyFactory owaspPolicy = null;
	static final public String[] allowedHTMLElements = new String[] { "b",
			"em", "font", "i", "p", "span", "strong", "sub", "sup", "ol", "ul",
			"li" };

	// Properties
	boolean htmlSanitizeEnabled = true;
	boolean useJTidy = false;
	String[] invalidNames = null;

	/**
     *
     */
	private static final long serialVersionUID = -2102399594424760213L;

	public StringUtil() {
		// Default constructor
		/*useJTidy = PropertiesUtil.getBooleanProperty(
				"aka.mystudy.html.jtidy.use", false);
		htmlSanitizeEnabled = PropertiesUtil.getBooleanProperty(
				"aka.mystudy.html.sanitize.enabled", true);
		invalidNames = PropertiesUtil
				.getPropertyStringList("aka.mystudy.names.invalid");*/
	}

	/**
	 * Checks if the string is null or empty string.
	 * 
	 * @param str
	 * @return true if it is empty string or null
	 */
	public boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public boolean equals(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}

		if (str1 == null || str2 == null) {
			return false;
		}

		return str1.equals(str2);
	}

	public boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}

		if (str1 == null || str2 == null) {
			return false;
		}

		return str1.equalsIgnoreCase(str2);
	}

	public int toInt(String str, int defaultValue) {
		if (!isEmpty(str)) {
			try {
				return Integer.parseInt(str);
			} catch (Throwable t) {
				logger.error("Invalid string passed. Can't be converted to integer. str="
						+ str);
			}
		}
		return defaultValue;
	}

	public Long toLong(String str) {
		if (!isEmpty(str)) {
			try {
				return new Long(str);
			} catch (Throwable t) {
				logger.error("Invalid string passed. Can't be converted to Long. str="
						+ str);
			}
		}
		return null;
	}

	public boolean toBoolean(String str, boolean defaultValue) {
		if (!isEmpty(str)) {
			try {
				return Boolean.parseBoolean(str);
			} catch (Throwable t) {
				logger.error("Invalid string passed. Can't be converted to boolean. str="
						+ str);
			}
		}
		return defaultValue;
	}

	public String toCamelCaseAllWords(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim().toLowerCase();
		StringBuffer result = new StringBuffer(str.length());
		boolean makeUpper = true;
		boolean lastCharSpace = true;
		for (int c = 0; c < str.length(); c++) {
			char ch = str.charAt(c);
			if (lastCharSpace && ch == ' ') {
				continue;
			}

			if (makeUpper) {
				result.append(str.substring(c, c + 1).toUpperCase());
				makeUpper = false;
			} else {
				result.append(ch);
			}
			if (ch == ' ') {
				lastCharSpace = true;
				makeUpper = true;
			} else {
				lastCharSpace = false;
			}

		}
		return result.toString();
	}

	public boolean validatePassword(String password, String[] invalidValues) {
		// For now let's make sure we have minimum 8 characters
		if (password == null) {
			return false;
		}
		password = password.trim();
		if (password.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}

		boolean hasAlpha = false;
		boolean hasNum = false;
		for (int i = 0; i < password.length(); i++) {
			char ch = password.charAt(i);

			if (Character.isDigit(ch)) {
				hasNum = true;
			} else if (Character.isLetter(ch)) {
				hasAlpha = true;
			}
		}

		if (!hasAlpha || !hasNum) {
			return false;
		}

		for (int i = 0; invalidValues != null && i < invalidValues.length; i++) {
			if (password.equalsIgnoreCase(invalidValues[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean validateEmail(String emailAddress) {
		if (emailAddress == null || emailAddress.trim().length() > 128) {
			return false;
		}
		emailAddress = emailAddress.trim();
		// String expression =
		// "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		// String expression =
		// "^[\\w]([\\-\\.\\w])+[\\w]+@([\\w]+[\\w\\-]+[\\w]+\\.)+[A-Z]{2,4}$";
		String expression = "^[\\w]([\\-\\.\\w])+[\\w]+@[\\w]+[\\w\\-]+[\\w]*\\.([\\w]+[\\w\\-]+[\\w]*(\\.[a-z][a-z|0-9]*)?)$";

		// [a-z][a-z|0-9|]*\.([a-z][a-z|0-9]*(\.[a-z][a-z|0-9]*)?)
		// CharSequence inputStr = emailAddress;
		return regExPatternMatch(expression, emailAddress);

	}

	public boolean regExPatternMatch(String expression, String inputStr) {
		Pattern pattern = compiledRegEx.get(expression);
		if (pattern == null) {
			pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			compiledRegEx.put(expression, pattern);
		}

		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

	public boolean validateLoginId(String loginId) {
		if (loginId == null || loginId.trim().length() < 3
				|| loginId.trim().length() > 25) {
			return false;
		}
		loginId = loginId.trim();
		return validateString(VALIDATION_LOGINID, loginId);
	}

	public boolean validateString(String regExStr, String str) {
		try {
			return regExPatternMatch(regExStr, str);
			// CharSequence inputStr = str;
			// Pattern pattern = Pattern.compile(regExStr);
			// Matcher matcher = pattern.matcher(inputStr);
			// return matcher.matches();
		} catch (Throwable t) {
			logger.info("Error validating string. str=" + str, t);
			return false;
		}
	}

	/**
	 * @param loginId
	 * @return
	 */
	public String normalizeLoginId(String loginId) {
		// Make email address as lower case
		if (loginId != null) {
			return loginId.trim().toLowerCase();
		}
		return null;
	}

	public String normalizeEmail(String email) {
		// Make email address as lower case
		if (email != null) {
			return email.trim().toLowerCase();
		}
		return null;
	}

	public String sanitizeStringLowerCase(String str) {
		if (str != null) {
			return str.trim().toLowerCase();
		}
		return null;
	}

	public String sanitizeString(String validChars, String str) {
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			if (validChars.indexOf(chars[i]) >= 0) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	public String validateMobileNumber(String value) {
		// TODO Need to implement mobile number validation
		return value;
	}

	public boolean containSpecialCharacters(String str) {
		return !validateString(VALIDATION_NAME, str);
	}

	/*
	 * public HTMLSanitizedContent getHTMLSanitizedContent(int mimeType, String
	 * textContent, String htmlContent, Long idForLogging, String fieldName) {
	 * 
	 * HTMLSanitizedContent content = new HTMLSanitizedContent(); if (mimeType
	 * == FSConstants.MIME_UNKNOWN) { // By default it is TEXT mimeType =
	 * FSConstants.MIME_TEXT; } content.setMimeType(mimeType);
	 * 
	 * if (mimeType == FSConstants.MIME_TEXT) {
	 * restErrorUtil.validateString(textContent, VALIDATION_TEXT_WITH_NEWLINES,
	 * "Invalid characters in content", MessageEnums.INVALID_INPUT_DATA, null,
	 * "fieldName", true); content.setHtmlContent(null);
	 * content.setTextContent(textContent); } else if (mimeType ==
	 * FSConstants.MIME_HTML) { String htmlStr = htmlContent;
	 * restErrorUtil.validateNotNull(htmlStr, "Content is empty", idForLogging,
	 * "richText");
	 * 
	 * // Sanitize String sanitizedHtmlStr = sanitizeHTML(htmlStr);
	 * restErrorUtil.validateNotNull(sanitizedHtmlStr, "Content is empty",
	 * idForLogging, "richText");
	 * 
	 * content.setHtmlContent(sanitizedHtmlStr);
	 * 
	 * // Create text equivalent String txtStr = htmlToText(sanitizedHtmlStr,
	 * false); content.setTextContent(txtStr); } else {
	 * restErrorUtil.createRESTException( "Not supported content mime type",
	 * MessageEnums.INVALID_INPUT_DATA, idForLogging, "mimeType", "" +
	 * mimeType); }
	 * 
	 * return content;
	 * 
	 * }
	 */

	public String sanitizeHTML(String htmlStr) {
		if (!htmlSanitizeEnabled) {
			return htmlStr;
		}
		String owaspOut = null;
		try {
			PolicyFactory owaspPolicy = getOWASPPolicy();
			owaspOut = owaspPolicy.sanitize(htmlStr);
			// TODO KARTIK
//			if (useJTidy && !isEmpty(owaspOut)) {
//				Tidy jTidy = new Tidy();
//				jTidy.setTidyMark(true);
//				jTidy.setShowErrors(0);
//				jTidy.setShowWarnings(false);
//				jTidy.setDocType("omit");
//				jTidy.setQuiet(true);
//				jTidy.setHideComments(true);
//				jTidy.setPrintBodyOnly(true);
//				jTidy.setDropEmptyParas(true);
//
//				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//				InputStream is = new ByteArrayInputStream(owaspOut.getBytes());
//				jTidy.parseDOM(is, outStream);
//				String tidyOut = outStream.toString().trim();
//				if (isEmpty(tidyOut)) {
//					logger.warn("jTidy gave empty result. originalHTML="
//							+ htmlStr + ", owaspOut=" + owaspOut + ", jTidy="
//							+ tidyOut);
//					return owaspOut;
//				}
//				return tidyOut;
//			}
		} catch (Throwable t) {
			logger.error(
					"Original input:" + htmlStr + ", owaspOut=" + owaspOut, t);
		}
		return owaspOut;
	}

	/**
	 * @return
	 */
	private static PolicyFactory getOWASPPolicy() {
		if (owaspPolicy == null) {
			HtmlPolicyBuilder policyBuilder = new HtmlPolicyBuilder();

			for (int i = 0; i < allowedHTMLElements.length; i++) {
				policyBuilder.allowElements(allowedHTMLElements[i]);
			}
			owaspPolicy = policyBuilder.toFactory();
		}
		return owaspPolicy;
	}

	public String htmlToText(String htmlStr, boolean sanitize) {
		if (sanitize) {
			htmlStr = sanitizeHTML(htmlStr);
		}
		if (!isEmpty(htmlStr)) {
			Source strSrc = new Source(htmlStr);
			Renderer renderer = new Renderer(strSrc);
			String txtStr = renderer.toString();
			return txtStr;
		}
		return null;
	}

	public String[] split(String value) {
		return split(value, ",");
	}

	public String[] split(String value, String delimiter) {
		if (value != null) {
			String[] splitValues = value.split(delimiter);
			String[] returnValues = new String[splitValues.length];
			int c = -1;
			for (int i = 0; i < splitValues.length; i++) {
				String str = splitValues[i].trim();
				if (str.length() > 0) {
					c++;
					returnValues[c] = str;
				}
			}
			return returnValues;
		} else {
			return new String[0];
		}
	}

	public static String trim(String str) {
		return str != null ? str.trim() : null;
	}

	/**
	 * @param firstName
	 * @return
	 */
	public boolean isValidName(String name) {
		if (name == null || name.trim().length() < 1) {
			return false;
		}
		for (String invalidName : invalidNames) {
			if (name.toUpperCase().trim()
					.startsWith(invalidName.toUpperCase().trim())) {
				return false;
			}
		}
		return validateString(VALIDATION_NAME, name);
	}

	/*public List<String> loadListFromFile(String fileName) {

		String propFileName = PropertiesUtil
				.getProperty("aka.mystudy.content.profanity.words.file");
		List<String> returnList = new ArrayList<String>();
		try {
			InputStream is = getClass().getResourceAsStream(fileName);
			if (is == null) {
				fileName = "/" + fileName;
				is = getClass().getResourceAsStream("/" + fileName);
			}
			if (is != null) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String line = null;
				while ((line = br.readLine()) != null
						&& !line.trim().equalsIgnoreCase("")) {
					returnList.add(line.trim());
				}
				logger.info("Loaded list file from " + fileName);
			} else {
				logger.warn("File for loading list not found propFileName="
						+ propFileName + " and also slashedFileName="
						+ fileName, new Throwable());
			}
		} catch (Throwable t) {
			logger.error("Error loading list from file. fileName=" + fileName,
					t);
		}
		return returnList;
	}*/

	public String replaceWithDBWildcardsChar(String str) {
		String ret = "%";
		if (!isEmpty(str)) {
			ret = str.replaceAll("\\*", "%");
		}
		return ret;
	}
}
