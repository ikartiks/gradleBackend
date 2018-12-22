package com.kartiks.view;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VNameValue extends ViewBaseBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * Name
	 */
	protected String name;
	/**
	 * Value
	 */
	protected String value;

	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public VNameValue ( ) {
	}
	
	/**
	 * @param name the key
	 * @param value the value
	 */
	public VNameValue (String name,String value) {
		
		this.name=name;
		this.value=value;
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

	/**
	 * This return the bean content in string format
	 * @return formatedStr
	*/
	@Override
	public String toString( ) {
		String str = "VNameValue={";
		str += super.toString();
		str += "name={" + name + "} ";
		str += "value={" + value + "} ";
		str += "}";
		return str;
	}
}
