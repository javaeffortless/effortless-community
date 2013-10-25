package org.effortless.gen.fields;

import java.util.ArrayList;

import java.util.List;

import org.effortless.core.Collections;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;

public class Restrictions {

	public static final String[] ARRAY_SINGLE_UNIQUE = {"code", "codigo", "cif", "nif", "dni", "passport"};
	public static final Object[][] ARRAY_SINGLE_UNIQUE_DEPENDS = {{"name", new String[] {"surname"}}, {"nombre", new String[] {"apellido"}}};
	
	public static List<GField> listNotNull (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			result = new ArrayList<GField>();
			List<GField> fields = clazz.getFields();
			for (GField field : fields) {
				if (isNotNull(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public static List<GField> listUnique (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			result = new ArrayList<GField>();
			List<GField> fields = clazz.getFields();
			for (GField field : fields) {
				if (isUnique(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public static List<GField> listNotNullUnique (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			List<GField> notNull = listNotNull(clazz);
			List<GField> unique = listUnique(clazz);
			result = new ArrayList<GField>();
			Collections.addAll(result, notNull);
			Collections.addAll(result, unique);
		}
		return result;
	}

	public static Boolean isUnique (GClass clazz, GField field) {
		return isSingleUnique(clazz, field);
	}
	
	public static Boolean isSingleUnique (GClass clazz, GField field) {
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
	
	public static Boolean containsAnyField (GClass clazz, String[] names) {
		Boolean result = false;
		for (String name : names) { 
			List<GField> fields = clazz.getFields();
			for (GField field : fields) {
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
	
	public static Boolean isNotNull (GClass clazz, GField field) {
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
