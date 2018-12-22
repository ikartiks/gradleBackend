
package com.kartiks.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SearchCriteria {

	int startIndex = 0;
	int maxRows = Integer.MAX_VALUE;
	String sortBy = null;
	String sortType = null;

	boolean getCount = true;
	Number ownerId = null;
	boolean familyOnly = false;
	boolean getChildren = false;
	boolean isDistinct = false;
	HashMap<String, Object> paramList = new HashMap<String, Object>();
	Set<String> nullParamList = new HashSet<String>();
	Set<String> notNullParamList = new HashSet<String>();

	// List<SearchGroup> searchGroups = new ArrayList<SearchGroup>();

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * @param maxRows
	 *            the maxRows to set
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}


	/**
	 * @return the sortType
	 */

	public String getSortType() {
		return sortType;
	}

	/**
	 * @param sortType
	 *            the sortType to set
	 */
	/*
	 * public void setSortType(String sortType) { this.sortType = sortType; }
	 */
	public boolean isGetCount() {
		return getCount;
	}

	public void setGetCount(boolean getCount) {
		this.getCount = getCount;
	}

	public Number getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Number ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isFamilyOnly() {
		return familyOnly;
	}

	public void setFamilyOnly(Boolean familyOnly) {
		if (familyOnly == null) {
			this.familyOnly = false;
		} else {
			this.familyOnly = familyOnly.booleanValue();
		}
	}

	public boolean isGetChildren() {
		return getChildren;
	}

	public void setGetChildren(boolean getChildren) {
		this.getChildren = getChildren;
	}

	/**
	 * @return the paramList
	 */
	public HashMap<String, Object> getParamList() {
		return paramList;
	}

	/**
	 * @param paramList
	 *            the paramList to set
	 */
	public void setParamList(HashMap<String, Object> paramList) {
		this.paramList = paramList;
	}

	/**
	 * @param string
	 * @param caId
	 */
	public void addParam(String name, Object value) {
		paramList.put(name, value);
	}

	public void setNullParam(String name) {
		nullParamList.add(name);
	}

	public void setNotNullParam(String name) {
		notNullParamList.add(name);
	}

	public Object getParamValue(String name) {
		return paramList.get(name);
	}

	/**
	 * @param string
	 * @param caId
	 */
	public Object removeParam(String name) {
		return paramList.remove(name);
	}

	/**
	 * @return the nullParamList
	 */
	public Set<String> getNullParamList() {
		return nullParamList;
	}

	/**
	 * @return the notNullParamList
	 */
	public Set<String> getNotNullParamList() {
		return notNullParamList;
	}

	/**
	 * @return the searchGroups
	 */
	// public List<SearchGroup> getSearchGroups() {
	// return searchGroups;
	// }

	/**
	 * @param searchGroups
	 *            the searchGroups to set
	 */
	// public void setSearchGroups(List<SearchGroup> searchGroups) {
	// this.searchGroups = searchGroups;
	// }
	
	
	/**
	 * @return the isDistinct
	 */
	public boolean isDistinct() {
		return isDistinct;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**
	 * @param isDistinct
	 *            the isDistinct to set
	 */
	public void setDistinct(boolean isDistinct) {
		this.isDistinct = isDistinct;
	}

}
