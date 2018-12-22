
package com.kartiks.common;

public class SortField {
    public enum SORT_ORDER {
	ASC, DESC
    };

    String paramName;
    String fieldName;
    boolean isDefault = false;
    boolean isCaseSensitive = false;
    SORT_ORDER defaultOrder = SORT_ORDER.ASC;

    /**
     * @param string
     * @param string2
     */
    public SortField(String paramName, String fieldName) {
	this.paramName = paramName;
	this.fieldName = fieldName;
	isDefault = false;
    }
    
    public SortField(String paramName, String fieldName,boolean isCaseSensitive) {
    	this.paramName = paramName;
    	this.fieldName = fieldName;
    	isDefault = false;
    	this.isCaseSensitive = isCaseSensitive;
        }

    /**
     * @param paramName
     * @param fieldName
     * @param isDefault
     */
    public SortField(String paramName, String fieldName, boolean isDefault,
	    SORT_ORDER defaultOrder) {
	this.paramName = paramName;
	this.fieldName = fieldName;
	this.isDefault = isDefault;
	this.defaultOrder = defaultOrder;
    }
    //case sensitive
    public SortField(String paramName, String fieldName, boolean isDefault,
    	    SORT_ORDER defaultOrder,boolean isCaseSensitive) {
    	this.paramName = paramName;
    	this.fieldName = fieldName;
    	this.isDefault = isDefault;
    	this.defaultOrder = defaultOrder;
    	this.isCaseSensitive = isCaseSensitive;
        }

    /**
     * @return the paramName
     */
    public String getParamName() {
	return paramName;
    }

    /**
     * @param paramName
     *            the paramName to set
     */
    public void setParamName(String paramName) {
	this.paramName = paramName;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
	return fieldName;
    }

    /**
     * @param fieldName
     *            the fieldName to set
     */
    public void setFieldName(String fieldName) {
	this.fieldName = fieldName;
    }

    /**
     * @return the isDefault
     */
    public boolean isDefault() {
	return isDefault;
    }

    /**
     * @param isDefault
     *            the isDefault to set
     */
    public void setDefault(boolean isDefault) {
	this.isDefault = isDefault;
    }

    /**
     * @return the defaultOrder
     */
    public SORT_ORDER getDefaultOrder() {
        return defaultOrder;
    }

    /**
     * @param defaultOrder the defaultOrder to set
     */
    public void setDefaultOrder(SORT_ORDER defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public void setCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
	}

    
}
