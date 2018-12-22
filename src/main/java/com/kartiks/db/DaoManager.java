package com.kartiks.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class DaoManager extends DaoManagerBase {

	@PersistenceContext
	EntityManager em;

	public DaoManager() {
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	
}
