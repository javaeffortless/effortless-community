package org.effortless.gen;

import java.util.ArrayList;
import java.util.List;

import org.effortless.ann.Finder;
import org.effortless.ann.NotNull;
import org.effortless.core.Collections;
import org.effortless.core.StringUtils;
import org.effortless.model.FileEntity;

public class InfoModel extends Object {

	
	public static final String[] ARRAY_SINGLE_UNIQUE = {"code", "codigo", "cif", "nif", "dni", "passport"};
	public static final Object[][] ARRAY_SINGLE_UNIQUE_DEPENDS = {{"name", new String[] {"surname"}}, {"nombre", new String[] {"apellido"}}};
	
	public static List<GField> listNotNull (GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			result = new ArrayList<GField>();
			List<GField> fields = clazz.getProperties();
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
			List<GField> fields = clazz.getProperties();
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
		List<GField> fields = clazz.getProperties();
		for (String name : names) { 
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
		result = (result && isNull(field) ? false : result);
		return result;
	}
	
	public static boolean isNull (GField field) {
		boolean result = false;
		GAnnotation ann = field.getAnnotation(NotNull.class);
		String value = StringUtils.forceNotNull((ann != null ? ann.getMemberString("enabled") : null));
		result = (ann != null && (value.length() > 0 && "false".equals(value)));
		return result;
	}
	
	

	public static boolean isSingleUnique(GField field) {
        boolean result = false;
        if (field != null) {
        	String fieldName = field.getName().toLowerCase();
                for (String it : ARRAY_SINGLE_UNIQUE) {
                        if (fieldName.contains(it)) {
                                result = true;
//                                println "UNIQUE " + effortless.MySession.getRootContext()
//                                println "$fieldName is unique on class ${clazz.name}"
                                break;
                        }
                }
                if (!result) {
                        for (Object[] it : ARRAY_SINGLE_UNIQUE_DEPENDS) {
                                if (fieldName.contains((String)it[0])) {
                                        result = !containsAnyField(field.getClazz(), (String[])it[1]);
                                        if (result) {
//                                                println "UNIQUE " + effortless.MySession.getRootContext()
//                                                println "$fieldName is unique on class ${clazz.name}"
                                                break;
                                        }
                                }
                        }
                }
        }
        return result;		
	}

	public static boolean isNotNull(GField field) {
        boolean result = false;
        if (field != null) {
                String fieldName = field.getName().toLowerCase();
                for (String it : ARRAY_NOT_NULL) {
                        if (fieldName.contains(it)) {
                                result = true;
//                                println "$fieldName is not null on class ${clazz.name}"
                                break;
                        }
                }
        }
        return result;
	}

    protected static final String[] COMMENT_NAMES = {"comment", "comentario", "remark", "observacion", "annotation", "anotacion"};
    	
	public static boolean checkCommentField(String fieldName) {
        boolean result = false;
        if (fieldName != null) {
                fieldName = fieldName.trim().toLowerCase();
                for (String name : COMMENT_NAMES) {
                        if (name != null && name.trim().toLowerCase().contains(fieldName)) {
                                result = true;
                                break;
                        }
                }
        }
        return result;
	}
	
	public static boolean checkAnyValidCustomAction(GClass clazz) {
		boolean result = false;
		String[] names = getActionCustomNames(clazz);
		result = (names != null && names.length > 0);
		return result;
	}
	
	public static String[] getActionCustomNames(GClass clazz) {
		String[] result = null;
		if (clazz != null) {
			List<String> list = new ArrayList<String>();
//			List<MethodNode> methods = clazz.getAllDeclaredMethods();
			List<GMethod> methods = clazz.getMethods();
			for (GMethod method : methods) {
				if (method.checkSingleAction()) {
					list.add(method.getName());
				}
			}
			result = list.toArray(new String[0]);
		}
		return result;
	}

	public static List<GField> getFinderProperties(GClass clazz) {
        List<GField> result = null;
        result = listNotNullUnique(clazz);
        result = (result != null ? result : new ArrayList<GField>());
        int length = (result != null ? result.size() : 0);
        if (length < 5) {
                List<GField> fields = clazz.getProperties();
                for (GField field : fields) {
                        if (!result.contains(field)) {
                                result.add(field);
                                if (result.size() >= 5) {
                                        break;
                                }
                        }
                }
        }
        return result;		
	}

	public static List<GField> listFileFields(GClass clazz) {
		List<GField> result = null;
		List<GField> fields = clazz.getProperties();
		result = new ArrayList<GField>();
		for (GField field : fields) {
			if (field.isType(FileEntity.class)) {
				result.add(field);
			}
		}
		return result;
	}

//si return type => se considera de consulta => NOLOG
//si NO return type => se considera de modificacion => LOG

	public static List<GField> getEditorProperties (GClass clazz) {
		List<GField> result = null;
		result = new ArrayList<GField>();
		List<GField> fields = clazz.getProperties();
		for (GField field : fields) {
			if (!result.contains(field)) {
				result.add(field);
			}
		}
		return result;
	}

	public static List<GField> getInfoViewProperties(GClass clazz) {
		List<GField> result = null;
		if (clazz != null) {
			GAnnotation ann = clazz.getAnnotation(Finder.class);
			String infoProperties = StringUtils.forceNotNull((ann != null ? ann.getMemberString("info") : null));
			if (infoProperties.length() > 0) {
				result = new ArrayList<GField>();
				String[] array = infoProperties.split(",");
				for (String fName : array) {
					GField field = clazz.getField(fName);
					if (field != null) {
						result.add(field);
					}
				}
			}
			else {
				result = getFinderProperties(clazz);
			}
		}
		return result;
	}
	
	public static boolean checkComment (GField field) {
		return checkKeywords(field, new String[] {"comment", "comentario"});
	}
	
	public static boolean checkPassword (GField field) {
		return checkKeywords(field, new String[] {"password", "contrase", "secret"});
	}
	
	protected static boolean checkKeywords (GField field, String[] keywords) {
		boolean result = false;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		for (int i = 0; i < keywords.length; i++) {
			result = (!result && keywords[i].contains(lName) ? true : result);
		}
		return result;
	}
	
	public static boolean checkPhoto (GField field) {
		return checkKeywords(field, new String[] {"photo", "image", "foto", "icon"});
	}

	public static boolean checkCompleteApplication() {
		return true;
	}

	public static boolean checkCreateFileEntity() {
		return true;
	}

	public static boolean checkCreateLogEntity() {
		return true;
	}
	
	
	
}
