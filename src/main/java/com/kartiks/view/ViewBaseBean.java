package com.kartiks.view;

import com.kartiks.common.FSCommonEnums;
import com.kartiks.entity.EWBase;

public class ViewBaseBean implements java.io.Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2953371059320142151L;
	
	
	private EWBase mObj = null;

	// TODO kartik do not expose this object via gson
	//https://www.baeldung.com/gson-exclude-fields-serialization
	public EWBase getMObj() {
		return mObj;
	}

	
	public void setMObj(EWBase gjObj) {
		this.mObj = gjObj;
	}

	
	public int getMyClassType() {
		return FSCommonEnums.CLASS_TYPE_NONE;
	}
}