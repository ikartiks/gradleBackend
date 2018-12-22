
package com.kartiks.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kartiks.db.BaseDao;
import com.kartiks.db.DaoManager;
import com.kartiks.view.VList;

/**
 * Contains the utility methods for searching
 * 
 * @author bosco
 * 
 */
@Component
public class SearchUtil {
	final static Logger logger = Logger.getLogger(SearchUtil.class);

	@Autowired
	DaoManager daoManager;

	@Autowired
	RESTErrorUtil restErrorUtil;
	
	@Autowired
	StringUtil stringUtil;

	int minInListLength = 20;
	String defaultDateFormat="MM/dd/yyyy";

	public SearchUtil() {
		//minInListLength = PropertiesUtil.getIntProperty("aka.mystudy.db.min_inlist", minInListLength);
		//defaultDateFormat = PropertiesUtil.getProperty("aka.mystudy.ui.defaultDateformat", defaultDateFormat);
	}

	@Deprecated
	public SearchCriteria extractCommonCriterias(HttpServletRequest request,
			String[] approvedSortByParams) {
		SearchCriteria searchCriteria = new SearchCriteria();
/**
		int startIndex = restErrorUtil.parseInt(
				request.getParameter("startIndex"), 0,
				"Invalid value for parameter startIndex",
				MessageEnums.INVALID_INPUT_DATA, null, "startIndex");
		searchCriteria.setStartIndex(startIndex);

		int pageSize = restErrorUtil.parseInt(request.getParameter("pageSize"),
				configUtil.getDefaultMaxRows(),
				"Invalid value for parameter pageSize",
				MessageEnums.INVALID_INPUT_DATA, null, "pageSize");
		searchCriteria.setMaxRows(pageSize);

		String sortBy = restErrorUtil.validateString(
				request.getParameter("sortBy"), StringUtil.VALIDATION_ALPHA,
				"Invalid value for parameter sortBy",
				MessageEnums.INVALID_INPUT_DATA, null, "sortBy");

		boolean sortSet = false;
		for (int i = 0; approvedSortByParams != null
				&& i < approvedSortByParams.length; i++) {

			if (approvedSortByParams[i].equalsIgnoreCase(sortBy)) {
				searchCriteria.setSortBy(approvedSortByParams[i]);
				String sortType = restErrorUtil.validateString(
						request.getParameter("sortType"),
						StringUtil.VALIDATION_ALPHA,
						"Invalid value for parameter sortType",
						MessageEnums.INVALID_INPUT_DATA, null, "sortType");
				searchCriteria.setSortType(sortType);
				sortSet = true;
				break;
			}
		}
		if (!sortSet && !stringUtil.isEmpty(sortBy)) {
			logger.info("Invalid or unsupported sortBy field passed. sortBy="
					+ sortBy, new Throwable());
		}
*/
		
//		for (int i = 0; approvedSortByParams != null
//				&& i < approvedSortByParams.length; i++) {
//
//			if (approvedSortByParams[i].equalsIgnoreCase(sortBy)) {
//				searchCriteria.setSortBy(approvedSortByParams[i]);
//				String sortType = restErrorUtil.validateString(
//						request.getParameter("sortType"),
//						StringUtil.VALIDATION_ALPHA,
//						"Invalid value for parameter sortType",
//						MessageEnums.INVALID_INPUT_DATA, null, "sortType");
//				searchCriteria.setSortType(sortType);
//				sortSet = true;
//				break;
//			}
//		}
//		if (!sortSet && !stringUtil.isEmpty(sortBy)) {
//			logger.info("Invalid or unsupported sortBy field passed. sortBy="
//					+ sortBy, new Throwable());
//		}
		
		return searchCriteria;
	}

	public SearchCriteria setCommonCriterias(int startIndex, int pageSize,
			boolean getCount, Map<String, Object> paramList,
			List<SortField> sortFields) {
		if (paramList == null) {
			paramList = new HashMap<String, Object>();
		}
		SearchCriteria searchCriteria = new SearchCriteria();

		searchCriteria.setStartIndex(startIndex);
		searchCriteria.setMaxRows(pageSize);

		// is count needed
		searchCriteria.setGetCount(getCount);

		searchCriteria.setOwnerId((Number) paramList.get("ownerId"));
		searchCriteria.setGetChildren(stringUtil.toBoolean(
				(String) paramList.get("getChildren"), false));

		String sortBy = (String) paramList.get("sortBy");
		boolean sortSet = false;
		if (!stringUtil.isEmpty(sortBy)) {
			for (SortField sortField : sortFields) {
				if (sortField.getParamName().equalsIgnoreCase(sortBy)) {					
					searchCriteria.setSortBy(sortField.getParamName());
					String sortType = (String) paramList.get("sortType");
					searchCriteria.setSortType(sortType);					
					sortSet = true;
					break;
				}
			}
		}

		if (!sortSet && !stringUtil.isEmpty(sortBy)) {
			logger.info("Invalid or unsupported sortBy field passed. sortBy="
					+ sortBy, new Throwable());
		}
		return searchCriteria;
	}

	/**
	 * @param request
	 * @param sortFields
	 * @return
	 */
	public SearchCriteria extractCommonCriterias(HttpServletRequest request,
			List<SortField> sortFields) {
		SearchCriteria searchCriteria = new SearchCriteria();

		int startIndex = restErrorUtil.parseInt(
				request.getParameter("startIndex"), 0,
				"Invalid value for parameter startIndex",
				MessageEnums.INVALID_INPUT_DATA, null, "startIndex");
		searchCriteria.setStartIndex(startIndex);

		int pageSize = restErrorUtil.parseInt(request.getParameter("pageSize"),
				25/*Integer.parseInt(PropertiesUtil.getProperty("db.maxResults","25"))*/,
				"Invalid value for parameter pageSize",
				MessageEnums.INVALID_INPUT_DATA, null, "pageSize");
		searchCriteria.setMaxRows(pageSize);

		// is count needed
		searchCriteria.setGetCount(restErrorUtil.parseBoolean(
				request.getParameter("getCount"), true));

		searchCriteria.setOwnerId(restErrorUtil.parseLong(
				request.getParameter("ownerId"), null));
		searchCriteria.setGetChildren(restErrorUtil.parseBoolean(
				request.getParameter("getChildren"), false));

		String sortBy = restErrorUtil.validateString(
				request.getParameter("sortBy"), StringUtil.VALIDATION_ALPHA,
				"Invalid value for parameter sortBy",
				MessageEnums.INVALID_INPUT_DATA, null, "sortBy");

		boolean sortSet = false;
		if (!stringUtil.isEmpty(sortBy)) {
			for (SortField sortField : sortFields) {
				if (sortField.getParamName().equalsIgnoreCase(sortBy)) {
					searchCriteria.setSortBy(sortField.getParamName());
					String sortType = restErrorUtil.validateString(
							request.getParameter("sortType"),
							StringUtil.VALIDATION_ALPHA,
							"Invalid value for parameter sortType",
							MessageEnums.INVALID_INPUT_DATA, null, "sortType");
					searchCriteria.setSortType(sortType);
					sortSet = true;
					break;
				}
			}
		}

		if (!sortSet && !stringUtil.isEmpty(sortBy)) {
			logger.info("Invalid or unsupported sortBy field passed. sortBy="
					+ sortBy, new Throwable());
		}
		
		return searchCriteria;
	}

	/**
	 * @param request
	 * @param sortFields
	 * @return
	 */
	public SearchCriteria buildSearchCriteria(HttpServletRequest request,
			List<SortField> sortFields, List<SearchField> searchFields) {
		SearchCriteria searchCriteria = extractCommonCriterias(request,
				sortFields);
		for (SearchField searchField : searchFields) {
			if (searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				extractLong(request, searchCriteria,
						searchField.getClientFieldName(),
						searchField.getClientFieldName());
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.STRING) {
				extractString(request, searchCriteria,
						searchField.getClientFieldName(),
						searchField.getClientFieldName(), null);
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.INT_LIST) {
				extractEnum(request, searchCriteria,
						searchField.getClientFieldName(),
						searchField.getEnumName(),
						searchField.getClientFieldName(),
						searchField.getMaxValue());
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.BOOLEAN) {
				extractBoolean(request, searchCriteria,
						searchField.getClientFieldName(),
						searchField.getClientFieldName());
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.DATE) {
				extractDate(request, searchCriteria,
						searchField.getClientFieldName(),
						searchField.getClientFieldName());
			}
		}

		return searchCriteria;
	}

	public SearchField getSearchField(List<SearchField> searchFields,
			String fieldName) {
		for (SearchField searchField : searchFields) {
			if (searchField.getClientFieldName().equals(fieldName)) {
				return searchField;
			}
		}
		return null;
	}

	public void setParam(SearchCriteria searchCriteria, String paramName,
			Object value) {
		searchCriteria.getParamList().put(paramName, value);
	}

	public void setOrParam(SearchCriteria searchCriteria, String paramName,
			Object value) {
		searchCriteria.getParamList().put(paramName, value);
	}

	public void setParamLongList(SearchCriteria searchCriteria,
			String paramName, int[] values) {
		if (values != null) {
			ArrayList<Long> valueList = new ArrayList<Long>();
			for (int i = 0; i < values.length; i++) {
				valueList.add(new Long(values[i]));
			}
			searchCriteria.getParamList().put(paramName, valueList);
		}
	}

	public void setParamIntList(SearchCriteria searchCriteria,
			String paramName, int[] values) {
		if (values != null) {
			ArrayList<Integer> valueList = new ArrayList<Integer>();
			for (int i = 0; i < values.length; i++) {
				valueList.add(new Integer(values[i]));
			}
			searchCriteria.getParamList().put(paramName, valueList);
		}
	}

	public void setParamStringList(SearchCriteria searchCriteria,
			String paramName, String[] values) {
		if (values != null) {
			ArrayList<String> valueList = new ArrayList<String>();
			for (int i = 0; i < values.length; i++) {
				valueList.add(values[i]);
			}
			searchCriteria.getParamList().put(paramName, valueList);
		}
	}

//	public void addSearchGroup(SearchCriteria searchCriteria,
//			SearchGroup searchGroup) {
//		searchCriteria.getSearchGroups().add(searchGroup);
//	}

	public Long extractLong(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName) {
		String[] values = getParamMultiValues(request, paramName, paramName);
		if (values != null && values.length > 1) {
			List<Long> multiValues = extractLongList(request, searchCriteria,
					paramName, userFriendlyParamName, paramName);
			if (multiValues != null && multiValues.size() > 0) {
				return multiValues.get(0);
			} else {
				return null;
			}
		} else {
			Long value = restErrorUtil.parseLong(
					request.getParameter(paramName), "Invalid value for "
							+ userFriendlyParamName,
					MessageEnums.INVALID_INPUT_DATA, null, paramName);
			if (value != null) {
				searchCriteria.getParamList().put(paramName, value);
			}
			return value;
		}
	}

	public Integer extractInt(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName) {
		Integer value = restErrorUtil.parseInt(request.getParameter(paramName),
				"Invalid value for " + userFriendlyParamName,
				MessageEnums.INVALID_INPUT_DATA, null, paramName);
		if (value != null) {
			searchCriteria.getParamList().put(paramName, value);
		}
		return value;
	}

	public Boolean extractBoolean(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName) {
		Boolean value = restErrorUtil.parseBoolean(
				request.getParameter(paramName), "Invalid value for "
						+ userFriendlyParamName,
				MessageEnums.INVALID_INPUT_DATA, null, paramName);
		if (value != null) {
			searchCriteria.getParamList().put(paramName, value);
		}
		return value;
	}

	/**
	 * @param request
	 * @param searchCriteria
	 * @param string
	 * @param string2
	 */
	public Date extractDate(HttpServletRequest request,
			SearchCriteria searchCriteria, String string, String string2) {
		Date value = null;

		// TODO Need to implement date parameters

		return value;
	}

	public String extractString(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName, String regEx) {
		String value = request.getParameter(paramName);
		if (!stringUtil.isEmpty(value)) {
			value = value.trim();
			if (!stringUtil.isEmpty(regEx)) {
				restErrorUtil.validateString(value, regEx, "Invalid value for "
						+ userFriendlyParamName,
						MessageEnums.INVALID_INPUT_DATA, null, paramName);
			}
			searchCriteria.getParamList().put(paramName, value);
		}
		return value;
	}

	public List<Integer> extractEnum(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName, String listName, int maxValue) {

		ArrayList<Integer> valueList = new ArrayList<Integer>();
		String[] values = getParamMultiValues(request, paramName, listName);
		for (int i = 0; values != null && i < values.length; i++) {
			Integer value = restErrorUtil.parseInt(values[i],
					"Invalid value for " + userFriendlyParamName,
					MessageEnums.INVALID_INPUT_DATA, null, paramName);

			restErrorUtil.validateMinMax(value, 0, maxValue,
					"Invalid value for " + userFriendlyParamName, null,
					paramName);
			valueList.add(value);
		}
		if (valueList.size() > 0) {
			searchCriteria.getParamList().put(listName, valueList);
		}
		return valueList;
	}

	/**
	 * @param request
	 * @param paramName
	 * @param listName
	 * @return
	 */
	String[] getParamMultiValues(HttpServletRequest request, String paramName,
			String listName) {
		String[] values = request.getParameterValues(paramName);
		if (values == null || values.length == 0) {
			values = request.getParameterValues(paramName + "[]");
			if (listName != null && (values == null || values.length == 0)) {
				values = request.getParameterValues(listName);
				if (values == null || values.length == 0) {
					// Let's try after appending []
					values = request.getParameterValues(listName + "[]");
				}
			}
		}
		return values;
	}

	public List<String> extractStringList(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName, String listName,
			String[] validValues, String regEx) {
		ArrayList<String> valueList = new ArrayList<String>();
		String[] values = getParamMultiValues(request, paramName, listName);

		for (int i = 0; values != null && i < values.length; i++) {
			if (!stringUtil.isEmpty(regEx)) {
				restErrorUtil.validateString(values[i], regEx,
						"Invalid value for " + userFriendlyParamName,
						MessageEnums.INVALID_INPUT_DATA, null, paramName);
			}
			valueList.add(values[i]);
		}
		searchCriteria.getParamList().put(listName, valueList);
		return valueList;
	}

	public List<Long> extractLongList(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName, String listName) {
		ArrayList<Long> valueList = new ArrayList<Long>();
		String[] values = getParamMultiValues(request, paramName, listName);

		for (int i = 0; values != null && i < values.length; i++) {
			Long value = restErrorUtil.parseLong(
					values[i], "Invalid value for "
							+ userFriendlyParamName,
					MessageEnums.INVALID_INPUT_DATA, null, paramName);
			valueList.add(value);
		}
		searchCriteria.getParamList().put(listName, valueList);
		return valueList;
	}

	public void updateQueryPageSize(Query query, SearchCriteria searchCriteria) {
		// Set max records
		int pageSize = validatePageSize(searchCriteria.getMaxRows());

		query.setMaxResults(pageSize);

		// Set hint for max records
		query.setHint("eclipselink.jdbc.max-rows", "" + pageSize);

	}

	public int validatePageSize(int inputPageSize) {
		int pageSize = inputPageSize;

		if (pageSize < 1) {
			// Use default max Records
			//pageSize = Integer.getInteger(PropertiesUtil.getProperty("db.maxResults","25"));
			pageSize = 25;
		}
		return pageSize;
	}

	/**
	 * @param searchCriteria
	 * @param sortFields
	 * @return
	 */
	public String constructSortClause(SearchCriteria searchCriteria,
			List<SortField> sortFields) {
		String sortBy = searchCriteria.getSortBy();
		String querySortBy = null;
		if (!stringUtil.isEmpty(sortBy)) {
			sortBy = sortBy.trim();
			for (SortField sortField : sortFields) {
				if (sortBy.equalsIgnoreCase(sortField.getParamName())) {
					querySortBy = sortField.getFieldName();
					if(sortField.isCaseSensitive()){
						querySortBy = "LOWER("+sortField.getFieldName()+")";
					}
					// Override the sortBy using the normalized value
					searchCriteria.setSortBy(sortField.getParamName());
					break;
				}
			}
		}

		if (querySortBy == null) {
			for (SortField sortField : sortFields) {
				if (sortField.isDefault()) {
					querySortBy = sortField.getFieldName();
					if(sortField.isCaseSensitive()){
						querySortBy = "LOWER("+sortField.getFieldName()+")";
					}
					
					// Override the sortBy using the default value
					searchCriteria.setSortBy(sortField.getParamName());
					searchCriteria.setSortType(sortField.getDefaultOrder()
							.name());
					break;
				}
			}
		}

		if (querySortBy != null) {
			// Add sort type
			String sortType = searchCriteria.getSortType();
			String querySortType = "asc";
			if (sortType != null) {
				if (sortType.equalsIgnoreCase("asc")
						|| sortType.equalsIgnoreCase("desc")) {
					querySortType = sortType;
				} else {
					logger.error("Invalid sortType. sortType=" + sortType);
				}
			}
			// Override the sortType using the final value
			searchCriteria.setSortType(querySortType);
			//String caseInsensitiveSortBy = "LOWER("+querySortBy+")";
			String sortClause = " ORDER BY " + querySortBy + " "
					+ querySortType;

			return sortClause;
		}
		return null;
	}

//	@Deprecated
//	public void addDomainObjectSecuirtyClause(StringBuilder whereClause,
//			boolean hasAttributes) {
//		String attributesClause = "(";
//		if (hasAttributes) {
//			attributesClause += "sharingPreference = "
//					+ MDataAttributesBase.SHARE_PREF_PUBLIC + " AND ";
//		}
//		whereClause
//				.append(" AND ( "
//						+ attributesClause
//						+ " not exists( from GJUserDataPref userPref where userPref.userId= :sec_currentUserId AND "
//						+ " userPref.hideData=1 AND userPref.objectClassType=:sec_objectClassType AND userPref.objectId = id )"
//						+ ") ) ");
//	}

	@Deprecated
	public void resolveDomainObjectSecuirtyParams(Query query,
			int objectClassType) {
		query.setParameter("sec_currentUserId", ContextUtil.getCurrentUserId());
		query.setParameter("sec_objectClassType", objectClassType);
	}

	private StringBuilder buildWhereClause(SearchCriteria searchCriteria,
			List<SearchField> searchFields) {
		return buildWhereClause(searchCriteria, searchFields, false, false);
	}

	@SuppressWarnings("unchecked")
	private StringBuilder buildWhereClause(SearchCriteria searchCriteria,
			List<SearchField> searchFields, boolean isNativeQuery,
			boolean excludeWhereKeyword) {

		Map<String, Object> paramList = searchCriteria.getParamList();

		StringBuilder whereClause = new StringBuilder(excludeWhereKeyword ? ""
				: "WHERE 1 = 1 ");

		List<String> joinTableList = new ArrayList<String>();

		String addedByFieldName = isNativeQuery ? "added_by_id"
				: "addedByUserId";

		Number ownerId = searchCriteria.getOwnerId();
		if (ownerId != null) {
			whereClause.append(" and obj.").append(addedByFieldName)
					.append(" = :ownerId");
		}

		// Let's handle search groups first
//		int groupCount = -1;
//		for (SearchGroup searchGroup : searchCriteria.getSearchGroups()) {
//			groupCount++;
//			whereClause.append(" and ").append(
//					searchGroup.getWhereClause("" + groupCount));
//		}

		for (SearchField searchField : searchFields) {
			int startWhereLen = whereClause.length();

			if (searchField.getFieldName() == null
					&& searchField.getCustomCondition() == null) { // this field
				// is used
				// only for
				// binding!
				continue;
			}

			Object paramValue = paramList.get(searchField.getClientFieldName());
			boolean isListValue = false;
			if (paramValue != null && paramValue instanceof Collection) {
				isListValue = true;
			}

			if (searchCriteria.getNullParamList().contains(
					searchField.getClientFieldName())) {
				whereClause.append(" and ").append(searchField.getFieldName())
						.append(" is null");
			} else if (searchCriteria.getNotNullParamList().contains(
					searchField.getClientFieldName())) {
				whereClause.append(" and ").append(searchField.getFieldName())
						.append(" is not null");

			} else if (searchField.getDataType() == SearchField.DATA_TYPE.INT_LIST
					|| isListValue
					&& searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				Collection<Number> intValueList = null;
				if (paramValue != null
						&& (paramValue instanceof Integer || paramValue instanceof Long)) {
					intValueList = new ArrayList<Number>();
					intValueList.add((Number) paramValue);
				} else {
					intValueList = (Collection<Number>) paramValue;
				}

				if (intValueList != null && intValueList.size() > 0) {
					if (searchField.getCustomCondition() == null) {
						if (intValueList.size() <= minInListLength) {
							whereClause.append(" and ");
							if (intValueList.size() > 1) {
								whereClause.append(" ( ");
							}
							for (int count = 0; count < intValueList.size(); count++) {
								if (count > 0) {
									whereClause.append(" or ");
								}
								whereClause
										.append(searchField.getFieldName())
										.append(" = :")
										.append(searchField
												.getClientFieldName()
												+ "_"
												+ count);
							}

							if (intValueList.size() > 1) {
								whereClause.append(" ) ");
							}

						} else {
							whereClause.append(" and ")
									.append(searchField.getFieldName())
									.append(" in ( :")
									.append(searchField.getClientFieldName())
									.append(")");
						}
					} else {
						whereClause.append(" and ").append(
								searchField.getCustomCondition());
					}
				}

			} else if (searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				Number intFieldValue = (Number) paramList.get(searchField
						.getClientFieldName());
				if (intFieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ")
								.append(searchField.getFieldName())
								.append("=:")
								.append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(
								searchField.getCustomCondition());
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.STRING) {
				String strFieldValue = (String) paramList.get(searchField
						.getClientFieldName());
				if (strFieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ").append("LOWER(")
								.append(searchField.getFieldName()).append(")");
						if (searchField.getSearchType() == SearchField.SEARCH_TYPE.FULL) {
							whereClause.append("= LOWER(:").append(
									searchField.getClientFieldName()+")");
						} else {
							whereClause.append("like LOWER(:").append(
									searchField.getClientFieldName()+")");
						}
					} else {
						whereClause.append(" and ").append(
								searchField.getCustomCondition());
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.BOOLEAN) {
				Boolean boolFieldValue = (Boolean) paramList.get(searchField
						.getClientFieldName());
				if (boolFieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ")
								.append(searchField.getFieldName())
								.append("=:")
								.append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(
								searchField.getCustomCondition());
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.DATE) {
				Date fieldValue = (Date) paramList.get(searchField
						.getClientFieldName());
				if (fieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ").append(
								searchField.getFieldName());
						if (searchField.getSearchType().equals(
								SearchField.SEARCH_TYPE.LESS_THAN)) {
							whereClause.append("< :");
						} else if (searchField.getSearchType().equals(
								SearchField.SEARCH_TYPE.LESS_EQUAL_THAN)) {
							whereClause.append("<= :");
						} else if (searchField.getSearchType().equals(
								SearchField.SEARCH_TYPE.GREATER_THAN)) {
							whereClause.append("> :");
						} else if (searchField.getSearchType().equals(
								SearchField.SEARCH_TYPE.GREATER_EQUAL_THAN)) {
							whereClause.append(">= :");
						}
						whereClause.append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(
								searchField.getCustomCondition());
					}
				}

			}

			if (whereClause.length() > startWhereLen
					&& searchField.getJoinTables() != null) {
				for (String table : searchField.getJoinTables()) {
					if (!joinTableList.contains(table)) {
						joinTableList.add(table);
					}
				}

				whereClause.append(" and (")
						.append(searchField.getJoinCriteria()).append(")");
			}
		} // for

		for (String joinTable : joinTableList) {
			whereClause.insert(0, ", " + joinTable + " ");
		}

		return whereClause;
	}

	private void addOrderByClause(StringBuilder queryClause, String sortClause) {
		if (sortClause != null) {
			queryClause.append(sortClause);
		}
	}

	@SuppressWarnings("unchecked")
	private void resolveQueryParams(Query query, SearchCriteria searchCriteria,
			List<SearchField> searchFields) {

		Map<String, Object> paramList = searchCriteria.getParamList();

		Number ownerId = searchCriteria.getOwnerId();
		if (ownerId != null) {
			query.setParameter("ownerId", ownerId);
		}

		// Let's handle search groups first
//		int groupCount = -1;
//		for (SearchGroup searchGroup : searchCriteria.getSearchGroups()) {
//			groupCount++;
//			searchGroup.resolveValues(query, "" + groupCount);
//		}

		for (SearchField searchField : searchFields) {
			Object paramValue = paramList.get(searchField.getClientFieldName());
			boolean isListValue = false;
			if (paramValue != null && paramValue instanceof Collection) {
				isListValue = true;
			}

			if (searchCriteria.getNullParamList().contains(
					searchField.getClientFieldName())
					|| searchCriteria.getNotNullParamList().contains(
							searchField.getClientFieldName())) {
				// Already addressed while building where clause
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.INT_LIST
					|| isListValue
					&& searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				Collection<Number> intValueList = null;
				if (paramValue != null
						&& (paramValue instanceof Integer || paramValue instanceof Long)) {
					intValueList = new ArrayList<Number>();
					intValueList.add((Number) paramValue);
				} else {
					intValueList = (Collection<Number>) paramValue;
				}

				if (intValueList != null && intValueList.size() > 0
						&& intValueList.size() <= minInListLength) {
					int count = -1;
					for (Number value : intValueList) {
						count++;
						query.setParameter(searchField.getClientFieldName()
								+ "_" + count, value);

					}

				} else if (intValueList != null && intValueList.size() > 1) {
					query.setParameter(searchField.getClientFieldName(),
							intValueList);
				}

			} else if (searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				Number intFieldValue = (Number) paramList.get(searchField
						.getClientFieldName());
				if (intFieldValue != null) {
					query.setParameter(searchField.getClientFieldName(),
							intFieldValue);
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.STRING) {
				String strFieldValue = (String) paramList.get(searchField
						.getClientFieldName());
				if (strFieldValue != null) {
					if (searchField.getSearchType() == SearchField.SEARCH_TYPE.FULL) {
						query.setParameter(searchField.getClientFieldName(),
								strFieldValue);
					} else {
						query.setParameter(searchField.getClientFieldName(),
								"%" + strFieldValue + "%");
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.BOOLEAN) {
				Boolean boolFieldValue = (Boolean) paramList.get(searchField
						.getClientFieldName());
				if (boolFieldValue != null) {
					query.setParameter(searchField.getClientFieldName(),
							boolFieldValue);
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.DATE) {
				Date fieldValue = (Date) paramList.get(searchField
						.getClientFieldName());
				if (fieldValue != null) {
					query.setParameter(searchField.getClientFieldName(),
							fieldValue);
				}
			}

		} // for
	}

	public Query createSearchQuery(String queryStr, String sortClause,
			SearchCriteria searchCriteria, List<SearchField> searchFields,
			int objectClassType, boolean hasAttributes, boolean isCountQuery) {

		// [1] Build where clause
		StringBuilder queryClause = buildWhereClause(searchCriteria,
				searchFields);

		// [2] Add domain-object-security clause if needed
		// if (objectClassType != -1
		// && !ContextUtil.getCurrentUserSession().isUserAdmin()) {
		// addDomainObjectSecuirtyClause(queryClause, hasAttributes);
		// }

		// [2] Add order by clause
		addOrderByClause(queryClause, sortClause);

		// [3] Create Query Object
		Query query = daoManager.getEntityManager().createQuery(
				queryStr + queryClause);

		// [4] Resolve query parameters with values
		resolveQueryParams(query, searchCriteria, searchFields);

		// [5] Resolve domain-object-security parameters
		// if (objectClassType != -1 &&
		// !securityHandler.hasModeratorPermission()) {
		// resolveDomainObjectSecuirtyParams(query, objectClassType);
		// }

		if (!isCountQuery) {
			query.setFirstResult(searchCriteria.getStartIndex());
			updateQueryPageSize(query, searchCriteria);
		}

		return query;
	}

	public Query createSearchQuery(String queryStr, String sortClause,
			SearchCriteria searchCriteria, List<SearchField> searchFields,
			boolean isCountQuery) {
		return createSearchQuery(queryStr, sortClause, searchCriteria,
				searchFields, -1, false, isCountQuery);
	}

	public Query createNativeSearchQuery(BaseDao<?> dao, String queryStr,
			boolean queryIncludesWhere, String sortClause,
			SearchCriteria searchCriteria, List<SearchField> searchFields,
			boolean isCountQuery) {
		return createNativeSearchQuery(dao, queryStr, queryIncludesWhere,
				sortClause, searchCriteria, searchFields, -1, false,
				isCountQuery);
	}

	public Query createNativeSearchQuery(BaseDao<?> dao, String queryStr,
			boolean queryIncludesWhere, String sortClause,
			SearchCriteria searchCriteria, List<SearchField> searchFields,
			int objectClassType, boolean hasAttributes, boolean isCountQuery) {

		StringBuilder queryClause = buildWhereClause(searchCriteria,
				searchFields, true, queryIncludesWhere);

		Query query = null;

//		if (isCountQuery) {
//			query = dao.getNativeCountQueryWithSecurityContext(queryStr + " "
//					+ queryClause.toString(), sortClause);
//		} else {
//			query = dao.getNativeQueryWithSecurityContext(queryStr + " "
//					+ queryClause.toString(), sortClause);
//		}

		resolveQueryParams(query, searchCriteria, searchFields);

		if (!isCountQuery) {
			query.setFirstResult(searchCriteria.getStartIndex());
			updateQueryPageSize(query, searchCriteria);
		}

		return query;
	}

	public Query createNativeSearchCountQuery(BaseDao<?> dao, String queryStr,
			boolean queryIncludesWhere, String sortClause,
			SearchCriteria searchCriteria, List<SearchField> searchFields) {
		return createNativeSearchQuery(dao, queryStr, queryIncludesWhere,
				sortClause, searchCriteria, searchFields, -1, false, true);
	}

	public List<SortBy> getSortByListFromRequest(HttpServletRequest request,
			List<SortField> sortFields) {
		String[] sortByValues = getParamMultiValues(request, "sortBy", "sortBy");
		String[] sortTypeValues = getParamMultiValues(request, "sortType",
				"sortType");
		List<SortBy> sortByList = new ArrayList<SortBy>();
		SortBy sortBy = new SortBy();
		if (sortByValues != null) {
			for (int i = 0; i < sortByValues.length; i++) {
				for (SortField sortField : sortFields) {
					if (sortByValues[i] != null) {
						if (sortField.getParamName().trim()
								.equalsIgnoreCase(sortByValues[i].trim())) {
							sortBy = new SortBy();

							sortBy.setFieldName(sortByValues[i]);

							if (sortTypeValues != null) {
								if (i < sortTypeValues.length
										&& sortTypeValues[i] != null) {
									sortBy.setSortType(sortTypeValues[i]);
								}
							}
							if (sortBy.getSortType() == null
									|| sortBy.getSortType().isEmpty()) {
								sortBy.setSortType(sortField.getDefaultOrder().name());
							}
							sortByList.add(sortBy);
							break;

						}

					}
				}
			}
		}
		validateSortByList(sortByList);		
		return sortByList;
	}
	
	private void validateSortByList(List<SortBy> sortByList) {
		for (SortBy sortBy : sortByList) {
			restErrorUtil.validateString(sortBy.getFieldName(),
					StringUtil.VALIDATION_ALPHA,
					"Invalid value for parameter sortBy",
					MessageEnums.INVALID_INPUT_DATA, null, "sortBy");
			restErrorUtil.validateString(sortBy.getSortType(),
					StringUtil.VALIDATION_ALPHA,
					"Invalid value for parameter sortType",
					MessageEnums.INVALID_INPUT_DATA, null, "sortType");
			
			if (sortBy.getSortType().equalsIgnoreCase("asc")
					|| sortBy.getSortType().equalsIgnoreCase("desc")) {
				logger.error("Invalid sortType. sortType=" + sortBy.getSortType());
			}

		}
	}
	/**
	 * 
	 * @param request
	 * @param searchCriteria
	 * @param paramName
	 * @param userFriendlyParamName
	 * @param dateFormat
	 * @return
	 */
	
	public Date extractDate(HttpServletRequest request,
			SearchCriteria searchCriteria, String paramName,
			String userFriendlyParamName, String dateFormat) {
		Date value = null;
		if (dateFormat == null || dateFormat.isEmpty()) {
			dateFormat = defaultDateFormat;
		}
		value = restErrorUtil.parseDate(request.getParameter(paramName),
				"Invalid value for" + userFriendlyParamName,
				MessageEnums.INVALID_INPUT_DATA, null, paramName, dateFormat);
		if (value != null) {
			searchCriteria.getParamList().put(paramName, value);
		}

		return value;
	}
	
	
	public void setPaginationManual(Query query , SearchCriteria searchCriteria, VList vList){
		query.setFirstResult(searchCriteria.getStartIndex());
		updateQueryPageSize(query, searchCriteria);
		if(vList!= null){
			
			
			//TODO kartik if using old hibernate version
			// Set the meta values for the query result
			
			//vList.setPageSize(query.getMaxResults());
			vList.setPageSize(query.getResultList().size());
			
			//vList.setStartIndex(query.getFirstResult());
			vList.setSortType(searchCriteria.getSortType());
			vList.setSortBy(searchCriteria.getSortBy());
		}
	}
}
