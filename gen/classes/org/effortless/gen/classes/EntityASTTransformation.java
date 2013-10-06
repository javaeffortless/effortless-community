package org.effortless.gen.classes;

import java.io.IOException;
import java.util.List;

import nu.xom.ParsingException;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.transform.*;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.objectweb.asm.Opcodes;

import org.effortless.core.ModelException;
import org.effortless.gen.AppTransform;
import org.effortless.gen.GenContext;
import org.effortless.gen.fields.DeletedField;
import org.effortless.gen.fields.PkField;
import org.effortless.gen.fields.UserFields;
import org.effortless.gen.fields.VersionField;
import org.effortless.gen.methods.*;
import org.effortless.model.CustomEntityTuplizer;
import org.effortless.model.FieldChangeSupportGenerator;
import org.effortless.model.FileEntity;
import org.effortless.model.LogData;
import org.effortless.model.SessionManager;
import org.effortless.model.StartupDb;
//import org.effortless.model.properties.*;
import org.effortless.server.ServerContext;


//@GroovyASTTransformation(phase=CompilePhase.INITIALIZATION)
//@GroovyASTTransformation(phase=CompilePhase.PARSING)
//@GroovyASTTransformation(phase=CompilePhase.CONVERSION)
@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
//@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class EntityASTTransformation implements ASTTransformation, Opcodes {

	public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
//		List classes = sourceUnit.getAST()?.getClasses()
//		classes.each { ClassNode clazz ->
//			processClass(clazz)
//		}
		
		String pkgName = getPackage(sourceUnit);
		String appId = (pkgName != null ? SessionManager.getDbId(pkgName) : null);
		if (appId != null) {
			AppTransform appTransform = GenContext.getAppTransform(appId, true);
			
			if (!appTransform.containsUnit(sourceUnit)) {
				if (EntityClassTransformation.ONE_PACKAGE) {
					setupPackage(sourceUnit);
				}
				if (nodes == null) {
					List<ClassNode> classes = sourceUnit.getAST().getClasses();
					int size = (classes != null ? classes.size() : 0);
					for (int i = 0; i < size; i++) {
						ClassNode clazz = classes.get(i);
						EntityClassTransformation.processClass(clazz, sourceUnit);
					}
				}
				else {
					for (ASTNode node : nodes) {
	//					nodes.each { ASTNode node ->
							if (node instanceof ClassNode) {
								EntityClassTransformation.processClass((ClassNode)node, sourceUnit);
							}
						}
				}
				appTransform.addItem(nodes, sourceUnit);
			}
		}
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
					if (EntityClassTransformation.ONE_PACKAGE) {
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
					if (EntityClassTransformation.ONE_PACKAGE) {
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
