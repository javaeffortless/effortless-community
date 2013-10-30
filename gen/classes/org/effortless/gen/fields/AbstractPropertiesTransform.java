package org.effortless.gen.fields;

import org.effortless.gen.Transform;
import org.effortless.gen.GField;
import org.effortless.model.FileEntity;

public abstract class AbstractPropertiesTransform extends Object implements Transform<GField> {

	@Override
	public void process(GField gField) {
		processField(gField);
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
		else if (field.isFile() || field.isType(FileEntity.class)) {
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
