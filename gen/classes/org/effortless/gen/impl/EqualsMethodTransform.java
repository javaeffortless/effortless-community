package org.effortless.gen.impl;

import java.util.List;

import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class EqualsMethodTransform extends Object implements Transform<GClass> {

	public EqualsMethodTransform () {
		super();
	}
	
	@Override
	public void process(GClass clazz) {
		List<GField> fields = clazz.getProperties();
		
		GMethod mg = clazz.addMethod("doEquals").setProtected(true).addParameter(org.apache.commons.lang3.builder.EqualsBuilder.class, "eqBuilder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doEquals", "eqBuilder", "obj"));
		for (GField field : fields) {
			String fName = field.getName();
			mg.add(mg.call("eqBuilder", "append", mg.field(fName), mg.property("obj", fName)));
		}
	}

}
