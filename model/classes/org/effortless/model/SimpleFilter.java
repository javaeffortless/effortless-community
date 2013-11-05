package org.effortless.model;

public class SimpleFilter<Type extends Entity<Type>> extends CriteriaFilter<Type> {

	public SimpleFilter () {
		super();
	}
	
	public SimpleFilter (Class<Type> type) {
		this();
		setType(type);
	}
	
	protected void initiate () {
		super.initiate();
		initiateType();
	}
	
	protected Class<Type> type;
	
	protected void initiateType () {
		this.type = null;
	}
	
	public Class<Type> getType () {
		return this.type;
	}
	
	public void setType (Class<Type> newValue) {
		this.type = newValue;
	}
	
	protected Class<?> doGetFilterClass () {
		return this.type;
	}
	
}
