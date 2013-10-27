package org.effortless.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.effortless.core.GlobalContext;
import org.effortless.gen.impl.HibernateEntityClassTransform;
import org.effortless.gen.impl.SamePackageClassTransform;
import org.effortless.gen.impl.SamePackageModuleTransform;
import org.effortless.gen.impl.UpdateDbClassTransform;
import org.effortless.gen.ui.EditorVMTransform;
import org.effortless.gen.ui.FinderVMTransform;
import org.effortless.server.ServerContext;

public class GenContext {

	protected static final String CONTEXT_NAME = "GEN_CONTEXT";
	
	public static void set (String key, Object value) {
		Map<String, Object> ctx = GlobalContext.getCache(Object.class, CONTEXT_NAME);
		ctx.put(key, value);
	}

	public static Object get (String key) {
		Object result = null;
		Map<String, Object> ctx = GlobalContext.getCache(Object.class, CONTEXT_NAME);
		result = ctx.get(key);
		return result;
	}
	
	protected static final String APP_ID = "app_id";
	
	public static String getAppId () {
		String result = null;
		result = GlobalContext.get(APP_ID, String.class, null);
		return result;
	}
	
	public static void setAppId(String newValue) {
		GlobalContext.set(APP_ID, newValue);
	}

	protected static final String APP_CONTEXT = "app_context";
	
	public static String getAppContext () {
		String result = null;
		result = GlobalContext.get(APP_CONTEXT, String.class, null);
		return result;
	}
	
	public static void setAppContext(String newValue) {
		GlobalContext.set(APP_CONTEXT, newValue);
	}

	protected static final String RESOURCES_CONTEXT = "resources_context";
	
	public static String getResourcesContext() {
		String result = null;
		result = GlobalContext.get(RESOURCES_CONTEXT, String.class, null);
		return result;
	}
	
	public static void setResourcesContext(String newValue) {
		GlobalContext.set(RESOURCES_CONTEXT, newValue);
	}

	public static GApplication getApplication (String appId) {
		return getApplication(appId, true);
	}

	public static GApplication getApplication (String appId, boolean create) {
		GApplication result = null;
		if (appId != null) {
			ServletContext servletContext = ServerContext.getServletContext();
			Map<String, GApplication> apps = (servletContext != null ? (Map)servletContext.getAttribute("apps") : null);
			result = (apps != null ? apps.get(appId) : null);
			if (result == null && create) {
				result = new GApplication(appId);
				if (apps == null) {
					apps = new HashMap<String, GApplication>();
					servletContext.setAttribute("apps", apps);
				}
				apps.put(appId, result);
			}
		}
		return result;
	}

	public static String toAppId (File file) {
		String result = null;
		if (file != null) {
			String rootCtx = ServerContext.getRootContext();
			String path = file.getAbsolutePath();
			if (path.startsWith(rootCtx)) {
				result = path.substring(rootCtx.length());
				int idx = result.indexOf(File.separator);
				result = (idx < 0 ? result : result.substring(0, idx));
			}
		}
		return result;
	}

	public static final String MODULE_TRANSFORMS = "MODULE_TRANSFORMS";

	public static List<Transform<GModule>> getModuleTransforms () {
		List<Transform<GModule>> result = null;
		result = (List<Transform<GModule>>)GlobalContext.get(MODULE_TRANSFORMS);
		if (result == null) {
			result = new ArrayList<Transform<GModule>>();
			result.add(new SamePackageModuleTransform());
			GlobalContext.set(MODULE_TRANSFORMS, result);
		}
		return result;
	}
	
	public static final String CLASS_TRANSFORMS = "CLASS_TRANSFORMS";
	
	public static List<Transform> getClassTransforms () {
		List<Transform> result = null;
		result = (List<Transform>)GlobalContext.get(CLASS_TRANSFORMS);
		if (result == null) {
			result = new ArrayList<Transform>();
			result.add(new SamePackageClassTransform());
			result.add(new HibernateEntityClassTransform());
			result.add(new FinderVMTransform());
			result.add(new EditorVMTransform());
			result.add(new UpdateDbClassTransform());
			GlobalContext.set(CLASS_TRANSFORMS, result);
		}
		return result;
	}

}
