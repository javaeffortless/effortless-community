package org.effortless.gen.impl;

import org.codehaus.groovy.ast.ClassNode;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.model.FileEntity;
import org.effortless.model.FileEntityTuplizer;

public class CreateFileEntityTransform extends Object  {

	public void process (GField field) {
		
		
		ClassNode fileClazz = GClass.tryNeedsNewExternalEntity(field.getClazz(), field.getClazz().getSourceUnit(), FileEntity.class, FileEntity.KEY_CLASS_NEEDS, FileEntity.KEY_APP_NEEDS, FileEntityTuplizer.class);
		
		
	}

	public GClass getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
