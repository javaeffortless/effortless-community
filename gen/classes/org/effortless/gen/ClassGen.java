package org.effortless.gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.effortless.ann.Finder;
import org.effortless.core.DebugUtils;
import org.effortless.core.StringUtils;
import org.effortless.gen.fields.BaseFields;
import org.objectweb.asm.Opcodes;

public class ClassGen extends Object {

	protected ClassGen () {
		super();
		initiate();
	}
	
	protected void initiate () {
		this.clazz = null;
	}
	
	public ClassGen (String name, SourceUnit sourceUnit) {
		this();
		this.clazz = new ClassNode(name, Opcodes.ACC_PUBLIC, ClassHelper.OBJECT_TYPE);
		sourceUnit.getAST().addClass(this.clazz);
		this.sourceUnit = sourceUnit;
	}
	
	public ClassGen (ClassNode clazz) {
		this();
		this.clazz = clazz;
	}
	
	public ClassGen (ClassNode clazz, SourceUnit sourceUnit) {
		this();
		this.clazz = clazz;
		this.sourceUnit = sourceUnit;
	}
	
	protected SourceUnit sourceUnit;
	
	public SourceUnit getSourceUnit () {
		return this.sourceUnit;
	}
	
	protected ClassNode clazz;

	public ClassNode getClassNode () {
		return this.clazz;
	}
	
	public FieldNode addField (Class<?> type, String name) {
		return addField(ClassNodeHelper.toClassNode(type), name);
	}
	
	public FieldNode addField (ClassNode type, String name) {
		FieldNode result = null;
		result = new FieldNode(name, Opcodes.ACC_PROTECTED, type, this.clazz, ConstantExpression.NULL);
		this.clazz.addField(result);
//		FieldExpression fieldExpression = new FieldExpression(field);
		return result;
	}

	public void removeField (String name) {
		this.clazz.removeField(name);
	}
	
	
	
	public ClassGen setPublic (boolean newValue) {
		if (newValue) {
			this.clazz.setModifiers(Opcodes.ACC_PUBLIC);
		}
		return this;
	}
	
	public ClassGen setProtected (boolean newValue) {
		if (newValue) {
			this.clazz.setModifiers(Opcodes.ACC_PROTECTED);
		}
		return this;
	}
	
	public ClassGen setPrivate (boolean newValue) {
		if (newValue) {
			this.clazz.setModifiers(Opcodes.ACC_PRIVATE);
		}
		return this;
	}
	
	public ClassNode createGenericType (Class<?> clazz, Class<?>[] types) {
		return createGenericType(ClassNodeHelper.toClassNode(clazz), ClassNodeHelper.toClassNodes(types));
	}
	
	public ClassNode createGenericType (Class<?> clazz, Class<?> type) {
		return createGenericType(ClassNodeHelper.toClassNode(clazz), ClassNodeHelper.toClassNodes(type));
	}
	
	public ClassNode createGenericType (ClassNode clazz, Class<?>[] types) {
		return createGenericType(clazz, ClassNodeHelper.toClassNodes(types));
	}
	
	public ClassNode createGenericType (Class<?> clazz, ClassNode type) {
		return createGenericType(ClassNodeHelper.toClassNode(clazz), ClassNodeHelper.toClassNodes(type));
	}
	
	public ClassNode createGenericType (ClassNode clazz, ClassNode[] types) {
		ClassNode result = null;
//		GenericsUtils.
		result = clazz.getPlainNodeReference();
		result.setGenericsPlaceHolder(true);
//		result = new ClassNode(clazz.getTypeClass());
//		result.setRedirect(clazz);
//		result = clazz;
//		result.setUsingGenerics(true);
		if (types != null) {
			GenericsType[] genericsTypes = new GenericsType[types.length];
			int idx = 0;
			for (ClassNode type : types) {
				GenericsType item = new GenericsType(type);
				genericsTypes[idx] = item;
				idx += 1;
			}
			result.setGenericsTypes(genericsTypes);
		}
		return result;
	}
	
	public ClassNode createGenericType (ClassNode clazz, ClassNode type) {
		return createGenericType(clazz, ClassNodeHelper.toClassNodes(type));
	}
	
	
	
	public ClassGen setSuperClass (Class<?> superClass) {
		return setSuperClass(ClassNodeHelper.toClassNode(superClass));
	}
	
	public ClassGen setSuperClass (ClassNode superClass) {
		if (superClass != null) {
			this.clazz.setSuperClass(superClass);
		}
		return this;
	}

	public ClassGen addInterface (Class<?> iface) {
		return addInterface(ClassNodeHelper.toClassNode(iface));
	}
	
	public ClassGen addInterface (ClassNode iface) {
		if (iface != null) {
			ClassNode[] interfaces = this.clazz.getInterfaces();
			List<ClassNode> list = new ArrayList<ClassNode>();
			if (interfaces != null && interfaces.length > 0) {
				for (ClassNode clazz : interfaces) {
					list.add(clazz);
				}
			}
			list.add(iface);
			interfaces = list.toArray(new ClassNode[0]);
			this.clazz.setInterfaces(interfaces);
		}
		return this;
	}

	public ClassGen addInterfaces (Class<?>... ifaces) {
		return addInterfaces(ClassNodeHelper.toClassNodes(ifaces));
	}
	
	public ClassGen addInterfaces (ClassNode... ifaces) {
		if (ifaces != null) {
			ClassNode[] interfaces = this.clazz.getInterfaces();
			List<ClassNode> list = new ArrayList<ClassNode>();
			if (interfaces != null && interfaces.length > 0) {
				for (ClassNode clazz : interfaces) {
					list.add(clazz);
				}
			}
			for (ClassNode iface : ifaces) {
				list.add(iface);
			}
			interfaces = list.toArray(new ClassNode[0]);
			this.clazz.setInterfaces(interfaces);
		}
		return this;
	}
	
	public ClassGen addInnerClass (String name) {
		ClassGen result = null;
		InnerClassNode inner = new InnerClassNode(this.clazz, name, Opcodes.ACC_PUBLIC, ClassNodeHelper.toClassNode(Object.class));
		result = new ClassGen(inner, this.sourceUnit);
		this.sourceUnit.getAST().addClass(inner);
		return result;
	}

	public int getInnerClassesSize () {
		int result = 0;
		Iterator<InnerClassNode> inners = this.clazz.getInnerClasses();
		if (inners != null) {
			while (inners.hasNext()) {
				inners.next();
				result += 1;
			}
		}
		return result;
	}
	
//	public ClassGen addAnonymousClass (Class<?> superClass) {
//		return addAnonymousClass(ClassNodeHelper.toClassNode(superClass));
//	}
//	
//	public ClassGen addAnonymousClass (ClassNode superClass) {
//		ClassGen result = null;
//		String suffix = "" + (getInnerClassesSize() + 1);
////		String suffix = System.currentTimeMillis();
////		String name = this.clazz.getName() + "$" + suffix;
////		String name = suffix;
//		String name = this.clazz.getNameWithoutPackage() + "$" + suffix;
//		InnerClassNode inner = new InnerClassNode(this.clazz, name, Opcodes.ACC_PUBLIC, superClass);
//		inner.setAnonymous(true);
//		result = new ClassGen(inner, this.sourceUnit);
//		this.sourceUnit.getAST().addClass(inner);
//		return result;
//	}
//	
	public MethodGen addSimpleGetter (String name) {
		MethodGen result = null;
		if (true) {
			String getterName = BaseFields.getGetterName(name);
			FieldNode field = this.clazz.getField(name);
			MethodGen mg = this.addMethod(getterName).setReturnType(field.getType()).setPublic(true);
			String fName = StringUtils.uncapFirst(name);
			mg.gPrintln(mg.call("getClass"));
			mg.gPrintln("printing get filter");
			mg.gPrintln(mg.field(fName));
			mg.add(mg.callStatic(DebugUtils.class, "inspect", mg.field(fName)));
			mg.addReturn(mg.field(fName));
			
			result = mg;
		}
		else {
			FieldNode field = this.clazz.getField(name);
			FieldExpression fieldExpression = new FieldExpression(field);

			ReturnStatement getterCode = new ReturnStatement(fieldExpression);
			String getterName = BaseFields.getGetterName(field);
			MethodNode getter = new MethodNode(getterName, Opcodes.ACC_PUBLIC, field.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, getterCode);
			this.clazz.addMethod(getter);
		}
		
		return result;
	}
	
	public ClassGen addSimpleSetter (String name) {
		
		if (true) {
			FieldNode field = this.clazz.getField(name);
			String setterName = BaseFields.getSetterName(name);
			MethodGen mg = this.addMethod(setterName).setPublic(true).addParameter(field.getType(), "newValue");
			mg.gPrintln(mg.var("newValue"));
			mg.add(mg.assign(mg.field(name), mg.var("newValue")));
		}
		else {
			FieldNode field = this.clazz.getField(name);
			FieldExpression fieldExpression = new FieldExpression(field);
	
			VariableExpression varNewValue = new VariableExpression("newValue", field.getType());
			ExpressionStatement setterCode = new ExpressionStatement(new BinaryExpression(fieldExpression, Token.newSymbol(Types.ASSIGN, -1, -1), varNewValue));
			String setterName = BaseFields.getSetterName(field);
			MethodNode setter = new MethodNode(setterName, Opcodes.ACC_PUBLIC, ClassHelper.VOID_TYPE, new Parameter[] {new Parameter(field.getType(), "newValue")}, ClassNode.EMPTY_ARRAY, setterCode);
			this.clazz.addMethod(setter);
		}
		
		return this;
	}

	public MethodGen addConstructor () {
		MethodGen result = null;
		ConstructorNode constructor = new ConstructorNode(Opcodes.ACC_PUBLIC, EmptyStatement.INSTANCE);
		this.clazz.addConstructor(constructor);
		result = new MethodGen(constructor, this);
		return result;
	}
	
	public MethodGen addMethod (String name) {
		MethodGen result = null;
		result = new MethodGen(name, this);
		return result;
	}

	public MethodGen addSimpleProperty (Class<?> type, String name) {
		return addSimpleProperty(ClassNodeHelper.toClassNode(type), name);
	}
	
	public MethodGen addSimpleProperty (ClassNode type, String name) {
		return addSimpleProperty(type, name, false);
	}

	public MethodGen addSimpleProperty (Class<?> type, String name, boolean readonly) {
		return addSimpleProperty(ClassNodeHelper.toClassNode(type), name, readonly);
	}
	
	public MethodGen addSimpleProperty (ClassNode type, String name, boolean readonly) {
		MethodGen result = null;
		this.addField(type, name);
		result = this.addSimpleGetter(name);
		if (!readonly) {
			this.addSimpleSetter(name);
		}
		return result;
	}


	public String queryAnnotation(Class<?> annotation, String member) {
		return queryAnnotation(this.clazz, ClassNodeHelper.toClassNode(annotation), member, null);
	}
	
	public String queryAnnotation(ClassNode annotation, String member) {
		return queryAnnotation(this.clazz, annotation, member, null);
	}
	
	public String queryAnnotation(Class<?> annotation, String member, String defaultValue) {
		return queryAnnotation(this.clazz, ClassNodeHelper.toClassNode(annotation), member, defaultValue);
	}

	public String queryAnnotation(ClassNode annotation, String member, String defaultValue) {
		return queryAnnotation(this.clazz, annotation, member, defaultValue);
	}

	public String queryAnnotation(ClassNode clazz, Class<?> annotation, String member) {
		return queryAnnotation(clazz, ClassNodeHelper.toClassNode(annotation), member, null);
	}

	public String queryAnnotation(ClassNode clazz, ClassNode annotation, String member) {
		return queryAnnotation(clazz, annotation, member, null);
	}

	public String queryAnnotation(ClassNode clazz, Class<?> annotation, String member, String defaultValue) {
		return queryAnnotation(clazz, ClassNodeHelper.toClassNode(annotation), member, defaultValue);
	}
	
	public String queryAnnotation(ClassNode clazz, ClassNode annotation, String member, String defaultValue) {
		String result = defaultValue;
		
		List<AnnotationNode> annotations = clazz.getAnnotations(annotation);
		if (annotations != null && annotations.size() > 0) {
			AnnotationNode ann = annotations.get(0);
			result = ann.getMember(member).getText();
		}
		
		return result;
	}

	public ClassGen addAnnotation (Class<?> annotation) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation));
	}
	
	
	public ClassGen addAnnotation (ClassNode annotation) {
		AnnotationNode ann = new AnnotationNode(annotation);
		this.clazz.addAnnotation(ann);
		return this;
	}

	public ClassGen addAnnotation (Class<?> annotation, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public ClassGen addAnnotation (ClassNode annotation, String value) {
		return addAnnotation(annotation, "value", value);
	}

	public ClassGen addAnnotation (Class<?> annotation, String property, String value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}

	public ClassGen addAnnotation (Class<?> annotation, String property, boolean value) {
		return addAnnotation(ClassNodeHelper.toClassNode(annotation), property, (value ? ConstantExpression.PRIM_TRUE : ConstantExpression.PRIM_FALSE));
	}

	
	
	public ClassGen addAnnotation (ClassNode annotation, String property, String value) {
		return addAnnotation(annotation, property, new ConstantExpression(value));
	}

	public ClassGen addAnnotation (ClassNode annotation, String property, Expression expr) {
		AnnotationNode ann = new AnnotationNode(annotation);
		ann.setMember(property, expr);
		this.clazz.addAnnotation(ann);
		return this;
	}

	
	
	
	
	
	
	
	
	
	
	
	public AnnotationNode createAnnotation (Class<?> annotation) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation));
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation) {
		AnnotationNode result = new AnnotationNode(annotation);
		return result;
	}

	public AnnotationNode createAnnotation (Class<?> annotation, String value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public AnnotationNode createAnnotation (Class<?> annotation, Expression value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), "value", value);
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation, String value) {
		return createAnnotation(annotation, "value", value);
	}

	public AnnotationNode createAnnotation (Class<?> annotation, String property, String value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}

	public AnnotationNode createAnnotation (ClassNode annotation, String property, String value) {
		return createAnnotation(annotation, property, new ConstantExpression(value));
	}

	public AnnotationNode createAnnotation (Class<?> annotation, String property, Expression value) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), property, value);
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation, String property, Expression value) {
		AnnotationNode result = new AnnotationNode(annotation);
		result.setMember(property, value);
		return result;
	}
	
	public AnnotationNode createAnnotation (Class<?> annotation, String[] properties, Expression... values) {
		return createAnnotation(ClassNodeHelper.toClassNode(annotation), properties, values);
	}
	
	public AnnotationNode createAnnotation (ClassNode annotation, String[] properties, Expression... values) {
		AnnotationNode result = new AnnotationNode(annotation);
		int length = (properties != null ? properties.length : 0);
		for (int i = 0; i < length; i++) {
			result.setMember(properties[i], values[i]);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ClassGen addCte(Class<?> type, String name, Object value) {
		return addCte(ClassNodeHelper.toClassNode(type), name, value);
	}
	
	
	/*

				protected static final String MODULE_SORTEOS = "sorteos";


	 */
	public ClassGen addCte(ClassNode type, String name, Object value) {
		FieldNode field = new FieldNode(name, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL, type, this.clazz, new ConstantExpression(value));
		this.clazz.addField(field);
		return this;
	}
	
	
	
}
