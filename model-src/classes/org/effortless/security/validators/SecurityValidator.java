package org.effortless.security.validators;

public interface SecurityValidator {

	public String getId();
	
	public boolean validate (Object data);
	
}
