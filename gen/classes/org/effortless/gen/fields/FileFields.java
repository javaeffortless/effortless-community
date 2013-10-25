package org.effortless.gen.fields;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nu.xom.ParsingException;

import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.groovy.transform.*;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.syntax.*;
import org.codehaus.groovy.ast.builder.*;
import org.effortless.core.ClassNodeHelper;
import org.effortless.core.ModelException;
import org.effortless.gen.GClass;
import org.effortless.gen.GenContext;
import org.effortless.model.FileEntity;
import org.effortless.model.FileEntityTuplizer;
import org.effortless.model.StartupDb;
import org.objectweb.asm.Opcodes;

public class FileFields {

	public static List<FieldNode> listFiles (ClassNode clazz, SourceUnit sourceUnit) {
		List<FieldNode> result = null;
		if (clazz != null) {
			result = new ArrayList<FieldNode>();
			List<FieldNode> fields = clazz.getFields();
			for (FieldNode field : fields) {
				if (checkFileField(clazz, field)) {
					result.add(field);
				}
			}
		}
		return result;
	}
	
	public static boolean checkFileField(ClassNode clazz, FieldNode field) {
		boolean result = false;
		if (clazz != null && field != null) {
			ClassNode fieldClass = field.getType();
			if (fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(File.class)) || fieldClass.isDerivedFrom(ClassNodeHelper.toClassNode(org.effortless.model.FileEntity.class))) {
				result = true;
			}
		}
		return result;
	}

	public static void processField (ClassNode clazz, FieldNode field, SourceUnit sourceUnit) {
		String keyFileEntity = clazz.getName() + "." + FileEntity.KEY_CLASS_NEEDS;
		GenContext.set(keyFileEntity, Boolean.TRUE);
		ClassNode fileClazz = GClass.tryNeedsNewExternalEntity(clazz, sourceUnit, FileEntity.class, FileEntity.KEY_CLASS_NEEDS, FileEntity.KEY_APP_NEEDS, FileEntityTuplizer.class);//EntityClassTransformation.tryNeedsFileEntity(clazz, sourceUnit);
		alterField(clazz, field, fileClazz);
		UserFields.addInitiate(clazz, field);
		MethodNode getter = UserFields.addGetter(clazz, field, false);
		addAnnotationGetterEntity(clazz, field, getter);
		addGetters(clazz, field);
		UserFields.addSetter(clazz, field);
		addSetters(clazz, field);
	}

	//	protected FileEntity fichero;
	protected static void alterField (ClassNode clazz, FieldNode field, ClassNode fileClazz) {
		field.setModifiers(Opcodes.ACC_PROTECTED);
		field.setType(fileClazz);
	}
	
	protected static void addGetters (ClassNode clazz, FieldNode field) {
		addGetterFile(clazz, field);
		addGetterPath(clazz, field);
	}
	
	/*
	 * 
	@javax.persistence.ManyToOne(cascade = {javax.persistence.CascadeType.ALL})
	@javax.persistence.JoinColumn(name="FICHERO")
	public FileEntity getFicheroEntity() {
		return this.fichero;
	}
	
	 * 
	 * 
	 */
	protected static void addAnnotationGetterEntity (ClassNode clazz, FieldNode field, MethodNode method) {
//		UserFields.addLazyAnnotation(clazz, field, method);
		
		AnnotationNode annMany = new AnnotationNode(new ClassNode(javax.persistence.ManyToOne.class));
		annMany.setMember("cascade", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.CascadeType.class)), new ConstantExpression("ALL")));
		annMany.setMember("fetch", new PropertyExpression(new ClassExpression(new ClassNode(javax.persistence.FetchType.class)), new ConstantExpression("LAZY")));
		method.addAnnotation(annMany);
		
		AnnotationNode annColumn = new AnnotationNode(new ClassNode(javax.persistence.JoinColumn.class));
		annColumn.setMember("name", new ConstantExpression(field.getName().toUpperCase()));
		method.addAnnotation(annColumn);
	}

	/*
	 * 
	@javax.persistence.Transient
	public java.io.File getFicheroFile () {
		FileEntity fichero = getFicheroEntity();
		return (fichero != null ? fichero.getContent() : null);
	}
	 * 
	 */
	protected static MethodNode addGetterFile (ClassNode clazz, FieldNode field) {
		MethodNode result = null;
		String methodName = BaseFields.getGetterName(field) + "File";
		
		Statement code = null;

		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, new ClassNode(java.io.File.class), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		
		AnnotationNode annTransient = new AnnotationNode(new ClassNode(javax.persistence.Transient.class));
		method.addAnnotation(annTransient);
		
		clazz.addMethod(method);
		result = method;
		return result;
	}

	/*
	 * 
	@javax.persistence.Transient
	public String getFicheroPath () {
		FileEntity fichero = getFicheroEntity();
		return (fichero != null ? fichero.getPath() : null);
	}
	 * 
	 */
	protected static MethodNode addGetterPath (ClassNode clazz, FieldNode field) {
		MethodNode result = null;
		String methodName = BaseFields.getGetterName(field) + "Path";
		
		Statement code = null;

		MethodNode method = new MethodNode(methodName, Opcodes.ACC_PUBLIC, ClassHelper.STRING_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
		
		AnnotationNode annTransient = new AnnotationNode(new ClassNode(javax.persistence.Transient.class));
		method.addAnnotation(annTransient);
		
		clazz.addMethod(method);
		result = method;
		return result;
	}
	
	
	
	


	
	protected static void addSetters (ClassNode clazz, FieldNode field) {
		addSetterFile(clazz, field);
		addSetterPath(clazz, field);
	}
	
	/*
	 * 
	public void setFichero (java.io.File newValue) {
		if (newValue != null) {
			FileEntity entity = getFichero();
			entity = (entity != null ? entity : new FileEntity());
			entity.setContent(newValue);
			setFichero(entity);
		}
		else {
			setFichero((FileEntity)null);
		}
	}
	 * 
	 * 
	 */
	protected static MethodNode addSetterFile (ClassNode clazz, FieldNode field) {
		MethodNode result = null;
		String setterName = BaseFields.getSetterName(field);
		String getterName = BaseFields.getGetterName(field);
		
		ClassNode fileClassNode = new ClassNode(java.io.File.class);
		ClassNode fileEntityClassNode = field.getType();
		
		VariableExpression varNewValue = new VariableExpression("newValue", fileClassNode);

		BinaryExpression conditionIf = new BinaryExpression(varNewValue, Token.newSymbol(Types.COMPARE_NOT_EQUAL, -1, -1), ConstantExpression.NULL);
		BlockStatement ifContent = new BlockStatement();
		BlockStatement elseContent = new BlockStatement();
		IfStatement code = new IfStatement(new BooleanExpression(conditionIf), ifContent, elseContent);

		// FileEntity _entity = getFichero();
		MethodCallExpression callGetFichero = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, getterName, new ArgumentListExpression());
		VariableExpression varEntity = new VariableExpression("_entity", fileEntityClassNode);
		DeclarationExpression assignEntity = new DeclarationExpression(varEntity, Token.newSymbol(Types.ASSIGN, -1, -1), callGetFichero);
		ifContent.addStatement(new ExpressionStatement(assignEntity));
		
		//entity = (entity != null ? entity : new FileEntity());
		BinaryExpression conditionEntityNotNull = new BinaryExpression(varEntity, Token.newSymbol(Types.COMPARE_NOT_EQUAL, -1, -1), ConstantExpression.NULL);
		ConstructorCallExpression newFileEntity = new ConstructorCallExpression(fileEntityClassNode, new ArgumentListExpression());
		TernaryExpression checkCreateEntity = new TernaryExpression(new BooleanExpression(conditionEntityNotNull), varEntity, newFileEntity);
		BinaryExpression assignCheckCreateEntity = new BinaryExpression(varEntity, Token.newSymbol(Types.ASSIGN, -1, -1), checkCreateEntity);
		ifContent.addStatement(new ExpressionStatement(assignCheckCreateEntity));

		//_entity.setContent(newValue);
		MethodCallExpression callSetContent = new MethodCallExpression(varEntity, "setContent", new ArgumentListExpression(new Expression[] {varNewValue}));
		ifContent.addStatement(new ExpressionStatement(callSetContent));
		
		//setFichero(_entity);
		MethodCallExpression callSetFichero = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, setterName, new ArgumentListExpression(new Expression[] {varEntity}));
		ifContent.addStatement(new ExpressionStatement(callSetFichero));

		//setFichero((FileEntity)null);
		MethodCallExpression callSetFicheroNull = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, setterName, new ArgumentListExpression(new Expression[] {new CastExpression(fileEntityClassNode, ConstantExpression.NULL)}));
		elseContent.addStatement(new ExpressionStatement(callSetFicheroNull));
		
		Parameter[] setterParameterTypes = new Parameter[] {new Parameter(fileClassNode, "newValue")};
		MethodNode method = new MethodNode(setterName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, setterParameterTypes, ClassNode.EMPTY_ARRAY, code);
		
		clazz.addMethod(method);
		result = method;
		return result;
	}
	
	/*
	 * 
	public void setFichero (String newValue) {
		setFichero(new java.io.File(newValue));
	}
	 * 
	 * 
	 */
	protected static MethodNode addSetterPath (ClassNode clazz, FieldNode field) {
		MethodNode result = null;
		String setterName = BaseFields.getSetterName(field);
		
		ClassNode fileClassNode = new ClassNode(java.io.File.class);
		VariableExpression varNewValue = new VariableExpression("newValue", ClassHelper.STRING_TYPE);

		//		setFichero(new java.io.File(newValue));
		ConstructorCallExpression newFile = new ConstructorCallExpression(fileClassNode, new ArgumentListExpression(new Expression[] {varNewValue}));
		MethodCallExpression callSetFichero = new MethodCallExpression(VariableExpression.THIS_EXPRESSION, setterName, new ArgumentListExpression(new Expression[] {newFile}));
		ExpressionStatement code = new ExpressionStatement(callSetFichero);

		Parameter[] setterParameterTypes = new Parameter[] {new Parameter(ClassHelper.STRING_TYPE, "newValue")};
		MethodNode method = new MethodNode(setterName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, setterParameterTypes, ClassNode.EMPTY_ARRAY, code);
		
		clazz.addMethod(method);
		result = method;
		return result;
	}
	
}
