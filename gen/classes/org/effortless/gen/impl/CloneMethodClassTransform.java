package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.core.StringUtils;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class CloneMethodClassTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass clazz) {
		List<GField> fields = clazz.getFields();
		
		GMethod mg = clazz.addMethod("createClone").setProtected(true).setReturnType(clazz);
		mg.declVariable(clazz, "result", mg.callConstructor(clazz));
		for (GField field : fields) {
			String fName = field.getName();
			String getter = field.getGetterName();//"get" + StringUtils.capFirst(fName) + "";
			mg.add(mg.assign(mg.property("result", fName), mg.call(getter)));
		}
		mg.addReturn("result");
	}
	
	
}
