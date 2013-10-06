package org.effortless.model.restrictions;

public class NumberRangeRestriction extends PropertyRestriction {

	public NumberRangeRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinValue();
		initiateMaxValue();
		initiateExcludeMinValue();
		initiateExcludeMaxValue();
	}

	protected Double minValue;
	
	protected void initiateMinValue () {
		this.minValue = null;
	}
	
	public Double getMinValue () {
		return this.minValue;
	}
	
	public void setMinValue (Double newValue) {
		this.minValue = newValue;
	}
	
	protected Double maxValue;
	
	protected void initiateMaxValue () {
		this.maxValue = null;
	}
	
	public Double getMaxValue () {
		return this.maxValue;
	}
	
	public void setMaxValue (Double newValue) {
		this.maxValue = newValue;
	}
	
	protected Boolean excludeMinValue;
	
	protected void initiateExcludeMinValue () {
		this.excludeMinValue = new Boolean(false);
	}
	
	public Boolean getExcludeMinValue () {
		return this.excludeMinValue;
	}
	
	public void setExcludeMinValue (Boolean newValue) {
		this.excludeMinValue = newValue;
	}
	
	protected boolean checkExcludeMinValue () {
		return (getExcludeMinValue() != null ? getExcludeMinValue().booleanValue() : false);
	}
	
	protected Boolean excludeMaxValue;
	
	protected void initiateExcludeMaxValue () {
		this.excludeMaxValue = new Boolean(false);
	}
	
	public Boolean getExcludeMaxValue () {
		return this.excludeMaxValue;
	}
	
	public void setExcludeMaxValue (Boolean newValue) {
		this.excludeMaxValue = newValue;
	}
	
	protected boolean checkExcludeMaxValue () {
		return (getExcludeMaxValue() != null ? getExcludeMaxValue().booleanValue() : false);
	}
	
	protected void doCheck (Object data, Object property) {
		if (!(property instanceof Double)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Double value = (Double)property;
			if (value != null) {
				int _value = value.intValue();
				Double minValue = getMinValue();
				if (minValue != null) {
					double _minValue = minValue.doubleValue();
					if (_value < _minValue || (_value == _minValue && checkExcludeMinValue())) {
						throwError(data, property, "min");
					}
				}
				Double maxValue = getMaxValue();
				if (maxValue != null) {
					double _maxValue = maxValue.doubleValue();
					if (_value > _maxValue || (_value == _maxValue && checkExcludeMaxValue())) {
						throwError(data, property, "max");
					}
				}
			}
		}
	}
	
}
