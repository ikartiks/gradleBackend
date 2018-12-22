package com.kartiks.view;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class VList extends ViewBaseBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Start index for the result
	 */
	protected int startIndex;
	/**
	 * Page size used for the result
	 */
	protected int pageSize;
	/**
	 * Total records in the database for the given search conditions
	 */
	protected long totalCount;
	/**
	 * Number of rows returned for the search condition
	 */
	protected int resultSize;
	/**
	 * // * Sort type. Either desc or asc //
	 */
	protected String sortType;
	// /**
	// * Comma seperated list of the fields for sorting
	// */
	protected String sortBy;

	protected long queryTimeMS = System.currentTimeMillis();

	/**
	 * Default constructor. This will set all the attributes to default value.
	 */
	public VList() {
	}

	/**
	 * Initialize with existing list
	 * 
	 * @param size
	 */
	public VList(@SuppressWarnings("rawtypes") List objectList) {
		int size = 0;
		if (objectList != null) {
			size = objectList.size();
		}

		startIndex = 0;
		pageSize = size;
		totalCount = size;
		resultSize = size;
		sortType = null;
		sortBy = null;
	}

	abstract public int getListSize();

	/*@JsonIgnore*/
	abstract public List<?> getList();

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public int getResultSize() {
		return getListSize();
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortType() {
		return sortType;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public long getQueryTimeMS() {
		return queryTimeMS;
	}

	public void setQueryTimeMS(long queryTimeMS) {
		this.queryTimeMS = queryTimeMS;
	}

}
