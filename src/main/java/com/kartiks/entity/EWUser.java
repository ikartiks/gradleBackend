package com.kartiks.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kartiks.common.FSConstants;

@Entity
@Table(name = "user_authentication")
public class EWUser extends EWBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1935742710684615765L;

	@Override
	public int getMyClassType() {
		return FSConstants.CLASS_TYPE_EWUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	Long id;

//	@Column(name = "user_type", nullable = false)
//	@Enumerated(EnumType.STRING)
//	Role role;
	@Column(name = "username", nullable = false)
	String email;

	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "enabled", nullable = false)
	boolean enabled;

	@Column(name = "name", nullable = false, precision = 255)
	String name;
	
	@Column(name = "company", nullable = false, precision = 255)
	String company;

	@Column(name = "number", nullable = false, precision = 13)
	String number;
	
	//-1 or greater
	//-1 indicates not a manager
	//0 or greater implies he is
	@Column(name = "maxFree", nullable = false)
	Long maxFree;
	

	public EWUser() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public Long getMaxFree() {
		return maxFree;
	}
	
	public void setMaxFree(Long maxFree) {
		this.maxFree = maxFree;
	}

}
