package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class RefFields {

	public static void processField (ClassNode clazz, FieldNode field) {
		UserFields.alterField(clazz, field);
		UserFields.addInitiate(clazz, field);
		MethodNode getter = addGetter(clazz, field);
		UserFields.addSetter(clazz, field);
		addAnnotations(clazz, field, getter);
	}
	
	public static MethodNode addGetter (ClassNode clazz, FieldNode field) {
		MethodNode result = null;
		String methodName = BaseFields.getGetterName(field);
		
		ReturnStatement returnCode = new ReturnStatement(new FieldExpression(field));

		BlockStatement code = new BlockStatement();
//		code.addStatement(createPrintlnAst("reading " + field.getName()))
		code.addStatement(returnCode);
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, field.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
		result = method;
		return result;
	}
	

	
	//@ManyToOne() 
    //@JoinColumn(name="CUST_ID")
	public static void addAnnotations (ClassNode clazz, FieldNode field, MethodNode getter) {
//		UserFields.addLazyAnnotation(clazz, field, getter);
		
		AnnotationNode annManyToOne = new AnnotationNode(new ClassNode(javax.persistence.ManyToOne.class));
		annManyToOne.setMember("cascade", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.CascadeType.class)), new ConstantExpression("ALL")));
		annManyToOne.setMember("targetEntity", new ClassExpression(field.getType()));
		annManyToOne.setMember("fetch", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.FetchType.class)), new ConstantExpression("LAZY")));
		getter.addAnnotation(annManyToOne);

		String columnName = field.getName().toUpperCase() + "_ID";
		
		AnnotationNode annJoin = new AnnotationNode(new ClassNode(javax.persistence.JoinColumn.class));
		annJoin.setMember("name", new ConstantExpression(columnName));
		getter.addAnnotation(annJoin);
	}

}
