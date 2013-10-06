package org.effortless.gen.methods;

import java.io.Serializable;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class AutoSession {

	public static void generate (ClassNode clazz) {
		generatePersist(clazz);
		generateDelete(clazz);
		generateErase(clazz);
		generateInsert(clazz);
		generateCreate(clazz);
		generateUpdate(clazz);
		generateRefresh(clazz);
		generateMerge(clazz);
		generateLoad(clazz);
	}

	//public void persist () { MySession.persist(this) }
	public static void generatePersist (ClassNode clazz) {
		generateSingleMethod(clazz, "persist");
	}
	
	public static void generateDelete (ClassNode clazz) {
		generateSingleMethod(clazz, "delete");
	}
	
	public static void generateErase (ClassNode clazz) {
		generateSingleMethod(clazz, "erase");
	}
	
	public static void generateInsert (ClassNode clazz) {
		generateSingleMethod(clazz, "insert");
	}
	
	public static void generateCreate (ClassNode clazz) {
		generateSingleMethod(clazz, "create");
	}
	
	public static void generateUpdate (ClassNode clazz) {
		generateSingleMethod(clazz, "update");
	}

	public static void generateRefresh (ClassNode clazz) {
		generateSingleMethod(clazz, "refresh");
	}
	
	public static void generateMerge (ClassNode clazz) {
		generateSingleMethod(clazz, "merge");
	}
	
	public static void generateLoad (ClassNode clazz) {
		generateLoadMethod(clazz, "load");
		//return MySession.load(Persona.class, id)
	}


	//public void persist () { MySession.persist(this) }
	protected static void generateSingleMethod (ClassNode clazz, String methodName) {
		StaticMethodCallExpression call = new StaticMethodCallExpression(new ClassNode(org.effortless.model.SessionManager.class), methodName, new VariableExpression("this"));
		ExpressionStatement code = new ExpressionStatement(call);
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		method.setSynthetic(true);
		clazz.addMethod(method);
	}

	protected static void generateLoadMethod (ClassNode clazz, String methodName) {
//		VariableExpression getClass = new VariableExpression("this");
//		ConstantExpression cteExpr = new ConstantExpression("getClass")
//		MethodCallExpression callGetClass = new MethodCallExpression(getClass, cteExpr, new ArgumentListExpression([]))
//		ArgumentListExpression args = new ArgumentListExpression([callGetClass, new VariableExpression("id")])
		ArgumentListExpression args = new ArgumentListExpression(new Expression[] {new ClassExpression(clazz), new VariableExpression("id")});
		StaticMethodCallExpression callLoad = new StaticMethodCallExpression(new ClassNode(org.effortless.model.SessionManager.class), methodName, args);
		ReturnStatement code = new ReturnStatement(callLoad);
		Parameter[] parameters = new Parameter [] {new Parameter(new ClassNode(java.io.Serializable.class), "id")};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, clazz, parameters, ClassNode.EMPTY_ARRAY, code);
		method.setSynthetic(true);
		clazz.addMethod(method);
	}

}
