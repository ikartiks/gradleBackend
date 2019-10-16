
package com.kartiks.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kartiks.view.VMessage;
import com.kartiks.view.VResponse;
import com.kartiks.view.VString;

/**
 * @author bosco
 * 
 */
@Component
public class RESTErrorUtil {

	static final Logger logger = Logger.getLogger(RESTErrorUtil.class);

	@Autowired
	StringUtil stringUtil;

	public static final String TRUE = "true";

	public WebApplicationException createRESTException(VResponse gjResponse) {
		Response errorResponse = Response
				.status(javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST)
				.entity(gjResponse).build();

		WebApplicationException restException = new WebApplicationException(
				errorResponse);
		restException.fillInStackTrace();
		UserSessionBase userSession = ContextUtil.getCurrentUserSession();
		Long sessionId = null;
		String loginId = null;
		if (userSession != null) {
			loginId = userSession.getLoginId();
			//sessionId = userSession.getSessionId();
		}

		logger.info("Request failed. SessionId=" + sessionId + ", loginId="
				+ loginId + ", logMessage=" + gjResponse.getMsgDesc(),
				restException);

		return restException;
	}
	
	
	public WebApplicationException createKartiksRESTException(VString errorMessage) {
		Response errorResponse = Response
				.status(javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST)				
				.entity(errorMessage).build();

		WebApplicationException restException = new WebApplicationException(errorResponse);
		restException.fillInStackTrace();
		UserSessionBase userSession = ContextUtil.getCurrentUserSession();
		Long sessionId = null;
		String loginId = null;
		if (userSession != null) {
			loginId = userSession.getLoginId();
			//sessionId = userSession.getSessionId();
		}

		logger.info("Request failed. SessionId=" + sessionId + ", loginId="
				+ loginId + ", logMessage=" + errorMessage,
				restException);

		return restException;
	}

	/**
	 * 
	 * @param logMessage
	 *            This is optional
	 * @return
	 */
	public WebApplicationException create403RESTException(String logMessage) {
		Response errorResponse = Response.status(
				javax.servlet.http.HttpServletResponse.SC_FORBIDDEN).build();

		WebApplicationException restException = new WebApplicationException(
				errorResponse);
		restException.fillInStackTrace();
		// Get user information
		UserSessionBase userSession = ContextUtil.getCurrentUserSession();
		Long sessionId = null;
		String loginId = null;
		String sessionInfo = "";
		if (userSession != null) {
			loginId = userSession.getLoginId();
			sessionInfo = userSession.toString();
			//sessionId = userSession.getSessionId();
		}

		String requestInfo = "";
		try {
			RequestContext reqContext = ContextUtil.getCurrentRequestContext();
			if (reqContext != null) {
				requestInfo = reqContext.toString();
				requestInfo += ", timeTaken="
						+ (System.currentTimeMillis() - reqContext
								.getStartTime());
			}
		} catch (Throwable contextEx) {
			logger.error("Error getting request info", contextEx);
		}

		logger.error("Access restricted. SessionId=" + sessionId + ", loginId="
				+ loginId + ", logMessage=" + logMessage + ", requestInfo="
				+ requestInfo + ", sessionInfo=" + sessionInfo, restException);

		return restException;
	}

	public boolean parseBoolean(String value, boolean defaultValue) {
		if (stringUtil.isEmpty(value)) {
			return defaultValue;
		}
		return TRUE.equalsIgnoreCase(value.trim());
	}

	public Boolean parseBoolean(String value, String errorMessage,
			MessageEnums messageEnum, Long objectId, String fieldName) {
		try {
			if (stringUtil.isEmpty(value)) {
				return null;
			} else {
				return new Boolean(value.trim());
			}
		} catch (Throwable t) {
			throw createRESTException(errorMessage, messageEnum, objectId,
					fieldName, value);
		}
	}

	public Integer parseInt(String value, String errorMessage,
			MessageEnums messageEnum, Long objectId, String fieldName) {
		try {
			if (stringUtil.isEmpty(value)) {
				return null;
			} else {
				return new Integer(value.trim());
			}
		} catch (Throwable t) {
			throw createRESTException(errorMessage, messageEnum, objectId,
					fieldName, value);
		}
	}

	public Integer parseInt(String value, int defaultValue,
			String errorMessage, MessageEnums messageEnum, Long objectId,
			String fieldName) {
		try {
			if (stringUtil.isEmpty(value)) {
				return new Integer(defaultValue);
			} else {
				return new Integer(value.trim());
			}
		} catch (Throwable t) {
			throw createRESTException(errorMessage, messageEnum, objectId,
					fieldName, value);
		}
	}

	public Long parseLong(String value, Long defaultValue) {
		if (stringUtil.isEmpty(value)) {
			return defaultValue;
		}
		return new Long(value.trim());
	}

	public Long parseLong(String value, String errorMessage,
			MessageEnums messageEnum, Long objectId, String fieldName) {
		try {
			if (stringUtil.isEmpty(value)) {
				return null;
			} else {
				return new Long(value.trim());
			}
		} catch (Throwable t) {
			throw createRESTException(errorMessage, messageEnum, objectId,
					fieldName, value);
		}
	}

	public Long parseLong(String value, long defaultValue, String errorMessage,
			MessageEnums messageEnum, Long objectId, String fieldName) {
		try {
			if (stringUtil.isEmpty(value)) {
				return new Long(defaultValue);
			} else {
				return new Long(value.trim());
			}
		} catch (Throwable t) {
			throw createRESTException(errorMessage, messageEnum, objectId,
					fieldName, value);
		}
	}

	public String validateString(String value, String regExStr,
			String errorMessage, MessageEnums messageEnum, Long objectId,
			String fieldName) {
		return validateString(value, regExStr, errorMessage, messageEnum,
				objectId, fieldName, false);

	}

	public String validateString(String value, String regExStr,
			String errorMessage, MessageEnums messageEnum, Long objectId,
			String fieldName, boolean isMandatory) {
		if (stringUtil.isEmpty(value)) {
			if (isMandatory) {
				throw createRESTException(errorMessage,
						MessageEnums.NO_INPUT_DATA, objectId, fieldName, null);
			}
			return null;
		}
		value = value.trim();
		if (value.length() != 0) {
			if (!stringUtil.validateString(regExStr, value)) {
				throw createRESTException(errorMessage, messageEnum, objectId,
						fieldName, value);
			}
			return value;
		} else {
			return null;
		}

	}

	public String validateStringForUpdate(String value, String originalValue,
			String regExStr, String errorMessage, MessageEnums messageEnum,
			Long objectId, String fieldName) {
		return validateStringForUpdate(value, originalValue, regExStr,
				errorMessage, messageEnum, objectId, fieldName, false);
	}

	public String validateStringForUpdate(String value, String originalValue,
			String regExStr, String errorMessage, MessageEnums messageEnum,
			Long objectId, String fieldName, boolean isMandatory) {
		if (stringUtil.isEmpty(value)) {
			if (isMandatory) {
				throw createRESTException(errorMessage,
						MessageEnums.NO_INPUT_DATA, objectId, fieldName, null);
			}
			return null;
		}

		if (!value.equalsIgnoreCase(originalValue)) {
			return validateString(value, StringUtil.VALIDATION_NAME,
					errorMessage, messageEnum, objectId, fieldName);
		} else {
			return value;
		}
	}

	public void validateStringList(String value, String[] validValues,
			String errorMessage, Long objectId, String fieldName) {
		for (int i = 0; i < validValues.length; i++) {
			if (validValues[i].equals(value)) {
				return;
			}
		}
		throw createRESTException(errorMessage,
				MessageEnums.INVALID_INPUT_DATA, objectId, fieldName, value);
	}

	public void validateEmail(String value, String errorMessage, Long objectId,
			String fieldName) {
		if (value == null || value.toString().trim().length() == 0
				|| !stringUtil.validateEmail(value)) {
			throw createRESTException(errorMessage,
					MessageEnums.INVALID_INPUT_DATA, objectId, fieldName, value);
		}
	}

	public void validateNotNull(Object value, String errorMessage,
			Long objectId, String fieldName) {
		if (value == null || value.toString().trim().length() == 0) {
			throw createRESTException(errorMessage, MessageEnums.NO_INPUT_DATA,
					objectId, fieldName, null);
		}
	}

	public void validateNotNull(Object value, String errorMessage,
			Long objectId, String fieldName, String logMessage) {
		if (value == null || value.toString().trim().length() == 0) {
			throw createRESTException(errorMessage, MessageEnums.NO_INPUT_DATA,
					objectId, fieldName, logMessage);
		}
	}

	public void validateMinMax(int value, int minValue, int maxValue,
			String errorMessage, Long objectId, String fieldName) {
		if (value < minValue || value > maxValue) {
			throw createRESTException(errorMessage,
					MessageEnums.INPUT_DATA_OUT_OF_BOUND, objectId, fieldName,
					"" + value);
		}
	}

	public String validateMobileNumber(String value, String errorMessage,
			Long objectId, String fieldName) {
		value = value.trim();
		if (value.length() != 0) {
			return stringUtil.validateMobileNumber(value);
		} else {
			return null;
		}
	}

	public WebApplicationException createRESTException(String errorMessage,
			MessageEnums messageEnum, Long objectId, String fieldName,
			String logMessage) {
		List<VMessage> messageList = new ArrayList<VMessage>();
		messageList.add(messageEnum.getMessage(objectId, fieldName));

		VResponse gjResponse = new VResponse();
		gjResponse.setStatusCode(VResponse.STATUS_ERROR);
		gjResponse.setMsgDesc(errorMessage);
		gjResponse.setMessageList(messageList);
		WebApplicationException webAppEx = createRESTException(gjResponse);
		logger.info("Validation error:logMessage=" + logMessage + ", response="
				+ gjResponse, webAppEx);
		return webAppEx;
	}

	public WebApplicationException createRESTException(String errorMessage,
			MessageEnums messageEnum) {
		List<VMessage> messageList = new ArrayList<VMessage>();
		messageList.add(messageEnum.getMessage());

		VResponse gjResponse = new VResponse();
		gjResponse.setStatusCode(VResponse.STATUS_ERROR);
		gjResponse.setMsgDesc(errorMessage);
		gjResponse.setMessageList(messageList);
		WebApplicationException webAppEx = createRESTException(gjResponse);
		logger.info("Operation error. response=" + gjResponse, webAppEx);
		return webAppEx;
	}

	public WebApplicationException createRESTExceptionSystemError(
			String logMessage) {
		List<VMessage> messageList = new ArrayList<VMessage>();
		messageList.add(MessageEnums.ERROR_SYSTEM.getMessage());

		VResponse gjResponse = new VResponse();
		gjResponse.setStatusCode(VResponse.STATUS_ERROR);
		gjResponse
				.setMsgDesc("Sorry there was a system error. Please try later.");
		gjResponse.setMessageList(messageList);
		WebApplicationException webAppEx = createRESTException(gjResponse);
		logger.info("Validation error:logMessage=" + logMessage + ", response="
				+ gjResponse, webAppEx);
		return webAppEx;
	}

	
	public Date parseDate(String value, String errorMessage,
			MessageEnums messageEnum, Long objectId, String fieldName,
			String dateFormat) {
		try {
			if (stringUtil.isEmpty(value)) {
				return null;
			} else {
				DateFormat formatter = new SimpleDateFormat(dateFormat);
				return formatter.parse(value);

			}
		} catch (Throwable t) {
			throw createRESTException(errorMessage, messageEnum, objectId,
					fieldName, value);
		}
	}
	
}
