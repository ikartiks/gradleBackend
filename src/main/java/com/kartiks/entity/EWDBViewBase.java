
package com.kartiks.entity;
import com.kartiks.common.FSCommonEnums;

public class EWDBViewBase extends EWBase implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public EWDBViewBase () {
	}

	@Override
	public int getMyClassType() {
	    return FSCommonEnums.CLASS_TYPE_NONE;
	}

}
