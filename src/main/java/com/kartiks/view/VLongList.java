package com.kartiks.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VLongList extends VList {
	private static final long serialVersionUID = 1L;
    List<VLong> vLongs = new ArrayList<VLong>();

    public VLongList() {
	super();
    }

    public VLongList(List<VLong> objList) {
	super(objList);
	this.vLongs = objList;
    }

    /**
     * @return the vLongs
     */
    public List<VLong> getVLongs() {
	return vLongs;
    }

    /**
     * @param vLongs
     *            the vLongs to set
     */
    public void setVLongs(List<VLong> vLongs) {
	this.vLongs = vLongs;
    }

    @Override
    public int getListSize() {
	if (vLongs != null) {
	    return vLongs.size();
	}
	return 0;
    }

    @Override
    public List<VLong> getList() {
	return vLongs;
    }

}
