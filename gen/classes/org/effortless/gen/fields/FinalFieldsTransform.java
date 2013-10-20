package org.effortless.gen.fields;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.effortless.core.StringUtils;
import org.effortless.gen.ClassTransform;
import org.effortless.model.AbstractEntity;
import org.effortless.model.Entity;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements

	
 * 
 * 
 * @author jesus
 *
 */
public class FinalFieldsTransform extends Object implements ClassTransform {

	public void process (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
			
			List<FieldNode> fields = listRefFields(clazz, sourceUnit);
			
			for (FieldNode field : fields) {
				String fieldName = field.getName();
				ClassNode fieldType = field.getType();
				List<FieldNode> finalFields = Restrictions.listNotNullUnique(fieldType);
				for (FieldNode finalField : finalFields) {
					String finalFieldName = finalField.getName();
					String newFdFieldName = "fd" + StringUtils.capFirst(fieldName) + StringUtils.capFirst(finalFieldName);
					ClassNode newFdFieldType = finalField.getType();
					FieldNode newFdField = new FieldNode(newFdFieldName, Opcodes.ACC_PROTECTED, newFdFieldType, clazz, ConstantExpression.NULL);
					clazz.addField(newFdField);
					
					UserFields.processField(clazz, newFdField, sourceUnit);
				}
			}
			
		}
	}

	protected static List<FieldNode> listRefFields(ClassNode clazz, SourceUnit sourceUnit) {
		List<FieldNode> result = null;
		result = new ArrayList<FieldNode>();
		
		List<FieldNode> fields = clazz.getFields();
		for (FieldNode field : fields) {
			ClassNode fieldClass = field.getType();
			if (fieldClass.implementsInterface(new ClassNode(Entity.class))) {
				result.add(field);
			}
		}
		
		return result;
	}
	
	
	
}
