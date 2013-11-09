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
	
	
	public T addAnnotation (Class<?> annotation) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation));
	}
	
	
	public T addAnnotation (ClassNode annotation) {
		AnnotationNode ann = new AnnotationNode(annotation);
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		annotatedNode.addAnnotation(ann);
		return (T)this;
	}

	public T addAnnotation (AnnotationNode annotation) {
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		annotatedNode.addAnnotation(annotation);
		return (T)this;
	}

	public T addAnnotation (Class<?> annotation, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public T addAnnotation (Class<?> annotation, Expression value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public T addAnnotation (ClassNode annotation, String value) {
		return addAnnotation(annotation, "value", value);
	}

	public T addAnnotation (Class<?> annotation, String property, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}

	public T addAnnotation (Class<?> annotation, String property, boolean value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, (value ? ConstantExpression.PRIM_TRUE : ConstantExpression.PRIM_FALSE));
	}

	
	
	public T addAnnotation (ClassNode annotation, String property, String value) {
		return addAnnotation(annotation, property, new ConstantExpression(value));
	}

	public T addAnnotation (ClassNode annotation, String property, Expression expr) {
		AnnotationNode ann = new AnnotationNode(annotation);
		ann.setMember(property, expr);
		AnnotatedNode annotatedNode = _getAnnotatedNode();
		annotatedNode.addAnnotation(ann);
		return (T)this;
	}

	public T addAnnotation (Class<?> annotation, String property, Expression value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}
	
	public T addAnnotation (Class<?> annotation, String[] properties, Expression... values) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), properties, values);
	}
	
	public T addAnnotation (ClassNode annotation, String[] properties, Expression... values) {
		AnnotationNode result = new AnnotationNode(annotation);
		int length = (properties != null ? properties.length : 0);
		for (int i = 0; i < length; i++) {
			result.setMember(properties[i], values[i]);
		}
		addAnnotation(result);
		return (T)this;
	}
	
	
	
	
	
	
	
	
	
	
	public AnnotationNode createAnnotation (Class<?> annotation) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation));
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation) {
		AnnotationNode result = new AnnotationNode(annotation);
		return result;
	}

	public AnnotationNode createAnnotation (Class<?> annotation, String value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public AnnotationNode createAnnotation (Class<?> annotation, Expression value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation, String value) {
		return createAnnotation(annotation, "value", value);
	}

	public AnnotationNode createAnnotation (Class<?> annotation, String property, String value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}

	public AnnotationNode createAnnotation (ClassNode annotation, String property, String value) {
		return createAnnotation(annotation, property, new ConstantExpression(value));
	}

	public AnnotationNode createAnnotation (Class<?> annotation, String property, Expression value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation, String property, Expression value) {
		AnnotationNode result = new AnnotationNode(annotation);
		result.setMember(property, value);
		return result;
	}
	
	public AnnotationNode createAnnotation (Class<?> annotation, String[] properties, Expression... values) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), properties, values);
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation, String[] properties, Expression... values) {
		AnnotationNode result = new AnnotationNode(annotation);
		int length = (properties != null ? properties.length : 0);
		for (int i = 0; i < length; i++) {
			result.setMember(properties[i], values[i]);
		}
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
			result = new GAnnotation(ann);
		}
		return result;
	}

//	protected ClassNode _toPlain (ClassNode node) {
//		return (node != null ? node.getPlainNodeReference() : node);
//	}
	
}
