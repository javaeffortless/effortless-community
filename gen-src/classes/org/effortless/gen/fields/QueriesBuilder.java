package org.effortless.gen.fields;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

import javax.naming.NamingException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class QueriesBuilder {

	//
	
	public static Criteria create (Class clazz, String name, Object[] params) throws NamingException, NoSuchFieldException, SecurityException {
		Criteria result = null;
		Session session = org.effortless.model.SessionManager.loadSession(clazz);
		
		result = session.createCriteria(clazz);
		
		if (checkDeleted(clazz, name, params)) {
			result = addParam(result, clazz, "deleted", Boolean.FALSE);
		}
		
		Object[][] parameters = getParameters(clazz, name, params);
		for (Object[] parameter : parameters) {
			String pName = (String)parameter[0];
			Object pValue = parameter[1];
			result = addParam(result, clazz, pName, pValue);
		}
		
		return result;
	}
	
	protected static boolean checkDeleted(Class clazz, String name,
			Object[] params) {
		// TODO Auto-generated method stub
		return true;
	}

	protected static final String[] PREFIXES = {"listBy", "findBy", "countBy"};
	
	protected static class Parameter {
		
		protected Parameter () {
			super();
		}
		
		public Parameter (String name, Object value) {
			this();
			setName(name);
			setValue(value);
		}
		
		protected String name;
		
		public String getName () {
			return this.name;
		}
		
		public void setName (String newValue) {
			this.name = newValue;
		}

		protected Object value;
		
		public Object getValue () {
			return this.value;
		}
		
		public void setValue (Object newValue) {
			this.value = newValue;
		}
		
		
	}
	
	protected static Object[][] getParameters(Class clazz, String name, Object[] params) {
		Object[][] result = null;
		name = removePrefix(name);
		String[] properties = name.split("(?=\\p{Upper})");
		int propertiesLength = (properties != null ? properties.length : 0);
		int paramsLength = (params != null ? params.length : 0);
		if (propertiesLength < paramsLength) {
			
		}
		propertiesLength = (properties != null ? properties.length : 0);
		for (int i = 0; i < propertiesLength; i++) {
//			result = new 
		}
		//listByName1Name2Name3
		//findBy
		//countBy
		// TODO Auto-generated method stub
		return result;
	}

	protected static String removePrefix(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	protected static Criteria addParam (Criteria criteria, Class clazz, String pName, Object pValue) throws NoSuchFieldException, SecurityException {
		Criteria result = criteria;
		Field field = clazz.getField(pName);
		Class fieldType = (field != null ? field.getType() : null);
		
		if (String.class.isAssignableFrom(fieldType) && pValue instanceof String) {
			result = addParamText(result, clazz, pName, (String)pValue);
		}
		else if (Boolean.class.isAssignableFrom(fieldType) && pValue instanceof Boolean) {
			result = addParamBool(result, clazz, pName, (Boolean)pValue);
		}
		else if (Integer.class.isAssignableFrom(fieldType) && pValue instanceof Integer) {
			result = addParamCount(result, clazz, pName, (Integer)pValue);
		}
		else if (Date.class.isAssignableFrom(fieldType) && pValue instanceof Date) {
			result = addParamDate(result, clazz, pName, (Date)pValue);
		}
		else if (Enum.class.isAssignableFrom(fieldType) && pValue instanceof Enum) {
			result = addParamEnum(result, clazz, pName, (Enum)pValue);
		}
		else if (File.class.isAssignableFrom(fieldType) && pValue instanceof File) {
			result = addParamFile(result, clazz, pName, (File)pValue);
		}
		else if (/*Collection.class.isAssignableFrom(fieldType) && */pValue instanceof Collection) {
			result = addParamList(result, clazz, pName, (Collection)pValue);
		}
		else if (Number.class.isAssignableFrom(fieldType) && pValue instanceof Number) {
			result = addParamNumber(result, clazz, pName, (Number)pValue);
		}
		else {
			result = addParamRef(result, clazz, pName, pValue);
		}
		
		return result;
	}
	
	protected static Criteria addParamText (Criteria criteria, Class clazz, String pName, String pValue) {
		Criteria result = criteria;
		if (pValue != null && !"".equals(pValue)) {//text
			result = result.add(Restrictions.like(pName, "%" + pValue + "%", MatchMode.ANYWHERE));
		}
		return result;
	}
	
	protected static Criteria addParamBool (Criteria criteria, Class clazz, String pName, Boolean pValue) {
		Criteria result = criteria;
		if (pValue != null) {//boolean
			Disjunction or = Restrictions.disjunction();
			if (pValue.booleanValue()) {
				or.add(Restrictions.isNotNull(pName));
			}
			else {
				or.add(Restrictions.isNull(pName));
			}
			or.add(Restrictions.eq(pName, pValue));
			result = result.add(or);
		}
		return result;
	}
	
	protected static Criteria addParamCount (Criteria criteria, Class clazz, String pName, Integer pValue) {
		Criteria result = criteria;
		if (pValue != null) {//count
			result = result.add(Restrictions.eq(pName, pValue));
		}
		return result;
	}
	
	protected static Criteria addParamDate (Criteria criteria, Class clazz, String pName, Date pValue) {
		Criteria result = criteria;
		if (pValue != null) {//date
			result = result.add(Restrictions.eq(pName, pValue));
		}
		return result;
	}
	
	protected static Criteria addParamEnum (Criteria criteria, Class clazz, String pName, Enum pValue) {
		Criteria result = criteria;
		if (pValue != null) {//enum
			result = result.add(Restrictions.eq(pName, pValue));
		}
		return result;
	}
	
	protected static Criteria addParamFile (Criteria criteria, Class clazz, String pName, File pValue) {
		Criteria result = criteria;
		if (pValue != null) {//file
//			result = result.add(Restrictions.eq(pName, pValue));
		}
		return result;
	}

	protected static Criteria addParamList (Criteria criteria, Class clazz, String pName, Collection pValue) {
		Criteria result = criteria;
		if (pValue != null && pValue.size() > 0) {//ref
			result = result.add(Restrictions.in(pName, pValue));
		}
		return result;
	}
	
	protected static Criteria addParamNumber (Criteria criteria, Class clazz, String pName, Number pValue) {
		Criteria result = criteria;
		if (pValue != null) {//number
			result = result.add(Restrictions.eq(pName, pValue));
		}
		return result;
	}
	
	protected static Criteria addParamRef (Criteria criteria, Class clazz, String pName, Object pValue) {
		Criteria result = criteria;
		if (pValue != null) {//ref
			result = result.add(Restrictions.eq(pName, pValue));
		}
		return result;
	}
	
}
