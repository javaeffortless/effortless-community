package org.effortless.model.restrictions;

public class CountRangeRestriction extends PropertyRestriction {

	public CountRangeRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinValue();
		initiateMaxValue();
	}

	protected Integer minValue;
	
	protected void initiateMinValue () {
		this.minValue = null;
	}
	
	public Integer getMinValue () {
		return this.minValue;
	}
	
	public void setMinValue (Integer newValue) {
		this.minValue = newValue;
	}
	
	protected Integer maxValue;
	
	protected void initiateMaxValue () {
		this.maxValue = null;
	}
	
	public Integer getMaxValue () {
		return this.maxValue;
	}
	
	public void setMaxValue (Integer newValue) {
		this.maxValue = newValue;
	}
	
	protected void doCheck (Object data, Object property) {
		if (!(property instanceof Integer)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Integer value = (Integer)property;
			if (value != null) {
				int _value = value.intValue();
				Integer minValue = getMinValue();
				if (minValue != null) {
					int _minValue = minValue.intValue();
					if (_value < _minValue) {
						throwError(data, property, "min");
					}
				}
				Integer maxValue = getMaxValue();
				if (maxValue != null) {
					int _maxValue = maxValue.intValue();
					if (_value > _maxValue) {
						throwError(data, property, "max");
					}
				}
			}
		}
	}
	
}
