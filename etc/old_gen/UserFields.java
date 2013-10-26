package org.effortless.gen.fields;

import java.io.File;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class UserFields {

	public static void processField (ClassNode clazz, FieldNode field, SourceUnit sourceUnit) {
		ClassNode fieldClass = field.getType();
//		Class fieldClass = fieldClass.getPlainNodeReference().getTypeClass()
		if (fieldClass.isDerivedFrom(new ClassNode(String.class))) {
			TextFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(Date.class))) {
			DateFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(Boolean.class))) {
			BoolFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(Integer.class))) {
			CountFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(Double.class))) {
			NumberFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(Enum.class))) {
			EnumFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(File.class))) {
			FileFields.processField(clazz, field, sourceUnit);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(Collection.class))) {
			ListFields.processField(clazz, field);
		}
		else if (fieldClass.isDerivedFrom(new ClassNode(List.class))) {
			ListFields.processField(clazz, field);
		}
		else {
			RefFields.processField(clazz, field);
		}
	}

	public static void alterField (ClassNode clazz, FieldNode field) {
		field.setModifiers(Opcodes.ACC_PROTECTED);
	}

	/*
	 * 
	protected void initiateFichero() {
		this.fichero = null;
	}
	 * 
	 */
	public static MethodNode addInitiate (ClassNode clazz, FieldNode field) {
		MethodNode result = null;
		String methodName = BaseFields.getInitiateName(field);
		
		BinaryExpression assign = new BinaryExpression(new FieldExpression(field), Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
		BlockStatement code = new BlockStatement();
		code.addStatement(new ExpressionStatement(assign));
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		
		clazz.addMethod(method);
		result = method;
		return result;
	}
	
	
	public static MethodNode addGetter (ClassNode clazz, FieldNode field, boolean includeAnnotation) {
		MethodNode result = null;
		String methodName = BaseFields.getGetterName(field);
		
		ReturnStatement returnCode = new ReturnStatement(new FieldExpression(field));

		BlockStatement code = new BlockStatement();
//		code.addStatement(createPrintlnAst("reading " + field.getName()))
		code.addStatement(returnCode);
//		code.addStatement((Statement)(nodes.get(0)))
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, field.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
//		MethodNode method = new MethodNode(methodName, ACC_PUBLIC, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code)
		
//		AnnotationNode annColumn = new AnnotationNode(new ClassNode(javax.persistence.Column.class))
//		annColumn.setMember("name", new ConstantExpression(field.getName().toUpperCase()))
//		if (Restrictions.isSingleUnique(clazz, field)) {
//			annColumn.setMember("unique", new ConstantExpression(true))
//		}
//		if (Restrictions.isNotNull(clazz, field)) {
//			annColumn.setMember("nullable", new ConstantExpression(false))
//		}
//		method.addAnnotation(annColumn)
		
		if (includeAnnotation) {
		addColumnAnnotation(clazz, field, method);
		addBasicAnnotation(clazz, field, method);
		}
		
		clazz.addMethod(method);
		result = method;
		return result;
	}
	
	public static MethodNode addGetter (ClassNode clazz, FieldNode field) {
		return addGetter(clazz, field, true);
	}
	
	//@Column(name="column", unique=true, nullable=false)
	public static void addColumnAnnotation (ClassNode clazz, FieldNode field, MethodNode method) {
		AnnotationNode annColumn = new AnnotationNode(new ClassNode(javax.persistence.Column.class));

		annColumn.setMember("name", new ConstantExpression(field.getName().toUpperCase()));
		
		if (Restrictions.isSingleUnique(clazz, field)) {
			annColumn.setMember("unique", new ConstantExpression(true));
		}
		if (Restrictions.isNotNull(clazz, field)) {
			annColumn.setMember("nullable", new ConstantExpression(false));
		}
//		Class fieldClass = fieldClass.getPlainNodeReference().getTypeClass()
		if (field.getType().isDerivedFrom(new ClassNode(String.class))) {
			String fieldName = field.getName();
			int length = (checkCommentField(fieldName) ? 3072 : 255);
			annColumn.setMember("length", new ConstantExpression(Integer.valueOf(length)));
		}

		method.addAnnotation(annColumn);
	}
	
	protected static final String[] COMMENT_NAMES = {"comment", "comentario", "remark", "observacion", "annotation", "anotacion"};
	
	protected static boolean checkCommentField (String fieldName) {
		boolean result = false;
		if (fieldName != null) {
			fieldName = fieldName.trim().toLowerCase();
			for (String name : COMMENT_NAMES) {
				if (name != null && name.trim().toLowerCase().contains(fieldName)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
	public static void addBasicAnnotation (ClassNode clazz, FieldNode field, MethodNode method) {
		AnnotationNode ann = new AnnotationNode(new ClassNode(javax.persistence.Basic.class));

		ann.setMember("fetch", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.FetchType.class)), new ConstantExpression("EAGER")));
		
		method.addAnnotation(ann);
	}
	
	//@javax.persistence.Basic(fetch = javax.persistence.FetchType.LAZY)
	public static void addLazyAnnotation (ClassNode clazz, FieldNode field, MethodNode method) {
		AnnotationNode ann = new AnnotationNode(new ClassNode(javax.persistence.Basic.class));

		ann.setMember("fetch", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.FetchType.class)), new ConstantExpression("LAZY")));
		
		method.addAnnotation(ann);
	}
	
	public static void addSetter (ClassNode clazz, FieldNode field) {
//		FieldExpression left = new FieldExpression(field)
//		Token operator = Token.newSymbol(Types.ASSIGN, -1, -1)
//		VariableExpression right = new VariableExpression("newValue")
//		BinaryExpression assign = new BinaryExpression(left, operator, right)
//		ExpressionStatement assignCode = new ExpressionStatement(assign)
		

////		ExpressionStatement assignCode = new ExpressionStatement(BinaryExpression.newAssignmentExpression(new VariableExpression(field), new VariableExpression("newValue")))
//		BlockStatement code = new BlockStatement()
////		code.addStatement(createPrintlnAst("writing " + field.getName()))
//		code.addStatement(assignCode)
////		code.addStatement((Statement)(nodes.get(0)))
		
		
		Statement code = BaseFields.createCodeSetter(clazz, field);
		//String setterName = "set" + MetaClassHelper.capitalize(field.getName())
		
		String setterName = BaseFields.getSetterName(field);
		
		Parameter[] setterParameterTypes = new Parameter[] {new Parameter(field.getType(), "newValue")};
//		MethodNode method = new MethodNode(setterName, ACC_PUBLIC, ClassHelper.VOID_TYPE, {new Parameter(field.getType(), "newValue")}, ClassNode.EMPTY_ARRAY, code)
//		MethodNode method = new MethodNode(setterName, ACC_PUBLIC, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code)
		MethodNode method = new MethodNode(setterName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, setterParameterTypes, ClassNode.EMPTY_ARRAY, code);
//		method.setSynthetic(true);
		clazz.addMethod(method);
		
		if (false) {
		BaseFields.addMethodDoChangeField(clazz, field);
		}
	}
	

	public static Statement createPrintlnAst(String message) {
		return new ExpressionStatement(
			new MethodCallExpression(
				new VariableExpression("this"),
				new ConstantExpression("println"),
				new ArgumentListExpression(
					new ConstantExpression(message)
				)
			)
		);
	}

}
