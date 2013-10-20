package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.core.StringUtils;
import org.effortless.gen.ClassGen;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.MethodGen;

public class InitiateMethodClassTransform extends Object implements ClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		ClassGen cg = new ClassGen(clazz, sourceUnit);
		List<FieldNode> fields = clazz.getFields();
		if (fields != null && fields.size() > 0) {
			MethodGen mg = new MethodGen("initiate", cg).setProtected(true);
			
			for (FieldNode field : fields) {
				String methodName = "initiate" + StringUtils.capFirst(field.getName());
				mg.add(mg.call(mg.cteThis(), methodName));
			}
		}
	}

}
