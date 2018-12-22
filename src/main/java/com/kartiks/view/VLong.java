package com.kartiks.view;

import javax.xml.bind.annotation.XmlRootElement;

import com.kartiks.common.FSCommonEnums;
import com.kartiks.db.view.VDataObject;


@XmlRootElement
public class VLong extends VDataObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * Value
	 */
	protected long value;

	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public VLong ( ) {
	}

	/**
	 * This method sets the value to the member attribute <b>value</b>.
	 * You cannot set null to the attribute.
	 * @param value Value to set member attribute <b>value</b>
	 */
	public void setValue( long value ) {
		this.value = value;
	}

	/**
	 * Returns the value for the member attribute <b>value</b>
	 * @return long - value of member attribute <b>value</b>.
	 */
	public long getValue( ) {
		return this.value;
	}

	@Override
	public int getMyClassType( ) {
	    return FSCommonEnums.CLASS_TYPE_LONG;
	}

	/**
	 * This return the bean content in string format
	 * @return formatedStr
	*/
	@Override
	public String toString() {
		String str = "VLong={";
		str += super.toString();
		str += "value={" + value + "} ";
		str += "}";
		return str;
	}
}
