package org.effortless.gen.impl;

import java.io.IOException;

import nu.xom.ParsingException;

import org.effortless.core.ModelException;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.model.StartupDb;

public class UpdateDbTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass clazz) {
		if (clazz != null && clazz.checkEntityValid() && !clazz.checkEnum()) {
			try {
				String packageName = clazz.getPackageName();
				String fullClassName = packageName + "." + clazz.getNameWithoutPackage();
	//			StartupDb.addAppEntity(fullClassName);
				StartupDb.updateDb(fullClassName);
			} catch (ParsingException e) {
				throw new ModelException(e);
			} catch (IOException e) {
				throw new ModelException(e);
			}
		}
	}

}
