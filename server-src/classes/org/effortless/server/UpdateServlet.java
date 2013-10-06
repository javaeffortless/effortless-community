package org.effortless.server;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.effortless.core.GlobalContext;
import org.zkoss.lang.ClassResolver;
import org.zkoss.zk.au.AuDecoder;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.sys.WebAppCtrl;

public class UpdateServlet extends DHtmlUpdateServlet {

	public void process(Session sess, HttpServletRequest request, HttpServletResponse response, boolean flag) throws ServletException, IOException {
		initSession(sess, request, response, flag);
		super.process(sess, request, response, flag);
	}
	
	protected void initSession (Session sess, HttpServletRequest request, HttpServletResponse response, boolean flag) throws ServletException, IOException {
		
		//parse desktop ID
//		final WebApp wapp = sess.getWebApp();
//		final WebAppCtrl wappc = (WebAppCtrl)wapp;
//		AuDecoder audec = (wappc != null ? wappc.getAuDecoder(): null);
		
//		final String dtid = audec.getDesktopId(request);
//		String dtid = ((HttpServletRequest)request).getParameter("dtid");
//		Desktop desktop = (dtid != null ? getDesktop(sess, dtid) : null);

//		Page page = null;
//		try {
//			page = Executions.getCurrent().getDesktop().getFirstPage();
//		}
//		catch (Throwable t) {
//			t.printStackTrace();
//		}
//		String requestPath = (desktop != null ? desktop.getRequestPath() : request.getServletPath());

		ServletContext servletContext = ((HttpSession)sess.getNativeSession()).getServletContext();
		String realPath = servletContext.getRealPath("/");
		
		ScriptEngine gse = (ScriptEngine)servletContext.getAttribute(ServerContext.GROOVY_ENGINE);

		ClassLoader classLoader = (gse != null ? gse.getGroovyClassLoader() : null);
		Thread.currentThread().setContextClassLoader(classLoader);

//		String appId = appId(requestPath, realPath);

		
		
		String ctxPath = request.getContextPath();//"/ewp"
//		String pathInfo = request.getPathInfo();
		String uri = request.getRequestURI();//"/ewp/zkau"
		String sessionId = request.getRequestedSessionId();//"F2269B1C579D0FB6A019F4F548A8E3C1"
//		Desktop desktop = null;
//		desktop = (desktop != null ? desktop : Executions.getCurrent().getDesktop());
//		String appName = (desktop != null ? desktop.getRequestPath() : null);
//		String appId = WebUtils.appId(appName, ServerContext.getRootContext());
//		ServerContext.setRootContext(realPath);
		
		
		String appId = (String)sess.getAttribute(GlobalContext.APP_ID);
		ServerContext.setAppId(appId);
		
		ServerContext.setRootContext(realPath);
		ServerContext.setServletContext(servletContext);
		ServerContext.setHttpRequest(request);
		ServerContext.setHttpResponse(response);
		ServerContext.setHttpSession(sess);
		
//		if (appId != null) {
//			String resourcesContext = appId + File.separator + "resources";
//			MySession.setResourcesContext(resourcesContext);
//				
//			String appContext = realPath + appId;
//			MySession.setAppId(appId);
//			MySession.setAppContext(appContext);
//		}
	}

}
