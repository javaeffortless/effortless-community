package org.effortless.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;


//import org.apache.commons.beanutils.PropertyUtils;

public class PropertyUtils {

	public static String[] getPropertyNames (Object target) {
		String[] result = null;
		PropertyDescriptor[] properties = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(target);
		int length = (properties != null ? properties.length : 0);
		if (length > 0) {
			result = new String[length];
			for (int i = 0; i < length; i++) {
				PropertyDescriptor propertyDescriptor = properties[i];
				String propertyName = propertyDescriptor.getName();
				result[i] = propertyName;
			}
		}
		return result;
	}

	public static boolean containsProperty (Object target, String propertyName) {
		boolean result = false;

		try {
			PropertyDescriptor property = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(target, propertyName);
			result = (property != null);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		} catch (InvocationTargetException e) {
			throw new ModelException(e);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		}

		return result;
	}
	
	
	public static void setProperty (Object target, String propertyName, Object propertyValue) {
		try {
			org.apache.commons.beanutils.PropertyUtils.setProperty(target, propertyName, propertyValue);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		} catch (InvocationTargetException e) {
			throw new ModelException(e);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		}
	}

	public static Object getProperty (Object source, String propertyName) {
		return getProperty(source, propertyName, null);
	}
	
	public static Object getProperty (Object source, String propertyName, Object defaultValue) {
		Object result = defaultValue;
		try {
			result = org.apache.commons.beanutils.PropertyUtils.getProperty(source, propertyName);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		} catch (InvocationTargetException e) {
			throw new ModelException(e);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		}
		return result;
	}
	
	public static void copyProperties (Object target, Object source) {
		try {
			org.apache.commons.beanutils.PropertyUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		} catch (InvocationTargetException e) {
			throw new ModelException(e);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		}
	}
	
}
