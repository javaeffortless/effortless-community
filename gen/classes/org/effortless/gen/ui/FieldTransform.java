package org.effortless.gen.ui;

import org.effortless.ann.HashPassword;
import org.effortless.core.StringUtils;
import org.effortless.gen.GField;
import org.effortless.gen.InfoModel;
import org.effortless.model.Filter;

public class FieldTransform extends Object/* implements Transform<GField>*/ {

	public FieldTransform () {
		super();
		initiate();
	}
	
	protected void initiate () {
		
	}

	public String writeReadonly (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		if ((field.isFile() || field.isType(org.effortless.model.FileEntity.class)) && InfoModel.checkPhoto(field)) {
			result = "<photo-field value=\"$" + lName + "\" readonly=\"true\" />";
		}
		else {
			result = "<label-field value=\"$" + lName + "\" />";
		}
		return result;
	}
	
	
	public String writeZul (GField field) {
		String result = null;
		
		if (field.isString()) {
			result = textField(field);
		}
		else if (field.isTime()) {
			result = timeField(field);
		}
		else if (field.isTimestamp()) {
			result = timestampField(field);
		}
		else if (field.isDate()) {
			result = dateField(field);
		}
		else if (field.isBoolean()) {
			result = boolField(field);
		}
		else if (field.isInteger()) {
			result = intField(field);
		}
		else if (field.isDouble()) {
			result = numberField(field);
		}
		else if (field.isEnum()) {
			result = enumField(field);
		}
		else if (field.isFile() || field.isType(org.effortless.model.FileEntity.class)) {
			result = fileField(field);
		}
		else if (field.isCollection() || field.isList()) {
			result = listField(field);
		}
		else {
			result = refField(field);
		}
		
		return result;
	}

	
	public String textField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		if (InfoModel.checkComment(field)) {
			result = "<comment-field value=\"$" + lName + "\" />";
		}
		else if (InfoModel.checkPassword(field)) {
			String hashAlgorithm = null;
			try { hashAlgorithm = field.getAnnotation(HashPassword.class).getValue(); } catch (Throwable t) {}
			String attrHashAlgorithm = (hashAlgorithm != null ? " hashAlgorithm=\"" + hashAlgorithm + "\"" : "");
			boolean isFilter = field.getClazz().isType(Filter.class);
			String attrType = (isFilter ? " type=\"login\"" : "");
			result = "<password-field value=\"$" + lName + "\"" + attrHashAlgorithm + attrType +  " />";
		}
		else {
			result = "<text-field value=\"$" + lName + "\" />";
		}
		return result;
	}
	
	public String intField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
		result = "<count-field value=\"$" + lName + "\" />";
		
		return result;
	}
	
	public String dateField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<date-field value=\"$" + lName + "\" />";
		
		return result;
	}
	
	public String timeField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<time-field value=\"$" + lName + "\" />";
		return result;
	}
	
	public String timestampField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<timestamp-field value=\"$" + lName + "\" />";
		return result;
	}
	
	public String boolField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<bool-field value=\"$" + lName + "\" />";
		return result;
	}
	
	public String numberField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		result = "<number-field value=\"$" + lName + "\" />";
		return result;
	}
	
	
	
	public String enumField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
//    	result += "<ref-field values="$listado" value="$referencia" />
   	  	result = "<enum-field value=\"$" + lName + "\" />";
        
		return result;
	}
	
	public String fileField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
		if (InfoModel.checkPhoto(field)) {
			result = "<photo-field value=\"$" + lName + "\" />";
		}
		else {
			result = "<file-field value=\"$" + lName + "\" />";
		}
//		<file-field name="$etiqueta" content="$fichero" />
		
		
//		result = "<button label=\"" + attrLabel + "\" upload=\"" + attrUpload + "\"" + attrApply + attrViewModel + " onUpload=\"@command((empty " + vmFieldName + ".value ? 'upload' : null), upEvent=event)\" onClick=\"@command((empty " + vmFieldName + ".value ? null : 'download'))\"" + "/>";
		
		return result;
	}
	
	public String listField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
		result = "<list-field value=\"$" + lName + "\" />";
//		<file-field name="$etiqueta" content="$fichero" />
		
//		result = "<button label=\"" + attrLabel + "\" upload=\"" + attrUpload + "\"" + attrApply + attrViewModel + " onUpload=\"@command((empty " + vmFieldName + ".value ? 'upload' : null), upEvent=event)\" onClick=\"@command((empty " + vmFieldName + ".value ? null : 'download'))\"" + "/>";
		
		return result;
	}
	
	public String refField (GField field) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		result = "<ref-field value=\"$" + lName + "\" />";
		
		return result;
	}
	
}
