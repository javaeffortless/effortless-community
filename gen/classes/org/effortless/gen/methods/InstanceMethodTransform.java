package org.effortless.gen.methods;

import org.effortless.ann.InstanceMethod;
import org.effortless.core.StringUtils;
import org.effortless.gen.GAnnotation;
import org.effortless.gen.GClass;
import org.effortless.gen.GMethod;
import org.effortless.gen.Transform;

/**
 * 
 * 
 *   @InstanceMethod("2,4,6,8,10")
  void ingresar (Double cantidad);
  
     void ingresar2 () { ingresar(2.0); }
     void ingresar4 () { ingresar(4.0); }
     void ingresar6 () { ingresar(6.0); }
     void ingresar8 () { ingresar(8.0); }
     void ingresar10 () { ingresar(10.0); }
 * 
 * @author jesus
 *
 */
public class InstanceMethodTransform extends Object implements Transform<GMethod> {

	public InstanceMethodTransform () {
		super();
	}
	
	@Override
	public void process(GMethod node) {
		GAnnotation ann = node.getAnnotation(InstanceMethod.class);
		String annValue = StringUtils.forceNotNull((ann != null ? ann.getValue() : null));
		if (annValue.length() > 0) {
			String[] values = annValue.split(",");
			GClass clazz = node.getClassGen();
			String methodName = node.getName();
			for (String value : values) {
				value = StringUtils.forceNotNull(value);
				String newMethodName = methodName + adaptSuffixValue(value);
				GMethod m = clazz.addMethod(newMethodName).setPublic(true).setReturnType(node.getReturnType());
				Object defaultValue = Double.valueOf(value);
				m.add(m.call(m.cteThis(), methodName, m.cte(defaultValue)));
			}
		}
	}
	
	protected String adaptSuffixValue (String value) {
		String result = null;
		result = value;
		result = result.replaceAll("-", "N");
		result = result.replaceAll("\\+", "");
		return result;
	}

}
