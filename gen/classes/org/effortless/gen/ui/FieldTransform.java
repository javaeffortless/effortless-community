package org.effortless.gen.ui;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.StringUtils;

public class FieldTransform {

	public FieldTransform () {
		super();
		initiate();
	}
	
	protected void initiate () {
		
	}
	
	public String writeZul (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		
		ClassNode fieldClass = field.getType();
//		Class fieldClass = fieldClass.getPlainNodeReference().getTypeClass()
		if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(String.class))) {
			result = textField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Time.class))) {
			result = timeField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Timestamp.class))) {
			result = timestampField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Date.class))) {
			result = dateField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Boolean.class))) {
			result = boolField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Integer.class))) {
			result = intField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Double.class))) {
			result = numberField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Enum.class))) {
			result = enumField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(File.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(org.effortless.model.FileEntity.class))) {
			result = fileField(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Collection.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(List.class))) {
			result = listField(field, parentId, itemId, checkReadonly);
		}
		else {
			result = refField(field, parentId, itemId, checkReadonly);
		}
		
		return result;
	}

	
	public String writeZul_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		
		ClassNode fieldClass = field.getType();
//		Class fieldClass = fieldClass.getPlainNodeReference().getTypeClass()
		if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(String.class))) {
			result = textField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Time.class))) {
			result = timeField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Timestamp.class))) {
			result = timestampField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Date.class))) {
			result = dateField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Boolean.class))) {
			result = boolField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Integer.class))) {
			result = intField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Double.class))) {
			result = numberField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Enum.class))) {
			result = enumField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(File.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(org.effortless.model.FileEntity.class))) {
			result = fileField_old(field, parentId, itemId, checkReadonly);
		}
		else if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(Collection.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(List.class))) {
			result = listField_old(field, parentId, itemId, checkReadonly);
		}
		else {
			result = refField_old(field, parentId, itemId, checkReadonly);
		}
		
		return result;
	}
	
	public String textField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		if (checkComment(field)) {
			result = "<comment-field value=\"$" + lName + "\" />";
		}
		else if (checkPassword(field)) {
			result = "<password-field value=\"$" + lName + "\" />";
		}
		else {
			result = "<text-field value=\"$" + lName + "\" />";
		}
		return result;
	}
	
	protected boolean checkComment (FieldNode field) {
		return checkKeywords(field, new String[] {"comment", "comentario"});
	}
	
	protected boolean checkPassword (FieldNode field) {
		return checkKeywords(field, new String[] {"password", "contrase", "secret"});
	}
	
	protected boolean checkKeywords (FieldNode field, String[] keywords) {
		boolean result = false;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		for (int i = 0; i < keywords.length; i++) {
			result = (!result && keywords[i].contains(lName) ? true : result);
		}
		return result;
	}
	
	
	
	public String textField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
		
//		result = "<textbox" + attrValue + attrReadonly + " />";
		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";
		result = "<text-field" + attrValue + attrReadonly + attrTagValue + " />";
		
		return result;
	}
	
	public String intField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
		result = "<count-field value=\"$" + lName + "\" />";
		
		return result;
	}
	
	public String intField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
//		result = "<intbox" + attrValue + attrReadonly + " />";
		
		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";
		result = "<count-field" + attrValue + attrReadonly + attrTagValue + " />";
		
		return result;
	}
	
	public String dateField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<date-field value=\"$" + lName + "\" />";
		
		return result;
	}
	
	
	public String dateField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
		String attrButtonVisible = (checkReadonly ? " buttonVisible=\"@load(" + parentId + ".readonly eq false)\"" : "");
		String attrDisabled = (checkReadonly ? " disabled=\"@load(" + parentId + ".readonly)\"" : "");
//		result = "<datebox format=\"dd/MM/yyyy\"" + attrValue + attrReadonly + attrButtonVisible + attrDisabled + " />";
		
		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";
		result = "<date-field" + attrValue + attrReadonly + attrTagValue + " />";
		
		return result;
	}
	
	public String timeField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<time-field value=\"$" + lName + "\" />";
		return result;
	}
	
	public String timeField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
		String attrButtonVisible = (checkReadonly ? " buttonVisible=\"@load(" + parentId + ".readonly eq false)\"" : "");
		String attrDisabled = (checkReadonly ? " disabled=\"@load(" + parentId + ".readonly)\"" : "");
//		result = "<timebox cols=\"4\" format=\"HH:mm\"" + attrValue + attrReadonly + attrButtonVisible + attrDisabled + " />";
		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";

		result = "<time-field" + attrValue + attrReadonly + attrTagValue + " />";
		return result;
	}
	
	public String timestampField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<timestamp-field value=\"$" + lName + "\" />";
		return result;
	}
	
	public String timestampField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
		String attrButtonVisible = (checkReadonly ? " buttonVisible=\"@load(" + parentId + ".readonly eq false)\"" : "");
		String attrDisabled = (checkReadonly ? " disabled=\"@load(" + parentId + ".readonly)\"" : "");
//		result = "<datebox cols=\"16\" format=\"dd/MM/yyyy HH:mm\"" + attrValue + attrReadonly + attrButtonVisible + attrDisabled + " />";

		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";
		result = "<timestamp-field" + attrValue + attrReadonly + attrTagValue + " />";
		return result;
	}
	
	public String boolField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);

		result = "<bool-field value=\"$" + lName + "\" />";
		return result;
	}
	
	public String boolField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
//		String attrValue = " checked=\"@bind(" + prefixValue + lName + ")\"";
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
//		String attrReadonly = (checkReadonly ? " disabled=\"@bind(" + parentId + ".readonly)\"" : "");
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
//		result = "<checkbox" + attrValue + attrReadonly + " />";

		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";
		result = "<bool-field" + attrValue + attrReadonly + attrTagValue + " />";
		return result;
	}

	public String numberField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		result = "<number-field value=\"$" + lName + "\" />";
		return result;
	}
	
	
	public String numberField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@bind(" + parentId + ".readonly)\"" : "");
//		result = "<decimalbox" + attrValue + attrReadonly + " />";

		String attrTagValue = " label=\"${i18n." + field.getOwner().getNameWithoutPackage() + "_" + lName + "_label}\"";
		result = "<number-field" + attrValue + attrReadonly + attrTagValue + " />";
		return result;
	}

	
	public String enumField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
//    	result += "<ref-field values="$listado" value="$referencia" />
   	  	result = "<enum-field value=\"$" + lName + "\" />";
        
		return result;
	}
	
	public String enumField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String vmFieldName = "f" + StringUtils.capFirst(pName);
		
//		String idListValues = itemId + StringUtils.capFirst(pName) + "Values";
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " selectedItem=\"@bind(" + prefixValue + lName + ")\"";
//		String listValues = " model=\"@load(" + parentId + "." + idListValues + ")\"";
		String listValues = " model=\"@load(" + vmFieldName + "." + "values" + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@load(" + parentId + ".readonly)\"" : "");
		String attrButtonVisible = (checkReadonly ? " buttonVisible=\"@load(" + parentId + ".readonly eq false)\"" : "");
		String attrDisabled = (checkReadonly ? " disabled=\"@load(" + parentId + ".readonly)\"" : "");
		
		String attrApply = " apply=\"org.zkoss.bind.BindComposer\"";
		String enumClass = field.getType().getName();//"org.effortless.icondb.icons.IconStatus";
		String attrViewModel = " viewModel=\"@id('" + vmFieldName + "') @init('org.effortless.ui.vm.fields.EnumFieldVM', enumClass='" + enumClass + "')\"";
		String attrLoadOnDemand = " fulfill=\"onOpen\"";
		String attrAutocomplete = " autocomplete=\"true\"";
        result = "<combobox" + attrApply + attrViewModel + attrLoadOnDemand + attrAutocomplete + listValues + attrValue + attrReadonly + attrButtonVisible + attrDisabled + ">";
        result += "  <template name=\"model\">";
//        result += "    <comboitem label=\"@load(each)\" image="@load(vm.getIconImage(each))" />
        result += "    <comboitem label=\"@load(each)\" />";
        result += "  </template>";
        result += "</combobox>";
		
		return result;
	}

	public String fileField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
		if (checkPhoto(field)) {
			result = "<photo-field value=\"$" + lName + "\" />";
		}
		else {
			result = "<file-field value=\"$" + lName + "\" />";
		}
//		<file-field name="$etiqueta" content="$fichero" />
		
		
//		result = "<button label=\"" + attrLabel + "\" upload=\"" + attrUpload + "\"" + attrApply + attrViewModel + " onUpload=\"@command((empty " + vmFieldName + ".value ? 'upload' : null), upEvent=event)\" onClick=\"@command((empty " + vmFieldName + ".value ? null : 'download'))\"" + "/>";
		
		return result;
	}
	
	protected boolean checkPhoto (FieldNode field) {
		boolean result = false;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		lName = (lName != null ? lName.toLowerCase() : "");
		result = (!result && "photo".contains(lName) ? true : result);
		result = (!result && "image".contains(lName) ? true : result);
		result = (!result && "foto".contains(lName) ? true : result);
		result = (!result && "icon".contains(lName) ? true : result);
		return result;
	}
	
	
	
	public String fileField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String vmFieldName = "f" + StringUtils.capFirst(pName);
		
//		String idListValues = itemId + StringUtils.capFirst(pName) + "Values";
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String property = prefixValue + lName;
		String attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
//		String listValues = " model=\"@load(" + parentId + "." + idListValues + ")\"";
		String listValues = " model=\"@load(" + vmFieldName + "." + "values" + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@load(" + parentId + ".readonly)\"" : "");
		String attrButtonVisible = (checkReadonly ? " buttonVisible=\"@load(" + parentId + ".readonly eq false)\"" : "");
		String attrDisabled = (checkReadonly ? " disabled=\"@load(" + parentId + ".readonly)\"" : "");

		
		String prefixValue2 = parentId + (itemId.length() > 0 ? "." + itemId : itemId);
		
		String bindValue = ", item=" + prefixValue2 + ", property='" + lName + "'";
		
		String attrApply = " apply=\"org.zkoss.bind.BindComposer\"";
		String type = field.getType().getName();//"org.effortless.icondb.icons.IconStatus";
		String attrViewModel = " viewModel=\"@id('" + vmFieldName + "') @init('org.effortless.ui.vm.fields.FileFieldVM', type='" + type + "'" + bindValue + ")\"";
		String attrLoadOnDemand = " fulfill=\"onOpen\"";
		String attrAutocomplete = " autocomplete=\"true\"";
		
		String attrLabel = "@load(" + vmFieldName + ".fileName)";
		String attrUpload = "@load(" + vmFieldName + ".upload)";
		
		result = "<file-field" + attrValue + " type=\"" + type + "\" />";

//		result = "<button label=\"" + attrLabel + "\" upload=\"" + attrUpload + "\"" + attrApply + attrViewModel + " onUpload=\"@command((empty " + vmFieldName + ".value ? 'upload' : null), upEvent=event)\" onClick=\"@command((empty " + vmFieldName + ".value ? null : 'download'))\"" + "/>";
		
		return result;
	}
	
	public String listField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		
		result = "<list-field value=\"$" + lName + "\" />";
//		<file-field name="$etiqueta" content="$fichero" />
		
//		result = "<button label=\"" + attrLabel + "\" upload=\"" + attrUpload + "\"" + attrApply + attrViewModel + " onUpload=\"@command((empty " + vmFieldName + ".value ? 'upload' : null), upEvent=event)\" onClick=\"@command((empty " + vmFieldName + ".value ? null : 'download'))\"" + "/>";
		
		return result;
	}
	
	public String listField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		
		
//		<hlayout><label value="${i18n.IconTag_size_finderMessage}" /><label value="@load(item.numElements)" /></hlayout>
//		<listbox model="@bind(item.list)" emptyMessage="${i18n.IconTag_noItems_finderMessage}" selectedItem="@bind(item.selectedItem)" style="margin-top:10px" rows="@load(item.listSize)">
//			<listhead sizable="true">
//				<listheader label="${i18n.IconTag_name_finderColumnLabel}" />
//				<listheader label="${i18n.IconTag_comentario_finderColumnLabel}" />
//				<listheader label="${i18n.IconTag_hora_finderColumnLabel}" />
//				<listheader label="${i18n.IconTag_fechaHora_finderColumnLabel}" />
//				<listheader label="${i18n.IconTag_fecha_finderColumnLabel}" />
//					<listheader label=" " width="20px" />
//			</listhead>
//			<template name="model">
//				<listitem onMouseOver="@command('changeOver', item=each)">
//					<listcell label="@bind(each.name)" />
//					<listcell label="@bind(each.comentario)" />
//					<listcell label="@bind(each.hora)" />
//					<listcell label="@bind(each.fechaHora)" />
//					<listcell label="@bind(each.fecha)" />
//						<listcell><div sclass="icon-info" tooltip="moreInfo"></div></listcell>
//				</listitem>
//			</template>
//		</listbox>
//	<popup id="moreInfo" width="35%">
//		<grid height="100%" width="100%">
//			<columns>
//				<column width="25%" />
//				<column width="75%" />
//			</columns>
//			<rows>
//				<row>
//					<label value="${i18n.IconTag_name_label}"/>
//					<label value="@load(item.selectedOver.name)" />
//				</row>
//				<row>
//					<label value="${i18n.IconTag_comentario_label}"/>
//					<label value="@load(item.selectedOver.comentario)" />
//				</row>
//				<row>
//					<label value="${i18n.IconTag_hora_label}"/>
//					<label value="@load(item.selectedOver.hora)" />
//				</row>
//				<row>
//					<label value="${i18n.IconTag_fechaHora_label}"/>
//					<label value="@load(item.selectedOver.fechaHora)" />
//				</row>
//				<row>
//					<label value="${i18n.IconTag_fecha_label}"/>
//					<label value="@load(item.selectedOver.fecha)" />
//				</row>
//			</rows>
//		</grid>
//	</popup>
//		<hlayout sclass="z-hlayout-right" visible="@load(item.pagination)">
//			<a image="${images}/previousPage.png" onClick="@command('previousPage')" />
//			<label value="@load(item.pageIndexStr)" />
//			<label value="${i18n.IconTag_sepPage_finderMessage}" />
//			<label value="@load(item.totalPages)" />
//			<a image="${images}/nextPage.png" onClick="@command('nextPage')" />
//		</hlayout>		
		
		return null;
	}
	
	public String refField (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		result = "<ref-field value=\"$" + lName + "\" />";
		
		return result;
	}
	
	public String refField_old (FieldNode field, String parentId, String itemId, boolean checkReadonly) {
		String result = null;
		String pName = field.getName();
		String lName = StringUtils.uncapFirst(pName);
		itemId = (itemId != null ? itemId.trim() : "");
		String vmFieldName = "f" + StringUtils.capFirst(pName);
		
//		String idListValues = itemId + StringUtils.capFirst(pName) + "Values";
		String prefixValue = parentId + (itemId.length() > 0 ? "." + itemId + "." : itemId);
		String attrValue = " selectedItem=\"@bind(" + prefixValue + lName + ")\"";
//		String listValues = " model=\"@load(" + parentId + "." + idListValues + ")\"";
		String listValues = " model=\"@load(" + vmFieldName + "." + "values" + ")\"";
		String attrReadonly = (checkReadonly ? " readonly=\"@load(" + parentId + ".readonly)\"" : "");
		String attrButtonVisible = (checkReadonly ? " buttonVisible=\"@load(" + parentId + ".readonly eq false)\"" : "");
		String attrDisabled = (checkReadonly ? " disabled=\"@load(" + parentId + ".readonly)\"" : "");
		
		String attrApply = " apply=\"org.zkoss.bind.BindComposer\"";
		String type = field.getType().getName();//"org.effortless.icondb.icons.IconStatus";
		String attrViewModel = " viewModel=\"@id('" + vmFieldName + "') @init('org.effortless.ui.vm.fields.RefFieldVM', type='" + type + "')\"";
		String attrLoadOnDemand = " fulfill=\"onOpen\"";
		String attrAutocomplete = " autocomplete=\"true\"";
		
		
		if (true) {
			attrValue = " value=\"@bind(" + prefixValue + lName + ")\"";
			result = "<ref-field type=\"" + type + "\"" + attrValue + " />";
		}
		else {
	        result = "<combobox" + attrApply + attrViewModel + attrLoadOnDemand + attrAutocomplete + listValues + attrValue + attrReadonly + attrButtonVisible + attrDisabled + ">";
	        result += "  <template name=\"model\">";
	//        result += "    <comboitem label=\"@load(each)\" image="@load(vm.getIconImage(each))" />
	//        result += "    <comboitem label=\"@load(each.name)\" />";
	        result += "    <comboitem label=\"@load(each)\" />";
	        result += "  </template>";
	        result += "    <comboitem label=\"...\" onClick=\"@command('selectMore')\" />";
	        result += "</combobox>";
		}
		
		return result;
	}
	
}
