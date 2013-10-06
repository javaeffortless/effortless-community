package org.effortless.io.decoders;

import java.text.ParseException;

import org.effortless.core.NumberUtils;

public class DoubleDecoder extends AbstractDecoder<Double> {

	public DoubleDecoder () {
		super();
	}
	
	public Double decode(String value) {
		Double result = null;
		value = (value != null ? value.trim() : "");
		if (value.length() > 0) {
			try {
				result = NumberUtils.toDouble(value);
			} catch (ParseException e) {
			}
		}
		return result;
	}

}
