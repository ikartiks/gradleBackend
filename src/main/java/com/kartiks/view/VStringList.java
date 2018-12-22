package com.kartiks.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VStringList extends VList implements Serializable{
	private static final long serialVersionUID = 1L;
    List<VString> vStrings = new ArrayList<VString>();

    public VStringList() {
	super();
    }

    public VStringList(List<VString> objList) {
	super(objList);
	this.vStrings = objList;
    }

    /**
     * @return the vUserProfiles
     */
    public List<VString> getVStrings() {
	return vStrings;
    }

    /**
     * @param vUserProfiles
     *            the vUserProfiles to set
     */
    public void setVStrings(List<VString> vUserProfiles) {
	this.vStrings = vUserProfiles;
    }

    @Override
    public int getListSize() {
	if (vStrings != null) {
	    return vStrings.size();
	}
	return 0;
    }

    @Override
    public List<VString> getList() {
	return vStrings;
    }
}
