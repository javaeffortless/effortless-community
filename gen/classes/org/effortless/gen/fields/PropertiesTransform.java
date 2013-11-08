package org.effortless.gen.fields;

import org.codehaus.groovy.ast.expr.Expression;
import org.effortless.core.Collections;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.GMethod;
import org.effortless.gen.impl.CreateFileEntityTransform;
import org.objectweb.asm.Opcodes;

public class PropertiesTransform extends AbstractPropertiesTransform {

	protected void textProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void dateProcessField (GField field) {
		protectField(field);
		addInitiateDefaultDate(field);
		addEntityGetter(field);
		addEntitySetter(field);
//		GMethod mg = addEntitySetter(field);
//		mg.add(mg.debug("debug set " + field.getName()));
	}
	
	protected void boolProcessField (GField field) {
		protectField(field);
		addInitiateDefaultBoolean(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void countProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void numberProcessField (GField field) {
		protectField(field);
		addInitiateDefaultNumber(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void enumProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}

	protected void fileProcessField (GField field) {
		CreateFileEntityTransform feT = new CreateFileEntityTransform();
		feT.process(field);
		GClass fileClazz = feT.getResult();

		//	protected FileEntity fichero;
		field.setModifiers(Opcodes.ACC_PROTECTED);
		field.setType(fileClazz);
		
		addInitiate(field);
		
		String getterName = field.getGetterName();
		String setterName = field.getSetterName();
		
		
		
		addEntityGetter(field);

//		@javax.persistence.Transient
//		public java.io.File getFicheroFile () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getContent() : null);
//		}
		GMethod mg = field.getClazz().addMethod(getterName + "File").setPublic(true).setReturnType(java.io.File.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz.getClassNode(), "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContent"), mg.cteNull()));
		
		
//		@javax.persistence.Transient
//		public String getFicheroPath () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getPath() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "Path").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz.getClassNode(), "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getPath"), mg.cteNull()));
		
		addEntitySetter(field);
		
//		public void setFichero (java.io.File newValue) {
//			if (newValue != null) {
//				FileEntity entity = getFichero();
//				entity = (entity != null ? entity : new FileEntity());
//				entity.setContent(newValue);
//				setFichero(entity);
//			}
//			else {
//				setFichero((FileEntity)null);
//			}
//		}
		mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(java.io.File.class, "newValue");
		GMethod ifCode = mg.newBlock();
		ifCode.declVariable(fileClazz.getClassNode(), "entity", ifCode.call(getterName));
		ifCode.add(ifCode.assign(ifCode.var("entity"), ifCode.triple(ifCode.notNull("entity"), ifCode.var("entity"), ifCode.callConstructor(fileClazz.getClassNode()))));
		ifCode.add(ifCode.call("entity", "setContent", ifCode.var("newValue")));
		ifCode.add(ifCode.call(setterName, ifCode.var("entity")));
		GMethod elseCode = mg.newBlock();
		elseCode.add(elseCode.call(setterName, elseCode.cast(fileClazz.getClassNode(), elseCode.cteNull())));
		mg.addIf(mg.notNull("newValue"), ifCode, elseCode);
		
		
//		public void setFichero (String newValue) {
//			setFichero(new java.io.File(newValue));
//		}
		mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(String.class, "newValue");
		mg.add(mg.call(setterName, mg.callConstructor(java.io.File.class, mg.var("newValue"))));
		
		
//		@javax.persistence.Transient
//		public String getFicheroName () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getName() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "Name").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz.getClassNode(), "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getName"), mg.cteNull()));
		
//		@javax.persistence.Transient
//		public String getFicheroFormat () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getFormat() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "Format").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz.getClassNode(), "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getFormat"), mg.cteNull()));

//		@javax.persistence.Transient
//		public String getFicheroContentType () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getContentType() : null);
//		}
		mg = field.getClazz().addMethod(getterName + "ContentType").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz.getClassNode(), "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContentType"), mg.cteNull()));
	}
	
	protected void listProcessField (GField field) {
		
	}
	
	protected void refProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	

	
	
	
	
	protected void protectField (GField field) {
		field.setModifiers(Opcodes.ACC_PROTECTED);
		field.getField().setInitialValueExpression(null); 		
	}

	protected void addInitiate (GField field) {
		String methodName = field.getInitiateName();
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		mg.add(mg.assign(mg.field(field), mg.cteNull()));
	}
	

	
	public GMethod addEntityGetter (GField field) {
		GMethod result = null;
		String methodName = field.getGetterName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setPublic(true).setReturnType(field.getType());
		mg.addReturn(mg.field(field));
		result = mg;
		return result;
	}
	
	public GMethod addEntitySetter (GField field) {
		GMethod result = null;
		String setterName = field.getSetterName();
		String fName = field.getName();
		GMethod mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(field.getType(), "newValue");
		mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(field), mg.assign(mg.field(field), "newValue")));//this._setProperty('text', this.text, this.text = newValue);
		result = mg;
		return result;
	}

	
	
	protected void addInitiateDefaultBoolean (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		boolean defaultValue = loadDefaultBooleanValue(field);
		Expression defaultExpression = (defaultValue ? mg.cteTRUE() : mg.cteFALSE());
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
	}
	
	public static final String[] BOOL_DEFAULT_TRUE = {"activo", "enabled"};
	
	protected boolean loadDefaultBooleanValue (GField field) {
		boolean result = false;
		String fieldName = (field != null ? field.getName() : null);
		result = Collections.like(BOOL_DEFAULT_TRUE, fieldName);
		return result;
	}

	protected void addInitiateDefaultDate (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		boolean defaultValue = loadDefaultDateValue(field);
//		Expression defaultExpression = (defaultValue ? mg.callConstructor(java.util.Date.class) : mg.cteNull());
//		Expression defaultExpression = mg.callConstructor(java.util.Date.class);
//		Expression defaultExpression = mg.callStatic(org.effortless.core.DateUtils.class, "getCurrentDate");
		Expression defaultExpression = (defaultValue ? mg.callStatic(org.effortless.core.DateUtils.class, "getCurrentDate") : mg.cteNull());
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
//		mg.add(mg.debug("inicializando " + field.getName()));
	}
	
	public static final String[] DATE_CURRENT_DEFAULT_TRUE = {"alta", "create"};
	public static final String[] DATE_CURRENT_DEFAULT_TRUE_EQUALS = {"fecha", "date"};
	
	protected boolean loadDefaultDateValue (GField field) {
		boolean result = false;
		String fieldName = (field != null ? field.getName() : null);
		result = Collections.like(DATE_CURRENT_DEFAULT_TRUE, fieldName);
		result = result || Collections.contains(DATE_CURRENT_DEFAULT_TRUE_EQUALS, fieldName);
		return result;
	}
	
	protected void addInitiateDefaultNumber (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		Expression defaultExpression = mg.callStatic(Double.class, "valueOf", mg.cte(0.0));
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
	}

}
