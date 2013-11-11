package org.effortless.gen.impl;

import java.util.List;

import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class CompareMethodTransform extends Object implements Transform<GClass> {

	public CompareMethodTransform () {
		super();
	}
	
	@Override
	public void process(GClass clazz) {
		List<GField> fields = clazz.getProperties();

		GMethod mg = clazz.addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang3.builder.CompareToBuilder.class, "cpBuilder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doCompare", "cpBuilder", "obj"));
		for (GField field : fields) {
			String fName = field.getName();
			mg.add(mg.call("cpBuilder", "append", mg.field(fName), mg.property("obj", fName)));
		}
	}

}
