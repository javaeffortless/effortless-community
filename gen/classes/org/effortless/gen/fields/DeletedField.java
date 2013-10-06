package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class DeletedField {

	public static void generate (ClassNode clazz) {
		clazz.addInterface(new ClassNode(org.effortless.model.MarkDeleted.class));
		
		String fieldName = "deleted";
		Class fieldType = Boolean.class;
		
		BaseFields.addField(clazz, fieldName, fieldType);
		BaseFields.addInitiateField(clazz, fieldName, fieldType);
		addGetterFieldDeleted(clazz, fieldName, fieldType);
		BaseFields.addSetterField(clazz, fieldName, fieldType);
	}

	public static void addGetterFieldDeleted(ClassNode clazz, String name, Class type) {
		FieldNode field = clazz.getField(name);

		String fieldName = name;
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String methodName = "get" + fieldName;
		
		ReturnStatement returnCode = new ReturnStatement(new FieldExpression(field));

		Statement code = returnCode;
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, new ClassNode(type), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
	
		AnnotationNode annBasic = new AnnotationNode(new ClassNode(javax.persistence.Basic.class));
		//annBasic.setMember("fetch", new ConstantExpression(javax.persistence.FetchType.EAGER));
		method.addAnnotation(annBasic);
		
		AnnotationNode annColumn = new AnnotationNode(new ClassNode(javax.persistence.Column.class));
		annColumn.setMember("name", new ConstantExpression(name.toUpperCase()));
		method.addAnnotation(annColumn);

		clazz.addMethod(method);
	}

}
