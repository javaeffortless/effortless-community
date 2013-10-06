package org.effortless.model.restrictions;

import java.sql.Time;

public class TimeRangeRestriction extends PropertyRestriction {

	public TimeRangeRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinValue();
		initiateMaxValue();
	}
	
	protected Time minValue;
	
	protected void initiateMinValue () {
		this.minValue = null;
	}
	
	public Time getMinValue () {
		return this.minValue;
	}
	
	public void setMinValue (Time newValue) {
		this.minValue = newValue;
	}
	
	protected Time maxValue;
	
	protected void initiateMaxValue () {
		this.maxValue = null;
	}
	
	public Time getMaxValue () {
		return this.maxValue;
	}
	
	public void setMaxValue (Time newValue) {
		this.maxValue = newValue;
	}
	
	protected void doCheck(Object data, Object property) {
		if (!(property instanceof Time)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Time value = (Time)property;
			if (value != null) {
				long _value = value.getTime();
				
				Time maxValue = getMaxValue();
				maxValue = (maxValue != null ? maxValue : null);
				if (maxValue != null && _value > maxValue.getTime()) {
					throwError(data, property, "max");
				}
				
				Time minValue = getMinValue();
				minValue = (minValue != null ? minValue : null);
				if (minValue != null && _value < minValue.getTime()) {
					throwError(data, property, "min");
				}
			}
		}
	}

}
