
package com.kartiks.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kartiks.common.SearchCriteria;
import com.kartiks.common.SearchField;
import com.kartiks.common.SearchField.DATA_TYPE;
import com.kartiks.common.SearchField.SEARCH_TYPE;
import com.kartiks.db.DAORoles;
import com.kartiks.db.DAOUser;
import com.kartiks.db.DaoManager;
import com.kartiks.db.view.VUser;
import com.kartiks.db.view.VUserList;
import com.kartiks.entity.EWUser;
import com.kartiks.view.VString;

@Service
@Scope("singleton")
public class UserService extends EWDBBaseService<EWUser, VUser>{
	
	@Autowired
	DaoManager daoManager;
	
	public UserService(){
		
		searchFields.add(new SearchField("maxFree", "obj.maxFree", DATA_TYPE.INTEGER, SEARCH_TYPE.FULL));
		searchFields.add(new SearchField("company", "obj.company", DATA_TYPE.STRING, SEARCH_TYPE.FULL));
		searchFields.add(new SearchField("email", "obj.email", DATA_TYPE.STRING, SEARCH_TYPE.FULL));
		searchFields.add(new SearchField("name", "obj.name", DATA_TYPE.STRING, SEARCH_TYPE.PARTIAL));
		searchFields.add(new SearchField("number", "obj.number", DATA_TYPE.STRING, SEARCH_TYPE.FULL));
	}
	
	@Override
	protected EWUser mapViewToEntityBean(VUser vObj, EWUser mObj, int OPERATION_CONTEXT) {
		super.mapBaseAttributesToEntityBean(mObj, vObj);
		
		mObj.setEmail(vObj.getEmail());
		mObj.setEnabled(vObj.isEnabled());
		mObj.setId(vObj.getId());
		mObj.setName(vObj.getName());
		mObj.setNumber(vObj.getNumber());
		mObj.setPassword(vObj.getPassword());
		mObj.setMaxFree(vObj.getMaxFree());
		mObj.setCompany(vObj.getCompany());
		
		return mObj;
	}
	
	@Override
	protected VUser mapEntityToViewBean(VUser vObj, EWUser mObj) {
		super.mapBaseAttributesToViewBean(mObj, vObj);
		
		vObj.setEmail(mObj.getEmail());
		vObj.setEnabled(mObj.isEnabled());
		vObj.setId(mObj.getId());
		vObj.setName(mObj.getName());
		vObj.setNumber(mObj.getNumber());
		vObj.setPassword(mObj.getPassword());
		vObj.setMaxFree(mObj.getMaxFree());
		vObj.setCompany(mObj.getCompany());
		
		return vObj;
	}
	
	@Override
	protected void validateForCreate(VUser vUser) {
		EWUser ewUser=daoMgr.getEWUser().findByUserName(vUser.getEmail());
		if(ewUser!=null)
			throw restErrorUtil.createKartiksRESTException(new VString("username already exists"));
	}

	@Override
	protected void validateForUpdate(VUser vUser, EWUser t) {
		
	}
	
	public VUser findUserByName(String email) {
		DAOUser daoUser=daoManager.getEWUser();
		DAORoles daoRoles=daoManager.getRolesDAO();
		EWUser ewUser = daoUser.findByUserName(email);
		if(ewUser!=null){
			VUser user=new VUser();
			mapEntityToViewBean(user, ewUser);
			user.setRole(daoRoles.getRoles(user.getId()));
			return user;
		}
			
		return null;
	}

	public VUserList searchUsers(SearchCriteria searchCriteria) {
		
		VUserList vList =new VUserList();
		List<VUser> vObjs=vList.getList();
		List<EWUser> mObjs = searchResources(searchCriteria, searchFields, sortFields, vList);
		for (Iterator<EWUser> iterator = mObjs.iterator(); iterator.hasNext();) {
			EWUser mObj =  iterator.next();
			vObjs.add(mapEntityToViewBean(new VUser(), mObj));
		}
		return vList;
	}
}
