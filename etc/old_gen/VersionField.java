package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class VersionField {

	public static void generate (ClassNode clazz) {
		String fieldName = "version";
		Class fieldType = Integer.class;
		
		BaseFields.addField(clazz, fieldName, fieldType);
		BaseFields.addInitiateField(clazz, fieldName, fieldType);
		addGetterFieldVersion(clazz, fieldName, fieldType);
		BaseFields.addSetterField(clazz, fieldName, fieldType);
	}
	
	public static void addGetterFieldVersion (ClassNode clazz, String name, Class type) {
		FieldNode field = clazz.getField(name);

		String fieldName = name;
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String methodName = "get" + fieldName;
		
		ReturnStatement returnCode = new ReturnStatement(new FieldExpression(field));

		Statement code = returnCode;
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, new ClassNode(type), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
	
		method.addAnnotation(new AnnotationNode(new ClassNode(javax.persistence.Version.class)));
		
		AnnotationNode annColumn = new AnnotationNode(new ClassNode(javax.persistence.Column.class));
		annColumn.setMember("name", new ConstantExpression(name.toUpperCase()));
		method.addAnnotation(annColumn);
		
		clazz.addMethod(method);
	}

}
