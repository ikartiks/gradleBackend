package com.kartiks.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kartiks.db.view.VDataObject;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VEWDBBaseList extends VList {
	private static final long serialVersionUID = 1L;
	List<VDataObject> vDataObjs = new ArrayList<VDataObject>();

	public VEWDBBaseList() {
		super();
	}

	public VEWDBBaseList(List<VDataObject> objList) {
		super(objList);
		this.vDataObjs = objList;
	}

	/**
	 * @return the vEWDBBases
	 */
	public List<VDataObject> getVEWDBBases() {
		return vDataObjs;
	}

	/**
	 * @param eWDBBaseList
	 *            the vEWDBBases to set
	 */
	public void setVEWDBBases(List<VDataObject> eWDBBaseList) {
		this.vDataObjs = eWDBBaseList;
	}

	@Override
	public int getListSize() {
		if (vDataObjs != null) {
			return vDataObjs.size();
		}
		return 0;
	}

	@Override
	public List<VDataObject> getList() {
		return vDataObjs;
	}

}
