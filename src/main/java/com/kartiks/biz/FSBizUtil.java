
package com.kartiks.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonParseException;
import com.kartiks.common.FSCommonEnums;
import com.kartiks.common.MessageEnums;
import com.kartiks.common.RESTErrorUtil;
import com.kartiks.common.SearchCriteria;
import com.kartiks.common.SearchField;
import com.kartiks.common.SearchUtil;
import com.kartiks.common.SortField;
import com.kartiks.db.DaoManager;
import com.kartiks.entity.EWBase;
import com.kartiks.view.VList;



@Component
public class FSBizUtil {

	static final Logger logger = Logger.getLogger(FSBizUtil.class);

	@Autowired
	SearchUtil searchUtil;

	@Autowired
	RESTErrorUtil restErrorUtil;

	@Autowired
	DaoManager daoManager;
	
	
	/**
	 * @param File f
	 * @return String contents of file
	 */
	public String readFile(File f) {

		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(f));
			StringBuffer sb=new StringBuffer();
			
			String line;
			try {
				while((line=reader.readLine())!=null){
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				reader.close();
				
			} catch (IOException e) {
				
				logger.error(e);
				throw restErrorUtil.createRESTException("error  creating file", MessageEnums.ERROR_SYSTEM);
			}

			return sb.toString();
		} catch (FileNotFoundException e1) {
			logger.error(e1);
			throw restErrorUtil.createRESTException("error creating file", MessageEnums.ERROR_SYSTEM);
		}
	}

	Map<String, Integer> classTypeMappings = new HashMap<String, Integer>();
	public int getClassType(Class<?> klass) {
		String className = klass.getName();
		// See if this mapping is already in the database
		Integer classType = classTypeMappings.get(className);
		if (classType == null) {
			// Instantiate the class and call the getClassType method
			if (EWBase.class.isAssignableFrom(klass)) {
				try {
					EWBase gjObj = (EWBase) klass.newInstance();
					classType = gjObj.getMyClassType();
					classTypeMappings.put(className, classType);
				} catch (Throwable ex) {
					logger.error("Error instantiating object for class " + className, ex);
				}
			}
		}
		if (classType == null) {
			return FSCommonEnums.CLASS_TYPE_NONE;
		} else {
			return classType;
		}
	}

	public void saveFile(InputStream uploadedInputStream,String uploadedFileLocation) throws IOException{
		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
 
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
 
			throw e;
		}
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void makeSearchnReturnCollection(VList collection, SearchCriteria searchCriteria) {
		
		Class collectionClass;
		String entityClassName = null;

		// get class name form obj
		try {
			collectionClass = Class.forName(collection.getClass().getName());
			Field[] array = collectionClass.getDeclaredFields();

			if (array.length == 0) {
				// System.out.println("f.getType().toString() is 0000");
				throw restErrorUtil.createRESTException("No fields in collection", MessageEnums.DATA_NOT_FOUND);
			}
			for (int i = 0; i < array.length; i++) {

				Field f = array[i];

				if (f.getType().toString().contains("java.util.List")) {

					Class<?> c = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
					entityClassName = (c.getName());
					break;
				}
			}

		} catch (ClassNotFoundException e1) {

			logger.error(e1);
			// will never be a case
		}

		try {

			if (entityClassName != null) {
				
				Class c = Class.forName(entityClassName);
				Object o = c.newInstance();

				String queryStr = "SELECT obj from " + entityClassName + " obj ";
				String countQueryStr = "SELECT count(*) from " + entityClassName + " obj ";

				// get sort and search lists
				List<SortField> sortList = null;
				List<SearchField> searchField = null;

				Method[] m = c.getMethods();

				//Object args = new Object();
				for (int i = 0; i < m.length; i++) {

					// System.out.println(i+" "+m[i].getName());
					if (m[i].getName().equals("getSortList")) {

						sortList = (List<SortField>) m[i].invoke(o);

					} else if (m[i].getName().equals("getSearchFields")) {

						searchField = (List<SearchField>) m[i].invoke(o);
					}
				}

				// proceed if both objs successfully found
				if (sortList != null && searchField != null) {

					String sortClause = searchUtil.constructSortClause(searchCriteria, sortList);
					Query query = searchUtil.createSearchQuery(countQueryStr, null, searchCriteria, searchField, true);
					Long count = (Long) query.getSingleResult();
					if (count == 0) {
						collection.setTotalCount(0);
						return;
					} else
						collection.setTotalCount(count);

					query = searchUtil.createSearchQuery(queryStr, sortClause, searchCriteria, searchField, false);
					searchUtil.setPaginationManual(query, searchCriteria, collection);
					collection.getList().addAll(query.getResultList());

				} else {
					throw restErrorUtil.createRESTException("sort or search fields not found with method name getSortList,getSearchFields",
							MessageEnums.DATA_NOT_FOUND);
				}

			} else {

				// ideally would never be the case
				throw restErrorUtil.createRESTException("entity class not found", MessageEnums.DATA_NOT_FOUND);
			}

		} catch (Exception e) {

			// ClassNotFoundException InstantiationException
			// IllegalAccessException IllegalArgumentException
			// InvocationTargetException
			logger.error(e);
			throw restErrorUtil.createRESTException("entity class not found", MessageEnums.DATA_NOT_FOUND);
		}
	}

	//TODO kartik
//	public Object writeJsonToJavaObject(String json, Class<?> tClass) {
//		ObjectMapper mapper = new ObjectMapper();
//
//		try {
//			return mapper.readValue(json, tClass);
//		} catch (JsonParseException e) {
//			throw restErrorUtil.createRESTException("Invalid input data: " + e.getMessage(), MessageEnums.INVALID_INPUT_DATA);
//		} catch (JsonMappingException e) {
//			throw restErrorUtil.createRESTException("Invalid input data: " + e.getMessage(), MessageEnums.INVALID_INPUT_DATA);
//		} catch (IOException e) {
//			throw restErrorUtil.createRESTException("Invalid input data: " + e.getMessage(), MessageEnums.INVALID_INPUT_DATA);
//		}
//	}

}
