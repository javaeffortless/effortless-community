package org.effortless.gen.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

	protected void doHashCode(org.apache.commons.lang3.builder.HashCodeBuilder hcBuilder) {
		// TODO Auto-generated method stub
		super.doHashCode(hcBuilder);
		hcBuilder.append("name");
		hcBuilder.append(this.name);
	}
	
	
 * 
 * 
 * @author jesus
 *
 */
public class HashCodeTransform {

	public static final ClassNode HASH_CODE_BUILDER = new ClassNode(org.apache.commons.lang3.builder.HashCodeBuilder.class);
	
	public static void processClass (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
			List<FieldNode> fields = Restrictions.listNotNullUnique(clazz);
			
				BlockStatement code = new BlockStatement();

				VariableExpression varHcBuilder = new VariableExpression("hcBuilder", HASH_CODE_BUILDER);

				//super.doHashCode(hcBuilder);
				MethodCallExpression addSuper = new MethodCallExpression(VariableExpression.SUPER_EXPRESSION, "doHashCode", new ArgumentListExpression(varHcBuilder));
				code.addStatement(new ExpressionStatement(addSuper));
				
				for (FieldNode field : fields) {
					//hcBuilder.append("name");
					MethodCallExpression addLabel = new MethodCallExpression(varHcBuilder, "append", new ConstantExpression(field.getName()));
					code.addStatement(new ExpressionStatement(addLabel));
					
					//hcBuilder.append(this.name);
					MethodCallExpression addField = new MethodCallExpression(varHcBuilder, "append", new FieldExpression(field));
					code.addStatement(new ExpressionStatement(addField));
				}

				Parameter[] parameterTypes = new Parameter [] {new Parameter(HASH_CODE_BUILDER, "hcBuilder")};
				MethodNode method = new MethodNode("doHashCode", Opcodes.ACC_PROTECTED, ClassHelper.VOID_TYPE, parameterTypes, ClassNode.EMPTY_ARRAY, code);
				clazz.addMethod(method);
		}
	}
	
}
