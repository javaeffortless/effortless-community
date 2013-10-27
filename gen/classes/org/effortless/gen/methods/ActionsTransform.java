package org.effortless.gen.methods;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.ModelException;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.gen.GMethod;
import org.effortless.model.AbstractEntity;

public class ActionsTransform extends Object implements Transform<GClass> {

	public void process (GClass clazz) {
		if (clazz != null) {
			List<GMethod> methods = clazz.getAllDeclaredMethods();
			for (GMethod method : methods) {
				if (!method.checkBaseMethod() && method.checkPublic()) {
					modifyMethod(method);
				}
			}
		}
	}
	
	protected static final ClassNode MODEL_EXCEPTION_CLAZZ = ClassNodeHelper.toClassNode(ModelException.class);
	protected static final ClassNode EXCEPTION_CLAZZ = ClassNodeHelper.toClassNode(Exception.class);
	
	
	/*
	 * 


	public String doAction(String text, Integer ammount) {// throws ModelException {
		String result = null;
		Class<?>[] __paramTypes = [String.class, Integer.class];
		Object[] __paramValues = [text, ammount];
		if (this._checkSecurityAction("doAction", String.class, __paramTypes, __paramValues)) {
			__startExecutionTime();
			boolean __stopExecutionTime = false;
			Long __executionTime = null;
			boolean __saveLog = true;
			Object[] __paramNamesValues = ["text", text, "ammount", ammount];
			String __commentLog = __toCommentLog(__paramNamesValues);
			try {
				System.out.println("EJECUTANDO doAction");
				__executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				if (__saveLog) {
					this.trySaveActionLog("doAction", __commentLog, __executionTime);
				}
			}
			catch (org.effortless.model.exceptions.ModelException e) {
				if (!__stopExecutionTime) {
					__executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR + "doAction", __executionTime);
				throw e;
			}
			catch (Exception e) {
				if (!__stopExecutionTime) {
					__executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR + "methodName", __executionTime);
				throw new org.effortless.model.exceptions.ModelException(e);
			}
		}
		return result;
	}


	 * 
	 */

	protected void modifyMethod(GMethod _method) {
		MethodNode method = _method.getMethodNode();
		Statement oldCode = method.getCode();
		BlockStatement oldBlock = null;
		try {
			oldBlock = (BlockStatement)oldCode;
		}
		catch (ClassCastException e) {
			oldBlock = new BlockStatement();
			oldBlock.addStatement(oldCode);
			method.setCode(oldBlock);
		}
		List<Statement> oldStatements = (oldBlock != null ? oldBlock.getStatements() : null);
		int statementsLength = (oldStatements != null ? oldStatements.size() : 0);
		if (true || statementsLength > 0) {
			String methodName = method.getName();
			ClassNode returnType = method.getReturnType();
			Statement firstStatement = (returnType != null && statementsLength > 1 ? oldStatements.get(0) : null);
			Statement lastStatement = (returnType != null && !ClassHelper.VOID_TYPE.equals(returnType) ? oldStatements.get(oldStatements.size() - 1) : null);
			
			List<Statement> oldRestStatements = new ArrayList<Statement>();
			for (Statement stm : oldStatements) {
				oldRestStatements.add(stm);
			}
			if (oldRestStatements != null) {
				if (firstStatement != null) {
					oldRestStatements.remove(firstStatement);
				}
				if (lastStatement != null) {
					oldRestStatements.remove(lastStatement);
				}
			}
			
			if (true) {
				BlockStatement newBlock = new BlockStatement();
				if (firstStatement != null) {
					newBlock.addStatement(firstStatement);
				}

				Parameter[] parameters = method.getParameters();
				//Class<?>[] __paramTypes = [String.class, Integer.class];
				
//				ClassNode classArrayType = new ClassNode(Class[].class);
//				classArrayType.redirect();
//				classArrayType = ClassHelper.make("Class[]");
				ClassNode classArrayType = ClassHelper.CLASS_Type.makeArray();
				VariableExpression varParamTypes = new VariableExpression("__paramTypes", classArrayType);
				Expression valueParamTypes = ConstantExpression.NULL;
				if (parameters != null && parameters.length > 0) {
					List<Expression> values = new ArrayList<Expression>();
					for (Parameter param : parameters) {
						values.add(new ClassExpression(param.getType()));
					}
					valueParamTypes = new ListExpression(values);
				}
				DeclarationExpression declParamTypes = new DeclarationExpression(varParamTypes, Token.newSymbol(Types.ASSIGN, -1, -1), valueParamTypes);
				newBlock.addStatement(new ExpressionStatement(declParamTypes));
				
				//Object[] __paramValues = [text, ammount];
				ClassNode objectArrayType = ClassHelper.OBJECT_TYPE.makeArray();
				VariableExpression varParamValues = new VariableExpression("__paramValues", objectArrayType);
				Expression valueParamValues = ConstantExpression.NULL;
				if (parameters != null && parameters.length > 0) {
					List<Expression> values = new ArrayList<Expression>();
					for (Parameter param : parameters) {
						values.add(new VariableExpression(param.getName(), param.getType()));
					}
					valueParamValues = new ListExpression(values);
				}
				DeclarationExpression declParamValues = new DeclarationExpression(varParamValues, Token.newSymbol(Types.ASSIGN, -1, -1), valueParamValues);
				newBlock.addStatement(new ExpressionStatement(declParamValues));
				
				//if (this._checkSecurityAction("methodName", String.class, paramTypes, paramValues)) {
				ArgumentListExpression paramsSecurity = new ArgumentListExpression(new Expression[] {new ConstantExpression(methodName), new ClassExpression(method.getReturnType()), varParamTypes, varParamValues});
				MethodCallExpression callSecurity = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "_checkSecurityAction", paramsSecurity);
				BlockStatement ifBlock = new BlockStatement();
				IfStatement ifSecurity = new IfStatement(new BooleanExpression(callSecurity), ifBlock, new EmptyStatement());
				if (ifSecurity != null) {
					newBlock.addStatement(ifSecurity);
				}

				//__startExecutionTime();
				MethodCallExpression callStartExecutionTime = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "__startExecutionTime", ArgumentListExpression.EMPTY_ARGUMENTS);
				ifBlock.addStatement(new ExpressionStatement(callStartExecutionTime));
				
				//boolean __stopExecutionTime = false;
				VariableExpression varStopExecutionTime = new VariableExpression("__stopExecutionTime", ClassHelper.boolean_TYPE);
				DeclarationExpression assignStopExecutionTime = new DeclarationExpression(varStopExecutionTime, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.PRIM_FALSE);
				ifBlock.addStatement(new ExpressionStatement(assignStopExecutionTime));
				
				//Long __executionTime = null;
				VariableExpression varExecutionTime = new VariableExpression("__executionTime", ClassHelper.Long_TYPE);
				DeclarationExpression assignExecutionTime = new DeclarationExpression(varExecutionTime, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
				ifBlock.addStatement(new ExpressionStatement(assignExecutionTime));
				
				//boolean __saveLog = true;
				VariableExpression varSaveLog = new VariableExpression("__saveLog", ClassHelper.boolean_TYPE);
				DeclarationExpression assignSaveLog = new DeclarationExpression(varSaveLog, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.PRIM_TRUE);
				ifBlock.addStatement(new ExpressionStatement(assignSaveLog));
				
				//Object[] __paramNamesValues = ["text", text, "ammount", ammount];
				VariableExpression varParamNamesValues = new VariableExpression("__paramValues", objectArrayType);
				Expression valueParamNamesValues = ConstantExpression.NULL;
				if (parameters != null && parameters.length > 0) {
					List<Expression> values = new ArrayList<Expression>();
					for (Parameter param : parameters) {
						values.add(new ConstantExpression(param.getName()));
						values.add(new VariableExpression(param.getName(), param.getType()));
					}
					valueParamNamesValues = new ListExpression(values);
				}
				DeclarationExpression declParamNamesValues = new DeclarationExpression(varParamNamesValues, Token.newSymbol(Types.ASSIGN, -1, -1), valueParamNamesValues);
				ifBlock.addStatement(new ExpressionStatement(declParamNamesValues));
				
				//String __commentLog = _toCommentLog(__paramNamesValues);
				VariableExpression varCommentLog = new VariableExpression("__commentLog", ClassHelper.STRING_TYPE);
				MethodCallExpression loadVarCommentLog = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "__toCommentLog", new ArgumentListExpression(new Expression[] {varParamNamesValues}));
				DeclarationExpression assignCommentLog = new DeclarationExpression(varCommentLog, Token.newSymbol(Types.ASSIGN, -1, -1), loadVarCommentLog);
				ifBlock.addStatement(new ExpressionStatement(assignCommentLog));

				//try {
				BlockStatement tryStatement = new BlockStatement();
				TryCatchStatement tcs = new TryCatchStatement(tryStatement, new EmptyStatement());
				ifBlock.addStatement(tcs);

				{
				//oldCode
					if (oldRestStatements != null) {
						tryStatement.addStatements(oldRestStatements);
					}
					
				//__executionTime = __stopExecutionTime();
					MethodCallExpression loadVarExecutionTime = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "__stopExecutionTime", ArgumentListExpression.EMPTY_ARGUMENTS);
					BinaryExpression assignLoadExecutionTime = new BinaryExpression(varExecutionTime, Token.newSymbol(Types.ASSIGN, -1, -1), loadVarExecutionTime);
					tryStatement.addStatement(new ExpressionStatement(assignLoadExecutionTime));
				
				
				//__stopExecutionTime = true;
					BinaryExpression stopExecutionTime = new BinaryExpression(varStopExecutionTime, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.PRIM_TRUE);
					tryStatement.addStatement(new ExpressionStatement(stopExecutionTime));
					
					
				//if (__saveLog) {
				//}
					BlockStatement blockIfSaveLog = new BlockStatement();
					IfStatement ifSaveLog = new IfStatement(new BooleanExpression(varSaveLog), blockIfSaveLog, new EmptyStatement());
					tryStatement.addStatement(ifSaveLog);
					
					//this.trySaveActionLog("doAction", __commentLog, __executionTime);
					MethodCallExpression trySaveActionLog = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "trySaveActionLog", new ArgumentListExpression(new Expression[] {new ConstantExpression(methodName), varCommentLog, varExecutionTime}));
					blockIfSaveLog.addStatement(new ExpressionStatement(trySaveActionLog));
				}
				
				//catch (ModelException e) {
				tcs.addCatch(gCatchException(method, _method.getClassGen().getClassNode(), _method.getClassGen().getSourceUnit(), MODEL_EXCEPTION_CLAZZ, varStopExecutionTime, varExecutionTime, null));
				
				//catch (Exception e) {
				tcs.addCatch(gCatchException(method, _method.getClassGen().getClassNode(), _method.getClassGen().getSourceUnit(), EXCEPTION_CLAZZ, varStopExecutionTime, varExecutionTime, MODEL_EXCEPTION_CLAZZ));
				
//				BlockStatement block = new BlockStatement();
				if (lastStatement != null) {
					newBlock.addStatement(lastStatement);
				}
				method.setCode(newBlock);
			}
			else {
				oldStatements.clear();
				
				if (firstStatement != null) {
					oldBlock.addStatement(firstStatement);
				}

				oldBlock.addStatement(gPrintln("begin " + methodName));
				
				VariableExpression vExpr = new VariableExpression("_endMsg", ClassHelper.STRING_TYPE);
				DeclarationExpression decl = new DeclarationExpression(vExpr, Token.newSymbol(Types.ASSIGN, -1, -1), new ConstantExpression("end " + methodName));
				oldBlock.addStatement(new ExpressionStatement(decl));
				
				if (oldRestStatements != null) {
					oldBlock.addStatements(oldRestStatements);
				}
				
				oldBlock.addStatement(gPrintln(vExpr));
				
				if (lastStatement != null) {
					oldBlock.addStatement(lastStatement);
				}
			}
		}
	}

	protected static Statement gPrintln (String msg) {
		return gPrintln(new ConstantExpression(msg));
	}
	
	protected static Statement gPrintln (Expression msg) {
		Statement result = null;
		ClassExpression system = new ClassExpression(new ClassNode(System.class));
		PropertyExpression systemOut = new PropertyExpression(system, "out");
		MethodCallExpression beginCall = new MethodCallExpression(systemOut, "println", new ArgumentListExpression(new Expression[] {msg}));
		result = new ExpressionStatement(beginCall);
		return result;
	}
	
	protected static CatchStatement gCatchException(MethodNode method, ClassNode clazz, SourceUnit sourceUnit, ClassNode exception, VariableExpression varStopExecutionTime, VariableExpression varExecutionTime, ClassNode constructorClassNode) {
		CatchStatement result = null;
		//catch (ModelException e) {
		{
			String methodName = method.getName();
			
			BlockStatement blockCatch = new BlockStatement();
			CatchStatement catchException = new CatchStatement(new Parameter(exception, "e"), blockCatch);
			result = catchException;
//			tcs.addCatch(catchException);
			
			VariableExpression varException = new VariableExpression("e", exception);
			//if (!__stopExecutionTime) {
			//}
			BlockStatement blockCatchIfContent = new BlockStatement();
			IfStatement blockCatchIf = new IfStatement(new BooleanExpression(new NotExpression(varStopExecutionTime)), blockCatchIfContent, new EmptyStatement());
			blockCatch.addStatement(blockCatchIf);
	
			//__executionTime = __stopExecutionTime();
			MethodCallExpression loadVarExecutionTime = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "__stopExecutionTime", ArgumentListExpression.EMPTY_ARGUMENTS);
			BinaryExpression assignLoadExecutionTime = new BinaryExpression(varExecutionTime, Token.newSymbol(Types.ASSIGN, -1, -1), loadVarExecutionTime);
			blockCatchIfContent.addStatement(new ExpressionStatement(assignLoadExecutionTime));
			
			//this.trySaveLogException(e, ERROR + "methodName", __executionTime);
			BinaryExpression varErrorName = new BinaryExpression(new ConstantExpression(AbstractEntity.ERROR), Token.newSymbol(Types.PLUS, -1, -1), new ConstantExpression(methodName));
			MethodCallExpression callTrySaveLogException = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, "trySaveLogException", new ArgumentListExpression(new Expression[] {varException, varErrorName, varExecutionTime}));
			blockCatch.addStatement(new ExpressionStatement(callTrySaveLogException));
	
			//throw e;
			ThrowStatement throwE = null;
			if (constructorClassNode != null) {
				ConstructorCallExpression newModelException = new ConstructorCallExpression(constructorClassNode, new ArgumentListExpression(new Expression[] {varException}));
				throwE = new ThrowStatement(newModelException);
			}
			else {
				throwE = new ThrowStatement(varException);
			}
			blockCatch.addStatement(throwE);
		}
		
		return result;
	}
	
}
