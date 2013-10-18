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
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GenContext;
import org.effortless.gen.ModuleTransform;
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
		
		String appId = GenContext.loadAppId(sourceUnit);
		if (appId != null) {
			AppTransform appTransform = GenContext.getAppTransform(appId, true);
			
			if (!appTransform.containsUnit(sourceUnit)) {
				List<ModuleTransform> moduleTransforms = GenContext.getModuleTransforms();
				if (moduleTransforms != null) {
					for (ModuleTransform transform : moduleTransforms) {
						transform.process(sourceUnit);
					}
				}
//				if (EntityClassTransformation.ONE_PACKAGE) {
//					setupPackage(sourceUnit);
//				}
				List<ClassTransform> classTransforms = GenContext.getClassTransforms();
				if (classTransforms != null) {
					if (nodes == null) {
						List<ClassNode> classes = sourceUnit.getAST().getClasses();
						int size = (classes != null ? classes.size() : 0);
						for (int i = 0; i < size; i++) {
							ClassNode clazz = classes.get(i);
							for (ClassTransform transform : classTransforms) {
								transform.process(clazz, sourceUnit);
							}
	//						EntityClassTransformation.processClass(clazz, sourceUnit);
						}
					}
					else {
						for (ASTNode node : nodes) {
		//					nodes.each { ASTNode node ->
								if (node instanceof ClassNode) {
									for (ClassTransform transform : classTransforms) {
										transform.process((ClassNode)node, sourceUnit);
									}
	//								EntityClassTransformation.processClass((ClassNode)node, sourceUnit);
								}
							}
					}
				}
				appTransform.addItem(nodes, sourceUnit);
			}
		}
	}
	
}
