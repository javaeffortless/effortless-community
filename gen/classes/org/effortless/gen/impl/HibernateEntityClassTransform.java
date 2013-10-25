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
import org.effortless.gen.GClass;
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
	public void process(GClass cg) {
		if (cg != null && cg.checkEntityValid() && !cg.checkEnum()) {
			new AlterActionsClassTransform().process(cg);
//			ecg.alterActions();
//				ActionsTransform.processClass(clazz, sourceUnit);
				
			new SetupEntityParentClassTransform().process(cg);
			new EntityTableClassTransform().process(cg);
		
			EntityClassGen ecg = new EntityClassGen(clazz, sourceUnit);
			List<FieldNode> fields = clazz.getFields();
			for (FieldNode field : fields) {
				ecg.processField(field);
			}
				
			if (false) {
				new FinalFieldsTransform().process(cg);
			}
				
			new ReferenciableTransform().process(cg);
			new SavePropertiesTransform().process(cg);
			
			new EntityStaticMethodsClassTransform().process(cg);
				
			new InitiateMethodClassTransform().process(cg);//ecg.addInitiate();
			new HashCodeMethodClassTransform().process(cg);//ecg.addDoHashCode();//HashCodeTransform.processClass(clazz, sourceUnit);
			new EqualsMethodClassTransform().process(cg);//ecg.addDoEquals();//EqualsTransform.processClass(clazz, sourceUnit);
			new CompareMethodClassTransform().process(cg);//ecg.addDoCompare();//CompareToTransform.processClass(clazz, sourceUnit);
			new ToStringMethodClassTransform().process(cg);//ecg.addDoToString();//ToStringTransform.processClass(clazz, sourceUnit);

			new KryoTransform().process(cg);
			new CloneMethodClassTransform().process(cg);
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
