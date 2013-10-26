package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class NumberFields {

	public static void processField (ClassNode clazz, FieldNode field) {
		UserFields.alterField(clazz, field);
		UserFields.addInitiate(clazz, field);
		UserFields.addGetter(clazz, field);
		UserFields.addSetter(clazz, field);
	}

}
