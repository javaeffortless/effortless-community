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
import org.effortless.core.ModelException;
import org.effortless.core.StringUtils;
import org.effortless.gen.ClassGen;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.InfoClassNode;
import org.effortless.gen.MethodGen;
import org.effortless.gen.ViewClassGen;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.Restrictions;
import org.effortless.model.AbstractEntity;
import org.effortless.model.SessionManager;
import org.effortless.server.ServerContext;
import org.effortless.ui.resources.ImageResources;
import org.effortless.ui.vm.EditorVM;
import org.objectweb.asm.Opcodes;

/**
 *
 * Implements

@org.effortless.NoEntity
public class BasicEditorViewModel {
	
	private Basic item;

	public Basic getItem(){
		return this.item;
	}
	
	public Basic getItem() {
		return this.item;
	}

	@org.zkoss.bind.annotation.Command
	public void save() {
		if (this.item.hasChanges()) {
			this.item.persist();
		}
	}

	@org.zkoss.bind.annotation.Command
	public void cancel() {
	}

}
	
	
 * 
 * 
 * @author jesus
 *
 */

public class EditorVMTransform extends Object implements ClassTransform {

	public static String getEditorName (ClassNode clazz, SourceUnit sourceUnit) {
		String result = null;
		result = clazz.getName() + "EditorViewModel";
		return result;
	}
	
	public void process (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null && InfoClassNode.checkEntityValid(clazz, sourceUnit) && !InfoClassNode.checkEnum(clazz, sourceUnit)) {
			ViewClassGen cg = new ViewClassGen(clazz, sourceUnit);
			ClassGen vm = null;//cg.addEditorVM();
			
			writeZul((vm != null ? vm.getClassNode() : null), clazz, sourceUnit);
		}
	}

	
	
	
	
	
	
	
	/**
	 * 
	 * 
<zk>
<editor-window>
	<text-field value="$nombre" />
	<label-field value="$etiqueta" />
	<count-field value="$contador" />
	<bool-field value="$activo" />
	<date-field value="$fecha" />
	<time-field value="$hora" />
</editor-window>
</zk>
	 * 
	 * 
	 * 
	 * @param vm
	 * @param clazz
	 * @param sourceUnit
	 */
	public static void writeZul(ClassNode vm, ClassNode clazz, SourceUnit sourceUnit) {
//		String vmName = getEditorName(clazz, sourceUnit);
		String vmName = EditorVM.class.getName();
		
		String className = clazz.getNameWithoutPackage();
		
		FieldTransform fieldTransform = new FieldTransform();
		
		List<FieldNode> properties = getEditorProperties(clazz, sourceUnit);
		String varId = "editor";
		
		List<String> zul = new ArrayList<String>();

		zul.add("<zk>");
		zul.add("  <editor-window>");

		for (FieldNode property : properties) {
			String widgetField = fieldTransform.writeZul(property, varId, "item", true);
			zul.add("    " + widgetField + "");
		}
		
		zul.add("  </editor-window>");
		zul.add("</zk>");


		String appId = SessionManager.getDbId(clazz.getName());
		ImageResources.copy(appId, "save", "save.png", 16);
		ImageResources.copy(appId, "cancel", "cancel.png", 16);
		ImageResources.copy(appId, "close", "close.png", 16);

		
		String zulName = clazz.getNameWithoutPackage().trim() + "_editor.zul";
		String folder = clazz.getPackageName();
		if (!ClassGen.ONE_PACKAGE) {
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
<window title="Editor" width="600px" border="normal" apply="org.zkoss.bind.BindComposer" viewModel="@id('editor') @init('org.effortless.samples.basic.IconTagEditorViewModel')">
	<grid>
		<columns>
			<column hflex="1" />
			<column hflex="5" />
		</columns>
		<rows>
			<row><label value="File:"/><textbox width="98%"/></row>
		</rows>
	</grid>

	<hlayout>
		<button label="OK" image="/org/effortless/samples/img/search.png" onClick="@command('save')" />
		<button label="Cancel" image="/org/effortless/samples/img/search.png" onClick="@command('cancel')" />
	</hlayout>
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
//		String vmName = getEditorName(clazz, sourceUnit);
		String vmName = EditorVM.class.getName();
		
		String className = clazz.getNameWithoutPackage();
		
		FieldTransform fieldTransform = new FieldTransform();
		
		List<FieldNode> properties = getEditorProperties(clazz, sourceUnit);
		String varId = "editor";
		
		List<String> zul = new ArrayList<String>();
		
		if (false) {
		
		zul.add("<window title=\"${i18n." + className + "_editorTitle}\" width=\"100%\" height=\"100%\" contentStyle=\"overflow:auto\" border=\"normal\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('" + varId + "') @init('" + vmName + "')\">");

		zul.add("	<grid>");
		zul.add("		<columns>");
		zul.add("			<column width=\"20%\" />");
		zul.add("			<column width=\"80%\" />");
		zul.add("		</columns>");
		zul.add("		<rows>");
		for (FieldNode property : properties) {
			String pName = property.getName();
			String lName = StringUtils.uncapFirst(pName);
//			String label = StringUtils.capFirst(pName);
//			String widgetField = "<textbox value=\"@bind(" + varId + ".item." + lName + ")\" readonly=\"@bind(" + varId + ".readonly)\" />";
			String widgetField = fieldTransform.writeZul(property, varId, "item", true);
			String tag = "";
			if (false) {
				tag = "<tag value=\"${i18n." + className + "_" + lName + "_label}\"/>";
			}
			zul.add("			<row>" + tag  + widgetField + "</row>");
		}
		zul.add("		</rows>");
		zul.add("	</grid>");

		zul.add("	<hlayout sclass=\"z-hlayout-right\">");
		String appId = SessionManager.getDbId(clazz.getName());
		ImageResources.copy(appId, "save", "save.png", 16);
		ImageResources.copy(appId, "cancel", "cancel.png", 16);
		ImageResources.copy(appId, "close", "close.png", 16);
		zul.add("		<button onClick=\"@command('save')\" label=\"${i18n." + className + "_save_editorButtonLabel}\" image=\"${images}/save.png\" visible=\"@load(" + varId + ".mode ne 'read'" + ")\" />");
		zul.add("		<button onClick=\"@command('cancel')\" label=\"${i18n." + className + "_cancel_editorButtonLabel}\" image=\"${images}/cancel.png\" visible=\"@load(" + varId + ".mode ne 'read'" + ")\" />");
		zul.add("		<button onClick=\"@command('close')\" label=\"${i18n." + className + "_close_editorButtonLabel}\" image=\"${images}/close.png\" visible=\"@load(" + varId + ".mode eq 'read'" + ")\" />");
		zul.add("	</hlayout>");
	
		zul.add("</window>");
		}
		else {
			zul.add("<editor-window i18n=\"" + className + "\" apply=\"org.zkoss.bind.BindComposer\" viewModel=\"@id('" + varId + "') @init('" + vmName + "')\" value=\"@bind(" + varId + ".item)\">");
			for (FieldNode property : properties) {
				String widgetField = fieldTransform.writeZul(property, varId, "item", true);
				zul.add("		" + widgetField + "");
			}
			zul.add("</editor-window>");
		}
		
		String zulName = clazz.getNameWithoutPackage().trim() + "_editor.zul";
		String folder = clazz.getPackageName();
		if (!ClassGen.ONE_PACKAGE) {
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

	
	public static List<FieldNode> getEditorProperties (ClassNode clazz, SourceUnit sourceUnit) {
		List<FieldNode> result = null;
		result = new ArrayList<FieldNode>();
		List<FieldNode> fields = clazz.getFields();
		for (FieldNode field : fields) {
			if (!result.contains(field)) {
				result.add(field);
			}
		}
		return result;
	}
	
}
