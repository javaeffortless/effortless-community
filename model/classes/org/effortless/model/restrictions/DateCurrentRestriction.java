package org.effortless.model.restrictions;

import java.util.Date;

public class DateCurrentRestriction extends PropertyRestriction {

	public DateCurrentRestriction () {
		super();
	}
	
	protected void initiate () {
		super.initiate();
		initiateOp();
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
	
//	protected Date getPivotDate () {
//		Date result = null;
//		jutils.date.DateBaseUtils utils = jutils.date.DateBaseUtils.getInstance();
//		result = utils.getCurrentDate();
//		return result;
//	}
	
	protected void doCheck(Object data, Object property) {
		if (!(property instanceof Date)) {
//			throwError(dao, data, property, "incompatible");
		}
		else {
			Date value = (Date)property;
			if (value != null) {
				OpCompareType op = getOp();
				if (op != null) {
					Date pivot = org.effortless.core.DateUtils.getCurrentDate();
					pivot = org.effortless.core.DateUtils.firstTime(pivot);
					value = org.effortless.core.DateUtils.firstTime(value);
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
