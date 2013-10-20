package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.ClassGen;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.MethodGen;

public class CompareMethodClassTransform extends Object implements ClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		ClassGen cg = new ClassGen(clazz, sourceUnit);
		List<FieldNode> fields = clazz.getFields();

		MethodGen mg = cg.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang3.builder.CompareToBuilder.class, "cpBuilder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doCompare", "cpBuilder", "obj"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("cpBuilder", "append", mg.field(fName), mg.property("obj", fName)));
		}
	}

}
