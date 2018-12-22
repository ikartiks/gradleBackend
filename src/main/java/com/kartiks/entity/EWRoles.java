package com.kartiks.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.kartiks.common.FSConstants;

@Entity
@Table(name="user_authorization")
public class EWRoles extends EWBase{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4386909352666868075L;

	@Id
	@Column(name="user_id")
	Long userId;
	
	@Id
	@Column(name="role")
	String role;
	
	@Override
	public int getMyClassType() {
		// TODO Auto-generated method stub
		return FSConstants.CLASS_TYPE_EWRoles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
