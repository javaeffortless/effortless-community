package org.effortless.gen.impl;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.effortless.gen.GClass;
import org.effortless.gen.Transform;
import org.effortless.server.ServerContext;

public class SamePackageTransform extends Object implements Transform<GClass> {

	@Override
	public void process(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		SourceUnit sourceUnit = cg.getSourceUnit();
		if (clazz != null && (true || cg.checkEntityValid())) {
			String packageName = clazz.getPackageName();
			if (packageName == null) {
				String rootCtx = ServerContext.getRootContext();
				String sourceUnitName = sourceUnit.getName();
				if (sourceUnitName.startsWith(rootCtx)) {
					String extension = FilenameUtils.getExtension(sourceUnitName);
					sourceUnitName = (extension != null ? sourceUnitName.substring(0, sourceUnitName.length() - (extension.length() + 1)) : sourceUnitName);
					String suffix = sourceUnitName.substring(rootCtx.length());
					if (suffix != null) {
						String newPackageName = suffix.replaceAll("/", ".");// + ".";
						if (GClass.ONE_PACKAGE) {//UN SOLO PAQUETE PARA TODOS LOS FICHEROS. HACE QUE NO SEA NECESARIO LOS IMPORTS
							int idx = (newPackageName != null ? newPackageName.lastIndexOf(".") : -1);
							newPackageName = (idx < 0 ? newPackageName : newPackageName.substring(0, idx));
						}
////						PackageNode packageNode = clazz.getPackage();
//						ModuleNode module = new ModuleNode(sourceUnit);
//						PackageNode packageNode = new PackageNode(newPackageName);
//						packageNode.addAnnotations(new ArrayList<AnnotationNode>());
//						module.setPackage(packageNode);
//						clazz.setModule(module);
						String newName = newPackageName + "." + clazz.getName();
						clazz.setName(newName);
//						boolean equals = ("".equals(packageName));
					}
				}
			}
		}
	}
	
}
