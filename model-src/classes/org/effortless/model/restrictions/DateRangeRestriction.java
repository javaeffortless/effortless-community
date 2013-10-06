package org.effortless.model.restrictions;

import java.util.Date;

public class DateRangeRestriction extends PropertyRestriction {

	public DateRangeRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateMinValue();
		initiateMaxValue();
	}
	
	protected Date minValue;
	
	protected void initiateMinValue () {
		this.minValue = null;
	}
	
	public Date getMinValue () {
		return this.minValue;
	}
	
	public void setMinValue (Date newValue) {
		this.minValue = newValue;
	}
	
	protected Date maxValue;
	
	protected void initiateMaxValue () {
		this.maxValue = null;
	}
	
	public Date getMaxValue () {
		return this.maxValue;
	}
	
	public void setMaxValue (Date newValue) {
		this.maxValue = newValue;
	}
	
	protected void doCheck(Object data, Object property) {
		if (!(property instanceof Date)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Date value = (Date)property;
			if (value != null) {
				long _value = value.getTime();
				
				Date maxValue = getMaxValue();
				maxValue = (maxValue != null ? maxValue : null);
				if (maxValue != null && _value > maxValue.getTime()) {
					throwError(data, property, "max");
				}
				
				Date minValue = getMinValue();
				minValue = (minValue != null ? minValue : null);
				if (minValue != null && _value < minValue.getTime()) {
					throwError(data, property, "min");
				}
			}
		}
	}

}
