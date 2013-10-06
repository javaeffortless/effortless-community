package org.effortless.io.decoders;

import java.text.ParseException;

import org.effortless.core.NumberUtils;

public class LongDecoder extends AbstractDecoder<Long> {

	public LongDecoder () {
		super();
	}
	
	public Long decode(String value) {
		Long result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			try {
				result = NumberUtils.toLong(value);
			} catch (ParseException e) {
			}
		}
		return result;
	}

}
