package org.effortless.ann;

import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.StringUtils;

public class InfoFacade {

	public static boolean isSingleton (Class<?> clazz) {
		boolean result = false;
		if (clazz != null) {
			String name = StringUtils.forceNotNull(clazz.getSimpleName());
			result = false;
			result = result || "settings".equals(name.toLowerCase()); 
			result = result || "configuration".equals(name.toLowerCase()); 
		}
		return result;
	}
	
	public static Boolean checkNotNull (FieldNode field) {
		Boolean result = null;
		AnnotationNode annotation = getFirstAnnotation(field, NotNull.class);
		if (annotation != null) {
			Expression expr = (annotation.getMember("enabled"));
			result = Boolean.valueOf(expr == null || "false".equals(expr.getText()));
		}
		return result;
	}
	
	public static AnnotationNode getFirstAnnotation (FieldNode field, Class clazz) {
		AnnotationNode result = null;
		List<AnnotationNode> annotations = field.getAnnotations(ClassNodeHelper.toClassNode(NotNull.class));
		result = (annotations != null && annotations.size() > 0 ? annotations.get(0) : null);
		return result;
	}
	
}
