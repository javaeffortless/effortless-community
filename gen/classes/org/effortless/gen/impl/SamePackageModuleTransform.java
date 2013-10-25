package org.effortless.gen.impl;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.GClass;
import org.effortless.gen.ModuleTransform;
import org.effortless.server.ServerContext;

public class SamePackageModuleTransform extends Object implements ModuleTransform {

	public void process (SourceUnit sourceUnit) {
		setupPackage(sourceUnit);
	}
	
	
	protected String getPackage (SourceUnit sourceUnit) {
		String result = null;
		if (sourceUnit != null) {
			ModuleNode module = sourceUnit.getAST();
			result = module.getPackageName();
			if (result == null) {
				String rootCtx = ServerContext.getRootContext();
				String sourceUnitName = sourceUnit.getName();
				if (sourceUnitName.startsWith(rootCtx)) {
					if (GClass.ONE_PACKAGE) {
						String fileName = FilenameUtils.getName(sourceUnitName);
						sourceUnitName = (fileName != null ? sourceUnitName.substring(0, sourceUnitName.length() - (fileName.length() + 1)) : sourceUnitName);
						if (rootCtx.length() <= sourceUnitName.length()) {
							String suffix = sourceUnitName.substring(rootCtx.length());
							if (suffix != null) {
								result = suffix.replaceAll("/", ".");
							}
						}
					}
					else {
						String extension = FilenameUtils.getExtension(sourceUnitName);
						sourceUnitName = (extension != null ? sourceUnitName.substring(0, sourceUnitName.length() - (extension.length() + 1)) : sourceUnitName);
						if (rootCtx.length() <= sourceUnitName.length()) {
							String suffix = sourceUnitName.substring(rootCtx.length());
							if (suffix != null) {
								result = suffix.replaceAll("/", ".");
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	protected void setupPackage (SourceUnit sourceUnit) {
		if (sourceUnit != null) {
			ModuleNode module = sourceUnit.getAST();
			String packageName = module.getPackageName();
			if (packageName == null) {
				String rootCtx = ServerContext.getRootContext();
				String sourceUnitName = sourceUnit.getName();
				if (sourceUnitName.startsWith(rootCtx)) {
					if (GClass.ONE_PACKAGE) {
						String fileName = FilenameUtils.getName(sourceUnitName);
						sourceUnitName = (fileName != null ? sourceUnitName.substring(0, sourceUnitName.length() - (fileName.length() + 1)) : sourceUnitName);
						if (rootCtx.length() <= sourceUnitName.length()) {
							String suffix = sourceUnitName.substring(rootCtx.length());
							if (suffix != null) {
								String newPackageName = suffix.replaceAll("/", ".");
								module.setPackageName(newPackageName);
							}
						}
					}
					else {
						String extension = FilenameUtils.getExtension(sourceUnitName);
						sourceUnitName = (extension != null ? sourceUnitName.substring(0, sourceUnitName.length() - (extension.length() + 1)) : sourceUnitName);
						if (rootCtx.length() <= sourceUnitName.length()) {
							String suffix = sourceUnitName.substring(rootCtx.length());
							if (suffix != null) {
								String newPackageName = suffix.replaceAll("/", ".");
								module.setPackageName(newPackageName);
							}
						}
					}
				}
			}
		}
	}
	
}
