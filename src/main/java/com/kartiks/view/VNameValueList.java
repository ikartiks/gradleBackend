
package com.kartiks.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VNameValueList extends VList {
	private static final long serialVersionUID = 1L;
    List<VNameValue> vNameValues = new ArrayList<VNameValue>();

    public VNameValueList() {
	super();
    }

    public VNameValueList(List<VNameValue> objList) {
	super(objList);
	this.vNameValues = objList;
    }

    /**
     * @return the vNameValues
     */
    public List<VNameValue> getVNameValues() {
	return vNameValues;
    }

    /**
     * @param vNameValues
     *            the vNameValues to set
     */
    public void setVNameValues(List<VNameValue> vNameValues) {
	this.vNameValues = vNameValues;
    }

    @Override
    public int getListSize() {
	if (vNameValues != null) {
	    return vNameValues.size();
	}
	return 0;
    }

    @Override
    public List<VNameValue> getList() {
	return vNameValues;
    }

}
