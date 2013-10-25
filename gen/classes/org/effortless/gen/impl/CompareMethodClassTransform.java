package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.gen.GClass;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GMethod;

public class CompareMethodClassTransform extends Object implements ClassTransform {

	@Override
	public void process(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		List<FieldNode> fields = clazz.getFields();

		GMethod mg = cg.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang3.builder.CompareToBuilder.class, "cpBuilder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doCompare", "cpBuilder", "obj"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("cpBuilder", "append", mg.field(fName), mg.property("obj", fName)));
		}
	}

}
