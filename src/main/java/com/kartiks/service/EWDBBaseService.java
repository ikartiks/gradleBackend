
package com.kartiks.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kartiks.db.view.VDataObject;
import com.kartiks.entity.EWBase;

@Service
@Scope("singleton")
public class EWDBBaseService <T extends EWBase, V extends VDataObject> extends EWDBBaseServiceBase<T, V> {
	
	@Override
	protected void validateForCreate(V viewBaseBean) {
		
	}

	@Override
	protected void validateForUpdate(V viewBaseBean, T t) {
		
	}
}
