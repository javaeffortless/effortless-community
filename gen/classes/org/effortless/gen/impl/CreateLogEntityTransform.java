package org.effortless.gen.impl;

import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.GClass;
import org.effortless.model.LogData;

public class CreateLogEntityTransform extends AbstractCreateClassTransform<GClass> {

	public CreateLogEntityTransform () {
		super();
	}
	
	public void process (GClass clazz) {
		GClass result = null;
		
		result = tryNeedsNewExternalEntity(clazz, ClassNodeHelper.toClassNode(LogData.class), null);

		AdaptLogEntityTransform transform = new AdaptLogEntityTransform();
		transform.process(result);
		
		setResult(result);
	}
	
}
