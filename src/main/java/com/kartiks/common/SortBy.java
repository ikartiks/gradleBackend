/*
 * Copyright (c) 2015 EPMWare Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * EPMWare Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with EPMWare Inc.
 */

/**************************************************************************
--
-- Purpose: Sorting information of a entity field is maintained here e.g ASC,DESC.
--
--
-- Author: Dharmesh Makwana
--
-- Change History
--
-- Modified by       Date         Notes
 =========================================
-- Dharmesh Makwana  27-Feb-2014  Initial Version 
--
**************************************************************************/
package com.kartiks.common;

public class SortBy {
	
	
	public SortBy(){
		sortType=null;
		fieldName=null;
	}	
	private  String fieldName;
	private String sortType;
	
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	

}
