package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.effortless.core.ModelException;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.gen.GMethod;
import org.effortless.model.AbstractEntity;

public class AlterActionsClassTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass clazz) {
		// TODO Auto-generated method stub
		
			if (clazz != null) {
				List<MethodNode> methods = clazz.getClassNode().getAllDeclaredMethods();
				for (MethodNode method : methods) {
					if (checkValidMethod(method)) {
						modifyAction(method);
					}
				}
			}
	}

	protected static final String[] EXCLUSION_LIST = {"wait", "toString", "notify", "getClass", "notifyAll", "finalize", "registerNatives", "equals", "clone", "hashCode"};
	
	protected boolean checkValidMethod(MethodNode method) {
		boolean result = true;
		String methodName = method.getName();
		if (methodName != null) {
			for (String exclude : EXCLUSION_LIST) {
				if (methodName.equals(exclude)) {
					result = false;
					break;
				}
			}
		}
		if (result && !method.isPublic()) {
			result = false;
		}
		return result;
	}
	
	protected GMethod modifyAction(MethodNode method) {
		GMethod result = null;
		
		GMethod mg = new GMethod(method);
		result = mg;

		int statementsLength = mg.getPreviousCodeLength();

		String methodName = method.getName();
		ClassNode returnType = method.getReturnType();
			
		if (returnType != null && statementsLength > 1) {
			mg.addFirstPreserveCode();
		}
		
		Parameter[] parameters = method.getParameters();

		//Class<?>[] __paramTypes = [String.class, Integer.class];
		ClassNode classArrayType = ClassHelper.CLASS_Type.makeArray();
		mg.declVariable(classArrayType, "__paramTypes", mg.arrayTypes(parameters));
				
		//Object[] __paramValues = [text, ammount];
		ClassNode objectArrayType = ClassHelper.OBJECT_TYPE.makeArray();
		mg.declVariable(objectArrayType, "__paramValues", mg.arrayParameters(parameters));
				

		GMethod ifCode = mg.newBlock();
		ifCode.add(ifCode.call("__startExecutionTime"));//__startExecutionTime();
		ifCode.declVariable(ClassHelper.boolean_TYPE, "__stopExecutionTime", ifCode.cteFalse());//boolean __stopExecutionTime = false;
		ifCode.declVariable(Long.class, "__executionTime", ifCode.cteNull());//Long __executionTime = null;
		ifCode.declVariable(ClassHelper.boolean_TYPE, "__saveLog", ifCode.cteTrue());//boolean __saveLog = true;
		ifCode.declVariable(objectArrayType, "__paramNamesValues", ifCode.arrayNameValue(parameters));//Object[] __paramNamesValues = ["text", text, "ammount", ammount];
		ifCode.add(ifCode.assign("__commentLog", ifCode.call("_toCommentLog", "__paramNamesValues")));//String __commentLog = _toCommentLog(__paramNamesValues);
		GMethod tryBlock = ifCode.newBlock();
		tryBlock.addPreviousCode();//oldCode
		tryBlock.add(tryBlock.assign("__executionTime", tryBlock.call("__stopExecutionTime")));//__executionTime = __stopExecutionTime();
		tryBlock.add(tryBlock.assign("__stopExecutionTime", tryBlock.cteTrue()));//__stopExecutionTime = true;
		GMethod blockSaveLog = tryBlock.newBlock();
		blockSaveLog.add(blockSaveLog.call("trySaveActionLog", blockSaveLog.cte(methodName), blockSaveLog.var("__commentLog"), blockSaveLog.var("__executionTime")));//this.trySaveActionLog("doAction", __commentLog, __executionTime);
		tryBlock.addIf(tryBlock.var("__saveLog"), blockSaveLog);//if (__saveLog) {
		
		GMethod catchModel = ifCode.newBlock();
		catchModel.newVariable(ModelException.class, "e1");
		GMethod ifCodeCatch = catchModel.newBlock();
		ifCodeCatch.add(ifCodeCatch.assign(ifCodeCatch.var("__executionTime"), ifCodeCatch.call("__stopExecutionTime")));//__executionTime = __stopExecutionTime();
		catchModel.addIf(catchModel.not(catchModel.var("__stopExecutionTime")), ifCodeCatch);//if (!__stopExecutionTime) {
		catchModel.add(catchModel.call("trySaveLogException", catchModel.var("e1"), catchModel.plus(catchModel.cte(AbstractEntity.ERROR), catchModel.cte(methodName)), catchModel.var("__executionTime")));//this.trySaveLogException(e, ERROR + "methodName", __executionTime);
		catchModel.throwException("e1");
		
		GMethod catchGeneral = ifCode.newBlock();
		catchGeneral.newVariable(Exception.class, "e2");
		GMethod ifCodeCatch2 = catchGeneral.newBlock();
		ifCodeCatch2.add(ifCodeCatch2.assign(ifCodeCatch2.var("__executionTime"), ifCodeCatch2.call("__stopExecutionTime")));//__executionTime = __stopExecutionTime();
		catchGeneral.addIf(catchGeneral.not(catchGeneral.var("__stopExecutionTime")), ifCodeCatch2);//if (!__stopExecutionTime) {
		catchGeneral.add(catchGeneral.call("trySaveLogException", catchGeneral.var("e2"), catchGeneral.plus(catchGeneral.cte(AbstractEntity.ERROR), catchGeneral.cte(methodName)), catchGeneral.var("__executionTime")));//this.trySaveLogException(e, ERROR + "methodName", __executionTime);
		catchGeneral.throwException(catchGeneral.callConstructor(ModelException.class, catchGeneral.var("e2")));
		
		ifCode.addTryCatch(tryBlock, new Object[]{ModelException.class, "e1", catchModel}, new Object[] {Exception.class, "e2", catchGeneral});
		mg.addIf(mg.call("_checkSecurityAction", mg.cte(methodName), mg.clazz(returnType), mg.var("__paramTypes"), mg.var("__paramValues")), ifCode);//if (this._checkSecurityAction("methodName", String.class, paramTypes, paramValues)) {

		if (returnType != null && !ClassHelper.VOID_TYPE.equals(returnType)) {
			mg.addLastPreserveCode();
		}
		
		return result;
	}
	
}
