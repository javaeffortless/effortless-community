package org.effortless.gen.fields;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

public class BaseFields {

	public static void addField (ClassNode clazz, String name, Class type) {
		FieldNode field = new FieldNode(name, Opcodes.ACC_PROTECTED, new ClassNode(type), clazz, null);
		clazz.addField(field);
	}

	public static void addInitiateField (ClassNode clazz, String name, Class type) {
		FieldNode field = clazz.getField(name);
		
		FieldExpression left = new FieldExpression(field);
		Token operator = Token.newSymbol(Types.ASSIGN, -1, -1);
		ConstantExpression right = new ConstantExpression("null");
		BinaryExpression assign = new BinaryExpression(left, operator, right);
		ExpressionStatement assignCode = new ExpressionStatement(assign);
		
		Statement code = assignCode;
		
		String fieldName = name;
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String methodName = "initiate" + fieldName;
		
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		method.setSynthetic(true);
		clazz.addMethod(method);
	}

	public static void addSetterField (ClassNode clazz, String name, Class type) {
		FieldNode field = clazz.getField(name);
		
		FieldExpression left = new FieldExpression(field);
		Token operator = Token.newSymbol(Types.ASSIGN, -1, -1);
		VariableExpression right = new VariableExpression("newValue");
		BinaryExpression assign = new BinaryExpression(left, operator, right);
		ExpressionStatement assignCode = new ExpressionStatement(assign);
		
		Statement code = assignCode;
		
		String fieldName = name;
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String setterName = "set" + fieldName;
		
		Parameter[] setterParameterTypes = new Parameter[] {new Parameter(new ClassNode(type), "newValue")};
		MethodNode method = new MethodNode(setterName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, setterParameterTypes, ClassNode.EMPTY_ARRAY, code);
		method.setSynthetic(true);
		clazz.addMethod(method);
	}
	
	
	public static String getGetterName (FieldNode field) {
		String result = null;
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "get" + fieldName;
		//		String methodName = "get" + field.getName().capitalize()
		
		return result;
	}
	
	public static String getGetterName (String fieldName) {
		String result = null;
//		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "get" + fieldName;
		//		String methodName = "get" + field.getName().capitalize()
		
		return result;
	}
	
	public static String getSetterName (String fieldName) {
		String result = null;
//		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "set" + fieldName;
		//		String methodName = "get" + field.getName().capitalize()
		
		return result;
	}
	
	public static String getInitiateName (FieldNode field) {
		String result = null;
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "initiate" + fieldName;
		//		String methodName = "get" + field.getName().capitalize()
		
		return result;
	}
	
	public static String getSetterName (FieldNode field) {
		String result = null;
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "set" + fieldName;
		return result;
	}

	public static Statement createCodeSetter (ClassNode clazz, FieldNode field) {
		return (true ? createCodeSetterShort(clazz, field) : createCodeSetterLarge(clazz, field));
	}
	
	/*
	 * 
	 * 
	 * 

        this._setProperty('text', this.text, this .text = newValue )
		
	 */
	public static Statement createCodeSetterShort (ClassNode clazz, FieldNode field) {
		BlockStatement result = null;
		result = new BlockStatement();
		
		String fieldName = field.getName();

		VariableExpression varNewValue = new VariableExpression("newValue", field.getType());
		FieldExpression fieldExpr = new FieldExpression(field);
		MethodCallExpression callCheckLoaded = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "_setProperty", new ArgumentListExpression(new Expression[] {new ConstantExpression(fieldName), fieldExpr, new BinaryExpression(fieldExpr, Token.newSymbol(Types.ASSIGN, -1, -1), varNewValue)}));
		result.addStatement(new ExpressionStatement(callCheckLoaded));
		
		return result;
	}
	
	
	/*
	 * 
		boolean _loaded = checkLoaded("text", true);
		String oldValue = (_loaded ? this.getText() : null);
		if (!_loaded || !_equals(oldValue, newValue)) {
			this.text = newValue;
			if (_loaded) {
				doChangeText(oldValue, newValue);
				firePropertyChange("text", oldValue, newValue);
			}
		}
		
	 */
	public static Statement createCodeSetterLarge (ClassNode clazz, FieldNode field) {
		BlockStatement result = null;
		result = new BlockStatement();
		
		String fieldName = field.getName();
		String upperFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String getterName = BaseFields.getGetterName(field);

		VariableExpression varNewValue = new VariableExpression("newValue", field.getType());
		
		//boolean _loaded = checkLoaded("text", true);
		VariableExpression varLoaded = new VariableExpression("_loaded", ClassHelper.boolean_TYPE);
		MethodCallExpression callCheckLoaded = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "checkLoaded", new ArgumentListExpression(new Expression[] {new ConstantExpression(fieldName), ConstantExpression.TRUE}));
		DeclarationExpression assignLoaded = new DeclarationExpression(varLoaded, Token.newSymbol(Types.ASSIGN, -1, -1), callCheckLoaded);
		result.addStatement(new ExpressionStatement(assignLoaded));
		
		//String oldValue = (_loaded ? this.getText() : null);
		VariableExpression varOldValue = new VariableExpression("oldValue", field.getType());
		MethodCallExpression getOldValue = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, getterName, new ArgumentListExpression());
		TernaryExpression chooseOldValue = new TernaryExpression(new BooleanExpression(varLoaded), getOldValue, ConstantExpression.NULL);
		DeclarationExpression assignOldValue = new DeclarationExpression(varLoaded, Token.newSymbol(Types.ASSIGN, -1, -1), chooseOldValue);
		result.addStatement(new ExpressionStatement(assignOldValue));

		//if (!_loaded || !_equals(oldValue, newValue)) {
		BlockStatement ifNotEqualsContent = new BlockStatement();
		MethodCallExpression callEqualsOldNewValue = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "_equals", new ArgumentListExpression(new Expression[] {varOldValue, varNewValue}));
		BinaryExpression conditionIf = new BinaryExpression(new NotExpression(varLoaded), Token.newSymbol(Types.LOGICAL_OR, -1, -1), new NotExpression(callEqualsOldNewValue));
		IfStatement ifNotEquals = new IfStatement(new BooleanExpression(conditionIf), ifNotEqualsContent, new EmptyStatement());
		result.addStatement(ifNotEquals);
		
		//this.text = newValue;
		BinaryExpression assignNewValue = new BinaryExpression(new FieldExpression(field), Token.newSymbol(Types.ASSIGN, -1, -1), varNewValue);
		ifNotEqualsContent.addStatement(new ExpressionStatement(assignNewValue));
		
		//if (_loaded) {
		BlockStatement ifLoadedContent = new BlockStatement();
		IfStatement ifLoaded = new IfStatement(new BooleanExpression(varLoaded), ifLoadedContent, new EmptyStatement());
		ifNotEqualsContent.addStatement(ifLoaded);

		//doChangeText(oldValue, newValue);
		MethodCallExpression callDoChangeProperty = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "doChange" + upperFieldName, new ArgumentListExpression(new Expression[] {varOldValue, varNewValue}));
		ifLoadedContent.addStatement(new ExpressionStatement(callDoChangeProperty));
		
		//firePropertyChange("text", oldValue, newValue);
		MethodCallExpression callFirePropertyChange = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "firePropertyChange", new ArgumentListExpression(new Expression[] {new ConstantExpression(fieldName), varOldValue, varNewValue}));
		ifLoadedContent.addStatement(new ExpressionStatement(callFirePropertyChange));
		
		return result;
	}
	
	public static Statement createCodeSetter2 (ClassNode clazz, FieldNode field) {
		BlockStatement result = null;
		result = new BlockStatement();
		VariableExpression varOldValue = new VariableExpression("oldValue", field.getType());
		VariableExpression varNewValue = new VariableExpression("newValue", field.getType());
		FieldExpression fieldExpr = new FieldExpression(field);
		DeclarationExpression assignOldValue = new DeclarationExpression(varOldValue, Token.newSymbol(Types.ASSIGN, -1, -1), fieldExpr);
		
		ArgumentListExpression argEquals = new ArgumentListExpression(new Expression[] {varOldValue, varNewValue});
		StaticMethodCallExpression cmpEquals = new StaticMethodCallExpression(new ClassNode(org.effortless.core.ObjectUtils.class), "equals", argEquals);
		NotExpression notCmpEquals = new NotExpression(cmpEquals);

		BinaryExpression assign = new BinaryExpression(fieldExpr, Token.newSymbol(Types.ASSIGN, -1, -1), varNewValue);
		
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String doChangeMethodName = "doChange" + fieldName;
		MethodCallExpression callDoChange = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, doChangeMethodName, new ArgumentListExpression(new Expression[] {varOldValue, varNewValue}));

		MethodCallExpression callFirePropertyChange = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "firePropertyChange", new ArgumentListExpression(new Expression[] {new ConstantExpression(field.getName()), varOldValue, varNewValue}));
				
		BlockStatement ifContentStm = new BlockStatement();
		ifContentStm.addStatement(new ExpressionStatement(assign));
		ifContentStm.addStatement(new ExpressionStatement(callDoChange));
		ifContentStm.addStatement(new ExpressionStatement(callFirePropertyChange));
		
		IfStatement ifStm = new IfStatement(notCmpEquals, ifContentStm, new EmptyStatement());
		
		result.addStatement(new ExpressionStatement(assignOldValue));
		result.addStatement(ifStm);
		
		return result;
	}
	
	public static void addMethodDoChangeField (ClassNode clazz, FieldNode field) {
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String methodName = "doChange" + fieldName;
		
		String argument1 = "oldValue";
		String argument2 = "newValue";
		
		BlockStatement code = new BlockStatement();
		
		Parameter[] parameterTypes = new Parameter [] {new Parameter(field.getType(), argument1), new Parameter(field.getType(), argument2)};
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(method);
	}

	
	
	//FORMA DE GENERAR ASTNODES (Statements) No funciona!!!!!!
	//		String codeTxt = "public " + field.type.name + " " + methodName + " () {"
	//		codeTxt += "return this." + field.name
	//		codeTxt += "}"
	//		String codeTxt = "return this." + field.name
			
	//		List<ASTNode> nodes = new AstBuilder().buildFromString(codeTxt)
	
	//		String codeTxt = "this." + field.name + " = newValue"
	//
	//		List<ASTNode> nodes = new AstBuilder().buildFromString(codeTxt)
	//		Statement assignCode = new ExpressionStatement(((ReturnStatement)(((BlockStatement)(nodes.get(0))).getStatements().get(0))).getExpression())
	
}
