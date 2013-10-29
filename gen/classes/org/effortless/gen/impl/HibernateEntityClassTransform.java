package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.gen.classes.ReferenciableTransform;
import org.effortless.gen.classes.SavePropertiesTransform;
import org.effortless.gen.fields.FinalFieldsTransform;
import org.effortless.gen.fields.MappingPropertiesClassTransform;
import org.effortless.gen.fields.PropertiesClassTransform;

public class HibernateEntityClassTransform extends Object implements Transform<GClass> {

	protected void setupEntityParent (GClass clazz) {
		new SetupEntityParentClassTransform().process(clazz);
	}
	
	@Override
	public void process(GClass cg) {
		if (cg != null && cg.checkEntityValid() && !cg.checkEnum()) {
			new AlterActionsClassTransform().process(cg);
//			ecg.alterActions();
//				ActionsTransform.processClass(clazz, sourceUnit);
				
			setupEntityParent(cg);
			new EntityTableClassTransform().process(cg);
		
			PropertiesClassTransform pT = new PropertiesClassTransform();
			MappingPropertiesClassTransform mT = new MappingPropertiesClassTransform();
			List<GField> fields = cg.getFields();
			for (GField field : fields) {
				pT.process(field);
				mT.process(field);
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
