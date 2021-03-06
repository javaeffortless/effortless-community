package org.effortless.security;


import java.io.Serializable;

public interface SecuritySystem extends Serializable {

	public SecurityResponse check (Resource resource);
	
	public Object login (String loginName, String loginPassword);
	
	public void setupSession (Object user); 
	
}
