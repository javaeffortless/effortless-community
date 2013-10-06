package org.effortless.ui.config;

import java.io.File;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.effortless.core.StringUtils;
import org.effortless.server.ScriptEngine;
import org.effortless.server.ServerContext;
import org.effortless.server.StartupListener;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuService;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Session;

public class MySessionDesktop implements AuService, Serializable {
	
	public MySessionDesktop() {
	}
	
	public boolean service(AuRequest request, boolean everError) {
		
		if (true) {
			Desktop desktop = request.getDesktop();
			Session sess = desktop.getSession();
			
//			String requestPath = (desktop != null ? desktop.getRequestPath() : null);

			ServletContext servletContext = ((HttpSession)sess.getNativeSession()).getServletContext();
			String realPath = servletContext.getRealPath("/");
			
			ScriptEngine gse = (ScriptEngine)servletContext.getAttribute(ServerContext.GROOVY_ENGINE);

			ClassLoader classLoader = (gse != null ? gse.getGroovyClassLoader() : null);
			Thread.currentThread().setContextClassLoader(classLoader);

//			String appId = appId(requestPath, realPath);

			ServerContext.setRootContext(realPath);
			ServerContext.setServletContext(servletContext);
			
//			if (appId != null) {
//				String resourcesContext = appId + File.separator + "resources";
//				MySession.setResourcesContext(resourcesContext);
//					
//				String appContext = realPath + appId;
//				MySession.setAppId(appId);
//				MySession.setAppContext(appContext);
//			}
		}
		return false;
	}
	
	protected String appId (String request, String rootCtx) {
		String result = null;
		if (request != null) {
			request = (request.startsWith("/") ? request.substring(1) : request);
			result = StringUtils.partSplit(request, "/", 0);
		}
		result = ((result != null && rootCtx != null) ? transformAppId(result, rootCtx) : result);
		return result;
	}
	
	protected String transformAppId(String app, String rootCtx) {
		String result = null;
		
		String addrApp = rootCtx + app;
		File folderApp = new File(addrApp);
		if (!folderApp.exists()) {
			File rootFolder = new File(rootCtx);
			File[] files = rootFolder.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					String folderName = FilenameUtils.getName(file.getAbsolutePath());
					if (folderName.endsWith("." + app)) {
						result = folderName;
						break;
					}
				}
			}
		}
		else {
			result = app;
		}
		
		// TODO Auto-generated method stub
		return result;
	}
	
}
