package org.effortless.gen.impl;

import java.io.File;
import java.util.Date;

import org.effortless.ann.NotNull;
import org.effortless.ann.Person;
import org.effortless.gen.GAnnotation;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.Transform;

public class AddFieldsTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass node) {
		if (node != null) {
			addComentarioField(node);
			addFechaAltaField(node);
			addBaseFields(node);
		}
	}
	
	protected void addComentarioField (GClass node) {
		doAddField(node, String.class, "comentario");
	}

	protected void addFechaAltaField (GClass node) {
		doAddField(node, Date.class, "fechaAlta");
	}

	protected void addBaseFields (GClass node) {
		GAnnotation ann = node.getAnnotation(Person.class);
		if (ann != null) {
			addPersonFields(node);
		}
		else {
			addNonPersonFields(node);
		}
	}

	private void addPersonFields(GClass node) {
		doAddField(node, String.class, "alias");
		doAddField(node, String.class, "nombre");
		doAddField(node, String.class, "apellidos");
		doAddField(node, String.class, "correo");
		doAddField(node, File.class, "foto");
	}

	private void addNonPersonFields(GClass node) {
		GField fNombre = doAddField(node, String.class, "nombre");
		if (fNombre != null) {
			fNombre.createAnnotation(NotNull.class, "enabled", node.cteFalse());
		}
		doAddField(node, String.class, "descripcion");
	}
	
	protected GField doAddField (GClass node, Class<?> type, String fieldName) {
		GField result = null;
		GField field = node.getField(fieldName);
		result = (field == null ? node.addField(type, fieldName) : null);
		return result;
	}

}
