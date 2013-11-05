package org.effortless.gen.impl;

import org.effortless.ann.Module;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.model.LogData;

public class CreateLogEntityTransform extends AbstractCreateClassTransform<GClass> {

	public CreateLogEntityTransform () {
		super();
	}
	
	public void process (GClass clazz) {
		GClass result = null;
		
		GApplication app = clazz.getApplication();
		result = app.getLogClass();
		if (result == null) {
			result = tryNeedsNewExternalEntity(clazz, ClassNodeHelper.toClassNode(LogData.class), null);
			result.addAnnotation(Module.class, "others");

			AdaptLogEntityTransform transform = new AdaptLogEntityTransform();
			transform.process(result);
			
			app.setLogClass(result);
		}
		setResult(result);
	}
	
}
