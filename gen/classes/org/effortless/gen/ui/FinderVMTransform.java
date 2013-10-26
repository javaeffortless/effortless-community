package org.effortless.gen.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.ast.ClassNode;
import org.effortless.ann.Finder;
import org.effortless.core.Collections;
import org.effortless.core.ModelException;
import org.effortless.core.StringUtils;
import org.effortless.gen.GAnnotation;
import org.effortless.gen.GClass;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GField;
import org.effortless.gen.InfoModel;
import org.effortless.model.SessionManager;
import org.effortless.server.ServerContext;
import org.effortless.ui.impl.CteUi;
import org.effortless.ui.resources.ImageResources;

/**
 *
 * Implements
 * 
 * 
 * @author jesus
 *
 */
public class FinderVMTransform extends Object implements ClassTransform {

	public void process (GClass clazz) {
		if (clazz != null && clazz.checkEntityValid() && !clazz.checkEnum()) {
			CreateFinderFilterClassTransform ffT = new CreateFinderFilterClassTransform();
			ffT.process(clazz);
			GClass finderFilter = ffT.getResult();

			if (finderFilter != null) {
				writeFinderFilterZul(finderFilter, clazz);
				writeMoreInfoZul(clazz);
				writeZul(clazz);
			}
		}
	}
	
	/*


<info-window>
  <label-field value="$nombre" />
</info-window>


	 * 
	 */
	public void writeMoreInfoZul(GClass clazz) {
		String className = clazz.getNameWithoutPackage();
		
		List<GField> properties = InfoModel.getFinderProperties(clazz);
		
		List<String> zul = new ArrayList<String>();
		zul.add("<zk>");
		zul.add("  <info-window>");
		int size = 0;
		for (GField property : properties) {
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
	

	protected void writeFinderFilterZul(GClass _filter, GClass clazz) {
		ClassNode filter = _filter.getClassNode();
		
		FieldTransform fieldTransform = new FieldTransform();
		
		String className = filter.getNameWithoutPackage();
		
		List<GField> properties = InfoModel.getFinderProperties(_filter);
		
		List<String> zul = new ArrayList<String>();
		zul.add("<zk>");
		zul.add("  <embedded-filter-window>");
		for (GField property : properties) {
			String widgetField = fieldTransform.writeZul(property);
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
	public void writeZul(GClass clazz) {
		String propertyNames = null;
		String inlineActions = null;
		String listActions = null;

		GAnnotation annFinder = clazz.getAnnotation(Finder.class);
		if (annFinder != null) {
			propertyNames = annFinder.getMemberString("properties");
			inlineActions = annFinder.getMemberString("inlineActions");
			listActions = annFinder.getMemberString("listActions");
		}
		String[] arrayPropertyNames = (propertyNames != null ? propertyNames.split(",") : null);
		String[] arrayInlineActions = (inlineActions != null ? inlineActions.split(",") : null);
		String[] arrayListActions = (listActions != null ? listActions.split(",") : null);

		String[] actionCustomNames = InfoModel.getActionCustomNames(clazz);
		
		String appId = SessionManager.getDbId(clazz.getName());
		
		List<GField> properties = (arrayPropertyNames != null && arrayPropertyNames.length > 0 ? clazz.getProperties(arrayPropertyNames) : InfoModel.getFinderProperties(clazz));
		
		List<String> zul = new ArrayList<String>();
		
		zul.add("<zk>");
		zul.add("  <finder-window>");
		
		ImageResources.copy(appId, "info", "info.png", 16);
		ImageResources.copy(appId, "deleteConfirm", "deleteConfirm.png", 48);
		ImageResources.copy(appId, "infoConfirm", "infoConfirm.png", 48);

		ImageResources.copy(appId, "search", "search.png", 16);
		
		zul.add("    <template name=\"content\">");

		FieldTransform fieldTransform = new FieldTransform();
		for (GField property : properties) {
			String widgetField = fieldTransform.writeZul(property);
			zul.add("      " + widgetField + "");
		}
		if (arrayInlineActions != null && arrayInlineActions.length > 0) {
			boolean anyAction = false;
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
		if (actionCustomNames != null && actionCustomNames.length > 0) {
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
			FileUtils.writeLines(file, "UTF-8", zul, false);
		} catch (IOException e) {
			throw new ModelException(e);
		} 
	}

}
