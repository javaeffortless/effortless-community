package org.effortless.ui.impl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;

public class Components extends Object {

	protected Components () {
		super();
		initiate();
	}
	
	protected void initiate () {
	}

	public static List<Component> getSiblings (Component cmp, boolean sameType) {
		List<Component> result = null;
		if (cmp != null) {
			Component parent = cmp.getParent();
			List<Component> children = parent.getChildren();
			result = new ArrayList<Component>();
			if (children != null) {
				int length = children.size();
				for (int i = 0; i < length; i++) {
					Component child = children.get(i);
					if (child != null && child != cmp) {
						result.add(child);
					}
				}
//				for (Component child : children) {
//					result.add(child);
//				}
//				result.remove(cmp);
			}
			result = (sameType ? onlySameType(cmp, result) : result);
		}
		return result;
	}

	public static List<Component> getSiblings (Component cmp) {
		return getSiblings(cmp, false);
	}

	public static <T extends Component> List<T> getSameSiblings (T cmp) {
		return (List<T>)getSiblings(cmp, true);
	}
	
	public static List<Component> onlySameType (Component cmp, List<Component> src) {
		List<Component> result = null;
		if (cmp != null && src != null) {
			result = new ArrayList<Component>();
			result.addAll(src);
			int length = result.size();
			Class clazz = cmp.getClass();
			for (int i = length - 1; i >= 0; i--) {
				Component item = result.get(i);
				Class itemClazz = item.getClass();
				boolean checkSameType = false;
				if (!itemClazz.isAssignableFrom(clazz)) {
					result.remove(i);
				}
			}
		}
		return result;
	}
	
	
	
}
