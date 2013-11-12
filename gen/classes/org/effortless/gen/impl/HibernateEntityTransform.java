package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.gen.GField;
import org.effortless.gen.GMethod;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.gen.classes.ReferenciableTransform;
import org.effortless.gen.classes.SavePropertiesTransform;
import org.effortless.gen.fields.FinalFieldsTransform;
import org.effortless.gen.fields.MappingPropertiesTransform;
import org.effortless.gen.fields.PropertiesTransform;
import org.effortless.gen.methods.InstanceMethodTransform;

public class HibernateEntityTransform extends Object implements Transform<GClass> {

	public HibernateEntityTransform () {
		super();
	}
	
	protected void setupEntityParent (GClass clazz) {
		new SetupEntityParentTransform().process(clazz);
	}

	protected void addFields (GClass cg) {
		new AddFieldsTransform().process(cg);
	}
	
	protected void setupStaticClass (GClass clazz) {
		if (clazz != null && clazz.isInner()) {
			clazz.setStatic(true);
		}
	}
	
	@Override
	public void process(GClass cg) {
		if (cg != null && cg.checkEntityValid() && !cg.checkEnum()) {
			GMethod mg = null;
			mg = cg.addConstructor().setPublic(true);
			mg.add(mg.cteSuper());
//			mg.add(mg.debug("HOLA CREANDO"));
			
			setupStaticClass(cg);
			
			addFields(cg);
			new AlterActionsTransform().process(cg);
//			ecg.alterActions();
//				ActionsTransform.processClass(clazz, sourceUnit);
				
			setupEntityParent(cg);
			new EntityTableTransform().process(cg);
		
			PropertiesTransform pT = new PropertiesTransform();
			MappingPropertiesTransform mT = new MappingPropertiesTransform();
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
			
			new EntityStaticMethodsTransform().process(cg);
				
			new InitiateMethodTransform().process(cg);//ecg.addInitiate();
			new HashCodeMethodTransform().process(cg);//ecg.addDoHashCode();//HashCodeTransform.processClass(clazz, sourceUnit);
			new EqualsMethodTransform().process(cg);//ecg.addDoEquals();//EqualsTransform.processClass(clazz, sourceUnit);
			new CompareMethodTransform().process(cg);//ecg.addDoCompare();//CompareToTransform.processClass(clazz, sourceUnit);
			new ToStringMethodTransform().process(cg);//ecg.addDoToString();//ToStringTransform.processClass(clazz, sourceUnit);

			new KryoTransform().process(cg);
			new CloneMethodTransform().process(cg);
//			ecg.addCreateClone();//CloneTransform.processClass(clazz, sourceUnit);
				
	//			addPrimitiveFields(clazz);
	//			AutoSession.generate(clazz);
	//			FieldChangeSupportGenerator.generate(clazz);
	//					println ">>>>>>>>>>>>>> PERIICOOOOOOOOOOOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
			//ñ
			applyEntityLog(cg);
			
			InstanceMethodTransform mt = new InstanceMethodTransform();
			List<GMethod> methods = cg.getPublicMethods();
			for (GMethod method : methods) {
				mt.process(method);
			}
//				tryNeedsFileEntity(clazz, sourceUnit);
		}
	}

	protected void applyEntityLog(GClass clazz) {
		new EntityLogTransform().process(clazz);
	}

}
