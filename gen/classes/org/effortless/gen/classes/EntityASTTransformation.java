package org.effortless.gen.classes;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.transform.*;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.objectweb.asm.Opcodes;

import org.effortless.gen.GModule;
import org.effortless.gen.InfoModel;
import org.effortless.gen.Transform;
import org.effortless.gen.GApplication;
import org.effortless.gen.GClass;
import org.effortless.gen.GenContext;
import org.effortless.gen.fields.CreateSettingsTransform;
import org.effortless.model.SessionManager;
import org.effortless.server.ServerContext;


//@GroovyASTTransformation(phase=CompilePhase.INITIALIZATION)
//@GroovyASTTransformation(phase=CompilePhase.PARSING)
//@GroovyASTTransformation(phase=CompilePhase.CONVERSION)
@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
//@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class EntityASTTransformation extends Object implements ASTTransformation, Opcodes {

	public EntityASTTransformation () {
		super();
	}
	
	public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
//		List classes = sourceUnit.getAST()?.getClasses()
//		classes.each { ClassNode clazz ->
//			processClass(clazz)
//		}
		
		String appId = loadAppId(sourceUnit);
		if (appId != null) {
			GApplication app = GenContext.getApplication(appId, true);
			GModule module = new GModule(sourceUnit);
			if (!app.containsUnit(sourceUnit)) {
				
				List<Transform<GModule>> moduleTransforms = GenContext.getModuleTransforms();
				if (moduleTransforms != null) {
					for (Transform<GModule> transform : moduleTransforms) {
						transform.process(module);
					}
				}
//				if (EntityClassTransformation.ONE_PACKAGE) {
//					setupPackage(sourceUnit);
//				}
				List<Transform> classTransforms = GenContext.getClassTransforms();
				if (classTransforms != null) {
					if (nodes == null) {
						List<ClassNode> classes = sourceUnit.getAST().getClasses();
						int size = (classes != null ? classes.size() : 0);
						for (int i = 0; i < size; i++) {
							ClassNode clazz = classes.get(i);
							
							GClass gClazz = new GClass(clazz, sourceUnit);
							app.addClass(gClazz);
							for (Transform transform : classTransforms) {
								transform.process(gClazz);
							}
	//						EntityClassTransformation.processClass(clazz, sourceUnit);
						}
					}
					else {
						for (ASTNode node : nodes) {
		//					nodes.each { ASTNode node ->
								if (node instanceof ClassNode) {
									GClass gClazz = new GClass((ClassNode)node, sourceUnit);
									app.addClass(gClazz);
									for (Transform transform : classTransforms) {
										transform.process(gClazz);
									}
	//								EntityClassTransformation.processClass((ClassNode)node, sourceUnit);
								}
							}
					}
				}
				completeApplication(app);
//				appTransform.addItem(nodes, sourceUnit);
			}
		}
	}

	protected void completeApplication(GApplication app) {
if (InfoModel.checkCompleteApplication()) {
		new CreateSettingsTransform().process(app);
		new CreateUserProfileTransform().process(app);
		new CreateUserTransform().process(app);
		new CreateSecuritySystemTransform().process(app);
}
	}

	protected String loadAppId (SourceUnit sourceUnit) {
		String result = null;
		String pkgName = getPackage(sourceUnit);
		result = (pkgName != null ? SessionManager.getDbId(pkgName) : null);
		return result;
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
	
}
