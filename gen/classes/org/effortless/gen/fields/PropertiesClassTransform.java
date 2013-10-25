package org.effortless.gen.fields;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.Collections;
import org.effortless.core.StringUtils;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.EntityClassGen;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.GMethod;
import org.effortless.gen.GenContext;
import org.effortless.model.FileEntity;
import org.effortless.model.FileEntityTuplizer;
import org.objectweb.asm.Opcodes;

public class PropertiesClassTransform extends Object implements ClassTransform {

	@Override
	public void process(GClass cg) {
		// TODO Auto-generated method stub
		ClassNode clazz = cg.getClassNode();
		List<FieldNode> fields = clazz.getFields();
		for (FieldNode field : fields) {
			GField gField = new GField(cg, field);
			processField(gField);
		}
		
	}

	
	public void processField (GField field) {
		if (field.isString()) {
			textProcessField(field);
		}
		else if (field.isDate()) {
			dateProcessField(field);
		}
		else if (field.isBoolean()) {
			boolProcessField(field);
		}
		else if (field.isInteger()) {
			countProcessField(field);
		}
		else if (field.isDouble()) {
			numberProcessField(field);
		}
		else if (field.isEnum()) {
			enumProcessField(field);
		}
		else if (field.isFile()) {
			fileProcessField(field);
		}
		else if (field.isCollection()) {
			listProcessField(field);
		}
		else if (field.isList()) {
			listProcessField(field);
		}
		else {
			refProcessField(field);
		}
	}

	protected void textProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		addEntityGetter(field);
		addEntitySetter(field);
	}
	
	protected void dateProcessField (GField field) {
		protectField(field);
		addInitiateDefaultDate(field);
		GMethod getter = addEntityGetter(field);
		addEntitySetter(field);
//		//@Temporal(TemporalType.TIMESTAMP)
////		@Type(type="timestamp")
////		@org.hibernate.annotations.Type(type="org.effortless.model.FileUserType")
//		String type = null;
//		String typeName = field.getType().getName();
//		if ("java.sql.Date".equals(typeName)) {
////			type = "DATE";//javax.persistence.TemporalType.DATE;
////			type = "org.effortless.model.DateSqlUserType";
////			field.setType(new ClassNode(java.util.Date.class));
//		}
//		else if ("java.sql.Time".equals(typeName)) {
//			type = "TIME";//javax.persistence.TemporalType.TIME;
//			type = "org.effortless.model.TimeUserType";
////			field.setType(new ClassNode(java.util.Date.class));
//		}
//		else if ("java.sql.Timestamp".equals(typeName)) {
////			type = "TIMESTAMP";//javax.persistence.TemporalType.TIMESTAMP;
//			type = "timestamp";
////			type = "org.effortless.model.TimestampType";
////			field.setType(new ClassNode(java.util.Date.class));
//		}
//		
//		if (type != null) {
//			getter.addAnnotation(org.hibernate.annotations.Type.class, "type", getter.cte(type));
//		}
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
//
//		getter.addAnnotation(javax.persistence.Enumerated.class, "value", getter.enumValue(javax.persistence.EnumType.class, "STRING"));//@javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
	}

	protected void fileProcessField (GField field) {
		String columnName = field.getName().toUpperCase() + "_ID";
		String getterName = field.getGetterName();
		String setterName = field.getSetterName();
		String keyFileEntity = field.getClazz().getName() + "." + FileEntity.KEY_CLASS_NEEDS;
		GenContext.set(keyFileEntity, Boolean.TRUE);
		ClassNode fileClazz = GClass.tryNeedsNewExternalEntity(field.getClazz(), field.getClazz().getSourceUnit(), FileEntity.class, FileEntity.KEY_CLASS_NEEDS, FileEntity.KEY_APP_NEEDS, FileEntityTuplizer.class);

		
		//	protected FileEntity fichero;
		field.setModifiers(Opcodes.ACC_PROTECTED);
		field.setType(fileClazz);
		
		addInitiate(field);
		
		
		
		
		
		
		GMethod getter = addEntityGetter(field);
//		AnnotationNode ann = getter.addAnnotation(javax.persistence.ManyToOne.class);//@javax.persistence.ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
//		ann.addMember("cascade", getter.enumValue(javax.persistence.CascadeType.class, "ALL"));
//		ann.addMember("targetEntity", getter.cteClass(field.getType()));
//		ann.addMember("fetch", getter.enumValue(javax.persistence.FetchType.class, "LAZY"));
//		getter.addAnnotation(javax.persistence.JoinColumn.class, "name", columnName);//@javax.persistence.JoinColumn(name="FICHERO")
//
//		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
////		getter.addAnnotation(javax.persistence.Basic.class, "fetch", getter.enumValue(javax.persistence.FetchType.class, "EAGER"));
//		
//		
////		@javax.persistence.Transient
////		public java.io.File getFicheroFile () {
////			FileEntity fichero = getFicheroEntity();
////			return (fichero != null ? fichero.getContent() : null);
////		}
//		GMethod mg = this.addMethod(getterName + "File").setPublic(true).setReturnType(java.io.File.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
//		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
//		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContent"), mg.cteNull()));
//		
//		
////		@javax.persistence.Transient
////		public String getFicheroPath () {
////			FileEntity fichero = getFicheroEntity();
////			return (fichero != null ? fichero.getPath() : null);
////		}
//		mg = this.addMethod(getterName + "Path").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
//		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
//		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getPath"), mg.cteNull()));
//		
		addEntitySetter(field);
//		
////		public void setFichero (java.io.File newValue) {
////			if (newValue != null) {
////				FileEntity entity = getFichero();
////				entity = (entity != null ? entity : new FileEntity());
////				entity.setContent(newValue);
////				setFichero(entity);
////			}
////			else {
////				setFichero((FileEntity)null);
////			}
////		}
//		mg = this.addMethod(setterName).setPublic(true).addParameter(java.io.File.class, "newValue");
//		GMethod ifCode = mg.newBlock();
//		ifCode.declVariable(fileClazz, "entity", ifCode.call(getterName));
//		ifCode.add(ifCode.assign(ifCode.var("entity"), ifCode.triple(ifCode.notNull("entity"), ifCode.var("entity"), ifCode.callConstructor(fileClazz))));
//		ifCode.add(ifCode.call("entity", "setContent", ifCode.var("newValue")));
//		ifCode.add(ifCode.call(setterName, ifCode.var("entity")));
//		GMethod elseCode = mg.newBlock();
//		elseCode.add(elseCode.call(setterName, elseCode.cast(fileClazz, elseCode.cteNull())));
//		mg.addIf(mg.notNull("newValue"), ifCode, elseCode);
//		
//		
////		public void setFichero (String newValue) {
////			setFichero(new java.io.File(newValue));
////		}
//		mg = this.addMethod(setterName).setPublic(true).addParameter(String.class, "newValue");
//		mg.add(mg.call(setterName, mg.callConstructor(java.io.File.class, mg.var("newValue"))));
//		
//		
////		@javax.persistence.Transient
////		public String getFicheroName () {
////			FileEntity fichero = getFicheroEntity();
////			return (fichero != null ? fichero.getName() : null);
////		}
//		mg = this.addMethod(getterName + "Name").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
//		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
//		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getName"), mg.cteNull()));
//		
////		@javax.persistence.Transient
////		public String getFicheroFormat () {
////			FileEntity fichero = getFicheroEntity();
////			return (fichero != null ? fichero.getFormat() : null);
////		}
//		mg = this.addMethod(getterName + "Format").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
//		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
//		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getFormat"), mg.cteNull()));
//
////		@javax.persistence.Transient
////		public String getFicheroContentType () {
////			FileEntity fichero = getFicheroEntity();
////			return (fichero != null ? fichero.getContentType() : null);
////		}
//		mg = this.addMethod(getterName + "ContentType").setPublic(true).setReturnType(String.class);
//		mg.addAnnotation(javax.persistence.Transient.class);
//		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
//		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContentType"), mg.cteNull()));
	}
	
	protected void listProcessField (GField field) {
		
	}
	
	protected void refProcessField (GField field) {
		protectField(field);
		addInitiate(field);
		GMethod getter = addEntityGetter(field);
		
		
		
		addEntitySetter(field);

//		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
//		getter.addAnnotation(javax.persistence.Basic.class, "fetch", getter.enumValue(javax.persistence.FetchType.class, "EAGER"));
//		
//		//@ManyToOne() 
//		AnnotationNode ann = getter.addAnnotation(javax.persistence.ManyToOne.class);
//		ann.addMember("cascade", getter.enumValue(javax.persistence.CascadeType.class, "ALL"));
//		ann.addMember("targetEntity", getter.cteClass(field.getType()));
//		ann.addMember("fetch", getter.enumValue(javax.persistence.FetchType.class, "LAZY"));
//		
//		String columnName = field.getName().toUpperCase() + "_ID";
//		AnnotationNode column = getter.addAnnotation(javax.persistence.JoinColumn.class, "name", getter.cte(columnName));//@JoinColumn(name="CUST_ID")
//		
//		//@javax.persistence.Column(name="column", unique=true, nullable=false)
//		if (isSingleUnique(field)) {
//			column.addMember("unique", getter.cteTrue());
//		}
//		if (isNotNull(field)) {
//			column.addMember("nullable", getter.cteFalse());
//		}
	}
	

	
	
	
	
	protected void protectField (GField field) {
		field.setModifiers(Opcodes.ACC_PROTECTED);
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
		
//		if (includeAnnotation) {
//			String columnName = field.getName().toUpperCase();
//			//@javax.persistence.Column(name="column", unique=true, nullable=false)
//			AnnotationNode column = mg.addAnnotation(javax.persistence.Column.class, "name", mg.cte(columnName));
//			if (isSingleUnique(field)) {
//				column.addMember("unique", mg.cteTrue());
//			}
//			if (isNotNull(field)) {
//				column.addMember("nullable", mg.cteFalse());
//			}
//			if (field.getType().isDerivedFrom(ClassNodeHelper.toClassNode(String.class))) {
//				String fieldName = field.getName();
//				int length = (checkCommentField(fieldName) ? 3072 : 255);
//				column.addMember("length", mg.cte(Integer.valueOf(length)));
//			}
//			
//			//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
//			mg.addAnnotation(javax.persistence.Basic.class, "fetch", mg.enumValue(javax.persistence.FetchType.class, "EAGER"));
//		}
		
		result = mg;
		return result;
	}
	
	
	public void addEntitySetter (GField field) {
		String setterName = field.getSetterName();
		String fName = field.getName();
		GMethod mg = field.getClazz().addMethod(setterName).setPublic(true).addParameter(field.getType(), "newValue");
		//String setterName = "set" + MetaClassHelper.capitalize(field.getName())
//		if (true) {
			mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(field), mg.assign(mg.field(field), "newValue")));//this._setProperty('text', this.text, this.text = newValue);
//		}
//		else {
//			String getterName = field.getGetterName();
//			mg.declVariable(ClassHelper.boolean_TYPE, "_loaded", mg.call("checkLoaded", mg.cte(fName), mg.cteTrue()));//boolean _loaded = checkLoaded("text", true);
//			mg.declVariable(field.getType(), "oldValue", mg.triple("_loaded", mg.call(getterName), mg.cteNull()));//String oldValue = (_loaded ? this.getText() : null);
//			GMethod ifCode = mg.newBlock();
//			ifCode.add(ifCode.assign(ifCode.field(field), "newValue"));//this.text = newValue;
//			GMethod cp = ifCode.newBlock();
//			cp.add(cp.call("doChange" + StringUtils.capFirst(fName), "oldValue", "newValue"));//doChangeText(oldValue, newValue);
//			cp.add(cp.call("firePropertyChange", mg.cte(fName), mg.var("oldValue"), mg.var("newValue")));//firePropertyChange("text", oldValue, newValue);
//			ifCode.addIf(ifCode.var("_loaded"), cp);//if (_loaded) {
//			mg.addIf(mg.or(mg.not("_loaded"), mg.not(mg.call("_equals", "oldValue", "newValue"))), ifCode);//if (!_loaded || !_equals(oldValue, newValue)) {
//		}
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
		result = Collections.contains(BOOL_DEFAULT_TRUE, fieldName);
		return result;
	}

	
	
	
	
	protected void addInitiateDefaultDate (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		boolean defaultValue = loadDefaultDateValue(field);
		Expression defaultExpression = (defaultValue ? mg.callConstructor(java.util.Date.class) : mg.cteNull());
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
	}
	
	public static final String[] DATE_CURRENT_DEFAULT_TRUE = {"alta", "create"};
	
	protected boolean loadDefaultDateValue (GField field) {
		boolean result = false;
		String fieldName = (field != null ? field.getName() : null);
		result = Collections.contains(DATE_CURRENT_DEFAULT_TRUE, fieldName);
		return result;
	}
	
	
	

	
	protected void addInitiateDefaultNumber (GField field) {
		String methodName = field.getInitiateName();
		
		GMethod mg = field.getClazz().addMethod(methodName).setProtected(true);
		Expression defaultExpression = mg.callStatic(Double.class, "valueOf", mg.cte(0.0));
		mg.add(mg.assign(mg.field(field.getName()), defaultExpression));
	}

}
