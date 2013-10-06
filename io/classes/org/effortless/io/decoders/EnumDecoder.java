package org.effortless.io.decoders;

import org.effortless.core.ClassUtils;

public class EnumDecoder<T extends Enum<T>> extends AbstractDecoder<T> {

	public EnumDecoder () {
		super();
	}
	
	public T decode(String value) {
		T result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			Class<T> enumType = _toEnumType();
			result = getEnumFromString(enumType, value);
		}
		return result;
	}
	
    protected Class<T> _toEnumType() {
    	Class<T> result = null;
        result = (Class<T>) ClassUtils.getTypeArguments(EnumDecoder.class, getClass()).get(0);
        return result;
    }
	
	protected static <T extends Enum<T>> T getEnumFromString(Class<T> enumType, String value) {
		T result = null;
		value = (value != null ? value.trim() : "");
	    if (enumType != null && value != null) {
	    	try {
	    		result = Enum.valueOf(enumType, value);
	    	}
	    	catch(IllegalArgumentException ex) {
	    	}
	    }
	    return result;
	}
	
}
