package org.effortless.gen.methods;

import java.util.List;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.InfoModel;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements

	protected void doToString(org.apache.commons.lang3.builder.ToStringBuilder toStringBuilder) {
		super.doToString(toStringBuilder);
		toStringBuilder.append("name", this.name);
	}
	
	
 * 
 * 
 * @author jesus
 *
 */
public class ToStringTransform extends Object {

	public static final ClassNode TO_STRING_BUILDER = new ClassNode(org.apache.commons.lang3.builder.ToStringBuilder.class);
	
	public static void processClass (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
			List<FieldNode> fields = InfoModel.listNotNullUnique(clazz);
			
				BlockStatement code = new BlockStatement();

				VariableExpression varToStringBuilder = new VariableExpression("toStringBuilder", TO_STRING_BUILDER);

				//super.doToString(toStringBuilder);
				MethodCallExpression addSuper = new MethodCallExpression(VariableExpression.SUPER_EXPRESSION, "doToString", new ArgumentListExpression(varToStringBuilder));
				code.addStatement(new ExpressionStatement(addSuper));
				
				for (FieldNode field : fields) {
					//toStringBuilder.append("name", this.name);
					MethodCallExpression addField = new MethodCallExpression(varToStringBuilder, "append", new ArgumentListExpression(new Expression[] {new ConstantExpression(field.getName()), new FieldExpression(field)}));
					code.addStatement(new ExpressionStatement(addField));
				}

				Parameter[] parameterTypes = new Parameter [] {new Parameter(TO_STRING_BUILDER, "toStringBuilder")};
				MethodNode method = new MethodNode("doToString", Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
				clazz.addMethod(method);
		}
	}
	
}
