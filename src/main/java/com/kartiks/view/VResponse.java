package com.kartiks.view;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.kartiks.common.FSConstants;

@XmlRootElement
public class VResponse extends ViewBaseBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Enum values for ResponseStatus
	 */
	/**
	 * STATUS_SUCCESS is an element of enum ResponseStatus. Its value is "STATUS_SUCCESS".
	 */
	public static final int STATUS_SUCCESS = 0;
	/**
	 * STATUS_ERROR is an element of enum ResponseStatus. Its value is "STATUS_ERROR".
	 */
	public static final int STATUS_ERROR = 1;
	/**
	 * STATUS_VALIDATION is an element of enum ResponseStatus. Its value is "STATUS_VALIDATION".
	 */
	public static final int STATUS_VALIDATION = 2;
	/**
	 * STATUS_WARN is an element of enum ResponseStatus. Its value is "STATUS_WARN".
	 */
	public static final int STATUS_WARN = 3;
	/**
	 * STATUS_INFO is an element of enum ResponseStatus. Its value is "STATUS_INFO".
	 */
	public static final int STATUS_INFO = 4;
	/**
	 * STATUS_PARTIAL_SUCCESS is an element of enum ResponseStatus. Its value is "STATUS_PARTIAL_SUCCESS".
	 */
	public static final int STATUS_PARTIAL_SUCCESS = 5;

	/**
	 * Max value for enum ResponseStatus_MAX
	 */
	public static final int ResponseStatus_MAX = 5;


	/**
	 * Status code
	 * This attribute is of type enum Response::ResponseStatus
	 */
	protected int statusCode;
	/**
	 * Message description
	 */
	protected String msgDesc;
	/**
	 * List of messages
	 */
	protected List<VMessage> messageList;

	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public VResponse ( ) {
		statusCode = 0;
	}

	/**
	 * This method sets the value to the member attribute <b>statusCode</b>.
	 * You cannot set null to the attribute.
	 * @param statusCode Value to set member attribute <b>statusCode</b>
	 */
	public void setStatusCode( int statusCode ) {
		this.statusCode = statusCode;
	}

	/**
	 * Returns the value for the member attribute <b>statusCode</b>
	 * @return int - value of member attribute <b>statusCode</b>.
	 */
	public int getStatusCode( ) {
		return this.statusCode;
	}

	/**
	 * This method sets the value to the member attribute <b>msgDesc</b>.
	 * You cannot set null to the attribute.
	 * @param msgDesc Value to set member attribute <b>msgDesc</b>
	 */
	public void setMsgDesc( String msgDesc ) {
		this.msgDesc = msgDesc;
	}

	/**
	 * Returns the value for the member attribute <b>msgDesc</b>
	 * @return String - value of member attribute <b>msgDesc</b>.
	 */
	public String getMsgDesc( ) {
		return this.msgDesc;
	}

	/**
	 * This method sets the value to the member attribute <b>messageList</b>.
	 * You cannot set null to the attribute.
	 * @param messageList Value to set member attribute <b>messageList</b>
	 */
	public void setMessageList( List<VMessage> messageList ) {
		this.messageList = messageList;
	}

	/**
	 * Returns the value for the member attribute <b>messageList</b>
	 * @return List<VMessage> - value of member attribute <b>messageList</b>.
	 */
	public List<VMessage> getMessageList( ) {
		return this.messageList;
	}

	@Override
	public int getMyClassType( ) {
	    return FSConstants.CLASS_TYPE_RESPONSE;
	}

	/**
	 * This return the bean content in string format
	 * @return formatedStr
	*/
	@Override
	public String toString( ) {
		String str = "VResponse={";
		str += super.toString();
		str += "statusCode={" + statusCode + "} ";
		str += "msgDesc={" + msgDesc + "} ";
		str += "messageList={" + messageList + "} ";
		str += "}";
		return str;
	}
}
