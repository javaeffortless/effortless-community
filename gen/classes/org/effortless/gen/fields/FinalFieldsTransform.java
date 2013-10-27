package org.effortless.gen.fields;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.core.StringUtils;
import org.effortless.gen.GField;
import org.effortless.gen.InfoModel;
import org.effortless.gen.Transform;
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
public class FinalFieldsTransform extends Object implements Transform<GClass> {

	public void process (GClass clazz) {
		if (clazz != null) {
			
			List<FieldNode> fields = listRefFields(clazz.getClassNode(), clazz.getSourceUnit());
			
			for (FieldNode field : fields) {
				String fieldName = field.getName();
				ClassNode fieldType = field.getType();
				GClass targetClazz = clazz.getApplication().loadClass(fieldType);
				List<GField> finalFields = InfoModel.listNotNullUnique(targetClazz);
				
				PropertiesClassTransform pT = new PropertiesClassTransform();
				MappingPropertiesClassTransform mT = new MappingPropertiesClassTransform();
				
				for (GField finalField : finalFields) {
					String finalFieldName = finalField.getName();
					String newFdFieldName = "fd" + StringUtils.capFirst(fieldName) + StringUtils.capFirst(finalFieldName);
					ClassNode newFdFieldType = finalField.getType();
					FieldNode newFdField = new FieldNode(newFdFieldName, Opcodes.ACC_PROTECTED, newFdFieldType, clazz.getClassNode(), ConstantExpression.NULL);
					clazz.getClassNode().addField(newFdField);
					GField newGField = new GField(clazz, newFdField);
					
					pT.process(newGField);
					mT.process(newGField);
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
