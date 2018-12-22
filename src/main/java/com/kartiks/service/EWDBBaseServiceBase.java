package com.kartiks.service;

import java.util.ArrayList;
import java.util.List;

import com.kartiks.common.SearchCriteria;
import com.kartiks.db.view.VDataObject;
import com.kartiks.entity.EWBase;
import com.kartiks.view.VEWDBBaseList;

public abstract class EWDBBaseServiceBase<T extends EWBase, V extends VDataObject> extends AbstractBaseResourceService<T, V> {
	public static final String NAME = "EWBase";

	public EWDBBaseServiceBase() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EWBase mapViewToEntityBean(VDataObject vObj, EWBase mObj, int OPERATION_CONTEXT) {
		
		if(vObj.getCreateTime()!=null && vObj.getUpdateTime()!=null){
			mObj.setCreateTime(vObj.getCreateTime());
			mObj.setUpdateTime(vObj.getUpdateTime());
		}
		
		return mObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected VDataObject mapEntityToViewBean(VDataObject vObj, EWBase mObj) {
		
		
		vObj.setCreateTime(mObj.getCreateTime());
		vObj.setUpdateTime(mObj.getUpdateTime());
		return vObj;
	}

	/**
	 * @param searchCriteria
	 * @return
	 */
	public VEWDBBaseList searchEWDBBases(SearchCriteria searchCriteria) {
		VEWDBBaseList collection = new VEWDBBaseList();
		List<VDataObject> eWDBBaseList = new ArrayList<VDataObject>();

		@SuppressWarnings("unchecked")
		List<EWBase> resultList = (List<EWBase>) searchResources(searchCriteria, searchFields, sortFields, collection);

		// Iterate over the result list and create the return list
		for (EWBase gjEWDBBase : resultList) {
			@SuppressWarnings("unchecked")
			VDataObject vEWDBBase = populateViewBean((T) gjEWDBBase);
			eWDBBaseList.add(vEWDBBase);
		}

		collection.setVEWDBBases(eWDBBaseList);
		return collection;
	}

}
