package org.effortless.gen.impl;

import org.effortless.ann.Module;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.model.FileEntity;
import org.effortless.model.FileEntityTuplizer;

public class CreateFileEntityTransform extends AbstractCreateClassTransform<GField>  {

	public CreateFileEntityTransform () {
		super();
	}
	
	public void process (GField field) {
		GClass result = null;
		
		result = tryNeedsNewExternalEntity(field.getClazz(), ClassNodeHelper.toClassNode(FileEntity.class), FileEntityTuplizer.class);
		result.addAnnotation(Module.class, "others");

		FileEntityTransform transform = new FileEntityTransform();
		transform.process(result);

		
		setResult(result);
	}

}
