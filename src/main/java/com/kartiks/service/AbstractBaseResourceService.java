
package com.kartiks.service;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.kartiks.biz.FSBizUtil;
import com.kartiks.common.MessageEnums;
import com.kartiks.common.RESTErrorUtil;
import com.kartiks.common.SearchCriteria;
import com.kartiks.common.SearchField;
import com.kartiks.common.SearchUtil;
import com.kartiks.common.SortField;
import com.kartiks.common.StringUtil;
import com.kartiks.db.BaseDao;
import com.kartiks.db.DaoManager;
import com.kartiks.db.view.VDataObject;
import com.kartiks.entity.EWBase;
import com.kartiks.view.VList;
import com.kartiks.view.VLong;
import com.kartiks.view.ViewBaseBean;


public abstract class AbstractBaseResourceService<T extends EWBase, V extends ViewBaseBean> {
	
	public static final Logger logger = Logger
			.getLogger(AbstractBaseResourceService.class);
	
	public static final int OPERATION_CREATE_CONTEXT = 1;
	public static final int OPERATION_UPDATE_CONTEXT = 2;
	
	static HashMap<Integer, AbstractBaseResourceService<?, ?>> serviceList = new HashMap<Integer, AbstractBaseResourceService<?, ?>>();
	static List<AbstractBaseResourceService<?, ?>> preServiceList = new ArrayList<AbstractBaseResourceService<?, ?>>();
	protected  Class<T> tEntityClass;
	protected  Class<V> tViewClass;

	protected String className;
	protected String viewClassName;
	protected final String countQueryStr;
	protected final String queryStr;
	protected final String distinctCountQueryStr;
	protected final String distinctQueryStr;

	protected static final HashMap<Class<?>, String> tEntityValueMap = new HashMap<Class<?>, String>();
	static {
		tEntityValueMap.put(EWBase.class, "Base");
	}
	
	public List<SortField> sortFields = new ArrayList<SortField>();
	public List<SearchField> searchFields = new ArrayList<SearchField>();

	BaseDao<T> entityDao;
	
	@Autowired
	DaoManager daoMgr;
	
	@Autowired
	FSBizUtil bizUtil;
	
	@Autowired
	StringUtil stringUtil;
	
	@Autowired
	protected SearchUtil searchUtil;

	@Autowired
	RESTErrorUtil restErrorUtil;
	
	/**
	 * constructor
	 */
	@SuppressWarnings("unchecked")
	public AbstractBaseResourceService() {
		@SuppressWarnings("rawtypes")
		Class klass = getClass();
		ParameterizedType genericSuperclass = (ParameterizedType) klass
				.getGenericSuperclass();
		TypeVariable<Class<?>> var[] = klass.getTypeParameters();
		
		if(genericSuperclass.getActualTypeArguments()[0] instanceof Class) {
			tEntityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
			tViewClass = (Class<V>) genericSuperclass.getActualTypeArguments()[1];
		} else if(var.length > 0){
			tEntityClass = (Class<T>) var[0].getBounds()[0];
			tViewClass = (Class<V>) var[1].getBounds()[0];
		} else {
			logger.fatal("Cannot find class for template", new Throwable());
		}
		className = tEntityClass.getName();
		viewClassName=tViewClass.getName();
		countQueryStr = "SELECT COUNT(*) FROM " + tEntityClass.getName()
				+ " obj ";
		queryStr = "SELECT obj FROM " + className + " obj ";

		distinctCountQueryStr = "SELECT COUNT(distinct obj.id) FROM "
				+ tEntityClass.getName() + " obj ";
		distinctQueryStr = "SELECT distinct obj FROM " + className + " obj ";

		registerService(this);
	}
	
	
	public static void registerService(
			AbstractBaseResourceService<?, ?> baseService) {
		preServiceList.add(baseService);
	}
	
	static public AbstractBaseResourceService<?, ?> getService(int classType) {
		AbstractBaseResourceService<?, ?> service = serviceList.get(classType);
		if (service == null) {
			for (AbstractBaseResourceService<?, ?> myService : preServiceList) {
				if (myService.getClassType() == classType) {
					serviceList.put(myService.getClassType(), myService);
					service = myService;
					break;
				}
			}
		}

		if (service == null) {
			logger.error("Service not found for classType=" + classType,
					new Throwable());
		}
		return service;
	}
	
	protected T createEntityObject() {
		try {
			return tEntityClass.newInstance();
		} catch (Throwable e) {
			logger.error("Error instantiating entity class. tEntityClass="
					+ tEntityClass.toString(), e);
		}
		return null;
	}

	protected V createViewObject() {
		try {
			return tViewClass.newInstance();
		} catch (Throwable e) {
			logger.error("Error instantiating view class. tViewClass="
					+ tViewClass.toString(), e);
		}
		return null;
	}

	protected int getClassType() {
		return bizUtil.getClassType(tEntityClass);
	}
	
	protected abstract void validateForCreate(V viewBaseBean);

	protected abstract void validateForUpdate(V viewBaseBean, T t);

	protected abstract T mapViewToEntityBean(V vObj, T mObj,int OPERATION_CONTEXT);

	protected abstract V mapEntityToViewBean(V vObj, T mObj);
	
	// ----------------------------------------------------------------------------------
	// Validation
	// ----------------------------------------------------------------------------------
	protected void validateGenericAttributes(V viewBaseBean) {
	}
	// ----------------------------------------------------------------------------------
	// mapping view bean attributes
	// ----------------------------------------------------------------------------------
	public V populateViewBean(T resource) {
		V viewBean = createViewObject();
		populateViewBean(resource, viewBean);
		mapEntityToViewBean(viewBean, resource);
		return viewBean;
	}
	
	protected V populateViewBean(T resource, V viewBean) {
		mapBaseAttributesToViewBean(resource, viewBean);
		// TODO:Bosco:Current:Open:Bosco: Need to set original and updated
		// content
		return viewBean;
	}
	
	protected void mapBaseAttributesToViewBean(T mObj, V vObj) {
		vObj.setMObj(mObj);
		if(vObj instanceof VDataObject){
			VDataObject vDataObj =(VDataObject) vObj;
			vDataObj.setCreateTime(mObj.getCreateTime());
			vDataObj.setUpdateTime(mObj.getUpdateTime());
			//vDataObj.setCreatedBy(resource.getCreatedBy());
			//vDataObj.setUpdatedBy(resource.getUpdatedBy());
		}
	}
	
	@SuppressWarnings("unchecked")
	protected BaseDao<T> getDao() {
		if (entityDao == null) {
			entityDao = (BaseDao<T>) daoMgr.getDaoForClassName(tEntityClass
					.getSimpleName());

		}
		return entityDao;
	}
	
	protected String getResourceName() {
		
		String resourceName = tEntityValueMap.get(tEntityClass);
		if(resourceName == null || resourceName.isEmpty()){
			resourceName = "Object";
		}
		return resourceName;
	}
	
	protected void mapBaseAttributesToEntityBean(T mObj, V vObj) {
		if(mObj instanceof EWBase){
			if (mObj.getCreateTime() == null) {
				mObj.setCreateTime(new Date());
			}
			mObj.setUpdateTime(new Date());
			//resource.setCreatedBy(ContextUtil.getCurrentUserId());
			//resource.setUpdatedBy(ContextUtil.getCurrentUserId());
		}
		

	}

	protected T populateEntityBeanForCreate(T t, V viewBaseBean) {
		mapBaseAttributesToEntityBean(t, viewBaseBean);
		return mapViewToEntityBean(viewBaseBean, t, OPERATION_CREATE_CONTEXT);
	}

	// ----------------------------------------------------------------------------------
	// Create Operation
	// ----------------------------------------------------------------------------------	

	protected T preCreate(V viewBaseBean) {
		validateGenericAttributes(viewBaseBean);
		validateForCreate(viewBaseBean);

		T t = createEntityObject();
		t = populateEntityBeanForCreate(t, viewBaseBean);
		return t;
	}
	
	public V createResource(V viewBaseBean) {
		T resource = preCreate(viewBaseBean);
		resource = getDao().create(resource);
		V view = postCreate(resource);
		return view;
	}

	protected V postCreate(T resource) {
		V view = populateViewBean(resource);
		return view;
	}
	
	
	// ----------------------------------------------------------------------------------
	// Read Operation
	// ----------------------------------------------------------------------------------

	protected T preRead(Long id) {
		return null;
	}

//	public V readResource(String gId) {
//		T resource = getDao().getByGUID(gId);
//		if (resource == null) {
//			// Returns code 400 with DATA_NOT_FOUND as the error message
//			throw restErrorUtil.createRESTException(getResourceName()
//					+ " not found", MessageEnums.DATA_NOT_FOUND, null, null,
//					"preRead: " + gId + " not found.");
//		}
//
//		V viewBean = readResource(resource);
//		return viewBean;
//
//	}
	
	public V readResource(Long id) {
		// T resource = preRead(id);

		T resource = getDao().getById(id);
		if (resource == null) {
			// Returns code 400 with DATA_NOT_FOUND as the error message
			throw restErrorUtil.createRESTException(getResourceName()
					+ " not found", MessageEnums.DATA_NOT_FOUND, id, null,
					"preRead: " + id + " not found.");
		}

		V viewBean = readResource(resource);
		return viewBean;
	}
	
	private V readResource(T resource) {
		V viewBean = postRead(resource);
		return viewBean;
	}
	
	protected V postRead(T resource) {
		V viewBean = populateViewBean(resource);
		return viewBean;
	}
	
	// ----------------------------------------------------------------------------------
	// Update Operation
	// ----------------------------------------------------------------------------------

	/**
	 * Populate Entity object from view object. Used in update operation
	 */
	protected T populateEntityBeanForUpdate(T t, V viewBaseBean) {
		mapBaseAttributesToEntityBean(t, viewBaseBean);
		return mapViewToEntityBean(viewBaseBean, t, OPERATION_UPDATE_CONTEXT);
	}
	
	protected T preUpdate(V viewBaseBean) {
		if(viewBaseBean instanceof VDataObject){
			VDataObject vDataObj =(VDataObject) viewBaseBean;
			T resource = getDao().getById(vDataObj.getId());
			if (resource == null) {
				// Returns code 400 with DATA_NOT_FOUND as the error message
				throw restErrorUtil.createRESTException(getResourceName()
						+ " not found", MessageEnums.DATA_NOT_FOUND,
						vDataObj.getId(), null, "preUpdate: id not found.");
			}
			validateForUpdate(viewBaseBean, resource);
			
			return populateEntityBeanForUpdate(resource, viewBaseBean);
		}else{
			throw restErrorUtil.createRESTException(getResourceName()
					+ " not found", MessageEnums.DATA_NOT_FOUND,
					0L, null, "update not supperted for this object");
		}
	}

	public V updateResource(V viewBaseBean) {
		T resource = preUpdate(viewBaseBean);
		resource = getDao().update(resource);
		V viewBean = postUpdate(resource);
		return viewBean;
	}

	protected V postUpdate(T resource) {
		V view = populateViewBean(resource);
		return view;
	}
	
	// ----------------------------------------------------------------------------------
	// Delete Operation
	// ----------------------------------------------------------------------------------
	protected T preDelete(Long id) {
		T resource = getDao().getById(id);
		if (resource == null) {
			// Return without error
			logger.info("Delete ignored for non-existent " + getResourceName()
					+ " id=" + id);
		}
		return resource;
	}
	
	public boolean deleteResource(Long id) {
		boolean result = false;
		T resource = preDelete(id);
		if (resource == null) {
			throw restErrorUtil.createRESTException(getResourceName()
					+ " not found", MessageEnums.DATA_NOT_FOUND, id, null,
					getResourceName() + ":" + id);
		}
		try {
			result = getDao().remove(resource);
		} catch (Exception e) {
			logger.error("Error deleting " + getResourceName() + ". Id=" + id,
					e);

			throw restErrorUtil.createRESTException(getResourceName()
					+ " can't be deleted",
					MessageEnums.OPER_NOT_ALLOWED_FOR_STATE, id, null, "" + id
							+ ", error=" + e.getMessage());
		}
		postDelete(resource);
		return result;
	}
	
	protected void postDelete(T resource) {

	}
	
	//Search support
	protected Query createQuery(String searchString, String sortString,
			SearchCriteria searchCriteria, List<SearchField> searchFieldList,
			boolean isCountQuery) {
		Query query = searchUtil.createSearchQuery(searchString, sortString,
				searchCriteria, searchFieldList, isCountQuery);
		return query;
	}

	public VLong getSearchCount(SearchCriteria searchCriteria,
			List<SearchField> searchFieldList) {
		long count = getCountForSearchQuery(searchCriteria, searchFieldList);

		VLong vLong = new VLong();
		vLong.setValue(count);
		return vLong;
	}

	protected long getCountForSearchQuery(SearchCriteria searchCriteria,
			List<SearchField> searchFieldList) {

		String q = countQueryStr;
		// Get total count of the rows which meet the search criteria
		if( searchCriteria.isDistinct()) {
			q = distinctCountQueryStr;
		}
		
		// Get total count of the rows which meet the search criteria
		Query query = createQuery(q, null, searchCriteria,
				searchFieldList, true);

		// Make the database call to get the total count
		Long count = getDao().executeCountQueryInSecurityContext(tEntityClass,
				query);
		if (count == null) {
			// If no data that meets the criteria, return 0
			return 0;
		}
		return count.longValue();
	}
	
	protected List<T> searchResources(SearchCriteria searchCriteria,
			List<SearchField> searchFields, List<SortField> sortFields,
			VList vList) {
		
		// Get total count of the rows which meet the search criteria
		long count = -1;
		if (searchCriteria.isGetCount()) {
			count = getCountForSearchQuery(searchCriteria, searchFields);
			if (count == 0) {
				return Collections.emptyList();
			}
		}
		// construct the sort clause
		String sortClause = searchUtil.constructSortClause(searchCriteria,
				sortFields);

		String q=queryStr;
		if(searchCriteria.isDistinct()){
			q=distinctQueryStr;
		}
		// construct the query object for retrieving the data
		Query query = createQuery(q, sortClause, searchCriteria,
				searchFields, false);

		List<T> resultList = getDao().executeQueryInSecurityContext(
				tEntityClass, query);

		if (vList != null) {
			// Set the meta values for the query result
			//TODO kartik 
			//vList.setPageSize(query.getMaxResults());
			vList.setSortBy(searchCriteria.getSortBy());
			//TODO: For compilation
			vList.setSortType(searchCriteria.getSortType());
			//vList.setStartIndex(query.getFirstResult());
			vList.setTotalCount(count);
			vList.setResultSize(resultList.size());
		}
		return resultList;
	}

}
