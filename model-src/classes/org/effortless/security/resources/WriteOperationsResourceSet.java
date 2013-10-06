package org.effortless.security.resources;

import org.effortless.security.ActionType;
import org.effortless.security.Resource;

public class WriteOperationsResourceSet extends ScopeResourceSet implements ResourceSet {

	public WriteOperationsResourceSet () {
		super();
	}
	
	@Override
	public boolean contains(Resource resource) {
		boolean result = false;
		
		if (ResourceType.ACTION.equals(resource.getType())) {
			result = _belongsScope(resource);
			if (result && ActionType.FUNCTION.equals(resource.getActionType())) {
				result = false;
			}
		}

		return result;
	}

}
