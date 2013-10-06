package org.effortless.server;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.effortless.core.GlobalContext;
import org.zkoss.zk.ui.Session;

public class ServerContext extends Object {

	public static javax.servlet.ServletContext getServletContext () {
		return GlobalContext.get(GlobalContext.SERVLET_CONTEXT_ATTR, javax.servlet.ServletContext.class, null);
	}
	
	public static void setServletContext (javax.servlet.ServletContext newValue) {
		GlobalContext.set(GlobalContext.SERVLET_CONTEXT_ATTR, newValue);
	}
	
	public static HttpServletRequest getHttpRequest () {
		return GlobalContext.get(GlobalContext.HTTP_REQUEST, HttpServletRequest.class, null);
	}
	
	public static void setHttpRequest (HttpServletRequest newValue) {
		GlobalContext.set(GlobalContext.HTTP_REQUEST, newValue);
	}
	
	public static HttpServletResponse getHttpResponse () {
		return GlobalContext.get(GlobalContext.HTTP_RESPONSE, HttpServletResponse.class, null);
	}
	
	public static void setHttpResponse (HttpServletResponse newValue) {
		GlobalContext.set(GlobalContext.HTTP_RESPONSE, newValue);
	}
	
	public static Session getHttpSession () {
		return GlobalContext.get(GlobalContext.HTTP_SESSION, Session.class, null);
	}
	
	public static void setHttpSession (Session newValue) {
		GlobalContext.set(GlobalContext.HTTP_SESSION, newValue);
	}
	
	
	
	
	public static final String GROOVY_ENGINE = "groovy.gse";

	public static void addImportPackage (String pkgName) {
		if (pkgName != null) {
			ImportCustomizer imports = getImportCustomizer();
			if (imports != null) {
				imports.addStarImports(pkgName);
			}
		}
	}
	
	protected static ImportCustomizer getImportCustomizer () {
		ImportCustomizer result = null;
		javax.servlet.ServletContext ctx = getServletContext();
		ScriptEngine gse = (ctx != null ? (ScriptEngine)ctx.getAttribute(GROOVY_ENGINE) : null);
		CompilerConfiguration config = (gse != null ? gse.getConfig() : null);
		java.util.List<CompilationCustomizer> list = (config != null ? config.getCompilationCustomizers() : null);
		if (list != null) {
			for (CompilationCustomizer custom : list) {
				try {
					result = (ImportCustomizer)custom;
					break;
				}
				catch (ClassCastException e) {
				}
			}
		}
		return result;
	}
	
	
	public static String getRootContext () {
		String result = null;
//		javax.servlet.ServletContext servletContext = getServletContext()
//		result = servletContext.getRealPath("/");
		result = GlobalContext.get(GlobalContext.ROOT_CONTEXT, String.class, null);
		result = (result != null ? result : getDefaultRootContext());
		return result;
	}
	
	public static String getDefaultRootContext () {
		String result = null;
		result = System.getProperty("user.dir");
		result = (result != null ? result : System.getProperty("user.home"));
		return result;
	}
	
	public static void setRootContext (String newValue) {
		GlobalContext.set(GlobalContext.ROOT_CONTEXT, newValue);
	}
	
//	public static I18nProperties getI18n () {
//		I18nProperties result = null;
//		String appId = "";//WebUtils.appId(appName, ServerContext.getRootContext());
//		Map<String, I18nProperties> container = GlobalContext.getCache(I18nProperties.class, "i18n_container");
//		I18nProperties i18n = container.get(appId);
//		i18n = (i18n != null ? i18n : I18nProperties.dev(appId));
//		container.put(appId, i18n);
//		return result;
//	}
//	
//	public static String i18n (String key) {
//		String result = null;
//		I18nProperties i18n = getI18n();
//		result = i18n.getKey(key);
//		return result;
//	}
//
	public static void addMainPackageFile(File file) {
		if (file != null) {
			String rootCtx = getRootContext();
			String path = file.getAbsolutePath();
			if (path.startsWith(rootCtx)) {
				String pkgName = path.substring(rootCtx.length());
				int idx = pkgName.indexOf(File.separator);
				pkgName = (idx > -1 ? pkgName.substring(0, idx) : pkgName);
				addImportPackage(pkgName);
			}
		}
	}

	
	public static String getAppId () {
		return GlobalContext.get(GlobalContext.APP_ID, String.class, null);
	}
	
	public static void setAppId (String newValue) {
		GlobalContext.set(GlobalContext.APP_ID, newValue);
	}
	
}
