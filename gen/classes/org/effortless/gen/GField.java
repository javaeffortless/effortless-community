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
import org.objectweb.asm.Opcodes;

public class GField extends Object implements GNode {

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
		List<GMethod> methods = (this.clazz != null ? this.clazz.getListMethods(methodName) : null);
		int methodsSize = (methods != null ? methods.size() : 0);
		if (methodsSize > 0) {
			ClassNode type = this.getType();
			for (GMethod method : methods) {
				if (method.isReturnType(type) && method.getNumParameters() <= 0) {
					result = method;
					break;
				}
			}
		}
		return result;
	}

	public GMethod getSetterMethod() {
		GMethod result = null;
		String methodName = getSetterName();
		List<GMethod> methods = (this.clazz != null ? this.clazz.getListMethods(methodName) : null);
		int methodsSize = (methods != null ? methods.size() : 0);
		if (methodsSize > 0) {
			ClassNode type = this.getType();
			for (GMethod method : methods) {
				if (method.isVoid() && method.getNumParameters() == 1 && method.checkParameterType(0, type)) {
					result = method;
					break;
				}
			}
		}
//		result = (this.clazz != null ? this.clazz.getMethod(methodName) : null);
		return result;
	}

	public GApplication getApplication () {
		return (this.clazz != null ? this.clazz.getApplication() : null);
	}

	public String toString () {
		return this.getClazz() + "->" + getName() + ":" + getType().getName();
	}

	public boolean equals (Object o) {
		boolean result = false;
		GField obj = null;
		try { obj = (GField)o; } catch (ClassCastException e) {}
		if (obj != null && this.field != null) {
			result = this.field.equals(obj.field);
		}
		return result;
	}

	protected boolean _isModifier (int modifier) {
		boolean result = false;
		if (this.field != null) {
			int modifiers = this.field.getModifiers();
			result = (modifiers & modifier) != 0;
		}
		return result;
	}
	
	public boolean isStatic() {
		return _isModifier(Opcodes.ACC_STATIC);
	}

	public boolean isFinal() {
		return _isModifier(Opcodes.ACC_FINAL);
	}

	public boolean isProperty() {
		boolean result = false;
		result = true;
		GMethod setter = (result ? getSetterMethod() : null);
		result = result && (setter != null && setter.isPublic());
		GMethod getter = (result ? getGetterMethod() : null);
		result = result && (getter != null && getter.isPublic());
		return result;
	}
	
}
