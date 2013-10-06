package org.effortless.core;

import java.text.ParseException;

public class NumberUtils {

	public static Double toDouble (String value) throws ParseException {
		Double result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			try {
				result = Double.valueOf(value);
			}
			catch (Throwable t) {
				throw new ParseException(value, 0);
			}
		}
		return result;
	}

	public static Integer toInteger(String value) throws ParseException {
		Integer result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			try {
				result = Integer.valueOf(value);
			}
			catch (Throwable t) {
				throw new ParseException(value, 0);
			}
		}
		return result;
	}

	public static Long toLong(String value) throws ParseException {
		Long result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			try {
				result = Long.valueOf(value);
			}
			catch (Throwable t) {
				throw new ParseException(value, 0);
			}
		}
		return result;
	}
	
}
