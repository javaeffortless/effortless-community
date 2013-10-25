package org.effortless.gen.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.CharSet;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
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
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
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
import org.effortless.ann.Finder;
import org.effortless.core.Collections;
import org.effortless.core.ModelException;
import org.effortless.core.StringUtils;
import org.effortless.gen.GClass;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.InfoClassNode;
import org.effortless.gen.GMethod;
import org.effortless.gen.ViewClassGen;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.gen.methods.ActionsTransform;
import org.effortless.model.AbstractEntity;
import org.effortless.model.SessionManager;
import org.effortless.server.ServerContext;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.resources.ImageResources;
import org.effortless.ui.vm.FinderFilterVM;
import org.effortless.ui.vm.FinderVM;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements

@org.effortless.NoEntity
public class BasicFinderViewModel {
	
	private String keyword;

	public void setKeyword(String newValue) {
		this.keyword = newValue;
	}
	public String getKeyword() {
		return this.keyword;
	}

	private java.util.List<Basic> list;

	public java.util.List<Basic> getList(){
		return this.list;
	}
	
	private Basic selectedItem;
		
	public void setSelectedItem(Basic newValue) {
		this.selectedItem = newValue;
	}
	public Basic getSelectedItem() {
		return this.selectedItem;
	}

	
	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange("list")
	public void search(){
System.out.println("SEARCH")
		this.list = Basic.listBy().lk("name", this.keyword);
	}

}
	
	
 * 
 * 
 * @author jesus
 *
 */

public class FinderVMTransform extends Object implements ClassTransform {

	public static final ClassNode HASH_CODE_BUILDER = new ClassNode(org.apache.commons.lang3.builder.HashCodeBuilder.class);
	
	public static String getFinderName (ClassNode clazz, SourceUnit sourceUnit) {
		String result = null;
		result = clazz.getName() + "FinderViewModel";
		return result;
	}
	
	public void process (GClass cg) {
		ClassNode clazz = cg.getClassNode();
		SourceUnit sourceUnit = cg.getSourceUnit();
		if (clazz != null && InfoClassNode.checkEntityValid(clazz, sourceUnit) && !InfoClassNode.checkEnum(clazz, sourceUnit)) {
			
			if (true) {
				ViewClassGen cg = new ViewClassGen(clazz, sourceUnit);

				ViewClassGen finderFilter = cg.addFinderFilter();
if (finderFilter != null) {
				ViewClassGen finderFilterVM = null;//finderFilter.addFinderFilterVM(finderFilter);
				writeFinderFilterZul(finderFilter, finderFilterVM, clazz, sourceUnit);

//				ViewClassGen moreInfoVM = cg.addMoreInfoVM();
//				writeMoreInfoZul_old(moreInfoVM, cg, sourceUnit);
				writeMoreInfoZul(null, cg, sourceUnit);
				
				
				GClass vm = null;//cg.addFinderVM(finderFilter.getClassNode());
				writeZul((vm != null ? vm.getClassNode() : null), clazz, sourceUnit);
}
			}
			else {
				ClassNode vm = new ClassNode(getFinderName(clazz, sourceUnit), Opcodes.ACC_PUBLIC, ClassHelper.OBJECT_TYPE);
				sourceUnit.getAST().addClass(vm);
					
				addFilterProperty(vm, clazz, sourceUnit);
				addListProperty(vm, clazz, sourceUnit);
				addSelectionProperty(vm, clazz, sourceUnit);
				addSearchButton(vm, clazz, sourceUnit);

				writeZul(vm, clazz, sourceUnit);
			}
		}
	}
	
	/*


<info-window>
  <label-field value="$nombre" />
</info-window>


	 * 
	 */
	public static void writeMoreInfoZul(ViewClassGen vm, ViewClassGen clazz, SourceUnit sourceUnit) {
		String className = clazz.getClassNode().getNameWithoutPackage();
		
		List<FieldNode> properties = clazz.getFinderProperties();
		
		List<String> zul = new ArrayList<String>();
		zul.add("<zk>");
		zul.add("  <info-window>");
		int size = 0;
		for (FieldNode property : properties) {
			size += 1;
			String pName = property.getName();
			String lName = StringUtils.uncapFirst(pName);
			String widgetField = "<label-field value=\"$" + lName + "\" />";
			zul.add("    " + widgetField + "");
			if (size > 0) {
				break;
			}
		}
		zul.add("  </info-window>");
		zul.add("</zk>");

		String zulName = className.trim() + CteUi.SUFFIX_INFO_ZUL + CteUi.EXT_ZUL;
		String folder = clazz.getClassNode().getPackageName();
		if (!GClass.ONE_PACKAGE) {
			int idx = folder.lastIndexOf(".");
			folder = (idx < 0 ? folder : folder.substring(0, idx));
		}
		String filename = ServerContext.getRootContext() + folder + "/resources/" + zulName;
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}
	

	public static void writeMoreInfoZul_old(ViewClassGen vm, ViewClassGen clazz, SourceUnit sourceUnit) {
		String vmName = vm.getClassNode().getName();
		
		String className = clazz.getClassNode().getNameWithoutPackage();
		
		List<FieldNode> properties = clazz.getFinderProperties();
		String varId = "vmmi.item";
		
		List<String> zul = new ArrayList<String>();
		zul.add("<div width=\"100%\" height=\"100%\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('" + varId + "') @init('" + vmName + "')\">");
		writeMoreInfoZul(zul, varId, className, properties);
//
//		zul.add("	<grid>");
//		zul.add("		<columns>");
//		zul.add("			<column hflex=\"1\" />");
//		zul.add("			<column hflex=\"5\" />");
//		zul.add("		</columns>");
//		zul.add("		<rows>");
//		for (FieldNode property : properties) {
//			String pName = property.getName();
//			String lName = StringUtils.uncapFirst(pName);
////			String label = StringUtils.capFirst(pName);
//			String widgetField = "<label value=\"@load(" + varId + ".item." + lName + ")\" />";
////			zul.add("			<row><label value=\"${i18n." + className + "_" + lName + "_label}\"/>" + widgetField + "</row>");
//			zul.add("			<row>");
//			zul.add("				<label value=\"${i18n." + className + "_" + lName + "_label}\"/>");
//			zul.add("				" + widgetField + "");
//			zul.add("			</row>");
//		}
//		zul.add("		</rows>");
//		zul.add("	</grid>");
//
		zul.add("</div>");

		String zulName = className.trim() + "moreinfo.zul";
		String folder = clazz.getClassNode().getPackageName();
		if (!GClass.ONE_PACKAGE) {
			int idx = folder.lastIndexOf(".");
			folder = (idx < 0 ? folder : folder.substring(0, idx));
		}
		String filename = ServerContext.getRootContext() + folder + "/resources/" + zulName;
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
		
		
	}

	public static void writeMoreInfoZul(List<String> zul, String varId, String className, List<FieldNode> properties) {
		if (false) {
		zul.add("	<grid height=\"100%\" width=\"100%\">");
		zul.add("		<columns>");
		zul.add("			<column width=\"25%\" />");
		zul.add("			<column width=\"75%\" />");
		zul.add("		</columns>");
		zul.add("		<rows>");
		for (FieldNode property : properties) {
			String pName = property.getName();
			String lName = StringUtils.uncapFirst(pName);
//			String label = StringUtils.capFirst(pName);
			String widgetField = "<label value=\"@load(" + varId + "." + lName + ")\" />";
//			zul.add("			<row><label value=\"${i18n." + className + "_" + lName + "_label}\"/>" + widgetField + "</row>");
			zul.add("			<row>");
			zul.add("				<label value=\"${i18n." + className + "_" + lName + "_label}\"/>");
			zul.add("				" + widgetField + "");
			zul.add("			</row>");
		}
		zul.add("		</rows>");
		zul.add("	</grid>");
		}
		else {
			zul.add("	<info-window>");
			for (FieldNode property : properties) {
				String pName = property.getName();
				String lName = StringUtils.uncapFirst(pName);
//				String label = StringUtils.capFirst(pName);
				String widgetField = "<label-field value=\"@load(" + varId + "." + lName + ")\" label=\"${i18n." + className + "_" + lName + "_label}\" />";
				zul.add("				" + widgetField + "");
			}
			zul.add("	</info-window>");
		}
	}
	

	public static void writeFinderFilterZul(ViewClassGen _filter, ViewClassGen vm, ClassNode clazz,
			SourceUnit sourceUnit) {
		ClassNode filter = _filter.getClassNode();
//		String vmName = (vm != null ? vm.getClassNode().getName() : null);
//		vmName = (vmName != null ? vmName : _filter.getClassNode().getName() + "VM");
		String vmName = FinderFilterVM.class.getName();
		
		FieldTransform fieldTransform = new FieldTransform();
		
		String className = filter.getNameWithoutPackage();
		
		List<FieldNode> properties = _filter.getFinderProperties();
		String varId = "vmf";
		
		List<String> zul = new ArrayList<String>();
		zul.add("<zk>");
		zul.add("  <embedded-filter-window>");
		for (FieldNode property : properties) {
			String widgetField = fieldTransform.writeZul(property, null, null, false);
			zul.add("    " + widgetField + "");
		}
		zul.add("  </embedded-filter-window>");
		zul.add("</zk>");

		String zulName = className.trim() + ".zul";
		String folder = clazz.getPackageName();
		if (!GClass.ONE_PACKAGE) {
			int idx = folder.lastIndexOf(".");
			folder = (idx < 0 ? folder : folder.substring(0, idx));
		}
		String filename = ServerContext.getRootContext() + folder + "/resources/" + zulName;
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
		
		
	}

	
	
	public static void writeFinderFilterZul_old(ViewClassGen _filter, ViewClassGen vm, ClassNode clazz,
			SourceUnit sourceUnit) {
		ClassNode filter = _filter.getClassNode();
//		String vmName = (vm != null ? vm.getClassNode().getName() : null);
//		vmName = (vmName != null ? vmName : _filter.getClassNode().getName() + "VM");
		String vmName = FinderFilterVM.class.getName();
		
		FieldTransform fieldTransform = new FieldTransform();
		
		String className = filter.getNameWithoutPackage();
		
		List<FieldNode> properties = _filter.getFinderProperties();
		String varId = "vmf";
		
		List<String> zul = new ArrayList<String>();
		zul.add("<div height=\"100%\" width=\"100%\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('" + varId + "') @init('" + vmName + "')\">");

		if (true) {
			zul.add("	<layout-grid size=\"2\">");

			for (FieldNode property : properties) {
				String pName = property.getName();
				String lName = StringUtils.uncapFirst(pName);
//				String label = StringUtils.capFirst(pName);
//				String widgetField = "<textbox value=\"@bind(" + varId + ".filterProperties." + lName + ")\" />";
				String widgetField = fieldTransform.writeZul(property, varId, "filterProperties", false);
//				zul.add("			<row><label value=\"${i18n." + className + "_" + lName + "_label}\"/>" + widgetField + "</row>");
				if (false) {
					zul.add("		<tag value=\"${i18n." + className + "_" + lName + "_label}\"/>");
				}
				zul.add("		" + widgetField + "");
			}
			zul.add("	</layout-grid>");
		}
		else {
			zul.add("	<grid>");
			zul.add("		<columns>");
			zul.add("			<column width=\"20%\" />");
			zul.add("			<column width=\"80%\" />");
			zul.add("		</columns>");
			zul.add("		<rows>");
			for (FieldNode property : properties) {
				String pName = property.getName();
				String lName = StringUtils.uncapFirst(pName);
//				String label = StringUtils.capFirst(pName);
//				String widgetField = "<textbox value=\"@bind(" + varId + ".filterProperties." + lName + ")\" />";
				String widgetField = fieldTransform.writeZul(property, varId, "filterProperties", false);
//				zul.add("			<row><label value=\"${i18n." + className + "_" + lName + "_label}\"/>" + widgetField + "</row>");
				zul.add("			<row>");
if (false) {
				zul.add("				<tag value=\"${i18n." + className + "_" + lName + "_label}\"/>");
}
				zul.add("				" + widgetField + "");
				zul.add("			</row>");
			}
			zul.add("		</rows>");
			zul.add("	</grid>");
		}

		zul.add("</div>");

		String zulName = className.trim() + ".zul";
		String folder = clazz.getPackageName();
		if (!GClass.ONE_PACKAGE) {
			int idx = folder.lastIndexOf(".");
			folder = (idx < 0 ? folder : folder.substring(0, idx));
		}
		String filename = ServerContext.getRootContext() + folder + "/resources/" + zulName;
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
		
		
	}

	/**
	 * 
	private String keyword;

	public void setKeyword(String newValue) {
		this.keyword = newValue;
	}
	public String getKeyword() {
		return this.keyword;
	}
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addFilterProperty(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit) {
		addSimpleProperty(vm, clazz, sourceUnit, "keyword", ClassHelper.STRING_TYPE, false, false);
	}

	/**
	 * 
	 * 
	private java.util.List<Basic> list;

	public java.util.List<Basic> getList(){
		return this.list;
	}
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addListProperty(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit) {
		addSimpleProperty(vm, clazz, sourceUnit, "list", LIST_CLAZZ, true, true);
	}

	public static final ClassNode LIST_CLAZZ = new ClassNode(java.util.List.class);
	
	/**
	 * 
	 * 
	private Basic selectedItem;
		
	public void setSelectedItem(Basic newValue) {
		this.selectedItem = newValue;
	}
	public Basic getSelectedItem() {
		return this.selectedItem;
	}
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addSelectionProperty(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit) {
		addSimpleProperty(vm, clazz, sourceUnit, "selectedItem", clazz, false, false);
	}

	/**
	 * 
	 * 
	@org.zkoss.bind.annotation.Command
	@org.zkoss.bind.annotation.NotifyChange("list")
	public void search(){
System.out.println("SEARCH")
		this.list = Basic.listBy().lk("name", this.keyword);
	}
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addSearchButton(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit) {
//		BlockStatement code = new BlockStatement();
		String op = "lk";
		List<AnnotationNode> annotations = clazz.getAnnotations(new ClassNode(Finder.class));
		if (annotations != null && annotations.size() > 0) {
			AnnotationNode annFinder = annotations.get(0);
			op = annFinder.getMember("op").getText();
		}
		
		FieldNode fieldKeyword = vm.getField("keyword");
		StaticMethodCallExpression callListBy = new StaticMethodCallExpression(clazz, "listBy", ArgumentListExpression.EMPTY_ARGUMENTS);
		ArgumentListExpression listExpr = new ArgumentListExpression(new Expression[] {new ConstantExpression("name"), new FieldExpression(fieldKeyword)});
		MethodCallExpression callLk = new MethodCallExpression(callListBy, op, listExpr);
		FieldNode fieldList = vm.getField("list");
		BinaryExpression assignList = new BinaryExpression(new FieldExpression(fieldList), Token.newSymbol(Types.ASSIGN, -1, -1), callLk);
		ExpressionStatement code = new ExpressionStatement(assignList);
		
		MethodNode method = new MethodNode("search", Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		vm.addMethod(method);

		//@org.zkoss.bind.annotation.Command
		AnnotationNode annCommand = new AnnotationNode(new ClassNode(org.zkoss.bind.annotation.Command.class));
		method.addAnnotation(annCommand);

		//@org.zkoss.bind.annotation.NotifyChange("list")
		AnnotationNode annNotifyChange = new AnnotationNode(new ClassNode(org.zkoss.bind.annotation.NotifyChange.class));
		annNotifyChange.setMember("value", new ConstantExpression("list"));
		method.addAnnotation(annNotifyChange);
	}

	/**
	 * 
	 * 
<window title="Search" width="600px" border="normal" apply="org.zkoss.bind.BindComposer" viewModel="@id('vm') @init('org.effortless.samples.basic.SearchViewModel')">
	<hbox align="center">
		Keyword:
		<textbox value="@bind(vm.keyword)" />
		<button label="Search" image="/org/effortless/samples/img/search.png" onClick="@command('search')" />
	</hbox>
	<listbox height="160px" model="@bind(vm.list)" emptyMessage="No item found in the result"
	selectedItem="@bind(vm.selectedItem)" style="margin-top:10px">
		<listhead>
			<listheader label="Name" />
			<listheader label="Description" />
		</listhead>
		<template name="model">
			<listitem>
				<listcell label="@bind(each.name)"></listcell>
				<listcell label="@bind(each.description)"></listcell>
			</listitem>
		</template>
	</listbox>
	<hbox style="margin-top:20px" visible="@bind(not empty vm.selectedItem)">
		<image src="@bind(vm.selectedItem.ficheroPath)" style="padding:10px" />
		<vbox>
			<hlayout>
				Name : <label value="@bind(vm.selectedItem.name)" style="font-weight:bold"/>
			</hlayout>
			<label value="@bind(vm.selectedItem.description)" />
		</vbox>
	</hbox>
</window>
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void writeZul_old(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit) {
		// TODO Auto-generated method stub
		ViewClassGen cg = new ViewClassGen(clazz, sourceUnit);
		
//		String finderName = cg.getFinderName();//getFinderName(clazz, sourceUnit);
		String finderName = FinderVM.class.getName();
		
		String defaultFilterClass = ", filterClass='" + clazz.getName() + "FinderFilter" + "'";
		String defaultFilterSrc = ", filterSrc='" + "" + clazz.getNameWithoutPackage().toLowerCase() + "finderfilter" + "'";
		String defaultEditorSrc = ", editorSrc='" + "" + clazz.getNameWithoutPackage().toLowerCase() + "_editor" + "'";
		String defaultItemClass = ", itemClass='" + clazz.getName() + "" + "'";
		String defaultValues = defaultFilterClass + defaultFilterSrc + defaultEditorSrc + defaultItemClass;
		
		String appId = SessionManager.getDbId(clazz.getName());
		
		String className = clazz.getNameWithoutPackage();
//		String title = "Search " + className;
//		title = "${i18n.title}";
//		String emptyMessage = "No " + className + " found in the result ";
		
		List<FieldNode> properties = cg.getFinderProperties();
		String varId = "item";
		
//		String zul = "";
		List<String> zul = new ArrayList<String>();
		
		zul.add("<?taglib uri=\"http://www.zkoss.org/dsp/web/core\" prefix=\"c\"?>");
		zul.add("<window title=\"${i18n." + className + "_finderTitle}\" width=\"100%\" height=\"100%\" contentStyle=\"overflow:auto\" border=\"normal\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('" + varId + "') @init('" + finderName + "'" + defaultValues + ")\">");
		
		ImageResources.copy(appId, "info", "info.png", 16);
		ImageResources.copy(appId, "deleteConfirm", "deleteConfirm.png", 48);
		ImageResources.copy(appId, "infoConfirm", "infoConfirm.png", 48);
if (false) {
		zul.add("	<style>");
		zul.add(".icon-info {"); 
		zul.add("	background-image : url(" + appId + "/resources/info.png);");
//		zul.add("	background-image : url(${resources}/info.png);");
		zul.add("	width: 16px; height: 16px;");
		zul.add("}");
		zul.add("	</style>");
}
		
		ImageResources.copy(appId, "search", "search.png", 16);
		zul.add("	<div>");
		zul.add("	<include filter=\"@load(" + varId + ".filter)\" src=\"@load(" + varId + ".filterSrc)\" />");
//		zul.add("	<include src=\"@load(" + varId + ".filterSrc)\" />");
		zul.add("	</div>");
//		zul.add("	<hbox align=\"center\">");
//		zul.add("Keyword:");
//		zul.add("		<textbox value=\"@bind(" + varId + "." + "keyword" + ")\" />");
//		zul.add("		<button label=\"${i18n." + className + "_search_finderButtonLabel}\" image=\"${images}/search.png\" onClick=\"@command('search')\" />");
//		zul.add("	</hbox>");
		
				
		zul.add("		<hlayout sclass=\"z-hlayout-right\"><button label=\"${i18n." + className + "_search_finderButtonLabel}\" image=\"${images}/search.png\" onClick=\"@command('search')\" /></hlayout>");

		zul.add("	<hlayout><label value=\"${i18n." + className + "_size_finderMessage}\" /><label value=\"@load(" + varId + ".numElements)\" /></hlayout>");

		zul.add("	<listbox model=\"@bind(" + varId + ".list)\" emptyMessage=\"${i18n." + className + "_noItems_finderMessage}\" selectedItem=\"@bind(" + varId + ".selectedItem)\" style=\"margin-top:10px\" rows=\"@load(" + varId + ".listSize)\">");
		zul.add("		<listhead sizable=\"true\">");
//		zul.add("<listheader label=\"Name2\" />");
//		zul.add("<listheader label=\"Description2\" />");
		for (FieldNode property : properties) {
			String pName = property.getName();
//			String label = StringUtils.capFirst(pName);
			zul.add("			<listheader label=\"${i18n." + className + "_" + pName + "_finderColumnLabel}\" />");
		}
		zul.add("				<listheader label=\" \" width=\"20px\" />");
		zul.add("		</listhead>");
		zul.add("		<template name=\"model\">");
		zul.add("			<listitem onMouseOver=\"@command('changeOver', item=each)\">");
//		zul.add("<listcell label=\"@bind(each." + "name" + ")\"></listcell>");
//		zul.add("<listcell label=\"@bind(each." + "description" + ")\"></listcell>");
		for (FieldNode property : properties) {
			String pName = property.getName();
			zul.add("				<listcell label=\"@bind(each." + pName + ")\" />");
		}

		if (false) {
			zul.add("					<listcell><div sclass=\"icon-info\" tooltip=\"moreInfo\"></div></listcell>");
		}
		else {
			zul.add("					<listcell><icon-field value=\"${each}\">");
			String varIdInfo = "each";
			for (FieldNode property : properties) {
				String pName = property.getName();
				String lName = StringUtils.uncapFirst(pName);
//				String label = StringUtils.capFirst(pName);
				String widgetField = "<label-field value=\"@load(" + varIdInfo + "." + lName + ")\" label=\"${i18n." + className + "_" + lName + "_label}\" />";
				zul.add("				" + widgetField + "");
			}
			zul.add("					</icon-field></listcell>");
		}
		
		
		
		zul.add("			</listitem>");
		zul.add("		</template>");
		zul.add("	</listbox>");

//		zul.add("<popup id=\"moreInfo\" width=\"35%\"><include item=\"@load(" + varId + ".selectedOver)\" src=\"${resources}/" + className.trim().toLowerCase() + "moreinfo.zul\" /></popup>");
		if (false) {
		zul.add("<popup id=\"moreInfo\" width=\"35%\">");
		String varIdInfo = varId + ".selectedOver";
		writeMoreInfoZul(zul, varIdInfo, className, properties);
		zul.add("</popup>");
		}
		
		ImageResources.copy(appId, "previousPage", "previousPage.png", 16);
		ImageResources.copy(appId, "nextPage", "nextPage.png", 16);
		zul.add("	<hlayout sclass=\"z-hlayout-right\" visible=\"@load(" + varId + ".pagination)\">");
		zul.add("		<a image=\"${images}/previousPage.png\" onClick=\"@command('previousPage')\" />");
		zul.add("		<label value=\"@load(" + varId + ".pageIndexStr)\" />");
		zul.add("		<label value=\"${i18n." + className + "_sepPage_finderMessage}\" />");
		zul.add("		<label value=\"@load(" + varId + ".totalPages)\" />");
		zul.add("		<a image=\"${images}/nextPage.png\" onClick=\"@command('nextPage')\" />");
		zul.add("	</hlayout>");
		
if (false) {
		zul.add("	<hbox style=\"margin-top:20px\" visible=\"@bind(not empty " + varId + ".selectedItem)\">");
		zul.add("		<image src=\"@bind(" + varId + ".selectedItem.ficheroPath)\" style=\"padding:10px\" />");
		zul.add("		<vbox>");
//		zul.add("<hlayout>");
//		zul.add("Name" + " : <label value=\"@bind(vm.selectedItem." + "name" + ")\" style=\"font-weight:bold\"/>");
//		zul.add("</hlayout>");
//		zul.add("<label value=\"@bind(vm.selectedItem." + "description" + ")\" />");
		
		int index = 0;
		for (FieldNode property : properties) {
			String pName = property.getName();
//			String label = StringUtils.capFirst(pName);
			if (index == 0) {
				zul.add("			<hlayout>");
				zul.add("				${i18n." + className + "_" + pName + "_label}" + ": <label value=\"@bind(" + varId + ".selectedItem." + pName + ")\" style=\"font-weight:bold\"/>");
				zul.add("			</hlayout>");
			}
			else {
				zul.add("			<label value=\"@bind(" + varId + ".selectedItem." + pName + ")\" />");
			}
			index += 1;
		}
		
		zul.add("		</vbox>");
		zul.add("	</hbox>");
}
		zul.add("	<hlayout sclass=\"z-hlayout-right\">");

		ImageResources.copy(appId, "clone", "clone" + ".png", 16);
		zul.add("		<button onClick=\"@command('run_" + "clone" + "_OnItem" + "')\" label=\"${i18n." + className + "_" + "clone" + "_finderButtonLabel}\" image=\"${images}/" + "clone" + ".png\" />");
		
		List<MethodNode> actions = cg.getPublicActions();
		if (actions != null) {
			for (MethodNode action : actions) {
				String actionName = action.getName();
//				String runAction = "run_" + actionName + "_OnItem";
				String runAction = "runCustomActionItem";
////				String runAction = actionName;
				ImageResources.copy(appId, actionName, actionName + ".png", 16);
//				zul.add("		<button onClick=\"@command('" + runAction + "')\" label=\"${i18n." + className + "_" + actionName + "_finderButtonLabel}\" image=\"${images}/" + actionName + ".png\" />");
				zul.add("		<button onClick=\"@command('" + runAction + "', action='" + actionName + "')\" label=\"${i18n." + className + "_" + actionName + "_finderButtonLabel}\" image=\"${images}/" + actionName + ".png\" />");
			}
		}
		
		ImageResources.copy(appId, "create", "create.png", 16);
		ImageResources.copy(appId, "read", "read.png", 16);
		ImageResources.copy(appId, "update", "update.png", 16);
		ImageResources.copy(appId, "delete", "delete.png", 16);
		zul.add("		<button onClick=\"@command('createItem')\" label=\"${i18n." + className + "_create_finderButtonLabel}\" image=\"${images}/create.png\" />");
		zul.add("		<button onClick=\"@command('readItem')\" label=\"${i18n." + className + "_read_finderButtonLabel}\" image=\"${images}/read.png\" />");
		zul.add("		<button onClick=\"@command('updateItem')\" label=\"${i18n." + className + "_update_finderButtonLabel}\" image=\"${images}/update.png\" />");
		zul.add("		<button onClick=\"@command('deleteItem')\" label=\"${i18n." + className + "_delete_finderButtonLabel}\" image=\"${images}/delete.png\" />");
		zul.add("	</hlayout>");
	
		zul.add("</window>");

		String zulName = clazz.getNameWithoutPackage().trim() + "_finder.zul";
		String folder = clazz.getPackageName();
		if (!GClass.ONE_PACKAGE) {
			int idx = folder.lastIndexOf(".");
			folder = (idx < 0 ? folder : folder.substring(0, idx));
		}
		String filename = ServerContext.getRootContext() + folder + "/resources/" + zulName;
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}

	

	
	
	
	/**
	 * 
	 * 
<zk>
<finder-window>
	<template name="content">
		<date-field value="$fecha" />
		<text-field value="$nombre" />
		<bool-field value="$activo" />
	</template>	
</finder-window>
</zk>
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void writeZul(ClassNode vm, ClassNode clazz, SourceUnit sourceUnit) {
		// TODO Auto-generated method stub
		ViewClassGen cg = new ViewClassGen(clazz, sourceUnit);
		
		String propertyNames = null;
		String inlineActions = null;
		String listActions = null;
		List<AnnotationNode> annotations = clazz.getAnnotations(new ClassNode(Finder.class));
		if (annotations != null && annotations.size() > 0) {
			AnnotationNode annFinder = annotations.get(0);
			Expression expr = annFinder.getMember("properties");
			String value = expr.getText();
			propertyNames = value;
			
			expr = annFinder.getMember("inlineActions");
			inlineActions = (expr != null ? expr.getText() : null);

			expr = annFinder.getMember("listActions");
			listActions = (expr != null ? expr.getText() : null);
		}
		String[] arrayPropertyNames = (propertyNames != null ? propertyNames.split(",") : null);
		String[] arrayInlineActions = (inlineActions != null ? inlineActions.split(",") : null);
		String[] arrayListActions = (listActions != null ? listActions.split(",") : null);
		String[] actionCustomNames = ActionsTransform.getActionCustomNames(clazz, sourceUnit);
		
		String appId = SessionManager.getDbId(clazz.getName());
		
		String className = clazz.getNameWithoutPackage();
//		String title = "Search " + className;
//		title = "${i18n.title}";
//		String emptyMessage = "No " + className + " found in the result ";
		
		List<FieldNode> properties = (arrayPropertyNames != null && arrayPropertyNames.length > 0 ? cg.getProperties(arrayPropertyNames) : cg.getFinderProperties());
		
//		String zul = "";
		List<String> zul = new ArrayList<String>();
		
		zul.add("<zk>");
		zul.add("  <finder-window>");
		
		ImageResources.copy(appId, "info", "info.png", 16);
		ImageResources.copy(appId, "deleteConfirm", "deleteConfirm.png", 48);
		ImageResources.copy(appId, "infoConfirm", "infoConfirm.png", 48);

		ImageResources.copy(appId, "search", "search.png", 16);
		
		zul.add("    <template name=\"content\">");

		FieldTransform fieldTransform = new FieldTransform();
		for (FieldNode property : properties) {
			String widgetField = fieldTransform.writeZul(property, null, null, true);
			zul.add("      " + widgetField + "");
		}
		if (arrayInlineActions != null && arrayInlineActions.length > 0) {
			boolean anyAction = false;
//			zul.add("      <hlayout sclass=\"inline-actions\">");
			for (String actionName : actionCustomNames) {
				if (Collections.contains(arrayInlineActions, actionName)) {
					if (!anyAction) {
						zul.add("      <hlayout sclass=\"inline-actions\">");
					}
					anyAction = true;
					zul.add("        <action name=\"" + actionName + "\" />");
				}
			}
			if (anyAction) {
				zul.add("      </hlayout>");
			}
		}
		zul.add("      " + "<icon-field value=\"$\" />" + "");
		
		zul.add("    </template>");
		
		
//	<template name="bottom-actions">
//		<action name="imprimir" />
//		<action name="descargar" />
//	</template>
		ActionsTransform actionsTransform = new ActionsTransform();
		if (actionCustomNames != null && actionCustomNames.length > 0) {
//			zul.add("    <template name=\"bottom-actions\">");
			boolean anyAction = false;
			for (String actionName : actionCustomNames) {
				boolean valid = true;
				valid = valid && (arrayInlineActions == null || arrayInlineActions.length <= 0 || !Collections.contains(arrayInlineActions, actionName));
				valid = valid && (arrayListActions == null || arrayListActions.length <= 0 || Collections.contains(arrayListActions, actionName));
				if (valid) {
					if (!anyAction) {
						zul.add("    <template name=\"bottom-actions\">");
					}
					anyAction = true;
					zul.add("      <action name=\"" + actionName + "\" />");
				}
			}
			if (anyAction) {
				zul.add("    </template>");
			}
		}
		
		zul.add("  </finder-window>");
		zul.add("</zk>");

		ImageResources.copy(appId, "previousPage", "previousPage.png", 16);
		ImageResources.copy(appId, "nextPage", "nextPage.png", 16);

		ImageResources.copy(appId, "clone", "clone" + ".png", 16);

if (false) {		
		List<MethodNode> actions = cg.getPublicActions();
		if (actions != null) {
			for (MethodNode action : actions) {
				String actionName = action.getName();
//				String runAction = "run_" + actionName + "_OnItem";
				String runAction = "runCustomActionItem";
////				String runAction = actionName;
				ImageResources.copy(appId, actionName, actionName + ".png", 16);
//				zul.add("		<button onClick=\"@command('" + runAction + "')\" label=\"${i18n." + className + "_" + actionName + "_finderButtonLabel}\" image=\"${images}/" + actionName + ".png\" />");
				zul.add("		<button onClick=\"@command('" + runAction + "', action='" + actionName + "')\" label=\"${i18n." + className + "_" + actionName + "_finderButtonLabel}\" image=\"${images}/" + actionName + ".png\" />");
			}
		}
}

		ImageResources.copy(appId, "create", "create.png", 16);
		ImageResources.copy(appId, "read", "read.png", 16);
		ImageResources.copy(appId, "update", "update.png", 16);
		ImageResources.copy(appId, "delete", "delete.png", 16);

		String zulName = clazz.getNameWithoutPackage().trim() + "_finder.zul";
		String folder = clazz.getPackageName();
		if (!GClass.ONE_PACKAGE) {
			int idx = folder.lastIndexOf(".");
			folder = (idx < 0 ? folder : folder.substring(0, idx));
		}
		String filename = ServerContext.getRootContext() + folder + "/resources/" + zulName;
		try {
			File file = new File(filename);
//			FileUtils.writeStringToFile(file, zul, "UTF-8", false);
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}

	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 
	protected propertyClazz<clazz> fieldName;
		
	public void setSelectedItem(Basic newValue) {
		this.fieldName = newValue;
	}
	public Basic getSelectedItem() {
		return this.fieldName;
	}
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void addSimpleProperty(ClassNode vm, ClassNode clazz,
			SourceUnit sourceUnit, String fieldName, ClassNode propertyClazz, boolean readonly, boolean generics) {

		ClassNode fieldClass = propertyClazz;
		if (false && generics) {
			fieldClass.setGenericsTypes(new GenericsType[] {new GenericsType(clazz.getDeclaringClass())});
		}
		FieldNode field = new FieldNode(fieldName, Opcodes.ACC_PROTECTED, fieldClass, vm, ConstantExpression.NULL);
		vm.addField(field);
		FieldExpression fieldExpression = new FieldExpression(field);
		
		ReturnStatement getterCode = new ReturnStatement(fieldExpression);
		String getterName = BaseFields.getGetterName(field);
		MethodNode getter = new MethodNode(getterName, Opcodes.ACC_PUBLIC, fieldClass, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, getterCode);
		vm.addMethod(getter);
		
		if (!readonly) {
			VariableExpression varNewValue = new VariableExpression("newValue", fieldClass);
			ExpressionStatement setterCode = new ExpressionStatement(new BinaryExpression(fieldExpression, Token.newSymbol(Types.ASSIGN, -1, -1), varNewValue));
			String setterName = BaseFields.getSetterName(field);
			MethodNode setter = new MethodNode(setterName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, new Parameter[] {new Parameter(fieldClass, "newValue")}, ClassNode.EMPTY_ARRAY, setterCode);
			vm.addMethod(setter);
		}
	}
	
}
