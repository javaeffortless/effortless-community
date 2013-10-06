package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class EnumFields {

	public static void processField (ClassNode clazz, FieldNode field) {
		UserFields.alterField(clazz, field);
		UserFields.addInitiate(clazz, field);
		MethodNode getter = UserFields.addGetter(clazz, field);
		UserFields.addSetter(clazz, field);
		addEnumeratedAnnotation(clazz, field, getter);
	}
	
	//@javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
	public static void addEnumeratedAnnotation (ClassNode clazz, FieldNode field, MethodNode getter) {
		AnnotationNode ann = new AnnotationNode(new ClassNode(javax.persistence.Enumerated.class));
		ann.setMember("value", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.EnumType.class)), new ConstantExpression("STRING")));
		getter.addAnnotation(ann);
	}

}
