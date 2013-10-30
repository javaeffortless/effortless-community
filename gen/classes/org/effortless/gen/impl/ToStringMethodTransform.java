package org.effortless.gen.impl;

import java.util.List;

import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.InfoModel;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class ToStringMethodTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass clazz) {
		List<GField> fields = InfoModel.listNotNullUnique(clazz);
		
		GMethod mg = clazz.addMethod("doToString").setProtected(true).addParameter(org.apache.commons.lang3.builder.ToStringBuilder.class, "toStringBuilder");
		mg.add(mg.call(mg.cteSuper(), "doToString", "toStringBuilder"));
		for (GField field : fields) {
			String fName = field.getName();
			mg.add(mg.call("toStringBuilder", "append", mg.cte(fName), mg.field(fName)));
		}
	}

}
