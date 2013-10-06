package org.effortless.security.conflicts;

import org.effortless.security.Resource;
import org.effortless.security.resources.ResourceType;

public class AllowActions extends BaseAllowConflict {

	public AllowActions () {
		super();
	}
	
	public boolean check(Resource resource) {
		boolean result = false;
		result = ResourceType.ACTION.equals(resource.getActionType());
		return result;
	}
	
}
