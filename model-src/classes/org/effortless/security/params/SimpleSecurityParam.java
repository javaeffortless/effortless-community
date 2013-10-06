package org.effortless.security.params;

import org.effortless.model.SessionManager;

public abstract class SimpleSecurityParam extends Object implements SecurityParam {

	public SimpleSecurityParam () {
		super();
		initiate();
	}
	
	public SimpleSecurityParam (String id) {
		this();
		setId(id);
	}
	
	protected void initiate () {
		initiateId();
	}
	
	protected String id;
	
	protected void initiateId () {
		this.id = null;
	}
	
	public String getId () {
		return this.id;
	}
	
	protected void setId (String newValue) {
		this.id = newValue;
	}
	
	protected Object getCurrentUser () {
		Object result = null;
		result = SessionManager.getCurrentUser();
		return result;
	}
	
	public abstract Object getValue();

}
