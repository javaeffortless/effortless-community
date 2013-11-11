package org.effortless.gen.classes;

import java.util.List;

import org.effortless.ann.Module;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.GenContext;
import org.effortless.gen.Transform;

public class CreateUserProfileTransform extends Object implements Transform<GApplication> {

	public CreateUserProfileTransform () {
		super();
	}
	
	@Override
	public void process(GApplication node) {
		if (node != null && node.getUserProfileClass() == null) {
			GClass cg = node.newClass("Perfil");
			node.setUserProfileClass(cg);
			cg.addAnnotation(Module.class, "usuarios");
			
			cg.addField(String.class, "nombre");
			cg.addField(String.class, "descripcion");
			cg.addField(String.class, "comentario");
			
			List<Transform> transforms = GenContext.getClassTransforms();		
			for (Transform t : transforms) {
				t.process(cg);
			}
		}
	}
	
}
