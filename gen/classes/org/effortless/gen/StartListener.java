package org.effortless.gen;

import javax.servlet.ServletContext;

import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.effortless.model.StartupDb;
import org.effortless.server.StartupListener;

public class StartListener extends StartupListener {

	protected void initConfig (CompilerConfiguration config) {
//	config.addCompilationCustomizers(new ASTTransformationCustomizer(org.effortless.Entity.class));
	org.effortless.gen.classes.EntityASTTransformation transform = new org.effortless.gen.classes.EntityASTTransformation();
//	config.addCompilationCustomizers(new ASTTransformationCustomizer(transform));
	config.addCompilationCustomizers(new CodeCustomizer(transform));
}
	
	protected void runFirstStart (String root, ServletContext ctx) {
//	println "BEGIN PRECOMPILE EFFORTLESS"
//	effortless.util.Compiler.precompile(root + "/effortless")
//		ppTransform appTransform = GenContext.getAppTransform(appId);
	org.effortless.server.Compiler.precompile(root);
//	println "END PRECOMPILE EFFORTLESS"
	
//	println "BEGIN PRECOMPILE ORG"
//	effortless.MySession.setServletContext(event.getServletContext())
//	effortless.MySession.setRootContext(root)
//	println "PRECOMPILE ROOT CTX ROOT CTX = " + effortless.MySession.getRootContext()
//	println " start thread id = " + Thread.currentThread().getName()
//	effortless.util.Compiler.precompile(root + "/org")
//	println "AFTER PRECOMPILE ROOT CTX ROOT CTX = " + effortless.MySession.getRootContext()
//	println "END PRECOMPILE ORG"

//	println "BEGIN RUN ALL DB"
//	println "ROOT CTX ROOT CTX = " + effortless.MySession.getRootContext()
	
	MainTransform.process();
	
	StartupDb.runAllDb();
//	println "END RUN ALL DB"
	AutoRefresh auto = new AutoRefresh();
	auto.setup(root, true, root, ctx);
	auto.start();
//	org.effortless.server.WatchDir.start(root, true, root, ctx);
}
	
}
