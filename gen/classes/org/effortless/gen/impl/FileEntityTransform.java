package org.effortless.gen.impl;

import org.effortless.gen.GClass;

public class FileEntityTransform extends HibernateEntityClassTransform {

	@Override
	public void process(GClass cg) {
		new SetupEntityParentClassTransform().process(cg);
//		addAnnotations(clazz);
		new EntityStaticMethodsClassTransform().process(cg);//addStaticMethods(clazz, sourceUnit);

		new UpdateDbClassTransform().process(cg);
	}
	
}
