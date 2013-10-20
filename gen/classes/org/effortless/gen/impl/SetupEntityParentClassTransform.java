package org.effortless.gen.impl;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.ClassTransform;
import org.effortless.model.AbstractIdEntity;

public class SetupEntityParentClassTransform extends Object implements ClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		setupParent(clazz);
	}
	
	 //extends AbstractIdEntity<AllBasicProperties>		
	protected void setupParent (ClassNode clazz) {
		ClassNode superClass = clazz.getSuperClass();
		if (superClass == null || "java.lang.Object".equals(superClass.getName())) {
		superClass = new ClassNode(AbstractIdEntity.class);
//		ClassNode thisClass = clazz.getDeclaringClass();//new ClassNode(clazz.getTypeClass());
//		ClassNode thisClass = new ClassNode(clazz.getName(), clazz.getModifiers(), clazz.getSuperClass());
//		thisClass.setRedirect(clazz);
//		ClassNode thisClass = clazz;
//		ClassNode thisClass = ClassHelper.getWrapper(clazz);
//		GenericsType gt = new GenericsType(superClass);
//		gt.setResolved(false);
//		gt.setType(thisClass);
//		gt.setWildcard(false);
//		superClass.setGenericsTypes(new GenericsType[] {gt});
		clazz.setSuperClass(superClass);
		}
	}
	
}
