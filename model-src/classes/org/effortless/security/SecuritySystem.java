package org.effortless.security;


import java.io.Serializable;

public interface SecuritySystem extends Serializable {

	public SecurityResponse check (Resource resource);
	
}
