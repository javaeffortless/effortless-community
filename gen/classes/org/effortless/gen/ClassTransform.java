package org.effortless.gen;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;

public interface ClassTransform {

	public void process (ClassNode clazz, SourceUnit sourceUnit);
	
}
