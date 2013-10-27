package org.effortless.gen.impl;

import java.util.List;

import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.InfoModel;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class HashCodeMethodClassTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass clazz) {
		List<GField> fields = InfoModel.listNotNullUnique(clazz);
			
		GMethod mg = clazz.addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang3.builder.HashCodeBuilder.class, "hcBuilder");
		mg.add(mg.call(mg.cteSuper(), "doHashCode", "hcBuilder"));
		for (GField field : fields) {
			String fName = field.getName();
			mg.add(mg.call("hcBuilder", "append", mg.cte(fName)));
			mg.add(mg.call("hcBuilder", "append", mg.field(fName)));
		}
	}

}
