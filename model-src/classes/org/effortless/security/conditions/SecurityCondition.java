package org.effortless.security.conditions;

import org.effortless.security.Resource;

public interface SecurityCondition {

	public String getId();
	
	public void transform (Resource resource);
	
}
