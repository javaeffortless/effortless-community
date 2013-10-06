package org.effortless.model.restrictions;

import java.util.Collection;

public class ListRangeRestriction extends PropertyRestriction {

	public ListRangeRestriction () {
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
		if (!(property instanceof Collection)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Collection value = (Collection)property;
			if (value != null) {
				int _value = value.size();
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
