package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.core.StringUtils;
import org.effortless.gen.GClass;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class InitiateMethodClassTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		List<FieldNode> fields = clazz.getFields();
		if (fields != null && fields.size() > 0) {
			GMethod mg = new GMethod("initiate", cg).setProtected(true);
			
			for (FieldNode field : fields) {
				String methodName = "initiate" + StringUtils.capFirst(field.getName());
				mg.add(mg.call(mg.cteThis(), methodName));
			}
		}
	}

}
