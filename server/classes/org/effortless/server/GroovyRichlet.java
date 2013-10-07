package org.effortless.server;

import java.io.FileInputStream;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.effortless.core.GlobalContext;
import org.effortless.core.StringUtils;
import org.effortless.server.binder.EffortlessELFactory;
import org.zkoss.lang.ClassResolver;
import org.zkoss.lang.ImportedClassResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Label;

import groovy.lang.Binding;

public class GroovyRichlet extends GenericRichlet {

	protected String part2nd (String request) {
		String result = null;
		if (request != null) {
			request = (request.startsWith("/") ? request.substring(1) : request);
			result = StringUtils.partSplit(request, "/", 1);
		}
		return result;
	}
	
	public void service(Page page) throws Exception {
		String requestPath = page.getRequestPath();
		String part2nd = part2nd(requestPath);

		ServletContext servletContext = page.getDesktop().getWebApp().getServletContext();
		String realPath = servletContext.getRealPath("/");
		
		if (!"resources".equals(part2nd)) {
//			String rootScripts = page.getDesktop().getWebApp().getServletContext().getRealPath("");
//			String[] roots = new String[] { rootScripts };
//			GroovyScriptEngine gse = new GroovyScriptEngine(roots);
//			
//			CompilerConfiguration config = gse.getConfig();
//			setupConfiguration(config, rootScripts);
//			
//			GroovyClassLoader loader = gse.getGroovyClassLoader();
//			loader.addClasspath(rootScripts);
//			Thread.currentThread().setContextClassLoader(loader);
			
//			setupSession(page);

//			GroovyScriptEngine gse = (GroovyScriptEngine)servletContext.getAttribute(StartupListener.GROOVY_ENGINE);
			ScriptEngine gse = (ScriptEngine)servletContext.getAttribute(ServerContext.GROOVY_ENGINE);

			ClassLoader classLoader = (gse != null ? gse.getGroovyClassLoader() : null);
			Thread.currentThread().setContextClassLoader(classLoader);
//			classLoader.loadClass("org.effortless.icondb.MainWindowVM");
			if (true) {
				
//				 ImportedClassResolver resolver = new ImportedClassResolver();
//				 resolver.addImportedClass("org.zkoss.lang.*");
//				 resolver.addImportedClass("org.zkoss.util.Maps");
//				 resolver.addImportedClass("org.effortless.icondb.MainWindowVM");
//				 Class<?> clazz = resolver.resolveClass("org.effortless.icondb.MainWindowVM");
//				 boolean c = clazz != null;
				
				ClassResolver classResolver = createClassResolver(gse);
				if (classResolver != null) {
					page.addClassResolver(classResolver);
				}
			}

			String appId = WebUtils.appId(requestPath, realPath);
			Session session = Sessions.getCurrent();
			GlobalContext.initSession();
			session.setAttribute(GlobalContext.APP_ID, appId);
			ServerContext.setAppId(appId);
			ServerContext.setRootContext(realPath);
			ServerContext.setServletContext(servletContext);
			
////			String resourcesContext = "resources" + File.separator + appId;// + File.separator + "resources";
//			String resourcesContext = appId + File.separator + "resources";
////			String resourcesContext = "resources";
//			MySession.setResourcesContext(resourcesContext);
			
			
//			String appContext = realPath + appId;
//			MySession.setAppId(appId);
//			MySession.setAppContext(appContext);
			
			
			//HACE FUNCIONAR LA VINCULACION CON FILTROS QUE TAMBIEN SON LISTADOS
//			org.zkoss.xel.Expressions.setExpressionFactoryClass(null);
			if (false) {
				org.zkoss.zk.ui.util.Configuration conf = page.getDesktop().getWebApp().getConfiguration();
				conf.setExpressionFactoryClass(EffortlessELFactory.class);
			}
			
			if (true) {
				if (appId != null) {
					Object currentUser = session.getAttribute(GlobalContext.CURRENT_USER);
					if (true || currentUser != null) {
						String zul = "/" + appId + "/resources/main/main.zul";
						Component root = org.zkoss.zk.ui.Executions.createComponents(zul, null, null);
						root.setPage(page);
					}
					else {
//						LoginWindow loginWindow = new LoginWindow();
						Label loginScreen = new Label();
						loginScreen.setValue("LOGIN SCREEN");
						loginScreen.setPage(page);
						session.setAttribute(GlobalContext.CURRENT_USER, "LOGIN");
					}
				}
				else if (false) {
					try {
						throw new Exception();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else {
				Binding binding = new Binding();
				binding.setVariable("page", page);
				binding.setVariable("app", appId);
//				binding.setVariable("appCtx", appContext);
				binding.setVariable("rootCtx", realPath);
				binding.setVariable("servletContext", servletContext);
				binding.setVariable("classLoader", classLoader);
				gse.run("main.gy", binding);
			}
		}
		else {
			Object nativeResponse = Executions.getCurrent().getNativeResponse();
			if (nativeResponse instanceof HttpServletResponse) {
				HttpServletResponse response = (HttpServletResponse)nativeResponse;
				String suffix = (requestPath.startsWith("/") ? requestPath.substring(1) : requestPath);
				String resFile = realPath + suffix;

				InputStream input = new FileInputStream(resFile);
				OutputStream output = response.getOutputStream();
				IOUtils.copyLarge(input, output);
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			}
		}
	}

	protected ClassResolver createClassResolver(ScriptEngine gse) {
		ClassResolver result = null;
		
		final ClassLoader classLoader = (gse != null ? gse.getGroovyClassLoader() : null);
		
		if (classLoader != null) {
			result = new ImportedClassResolver() {
	
				@Override
				public Class<?> resolveClass(String name) throws ClassNotFoundException {
					Class<?> result = null;
					result = classLoader.loadClass(name);
					return result;
				}
					
			};
//			try {
//				result.resolveClass("org.effortless.icondb.MainWindowVM");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		return result;
	}
	
//	protected void setupSession (Page page) throws Exception {
//		MySession.setEntityFactory(EntityFactory.getInstance());
//		MySession.setFilterFactory(FilterFactory.getInstance());
//	}
	
//	protected void setupConfiguration (CompilerConfiguration config, String rootScripts) throws Exception {
//		config.setRecompileGroovySource(true);
//		config.setTargetBytecode("1.7");
//		config.setSourceEncoding("UTF-8");
//		config.setDefaultScriptExtension("groovy");
//		config.setDebug(true);
//		config.setClasspath(rootScripts);
//		config.setVerbose(true);
//	}

}
