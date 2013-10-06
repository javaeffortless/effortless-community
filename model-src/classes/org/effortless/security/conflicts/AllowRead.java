package org.effortless.security.conflicts;

import org.effortless.security.Resource;
import org.effortless.security.resources.ResourceType;

public class AllowRead extends BaseAllowConflict {

	public AllowRead () {
		super();
	}
	
	public static final String OP_READ = "read";
	
	public boolean check(Resource resource) {
		boolean result = false;
		result = ResourceType.ACTION.equals(resource.getActionType()) && OP_READ.equals(resource.getAction());
		return result;
	}
	
}
