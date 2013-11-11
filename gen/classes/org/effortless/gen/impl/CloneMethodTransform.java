package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.core.StringUtils;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class CloneMethodTransform extends Object implements Transform<GClass> {

	public CloneMethodTransform () {
		super();
	}
	
	@Override
	public void process(GClass clazz) {
		List<GField> fields = clazz.getProperties();
		GMethod mg = null;
		
//		protected Type createClone () {
//			return null;
//		}

//		ClassNode c3 = ClassHelper.make(clazz.getClassNode().getName());
		
//		ClassNode c3 = clazz.getClassNode().getPlainNodeReference();
//		ClassNode c3 = clazz.getClassNode();
		ClassNode gType = clazz.getPlainClassForGenerics();
//		ClassNode c3 = new ClassNode(clazz.getClassNode().getPlainNodeReference().getTypeClass());
//		ClassNode c3 = clazz.getClassNode().redirect();
//		ClassNode c3 = new ClassNode(clazz.getClassNode().getTypeClass());
		
		mg = clazz.addMethod("createClone").setProtected(true).setReturnType(gType);
		mg.declVariable(clazz, "result", mg.callConstructor(gType));
if (false) {
		for (GField field : fields) {
			String fName = field.getName();
			String getter = field.getGetterName();//"get" + StringUtils.capFirst(fName) + "";
			mg.add(mg.assign(mg.property("result", fName), mg.call(getter)));
		}
}
		mg.addReturn("result");
		
		
//		public void copyTo (String properties, Type target) {
//		}
		mg = clazz.addMethod("copyTo").setProtected(true).setReturnType(ClassHelper.VOID_TYPE).addParameter(String.class, "properties").addParameter(gType, "target");
		for (GField field : fields) {
			String fName = field.getName();
			String getter = field.getGetterName();//"get" + StringUtils.capFirst(fName) + "";
			mg.add(mg.assign(mg.property("target", fName), mg.call(getter)));
		}
	}
	
	
}
