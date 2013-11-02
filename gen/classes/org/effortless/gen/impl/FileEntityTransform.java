package org.effortless.gen.impl;

import org.codehaus.groovy.ast.ClassNode;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.GClass;
import org.effortless.gen.ui.EditorVMTransform;
import org.effortless.gen.ui.FinderVMTransform;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.FileEntity;

public class FileEntityTransform extends HibernateEntityTransform {

	protected void addFields (GClass cg) {
	}
	
	protected void setupEntityParent (GClass cg) {
		ClassNode clazz = cg.getClassNode();
		ClassNode superClass = clazz.getSuperClass();
		if (superClass == null || "java.lang.Object".equals(superClass.getName())) {
			superClass = ClassNodeHelper.toClassNode(FileEntity.class);
			clazz.setSuperClass(superClass);
		}
	}
	
	@Override
	public void process(GClass cg) {
		super.process(cg);
//		new SetupEntityParentClassTransform().process(cg);
//		addAnnotations(clazz);
//		new EntityStaticMethodsClassTransform().process(cg);//addStaticMethods(clazz, sourceUnit);

		new UpdateDbTransform().process(cg);
		
		
		new FinderVMTransform().process(cg);
		new EditorVMTransform().process(cg);
	}
	
}
