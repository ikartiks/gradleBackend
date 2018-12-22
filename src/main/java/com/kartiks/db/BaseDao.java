package com.kartiks.db;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
//import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

public abstract class BaseDao<T> {
	
	static final Logger logger = Logger.getLogger(BaseDao.class);
	
	protected DaoManager daoManager;

	EntityManager em;

	protected Class<T> tClass;

	public BaseDao(DaoManagerBase daoManager) {
		this.daoManager = (DaoManager) daoManager;
		this.init(daoManager.getEntityManager());
	}
	
	public EntityManager getEm() {
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	@SuppressWarnings("unchecked")
	private void init(EntityManager em) {
		this.em = em;

		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();

		Type type = genericSuperclass.getActualTypeArguments()[0];

		if (type instanceof ParameterizedType) {
			this.tClass = (Class<T>) ((ParameterizedType) type).getRawType();
		} else {
			this.tClass = (Class<T>) type;
		}
	}

	public T create(T obj) {
		T ret = null;


		// Set<?> attrs = em
		// .getMetamodel().entity(obj.getClass()).getAttributes();

		em.persist(obj);
		em.flush();

		ret = obj;
		
		return ret;
	}
	
	public T getById(Long id) {
		if (id == null) {
			return null;
		}
		T ret = null;
		try {
			ret = em.find(tClass, id);
			return ret;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public T update(T obj) {
		em.merge(obj);
		em.flush();
		return obj;
	}
	
	public boolean remove(Long id) {
		return remove(getById(id));
	}

	public boolean remove(T obj) {
		if (obj == null) {
			return true;
		}

		boolean ret = false;

		em.remove(obj);
		em.flush();

		ret = true;
		return ret;
	}
	
	public synchronized Connection getConnection() throws SQLException{
		//TODO kartik if using old jar chnage in SearchUtil.java also & abstractBaseResourceService.java
		Session session = em.unwrap(Session.class);
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
		ConnectionProvider cp = sfi.getConnectionProvider();
		Connection connection = cp.getConnection();
		return connection;
	}
	
	//Query support
	public boolean enableVisiblityFilters(Class<?> entityClass) {
		return enableVisiblityFilters(entityClass, false);
	}

	public boolean enableVisiblityFilters(Class<?> entityClass,
			boolean userPrefFilter) {
		//TODO: Hard coded false
		return false;
	}
	public void disableVisiblityFilters(Class<?> entityClass) {
		//TODO: No implementation required
	}
	
	public List<T> executeQueryInSecurityContext(Class<T> clazz, Query query) {
		return executeQueryInSecurityContext(clazz, query, true);
	}

	@SuppressWarnings("unchecked")
	public List<T> executeQueryInSecurityContext(Class<T> clazz, Query query,
			boolean userPrefFilter) {
		boolean filterEnabled = false;
		List<T> rtrnList = null;
		try {
			filterEnabled = enableVisiblityFilters(clazz, userPrefFilter);

			rtrnList = query.getResultList();
		} finally {
			if (filterEnabled) {
				disableVisiblityFilters(clazz);
			}

		}

		return rtrnList;
	}

	public T executeSingleResultQueryInSecurityContext(Class<T> clazz,
			Query query) {
		return executeSingleResultQueryInSecurityContext(clazz, query, true);
	}

	public Long executeCountQueryInSecurityContext(Class<T> clazz, Query query) {
		return executeCountQueryInSecurityContext(clazz, query, true);
	}

	/**
	 * @param clazz
	 * @param query
	 * @param b
	 * @return
	 */
	private Long executeCountQueryInSecurityContext(Class<T> clazz,
			Query query, boolean userPrefFilter) {
		boolean filterEnabled = false;
		Long rtrnObj = null;
		try {
			filterEnabled = enableVisiblityFilters(clazz, userPrefFilter);
			rtrnObj = (Long) query.getSingleResult();
		} finally {
			if (filterEnabled) {
				disableVisiblityFilters(clazz);
			}
		}

		return rtrnObj;
	}

	public T executeSingleResultQueryInSecurityContext(Class<T> clazz,
			Query query, boolean userPrefFilter) {
		boolean filterEnabled = false;
		T rtrnObj = null;
		try {
			filterEnabled = enableVisiblityFilters(clazz, userPrefFilter);
			rtrnObj = (T) query.getSingleResult();
		} catch (NoResultException ex) {
			// Object not found, so will return null
		} finally {
			if (filterEnabled) {
				disableVisiblityFilters(clazz);
			}

		}

		return rtrnObj;
	}
}
