package org.effortless.gen.impl;

import java.util.List;

import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;
import org.effortless.gen.GMethod;

public class InitiateMethodTransform extends Object implements Transform<GClass> {

	public InitiateMethodTransform () {
		super();
	}
	
	@Override
	public void process(GClass clazz) {
		List<GField> fields = clazz.getFields();
		if (fields != null && fields.size() > 0) {
			GMethod mg = clazz.addMethod("initiate").setProtected(true);
			
			mg.add(mg.call(mg.cteSuper(), "initiate"));
			for (GField field : fields) {
				String methodName = field.getInitiateName();
				mg.add(mg.call(mg.cteThis(), methodName));
			}
		}
	}

}
