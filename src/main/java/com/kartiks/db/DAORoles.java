package com.kartiks.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.NoResultException;

import com.kartiks.db.view.VUser;
import com.kartiks.entity.EWRoles;

public class DAORoles extends BaseDao<EWRoles> {

	public DAORoles(DaoManagerBase daoManager) {
		super(daoManager);
	}
	
	public String getRoles(Long id){
		
		try{
			String role=(String)getEm()
					.createNativeQuery("SELECT role FROM `user_authorization` WHERE user_id=?1")
					.setParameter("1", id)
					.getSingleResult();
			return role;
		}catch(NoResultException e){
			return null;
		}
	}
	
	public void insertRoles(VUser user){
			
		Date dt = new java.util.Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		getEm().createNativeQuery("INSERT INTO user_authorization (user_id,role,createdAt,updatedAt) "
				+ "VALUES (?,?,?,?);")
		.setParameter(1, user.getId())
		.setParameter(2, user.getRole())
		.setParameter(3, currentTime)
		.setParameter(4, currentTime)
		.executeUpdate();
		
	}
}
