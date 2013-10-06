package org.effortless.model.restrictions;

public class BoolTrueRestriction extends PropertyRestriction {

	public BoolTrueRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiatePivotProperty();
	}
	
	protected String pivotProperty;
	
	protected void initiatePivotProperty () {
		this.pivotProperty = null;
	}
	
	public String getPivotProperty () {
		return this.pivotProperty;
	}
	
	public void setPivotProperty (String newValue) {
		this.pivotProperty = newValue;
	}
	
	protected void doCheck(Object data, Object property) {
		if (!(property instanceof Boolean)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Boolean value = (Boolean)property;
			if (value == null || value.booleanValue() == false) {
				throwError(data, property, "bool.true");
			}
		}
	}

}
