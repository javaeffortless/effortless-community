package org.effortless.gen.fields;

import java.util.List;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.effortless.core.Collections;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GClass;
import org.effortless.gen.GField;
import org.effortless.gen.GMethod;
import org.effortless.gen.GenContext;
import org.effortless.gen.impl.CreateFileEntityTransform;
import org.effortless.model.FileEntity;
import org.objectweb.asm.Opcodes;

public abstract class AbstractPropertiesClassTransform extends Object implements ClassTransform {

	@Override
	public void process(GClass cg) {
		ClassNode clazz = cg.getClassNode();
		List<FieldNode> fields = clazz.getFields();
		for (FieldNode field : fields) {
			GField gField = new GField(cg, field);
			processField(gField);
		}
	}

	public void processField (GField field) {
		if (field.isString()) {
			textProcessField(field);
		}
		else if (field.isDate()) {
			dateProcessField(field);
		}
		else if (field.isBoolean()) {
			boolProcessField(field);
		}
		else if (field.isInteger()) {
			countProcessField(field);
		}
		else if (field.isDouble()) {
			numberProcessField(field);
		}
		else if (field.isEnum()) {
			enumProcessField(field);
		}
		else if (field.isFile()) {
			fileProcessField(field);
		}
		else if (field.isCollection()) {
			listProcessField(field);
		}
		else if (field.isList()) {
			listProcessField(field);
		}
		else {
			refProcessField(field);
		}
	}

	protected abstract void textProcessField (GField field);
	
	protected abstract void dateProcessField (GField field);
	
	protected abstract void boolProcessField (GField field);
	
	protected abstract void countProcessField (GField field);
	
	protected abstract void numberProcessField (GField field);
	
	protected abstract void enumProcessField (GField field);

	protected abstract void fileProcessField (GField field);
	
	protected abstract void listProcessField (GField field);
	
	protected abstract void refProcessField (GField field);
	
}
