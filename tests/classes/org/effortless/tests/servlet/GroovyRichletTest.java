package org.effortless.tests.servlet;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javassist.Modifier;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;

public class GroovyRichletTest extends GenericRichlet {

	public void service(Page page) throws Exception {
		
//		String requestPath = page.getRequestPath();
		String rootScripts = page.getDesktop().getWebApp().getServletContext().getRealPath("");
//		System.out.println(">>>>>>>>> requestPath = " + requestPath);
		String[] roots = new String[] { rootScripts };
		GroovyScriptEngine gse = new GroovyScriptEngine(roots);
		Binding binding = new Binding();
		binding.setVariable("page", page);
		
		CompilerConfiguration config = gse.getConfig();
		config.setRecompileGroovySource(true);
		config.setTargetBytecode("1.7");
		config.setSourceEncoding("UTF-8");
		config.setDefaultScriptExtension("groovy");
		config.setDebug(true);
		config.setClasspath(rootScripts);
		config.setVerbose(true);
		
		GroovyClassLoader loader = gse.getGroovyClassLoader();
		loader.addClasspath(rootScripts);
		Thread.currentThread().setContextClassLoader(loader);
		
		if (false) {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		URL[] urls = new URL[]{new java.io.File(rootScripts + "/demo").toURI().toURL()};
		URLClassLoader ucl = new URLClassLoader(urls, cl);
		GroovyClassLoader gcl = new GroovyClassLoader(ucl, gse.getConfig());
		Thread.currentThread().setContextClassLoader(gcl);
		
		if (false) {
		Class clazz = gcl.loadClass("demo.Book");
		Method mtd[] = clazz.getMethods();
		for (int i = 0; i < mtd.length ; i++) {
//			System.out.println("method = " + mtd[i].getName());
//			System.out.println("public = " + (Modifier.isPublic(mtd[i].getModifiers()) ? "Sí" : "NO"));
			System.out.println("tOString = " + mtd[i].toString());
		}
		System.out.println(clazz);
		}
		}
//		binding.setVariable("_gcl", gcl);
		
		gse.run("main.groovy", binding);
//		System.out.println(binding.getVariable("output"));		
	}

}
