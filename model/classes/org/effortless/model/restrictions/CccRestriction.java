package org.effortless.model.restrictions;

public class CccRestriction extends PropertyRestriction {

	public CccRestriction () {
		super();
	}
	
	protected void doCheck (Object data, Object property) {
		if (!(property instanceof String)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			String value = (String)property;
			if (value != null) {
				value = CccValidator.removeSomeChars(value);
				if (!CccValidator.check(value)) {
					throwError(data, property, "regex");
				}
			}
		}
	}
	
}
