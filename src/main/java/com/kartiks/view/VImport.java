
package com.kartiks.view;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VImport extends ViewBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected Long taskId;
	
	protected String status;
	
	protected String message;
	
	protected Long percentCompleted;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getPercentCompleted() {
		return percentCompleted;
	}

	public void setPercentCompleted(Long percentCompleted) {
		this.percentCompleted = percentCompleted;
	}
	
	
	
	
}
