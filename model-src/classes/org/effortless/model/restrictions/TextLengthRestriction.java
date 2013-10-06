package org.effortless.model.restrictions;

public class TextLengthRestriction extends PropertyRestriction {

	public TextLengthRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinLength();
		initiateMaxLength();
	}
	
	protected Integer minLength;
	
	protected void initiateMinLength () {
		this.minLength = null;
	}
	
	public Integer getMinLength () {
		return this.minLength;
	}
	
	public void setMinLength (Integer newValue) {
		this.minLength = newValue;
	}
	
	protected Integer maxLength;
	
	protected void initiateMaxLength () {
		this.maxLength = null;
	}
	
	public Integer getMaxLength () {
		return this.maxLength;
	}
	
	public void setMaxLength (Integer newValue) {
		this.maxLength = newValue;
	}
	
	protected void doCheck (Object data, Object property) {
		if (!(property instanceof String)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			String value = (String)property;
			if (value != null) {
				int length = value.length();
				Integer minLength = getMinLength();
				if (minLength != null) {
					int _minLength = minLength.intValue();
					if (length < _minLength) {
						throwError(data, property, "min");
					}
				}
				Integer maxLength = getMaxLength();
				if (maxLength != null) {
					int _maxLength = maxLength.intValue();
					if (length > _maxLength) {
						throwError(data, property, "max");
					}
				}
			}
		}
	}
	
}
