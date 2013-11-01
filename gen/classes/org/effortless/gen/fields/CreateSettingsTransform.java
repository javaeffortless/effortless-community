package org.effortless.gen.fields;

import java.util.List;

import org.effortless.ann.Module;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.GenContext;
import org.effortless.gen.Transform;
//import org.effortless.gen.impl.HibernateEntityTransform;

public class CreateSettingsTransform extends Object implements Transform<GApplication> {

	@Override
	public void process(GApplication node) {
		if (node != null && node.getSettingsClass() == null) {
			GClass cg = node.newClass("Settings");
			node.setSettingsClass(cg);
			cg.addAnnotation(Module.class, "settings");
			
			cg.addField(Integer.class, "defaultPageSize");
	
			List<Transform> transforms = GenContext.getClassTransforms();		
			for (Transform t : transforms) {
				t.process(cg);
			}
		}
	}

}
