package com.kartiks.view;

import javax.xml.bind.annotation.XmlRootElement;

import com.kartiks.common.FSConstants;

@XmlRootElement
public class VMessage extends ViewBaseBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * Message key
	 */
	protected String name;
	/**
	 * Resource bundle key
	 */
	protected String rbKey;
	/**
	 * Message description. Use rbKey for doing localized lookup
	 */
	protected String message;
	/**
	 * Id of the object to which this message is related to
	 */
	protected Long objectId;
	/**
	 * Name of the field or attribute to which this message is related to
	 */
	protected String fieldName;

	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public VMessage ( ) {
	}

	/**
	 * This method sets the value to the member attribute <b>name</b>.
	 * You cannot set null to the attribute.
	 * @param name Value to set member attribute <b>name</b>
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * Returns the value for the member attribute <b>name</b>
	 * @return String - value of member attribute <b>name</b>.
	 */
	public String getName( ) {
		return this.name;
	}

	/**
	 * This method sets the value to the member attribute <b>rbKey</b>.
	 * You cannot set null to the attribute.
	 * @param rbKey Value to set member attribute <b>rbKey</b>
	 */
	public void setRbKey( String rbKey ) {
		this.rbKey = rbKey;
	}

	/**
	 * Returns the value for the member attribute <b>rbKey</b>
	 * @return String - value of member attribute <b>rbKey</b>.
	 */
	public String getRbKey( ) {
		return this.rbKey;
	}

	/**
	 * This method sets the value to the member attribute <b>message</b>.
	 * You cannot set null to the attribute.
	 * @param message Value to set member attribute <b>message</b>
	 */
	public void setMessage( String message ) {
		this.message = message;
	}

	/**
	 * Returns the value for the member attribute <b>message</b>
	 * @return String - value of member attribute <b>message</b>.
	 */
	public String getMessage( ) {
		return this.message;
	}

	/**
	 * This method sets the value to the member attribute <b>objectId</b>.
	 * You cannot set null to the attribute.
	 * @param objectId Value to set member attribute <b>objectId</b>
	 */
	public void setObjectId( Long objectId ) {
		this.objectId = objectId;
	}

	/**
	 * Returns the value for the member attribute <b>objectId</b>
	 * @return Long - value of member attribute <b>objectId</b>.
	 */
	public Long getObjectId( ) {
		return this.objectId;
	}

	/**
	 * This method sets the value to the member attribute <b>fieldName</b>.
	 * You cannot set null to the attribute.
	 * @param fieldName Value to set member attribute <b>fieldName</b>
	 */
	public void setFieldName( String fieldName ) {
		this.fieldName = fieldName;
	}

	/**
	 * Returns the value for the member attribute <b>fieldName</b>
	 * @return String - value of member attribute <b>fieldName</b>.
	 */
	public String getFieldName( ) {
		return this.fieldName;
	}

	@Override
	public int getMyClassType( ) {
	    return FSConstants.CLASS_TYPE_MESSAGE;
	}

	/**
	 * This return the bean content in string format
	 * @return formatedStr
	*/
	@Override
	public String toString( ) {
		String str = "VMessage={";
		str += super.toString();
		str += "name={" + name + "} ";
		str += "rbKey={" + rbKey + "} ";
		str += "message={" + message + "} ";
		str += "objectId={" + objectId + "} ";
		str += "fieldName={" + fieldName + "} ";
		str += "}";
		return str;
	}
}
