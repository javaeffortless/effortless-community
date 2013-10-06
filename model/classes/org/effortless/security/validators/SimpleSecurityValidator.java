package org.effortless.security.validators;

import org.effortless.core.GlobalContext;
import org.effortless.model.SessionManager;

public abstract class SimpleSecurityValidator extends Object implements SecurityValidator {

	public SimpleSecurityValidator () {
		super();
		initiate();
	}
	
	public SimpleSecurityValidator (String id) {
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
	
	public boolean validate(Object data) {
		boolean result = false;
		if (data != null) {
			result = doValidate(data);
		}
		else {
			result = true;
		}
		return result;
	}

	protected abstract boolean doValidate(Object data);

}
