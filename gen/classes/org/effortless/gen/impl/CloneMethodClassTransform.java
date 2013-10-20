package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.core.StringUtils;
import org.effortless.gen.ClassGen;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.MethodGen;

public class CloneMethodClassTransform extends Object implements ClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		ClassGen cg = new ClassGen(clazz, sourceUnit);
		
		List<FieldNode> fields = clazz.getFields();
		
		MethodGen mg = cg.addMethod("createClone").setProtected(true).setReturnType(clazz);
		mg.declVariable(clazz, "result", mg.callConstructor(clazz));
		for (FieldNode field : fields) {
			String fName = field.getName();
			String getter = "get" + StringUtils.capFirst(fName) + "";
			mg.add(mg.assign(mg.property("result", fName), mg.call(getter)));
		}
		mg.addReturn("result");
	}
	
	
}
