package org.effortless.gen.impl;

import org.codehaus.groovy.ast.ClassNode;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.model.AbstractIdEntity;

public class SetupEntityParentTransform extends Object implements Transform<GClass> {

	public SetupEntityParentTransform () {
		super();
	}
	
	@Override
	public void process(GClass cg) {
		setupParent(cg);
	}
	
	 //extends AbstractIdEntity<AllBasicProperties>		
	protected void setupParent (GClass cg) {
		ClassNode clazz = cg.getClassNode();
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
