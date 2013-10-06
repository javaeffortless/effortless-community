package org.effortless.gen;

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.core.Collections;
import org.effortless.core.ModelException;
import org.effortless.core.StringUtils;
import org.effortless.gen.classes.EntityClassTransformation;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.BoolFields;
import org.effortless.gen.fields.CountFields;
import org.effortless.gen.fields.DateFields;
import org.effortless.gen.fields.EnumFields;
import org.effortless.gen.fields.FileFields;
import org.effortless.gen.fields.ListFields;
import org.effortless.gen.fields.NumberFields;
import org.effortless.gen.fields.RefFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.gen.fields.TextFields;
import org.effortless.gen.fields.UserFields;
import org.effortless.model.AbstractEntity;
import org.effortless.model.FileEntity;
import org.objectweb.asm.Opcodes;

public class EntityClassGen extends ClassGen {

	protected EntityClassGen () {
		super();
	}
	
	public EntityClassGen (String name, SourceUnit sourceUnit) {
		super(name, sourceUnit);
	}
	
	public EntityClassGen (ClassNode clazz, SourceUnit sourceUnit) {
		super(clazz);
		this.sourceUnit = sourceUnit;
	}
	
	public EntityClassGen addCreateClone () {
		List<FieldNode> fields = this.clazz.getFields();
		
		MethodGen mg = addMethod("createClone").setProtected(true).setReturnType(this.clazz);
		mg.declVariable(this.clazz, "result", mg.callConstructor(this.clazz));
		for (FieldNode field : fields) {
			String fName = field.getName();
			String getter = "get" + StringUtils.capFirst(fName) + "";
			mg.add(mg.assign(mg.property("result", fName), mg.call(getter)));
		}
		mg.addReturn("result");

		return this;
	}
	
	public EntityClassGen addDoCompare () {
		List<FieldNode> fields = clazz.getFields();

		MethodGen mg = addMethod("doCompare").setProtected(true).addParameter(org.apache.commons.lang3.builder.CompareToBuilder.class, "cpBuilder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doCompare", "cpBuilder", "obj"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("cpBuilder", "append", mg.field(fName), mg.property("obj", fName)));
		}

		return this;
	}
	
	public EntityClassGen addDoEquals () {
		List<FieldNode> fields = this.clazz.getFields();
		
		MethodGen mg = addMethod("doEquals").setProtected(true).addParameter(org.apache.commons.lang3.builder.EqualsBuilder.class, "eqBuilder").addParameter(Object.class, "obj");
		mg.add(mg.call(mg.cteSuper(), "doEquals", "eqBuilder", "obj"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("eqBuilder", "append", mg.field(fName), mg.property("obj", fName)));
		}
		
		return this;
	}
	
	public EntityClassGen addDoHashCode () {
		List<FieldNode> fields = Restrictions.listNotNullUnique(this.clazz);
		
		MethodGen mg = addMethod("doHashCode").setProtected(true).addParameter(org.apache.commons.lang3.builder.HashCodeBuilder.class, "hcBuilder");
		mg.add(mg.call(mg.cteSuper(), "doHashCode", "hcBuilder"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("hcBuilder", "append", mg.cte(fName)));
			mg.add(mg.call("hcBuilder", "append", mg.field(fName)));
		}
		
		return this;
	}
	
	public EntityClassGen addDoToString () {
		List<FieldNode> fields = Restrictions.listNotNullUnique(this.clazz);
		
		MethodGen mg = addMethod("doToString").setProtected(true).addParameter(org.apache.commons.lang3.builder.ToStringBuilder.class, "toStringBuilder");
		mg.add(mg.call(mg.cteSuper(), "doToString", "toStringBuilder"));
		for (FieldNode field : fields) {
			String fName = field.getName();
			mg.add(mg.call("toStringBuilder", "append", mg.cte(fName), mg.field(fName)));
		}
		
		return this;
	}

	public EntityClassGen addToLabel () {
		MethodGen mg = addMethod("toLabel").setPublic(true).setReturnType(String.class).addParameter(java.util.Locale.class, "locale");
		mg.declVariable(String.class, "result");
		mg.declVariable(org.effortless.util.ToLabel.class, "toLabel", mg.callConstructor(org.effortless.util.ToLabel.class));
		List<FieldNode> fields = this.clazz.getFields();
		for (FieldNode field : fields) {
			String getterName = getGetterName(field);
			mg.add(mg.call("toLabel", "add", mg.call(getterName)));
		}
		mg.add(mg.assign("result", mg.call("toLabel", "getText")));
		mg.addReturn("result");
		
		return this;
	}

	public EntityClassGen protectField (FieldNode field) {
		field.setModifiers(Opcodes.ACC_PROTECTED);
		return this;
	}

	public EntityClassGen addInitiate (FieldNode field) {
		String methodName = getInitiateName(field);
		MethodGen mg = this.addMethod(methodName).setProtected(true);
		mg.add(mg.assign(mg.field(field.getName()), mg.cteNull()));
		
		return this;
	}
	
	public MethodGen addEntityGetter (FieldNode field, boolean includeAnnotation) {
		MethodGen result = null;
		String methodName = getGetterName(field);
		
		MethodGen mg = this.addMethod(methodName).setPublic(true).setReturnType(field.getType());
		mg.addReturn(mg.field(field));
		
		if (includeAnnotation) {
			String columnName = field.getName().toUpperCase();
			//@javax.persistence.Column(name="column", unique=true, nullable=false)
			AnnotationNode column = mg.addAnnotation(javax.persistence.Column.class, "name", mg.cte(columnName));
			if (isSingleUnique(field)) {
				column.addMember("unique", mg.cteTrue());
			}
			if (isNotNull(field)) {
				column.addMember("nullable", mg.cteFalse());
			}
			if (field.getType().isDerivedFrom(new ClassNode(String.class))) {
				String fieldName = field.getName();
				int length = (checkCommentField(fieldName) ? 3072 : 255);
				column.addMember("length", mg.cte(Integer.valueOf(length)));
			}
			
			//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
			mg.addAnnotation(javax.persistence.Basic.class, "fetch", mg.enumValue(javax.persistence.FetchType.class, "EAGER"));
		}
		
		result = mg;
		return result;
	}
	
	
	public void addEntitySetter (FieldNode field) {
		String setterName = getSetterName(field);
		String fName = field.getName();
		MethodGen mg = addMethod(setterName).setPublic(true).addParameter(field.getType(), "newValue");
		//String setterName = "set" + MetaClassHelper.capitalize(field.getName())
		if (true) {
			mg.add(mg.call("_setProperty", mg.cte(fName), mg.field(field), mg.assign(mg.field(field), "newValue")));//this._setProperty('text', this.text, this.text = newValue);
		}
		else {
			String getterName = getGetterName(field);
			mg.declVariable(ClassHelper.boolean_TYPE, "_loaded", mg.call("checkLoaded", mg.cte(fName), mg.cteTrue()));//boolean _loaded = checkLoaded("text", true);
			mg.declVariable(field.getType(), "oldValue", mg.triple("_loaded", mg.call(getterName), mg.cteNull()));//String oldValue = (_loaded ? this.getText() : null);
			MethodGen ifCode = mg.newBlock();
			ifCode.add(ifCode.assign(ifCode.field(field), "newValue"));//this.text = newValue;
			MethodGen cp = ifCode.newBlock();
			cp.add(cp.call("doChange" + StringUtils.capFirst(fName), "oldValue", "newValue"));//doChangeText(oldValue, newValue);
			cp.add(cp.call("firePropertyChange", mg.cte(fName), mg.var("oldValue"), mg.var("newValue")));//firePropertyChange("text", oldValue, newValue);
			ifCode.addIf(ifCode.var("_loaded"), cp);//if (_loaded) {
			mg.addIf(mg.or(mg.not("_loaded"), mg.not(mg.call("_equals", "oldValue", "newValue"))), ifCode);//if (!_loaded || !_equals(oldValue, newValue)) {
		}
	}

	protected static final String[] COMMENT_NAMES = {"comment", "comentario", "remark", "observacion", "annotation", "anotacion"};
	
	protected boolean checkCommentField (String fieldName) {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String[] ARRAY_SINGLE_UNIQUE = {"code", "codigo", "cif", "nif", "dni", "passport"};
	public static final Object[][] ARRAY_SINGLE_UNIQUE_DEPENDS = {{"name", new String[] {"surname"}}, {"nombre", new String[] {"apellido"}}};
	
	public List<FieldNode> listNotNull () {
		List<FieldNode> result = null;
		if (this.clazz != null) {
			result = new ArrayList<FieldNode>();
			List<FieldNode> fields = this.clazz.getFields();
			for (FieldNode field : fields) {
				if (isNotNull(field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public List<FieldNode> listUnique () {
		List<FieldNode> result = null;
		if (this.clazz != null) {
			result = new ArrayList<FieldNode>();
			List<FieldNode> fields = this.clazz.getFields();
			for (FieldNode field : fields) {
				if (isUnique(field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
		
	public List<FieldNode> listNotNullUnique () {
		List<FieldNode> result = null;
		if (this.clazz != null) {
			List<FieldNode> notNull = listNotNull();
			List<FieldNode> unique = listUnique();
			result = new ArrayList<FieldNode>();
			Collections.addAll(result, notNull);
			Collections.addAll(result, unique);
		}
		return result;
	}

	public Boolean isUnique (FieldNode field) {
		return isSingleUnique(field);
	}
	
	public Boolean isSingleUnique (FieldNode field) {
		Boolean result = false;
		if (this.clazz != null && field != null) {
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
						result = !containsAnyField((String[])it[1]);
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
	
	public Boolean containsAnyField (String[] names) {
		Boolean result = false;
		for (String name : names) { 
			List<FieldNode> fields = this.clazz.getFields();
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
	
	public Boolean isNotNull (FieldNode field) {
		Boolean result = false;
		if (this.clazz != null && field != null) {
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
	

	
	
	
	public String getGetterName (FieldNode field) {
		String result = null;
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "get" + fieldName;
		//		String methodName = "get" + field.getName().capitalize()
		
		return result;
	}
	
	public String getInitiateName (FieldNode field) {
		String result = null;
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "initiate" + fieldName;
		//		String methodName = "get" + field.getName().capitalize()
		
		return result;
	}
	
	public String getSetterName (FieldNode field) {
		String result = null;
		String fieldName = field.getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "set" + fieldName;
		return result;
	}

	
	
	
	
	
	protected void textProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		this.addEntityGetter(field, true);
		this.addEntitySetter(field);
	}
	
	protected void dateProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		MethodGen getter = this.addEntityGetter(field, true);
		this.addEntitySetter(field);
		
		//@Temporal(TemporalType.TIMESTAMP)
//		@Type(type="timestamp")
//		@org.hibernate.annotations.Type(type="org.effortless.model.FileUserType")
		String type = null;
		String typeName = field.getType().getName();
		if ("java.sql.Date".equals(typeName)) {
//			type = "DATE";//javax.persistence.TemporalType.DATE;
//			type = "org.effortless.model.DateSqlUserType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		else if ("java.sql.Time".equals(typeName)) {
			type = "TIME";//javax.persistence.TemporalType.TIME;
			type = "org.effortless.model.TimeUserType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		else if ("java.sql.Timestamp".equals(typeName)) {
//			type = "TIMESTAMP";//javax.persistence.TemporalType.TIMESTAMP;
			type = "timestamp";
//			type = "org.effortless.model.TimestampType";
//			field.setType(new ClassNode(java.util.Date.class));
		}
		
		if (type != null) {
			getter.addAnnotation(org.hibernate.annotations.Type.class, "type", getter.cte(type));
		}
	}
	
	protected void boolProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		this.addEntityGetter(field, true);
		this.addEntitySetter(field);
	}
	
	protected void countProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		this.addEntityGetter(field, true);
		this.addEntitySetter(field);
	}
	
	protected void numberProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		this.addEntityGetter(field, true);
		this.addEntitySetter(field);
	}
	
	protected void enumProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		MethodGen getter = this.addEntityGetter(field, false);
		this.addEntitySetter(field);

		getter.addAnnotation(javax.persistence.Enumerated.class, "value", getter.enumValue(javax.persistence.EnumType.class, "STRING"));//@javax.persistence.Enumerated(javax.persistence.EnumType.STRING)
	}

	protected void fileProcessField (FieldNode field) {
		String columnName = field.getName().toUpperCase() + "_ID";
		String getterName = getGetterName(field);
		String setterName = getSetterName(field);
		String keyFileEntity = this.clazz.getName() + "." + FileEntity.KEY_CLASS_NEEDS;
		GenContext.set(keyFileEntity, Boolean.TRUE);
		ClassNode fileClazz = EntityClassTransformation.tryNeedsFileEntity(this.clazz, this.sourceUnit);
		
		//	protected FileEntity fichero;
		field.setModifiers(Opcodes.ACC_PROTECTED);
		field.setType(fileClazz);
		
		addInitiate(field);
		
		
		
		
		
		
		MethodGen getter = addEntityGetter(field, false);
		AnnotationNode ann = getter.addAnnotation(javax.persistence.ManyToOne.class);//@javax.persistence.ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
		ann.addMember("cascade", getter.enumValue(javax.persistence.CascadeType.class, "ALL"));
		ann.addMember("targetEntity", getter.cteClass(field.getType()));
		ann.addMember("fetch", getter.enumValue(javax.persistence.FetchType.class, "LAZY"));
		getter.addAnnotation(javax.persistence.JoinColumn.class, "name", columnName);//@javax.persistence.JoinColumn(name="FICHERO")

		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
//		getter.addAnnotation(javax.persistence.Basic.class, "fetch", getter.enumValue(javax.persistence.FetchType.class, "EAGER"));
		
		
//		@javax.persistence.Transient
//		public java.io.File getFicheroFile () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getContent() : null);
//		}
		MethodGen mg = this.addMethod(getterName + "File").setPublic(true).setReturnType(java.io.File.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContent"), mg.cteNull()));
		
		
//		@javax.persistence.Transient
//		public String getFicheroPath () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getPath() : null);
//		}
		mg = this.addMethod(getterName + "Path").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
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
		mg = this.addMethod(setterName).setPublic(true).addParameter(java.io.File.class, "newValue");
		MethodGen ifCode = mg.newBlock();
		ifCode.declVariable(fileClazz, "entity", ifCode.call(getterName));
		ifCode.add(ifCode.assign(ifCode.var("entity"), ifCode.triple(ifCode.notNull("entity"), ifCode.var("entity"), ifCode.callConstructor(fileClazz))));
		ifCode.add(ifCode.call("entity", "setContent", ifCode.var("newValue")));
		ifCode.add(ifCode.call(setterName, ifCode.var("entity")));
		MethodGen elseCode = mg.newBlock();
		elseCode.add(elseCode.call(setterName, elseCode.cast(fileClazz, elseCode.cteNull())));
		mg.addIf(mg.notNull("newValue"), ifCode, elseCode);
		
		
//		public void setFichero (String newValue) {
//			setFichero(new java.io.File(newValue));
//		}
		mg = this.addMethod(setterName).setPublic(true).addParameter(String.class, "newValue");
		mg.add(mg.call(setterName, mg.callConstructor(java.io.File.class, mg.var("newValue"))));
		
		
//		@javax.persistence.Transient
//		public String getFicheroName () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getName() : null);
//		}
		mg = this.addMethod(getterName + "Name").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getName"), mg.cteNull()));
		
//		@javax.persistence.Transient
//		public String getFicheroFormat () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getFormat() : null);
//		}
		mg = this.addMethod(getterName + "Format").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getFormat"), mg.cteNull()));

//		@javax.persistence.Transient
//		public String getFicheroContentType () {
//			FileEntity fichero = getFicheroEntity();
//			return (fichero != null ? fichero.getContentType() : null);
//		}
		mg = this.addMethod(getterName + "ContentType").setPublic(true).setReturnType(String.class);
		mg.addAnnotation(javax.persistence.Transient.class);
		mg.declVariable(fileClazz, "varFile", mg.call(getterName));
		mg.addReturn(mg.triple(mg.notNull("varFile"), mg.call("varFile", "getContentType"), mg.cteNull()));
	}
	
	protected void listProcessField (FieldNode field) {
		
	}
	
	protected void refProcessField (FieldNode field) {
		this.protectField(field);
		this.addInitiate(field);
		MethodGen getter = this.addEntityGetter(field, false);
		
		
		
		this.addEntitySetter(field);

		//@javax.persistence.Basic(fetch = javax.persistence.FetchType.EAGER)
		getter.addAnnotation(javax.persistence.Basic.class, "fetch", getter.enumValue(javax.persistence.FetchType.class, "EAGER"));
		
		//@ManyToOne() 
		AnnotationNode ann = getter.addAnnotation(javax.persistence.ManyToOne.class);
		ann.addMember("cascade", getter.enumValue(javax.persistence.CascadeType.class, "ALL"));
		ann.addMember("targetEntity", getter.cteClass(field.getType()));
		ann.addMember("fetch", getter.enumValue(javax.persistence.FetchType.class, "LAZY"));
		
		String columnName = field.getName().toUpperCase() + "_ID";
		AnnotationNode column = getter.addAnnotation(javax.persistence.JoinColumn.class, "name", getter.cte(columnName));//@JoinColumn(name="CUST_ID")
		
		//@javax.persistence.Column(name="column", unique=true, nullable=false)
		if (isSingleUnique(field)) {
			column.addMember("unique", getter.cteTrue());
		}
		if (isNotNull(field)) {
			column.addMember("nullable", getter.cteFalse());
		}
	}

	public void processField (FieldNode field) {
		ClassNode fieldClass = field.getType();
//		Class fieldClass = fieldClass.getPlainNodeReference().getTypeClass()
		if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(String.class))) {
			textProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Date.class))) {
			dateProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Boolean.class))) {
			boolProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Integer.class))) {
			countProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Double.class))) {
			numberProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Enum.class))) {
			enumProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(File.class))) {
			fileProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Collection.class))) {
			listProcessField(field);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(List.class))) {
			listProcessField(field);
		}
		else {
			refProcessField(field);
		}
	}
	
	
	
	
	
	
	
	
	
	public void addDeleteField () {
		addInterface(org.effortless.model.MarkDeleted.class);
		
		String fieldName = "deleted";
		Class<?> fieldType = Boolean.class;
		
		FieldNode field = addField(fieldType, fieldName);
		this.addInitiate(field);
		this.addEntityGetter(field, true);
		this.addEntitySetter(field);
	}


	public EntityClassGen alterActions () {
		if (this.clazz != null) {
			List<MethodNode> methods = this.clazz.getAllDeclaredMethods();
			for (MethodNode method : methods) {
				if (checkValidMethod(method)) {
					modifyAction(method);
				}
			}
		}
		return this;
	}
	
	
	/*
	 * 


	public String doAction(String text, Integer ammount) {// throws ModelException {
		String result = null;
		Class<?>[] __paramTypes = [String.class, Integer.class];
		Object[] __paramValues = [text, ammount];
		if (this._checkSecurityAction("doAction", String.class, __paramTypes, __paramValues)) {
			__startExecutionTime();
			boolean __stopExecutionTime = false;
			Long __executionTime = null;
			boolean __saveLog = true;
			Object[] __paramNamesValues = ["text", text, "ammount", ammount];
			String __commentLog = __toCommentLog(__paramNamesValues);
			try {
				System.out.println("EJECUTANDO doAction");
				__executionTime = __stopExecutionTime();
				__stopExecutionTime = true;
				if (__saveLog) {
					this.trySaveActionLog("doAction", __commentLog, __executionTime);
				}
			}
			catch (org.effortless.model.exceptions.ModelException e) {
				if (!__stopExecutionTime) {
					__executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR + "doAction", __executionTime);
				throw e;
			}
			catch (Exception e) {
				if (!__stopExecutionTime) {
					__executionTime = __stopExecutionTime();
				}
				this.trySaveLogException(e, ERROR + "methodName", __executionTime);
				throw new org.effortless.model.exceptions.ModelException(e);
			}
		}
		return result;
	}


	 * 
	 */

	protected MethodGen modifyAction(MethodNode method) {
		MethodGen result = null;
		
		MethodGen mg = new MethodGen(method);
		result = mg;

		int statementsLength = mg.getPreviousCodeLength();

		String methodName = method.getName();
		ClassNode returnType = method.getReturnType();
			
		if (returnType != null && statementsLength > 1) {
			mg.addFirstPreserveCode();
		}
		
		Parameter[] parameters = method.getParameters();

		//Class<?>[] __paramTypes = [String.class, Integer.class];
		ClassNode classArrayType = ClassHelper.CLASS_Type.makeArray();
		mg.declVariable(classArrayType, "__paramTypes", mg.arrayTypes(parameters));
				
		//Object[] __paramValues = [text, ammount];
		ClassNode objectArrayType = ClassHelper.OBJECT_TYPE.makeArray();
		mg.declVariable(objectArrayType, "__paramValues", mg.arrayParameters(parameters));
				

		MethodGen ifCode = mg.newBlock();
		ifCode.add(ifCode.call("__startExecutionTime"));//__startExecutionTime();
		ifCode.declVariable(ClassHelper.boolean_TYPE, "__stopExecutionTime", ifCode.cteFalse());//boolean __stopExecutionTime = false;
		ifCode.declVariable(Long.class, "__executionTime", ifCode.cteNull());//Long __executionTime = null;
		ifCode.declVariable(ClassHelper.boolean_TYPE, "__saveLog", ifCode.cteTrue());//boolean __saveLog = true;
		ifCode.declVariable(objectArrayType, "__paramNamesValues", ifCode.arrayNameValue(parameters));//Object[] __paramNamesValues = ["text", text, "ammount", ammount];
		ifCode.add(ifCode.assign("__commentLog", ifCode.call("_toCommentLog", "__paramNamesValues")));//String __commentLog = _toCommentLog(__paramNamesValues);
		MethodGen tryBlock = ifCode.newBlock();
		tryBlock.addPreviousCode();//oldCode
		tryBlock.add(tryBlock.assign("__executionTime", tryBlock.call("__stopExecutionTime")));//__executionTime = __stopExecutionTime();
		tryBlock.add(tryBlock.assign("__stopExecutionTime", tryBlock.cteTrue()));//__stopExecutionTime = true;
		MethodGen blockSaveLog = tryBlock.newBlock();
		blockSaveLog.add(blockSaveLog.call("trySaveActionLog", blockSaveLog.cte(methodName), blockSaveLog.var("__commentLog"), blockSaveLog.var("__executionTime")));//this.trySaveActionLog("doAction", __commentLog, __executionTime);
		tryBlock.addIf(tryBlock.var("__saveLog"), blockSaveLog);//if (__saveLog) {
		
		MethodGen catchModel = ifCode.newBlock();
		catchModel.newVariable(ModelException.class, "e1");
		MethodGen ifCodeCatch = catchModel.newBlock();
		ifCodeCatch.add(ifCodeCatch.assign(ifCodeCatch.var("__executionTime"), ifCodeCatch.call("__stopExecutionTime")));//__executionTime = __stopExecutionTime();
		catchModel.addIf(catchModel.not(catchModel.var("__stopExecutionTime")), ifCodeCatch);//if (!__stopExecutionTime) {
		catchModel.add(catchModel.call("trySaveLogException", catchModel.var("e1"), catchModel.plus(catchModel.cte(AbstractEntity.ERROR), catchModel.cte(methodName)), catchModel.var("__executionTime")));//this.trySaveLogException(e, ERROR + "methodName", __executionTime);
		catchModel.throwException("e1");
		
		MethodGen catchGeneral = ifCode.newBlock();
		catchGeneral.newVariable(Exception.class, "e2");
		MethodGen ifCodeCatch2 = catchGeneral.newBlock();
		ifCodeCatch2.add(ifCodeCatch2.assign(ifCodeCatch2.var("__executionTime"), ifCodeCatch2.call("__stopExecutionTime")));//__executionTime = __stopExecutionTime();
		catchGeneral.addIf(catchGeneral.not(catchGeneral.var("__stopExecutionTime")), ifCodeCatch2);//if (!__stopExecutionTime) {
		catchGeneral.add(catchGeneral.call("trySaveLogException", catchGeneral.var("e2"), catchGeneral.plus(catchGeneral.cte(AbstractEntity.ERROR), catchGeneral.cte(methodName)), catchGeneral.var("__executionTime")));//this.trySaveLogException(e, ERROR + "methodName", __executionTime);
		catchGeneral.throwException(catchGeneral.callConstructor(ModelException.class, catchGeneral.var("e2")));
		
		ifCode.addTryCatch(tryBlock, new Object[]{ModelException.class, "e1", catchModel}, new Object[] {Exception.class, "e2", catchGeneral});
		mg.addIf(mg.call("_checkSecurityAction", mg.cte(methodName), mg.clazz(returnType), mg.var("__paramTypes"), mg.var("__paramValues")), ifCode);//if (this._checkSecurityAction("methodName", String.class, paramTypes, paramValues)) {

		if (returnType != null && !ClassHelper.VOID_TYPE.equals(returnType)) {
			mg.addLastPreserveCode();
		}
		
		return result;
	}

	protected static final String[] EXCLUSION_LIST = {"wait", "toString", "notify", "getClass", "notifyAll", "finalize", "registerNatives", "equals", "clone", "hashCode"};
	
	protected boolean checkValidMethod(MethodNode method) {
		boolean result = true;
		String methodName = method.getName();
		if (methodName != null) {
			for (String exclude : EXCLUSION_LIST) {
				if (methodName.equals(exclude)) {
					result = false;
					break;
				}
			}
		}
		if (result && !method.isPublic()) {
			result = false;
		}
		return result;
	}
	
	
//si return type => se considera de consulta => NOLOG
//si NO return type => se considera de modificacion => LOG
	
	
	
	
	
}
