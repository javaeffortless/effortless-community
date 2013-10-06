package org.effortless.model;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

public class FileEntityTuplizer extends PojoEntityTuplizer {

	public FileEntityTuplizer(EntityMetamodel model, EntityBinding binding) {
		super(model, binding);
	}

	public FileEntityTuplizer(EntityMetamodel model, PersistentClass clazz) {
		super(model, clazz);
	}

}
