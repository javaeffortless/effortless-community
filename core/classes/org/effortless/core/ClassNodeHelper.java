package org.effortless.core;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;

public class ClassNodeHelper {

	protected static final Map<String, ClassNode> NODES = new HashMap<String, ClassNode>();
	static {
		NODES.put(String.class.getName(), ClassHelper.STRING_TYPE);
		NODES.put(Integer.class.getName(), ClassHelper.Integer_TYPE);
		NODES.put(Double.class.getName(), ClassHelper.Double_TYPE);
		NODES.put(Boolean.class.getName(), ClassHelper.Boolean_TYPE);
		NODES.put(Byte.class.getName(), ClassHelper.Byte_TYPE);
		NODES.put(Object.class.getName(), ClassHelper.OBJECT_TYPE);
	}
	
	public static ClassNode toClassNode (Class<?> clazz) {
		ClassNode result = null;
		if (clazz != null) {
			String key = clazz.getName();
			result = NODES.get(key);
			if (result == null) {
				result = new ClassNode(clazz);
				NODES.put(key, result);
			}
		}
		return result;
	}

	public static ClassNode[] toClassNodes (Class<?> clazz) {
		ClassNode[] result = null;
		result = (clazz != null ? new ClassNode[] {toClassNode(clazz)} : null);
		return result;
	}
	
	public static ClassNode[] toClassNodes (ClassNode clazz) {
		ClassNode[] result = null;
		result = (clazz != null ? new ClassNode[] {clazz} : null);
		return result;
	}
	
	public static ClassNode[] toClassNodes (Class<?>... ifaces) {
		ClassNode[] result = null;
		if (ifaces != null) {
			result = new ClassNode[ifaces.length];
			int idx = 0;
			for (Class<?> clazz : ifaces) {
				result[idx] = ClassNodeHelper.toClassNode(clazz);
				idx += 1;
			}
		}
		return result;
	}
	
	
}
