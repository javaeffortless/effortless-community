package org.effortless.model;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;

import org.objectweb.asm.Opcodes;

public class FieldChangeSupportGenerator {

	public static void generate (ClassNode clazz) {
		addInterface(clazz);
		generateField(clazz);
		generatePublicMethods(clazz);
		generateProtectedMethods(clazz);
	}

	public static void addInterface (ClassNode clazz) {
		clazz.addInterface(new ClassNode(BindableBean.class));
	}

	//this._fcs = new FieldChangeSupport(this)
	public static void generateInitiateStatement (ClassNode clazz) {
		
	}
	
	public static String getFieldName (ClassNode clazz) {
		return "_fcs";
	}
	
	//protected FieldChangeSupport _fcs = new FieldChangeSupport(this)
	//protected FieldChangeSupport _fcs
	public static void generateField (ClassNode clazz) {
		ClassNode fieldClassNode = new ClassNode(FieldChangeSupport.class);
		ConstructorCallExpression expr = new ConstructorCallExpression(fieldClassNode, VariableExpression.THIS_EXPRESSION);
		
		FieldNode field = new FieldNode(getFieldName(clazz), Opcodes.ACC_PROTECTED | Opcodes.ACC_TRANSIENT, fieldClassNode, clazz, expr);
		clazz.addField(field);
		
		AnnotationNode annTransient = new AnnotationNode(new ClassNode(javax.persistence.Transient.class));
		field.addAnnotation(annTransient);
	}
	
	public static void generatePublicMethods (ClassNode clazz) {
		addPropertyChangeListener1(clazz);
		removePropertyChangeListener1(clazz);
		addPropertyChangeListener2(clazz);
		removePropertyChangeListener2(clazz);
		getPropertyChangeListeners1(clazz);
		getPropertyChangeListeners2(clazz);
		containsPropertyChangeListener1(clazz);
		containsPropertyChangeListener2(clazz);
		hasListeners(clazz);
	}

	//public boolean containsPropertyChangeListener(PropertyChangeListener listener) { return this._fcs.containsPropertyChangeListener(listener) }
	public static void containsPropertyChangeListener1 (ClassNode clazz) {
		String methodName = "containsPropertyChangeListener";
		String argument1 = "listener";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr = new VariableExpression(argument1);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(java.beans.PropertyChangeListener.class), argument1)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.boolean_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}

	//public boolean containsPropertyChangeListener(String propertyName, PropertyChangeListener listener) { return this._fcs.containsPropertyChangeListener(propertyName, listener) }
	public static void containsPropertyChangeListener2 (ClassNode clazz) {
		String methodName = "containsPropertyChangeListener";
		String argument1 = "propertyName";
		String argument2 = "listener";
		
		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr1 = new VariableExpression(argument1);
		VariableExpression varExpr2 = new VariableExpression(argument2);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr1, varExpr2);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter[] {new Parameter(new ClassNode(String.class), argument1), new Parameter(new ClassNode(java.beans.PropertyChangeListener.class), argument2)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.boolean_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}

	//public boolean hasListeners(String propertyName) { return this._fcs.hasListeners(propertyName) }
	public static void hasListeners (ClassNode clazz) {
		String methodName = "hasListeners";
		String argument1 = "propertyName";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr = new VariableExpression(argument1);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter[] {new Parameter(new ClassNode(String.class), argument1)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.boolean_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}

	//public List<PropertyChangeListener> getPropertyChangeListeners() { return this._fcs.getPropertyChangeListener() }
	public static void getPropertyChangeListeners1 (ClassNode clazz) {
		String methodName = "getPropertyChangeListeners";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		ArgumentListExpression argExpr = new ArgumentListExpression();
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ReturnStatement code = new ReturnStatement(callExpr);
		
		Parameter[] parameterTypes = Parameter.EMPTY_ARRAY;
		ClassNode returnType = new ClassNode(java.util.List.class);
//		GenericsType[] genericsTypes = [new GenericsType(new ClassNode(java.beans.PropertyChangeListener.class))]
//		returnType.setGenericsTypes(genericsTypes)
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, returnType, parameterTypes, ClassNode.EMPTY_ARRAY, code);
//		method.setGenericsTypes(genericsTypes)
		clazz.addMethod(method);
		
		
		AnnotationNode annTransient = new AnnotationNode(new ClassNode(javax.persistence.Transient.class));
		method.addAnnotation(annTransient);
	}
		
	//public List<PropertyChangeListener> getPropertyChangeListeners(String propertyName) { return this._fcs.getPropertyChangeListeners(propertyName) }
	public static void getPropertyChangeListeners2 (ClassNode clazz) {
		String methodName = "getPropertyChangeListeners";
		String argument1 = "propertyName";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr1 = new VariableExpression(argument1);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr1);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ReturnStatement code = new ReturnStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter[] {new Parameter(new ClassNode(String.class), argument1)};
		ClassNode returnType = new ClassNode(java.util.List.class);
//		GenericsType[] genericsTypes = [new GenericsType(new ClassNode(java.beans.PropertyChangeListener.class))]
//		returnType.setGenericsTypes(genericsTypes)
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, returnType, parameterTypes, ClassNode.EMPTY_ARRAY, code);
//		method.setGenericsTypes(genericsTypes)
		clazz.addMethod(method);
	}
		

	
	
	
	//public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) { this._fcs.addPropertyChangeListener(propertyName, listener) }
	public static void addPropertyChangeListener2 (ClassNode clazz) {
		String methodName = "addPropertyChangeListener";
		String argument1 = "propertyName";
		String argument2 = "listener";
		

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr1 = new VariableExpression(argument1);
		VariableExpression varExpr2 = new VariableExpression(argument2);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr1, varExpr2);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(String.class), argument1), new Parameter(new ClassNode(java.beans.PropertyChangeListener.class), argument2)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}
		
	//public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) { this._fcs.removePropertyChangeListener(propertyName, listener) }
	public static void removePropertyChangeListener2 (ClassNode clazz) {
		String methodName = "removePropertyChangeListener";
		String argument1 = "propertyName";
		String argument2 = "listener";
		

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr1 = new VariableExpression(argument1);
		VariableExpression varExpr2 = new VariableExpression(argument2);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr1, varExpr2);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(String.class), argument1), new Parameter(new ClassNode(java.beans.PropertyChangeListener.class), argument2)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}
		
	//public void addPropertyChangeListener(PropertyChangeListener listener) { this._fcs.addPropertyChangeListener(listener) }
	public static void addPropertyChangeListener1 (ClassNode clazz) {
		String methodName = "addPropertyChangeListener";
		String argument1 = "listener";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr = new VariableExpression(argument1);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(java.beans.PropertyChangeListener.class), argument1)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}
		
	//public void removePropertyChangeListener(PropertyChangeListener listener) { this._fcs.removePropertyChangeListener(listener) }
	public static void removePropertyChangeListener1 (ClassNode clazz) {
		String methodName = "removePropertyChangeListener";
		String argument1 = "listener";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr = new VariableExpression(argument1);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(java.beans.PropertyChangeListener.class), argument1)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}

	public static void generateProtectedMethods (ClassNode clazz) {
		firePropertyChange(clazz);
		firePropertyChangeEvent(clazz);
		fireIndexedPropertyChange(clazz);
	}

	//protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) { this._fcs.firePropertyChange(propertyName, oldValue, newValue) }
	public static void firePropertyChange (ClassNode clazz) {
		String methodName = "firePropertyChange";
		String argument1 = "propertyName";
		String argument2 = "oldValue";
		String argument3 = "newValue";
		
		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr1 = new VariableExpression(argument1);
		VariableExpression varExpr2 = new VariableExpression(argument2);
		VariableExpression varExpr3 = new VariableExpression(argument3);
		ArgumentListExpression argExpr = new ArgumentListExpression(new Expression[] {varExpr1, varExpr2, varExpr3});
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter[] {new Parameter(ClassHelper.STRING_TYPE, argument1), new Parameter(ClassHelper.OBJECT_TYPE, argument2), new Parameter(ClassHelper.OBJECT_TYPE, argument3)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}
	
	//protected void firePropertyChange(PropertyChangeEvent evt) { this._fcs.firePropertyChange(evt) }
	public static void firePropertyChangeEvent (ClassNode clazz) {
		String methodName = "firePropertyChange";
		String argument1 = "evt";

		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr = new VariableExpression(argument1);
		ArgumentListExpression argExpr = new ArgumentListExpression(varExpr);
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(new ClassNode(java.beans.PropertyChangeEvent.class), argument1)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}

	//protected void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) { this._fcs.fireIndexedPropertyChange(propertyName, index, oldValue, newValue) }
	public static void fireIndexedPropertyChange (ClassNode clazz) {
		String methodName = "fireIndexedPropertyChange";
		String argument1 = "propertyName";
		String argument2 = "index";
		String argument3 = "oldValue";
		String argument4 = "newValue";
		
		FieldNode field = clazz.getField(getFieldName(clazz));
		FieldExpression fieldExpression = new FieldExpression(field);
		VariableExpression varExpr1 = new VariableExpression(argument1);
		VariableExpression varExpr2 = new VariableExpression(argument2);
		VariableExpression varExpr3 = new VariableExpression(argument3);
		VariableExpression varExpr4 = new VariableExpression(argument4);
		ArgumentListExpression argExpr = new ArgumentListExpression(new Expression [] {varExpr1, varExpr2, varExpr3, varExpr4});
		MethodCallExpression callExpr = new MethodCallExpression(fieldExpression, new ConstantExpression(methodName), argExpr);
		ExpressionStatement code = new ExpressionStatement(callExpr);
		
		Parameter[] parameterTypes = new Parameter[] {new Parameter(ClassHelper.STRING_TYPE, argument1), new Parameter(ClassHelper.int_TYPE, argument2), new Parameter(ClassHelper.OBJECT_TYPE, argument3), new Parameter(ClassHelper.OBJECT_TYPE, argument4)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}
	
}
