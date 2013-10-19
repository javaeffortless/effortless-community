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
import org.effortless.gen.ClassGen;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GenContext;
import org.effortless.model.LogData;
import org.objectweb.asm.Opcodes;

public class EntityLogClassTransform extends Object implements ClassTransform {

	public static final ClassNode NO_LOG_CLAZZ = ClassNodeHelper.toClassNode(NoLog.class);
	
	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		List<AnnotationNode> annotationsNoLog = clazz.getAnnotations(NO_LOG_CLAZZ);
		if (false && !(annotationsNoLog != null && annotationsNoLog.size() > 0)) {
			String keyLogData = clazz.getName() + "." + LogData.KEY_CLASS_NEEDS;
			GenContext.set(keyLogData, Boolean.TRUE);
			ClassNode logClass = tryNeedsLogEntity(clazz, sourceUnit);
			if (logClass != null) {
				addMethodNewLogData(clazz, sourceUnit, logClass);
			}
		}
		else {
			disableLog(clazz, sourceUnit);
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
	protected static MethodNode disableLog(ClassNode clazz, SourceUnit sourceUnit) {
		MethodNode result = null;
		ReturnStatement code = new ReturnStatement(ConstantExpression.PRIM_FALSE);
		result = new MethodNode("doCheckLog", Opcodes.ACC_PUBLIC, ClassHelper.boolean_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(result);
		return result;
	}

	public static ClassNode tryNeedsLogEntity(ClassNode clazz, SourceUnit sourceUnit) {
		return ClassGen.tryNeedsNewExternalEntity(clazz, sourceUnit, LogData.class, LogData.KEY_CLASS_NEEDS, LogData.KEY_APP_NEEDS, null);
	}

		/*
		protected LogData _newInstanceLogData () {
			return new LogData();
		}
	* 
	*/
	protected static MethodNode addMethodNewLogData(ClassNode clazz,
		SourceUnit sourceUnit, ClassNode logClazz) {
		MethodNode result = null;
		
		ConstructorCallExpression newInstance = new ConstructorCallExpression(logClazz, new ArgumentListExpression());
		ReturnStatement code = new ReturnStatement(newInstance);
		ClassNode logClassNode = new ClassNode(LogData.class);
		
		result = new MethodNode("_newInstanceLogData", Opcodes.ACC_PROTECTED, logClassNode, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(result);
		return result;
	}

}
