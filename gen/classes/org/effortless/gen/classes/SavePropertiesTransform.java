package org.effortless.gen.classes;

import java.util.List;


import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.FieldNode;
import org.effortless.gen.GClass;
import org.effortless.gen.ClassTransform;
import org.effortless.gen.GMethod;
import org.effortless.gen.fields.FileFields;

/**
 *
 * Implements


	protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
		boolean result = true;
		
		if (this.fileProperty != null) {
			this.fileProperty.persist()
		}

		return result;
	}

 * 
 * 
 * @author jesus
 *
 */
public class SavePropertiesTransform extends Object implements ClassTransform {

	public void process (GClass cg) {
		if (cg != null) {
			
			List<FieldNode> fields = FileFields.listFiles(cg.getClassNode(), cg.getSourceUnit());
			
			if (fields != null && fields.size() > 0) {
				GMethod mg = null;
				
				//protected boolean doSavePreviousProperties(String properties, boolean validate, boolean create) {// throws ModelException {
				mg = cg.addMethod("doSavePreviousProperties").setProtected(true).setReturnType(ClassHelper.boolean_TYPE).addParameter(String.class, "properties").addParameter(ClassHelper.boolean_TYPE, "validate").addParameter(ClassHelper.boolean_TYPE, "create");
				mg.declVariable(ClassHelper.boolean_TYPE, "result", mg.cteTrue());//boolean result = true;
				
				for (FieldNode field : fields) {
					GMethod ifCode = mg.newBlock();
					mg.addIf(mg.notNull(mg.field(field)), ifCode);//if (this.fileProperty != null) {
					ifCode.add(ifCode.call(ifCode.field(field), "persist"));//this.fileProperty.persist()
				}
				mg.addReturn(mg.var("result"));//return result
			}
		}
	}
	
	
	
}
