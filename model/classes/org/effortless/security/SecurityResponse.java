package org.effortless.security;


public class SecurityResponse extends Object {

	public SecurityResponse () {
		super();
		initiate();
	}

	protected void initiate () {
		initiateAllow();
		initiateSeverity();
		initiateException();
		initiateResource();
		initiatePermission();
	}
	
	protected String permission;
	
	protected void initiatePermission () {
		this.permission = null;
	}
	
	public String getPermission () {
		return this.permission;
	}
	
	public void setPermission (String newValue) {
		this.permission = newValue;
	}
	
	protected Resource resource;
	
	protected void initiateResource () {
		this.resource = null;
	}
	
	public Resource getResource () {
		return this.resource;
	}
	
	public void setResource (Resource newValue) {
		this.resource = newValue;
	}
	
	protected boolean allow;
	
	protected void initiateAllow () {
		this.allow = false;
	}
	
	public boolean isAllow() {
		return this.allow;
	}
	
	public void setAllow (boolean newValue) {
		this.allow = newValue;
	}

	protected SecuritySeverity severity;
	
	protected void initiateSeverity () {
		this.severity = null;
	}
	
	public SecuritySeverity getSeverity () {
		return this.severity;
	}
	
	public void setSeverity (SecuritySeverity newValue) {
		this.severity = newValue;
	}

	protected AccessSecurityException exception;
	
	protected void initiateException () {
		this.exception = null;
	}
	
	public AccessSecurityException getException () {
		return this.exception;
	}
	
	public void setException (AccessSecurityException newValue) {
		this.exception = newValue;
	}
	
	public void throwSecurityException() {
		AccessSecurityException exception = getException();
		if (exception != null) {
			exception.setResponse(this);
			throw exception;
		}
	}

	public int getPriority() {
		int result = 0;
		if (isAllow()) {
			result = 0;
		}
		else {
			SecuritySeverity severity = this.getSeverity();
			if (severity != null) {
				result = severity.getPriority();
			}
			else {
				result = Integer.MAX_VALUE;
			}
		}
		return result;
	}

	
}
