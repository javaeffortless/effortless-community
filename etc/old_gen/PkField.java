package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class PkField {

//	public static void addPrimitiveFields (ClassNode clazz) {
////		println "PrimitiveFields $clazz.name"
//		addFieldPrimaryKey(clazz);
////		addFieldVersion(clazz);
////		addFieldDeleted(clazz);
//	}

	public static void generate (ClassNode clazz) {
		BaseFields.addField(clazz, "id", Long.class);
		BaseFields.addInitiateField(clazz, "id", Long.class);
		addGetterFieldId(clazz, "id", Long.class);
		BaseFields.addSetterField(clazz, "id", Long.class);
	}
	
	public static void addGetterFieldId (ClassNode clazz, String name, Class type) {
		FieldNode field = clazz.getField(name);

		String fieldName = name;
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String methodName = "get" + fieldName;
		
		ReturnStatement returnCode = new ReturnStatement(new FieldExpression(field));

		Statement code = returnCode;
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, new ClassNode(type), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		method.addAnnotation(new AnnotationNode(new ClassNode(javax.persistence.Id.class)));
		method.addAnnotation(new AnnotationNode(new ClassNode(javax.persistence.GeneratedValue.class)));
		
		AnnotationNode annColumn = new AnnotationNode(new ClassNode(javax.persistence.Column.class));
		annColumn.setMember("name", new ConstantExpression(name.toUpperCase()));
		method.addAnnotation(annColumn);
		
		clazz.addMethod(method);
	}

}
