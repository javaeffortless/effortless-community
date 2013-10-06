package org.effortless.model.restrictions;

public class CustomRestriction extends Object implements Restriction {

	public CustomRestriction () {
		super();
		initiate();
	}
	
	protected void initiate () {
		initiateClazz();
	}
	
	protected String clazz;
	
	protected void initiateClazz () {
		this.clazz = null;
	}
	
	public String getClazz () {
		return this.clazz;
	}
	
	public void setClazz (String newValue) {
		this.clazz = newValue;
	}

	protected transient Restriction restriction;
	
	protected void initiateRestriction () {
		this.restriction = null;
	}
	
	protected Restriction getRestriction () {
		return this.restriction;
	}
	
	protected void setRestriction (Restriction newValue) {
		this.restriction = newValue;
	}
	
	protected Restriction doGetRestriction () {
		if (this.restriction == null) {
			this.restriction = null;//TODO IMPLEMENT ME!!!
		}
		return this.restriction;
	}
	
	public void check(Object value) {
		if (value != null) {
			Restriction restriction = doGetRestriction();
			if (restriction != null) {
				restriction.check(value);
			}
		}
	}

}
