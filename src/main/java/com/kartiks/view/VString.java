package com.kartiks.view;

import javax.xml.bind.annotation.XmlRootElement;

import com.kartiks.common.FSCommonEnums;

@XmlRootElement
public class VString extends ViewBaseBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * Value
	 */
	protected String value;

	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public VString (String value ) {
		this.value=value;
	}

	/**
	 * This method sets the value to the member attribute <b>value</b>.
	 * You cannot set null to the attribute.
	 * @param value Value to set member attribute <b>value</b>
	 */
	public void setValue( String value ) {
		this.value = value;
	}

	/**
	 * Returns the value for the member attribute <b>value</b>
	 * @return String - value of member attribute <b>value</b>.
	 */
	public String getValue( ) {
		return this.value;
	}

	@Override
	public int getMyClassType( ) {
	    return FSCommonEnums.CLASS_TYPE_STRING;
	}

	/**
	 * This return the bean content in string format
	 * @return formatedStr
	*/
	@Override
	public String toString( ) {
		String str = "VString={";
		str += super.toString();
		str += "value={" + value + "} ";
		str += "}";
		return str;
	}
}
