package org.effortless.io.decoders;

import java.text.ParseException;
import java.util.Date;

import org.effortless.core.DateUtils;

public class DateDecoder extends AbstractDecoder<Date> {

	public DateDecoder () {
		super();
	}
	
	public Date decode(String value) {
		Date result = null;
		if (value != null) {
			try {
				result = DateUtils.parse(value);
			} catch (ParseException e) {
			}
		}
		return result;
	}

}
