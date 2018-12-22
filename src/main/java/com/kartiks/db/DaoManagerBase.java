package com.kartiks.db;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.kartiks.common.FSConstants;
import com.kartiks.common.RESTErrorUtil;
import com.kartiks.entity.EWRoles;
import com.kartiks.entity.EWUser;

public abstract class DaoManagerBase {
	final static Logger logger = Logger.getLogger(DaoManagerBase.class);

	@Autowired
	protected RESTErrorUtil restErrorUtil;

	abstract public EntityManager getEntityManager();

	protected String[] moderationClassNames = new String[0];
	protected int[] moderationClassTypes = new int[0];

	public DaoManagerBase() {

		moderationClassNames = new String[] {};
		moderationClassTypes = new int[] {};
	}

	public String[] getModerationClassNames() {
		return moderationClassNames;
	}

	public int[] getModerationClassTypes() {
		return moderationClassTypes;
	}

	public BaseDao<?> getDaoForClassType(int classType) {

		// if (classType == FSConstants.CLASS_TYPE_DBBASE) {
		// return getEWDBBase();
		// }

		if (classType == FSConstants.CLASS_TYPE_EWUser) {
			return getEWUser();
		}

		if (classType == FSConstants.CLASS_TYPE_EWRoles) {
			return getRolesDAO();
		}

		logger.error("No DaoManager found for classType=" + classType, new Throwable());
		return null;
	}

	public BaseDao<?> getDaoForClassName(String className) {

		if (className.equals(EWUser.class.getSimpleName())) {
			return getEWUser();
		}

		if (className.equals(EWRoles.class.getSimpleName())) {
			return getRolesDAO();
		}

		logger.error("No DaoManager found for className=" + className, new Throwable());
		return null;
	}

	public DAOUser getEWUser() {

		return new DAOUser(this);
	}

	public DAORoles getRolesDAO() {
		return new DAORoles(this);
	}

}
