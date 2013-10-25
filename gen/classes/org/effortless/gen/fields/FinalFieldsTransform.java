package org.effortless.gen.fields;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.core.StringUtils;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GClass;
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

	public void process (GClass clazz) {
		if (clazz != null) {
			
			List<FieldNode> fields = listRefFields(clazz.getClassNode(), clazz.getSourceUnit());
			
			for (FieldNode field : fields) {
				String fieldName = field.getName();
				ClassNode fieldType = field.getType();
				List<FieldNode> finalFields = Restrictions.listNotNullUnique(fieldType);
				for (FieldNode finalField : finalFields) {
					String finalFieldName = finalField.getName();
					String newFdFieldName = "fd" + StringUtils.capFirst(fieldName) + StringUtils.capFirst(finalFieldName);
					ClassNode newFdFieldType = finalField.getType();
					FieldNode newFdField = new FieldNode(newFdFieldName, Opcodes.ACC_PROTECTED, newFdFieldType, clazz.getClassNode(), ConstantExpression.NULL);
					clazz.getClassNode().addField(newFdField);
					
					UserFields.processField(clazz.getClassNode(), newFdField, clazz.getSourceUnit());
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
