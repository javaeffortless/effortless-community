package org.effortless.gen.impl;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GClass;

public class EntityTableClassTransform extends Object implements ClassTransform {

	@Override
	public void process(GClass clazz) {
		addAnnotations(clazz);
	}
	
	//@Entity
	//@Table(name="TABLE")
	protected void addAnnotations (GClass cg) {
		ClassNode clazz = cg.getClassNode();
		String tableName = clazz.getNameWithoutPackage().toUpperCase();

		AnnotationNode annEntity = new AnnotationNode(new ClassNode(javax.persistence.Entity.class));
		clazz.addAnnotation(annEntity);

		AnnotationNode annTable = new AnnotationNode(new ClassNode(javax.persistence.Table.class));
		annTable.setMember("name", new ConstantExpression(tableName));
		clazz.addAnnotation(annTable);
		
		//@javax.persistence.SequenceGenerator(name="sequence_id", sequenceName="basic_sequence")
		AnnotationNode annSequence = new AnnotationNode(new ClassNode(javax.persistence.SequenceGenerator.class));
		annSequence.setMember("name", new ConstantExpression("sequence_id"));
		annSequence.setMember("sequenceName", new ConstantExpression(tableName));
		annSequence.setMember("initialValue", new ConstantExpression(Integer.valueOf(1)));
		clazz.addAnnotation(annSequence);
		
		AnnotationNode annDynamicUpdate = new AnnotationNode(new ClassNode(org.hibernate.annotations.DynamicUpdate.class));
		annDynamicUpdate.setMember("value", ConstantExpression.TRUE);
		clazz.addAnnotation(annDynamicUpdate);

		AnnotationNode annDynamicInsert = new AnnotationNode(new ClassNode(org.hibernate.annotations.DynamicInsert.class));
		annDynamicInsert.setMember("value", ConstantExpression.TRUE);
		clazz.addAnnotation(annDynamicInsert);
		
		AnnotationNode annSelectBeforeUpdate = new AnnotationNode(new ClassNode(org.hibernate.annotations.SelectBeforeUpdate.class));
		annSelectBeforeUpdate.setMember("value", ConstantExpression.FALSE);
		clazz.addAnnotation(annSelectBeforeUpdate);
		
		
		
		
if (false) {
		//@org.hibernate.annotations.Tuplizer(impl = entities.CustomEntityTuplizer.class)
		AnnotationNode annTuplizer = new AnnotationNode(new ClassNode(org.hibernate.annotations.Tuplizer.class));
		annTuplizer.setMember("impl", new ClassExpression(new ClassNode(org.effortless.model.CustomEntityTuplizer.class)));
		clazz.addAnnotation(annTuplizer);
}
	}

}
