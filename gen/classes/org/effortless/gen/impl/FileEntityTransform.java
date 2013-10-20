package org.effortless.gen.impl;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.ClassTransform;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.Filter;
import org.objectweb.asm.Opcodes;

public class FileEntityTransform extends HibernateEntityClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		new SetupEntityParentClassTransform().process(clazz, sourceUnit);
//		addAnnotations(clazz);
		new EntityStaticMethodsClassTransform().process(clazz, sourceUnit);//addStaticMethods(clazz, sourceUnit);

		UpdateDbClassTransform updateDb = new UpdateDbClassTransform();
		updateDb.process(clazz, sourceUnit);
	}
	
}
