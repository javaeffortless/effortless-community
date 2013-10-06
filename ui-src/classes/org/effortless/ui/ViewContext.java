package org.effortless.ui;

import java.util.Map;

import org.effortless.core.GlobalContext;
import org.effortless.core.I18nProperties;
import org.effortless.server.ServerContext;
import org.effortless.server.WebUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class ViewContext {

//	protected static final String CONTEXT_NAME = "VIEW_CONTEXT";
	
	public static void set (String key, Object value) {
//		Map<String, Object> ctx = GlobalContext.getCache(Object.class, CONTEXT_NAME);
		Session session = Sessions.getCurrent();
		session.setAttribute(key, value);
//		ctx.put(key, value);
	}

	public static Object get (String key) {
		Object result = null;
		Session session = Sessions.getCurrent();
		result = session.getAttribute(key);
//		Map<String, Object> ctx = GlobalContext.getCache(Object.class, CONTEXT_NAME);
//		result = ctx.get(key);
		return result;
	}
	
	protected static final String MAIN_WINDOW = "main_window";
	protected static final String MAIN_CTRL = "main_ctrl";

	public static Component getMainWindow () {
		Component result = null;
		result = (Component)ViewContext.get(MAIN_WINDOW);
		return result;
	}
	
	public static void setMainWindow (Component newValue) {
		ViewContext.set(MAIN_WINDOW, newValue);
	}
	
	public static org.zkoss.zk.ui.event.EventListener getMainCtrl () {
		org.zkoss.zk.ui.event.EventListener result = null;
		result = (org.zkoss.zk.ui.event.EventListener)ViewContext.get(MAIN_CTRL);
		return result;
	}
	
	public static void setMainCtrl (org.zkoss.zk.ui.event.EventListener newValue) {
		ViewContext.set(MAIN_CTRL, newValue);
	}

	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	protected I18nProperties _i18n;

	public static I18nProperties getI18n () {
		I18nProperties result = null;
		result = (I18nProperties)ViewContext.get(GlobalContext.I18N);
		if (result == null) {
	    	Desktop desktop = Executions.getCurrent().getDesktop();
			String appName = desktop.getRequestPath();
	
			if (appName != null) {
				String appId = WebUtils.appId(appName, ServerContext.getRootContext());
				result = I18nProperties.dev(appId);
				setI18n(result);
			}
		}
		return result;
	}
	
	public static void setI18n (I18nProperties newValue) {
		ViewContext.set(GlobalContext.I18N, newValue);
	}
	
	
    public static String i18n(String key, Object[] params) {
    	String result = null;
    	result = getI18n().resolve(key, params);
    	return result;
    }
	
    public static String i18n(String key) {
    	String result = null;

//    	String nkey = this.i18n + "_" + key;
    	result = (String)getI18n().get(key);
    	
    	return result;
    }

    public static String i18n(String key, boolean save, String defaultKey) {
    	String result = null;
//    	String nkey = this.i18n + "_" + key;
    	I18nProperties i18n = getI18n();
    	boolean firstSave = (defaultKey == null);
    	result = (String)i18n.getKey(key, firstSave);
    	if (result == null && defaultKey != null) {
        	result = (String)i18n.getKey(defaultKey, save);
    	}
    	
    	return result;
    }

    public static String i18n(String key, boolean save) {
    	String result = null;
//    	String nkey = this.i18n + "_" + key;
    	result = (String)getI18n().getKey(key, save);
    	return result;
    }
	
}
