package org.effortless.gen;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.core.ClassNodeHelper;
import org.effortless.model.Entity;

public class GField extends Object {

	public GField () {
		super();
		initiate();
	}
	
	public GField (GClass clazz, FieldNode field) {
		this();
		setClazz(clazz);
		setField(field);
	}
	
	protected void initiate () {
		initiateClazz();
		initiateField();
	}
	
	protected GClass clazz;
	
	protected void initiateClazz () {
		this.clazz = null;
	}
	
	public GClass getClazz () {
		return this.clazz;
	}
	
	public void setClazz (GClass newValue) {
		this.clazz = newValue;
	}
	
	protected FieldNode field;
	
	protected void initiateField () {
		this.field = null;
	}
	
	public FieldNode getField () {
		return this.field;
	}
	
	public void setField (FieldNode newValue) {
		this.field = newValue;
	}

	public boolean isType (Class<?> type) {
		boolean result = false;
		ClassNode fieldClass = getType();
		result = (type != null && fieldClass != null && fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(type)));
		return result;
	}
	
	
	public boolean isString () {
		return isType(String.class);
	}
	
	public boolean isTime () {
		return isType(Time.class);
	}

	public boolean isTimestamp () {
		return isType(Timestamp.class);
	}

	public boolean isDate () {
		return isType(Date.class);
	}

	public boolean isBoolean () {
		return isType(Boolean.class);
	}
	
	public boolean isInteger () {
		return isType(Integer.class);
	}
	
	public boolean isDouble () {
		return isType(Double.class);
	}
	
	public boolean isEnum () {
		return isType(Enum.class);
	}
	
	public boolean isFile () {
		return isType(File.class);
	}
	
	public boolean isCollection () {
		return isType(Collection.class);
	}
	
	public boolean isList () {
		return isType(List.class);
	}
	
	public boolean isEntity () {
		return isType(Entity.class);
	}

	public String getName () {
		return (this.field != null ? this.field.getName() : null);
	}

	public String getGetterName () {
		String result = null;
		String fieldName = getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "get" + fieldName;
		return result;
	}
	
	public String getInitiateName () {
		String result = null;
		String fieldName = getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "initiate" + fieldName;
		return result;
	}
	
	public String getSetterName () {
		String result = null;
		String fieldName = getName();
		fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		result = "set" + fieldName;
		return result;
	}

	public ClassNode getType () {
		return (this.field != null ? this.field.getType() : null);
	}

	public void setModifiers(int modifiers) {
		if (this.field != null) {
			this.field.setModifiers(modifiers);
		}
	}

	public void setType(ClassNode type) {
		if (type != null && this.field != null) {
			this.field.setType(type);
		}
	}
	
	public void setType(GClass type) {
		if (type != null) {
			setType(type.getClassNode());
		}
	}

	public GMethod getGetterMethod() {
		GMethod result = null;
		String methodName = getGetterName();
		result = (this.clazz != null ? this.clazz.getMethod(methodName) : null);
		return result;
	}
	
}
