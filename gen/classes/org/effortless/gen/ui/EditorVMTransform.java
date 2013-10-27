package org.effortless.gen.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.effortless.core.ModelException;
import org.effortless.gen.GClass;
import org.effortless.gen.Transform;
import org.effortless.gen.GField;
import org.effortless.model.SessionManager;
import org.effortless.server.ServerContext;
import org.effortless.ui.resources.ImageResources;

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

public class EditorVMTransform extends Object implements Transform<GClass> {

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
			String widgetField = fieldTransform.writeZul(property);
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
