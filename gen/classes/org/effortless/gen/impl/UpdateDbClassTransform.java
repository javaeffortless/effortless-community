package org.effortless.gen.impl;

import java.io.IOException;

import nu.xom.ParsingException;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.core.ModelException;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.InfoClassNode;
import org.effortless.model.StartupDb;

public class UpdateDbClassTransform extends Object implements ClassTransform {

	@Override
	public void process(ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null && InfoClassNode.checkEntityValid(clazz, sourceUnit) && !InfoClassNode.checkEnum(clazz, sourceUnit)) {
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
