package com.kartiks.db.view;

import java.io.Serializable;

public class VUser extends VDataObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3383408527544239594L;

	String email;
	
	String password;
	
	boolean enabled;
	
	String name;
	
	String number;
	
	String company;
	
	Long maxFree;
	
	String role;
	
	Long createdBy;
	
	String sessionId;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getMaxFree() {
		return maxFree;
	}

	public void setMaxFree(Long maxFree) {
		this.maxFree = maxFree;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
