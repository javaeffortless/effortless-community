package org.effortless.gen.fields;

import java.util.ArrayList;

import java.util.List;

import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.effortless.core.Collections;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.objectweb.asm.Opcodes;

public class Restrictions {

	public static final String[] ARRAY_SINGLE_UNIQUE = {"code", "codigo", "cif", "nif", "dni", "passport"};
	public static final Object[][] ARRAY_SINGLE_UNIQUE_DEPENDS = {{"name", new String[] {"surname"}}, {"nombre", new String[] {"apellido"}}};
	
	public static List<FieldNode> listNotNull (ClassNode clazz) {
		List<FieldNode> result = null;
		if (clazz != null) {
			result = new ArrayList<FieldNode>();
			List<FieldNode> fields = clazz.getFields();
			for (FieldNode field : fields) {
				if (isNotNull(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public static List<FieldNode> listUnique (ClassNode clazz) {
		List<FieldNode> result = null;
		if (clazz != null) {
			result = new ArrayList<FieldNode>();
			List<FieldNode> fields = clazz.getFields();
			for (FieldNode field : fields) {
				if (isUnique(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public static List<FieldNode> listNotNullUnique (ClassNode clazz) {
		List<FieldNode> result = null;
		if (clazz != null) {
			List<FieldNode> notNull = listNotNull(clazz);
			List<FieldNode> unique = listUnique(clazz);
			result = new ArrayList<FieldNode>();
			Collections.addAll(result, notNull);
			Collections.addAll(result, unique);
		}
		return result;
	}

	public static Boolean isUnique (ClassNode clazz, FieldNode field) {
		return isSingleUnique(clazz, field);
	}
	
	public static Boolean isSingleUnique (ClassNode clazz, FieldNode field) {
		Boolean result = false;
		if (clazz != null && field != null) {
			String fieldName = field.getName().toLowerCase();
			for (String it : ARRAY_SINGLE_UNIQUE) {
				if (fieldName.contains(it)) {
					result = true;
//					println "UNIQUE " + effortless.MySession.getRootContext()
//					println "$fieldName is unique on class ${clazz.name}"
					break;
				}
			}
			if (!result) {
				for (Object[] it : ARRAY_SINGLE_UNIQUE_DEPENDS) {
					if (fieldName.contains((String)it[0])) {
						result = !containsAnyField(clazz, (String[])it[1]);
						if (result) {
//							println "UNIQUE " + effortless.MySession.getRootContext()
//							println "$fieldName is unique on class ${clazz.name}"
							break;
						}
					}
				}
			}
		}
		return result;
	}
	
	public static Boolean containsAnyField (ClassNode clazz, String[] names) {
		Boolean result = false;
		for (String name : names) { 
			List<FieldNode> fields = clazz.getFields();
			for (FieldNode field : fields) {
				if (field.getName().contains(name)) {
					result = true;
					break;
				}
			}
			if (result) {
				break;
			}
		}
		return result;
	}
	
	public static final String[] ARRAY_NOT_NULL = {"code", "codigo", "nombre", "apellido", "name", "surname", "cif", "nif", "dni", "passport", "pasaporte"};
	
	public static Boolean isNotNull (ClassNode clazz, FieldNode field) {
		Boolean result = false;
		if (clazz != null && field != null) {
			String fieldName = field.getName().toLowerCase();
			for (String it : ARRAY_NOT_NULL) {
				if (fieldName.contains(it)) {
					result = true;
//					println "$fieldName is not null on class ${clazz.name}"
					break;
				}
			}
		}
		return result;
	}

}
