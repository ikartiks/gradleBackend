
package com.kartiks.db;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;

import com.kartiks.common.RESTErrorUtil;
import com.kartiks.entity.EWUser;

public class DAOUser extends BaseDao<EWUser>{

	@Autowired
	RESTErrorUtil restErrorUtil;
	
	public DAOUser(DaoManagerBase daoManager) {
		super(daoManager);
	}
	
	public EWUser authenticate(String email,String password){
		
		try {
			return (EWUser) getEm().createNamedQuery("EWUser.authenticate")
					.setParameter("email", email)
					.setParameter("password",password)
					.getSingleResult();
		}catch (NoResultException ex) {
			return null;
		}
	}
	
	public EWUser findByUserName(String email){
		
		try {
			return (EWUser) getEm().createNamedQuery("EWUser.findByUserName")
					.setParameter("email", email)
					.getSingleResult();
		}catch (NoResultException ex) {
			return null;
		}
	}
	
	
	
	
}
