package org.effortless.gen.impl;

//import org.codehaus.groovy.ast.ClassNode;
//import org.codehaus.groovy.ast.MethodNode;
//import org.codehaus.groovy.ast.Parameter;
//import org.codehaus.groovy.ast.expr.ArgumentListExpression;
//import org.codehaus.groovy.ast.expr.ClassExpression;
//import org.codehaus.groovy.ast.expr.Expression;
//import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
//import org.codehaus.groovy.ast.stmt.ReturnStatement;
//import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.model.Filter;
//import org.effortless.model.AbstractIdEntity;
//import org.effortless.model.Filter;
//import org.objectweb.asm.Opcodes;

public class EntityStaticMethodsTransform extends Object implements Transform<GClass> {

	public EntityStaticMethodsTransform () {
		super();
	}
	
	/**
	 * 
		public static Filter<AllBasicProperties> listBy () {
			return AbstractIdEntity.listBy(AllBasicProperties.class);
		}
	 * 
	 * @param clazz
	 * @param sourceUnit
	 */
	@Override
	public void process(GClass clazz) {
//		String methodName = "listBy";
//		
//		Expression arguments = new ArgumentListExpression(new Expression[] {new ClassExpression(clazz)});
//		StaticMethodCallExpression call = new StaticMethodCallExpression(new ClassNode(AbstractIdEntity.class), "listBy", arguments);
//		ReturnStatement returnCode = new ReturnStatement(call);
//		ClassNode returnType = new ClassNode(Filter.class);
////		returnType.setUsingGenerics(true);
////		returnType.setGenericsTypes(new GenericsType[] {new GenericsType(clazz)});
//		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, returnType, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, returnCode);
//
//		clazz.addMethod(method);
	}

//	public static org.effortless.model.Filter<org.effortless.model.AbstractIdEntity> listBy () {
//		
//		return AbstractIdEntity.listBy(AllBasicProperties.class);
//	}
	
}
