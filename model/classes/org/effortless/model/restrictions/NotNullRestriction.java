package org.effortless.model.restrictions;

public class NotNullRestriction extends PropertyRestriction {

	public NotNullRestriction () {
		super();
	}
	
	protected void doCheck (Object data, Object property) {
		if (property instanceof String && "".equals(property)) {
			throwError(data, property, "notnull");
		}
		else if (property == null) {
			throwError(data, property, "notnull");
		}
	}

}
