
package com.kartiks.common;

import java.io.Serializable;

import com.kartiks.entity.EWUser;

public class UserSessionBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected EWUser ewUser;

	public EWUser getEWUser() {
		return ewUser;
	}

	public void setEWUser(EWUser ewUser) {
		this.ewUser = ewUser;
	}
	
	public String getLoginId() {
		if (ewUser != null) {
			return ewUser.getEmail();
		}
		return null;
	}
}
