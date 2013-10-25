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
import org.effortless.gen.GClass;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GField;
import org.effortless.gen.InfoClassNode;
import org.effortless.gen.GMethod;
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

	public void process (GClass cg) {
		if (cg != null && cg.checkEntityValid() && !cg.checkEnum()) {
			writeZul(cg);
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
	protected void writeZul(GClass clazz/*ClassNode vm, ClassNode clazz, SourceUnit sourceUnit*/) {
		String className = clazz.getNameWithoutPackage();
		
		FieldTransform fieldTransform = new FieldTransform();
		
		List<GField> properties = getEditorProperties(clazz);
		String varId = "editor";
		
		List<String> zul = new ArrayList<String>();

		zul.add("<zk>");
		zul.add("  <editor-window>");

		for (GField property : properties) {
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
	
	protected List<GField> getEditorProperties (GClass clazz) {
		List<GField> result = null;
		result = new ArrayList<GField>();
		List<GField> fields = clazz.getFields();
		for (GField field : fields) {
			if (!result.contains(field)) {
				result.add(field);
			}
		}
		return result;
	}
	
}
