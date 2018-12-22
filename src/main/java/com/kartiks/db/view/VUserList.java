package com.kartiks.db.view;

import java.util.ArrayList;
import java.util.List;

import com.kartiks.view.VList;

public class VUserList extends VList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8581004725310152621L;

	List<VUser> list = new ArrayList<VUser>();

	@Override
	public int getListSize() {
		return list.size();
	}

	@Override
	public List<VUser> getList() {
		return list;
	}

}
