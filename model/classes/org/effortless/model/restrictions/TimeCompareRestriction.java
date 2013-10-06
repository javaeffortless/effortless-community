package org.effortless.model.restrictions;

import java.util.Date;

import org.effortless.core.PropertyUtils;

public class TimeCompareRestriction extends PropertyRestriction {

	public TimeCompareRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiatePivotProperty();
		initiateOp();
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
	
	protected OpCompareType op;
	
	protected void initiateOp () {
		this.op = null;
	}
	
	public OpCompareType getOp () {
		return this.op;
	}
	
	public void setOp (OpCompareType newValue) {
		this.op = newValue;
	}
	
	protected void doCheck(Object data, Object property) {
		if (!(property instanceof Date)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Date value = (Date)property;
			if (value != null) {
				String pivotProperty = getPivotProperty();
				
				Date pivot = (Date)PropertyUtils.getProperty(data, pivotProperty);
				OpCompareType op = getOp();
				if (pivot != null && op != null) {
					pivot = org.effortless.core.DateUtils.resetDay(pivot);
					value = org.effortless.core.DateUtils.resetDay(value);
					if (OpCompareType.LT.equals(op)) {
						if (!org.effortless.core.DateUtils.isPrevious(value, pivot)) {
							throwError(data, property, "compare.lt");
						}
					}
					else if (OpCompareType.LE.equals(op)) {
						if (!org.effortless.core.DateUtils.isPreviousEqual(value, pivot)) {
							throwError(data, property, "compare.le");
						}
					}
					else if (OpCompareType.GT.equals(op)) {
						if (!org.effortless.core.DateUtils.isLater(value, pivot)) {
							throwError(data, property, "compare.gt");
						}
					}
					else if (OpCompareType.GE.equals(op)) {
						if (!org.effortless.core.DateUtils.isLaterEqual(value, pivot)) {
							throwError(data, property, "compare.ge");
						}
					}
					else if (OpCompareType.NE.equals(op)) {
						if (org.effortless.core.DateUtils.isEquals(value, pivot)) {
							throwError(data, property, "compare.ne");
						}
					}
					else if (OpCompareType.EQ.equals(op)) {
						if (!org.effortless.core.DateUtils.isEquals(value, pivot)) {
							throwError(data, property, "compare.eq");
						}
					}
				}
			}
		}
	}

}
