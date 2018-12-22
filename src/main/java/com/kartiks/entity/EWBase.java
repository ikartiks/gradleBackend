package com.kartiks.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.kartiks.common.FSCommonEnums;

@MappedSuperclass
@XmlRootElement
public class EWBase extends Object implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false)
	protected Date createTime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	protected Date updateTime = new Date();
	
	public int getMyClassType() {
		return FSCommonEnums.CLASS_TYPE_NONE;
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

	

	@Override
	public String toString() {
		String str = "EWDBBase={";
		str += "createTime={" + createTime + "} ";
		str += "updateTime={" + updateTime + "} ";
		str += "}";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		EWBase other = (EWBase) obj;
		if ((this.createTime == null && other.createTime != null)
				|| (this.createTime != null && !this.createTime.equals(other.createTime))) {
			return false;
		}
		
		if ((this.updateTime == null && other.updateTime != null)
				|| (this.updateTime != null && !this.updateTime.equals(other.updateTime))) {
			return false;
		}
		
		return true;
	}
}
