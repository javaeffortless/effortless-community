package org.effortless.io.decoders;

import org.effortless.core.Collections;

public class BooleanDecoder extends AbstractDecoder<Boolean> {

	public BooleanDecoder () {
		super();
	}
	
	protected static final String[] TRUE_VALUES = {"true", "yes", "y", "enabled", "on"};

	protected static final String[] FALSE_VALUES = {"false", "no", "n", "disabled", "off"};
	
	public Boolean decode(String value) {
		Boolean result = null;
		
		value = (value != null ? value.trim().toLowerCase() : "");
		
		if (value.length() > 0) {
			boolean flagTrue = Collections.contains(TRUE_VALUES, value);
			boolean flagFalse = (flagTrue ? false : Collections.contains(FALSE_VALUES, value));
			result = (flagTrue ? Boolean.TRUE : (flagFalse ? Boolean.FALSE : null));
		}
		
		return result;
	}

}
