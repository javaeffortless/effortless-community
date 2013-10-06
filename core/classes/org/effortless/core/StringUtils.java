package org.effortless.core;

public class StringUtils {

	public static String removeWords (String text, String toRemove, String separator) {
		String result = null;
		result = text;
		int length = (toRemove != null ? toRemove.length() : 0);
		if (text != null && length > 0) {
			int startIdx = text.indexOf(toRemove);
			if (startIdx > -1) {
				int endIdx = startIdx + length;
				result = text.substring(0, startIdx) + text.substring(endIdx);
			}
		}
		return result;
	}
	
	public static String capFirst (String text) {
		String result = null;
		result = (text != null && text.length() > 0 ? text.substring(0, 1).toUpperCase() + text.substring(1) : text);
		return result;
	}

	public static String partSplit(String text, String split, int index) {
		String result = null;
		if (text != null && split != null) {
			String[] array = text.split(split);
			result = (index >= 0 && index < array.length ? array[index] : null);
		}
		return result;
	}

	public static String uncapFirst(String text) {
		String result = null;
		result = (text != null && text.length() > 0 ? text.substring(0, 1).toLowerCase() + text.substring(1) : text);
		return result;
	}
	
	public static String lastPart (String text, String split) {
		String result = null;
		result = text;
		if (text != null && split != null) {
			String[] array = text.split(split);
			result = (array != null && array.length > 0 ? array[array.length - 1] : null);
		}
		return result;
	}
	
	public static String concat (String[] text, String separator) {
		String result = null;

		int length = (text != null ? text.length : 0);
		if (length > 0) {
			result = "";
			separator = (separator != null ? separator : "");
			for (int i = 0; i < length; i++) {
				String value = text[i];
				value = (value != null ? value : "");
				result += (result.length() > 0 && value.length() > 0 ? separator : "") + value;
			}
		}

		return result;
	}
	
	public static boolean isEmpty (String text) {
		return (text == null || "".equals(text));
	}

	public static String forceNotNull (String text, boolean trim) {
		String result = null;
		result = (text != null ? (trim ? text.trim() : text) : "");
		return result;
	}

	public static String forceNotNull (String text) {
		return forceNotNull(text, true);
	}

}
