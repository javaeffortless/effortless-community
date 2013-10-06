package org.effortless.io.decoders;

import java.text.ParseException;

import org.effortless.core.NumberUtils;

public class IntegerDecoder extends AbstractDecoder<Integer> {

	public IntegerDecoder () {
		super();
	}
	
	public Integer decode(String value) {
		Integer result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			try {
				result = NumberUtils.toInteger(value);
			} catch (ParseException e) {
			}
		}
		return result;
	}

}
