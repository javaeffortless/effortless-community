package org.effortless.gen.classes;

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
import org.effortless.gen.ClassGen;
import org.effortless.gen.ClassNodeHelper;
import org.effortless.gen.MethodGen;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.FileFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.model.AbstractEntity;
import org.effortless.model.Referenciable;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements


	protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		if (this.fileProperty != null) {
			this.fileProperty.persist()
		}

		return result;
	}

 * 
 * 
 * @author jesus
 *
 */
public class SavePropertiesTransform {

	
	
	public static void processClass (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
			
			List<FieldNode> fields = FileFields.listFiles(clazz, sourceUnit);
			
			if (fields != null && fields.size() > 0) {
				ClassGen cg = new ClassGen(clazz);

				
				MethodGen mg = null;
				
				//protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
				mg = cg.addMethod("doSavePreviousProperties").setProtected(true).setReturnType(ClassHelper.boolean_TYPE).addParameter(String.class, "properties").addParameter(ClassHelper.boolean_TYPE, "validate").addParameter(ClassHelper.boolean_TYPE, "create");
				mg.declVariable(ClassHelper.boolean_TYPE, "result", mg.cteTrue());//boolean result = true;
				
				for (FieldNode field : fields) {
					MethodGen ifCode = mg.newBlock();
					mg.addIf(mg.notNull(mg.field(field)), ifCode);//if (this.fileProperty != null) {
					ifCode.add(ifCode.call(ifCode.field(field), "persist"));//this.fileProperty.persist()
				}
				mg.addReturn(mg.var("result"));//return result
			}
		}
	}
	
	
	
}
