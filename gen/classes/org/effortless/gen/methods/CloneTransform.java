package org.effortless.gen.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
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
import org.codehaus.groovy.ast.expr.FieldExpression;
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
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.core.ModelException;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.model.AbstractEntity;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements

protected Basic createClone () {
	Basic result = null
result = new Basic()
result.name = getName()
result.comentario = getComentario()
return result
	}
	
 * 
 * 
 * @author jesus
 *
 */
public class CloneTransform {

	public static void processClass (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
			List<FieldNode> fields = clazz.getFields();
			
				BlockStatement code = new BlockStatement();
	
				//Basic result = null
				VariableExpression varResult = new VariableExpression("result", clazz);
				DeclarationExpression declResult = new DeclarationExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
				code.addStatement(new ExpressionStatement(declResult));
				
				//result = new Basic()
				BinaryExpression newResult = new BinaryExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), new ConstructorCallExpression(clazz, ArgumentListExpression.EMPTY_ARGUMENTS));
				code.addStatement(new ExpressionStatement(newResult));
	
				for (FieldNode field : fields) {
					//result.name = getName()
					MethodCallExpression getProperty = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, BaseFields.getGetterName(field), ArgumentListExpression.EMPTY_ARGUMENTS);
					BinaryExpression assignProperty = new BinaryExpression(new PropertyExpression(varResult, field.getName()), Token.newSymbol(Types.ASSIGN, -1, -1), getProperty);
					code.addStatement(new ExpressionStatement(assignProperty));
				}
				
				//return result
				ReturnStatement returnResult = new ReturnStatement(varResult);
				code.addStatement(returnResult);
	
				MethodNode method = new MethodNode("createClone", Opcodes.ACC_PROTECTED, clazz, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
				clazz.addMethod(method);
		}
	}
	
}
