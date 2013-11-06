package org.effortless.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.effortless.core.MethodUtils;
import org.effortless.core.StringUtils;
import org.effortless.model.Filter;
import org.effortless.server.ServerContext;
import org.effortless.server.WebUtils;
import org.effortless.ui.impl.Bindings;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.widgets.Field;
import org.effortless.ui.windows.FieldWindow;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;

public class UiApplication {

	public static String getAppAlias () {
		String result = null;
		Execution exec = Executions.getCurrent();
		Desktop desktop = (exec != null ? exec.getDesktop() : null);
		result = (desktop != null ? desktop.getRequestPath() : null);
		result = (result.startsWith(File.separator) ? result.substring(1) : result);
		return result;
	}
	
	public static String getAppPackageName () {
		String result = null;
		String appName = getAppAlias();
		result = (appName != null ? WebUtils.appId(appName, ServerContext.getRootContext()) : null);
		return result;
	}

	public static String getFullClassName (String name) {
		String result = null;
		String packageName = getAppPackageName();
		String capName = StringUtils.capFirst(name);
		result = StringUtils.concat(new String[] {packageName,  capName}, ".");
		return result;
	}
	
	public static Class<?> getEntityClass (String name) {
		Class<?> result = null;
		String fullClassName = getFullClassName(name);
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			result = classLoader.loadClass(fullClassName);
		}
		catch (Throwable t) {
			try {
				result = Class.forName(fullClassName);
			}
			catch (Throwable t2) {
				throw new UiException(t2);
			}
		}
		
		return result;
	}

	public static String getEditorZul (Class<?> clazz) {
		String result = null;
		if (clazz != null) {
			String resourcesDir = getResourcesDir();
//			String name = StringUtils.uncapFirst(clazz.getSimpleName());
			String name = clazz.getSimpleName();
			result = resourcesDir + name + CteUi.SUFFIX_EDITOR_ZUL + CteUi.EXT_ZUL;
		}
		return result;
	}
	
	public static String getFinderZul (Class<?> clazz) {
		String result = null;
		if (clazz != null) {
			String resourcesDir = getResourcesDir();
//			String name = StringUtils.uncapFirst(clazz.getSimpleName());
			String name = clazz.getSimpleName();
			result = resourcesDir + name + CteUi.SUFFIX_FINDER_ZUL + CteUi.EXT_ZUL;
		}
		return result;
	}
	
	public static String getFinderFilterZul(Class<?> clazz) {
		String result = null;
		if (clazz != null) {
			String resourcesDir = getResourcesDir();
//			String name = StringUtils.uncapFirst(clazz.getSimpleName());
			String name = clazz.getSimpleName();
			result = resourcesDir + name + CteUi.SUFFIX_FINDERFILTER_ZUL + CteUi.EXT_ZUL;
		}
		return result;
	}
	
	public static Class<?> getFinderFilterClass (String name) {
		Class<?> result = null;
		if (name != null) {
			name = name + CteUi.SUFFIX_FINDER_FILTER;
			result = getEntityClass(name);
		}
		return result;
	}
	
	
	public static Object newItem (Class<?> clazz) {
		Object result = null;
		if (clazz != null) {
			try {
				result = clazz.newInstance();
			} catch (InstantiationException e) {
				throw new UiException(e);
			} catch (IllegalAccessException e) {
				throw new UiException(e);
			}
		}
		return result;
	}

	public static Object loadCurrentSingleton (Class<?> clazz) {
		Object result = null;
		if (clazz != null) {
			result = MethodUtils.runStatic(clazz, "getCurrent", null);
		}
		return result;
	}
	
//	String zul = "/org.effortless.icondb/resources/";
	public static String getResourcesDir () {
		String result = null;
		String packageName = getAppPackageName();
		String sep = File.separator;
		result = sep + packageName + sep + "resources" + sep;
		return result;
	}

	public static String getMoreInfoZul(Class clazz) {
		String result = null;
		if (clazz != null) {
			String resourcesDir = getResourcesDir();
//			String name = StringUtils.uncapFirst(clazz.getSimpleName());
			String name = clazz.getSimpleName();
			result = resourcesDir + name + CteUi.SUFFIX_INFO_ZUL + CteUi.EXT_ZUL;
		}
		return result;
	}

	public static Class<?> getPropertyType (Component cmp, String propertyName) {
		Class<?> result = null;
		String getterName = "get" + StringUtils.capFirst(propertyName);
		Class<?> entity = loadEntityClass(cmp);
		result = (entity != null ? (Class<?>)MethodUtils.getReturnType(entity, getterName) : null);
		return result;
	}
	
	
	public static List<?> loadEnumDefaultValues (Component cmp, String propertyName) {
		List<?> result = null;
		Class<Enum> enumType = (Class<Enum>)getPropertyType(cmp, propertyName);
		EnumSet values = (enumType != null ? EnumSet.allOf(enumType) : null);
		if (values != null) {
			result = new ArrayList();
			result.addAll(values);
		}
		return result;
	}
	
	public static Class<?> loadEntityClass (Component cmp) {
		Class<?> result = null;
		FieldWindow wnd = getFieldWindow(cmp);
		result = (wnd != null ? (Class<?>)wnd.getAttribute(CteUi.ENTITY_CLASS) : null);
		return result;
	}

	public static String loadZul (Component cmp, String propertyName, String suffix) {
		String result = null;
		Class<?> clazz = getPropertyType(cmp, propertyName);
		if (clazz != null) {
			String resourcesDir = getResourcesDir();
//			String name = StringUtils.uncapFirst(clazz.getSimpleName());
			String name = clazz.getSimpleName();
			result = resourcesDir + name + suffix + CteUi.EXT_ZUL;
		}
		return result;
	}
	
	
	public static String loadFinderFilterZul (Component cmp, String propertyName) {
		return loadZul(cmp, propertyName, CteUi.SUFFIX_FINDER_FILTER);
	}

	public static String loadEditorZul (Component cmp, String propertyName) {
		return loadZul(cmp, propertyName, CteUi.SUFFIX_EDITOR_ZUL);
	}

	public static String loadFinderZul (Component cmp, String propertyName) {
		return loadZul(cmp, propertyName, CteUi.SUFFIX_FINDER_ZUL);
	}
	
	public static Class<?> getFinderFilterClass (Component cmp, String propertyName) {
		Class<?> result = null;
		Class<?> clazz = getPropertyType(cmp, propertyName);
		result = (clazz != null ? getFinderFilterClass(clazz.getSimpleName()) : null);
		return result;
	}

	public static Class<?> getFieldClass (Field cmp) {
		Class<?> result = null;
		String propertyName = Bindings._getBindProperty(cmp, "value");
		result = getPropertyType(cmp, propertyName);
		return result;
	}

	
	public static FieldWindow getFieldWindow (Component cmp) {
		FieldWindow result = null;
		if (cmp != null) {
			do {
				cmp = cmp.getParent();
				try {
					result = (FieldWindow)cmp;
					break;
				}
				catch (ClassCastException e) {
				}
			} while (cmp != null);
		}
		return result;
	}
	
	public static List<?> loadRefDefaultValues (Component cmp, String propertyName) {
		List<?> result = null;
		Class<?> clazz = (Class<?>)getFinderFilterClass(cmp, propertyName);
		Filter filter = (Filter)UiApplication.newItem(clazz);
		if (filter != null) {
			filter.setPaginated(Boolean.TRUE);
			filter.setPageIndex(Integer.valueOf(0));
//			filter.setPageSize(Integer.valueOf(2));
			result = filter;
		}
		return result;
	}
	
	
	
}
