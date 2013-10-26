package org.effortless.gen.impl;

import org.effortless.gen.ClassTransform;
import org.effortless.gen.GClass;
import org.effortless.gen.classes.ReferenciableTransform;
import org.effortless.gen.classes.SavePropertiesTransform;
import org.effortless.gen.fields.FinalFieldsTransform;
import org.effortless.gen.fields.MappingPropertiesClassTransform;
import org.effortless.gen.fields.PropertiesClassTransform;

public class HibernateEntityClassTransform extends Object implements ClassTransform {

	@Override
	public void process(GClass cg) {
		if (cg != null && cg.checkEntityValid() && !cg.checkEnum()) {
			new AlterActionsClassTransform().process(cg);
//			ecg.alterActions();
//				ActionsTransform.processClass(clazz, sourceUnit);
				
			new SetupEntityParentClassTransform().process(cg);
			new EntityTableClassTransform().process(cg);
		
			new PropertiesClassTransform().process(cg);
			new MappingPropertiesClassTransform().process(cg);
				
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
