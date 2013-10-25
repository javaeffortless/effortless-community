package org.effortless.gen.impl;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.GClass;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GMethod;
import org.effortless.gen.fields.Restrictions;

public class ToStringMethodClassTransform extends Object implements ClassTransform {

	@Override
	public void process(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		List<FieldNode> fields = Restrictions.listNotNullUnique(clazz);
		
		GMethod mg = cg.addMethod("doToString").setProtected(true).addParameter(org.apache.commons.lang3.builder.ToStringBuilder.class, "toStringBuilder");
		mg.add(mg.call(mg.cteSuper(), "doToString", "toStringBuilder"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("toStringBuilder", "append", mg.cte(fName), mg.field(fName)));
		}
	}

}
