package org.effortless.security.params;

public abstract class CacheableSecurityParam extends SimpleSecurityParam {

	public CacheableSecurityParam () {
		super();
	}
	
	public CacheableSecurityParam (String id) {
		super(id);
	}
	
	protected void initiate () {
		super.initiate();
		initiateValue();
	}
	
	protected Object value;
	
	protected void initiateValue () {
		this.value = null;
	}
	
	protected void setValue (Object newValue) {
		this.value = newValue;
	}
	
	public Object getValue() {
		if (this.value == null) {
			this.value = doGetValue();
		}
		return this.value;
	}

	protected abstract Object doGetValue();

}
