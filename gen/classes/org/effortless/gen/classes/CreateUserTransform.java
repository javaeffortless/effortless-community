package org.effortless.gen.classes;

import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.effortless.ann.Finder;
import org.effortless.ann.HashPassword;
import org.effortless.ann.Module;
import org.effortless.ann.Person;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.GenContext;
import org.effortless.gen.Transform;

public class CreateUserTransform extends Object implements Transform<GApplication> {

	@Override
	public void process(GApplication node) {
		if (node != null && node.getUserClass() == null) {
			GClass cg = node.newClass("Usuario");
			node.setUserClass(cg);
			
			AnnotationNode ann = cg.createAnnotation(Finder.class);
			ann.addMember("properties", cg.cte("activo,login,nombre,apellidos"));
			ann.addMember("info", cg.cte("foto"));
			cg.addAnnotation(ann);
			
			cg.addAnnotation(Person.class);
			cg.addAnnotation(Module.class, "usuarios");

			cg.addField(String.class, "login");
			
			cg.addField(String.class, "password").addAnnotation(HashPassword.class, "MD5");
			cg.addField(Boolean.class, "activo");

			if (node.getUserProfileClass() != null) {
				cg.addField(node.getUserProfileClass().getClassNode(), "perfil");
			}

			List<Transform> transforms = GenContext.getClassTransforms();		
			for (Transform t : transforms) {
				t.process(cg);
			}
		}
	}

}
