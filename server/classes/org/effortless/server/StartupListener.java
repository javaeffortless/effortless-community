package org.effortless.server;

import groovy.lang.Binding;

import groovy.lang.GroovyClassLoader;
//import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.effortless.core.FilenameUtils;
//import org.effortless.model.StartupDb;
//import org.effortless.transform.MainTransform;

import java.io.*;

public class StartupListener extends Object implements ServletContextListener {

	public StartupListener () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("contextDestroyed");
		ServletContext ctx = event.getServletContext();
//		GroovyScriptEngine gse = (GroovyScriptEngine)ctx.getAttribute(GROOVY_ENGINE);
		ScriptEngine gse = (ScriptEngine)ctx.getAttribute(ServerContext.GROOVY_ENGINE);
		System.out.println(" gse is not null? " + (gse != null));
		
		Binding binding = new Binding();
		binding.setVariable("event", event);
		
		try {
			gse.run("stop.gy", binding);
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	protected void initConfig (CompilerConfiguration config) {
////		config.addCompilationCustomizers(new ASTTransformationCustomizer(org.effortless.Entity.class));
//		org.effortless.transform.EntityASTTransformation transform = new org.effortless.transform.EntityASTTransformation();
//		config.addCompilationCustomizers(new ASTTransformationCustomizer(transform));
	}
	
	public void contextInitialized(ServletContextEvent event) {
//		ClassLoader parentClassLoader = startLibs(event);
		ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
		
//		org.apache.catalina.loader.WebappClassLoader		
		
		//parentClassLoader = null;
		
		ServletContext ctx = event.getServletContext();
		String realPath = ctx.getRealPath("/");
		
//		GroovyScriptEngine gse = null;
		ScriptEngine gse = null;
		try {
			if (parentClassLoader != null) {
//				gse = new GroovyScriptEngine(realPath, parentClassLoader);
				gse = new ScriptEngine(realPath, parentClassLoader);
			}
			else {
//				gse = new GroovyScriptEngine(realPath);
				gse = new ScriptEngine(realPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ctx.setAttribute(ServerContext.GROOVY_ENGINE, gse);

		Binding binding = new Binding();
		binding.setVariable("event", event);
		binding.setVariable("root", realPath);
		
		CompilerConfiguration config = gse.getConfig();
		initConfig(config);
		addDefaultImports(config);
		try {
			setupConfiguration(config, realPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GroovyClassLoader loader = gse.getGroovyClassLoader();
		loader.setShouldRecompile(Boolean.TRUE);
		loader.addClasspath(realPath);
//		loader.addClasspath("/media/jesus/64F27B47F27B1C8C/TRABAJO/runtime/apache-tomcat-7.0.40/webapps/ewp/org.effortless.lotogest");
		
		if (true) {
		Thread.currentThread().setContextClassLoader(loader);
		}
		
		if (true) {
		preStart(realPath, ctx);
		}

		if (true) {
		try {
			gse.run("start.gy", binding);
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		}
	}

	protected CompilationCustomizer addDefaultImports(CompilerConfiguration config) {
		ImportCustomizer result = null;
		if (config != null) {
			result = new ImportCustomizer();
			result.addImports("java.sql.Time", "java.sql.Timestamp");
			result.addStarImports("org.effortless");
			result.addStarImports("org.effortless.ann");
//			result.addStarImports("org.effortless.lotogest");
//			result.addImports("org.effortless.model.FileEntity", "org.effortless.model.LogData");
			if (result != null) {
				config.addCompilationCustomizers(result);
			}
		}
		return result;
	}

	protected void runFirstStart (String root, ServletContext ctx) {
	}
	
	protected void preStart (String root, ServletContext ctx) {
//		println "start from groovy (begin) " + new Date()
		//effortless.MySession.setServletContext(event.getServletContext())
		ServerContext.setServletContext(ctx);
		ServerContext.setRootContext(root);
		
		runFirstStart(root, ctx);

//		println "start from groovy (end) " + new Date();
	}
	
	
	public ClassLoader startLibs (ServletContextEvent event) {
		ClassLoader result = null;
		
		ServletContext ctx = event.getServletContext();
		String realPath = ctx.getRealPath("/");
		realPath = FilenameUtils.concat(realPath, "effortless/");
System.out.println(">>>>>>>>>> realPath effortless " + realPath);
//		GroovyScriptEngine gse = null;
		ScriptEngine gse = null;
		try {
//			gse = new GroovyScriptEngine(realPath);
			gse = new ScriptEngine(realPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Binding binding = new Binding();
		binding.setVariable("event", event);
		
		CompilerConfiguration config = gse.getConfig();
		try {
			setupConfiguration(config, realPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GroovyClassLoader loader = gse.getGroovyClassLoader();
		loader.addClasspath(realPath);
		
		try {
			gse.run("startlibs.gy", binding);
		} catch (ResourceException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
//		Thread.currentThread().setContextClassLoader(loader);
//		ctx.setAttribute(GROOVY_ENGINE, gse);
		
		result = loader;
		return result;
	}
	
	protected void setupConfiguration (CompilerConfiguration config, String rootScripts) throws Exception {
		config.setRecompileGroovySource(true);
		config.setTargetBytecode("1.7");
		config.setSourceEncoding("UTF-8");
		config.setDefaultScriptExtension("gy");
		config.setDebug(true);
		config.setClasspath(rootScripts);
		config.setVerbose(true);
	}

}
