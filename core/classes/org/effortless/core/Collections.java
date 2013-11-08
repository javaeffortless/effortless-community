package org.effortless.core;

import java.util.ArrayList;
import java.util.List;

public class Collections {

	public static void addAll (List target, List toAdd) {
		addAll(target, toAdd, false);
	}
	
	public static void addAll (List target, List toAdd, boolean allowRepeat) {
		if (toAdd != null && target != null) {
			for (Object item : toAdd) {
				if (item != null && (allowRepeat || !target.contains(item))) {
					target.add(item);
				}
			}
		}
	}
	
	public static <T> List<T> asList (T[] array) {
		List<T> result = null;
		if (array != null) {
			result = new ArrayList<T>();
			int length = array.length;
			for (int i = 0; i < length; i++) {
				result.add(array[i]);
			}
		}
		return result;
	}

	public static boolean like (String[] values, String value) {
		return Collections.like(values, value, true);
	}
	
	public static boolean contains (String[] values, String value) {
		return Collections.contains(values, value, true);
	}
	
	public static boolean like (String[] values, String value, boolean insensitive) {
		boolean result = false;
		value = (value != null && insensitive ? value.trim().toLowerCase() : value);
		int length = (values != null ? values.length : 0);
		for (int i = 0; i < length; i++) {
			String item = values[i];
			item = (item != null && insensitive ? item.trim().toLowerCase() : item);
			if ((item == null && value == null) || (value != null && value.contains(item))) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static boolean contains (String[] values, String value, boolean insensitive) {
		boolean result = false;
		value = (value != null && insensitive ? value.trim().toLowerCase() : value);
		int length = (values != null ? values.length : 0);
		for (int i = 0; i < length; i++) {
			String item = values[i];
			item = (item != null && insensitive ? item.trim().toLowerCase() : value);
			if ((item == null && value == null) || (value != null && value.equals(item))) {
				result = true;
				break;
			}
		}
		return result;
	}
	
}
