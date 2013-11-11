package org.effortless.gen;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.effortless.core.ClassNodeHelper;
import org.effortless.model.Entity;

public abstract class AbstractNode<T extends AbstractNode<T>> extends Object implements GNode {

	public AbstractNode () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}
	
	protected abstract ClassNode _getType ();
	
	public boolean isType (Class<?> type) {
		boolean result = false;
		ClassNode clazz = _getType();
		result = (type != null && clazz != null);
		ClassNode ctype = ClassNodeHelper.toClassNode(type);
		
		result = result && (clazz.isDerivedFrom(ctype) || checkImplements(type));
		return result;
	}
	
	public boolean checkImplements (Class<?> type) {
		boolean result = false;
		ClassNode clazz = _getType();
		result = (type != null && clazz != null);
		if (result) {
			ClassNode ctype = ClassNodeHelper.toClassNode(type);
			do {
				result = (clazz.declaresInterface(ctype));
				clazz = clazz.getSuperClass();
			} while (result == false && clazz != null);
		}
		return result;
	}

	public boolean isString () {
		return isType(String.class);
	}
	
	public boolean isTime () {
		return isType(Time.class);
	}

	public boolean isTimestamp () {
		return isType(Timestamp.class);
	}

	public boolean isDate () {
		return isType(Date.class);
	}

	public boolean isBoolean () {
		return isType(Boolean.class);
	}
	
	public boolean isInteger () {
		return isType(Integer.class);
	}
	
	public boolean isDouble () {
		return isType(Double.class);
	}
	
	public boolean isEnum () {
		return isType(Enum.class);
	}
	
	public boolean isFile () {
		return isType(File.class);
	}
	
	public boolean isCollection () {
		return isType(Collection.class);
	}
	
	public boolean isList () {
		return isType(List.class);
	}
	
	public boolean isEntity () {
		return isType(Entity.class);
	}

	protected abstract AnnotatedNode _getAnnotatedNode ();
	
	
	public GAnnotation addAnnotation (Class<?> annotation) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation));
	}
	
	
	public GAnnotation addAnnotation (ClassNode annotation) {
		GAnnotation result = null;
		AnnotationNode ann = new AnnotationNode(annotation);
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		result = new GAnnotation(ann, annotatedNode);
		annotatedNode.addAnnotation(ann);
		return result;
	}

	public GAnnotation addAnnotation (AnnotationNode annotation) {
		GAnnotation result = null;
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		result = new GAnnotation(annotation, annotatedNode);
		annotatedNode.addAnnotation(annotation);
		return result;
	}

	public GAnnotation addAnnotation (Class<?> annotation, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public GAnnotation addAnnotation (Class<?> annotation, Expression value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public GAnnotation addAnnotation (ClassNode annotation, String value) {
		return addAnnotation(annotation, "value", value);
	}

	public GAnnotation addAnnotation (Class<?> annotation, String property, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}

	public GAnnotation addAnnotation (Class<?> annotation, String property, boolean value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, (value ? ConstantExpression.PRIM_TRUE : ConstantExpression.PRIM_FALSE));
	}

	
	
	public GAnnotation addAnnotation (ClassNode annotation, String property, String value) {
		return addAnnotation(annotation, property, new ConstantExpression(value));
	}

	public GAnnotation addAnnotation (ClassNode annotation, String property, Expression expr) {
		GAnnotation result = null;
		AnnotationNode ann = new AnnotationNode(annotation);
		ann.setMember(property, expr);
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		result = new GAnnotation(ann, annotatedNode);
		annotatedNode.addAnnotation(ann);
		return result;
	}

	public GAnnotation addAnnotation (Class<?> annotation, String property, Expression value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}
	
	public GAnnotation addAnnotation (Class<?> annotation, String[] properties, Expression... values) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), properties, values);
	}
	
	public GAnnotation addAnnotation (ClassNode annotation, String[] properties, Expression... values) {
		GAnnotation result = null;
		AnnotationNode ann = new AnnotationNode(annotation);
		int length = (properties != null ? properties.length : 0);
		for (int i = 0; i < length; i++) {
			ann.setMember(properties[i], values[i]);
		}
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		result = new GAnnotation(ann, annotatedNode);
		annotatedNode.addAnnotation(ann);
		return result;
	}
	
	public Expression cteNull () {
		return ConstantExpression.NULL;
	}
	
	public Expression cte (Object value) {
		return new ConstantExpression(value);
	}
	
	public Expression cteSuper () {
		return VariableExpression.SUPER_EXPRESSION;
	}
	
	public Expression cteThis () {
		return VariableExpression.THIS_EXPRESSION;
	}
	
	public Expression cteTrue() {
		return ConstantExpression.PRIM_TRUE;
	}
	
	public Expression cteFalse() {
		return ConstantExpression.PRIM_FALSE;
	}

	public Expression cteTRUE() {
		return ConstantExpression.TRUE;
	}
	
	public Expression cteFALSE() {
		return ConstantExpression.FALSE;
	}

	public Expression cteClass(Class<?> type) {
		return cteClass(ClassNodeHelper.toClassNode(type));
	}
	
	public Expression cteClass(ClassNode type) {
		return new ClassExpression(type);
	}
	
	public GAnnotation getAnnotation (Class<?> clazz) {
		GAnnotation result = null;
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		List<AnnotationNode> annotations = annotatedNode.getAnnotations(ClassNodeHelper.toClassNode(clazz));
		if (annotations != null && annotations.size() == 1) {
			AnnotationNode ann = annotations.get(0);
			result = new GAnnotation(ann, _getAnnotatedNode());
		}
		return result;
	}

//	protected ClassNode _toPlain (ClassNode node) {
//		return (node != null ? node.getPlainNodeReference() : node);
//	}

	public boolean hasAnnotation (Class<?> clazz) {
		boolean result = false;
		if (clazz != null) {
			ClassNode cNode = ClassNodeHelper.toClassNode(clazz);
			result = hasAnnotation(cNode);
		}
		return result;
	}

	public boolean hasAnnotation (ClassNode clazz) {
		boolean result = false;
		if (clazz != null) {
			AnnotatedNode annotatedNode = _getAnnotatedNode();
			List<AnnotationNode> annotations = (annotatedNode != null ? annotatedNode.getAnnotations(clazz) : null);
			result = (annotations != null && annotations.size() > 0);
		}
		return result;
	}

	
	
}
