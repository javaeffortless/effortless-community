package org.effortless.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodUtils {

	public static Class<?> getReturnType (Class<?> clazz, String methodName) {
		Class<?> result = null;
		try {
			Method method = clazz.getMethod(methodName, null);
			result = (method != null ? method.getReturnType() : null);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		} catch (SecurityException e) {
			throw new ModelException(e);
		}
//		Method method = org.apache.commons.beanutils.MethodUtils.getAccessibleMethod(clazz, methodName, (Class<?>)null);
		return result;
	}
	
	public static Object run (Object bean, String methodName, Object[] params) {
		Object result = null;
		try {
			result = org.apache.commons.beanutils.MethodUtils.invokeExactMethod(bean, methodName, params);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		} catch (InvocationTargetException e) {
			throw new ModelException(e);
		}
		return result;
	}
	
	public static Object[] EMPTY_PARAMS = new Object[]{};
	
	public static Object run (Object bean, String methodName) {
		return run(bean, methodName, EMPTY_PARAMS);
	}

	public static Object runStatic (Class<?> clazz, String methodName, Object[] params) {
		Object result = null;
		try {
			result = org.apache.commons.beanutils.MethodUtils.invokeStaticMethod(clazz, methodName, params);
		} catch (NoSuchMethodException e) {
			throw new ModelException(e);
		} catch (IllegalAccessException e) {
			throw new ModelException(e);
		} catch (InvocationTargetException e) {
			throw new ModelException(e);
		}
		return result;
	}

}
