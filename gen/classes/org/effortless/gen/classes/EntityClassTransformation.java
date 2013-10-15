package org.effortless.gen.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nu.xom.ParsingException;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.ann.NoTransform;
import org.effortless.ann.NoLog;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.ModelException;
import org.effortless.gen.EntityClassGen;
import org.effortless.gen.GenContext;
import org.effortless.gen.ViewClassGen;
import org.effortless.gen.fields.BaseFields;
import org.effortless.gen.fields.DeletedField;
import org.effortless.gen.fields.FinalFieldsTransform;
import org.effortless.gen.fields.PkField;
import org.effortless.gen.fields.Restrictions;
import org.effortless.gen.fields.UserFields;
import org.effortless.gen.fields.VersionField;
import org.effortless.gen.methods.AutoSession;
import org.effortless.gen.methods.KryoTransform;
import org.effortless.gen.ui.EditorVMTransform;
import org.effortless.gen.ui.FinderVMTransform;
import org.effortless.model.AbstractIdEntity;
import org.effortless.model.FieldChangeSupportGenerator;
import org.effortless.model.FileEntity;
import org.effortless.model.FileEntityTuplizer;
import org.effortless.model.Filter;
import org.effortless.model.LogData;
import org.effortless.model.SessionManager;
import org.effortless.model.StartupDb;
import org.effortless.server.ServerContext;
import org.effortless.util.ToLabel;
import org.objectweb.asm.Opcodes;

public class EntityClassTransformation {

	protected static boolean checkValid (ClassNode clazz, SourceUnit sourceUnit) {
		boolean result = false;
		if (clazz != null) {
			ClassNode superClass = clazz.getSuperClass();
			String superClassName = (superClass != null ? superClass.getName() : null);
			result = !"groovy.lang.Script".equals(superClassName);
			if (result) {
				List<AnnotationNode> annotations = clazz.getAnnotations(NO_ENTITY_CLAZZ);
				result = (!(annotations != null && annotations.size() > 0));
			}
//			result = result && !"java.lang.Enum".equals(superClassName);
		}
		return result;
	}

	protected static boolean checkEnum (ClassNode clazz, SourceUnit sourceUnit) {
		boolean result = false;
		if (clazz != null) {
			ClassNode superClass = clazz.getSuperClass();
			String superClassName = (superClass != null ? superClass.getName() : null);
			result = "java.lang.Enum".equals(superClassName);
		}
		return result;
	}

	protected static void setupPackage (ClassNode clazz, SourceUnit sourceUnit) {
		if (clazz != null) {
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
						if (ONE_PACKAGE) {//UN SOLO PAQUETE PARA TODOS LOS FICHEROS. HACE QUE NO SEA NECESARIO LOS IMPORTS
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
	
	public static final ClassNode NO_LOG_CLAZZ = new ClassNode(NoLog.class);
	public static final ClassNode NO_ENTITY_CLAZZ = new ClassNode(NoTransform.class);
	
	public static void processClass (ClassNode clazz, SourceUnit sourceUnit) {
		//println ">>>>>>>>>>>>> EntityAST Transformation = " + clazz.getName() + " (begin)"
//							println " EntityAST Transformatio  thread id = " + Thread.currentThread().getName()
//							println " EntityAST Transformatio " + effortless.MySession.getRootContext()
		if (checkValid(clazz, sourceUnit)) {// || !clazz.getPackageName().equals("effortless")) {
//			clazz.addInterface(new ClassNode(java.io.Serializable.class));
			setupPackage(clazz, sourceUnit);
			if (!checkEnum(clazz, sourceUnit)) {
				EntityClassGen ecg = new EntityClassGen(clazz, sourceUnit);
				ecg.alterActions();
//				ActionsTransform.processClass(clazz, sourceUnit);
				
				setupParent(clazz);
				addAnnotations(clazz);
		
				List<FieldNode> fields = clazz.getFields();
				for (FieldNode field : fields) {
	//				fields.each { FieldNode field ->
//					UserFields.processField(clazz, field, sourceUnit);
					ecg.processField(field);
				}
				
				if (false) {
				FinalFieldsTransform.processClass(clazz, sourceUnit);
				}
				
				addMethods(clazz, sourceUnit);
				addStaticMethods(clazz, sourceUnit);
				
				ecg.addDoHashCode();//HashCodeTransform.processClass(clazz, sourceUnit);
				ecg.addDoEquals();//EqualsTransform.processClass(clazz, sourceUnit);
				ecg.addDoCompare();//CompareToTransform.processClass(clazz, sourceUnit);
				ecg.addDoToString();//ToStringTransform.processClass(clazz, sourceUnit);
//				ecg.addD
				KryoTransform.processClass(clazz, sourceUnit);
				ecg.addCreateClone();//CloneTransform.processClass(clazz, sourceUnit);
				
	//			addPrimitiveFields(clazz);
	//			AutoSession.generate(clazz);
	//			FieldChangeSupportGenerator.generate(clazz);
	//					println ">>>>>>>>>>>>>> PERIICOOOOOOOOOOOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
			//ñ
				
				List<AnnotationNode> annotationsNoLog = clazz.getAnnotations(NO_LOG_CLAZZ);
				if (false && !(annotationsNoLog != null && annotationsNoLog.size() > 0)) {
					String keyLogData = clazz.getName() + "." + LogData.KEY_CLASS_NEEDS;
					GenContext.set(keyLogData, Boolean.TRUE);
					ClassNode logClass = tryNeedsLogEntity(clazz, sourceUnit);
					if (logClass != null) {
						addMethodNewLogData(clazz, sourceUnit, logClass);
					}
				}
				else {
					disableLog(clazz, sourceUnit);
					System.out.println("NO LOG for " + clazz.getName());
				}
				
//				ViewClassGen cg = new ViewClassGen(clazz, sourceUnit);
//				cg.addFinderFilter();
				
				FinderVMTransform.processClass(clazz, sourceUnit);
				EditorVMTransform.processClass(clazz, sourceUnit);
				
				updateDb(clazz, sourceUnit);
//				tryNeedsFileEntity(clazz, sourceUnit);
			}
		}
//		else if (checkEnum(clazz, sourceUnit)) {
		else {
			setupPackage(clazz, sourceUnit);
		}
		//println ">>>>>>>>>>>>> EntityAST Transformation = " + clazz.getName() + " (end)"
	}

	
	protected static void addMethods(ClassNode clazz, SourceUnit sourceUnit) {
		ReferenciableTransform.processClass(clazz, sourceUnit);
		SavePropertiesTransform.processClass(clazz, sourceUnit);
//		addMethodsToLabelLocale(clazz, sourceUnit);
	}

	protected static final ClassNode CLASS_NODE_TO_LABEL = new ClassNode(ToLabel.class);
	
//	/*
//	 * 
//	public String toLabel (Locale locale) {
//		String result = null;
//		ToLabel tl = new ToLabel(null);
//		result = tl.add(result, getDate());
//		result = tl.add(result, getType());
//		result = tl.add(result, getAuthorCode());
//		result = tl.add(result, getTargetType());
//		result = tl.add(result, getTargetCode());
//		return result;
//	}
//
//	 * 
//	 * 
//	 */
//	protected static MethodNode addMethodsToLabelLocale (ClassNode clazz, SourceUnit sourceUnit) {
//		MethodNode result = null;
//		Statement code = null;
//		List<FieldNode> fields = toLabelFields(clazz, sourceUnit);
//		if (fields != null && fields.size() > 0) {
//			BlockStatement block = new BlockStatement();
//
//			//String result = null;
//			VariableExpression varResult = new VariableExpression("result", ClassHelper.STRING_TYPE);
//			DeclarationExpression exprResult = new DeclarationExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), ConstantExpression.NULL);
//			block.addStatement(new ExpressionStatement(exprResult));
//			
//			//ToLabel tl = new ToLabel(null);
//			VariableExpression varTl = new VariableExpression("tl", CLASS_NODE_TO_LABEL);
//			ConstructorCallExpression exprNewTl = new ConstructorCallExpression(CLASS_NODE_TO_LABEL, new ArgumentListExpression(ConstantExpression.NULL));
//			DeclarationExpression exprTl = new DeclarationExpression(varTl, Token.newSymbol(Types.ASSIGN, -1, -1), exprNewTl);
//			block.addStatement(new ExpressionStatement(exprTl));
//
//			for (FieldNode field : fields) {
//				//result = tl.add(result, getFieldName());
//				String getterName = BaseFields.getGetterName(field);
//				MethodCallExpression exprGetProperty = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, getterName, new ArgumentListExpression());
//				MethodCallExpression exprAdd = new MethodCallExpression(varTl, "add", new ArgumentListExpression(new Expression[] {varResult, exprGetProperty}));
//				BinaryExpression assignProperty = new BinaryExpression(varResult, Token.newSymbol(Types.ASSIGN, -1, -1), exprAdd);
//				block.addStatement(new ExpressionStatement(assignProperty));
//			}
//			
//			//return result;
//			block.addStatement(new ReturnStatement(varResult));
//			
//			code = block;
//		}
//		if (code != null) {
//			Parameter[] parameters = new Parameter[] {new Parameter(new ClassNode(Locale.class), "locale")};
//			
//			result = new MethodNode("toLabel", Opcodes.ACC_PUBLIC, ClassHelper.STRING_TYPE, parameters, ClassNode.EMPTY_ARRAY, code);
//			clazz.addMethod(result);
//		}
//		return result;
//	}
//
//	protected static List<FieldNode> toLabelFields(ClassNode clazz, SourceUnit sourceUnit) {
//		List<FieldNode> result = null;
//		result = new ArrayList<FieldNode>();
//		List<FieldNode> fields = clazz.getFields();
//		for (FieldNode field : fields) {
//			if (Restrictions.isSingleUnique(clazz, field)) {
//				result.add(field);
//			}
//			else if (Restrictions.isNotNull(clazz, field)) {
//				result.add(field);
//			}
//		}
//		return result;
//	}

	/*
					protected LogData _newInstanceLogData () {
						return new LogData();
					}
	 * 
	 */
	protected static MethodNode addMethodNewLogData(ClassNode clazz,
			SourceUnit sourceUnit, ClassNode logClazz) {
		MethodNode result = null;
		
		ConstructorCallExpression newInstance = new ConstructorCallExpression(logClazz, new ArgumentListExpression());
		ReturnStatement code = new ReturnStatement(newInstance);
		ClassNode logClassNode = new ClassNode(LogData.class);
		
		result = new MethodNode("_newInstanceLogData", Opcodes.ACC_PROTECTED, logClassNode, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(result);
		return result;
	}

	/**
	 * 
	@Override
	public boolean doCheckLog() {
		return false;
	}
	 */
	protected static MethodNode disableLog(ClassNode clazz, SourceUnit sourceUnit) {
		MethodNode result = null;
		ReturnStatement code = new ReturnStatement(ConstantExpression.PRIM_FALSE);
		result = new MethodNode("doCheckLog", Opcodes.ACC_PUBLIC, ClassHelper.boolean_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		clazz.addMethod(result);
		return result;
	}

	protected static void updateDb(ClassNode clazz, SourceUnit sourceUnit) {
		try {
			String packageName = clazz.getPackageName();
			String fullClassName = packageName + "." + clazz.getNameWithoutPackage();
//			StartupDb.addAppEntity(fullClassName);
			StartupDb.updateDb(fullClassName);
		} catch (ParsingException e) {
			throw new ModelException(e);
		} catch (IOException e) {
			throw new ModelException(e);
		}
	}
	
	public static ClassNode tryNeedsFileEntity(ClassNode clazz, SourceUnit sourceUnit) {
		return tryNeedsNewExternalEntity(clazz, sourceUnit, FileEntity.class, FileEntity.KEY_CLASS_NEEDS, FileEntity.KEY_APP_NEEDS, FileEntityTuplizer.class);
	}
	
	public static ClassNode tryNeedsLogEntity(ClassNode clazz, SourceUnit sourceUnit) {
		return tryNeedsNewExternalEntity(clazz, sourceUnit, LogData.class, LogData.KEY_CLASS_NEEDS, LogData.KEY_APP_NEEDS, null);
	}
	
	protected static ClassNode tryNeedsNewExternalEntity(ClassNode clazz, SourceUnit sourceUnit, Class externalEntity, String suffixClass, String suffixApp, Class tuplizer) {
		ClassNode result = null;
		String className = clazz.getName();
		String key = className + "." + suffixClass;
		Boolean needs = (Boolean)GenContext.get(key);
		if (needs != null && needs.booleanValue()) {
			String dbId = SessionManager.getDbId(className);
			key = dbId + "." + suffixApp;
			needs = (Boolean)GenContext.get(key);
			if (needs == null || needs.booleanValue() == false) {
				result = addNewExternalClass(clazz, sourceUnit, externalEntity, true);
				GenContext.set(key, Boolean.TRUE);
			}
			else {
				result = addNewExternalClass(clazz, sourceUnit, externalEntity, false);
			}
			if (result != null && tuplizer != null && false) {
				AnnotationNode annTuplizer = new AnnotationNode(new ClassNode(org.hibernate.annotations.Tuplizer.class));
				annTuplizer.setMember("impl", new ClassExpression(ClassNodeHelper.toClassNode(tuplizer)));
				clazz.addAnnotation(annTuplizer);
			}
		}
		return result;
	}
	
	protected static void tryNeedsExternalEntity(ClassNode clazz, SourceUnit sourceUnit, Class externalEntity, String suffixClass, String suffixApp) {
		String className = clazz.getName();
		String key = className + "." + suffixClass;
		Boolean needs = (Boolean)GenContext.get(key);
		if (needs != null && needs.booleanValue()) {
			String dbId = SessionManager.getDbId(className);
			key = dbId + "." + suffixApp;
			needs = (Boolean)GenContext.get(key);
			if (needs == null || needs.booleanValue() == false) {
				addSameExternalClass(clazz, sourceUnit, externalEntity);
				GenContext.set(key, Boolean.TRUE);
			}
		}
	}
	
	public static final boolean ONE_PACKAGE = true;
	
	protected static String newNameExternalClass (ClassNode clazz, SourceUnit sourceUnit, ClassNode externClazz) {
		String result = null;
		String packageName = clazz.getPackageName();
		if (!ONE_PACKAGE) {
			int lastIdx = (packageName != null ? packageName.lastIndexOf(".") : -1);
			packageName = (lastIdx > -1 ? packageName.substring(0, lastIdx) : "");
		}

		int lastIdx = (packageName != null ? packageName.lastIndexOf(".") : -1);
		String appName = (lastIdx > -1 ? packageName.substring(lastIdx + 1) : "");
		
		String className = externClazz.getNameWithoutPackage();
		if (appName != null && appName.length() > 0) {
			className = appName.substring(0, 1).toUpperCase() + appName.substring(1) + className.substring(0, 1).toUpperCase() + className.substring(1);
		}
		packageName += (packageName != null && packageName.length() > 0 ? "." : "");
		result = packageName + className;
		return result;
	}
	
	protected static ClassNode addNewExternalClass (ClassNode clazz, SourceUnit sourceUnit, Class externalClass, boolean add) {
		ClassNode result = null;
		ClassNode externClazz = new ClassNode(externalClass);
		ClassNode newClazz = new ClassNode(externClazz.getNameWithoutPackage(), externClazz.getModifiers(), externClazz);
		String newName = newNameExternalClass(clazz, sourceUnit, externClazz);
		newClazz.setName(newName);
		
		if (add) {
			addAnnotations(newClazz);
			addStaticMethods(newClazz, sourceUnit);
			
			sourceUnit.getAST().addClass(newClazz);
			updateDb(newClazz, sourceUnit);
		}
		
		result = newClazz;
		return result;
	}

	protected static void addSameExternalClass (ClassNode clazz, SourceUnit sourceUnit, Class externalClass) {
		try {
			ClassNode externClazz = new ClassNode(externalClass);
			String externPackageName = externClazz.getPackageName();
			String externFullClassName = externPackageName + "." + externClazz.getNameWithoutPackage();
			
			String entityPackageName = clazz.getPackageName();
			String entityFullClassName = entityPackageName + "." + clazz.getNameWithoutPackage();
			
//			StartupDb.addAppExternEntity(entityFullClassName, externFullClassName);
			StartupDb.updateDbExternEntity(entityFullClassName, externFullClassName);
		} catch (ParsingException e) {
			throw new ModelException(e);
		} catch (IOException e) {
			throw new ModelException(e);
		}
	}

	
	

	/**
	 * 
		public static Filter<AllBasicProperties> listBy () {
			return AbstractIdEntity.listBy(AllBasicProperties.class);
		}
	 * 
	 * @param clazz
	 * @param sourceUnit
	 */
	protected static void addStaticMethods(ClassNode clazz, SourceUnit sourceUnit) {
		String methodName = "listBy";
		
		Expression arguments = new ArgumentListExpression(new Expression[] {new ClassExpression(clazz)});
		StaticMethodCallExpression call = new StaticMethodCallExpression(new ClassNode(AbstractIdEntity.class), "listBy", arguments);
		ReturnStatement returnCode = new ReturnStatement(call);
		ClassNode returnType = new ClassNode(Filter.class);
//		returnType.setUsingGenerics(true);
//		returnType.setGenericsTypes(new GenericsType[] {new GenericsType(clazz)});
		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, returnType, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, returnCode);

		clazz.addMethod(method);
	}

	protected static void addPrimitiveFields (ClassNode clazz) {
		PkField.generate(clazz);
		VersionField.generate(clazz);
		DeletedField.generate(clazz);
	}

	 //extends AbstractIdEntity<AllBasicProperties>		
	protected static void setupParent (ClassNode clazz) {
		ClassNode superClass = clazz.getSuperClass();
		if (superClass == null || "java.lang.Object".equals(superClass.getName())) {
		superClass = new ClassNode(AbstractIdEntity.class);
//		ClassNode thisClass = clazz.getDeclaringClass();//new ClassNode(clazz.getTypeClass());
//		ClassNode thisClass = new ClassNode(clazz.getName(), clazz.getModifiers(), clazz.getSuperClass());
//		thisClass.setRedirect(clazz);
//		ClassNode thisClass = clazz;
//		ClassNode thisClass = ClassHelper.getWrapper(clazz);
//		GenericsType gt = new GenericsType(superClass);
//		gt.setResolved(false);
//		gt.setType(thisClass);
//		gt.setWildcard(false);
//		superClass.setGenericsTypes(new GenericsType[] {gt});
		clazz.setSuperClass(superClass);
		}
	}

	//@Entity
	//@Table(name="TABLE")
	protected static void addAnnotations (ClassNode clazz) {
		String tableName = clazz.getNameWithoutPackage().toUpperCase();

		AnnotationNode annEntity = new AnnotationNode(new ClassNode(javax.persistence.Entity.class));
		clazz.addAnnotation(annEntity);

		AnnotationNode annTable = new AnnotationNode(new ClassNode(javax.persistence.Table.class));
		annTable.setMember("name", new ConstantExpression(tableName));
		clazz.addAnnotation(annTable);
		
		//@javax.persistence.SequenceGenerator(name="sequence_id", sequenceName="basic_sequence")
		AnnotationNode annSequence = new AnnotationNode(new ClassNode(javax.persistence.SequenceGenerator.class));
		annSequence.setMember("name", new ConstantExpression("sequence_id"));
		annSequence.setMember("sequenceName", new ConstantExpression(tableName));
		annSequence.setMember("initialValue", new ConstantExpression(Integer.valueOf(1)));
		clazz.addAnnotation(annSequence);
		
		AnnotationNode annDynamicUpdate = new AnnotationNode(new ClassNode(org.hibernate.annotations.DynamicUpdate.class));
		annDynamicUpdate.setMember("value", ConstantExpression.TRUE);
		clazz.addAnnotation(annDynamicUpdate);

		AnnotationNode annDynamicInsert = new AnnotationNode(new ClassNode(org.hibernate.annotations.DynamicInsert.class));
		annDynamicInsert.setMember("value", ConstantExpression.TRUE);
		clazz.addAnnotation(annDynamicInsert);
		
		AnnotationNode annSelectBeforeUpdate = new AnnotationNode(new ClassNode(org.hibernate.annotations.SelectBeforeUpdate.class));
		annSelectBeforeUpdate.setMember("value", ConstantExpression.FALSE);
		clazz.addAnnotation(annSelectBeforeUpdate);
		
		
		
		
if (false) {
		//@org.hibernate.annotations.Tuplizer(impl = entities.CustomEntityTuplizer.class)
		AnnotationNode annTuplizer = new AnnotationNode(new ClassNode(org.hibernate.annotations.Tuplizer.class));
		annTuplizer.setMember("impl", new ClassExpression(new ClassNode(org.effortless.model.CustomEntityTuplizer.class)));
		clazz.addAnnotation(annTuplizer);
}
	}

	
	
}
