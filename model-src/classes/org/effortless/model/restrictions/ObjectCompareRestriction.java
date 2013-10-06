package org.effortless.model.restrictions;

import org.effortless.core.PropertyUtils;

public class ObjectCompareRestriction extends PropertyRestriction {

	public ObjectCompareRestriction () {
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
			Object value = (Object)property;
			if (value != null) {
				String pivotProperty = getPivotProperty();
				
				Object pivot = (Object)PropertyUtils.getProperty(data, pivotProperty);
				OpCompareType op = getOp();
				if (pivot != null && op != null) {
					if (OpCompareType.NE.equals(op)) {
						if (value.equals(pivot)) {
							throwError(data, property, "compare.ne");
						}
					}
					else if (OpCompareType.EQ.equals(op)) {
						if (!value.equals(pivot)) {
							throwError(data, property, "compare.eq");
						}
					}
					else if (value instanceof Comparable && pivot instanceof Comparable) {
						Comparable comparableValue = (Comparable)value;
						Comparable comparablePivot = (Comparable)pivot;
						if (OpCompareType.LT.equals(op)) {
							if (!(comparableValue.compareTo(comparablePivot) < 0)) {
								throwError(data, property, "compare.lt");
							}
						}
						else if (OpCompareType.LE.equals(op)) {
							if (!(comparableValue.compareTo(comparablePivot) <= 0)) {
								throwError(data, property, "compare.le");
							}
						}
						else if (OpCompareType.GT.equals(op)) {
							if (!(comparableValue.compareTo(comparablePivot) > 0)) {
								throwError(data, property, "compare.gt");
							}
						}
						else if (OpCompareType.GE.equals(op)) {
							if (!(comparableValue.compareTo(comparablePivot) >= 0)) {
								throwError(data, property, "compare.ge");
							}
						}
					}
				}
			}
	}

}
