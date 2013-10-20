package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.EntityClassGen;
import org.effortless.gen.GenContext;
import org.effortless.gen.InfoClassNode;
import org.effortless.gen.classes.ReferenciableTransform;
import org.effortless.gen.classes.SavePropertiesTransform;
import org.effortless.gen.fields.FinalFieldsTransform;
import org.effortless.gen.methods.KryoTransform;
import org.effortless.gen.ui.EditorVMTransform;
import org.effortless.gen.ui.FinderVMTransform;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.Filter;
import org.effortless.model.LogData;
import org.objectweb.asm.Opcodes;

public class HibernateEntityClassTransform extends Object implements ClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		if (InfoClassNode.checkEntityValid(clazz, sourceUnit) && !InfoClassNode.checkEnum(clazz, sourceUnit)) {
			EntityClassGen ecg = new EntityClassGen(clazz, sourceUnit);
			new AlterActionsClassTransform().process(clazz, sourceUnit);
//			ecg.alterActions();
//				ActionsTransform.processClass(clazz, sourceUnit);
				
			new SetupEntityParentClassTransform().process(clazz, sourceUnit);
			new EntityTableClassTransform().process(clazz, sourceUnit);
		
			List<FieldNode> fields = clazz.getFields();
			for (FieldNode field : fields) {
				ecg.processField(field);
			}
				
			if (false) {
				new FinalFieldsTransform().process(clazz, sourceUnit);
			}
				
			new ReferenciableTransform().process(clazz, sourceUnit);
			new SavePropertiesTransform().process(clazz, sourceUnit);
			
			new EntityStaticMethodsClassTransform().process(clazz, sourceUnit);
				
			new InitiateMethodClassTransform().process(clazz, sourceUnit);//ecg.addInitiate();
			new HashCodeMethodClassTransform().process(clazz, sourceUnit);//ecg.addDoHashCode();//HashCodeTransform.processClass(clazz, sourceUnit);
			new EqualsMethodClassTransform().process(clazz, sourceUnit);//ecg.addDoEquals();//EqualsTransform.processClass(clazz, sourceUnit);
			new CompareMethodClassTransform().process(clazz, sourceUnit);//ecg.addDoCompare();//CompareToTransform.processClass(clazz, sourceUnit);
			new ToStringMethodClassTransform().process(clazz, sourceUnit);//ecg.addDoToString();//ToStringTransform.processClass(clazz, sourceUnit);

			KryoTransform.processClass(clazz, sourceUnit);
			new CloneMethodClassTransform().process(clazz, sourceUnit);
//			ecg.addCreateClone();//CloneTransform.processClass(clazz, sourceUnit);
				
	//			addPrimitiveFields(clazz);
	//			AutoSession.generate(clazz);
	//			FieldChangeSupportGenerator.generate(clazz);
	//					println ">>>>>>>>>>>>>> PERIICOOOOOOOOOOOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
			//ñ
				
				
				
//				tryNeedsFileEntity(clazz, sourceUnit);
		}
	}

}
