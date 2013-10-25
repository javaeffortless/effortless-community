package org.effortless.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.Collections;
import org.effortless.model.AbstractEntity;
import org.objectweb.asm.Opcodes;

public class GMethod extends Object {

	protected GMethod () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.methodNode = null;
		this.classGen = null;
		this.code = null;
		this.variables = new HashMap<String, VariableExpression>();
		this.previousCode = null;
	}
	
	public GMethod (String name, GClass classGen) {
		this.code = new BlockStatement();
		this.code.addStatement(EmptyStatement.INSTANCE);
		this.methodNode = new MethodNode(name, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, this.code);
		this.classGen = classGen;
		this.classGen.getClassNode().addMethod(this.methodNode);
	}
	
	public GMethod (MethodNode methodNode) {
		this(methodNode, new GClass(methodNode.getDeclaringClass()));
	}
	
	public GMethod (MethodNode methodNode, GClass classGen) {
		this.methodNode = methodNode;
		this.classGen = classGen;
		this.code = new BlockStatement();
		this.preserveCode();
	}
	
	protected MethodNode methodNode;
	
	protected GClass classGen;

	public MethodNode getMethodNode () {
		return this.methodNode;
	}
	
	public GClass getClassGen () {
		return this.classGen;
	}
	
	public GMethod setPublic (boolean newValue) {
		if (newValue) {
			this.methodNode.setModifiers(Opcodes.ACC_PUBLIC);
		}
		return this;
	}
	
	public GMethod setProtected (boolean newValue) {
		if (newValue) {
			this.methodNode.setModifiers(Opcodes.ACC_PROTECTED);
		}
		return this;
	}

	public GMethod setPrivate (boolean newValue) {
		if (newValue) {
			this.methodNode.setModifiers(Opcodes.ACC_PRIVATE);
		}
		return this;
	}
	
	public GMethod setReturnType (Class<?> type) {
		return setReturnType(ClassNodeHelper.toClassNode(type));
	}

	public GMethod setReturnType (ClassNode type) {
		this.methodNode.setReturnType(type);
		return this;
	}

	public GMethod addParameter (Class<?> type, String name) {
		return addParameter(ClassNodeHelper.toClassNode(type), name);
	}

	public GMethod addParameter (ClassNode type, String name) {
		return addParameter(new Parameter(type, name));
	}

	public GMethod addParameter (ClassNode type, String name, AnnotationNode ann) {
		Parameter param = new Parameter(type, name);
		param.addAnnotation(ann);
		return addParameter(param);
	}

	public GMethod addParameter (Class<?> type, String name, AnnotationNode ann) {
		Parameter param = new Parameter(ClassNodeHelper.toClassNode(type), name);
		param.addAnnotation(ann);
		return addParameter(param);
	}

	public GMethod addParameter (Parameter param) {
		if (param != null) {
			this._saveVariableParam(param);
			java.util.List<Parameter> list = Collections.asList(this.methodNode.getParameters());
			list.add(param);
			Parameter[] parameters = list.toArray(new Parameter[0]);
			this.methodNode.setParameters(parameters);
		}
		return this;
	}

	public GMethod addParameters (Parameter... params) {
		if (params != null) {
			for (Parameter param : params) {
				this._saveVariableParam(param);
			}
			java.util.List<Parameter> list = Collections.asList(this.methodNode.getParameters());
			java.util.List<Parameter> paramList = Collections.asList(params);
			list.addAll(paramList);
			Parameter[] parameters = list.toArray(new Parameter[0]);
			this.methodNode.setParameters(parameters);
		}
		return this;
	}

//	public MethodGen addParameter (ClassNode type, String name) {
//	
//	Parameter[] parameterTypes = new Parameter [] {new Parameter(COMPARE_BUILDER, "eqBuilder"), new Parameter(ClassHelper.OBJECT_TYPE, "obj")};
//	MethodNode method = new MethodNode("doCompare", Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
//	this.clazz.addMethod(method);
//	
//	
//}

	public AnnotationNode addAnnotation (Class<?> annotation) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation));
	}
	
	public AnnotationNode addAnnotation (ClassNode annotation) {
		AnnotationNode result = new AnnotationNode(annotation);
		this.methodNode.addAnnotation(result);
		return result;
	}

	public AnnotationNode addAnnotation (Class<?> annotation, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public AnnotationNode addAnnotation (Class<?> annotation, Expression value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public AnnotationNode addAnnotation (ClassNode annotation, String value) {
		return addAnnotation(annotation, "value", value);
	}

	public AnnotationNode addAnnotation (Class<?> annotation, String property, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}

	public AnnotationNode addAnnotation (ClassNode annotation, String property, String value) {
		return addAnnotation(annotation, property, new ConstantExpression(value));
	}

	public AnnotationNode addAnnotation (Class<?> annotation, String property, Expression value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}
	
	public AnnotationNode addAnnotation (ClassNode annotation, String property, Expression value) {
		AnnotationNode result = new AnnotationNode(annotation);
		result.setMember(property, value);
		this.methodNode.addAnnotation(result);
		return result;
	}
	
	public AnnotationNode addAnnotation (Class<?> annotation, String[] properties, Expression... values) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), properties, values);
	}
	
	public AnnotationNode addAnnotation (ClassNode annotation, String[] properties, Expression... values) {
		AnnotationNode result = new AnnotationNode(annotation);
		int length = (properties != null ? properties.length : 0);
		for (int i = 0; i < length; i++) {
			result.setMember(properties[i], values[i]);
		}
		this.methodNode.addAnnotation(result);
		return result;
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
	
	
	
	
	
	
	
	
	
	
	
	public BlockStatement getCode () {
		return this.code;
	}
	
	protected BlockStatement code;
	
	public GMethod gPrintln (String msg) {
		return gPrintln(new ConstantExpression(msg));
	}
	
	public GMethod gPrintln (Expression msg) {
		Statement statement = null;
		ClassExpression system = new ClassExpression(new ClassNode(System.class));
		PropertyExpression systemOut = new PropertyExpression(system, "out");
		MethodCallExpression beginCall = new MethodCallExpression(systemOut, "println", new ArgumentListExpression(new Expression[] {msg}));
		statement = new ExpressionStatement(beginCall);
		this.code.addStatement(statement);
		return this;
	}

	public GMethod newObjectArray (String varName, Parameter[] parameters) {
		VariableExpression[] variables = new VariableExpression[parameters.length];
		int index = 0;
		for (Parameter param : parameters) {
			variables[index] = new VariableExpression(param.getName(), param.getType());
			index += 1;
		}
		return newObjectArray(varName, variables);
	}
	
	public GMethod newObjectArray (String varName, VariableExpression[] variables) {
		//Object[] __paramValues = [text, ammount];
		ClassNode objectArrayType = ClassHelper.OBJECT_TYPE.makeArray();
		VariableExpression varParamValues = new VariableExpression("__paramValues", objectArrayType);
		Expression valueParamValues = ConstantExpression.NULL;
		if (variables != null && variables.length > 0) {
			List<Expression> values = new ArrayList<Expression>();
			for (VariableExpression var : variables) {
				values.add(var);
			}
			valueParamValues = new ListExpression(values);
		}
		DeclarationExpression declParamValues = new DeclarationExpression(varParamValues, Token.newSymbol(Types.ASSIGN, -1, -1), valueParamValues);
		this.code.addStatement(new ExpressionStatement(declParamValues));
		return this;
	}
	
//	public MethodGen execute (String method) {
//		MethodCallExpression callMethod = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, method, ArgumentListExpression.EMPTY_ARGUMENTS);
//		this.code.addStatement(new ExpressionStatement(callMethod));
//		return this;
//	}
//	
//	public MethodGen execute (String method, ClassNode returnType, String varName) {
//		MethodCallExpression callMethod = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, method, ArgumentListExpression.EMPTY_ARGUMENTS);
//		this.code.addStatement(new ExpressionStatement(callMethod));
//		return this;
//	}

//	public MethodGen newVariable (Class<?> type, String name) {
//		return newVariable(ClassNodeHelper.toClassNode(type), name);
//	}
	
//	public MethodGen newVariable (Class<?> type, String name, Object defaultValue) {
//		return newVariable(ClassNodeHelper.toClassNode(type), name, new ConstantExpression(defaultValue));
//	}
	
	
	/*
	 * 


			//Basic result = null
			VariableExpression varResult = new VariableExpression("result", clazz);
			DeclarationExpression declResult = new DeclarationExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
			code.addStatement(new ExpressionStatement(declResult));

		//boolean __stopExecutionTime = false;


	 * 
	 * 
	 * 
	 * 
	 */
//	public MethodGen newVariable (ClassNode type, String name) {
//		VariableExpression varStopExecutionTime = new VariableExpression(name, type);
//		DeclarationExpression assignStopExecutionTime = new DeclarationExpression(varStopExecutionTime, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
//		this.code.addStatement(new ExpressionStatement(assignStopExecutionTime));
//		return this;
//	}

	public GMethod declVariable (Class<?> type, String name) {
		return declVariable(ClassNodeHelper.toClassNode(type), name, ConstantExpression.NULL);
	}
	
	public GMethod declVariable (ClassNode type, String name) {
		return declVariable(type, name, ConstantExpression.NULL);
	}
	
	public GMethod declVariable (Class<?> type, String name, Expression defaultValue) {
		return declVariable(ClassNodeHelper.toClassNode(type), name, defaultValue);
	}

	public GMethod declVariable (ClassNode type, String name, Expression defaultValue) {
		VariableExpression var = new VariableExpression(name, type);
		this._saveVariable(name, var);
		DeclarationExpression assign = new DeclarationExpression(var, Token.newSymbol(Types.ASSIGN, -1, -1), defaultValue);
		this.code.addStatement(new ExpressionStatement(assign));
		return this;
	}

	
	public GMethod declFinalVariable (Class<?> type, String name) {
		return declFinalVariable(ClassNodeHelper.toClassNode(type), name, ConstantExpression.NULL);
	}
	
	public GMethod declFinalVariable (ClassNode type, String name) {
		return declVariable(type, name, ConstantExpression.NULL);
	}
	
	public GMethod declFinalVariable (Class<?> type, String name, Expression defaultValue) {
		return declVariable(ClassNodeHelper.toClassNode(type), name, defaultValue);
	}

	public GMethod declFinalVariable (ClassNode type, String name, Expression defaultValue) {
		VariableExpression var = new VariableExpression(name, type);
		var.setModifiers(Opcodes.ACC_FINAL);
		this._saveVariable(name, var);
		DeclarationExpression assign = new DeclarationExpression(var, Token.newSymbol(Types.ASSIGN, -1, -1), defaultValue);
		this.code.addStatement(new ExpressionStatement(assign));
		return this;
	}


	
	
	public Expression callConstructor (Class<?> type) {
		return callConstructor(ClassNodeHelper.toClassNode(type), (Expression[])null);
	}
	
	public Expression callConstructor (ClassNode type) {
		return callConstructor(type, (Expression[])null);
	}
	
	public Expression callConstructor (Class<?> type, String... arguments) {
		return callConstructor(ClassNodeHelper.toClassNode(type), this.toExpressions(arguments));
	}
	
	public Expression callConstructor (ClassNode type, String... arguments) {
		return callConstructor(type, this.toExpressions(arguments));
	}
	
	public Expression callConstructor (Class<?> type, Expression... arguments) {
		return callConstructor(ClassNodeHelper.toClassNode(type), arguments);
	}
	
	public Expression callConstructor (ClassNode type, Expression... arguments) {
		ConstructorCallExpression result = null;
		ArgumentListExpression list = toListExpression(arguments);
		result = new ConstructorCallExpression(type, list);
		if (type instanceof InnerClassNode && type.getNameWithoutPackage().contains("$")) {
			result.setUsingAnonymousInnerClass(true);
		}
		return result;
	}
	
//	public MethodGen newVariable (ClassNode type, String name, Expression defaultValue) {
//		//boolean __stopExecutionTime = false;
//		VariableExpression var = new VariableExpression(name, type);
//		DeclarationExpression assignStopExecutionTime = new DeclarationExpression(var, Token.newSymbol(Types.ASSIGN, -1, -1), defaultValue);
//		this.code.addStatement(new ExpressionStatement(assignStopExecutionTime));
//		return this;
//	}
	
//	public MethodGen runFunction (String var, String function) {
//		//String __commentLog = _toCommentLog(__paramNamesValues);
////		ClassNode returnType = ClassHelper.STRING_TYPE;
////		VariableExpression varCommentLog = new VariableExpression(var, returnType);
////		MethodCallExpression loadVar = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, function, new ArgumentListExpression(new Expression[] {varParamNamesValues}));
////		DeclarationExpression assign = new DeclarationExpression(varCommentLog, Token.newSymbol(Types.ASSIGN, -1, -1), loadVar);
////		this.code.addStatement(new ExpressionStatement(assign));
//		return this;
//	}
	
	protected Map<String, VariableExpression> variables;

	protected VariableExpression _doLoadVariable(String name, Class<?> type) {
		return this._doLoadVariable(name, ClassNodeHelper.toClassNode(type));
	}
	
	protected VariableExpression _doLoadVariable(String name, ClassNode type) {
		VariableExpression result = null;
		result = _loadVariable(name);
		if (result == null) {
			result = new VariableExpression(name, type);
			this._saveVariable(name, result);
		}
		return result;
	}

	
	protected VariableExpression _loadVariable(String name) {
		VariableExpression result = null;
		if (name != null && this.variables != null) {
			result = this.variables.get(name);
		}
//		VariableExpression varEqBuilder = new VariableExpression("eqBuilder", EQUALS_BUILDER);
		return result;
	}

	protected VariableExpression _saveVariable(String name, VariableExpression variable) {
		VariableExpression result = null;
		if (name != null && variable != null) {
			this.variables = (this.variables != null ? this.variables : new HashMap<String, VariableExpression>());
			this.variables.put(name, variable);
		}
//		VariableExpression varEqBuilder = new VariableExpression("eqBuilder", EQUALS_BUILDER);
		return result;
	}

	protected void _saveVariableParam(Parameter param) {
		if (param != null) {
			String name = param.getName();
			VariableExpression variable = new VariableExpression(name, param.getType());
			this._saveVariable(name, variable);
		}
	}

	public void newVariable (Class<?> type, String name) {
		newVariable(ClassNodeHelper.toClassNode(type), name);
	}
	
	public void newVariable (ClassNode type, String name) {
		if (type != null && name != null) {
			VariableExpression variable = new VariableExpression(name, type);
			this._saveVariable(name, variable);
		}
	}

	/*
	 * 
	 * 
			cdg.run("super.doEquals", "eqBuilder", "obj");
				cdg.run("eqBuilder.append", "this." + field.getName(), "obj." + field.getName());
	 * 
	 */
//	public MethodGen run(String method, String... variables) {
//		if (method != null) {
//			int lastIdx = method.lastIndexOf(".");
//			Expression exprMethod = VariableExpression.THIS_EXPRESSION;
//			if (lastIdx >= 0) {
////				exprMethod = toExpression(method.substring(0, lastIdx));
//			}
////			VariableExpression.SUPER_EXPRESSION;
//			String methodName = (lastIdx < 0 ? method : method.substring(lastIdx + 1));
//			
//			ArgumentListExpression arguments = ArgumentListExpression.EMPTY_ARGUMENTS;
//			if (variables != null && variables.length > 0) {
//				arguments = new ArgumentListExpression();//new Expression[] {varEqBuilder, varObj};
//				for (String var : variables) {
////					Expression expression = toExpression(var);
////					if (expression != null) {
////						arguments.addExpression(expression);
////					}
//				}
//			}
//			
//			MethodCallExpression call = new MethodCallExpression(exprMethod, methodName, arguments);
//			this.code.addStatement(new ExpressionStatement(call));
//		}
////		cdg.run("super.doEquals", "eqBuilder", "obj");
////		for (FieldNode field : fields) {
////			cdg.run("eqBuilder.append", "this." + field.getName(), "obj." + field.getName());
//		
//		
//		// TODO Auto-generated method stub
//		return this;
//	}
	
	/*
	 * 


			//result = new Basic()
			BinaryExpression newResult = new BinaryExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), new ConstructorCallExpression(clazz, ArgumentListExpression.EMPTY_ARGUMENTS));
			code.addStatement(new ExpressionStatement(newResult));


	 * 
	 */
//	public MethodGen newConstructor(String var) {
//		VariableExpression varExpr = loadVariableExpression(var);
//		ClassNode clazz = varExpr.getType();
//		BinaryExpression newResult = new BinaryExpression(varExpr, Token.newSymbol(Types.ASSIGN, -1, -1), new ConstructorCallExpression(clazz, ArgumentListExpression.EMPTY_ARGUMENTS));
//		this.code.addStatement(new ExpressionStatement(newResult));
//		return this;
//	}

	/*
				//result.name = getName()
				MethodCallExpression getProperty = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, BaseFields.getGetterName(field), ArgumentListExpression.EMPTY_ARGUMENTS);
				BinaryExpression assignProperty = new BinaryExpression(new PropertyExpression(varResult, field.getName()), Token.newSymbol(Types.ASSIGN, -1, -1), getProperty);
				code.addStatement(new ExpressionStatement(assignProperty));
	 */
//	public MethodGen assign(String target, String source, String... params) {
////		Expression getSourceValue = toExpression(source);
////		Expression getTarget = toExpression(target);
////		BinaryExpression assign = new BinaryExpression(getTarget, Token.newSymbol(Types.ASSIGN, -1, -1), getSourceValue);
////		this.code.addStatement(new ExpressionStatement(assign));
//		return this;
//	}


	/*

		//return result


	 * 
	 * 
	 */
	public GMethod addReturn(String var) {
		return addReturn(this._loadVariable(var));
	}

	public GMethod addReturn(Expression expr) {
		ReturnStatement returnResult = new ReturnStatement(expr);
		this.code.addStatement(returnResult);
		return this;
	}

//	public MethodGen newDefinedVariable(Class<?> type, String name) {
//		return newDefinedVariable(ClassNodeHelper.toClassNode(type), name);
//	}

	/*
	 * 

		//org.effortless.util.ToLabel toLabel = new org.effortless.util.ToLabel()

	 * 
	 */
//	public MethodGen newDefinedVariable(ClassNode type, String name) {
//		VariableExpression var = new VariableExpression(name, type);
//		this._saveVariable(name, var);
//		ConstructorCallExpression newVar = new ConstructorCallExpression(type, ArgumentListExpression.EMPTY_ARGUMENTS);
//		DeclarationExpression decl = new DeclarationExpression(var, Token.newSymbol(Types.ASSIGN, -1, -1), newVar);
//		this.code.addStatement(new ExpressionStatement(decl));
//		return this;
//	}

	/*
	 * 

				FieldNode fieldKeyword = vm.getField("keyword");
				StaticMethodCallExpression callListBy = new StaticMethodCallExpression(clazz, "listBy", ArgumentListExpression.EMPTY_ARGUMENTS);
				ArgumentListExpression listExpr = new ArgumentListExpression(new Expression[] {new ConstantExpression("name"), new FieldExpression(fieldKeyword)});
				MethodCallExpression callLk = new MethodCallExpression(callListBy, op, listExpr);
				FieldNode fieldList = vm.getField("list");
				BinaryExpression assignList = new BinaryExpression(new FieldExpression(fieldList), Token.newSymbol(Types.ASSIGN, -1, -1), callLk);
				ExpressionStatement code = new ExpressionStatement(assignList);



	 * 
	 */
//	public MethodGen assign(String left, ClassNode clazz, String method, String... params) {
//		
////		FieldNode fieldKeyword = vm.getField("keyword");
////		StaticMethodCallExpression callListBy = new StaticMethodCallExpression(clazz, "listBy", ArgumentListExpression.EMPTY_ARGUMENTS);
////		ArgumentListExpression listExpr = new ArgumentListExpression(new Expression[] {new ConstantExpression("name"), new FieldExpression(fieldKeyword)});
////		MethodCallExpression callLk = new MethodCallExpression(callListBy, op, listExpr);
////		FieldNode fieldList = vm.getField("list");
////		BinaryExpression assignList = new BinaryExpression(new FieldExpression(fieldList), Token.newSymbol(Types.ASSIGN, -1, -1), callLk);
////		ExpressionStatement code = new ExpressionStatement(assignList);
//		
//		
//		// TODO Auto-generated method stub
//		return this;
//	}

	/*
	 * 

			        	org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "checked" + StringUtils.capFirst(module));


	 * 
	 * 
	 */
//	public MethodGen run(ClassNode clazz, String method, String... params) {
//		return this;
//	}
	
//	public MethodGen run(Class<?> clazz, String method, String... params) {
//		return run(ClassNodeHelper.toClassNode(clazz), method, params);
//	}
	
	protected static AstBuilder BUILDER = new AstBuilder();

//	public MethodGen add (String expr) {
//		List<ASTNode> list = toExpression(expr);
//		if (list != null) {
//			
//		}
//		return this;
//	}
	
//	protected List<ASTNode> toExpression (String source) {
//		List<ASTNode> result = null;
//		result = BUILDER.buildFromString(CompilePhase.SEMANTIC_ANALYSIS, true, source);
//		return result;
//	}

//	/*
//	 * 
//	 * 
//	 * 
//			Expression exprMethod = VariableExpression.THIS_EXPRESSION;
//			VariableExpression.SUPER_EXPRESSION;
//			
//			cdg.run("eqBuilder.append", "this." + field.getName(), "obj." + field.getName());
//
//		MethodCallExpression addField = new MethodCallExpression(varEqBuilder, "append", new ArgumentListExpression(new Expression[] {new FieldExpression(field), new PropertyExpression(varObj, field.getName())}));
//			
//	 * 
//	 */
//	protected Expression toExpression (String var) {
//		Expression result = null;
//		if (var != null) {
//			int lastIdx = var.lastIndexOf(".");
//			if (lastIdx < 0) {
//				result = loadVariableExpression(var);
//			}
//			else {
//				String first = var.substring(0, lastIdx);
//				String last = var.substring(lastIdx + 1);
//				boolean nomore = true;
//				nomore = (nomore && (first.lastIndexOf(".") < 0));
//				nomore = (nomore && (last.lastIndexOf(".") < 0));
//				if (nomore) {
//					if ("this".equals(first)) {
//						FieldNode field = this.clazzNode.getFields(last);
//						result = new FieldExpression(field);
//					}
//					else if ("super".equals(first)) {
//						FieldNode field = this.clazzNode.getFields(last);
//						result = new FieldExpression(field);
//					}
//					else {
//						
//					}
//				}
//			}
//		}
//		
//		
//		
//		return result;
//	}

	public Expression eq (Expression left, Expression right) {
		return _cmp(Token.newSymbol(Types.COMPARE_EQUAL, -1, -1), left, right);
	}

	public Expression notNull (Expression left) {
		return ne(left, ConstantExpression.NULL);
	}
	
	public Expression isNull (Expression left) {
		return eq(left, ConstantExpression.NULL);
	}
	
	public Expression notNull (String name) {
		return notNull(this._loadVariable(name));
	}
	
	public Expression isNull (String name) {
		return isNull(this._loadVariable(name));
	}
	
	public Expression cteNull () {
		return ConstantExpression.NULL;
	}
	
	public Expression ne (Expression left, Expression right) {
		return _cmp(Token.newSymbol(Types.COMPARE_NOT_EQUAL, -1, -1), left, right);
	}
	
	public Expression gt (Expression left, Expression right) {
		return _cmp(Token.newSymbol(Types.COMPARE_GREATER_THAN, -1, -1), left, right);
	}
	
	public Expression lt (Expression left, Expression right) {
		return _cmp(Token.newSymbol(Types.COMPARE_LESS_THAN, -1, -1), left, right);
	}
	
	public Expression ge (Expression left, Expression right) {
		return _cmp(Token.newSymbol(Types.COMPARE_GREATER_THAN_EQUAL, -1, -1), left, right);
	}
	
	public Expression le (Expression left, Expression right) {
		return _cmp(Token.newSymbol(Types.COMPARE_LESS_THAN_EQUAL, -1, -1), left, right);
	}

	public Expression assign (String left, Expression right) {
		return this.assign(this._loadVariable(left), right);
	}
	
	public Expression assign (String left, String right) {
		return this.assign(this._loadVariable(left), this._loadVariable(right));
	}
	
	public Expression assign (Expression left, String right) {
		return this.assign(left, this._loadVariable(right));
	}
	
	public Expression assign (Expression left, Expression right) {
		Expression result = null;
		if (left != null && right != null) {
			result = new BinaryExpression(left, Token.newSymbol(Types.ASSIGN, -1, -1), right);
		}
		return result;
	}

	public Expression cte (Object value) {
		return new ConstantExpression(value);
	}
	
	public Expression field (String fieldName) {
		return field(this.classGen.getClassNode().getField(fieldName));
	}

	public Expression field (GField field) {
		return (field != null ? field(field.getField()) : null);
	}

	public Expression field (FieldNode field) {
		Expression result = null;
		result = new FieldExpression(field);
		return result;
	}

	public Expression property (String property) {
		return property(VariableExpression.THIS_EXPRESSION, property);
	}
	
	public Expression property (String obj, String property) {
		return property(this._loadVariable(obj), property);
	}
	
	public Expression property (Expression obj, String property) {
		Expression result = null;
		result = new PropertyExpression(obj, property);
		return result;
	}

	protected Expression[] toExpressions(String... variables) {
		Expression[] result = null;
		result = new Expression[variables.length];
		int idx = 0;
		for (String variable : variables) {
			result[idx] = this._loadVariable(variable);
			idx += 1;
		}
		return result;
	}


	public Expression triple (Expression condition, Expression exprTrue, Expression exprFalse) {
		TernaryExpression result = null;
		BooleanExpression booleanExpression = _toBooleanExpression(condition);
		result = new TernaryExpression(booleanExpression, exprTrue, exprFalse);
		return result;
	}
	
	public Expression triple (Expression condition, String varTrue, Expression exprFalse) {
		return triple(condition, this._loadVariable(varTrue), exprFalse);
	}
	
	public Expression triple (Expression condition, Expression exprTrue, String varFalse) {
		return triple(condition, exprTrue, this._loadVariable(varFalse));
	}

	public Expression triple (Expression condition, String varTrue, String varFalse) {
		return triple(condition, this._loadVariable(varTrue), this._loadVariable(varFalse));
	}

	public Expression triple (String variable, Expression exprTrue, Expression exprFalse) {
		return triple(this._loadVariable(variable), exprTrue, exprFalse);
	}

	public Expression triple (String variable, String varTrue, Expression exprFalse) {
		return triple(this._loadVariable(variable), this._loadVariable(varTrue), exprFalse);
	}
	
	public Expression triple (String variable, Expression exprTrue, String varFalse) {
		return triple(this._loadVariable(variable), exprTrue, this._loadVariable(varFalse));
	}

	public Expression triple (String variable, String varTrue, String varFalse) {
		return triple(this._loadVariable(variable), this._loadVariable(varTrue), this._loadVariable(varFalse));
	}

	
	
	
	protected ArgumentListExpression toListExpression (Expression... arguments) {
		ArgumentListExpression result = null;
		result = ArgumentListExpression.EMPTY_ARGUMENTS;
		if (arguments != null && arguments.length > 0) {
			result = new ArgumentListExpression(arguments);
		}
		return result;
	}
	
	public Expression cteSuper () {
		return VariableExpression.SUPER_EXPRESSION;
	}
	
	public Expression cteThis () {
		return VariableExpression.THIS_EXPRESSION;
	}
	

	
	public Expression call (String obj, String method) {
		return call(this._loadVariable(obj), method, (Expression[])null);
	}
	
	public Expression call (String obj, String method, String... variables) {
		return call(this._loadVariable(obj), method, toExpressions(variables));
	}
	
	public Expression call (String obj, String method, Expression... arguments) {
		return call(this._loadVariable(obj), method, arguments);
	}
	
	public Expression call (Expression obj, String method, String... variables) {
		return call(obj, method, toExpressions(variables));
	}
	
	public Expression call (Expression obj, String method, Expression... arguments) {
		Expression result = null;
		ArgumentListExpression list = toListExpression(arguments);
		result = new MethodCallExpression(obj, method, list);
		return result;
	}

	public Expression call (String method, Expression... arguments) {
		return call(VariableExpression.THIS_EXPRESSION, method, arguments);
	}
	
	public Expression call (Expression obj, String method) {
		return call(obj, method, (Expression[])null);
	}
	
	public Expression call (String method) {
		return call(VariableExpression.THIS_EXPRESSION, method, (Expression[])null);
	}

	
	
	public Expression callStatic (Class<?> type, String method, String... variables) {
		return callStatic(ClassNodeHelper.toClassNode(type), method, toExpressions(variables));
	}
	
	public Expression callStatic (Class<?> type, String method) {
		return callStatic(ClassNodeHelper.toClassNode(type), method, (Expression[])null);
	}
	
	public Expression callStatic (Class<?> type, String method, Expression... arguments) {
		return callStatic(ClassNodeHelper.toClassNode(type), method, arguments);
	}
	
	public Expression callStatic (ClassNode type, String method, String... variables) {
		return callStatic(type, method, toExpressions(variables));
	}
	
	public Expression callStatic (ClassNode type, String method) {
		return callStatic(type, method, (Expression[])null);
	}
	
	public Expression callStatic (ClassNode type, String method, Expression... arguments) {
		Expression result = null;
		ArgumentListExpression list = toListExpression(arguments);
		result = new StaticMethodCallExpression(type, method, list);
		return result;
	}

	
	
	protected Expression _cmp (Token op, Expression left, Expression right) {
		Expression result = null;
		if (left != null && right != null) {
			result = new BinaryExpression(left, op, right);
		}
		return result;
	}
	
	
	public Expression and (Expression... expr) {
		Expression result = null;
		if (expr != null) {
			result = _binaries(Token.newSymbol(Types.LOGICAL_AND, -1, -1), expr);
		}
		return result;
	}
	
	public Expression or (Expression... expr) {
		Expression result = null;
		if (expr != null) {
			result = _binaries(Token.newSymbol(Types.LOGICAL_OR, -1, -1), expr);
		}
		return result;
	}

	protected Expression _binaries (Token op, Expression... expr) {
		Expression result = null;
		if (expr != null) {
			if (expr.length == 1) {
				result = expr[0];
			}
			else if (expr.length >= 2) {
				result = new BinaryExpression(expr[0], op, expr[1]);
				for (int i = 2; i < expr.length; i++) {
					result = new BinaryExpression(result, op, expr[i]);
				}
			}
		}
		return result;
	}

	public Expression var (String name) {
		return this._loadVariable(name);
	}
	
	public Expression not (String variable) {
		return this.not(this._loadVariable(variable));
	}
	
	public Expression not (Expression expr) {
		Expression result = null;
		result = (expr != null ? new NotExpression(expr) : null);
		return result;
	}

	public GMethod add (Expression expr) {
		if (expr != null) {
			this.code.addStatement(new ExpressionStatement(expr));
		}
		return this;
	}

	public GMethod addIf(Expression condition, GMethod ifCode) {
		return this.addIf(condition, ifCode, (GMethod)null);
	}
	
	public GMethod addIf(Expression condition, GMethod ifCode, GMethod elseBlock) {
		return this.addIf(condition, (ifCode != null ? ifCode.getCode() : null), (elseBlock != null ? elseBlock.getCode() : null));
	}

	public GMethod addIf(Expression condition, Statement ifBlock) {
		return addIf(condition, ifBlock, null);
	}
	
	protected BooleanExpression _toBooleanExpression (Expression condition) {
		BooleanExpression result = null;
		if (condition instanceof BooleanExpression) {
			result = (BooleanExpression)condition;
		}
		else {
			result = new BooleanExpression(condition);
		}
		return result;
	}
	
	public GMethod addIf(Expression condition, Statement ifBlock, Statement elseBlock) {
		BooleanExpression booleanExpression = _toBooleanExpression(condition);
		ifBlock = (ifBlock != null ? ifBlock : EmptyStatement.INSTANCE);
		elseBlock = (elseBlock != null ? elseBlock : EmptyStatement.INSTANCE);
		IfStatement ifStatement = new IfStatement(booleanExpression, ifBlock, elseBlock);
		this.code.addStatement(ifStatement);
		return this;
	}

	public GMethod newBlock() {
		GMethod result = null;
		result = new GMethod();
		
		result.methodNode = this.methodNode;
		result.classGen = this.classGen;
		result.code = new BlockStatement();
		if (this.variables == null) {
			this.variables = (this.variables != null ? this.variables : new HashMap<String, VariableExpression>());
		}
		result.variables = this.variables;
		result.previousCode = this.previousCode;
		
		return result;
	}

	public Expression enumValue (Class<?> type, String enumValue) {
		return enumValue(ClassNodeHelper.toClassNode(type), enumValue);
	}

	public Expression enumValue (ClassNode type, String enumValue) {
		Expression result = null;
		result = new PropertyExpression(new ClassExpression(type), new ConstantExpression(enumValue));
		return result;
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

	public Expression cast(ClassNode type, Expression value) {
		CastExpression result = null;
		result = new CastExpression(type, value);
		return result;
	}

	public Expression cast(Class<?> type, Expression value) {
		return cast(ClassNodeHelper.toClassNode(type), value);
	}
	
	public Expression cast(ClassNode type, String var) {
		return cast(type, this._loadVariable(var));
	}

	public Expression cast(Class<?> type, String var) {
		return cast(ClassNodeHelper.toClassNode(type), this._loadVariable(var));
	}

	protected BlockStatement previousCode;

	public void addPreviousCode () {
		if (this.previousCode != null) {
			this.code.addStatement(this.previousCode);
		}
	}
	
	public int getPreviousCodeLength () {
		int result = 0;
		result = (this.previousCode != null && this.previousCode.getStatements() != null ? this.previousCode.getStatements().size() : 0);
		return result;
	}
	
	public void preserveCode() {
		BlockStatement block = null;
		try {
			block = (BlockStatement)this.previousCode;
		}
		catch (ClassCastException e) {
			Statement stm = this.methodNode.getCode();
			if (stm != null) {
				block = new BlockStatement();
				block.addStatement(this.methodNode.getCode());
			}
		}
		this.previousCode = block;
	}

	public Expression clazz (Class<?> type) {
		return clazz(ClassNodeHelper.toClassNode(type));
	}
	
	public Expression clazz (ClassNode type) {
		return new ClassExpression(type);
	}
	
	public Expression array (Expression... expressions) {
		Expression result = null;
		result = ConstantExpression.NULL;
		if (expressions != null && expressions.length > 0) {
			List<Expression> values = Collections.asList(expressions);
			result = new ListExpression(values);
		}
		return result;
	}
	
	public Expression arrayTypes (Parameter... params) {
		Expression result = null;
		Expression[] arrayValues = null;
		if (params != null && params.length > 0) {
			List<Expression> values = new ArrayList<Expression>();
			for (Parameter param : params) {
				values.add(clazz(param.getType()));
			}
			arrayValues = values.toArray(new Expression[0]);
		}
		result = array(arrayValues);
		return result;
	}
	
	public Expression arrayParameters (Parameter... params) {
		Expression result = null;
		Expression[] arrayValues = null;
		if (params != null && params.length > 0) {
			List<Expression> values = new ArrayList<Expression>();
			for (Parameter param : params) {
				values.add(this._loadVariable(param.getName()));
			}
			arrayValues = values.toArray(new Expression[0]);
		}
		result = array(arrayValues);
		return result;
	}
	
	public Expression arrayNameValue (Parameter... params) {
		Expression result = null;
		Expression[] arrayValues = null;
		if (params != null && params.length > 0) {
			List<Expression> values = new ArrayList<Expression>();
			for (Parameter param : params) {
				values.add(this.cte(param.getName()));
				values.add(this._loadVariable(param.getName()));
			}
			arrayValues = values.toArray(new Expression[0]);
		}
		result = array(arrayValues);
		return result;
	}

	public void addTryCatch(GMethod tryBlock, Object[]... catchs) {
		TryCatchStatement tcs = new TryCatchStatement(tryBlock.getCode(), new EmptyStatement());
		for (Object[] item : catchs) {
			if (item != null && item.length >= 3) {
				Class<?> type = (Class<?>)item[0];
				String name = (String)item[1];
				GMethod code = (GMethod)item[2];
				
				ClassNode varExceptionType = ClassNodeHelper.toClassNode(type);
//				VariableExpression varException = this._loadVariable(name);
//				if (varException == null) {
//					varException = new VariableExpression(name, varExceptionType);
//					this._saveVariable(name, varException);
//				}
				
				CatchStatement catchException = new CatchStatement(new Parameter(varExceptionType, name), code.getCode());
				tcs.addCatch(catchException);
			}
		}
		
		this.code.addStatement(tcs);
	}

	public Expression plus (Expression left, Expression right) {
		BinaryExpression result = null;
		result = new BinaryExpression(left, Token.newSymbol(Types.PLUS, -1, -1), right);
		return result;
	}

	public GMethod throwException(String name) {
		return throwException(this._loadVariable(name));
	}
	
	public GMethod throwException(Expression expr) {
		ThrowStatement throwE = new ThrowStatement(expr);
		this.code.addStatement(throwE);
		return this;
	}

	public GMethod addFirstPreserveCode() {
		List<Statement> statements = (this.previousCode != null ? this.previousCode.getStatements() : null);
		int size = (statements != null ? statements.size() : 0);
		if (size > 0) {
			Statement stm = statements.remove(0);
			if (stm != null) {
				this.code.addStatement(stm);
			}
		}
		return this;
	}

	public GMethod addLastPreserveCode() {
		List<Statement> statements = (this.previousCode != null ? this.previousCode.getStatements() : null);
		int size = (statements != null ? statements.size() : 0);
		if (size > 0) {
			Statement stm = statements.remove(size - 1);
			if (stm != null) {
				this.code.addStatement(stm);
			}
		}
		return this;
	}

	
	public GClass addAnonymousClass (Class<?> superClass) {
		return addAnonymousClass(ClassNodeHelper.toClassNode(superClass));
	}
	
	public GClass addAnonymousClass (ClassNode superClass) {
		GClass result = null;
		String suffix = "" + (this.classGen.getInnerClassesSize() + 1);
//		String suffix = System.currentTimeMillis();
//		String name = this.clazz.getName() + "$" + suffix;
//		String name = suffix;
		String name = this.classGen.getClassNode().getNameWithoutPackage() + "$" + suffix;
		InnerClassNode inner = new InnerClassNode(this.classGen.getClassNode(), name, Opcodes.ACC_PUBLIC, superClass);
		inner.setAnonymous(true);
		result = new GClass(inner, this.classGen.getSourceUnit());
//		this.add(new ClassExpression(inner));
		this.classGen.getSourceUnit().getAST().addClass(inner);
		return result;
	}
	
	
	
}
