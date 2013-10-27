package org.effortless.gen;

import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.ann.NoTransform;
import org.effortless.core.ClassNodeHelper;

public class InfoClassNode {

	public static boolean checkEntityValid (ClassNode clazz, SourceUnit sourceUnit) {
		boolean result = false;
		if (clazz != null) {
			ClassNode superClass = clazz.getSuperClass();
			String superClassName = (superClass != null ? superClass.getName() : null);
			result = !"groovy.lang.Script".equals(superClassName);
			if (result) {
				List<AnnotationNode> annotations = clazz.getAnnotations(NO_ENTITY_CLAZZ);
				result = (!(annotations != null && annotations.size() > 0));
			}
//			result = result && !"java.lang.Enum".equals(superClassName);
		}
		return result;
	}

	public static boolean checkEnum (ClassNode clazz, SourceUnit sourceUnit) {
		boolean result = false;
		if (clazz != null) {
			ClassNode superClass = clazz.getSuperClass();
			String superClassName = (superClass != null ? superClass.getName() : null);
			result = "java.lang.Enum".equals(superClassName);
		}
		return result;
	}

	public static final ClassNode NO_ENTITY_CLAZZ = ClassNodeHelper.toClassNode(NoTransform.class);

}
