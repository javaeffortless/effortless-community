package org.effortless.server;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.effortless.core.StringUtils;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;

public class WebUtils {

	public static String appId (String request, String rootCtx) {
		String result = null;
		if (request != null) {
			request = (request.startsWith("/") ? request.substring(1) : request);
			result = StringUtils.partSplit(request, "/", 0);
		}
		result = ((result != null && rootCtx != null) ? transformAppId(result, rootCtx) : result);
		return result;
	}
	
	public static String transformAppId(String app, String rootCtx) {
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
	
    public static String toScreen (String screenId) {
    	String result = null;
		Desktop desktop = Executions.getCurrent().getDesktop();//(cmp != null ? cmp.getDesktop() : null);
		String appName = (desktop != null ? desktop.getRequestPath() : null);
		String appId = (appName != null ? WebUtils.appId(appName, ServerContext.getRootContext()) : null);
		result = File.separator + appId + File.separator + "resources" + File.separator + screenId.toLowerCase() + ".zul";
    	return result;
    }

    public static String toScreenTimestamp (String screenId) {
    	String result = null;
    	result = toScreen(screenId);
    	result += "?tms=" + System.currentTimeMillis();
    	return result;
    }

}
