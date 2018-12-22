
package com.kartiks.db.view;

import java.io.Serializable;
import java.util.Date;

import com.kartiks.common.FSConstants;
import com.kartiks.view.ViewBaseBean;

public class VDataObject extends ViewBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	
	protected Date createTime;
	
	protected Date updateTime;
	
	@Override
	public int getMyClassType() {
		
		return FSConstants.CLASS_TYPE_NONE;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
