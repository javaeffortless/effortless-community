package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.ann.NoLog;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;
import org.effortless.gen.GenContext;
import org.effortless.model.LogData;
import org.objectweb.asm.Opcodes;

public class EntityLogTransform extends Object implements Transform<GClass> {

	public static final ClassNode NO_LOG_CLAZZ = ClassNodeHelper.toClassNode(NoLog.class);
	
	@Override
	public void process(GClass clazz) {
		if (true && !clazz.hasAnnotation(NO_LOG_CLAZZ)) {
			GApplication app = clazz.getApplication();
			GClass logClass = app.getLogClass();
			if (logClass == null) {
				CreateLogEntityTransform creator = new CreateLogEntityTransform();
				creator.process(clazz);
				logClass = creator.getResult();
				app.setLogClass(logClass);
			}
			if (logClass != null) {
				addMethodNewLogData(clazz, logClass);
			}
		}
		else {
			disableLog(clazz);
			System.out.println("NO LOG for " + clazz.getName());
		}
	}

	/**
	 * 
	@Override
	public boolean doCheckLog() {
		return false;
	}
	 */
	protected GMethod disableLog(GClass clazz) {
		GMethod result = null;
//		MethodNode result = null;
//		ReturnStatement code = new ReturnStatement(ConstantExpression.PRIM_FALSE);
//		result = new MethodNode("doCheckLog", Opcodes.ACC_PUBLIC, ClassHelper.boolean_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
//		clazz.addMethod(result);
		
		result = clazz.addMethod("doCheckLog").setPublic(true).setReturnType(ClassHelper.boolean_TYPE);
		result.addReturn(result.cteFalse());
		
		return result;
	}

	/*
	protected LogData _newInstanceLogData () {
		return new LogData();
	}
	 */
	protected GMethod addMethodNewLogData(GClass clazz, GClass logClazz) {
		GMethod result = null;
		result = clazz.addMethod("_newInstanceLogData").setProtected(true).setReturnType(LogData.class);
		result.addReturn(result.callConstructor(logClazz.getClassNode()));
		return result;
	}

}
