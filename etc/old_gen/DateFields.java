package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class DateFields {

	public static void processField (ClassNode clazz, FieldNode field) {
		UserFields.alterField(clazz, field);
		UserFields.addInitiate(clazz, field);
		MethodNode getter = UserFields.addGetter(clazz, field);
		UserFields.addSetter(clazz, field);
		addTemporalType(clazz, field, getter);
	}
	
	//@Temporal(TemporalType.TIMESTAMP)
//	@Type(type="timestamp")
//	@org.hibernate.annotations.Type(type="org.effortless.model.FileUserType")

	public static void addTemporalType (ClassNode clazz, FieldNode field, MethodNode getter) {
		String type = null;
		String typeName = field.getType().getName();
		if ("java.sql.Date".equals(typeName)) {
//			type = "DATE";//javax.persistence.TemporalType.DATE;
//			type = "org.effortless.model.DateSqlUserType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		else if ("java.sql.Time".equals(typeName)) {
			type = "TIME";//javax.persistence.TemporalType.TIME;
			type = "org.effortless.model.TimeUserType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		else if ("java.sql.Timestamp".equals(typeName)) {
//			type = "TIMESTAMP";//javax.persistence.TemporalType.TIMESTAMP;
			type = "timestamp";
//			type = "org.effortless.model.TimestampType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		
		
		if (type != null) {
//			AnnotationNode ann = new AnnotationNode(new ClassNode(javax.persistence.Temporal.class));
//			ann.setMember("value", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.TemporalType.class)), new ConstantExpression(type)));
			AnnotationNode ann = new AnnotationNode(new ClassNode(org.hibernate.annotations.Type.class));
			ann.setMember("type", new ConstantExpression(type));
			getter.addAnnotation(ann);
		}
	}

}
