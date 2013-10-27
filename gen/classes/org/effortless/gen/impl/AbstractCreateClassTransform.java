package org.effortless.gen.impl;

import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.effortless.core.ClassNodeHelper;
import org.effortless.gen.Transform;
import org.effortless.gen.GClass;
import org.effortless.gen.GNode;

public abstract class AbstractCreateClassTransform<T extends GNode> extends Object implements Transform<T>  {

	public AbstractCreateClassTransform () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateResult();
	}
	
	protected GClass result;
	
	protected void initiateResult () {
		this.result = null;
	}
	
	public GClass getResult () {
		return this.result;
	}
	
	protected void setResult (GClass newValue) {
		this.result = newValue;
	}
	
	public abstract void process (T field);

	protected GClass tryNeedsNewExternalEntity(GClass clazz, ClassNode externalEntity, Class<?> tuplizer) {
		GClass result = null;

		result = addNewExternalClass(clazz, externalEntity);

		if (false && result != null && tuplizer != null) {
			AnnotationNode annTuplizer = new AnnotationNode(ClassNodeHelper.toClassNode(org.hibernate.annotations.Tuplizer.class));
			annTuplizer.setMember("impl", new ClassExpression(ClassNodeHelper.toClassNode(tuplizer)));
			result.addAnnotation(annTuplizer);
		}

		return result;
	}

	protected GClass addNewExternalClass (GClass clazz, ClassNode externClazz) {
		GClass result = null;
		
		ClassNode newClazz = new ClassNode(externClazz.getNameWithoutPackage(), externClazz.getModifiers(), externClazz);
		String newName = newNameExternalClass(clazz, externClazz);
		newClazz.setName(newName);
		clazz.getSourceUnit().getAST().addClass(newClazz);

		result = new GClass(newClazz);
		clazz.getApplication().addClass(result);
		
		return result;
	}
	
	protected String newNameExternalClass (GClass clazz, ClassNode externClazz) {
		String result = null;
		String packageName = clazz.getPackageName();
		if (!GClass.ONE_PACKAGE) {
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

}
