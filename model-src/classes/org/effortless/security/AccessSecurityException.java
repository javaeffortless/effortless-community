package org.effortless.security;

import org.effortless.core.ModelException;

public class AccessSecurityException extends ModelException {

	public AccessSecurityException () {
		super();
	}
	
	public AccessSecurityException (String msg) {
		super(msg);
	}
	
	public AccessSecurityException (Throwable cause) {
		super(cause);
	}
	
	public AccessSecurityException (String msg, Throwable cause) {
		super(msg, cause);
	}

	protected SecurityResponse response;

	protected void initiateResponse() {
		this.response = null;
	}

	public SecurityResponse getResponse() {
		return this.response;
	}

	public void setResponse(SecurityResponse newValue) {
		this.response = newValue;
	}

}
